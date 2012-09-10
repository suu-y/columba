//The contents of this file are subject to the Mozilla Public License Version 1.1
//(the "License"); you may not use this file except in compliance with the 
//License. You may obtain a copy of the License at http://www.mozilla.org/MPL/
//
//Software distributed under the License is distributed on an "AS IS" basis,
//WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License 
//for the specific language governing rights and
//limitations under the License.
//
//The Original Code is "The Columba Project"
//
//The Initial Developers of the Original Code are Frederik Dietz and Timo Stich.
//Portions created by Frederik Dietz and Timo Stich are Copyright (C) 2003. 
//
//All Rights Reserved.
package org.columba.addressbook.folder;

import java.io.File;

import org.columba.addressbook.config.FolderItem;
import org.columba.addressbook.model.ContactModelFactory;
import org.columba.addressbook.model.ContactModelXMLFactory;
import org.columba.addressbook.model.IContactModel;
import org.columba.addressbook.model.IContactModelPartial;
import org.columba.api.exception.StoreException;
import org.columba.core.config.DefaultConfigDirectory;
import org.columba.core.io.DiskIO;
import org.columba.core.xml.XmlNewIO;
import org.jdom.Document;

/**
 * 
 * AbstractLocalFolder-class gives as an additional abstraction-layer: -->
 * IDataStorage
 * 
 * this makes it very easy to add other folder-formats
 * 
 * the important methods from Folder are just mapped to the corresponding
 * methods from IDataStorage
 * 
 * 
 */
public abstract class LocalFolder extends AbstractFolder {

	protected DataStorage dataStorage = null;

	/**
	 * directory where contact files are stored
	 */
	private File directoryFile;

	public LocalFolder(String name, String path) {
		super(name, path);

		if (DiskIO.ensureDirectory(path)) {
			directoryFile = new File(path);
		}
	}

	public LocalFolder(FolderItem item) {
		super(item);

		String dir = DefaultConfigDirectory.getInstance().getCurrentPath()
				+ "/addressbook/" + getId();

		if (DiskIO.ensureDirectory(dir)) {
			directoryFile = new File(dir);
		}
	}

	private void initCache() {
		cacheStorage = new ContactItemCacheStorageImpl(this);

		File[] list = directoryFile.listFiles();

		for (int i = 0; i < list.length; i++) {
			File file = list[i];
			String name = file.getName();
			int index = name.indexOf("header");

			if (index == -1) {

				if ((file.exists()) && (file.length() > 0)) {
					int extension = name.indexOf('.');
					if (extension == -1)
						continue;
					int uid;
					try {
						uid = Integer.parseInt(name.substring(0, extension));
					} catch(NumberFormatException e) {
						continue;
					}

					try {
						Document doc = XmlNewIO.load(file);

						IContactModel model = ContactModelXMLFactory.unmarshall(doc,
								new Integer(uid).toString());

						IContactModelPartial item = ContactModelFactory
								.createContactModelPartial(model, new Integer(uid)
										.toString());
						cacheStorage.add(new Integer(uid).toString(), item);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}

			} else {
				// header file found
				file.delete();
			}
		}
	}

	public ContactItemCacheStorage getCacheStorage() {
		if (cacheStorage == null) {
			initCache();
		}
		return cacheStorage;
	}

	public abstract DataStorage getDataStorageInstance();

	/**
     * Returns folder where we save everything name of folder is usually the UID-number
     *
     * @return folder where we save everything name of folder is usually the UID-number
     */
    public File getDirectoryFile() {
        return directoryFile;
	}

	/**
	 * @see org.columba.addressbook.folder.IContactStorage#add(IContactModel)
	 */
	public String add(IContactModel contact) throws StoreException {
		String uid = super.add(contact);

		getDataStorageInstance().save(uid, contact);

		fireItemAdded(uid);

		return uid;
	}

	/**
	 * @see org.columba.addressbook.folder.IContactStorage#modify(java.lang.Object,
	 *      IContactModel)
	 */
	public void modify(String uid, IContactModel contact) throws StoreException {
		super.modify(uid, contact);

		getDataStorageInstance().modify(uid, contact);

		fireItemChanged(uid);
	}

	/**
	 * @see org.columba.addressbook.folder.IContactStorage#remove(java.lang.Object)
	 */
	public void remove(String uid) throws StoreException {
		super.remove(uid);

		getDataStorageInstance().remove(uid);

		fireItemRemoved(uid);
	}

	/**
	 * @see org.columba.addressbook.folder.IContactStorage#get(java.lang.Object)
	 */
	public IContactModel get(String uid) throws StoreException {
		return getDataStorageInstance().load(uid);
	}
}
