// The contents of this file are subject to the Mozilla Public License Version
// 1.1
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
//The Initial Developers of the Original Code are Frederik Dietz and Timo
// Stich.
//Portions created by Frederik Dietz and Timo Stich are Copyright (C) 2003.
//
//All Rights Reserved.
package org.columba.addressbook.folder;

import java.util.Hashtable;
import java.util.Map;

import org.columba.addressbook.model.IContactModel;
import org.columba.addressbook.model.IContactModelPartial;
import org.columba.api.exception.StoreException;

/**
 * Contact item cache storage.
 *
 * @author fdietz
 *
 */
public class ContactItemCacheStorageImpl implements ContactItemCacheStorage {
	/**
	 *
	 * keeps a list of HeaderItem's we need for the table-view
	 *
	 */
	private Hashtable<String, IContactModelPartial> map;

	/**
	 *
	 */
	public ContactItemCacheStorageImpl(AbstractFolder folder) {
		super();

		map = new Hashtable<String, IContactModelPartial>();
	}

	/**
	 * @see org.columba.addressbook.folder.ContactItemCacheStorage#getHeaderItemMap()
	 */
	public Map<String, IContactModelPartial> getContactItemMap() {
		return map;
	}

	/**
	 * @see org.columba.addressbook.folder.ContactItemCacheStorage#add(IContactModel)
	 */
	public void add(String uid, IContactModelPartial item)
			throws StoreException {
		map.put(uid, item);

	}

	/**
	 * @see org.columba.addressbook.folder.ContactItemCacheStorage#remove(java.lang.Object)
	 */
	public void remove(String uid) throws StoreException {
		map.remove(uid);

	}

	/**
	 * @see org.columba.addressbook.folder.ContactItemCacheStorage#modify(java.lang.Object,
	 *      IContactModel)
	 */
	public void modify(String uid, IContactModelPartial item)
			throws StoreException {
		map.remove(uid);
		map.put(uid, item);

	}

	/**
	 * @see org.columba.addressbook.folder.ContactItemCacheStorage#save()
	 */
	public void save() throws StoreException {

	}

	/**
	 * @see org.columba.addressbook.folder.ContactItemCacheStorage#load()
	 */
	public void load() throws StoreException {

	}

	/**
	 * @see org.columba.addressbook.folder.ContactItemCacheStorage#count()
	 */
	public int count() {
		return map.size();
	}

	/**
	 * @see org.columba.addressbook.folder.ContactItemCacheStorage#exists(java.lang.Object)
	 */
	public boolean exists(String uid) {
		return map.containsKey(uid);
	}

	/**
	 * @see org.columba.addressbook.folder.ContactItemCacheStorage#getContactItemMap(java.lang.String[])
	 */
	public Map<String, IContactModelPartial> getContactItemMap(String[] ids)
			throws StoreException {
		if (ids == null)
			throw new IllegalArgumentException("ids == null");

		Map<String, IContactModelPartial> result = new Hashtable<String, IContactModelPartial>();

		for (int i = 0; i < ids.length; i++) {
			// skip, if null
			if (ids[i] == null)
				continue;

			IContactModelPartial p = map.get(ids[i]);
			if (p != null)
				result.put(ids[i], p);
		}

		return result;
	}
}
