package org.columba.mail.search;

import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import org.columba.core.filter.FilterCriteria;
import org.columba.core.filter.FilterRule;
import org.columba.mail.folder.IMailFolder;
import org.columba.mail.folder.IMailbox;
import org.columba.mail.folder.virtual.VirtualFolder;
import org.columba.mail.gui.tree.FolderTreeModel;

public class SearchFolderFactory {

	/**
	 * Create search folder for simple search with a single criteria
	 */
	public static VirtualFolder createSearchFolder(FilterCriteria c,
			IMailFolder folder) throws Exception {

		// create search folder
		VirtualFolder searchFolder = createVirtualFolder(folder);

		// add filter criteria
		searchFolder.getFilter().getFilterRule().add(c);

		return searchFolder;
	}

	/**
	 * Create new search folder but use existing virtual folder for an "search
	 * inside results" search
	 */
	public static VirtualFolder prepareSearchFolder(FilterCriteria c,
			IMailFolder folder) throws Exception {

		// create search folder
		VirtualFolder searchFolder = createVirtualFolder(folder);

		// add filter criteria
		searchFolder.getFilter().getFilterRule().add(c);

		return searchFolder;
	}

	/**
	 * Create search for folder complex search with multiple criteria
	 */
	public static VirtualFolder createSearchFolder(FilterRule rule,
			IMailFolder folder) throws Exception {
		// create search folder
		VirtualFolder searchFolder = createVirtualFolder(folder);

		searchFolder.getFilter().getFilterRule().setCondition(
				rule.getConditionInt());

		for (int i = 0; i < rule.getChildCount(); i++) {
			// add filter criteria
			searchFolder.getFilter().getFilterRule().add(rule.get(i));
		}

		return searchFolder;
	}

	/**
	 * Update already existing virtual folder for an "search inside results"
	 * search
	 */
	public static VirtualFolder prepareSearchFolder(FilterRule rule,
			IMailFolder folder) throws Exception {

		// create search folder
		VirtualFolder searchFolder = createVirtualFolder(folder);

		searchFolder.getFilter().getFilterRule().setCondition(
				rule.getConditionInt());

		for (int i = 0; i < rule.getChildCount(); i++) {
			// add filter criteria
			searchFolder.getFilter().getFilterRule().add(rule.get(i));
		}

		return searchFolder;
	}

	/**
	 * Create virtual folder.
	 *
	 * @param folder
	 * @return
	 */
	private static VirtualFolder createVirtualFolder(IMailFolder folder) {

		VirtualFolder searchFolder = new VirtualFolder("Search Result", folder);

		// remove old filters
		searchFolder.getFilter().getFilterRule().removeAll();

		// search in subfolders recursively
		searchFolder.getConfiguration().setString("property",
				"include_subfolders", "true");

		String uid = folder.getId();

		// set source folder UID
		searchFolder.getConfiguration()
				.setString("property", "source_uid", uid);

		return searchFolder;
	}

	/**
	 * Return list of all source folders we going to query.
	 *
	 * @return list of source folders
	 */
	public static List<IMailbox> getAllSourceFolders() {
		IMailFolder rootFolder = (IMailFolder) FolderTreeModel.getInstance()
				.getRoot();

		List<IMailbox> v = new Vector<IMailbox>();
		getChildren(rootFolder, v);

		return v;
	}

	private static void getChildren(IMailFolder parentFolder,
			List<IMailbox> list) {
		IMailFolder child;

		for (Enumeration e = parentFolder.children(); e.hasMoreElements();) {
			child = (IMailFolder) e.nextElement();

			if (child instanceof IMailbox) {
				if (child.getName().equalsIgnoreCase("Inbox")) {
					list.add((IMailbox) child);
				}
			}

			getChildren(child, list);
		}
	}
}
