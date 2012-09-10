package org.columba.addressbook.gui.action;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import org.columba.addressbook.facade.IContactItem;
import org.columba.addressbook.folder.IFolder;
import org.columba.addressbook.gui.frame.AddressbookFrameMediator;
import org.columba.addressbook.util.AddressbookResourceLoader;
import org.columba.api.gui.frame.IFrameMediator;
import org.columba.core.association.AssociationStore;
import org.columba.core.association.api.IAssociation;
import org.columba.core.gui.tagging.TaggingMenu;
import org.columba.core.tagging.TagManager;
import org.columba.core.tagging.api.ITag;

public class TagContactMenu extends TaggingMenu implements
		ListSelectionListener, TreeSelectionListener {

	final static private String SERVICE_ID = "tagging";

	private String folderId;

	private String contactId;

	/**
	 * @param frameMediator
	 */
	public TagContactMenu(IFrameMediator frameMediator) {
		super(frameMediator, AddressbookResourceLoader.getString("dialog",
				"tagging", "contact_tag_message"), "menu_tag_message");

		// register interest on contact selection changes
		((AddressbookFrameMediator) frameMediator)
				.addTableSelectionListener(this);

		// register interest on tree selection changes
		((AddressbookFrameMediator) frameMediator)
				.addTreeSelectionListener(this);
	}

	/**
	 */
	public void valueChanged(TreeSelectionEvent e) {
		TreePath path = e.getNewLeadSelectionPath();

		// remember last selected folder treenode
		if (path != null) {
			IFolder folder = (IFolder) path.getLastPathComponent();
			folderId = folder.getId();
		}

	}

	/**
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	public void valueChanged(ListSelectionEvent event) {
		// return if selection change is in flux
		if (event.getValueIsAdjusting()) {
			return;
		}

		Object[] uids = ((AddressbookFrameMediator) getFrameMediator())
				.getTable().getUids();

		if (uids.length > 0) {
			setEnabled(true);

			removeAll();
			createSubMenu();
		} else {
			setEnabled(false);
		}
	}

	@Override
	protected void assignTag(String tagId) {

		String[] ids = ((AddressbookFrameMediator) getFrameMediator())
				.getTable().getUids();
		for (int i = 0; i < ids.length; i++) {
			String contactId = ids[i];
			AssociationStore.getInstance().addAssociation(SERVICE_ID, tagId,
					createURI(folderId, contactId).toString());
		}
	}

	@Override
	protected boolean isTagged(ITag tag) {
		String[] ids = ((AddressbookFrameMediator) getFrameMediator())
				.getTable().getUids();
		boolean tagged = true;
		for (int i = 0; i < ids.length; i++) {
			String contactId = ids[i];

			tagged &= checkAssocation(folderId, contactId, tag);
		}

		return tagged;
	}

	@Override
	protected void removeAllTags() {
		IContactItem contactItem = ((AddressbookFrameMediator) getFrameMediator())
				.getTable().getSelectedItem();
		String contactId = contactItem.getId();

		for (Iterator<ITag> iter = TagManager.getInstance().getAllTags(); iter
				.hasNext();) {
			ITag tag = iter.next();

			AssociationStore.getInstance().removeAssociation(SERVICE_ID,
					tag.getId(), createURI(folderId, contactId).toString());
		}
	}

	@Override
	protected void removeTag(String tagId) {

		String[] ids = ((AddressbookFrameMediator) getFrameMediator())
				.getTable().getUids();
		for (int i = 0; i < ids.length; i++) {
			String contactId = ids[i];
			AssociationStore.getInstance().removeAssociation(SERVICE_ID, tagId,
					createURI(folderId, contactId).toString());
		}

	}

	// create URI representing the contact
	public static URI createURI(String folderId, Object contactId) {
		URI uri = null;
		try {
			uri = new URI("columba://org.columba.contact/" + folderId + "/"
					+ contactId);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return uri;
	}

	// check if contact is tagged
	private boolean checkAssocation(String folderId, String contactId, ITag tag) {
		URI uri = createURI(folderId, contactId);
		Collection<IAssociation> c = AssociationStore.getInstance()
				.getAllAssociations(uri.toString());

		for (IAssociation as : c) {
			if (as.getServiceId().equals(SERVICE_ID)
					&& (as.getMetaDataId() != null)
					&& (as.getMetaDataId().equals(tag.getId()))) {
				return true;
			}
		}
		return false;
	}

}
