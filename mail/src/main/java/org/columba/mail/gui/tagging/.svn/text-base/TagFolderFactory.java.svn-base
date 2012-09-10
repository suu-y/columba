package org.columba.mail.gui.tagging;

import org.columba.core.tagging.api.ITag;
import org.columba.mail.folder.IMailFolder;
import org.columba.mail.folder.virtual.VirtualFolder;

/**
 * NOT USED YET!
 *
 * @author hubms
 *
 */
public class TagFolderFactory {

	public static VirtualFolder createTagFolder(ITag tag, IMailFolder folder)
			throws Exception {

		// get search folder
		VirtualFolder tagFolder = new VirtualFolder("Tag Folder", folder);

		// TODO: @author hubms check what is neccessary
		// remove old filters
		// searchFolder.getFilter().getFilterRule().removeAll();
		// add filter criteria
		// searchFolder.getFilter().getFilterRule().add(c);

		// search in subfolders recursively
		tagFolder.getConfiguration().setString("property",
				"include_subfolders", "true");

		String uid = folder.getId();

		// set source folder UID
		tagFolder.getConfiguration().setString("property", "source_uid", uid);

		return tagFolder;
	}

}
