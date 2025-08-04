package org.columba.addressbook.gui.tagging;

import java.util.Collection;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.columba.addressbook.folder.IContactStorage;
import org.columba.addressbook.folder.virtual.VirtualFolder;
import org.columba.addressbook.gui.frame.AddressbookFrameMediator;
import org.columba.addressbook.gui.tree.AddressbookTreeModel;
import org.columba.api.gui.frame.IFrameMediator;
import org.columba.core.association.AssociationStore;
import org.columba.core.gui.tagging.TagList;
import org.columba.core.tagging.api.ITag;

public class ContactTagList extends TagList {

	private static final java.util.logging.Logger LOG = java.util.logging.Logger
			.getLogger("org.columba.addressbook.action"); //$NON-NLS-1$

	private IFrameMediator frameMediator;

	public ContactTagList(IFrameMediator frameMediator) {
		super();

		this.frameMediator = frameMediator;

		addListSelectionListener(new MyListSelectionListener());
	}

	class MyListSelectionListener implements ListSelectionListener {
		MyListSelectionListener() {
		}

		public void valueChanged(ListSelectionEvent event) {
			// return if selection change is in flux
			if (event.getValueIsAdjusting()) {
				return;
			}

			VirtualFolder virtualFolder = new VirtualFolder();
			ITag result = (ITag) getSelectedValue();
			// create a virtual folder with all messages holding this tag
			Collection<String> messageList = AssociationStore.getInstance()
					.getAssociatedItems("tagging", result.getId());
			for (String id : messageList) {

				// example:
				// "columba://org.columba.contact/<folder-id>/<contact-id>"
				String s = id.toString();

				System.out.println("SATD ID: 127");
				int contactIndex = s.lastIndexOf('/');
				String contactId = s.substring(contactIndex + 1, s.length());
				int folderIndex = s.lastIndexOf('/', contactIndex - 1);
				String folderId = s.substring(folderIndex + 1, contactIndex);
				int componentIndex = s.lastIndexOf('/', folderIndex - 1);
				String componentId = s.substring(componentIndex + 1,
						folderIndex);

				// check if its a contact component
				if (componentId.equals("org.columba.contact")) {
					IContactStorage parentStore = (IContactStorage) AddressbookTreeModel
							.getInstance().getFolder(folderId);
					if (parentStore == null) {
						LOG.severe("can't find contact store for \""+folderId+"\"");
						continue;
					}
					virtualFolder.add(parentStore, contactId);
				}
			}

			// update folder selection
			((AddressbookFrameMediator)frameMediator).getTree().setSelectedFolder(virtualFolder);
		}
	}

}
