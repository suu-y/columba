package org.columba.mail.gui.table.action;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.columba.api.gui.frame.IFrameMediator;
import org.columba.api.selection.ISelectionListener;
import org.columba.api.selection.SelectionChangedEvent;
import org.columba.core.association.AssociationStore;
import org.columba.core.association.api.IAssociation;
import org.columba.core.command.CommandProcessor;
import org.columba.core.folder.api.IFolder;
import org.columba.core.gui.tagging.TaggingMenu;
import org.columba.core.tagging.TagManager;
import org.columba.core.tagging.api.ITag;
import org.columba.mail.command.IMailFolderCommandReference;
import org.columba.mail.folder.IMailbox;
import org.columba.mail.folder.command.TagMessageCommand;
import org.columba.mail.folder.virtual.VirtualFolder;
import org.columba.mail.folder.virtual.VirtualHeader;
import org.columba.mail.gui.frame.MailFrameMediator;
import org.columba.mail.gui.table.selection.TableSelectionChangedEvent;
import org.columba.mail.message.IHeaderList;
import org.columba.mail.util.MailResourceLoader;

public class TagMessageMenu extends TaggingMenu implements ISelectionListener {

	final static private String SERVICE_ID = "tagging";

	private static final Logger LOG = Logger
			.getLogger("org.columba.mail.gui.table.action.TagMessageMenu");

	/**
	 * @param controller
	 */
	public TagMessageMenu(IFrameMediator controller) {
		super(controller, MailResourceLoader.getString("dialog", "tagging",
				"menu_tag_message"), "menu_tag_message");

		((MailFrameMediator) controller).registerTableSelectionListener(this);
	}

	// listener updates selection status, when ever a message selection changes
	public void selectionChanged(SelectionChangedEvent e) {
		if (((TableSelectionChangedEvent) e).getUids().length > 0) {
			setEnabled(true);
			removeAll();
			createSubMenu();
		} else {
			setEnabled(false);
		}

	}

	private boolean checkOneUid(Object uid, IFolder srcFolder, ITag tag) {
		for (IAssociation as : AssociationStore.getInstance()
				.getAllAssociations(
						TagMessageCommand.createURI(srcFolder.getId(), uid)
								.toString())) {
			if (as.getServiceId().equals(SERVICE_ID)
					&& (as.getMetaDataId() != null)
					&& (as.getMetaDataId().equals(tag.getId()))) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected void assignTag(String tagId) {

		// get current message list selection
		IMailFolderCommandReference r = ((MailFrameMediator) getFrameMediator())
				.getTableSelection();

		// pass command to scheduler
		CommandProcessor.getInstance().addOp(
				new TagMessageCommand(r, TagMessageCommand.ADD_TAG, tagId));
	}

	@Override
	protected boolean isTagged(ITag tag) {
		// get current message list selection
		IMailFolderCommandReference r = ((MailFrameMediator) getFrameMediator())
				.getTableSelection();

		if ((r == null) || (r.getUids().length <= 0) || (tag == null))
			return false;

		Hashtable<Object, IFolder> mails = new Hashtable<Object, IFolder>();

		// check if virtual folder, if yes, do not use these uids, use the
		// real uids instead
		if (r.getSourceFolder() instanceof VirtualFolder) {
			// get original folder
			try {

				IHeaderList hl = ((IMailbox) r.getSourceFolder())
						.getHeaderList();
				for (Object uid : r.getUids()) {
					// should be virtual
					mails.put(((VirtualHeader) hl.get(uid)).getSrcUid(),
							((VirtualHeader) hl.get(uid)).getSrcFolder());
				}
			} catch (Exception e) {
				LOG.severe("Error getting header list from virtual folder");
				e.printStackTrace();
				return false;
			}
		} else {
			for (Object uid : r.getUids()) {
				mails.put(uid, r.getSourceFolder());
			}
		}

		// if all messages are tagged with tag, then return true, else false
		boolean result = true;
		for (Entry<Object, IFolder> entry : mails.entrySet()) {
			boolean current = checkOneUid(entry.getKey(), entry.getValue(), tag);
			if (current == false)
				return false;
		}
		return result;
	}

	@Override
	protected void removeAllTags() {

		// get current message list selection
		IMailFolderCommandReference r = ((MailFrameMediator) getFrameMediator())
				.getTableSelection();

		for (Iterator<ITag> iter = TagManager.getInstance().getAllTags(); iter
				.hasNext();) {

			ITag tag = iter.next();
			// TODO @author hubms: bad: if there is a icon it is tagged or
			// not

			// pass command to scheduler
			CommandProcessor.getInstance().addOp(
					new TagMessageCommand(r, TagMessageCommand.REMOVE_TAG, tag
							.getId()));
		}
	}

	@Override
	protected void removeTag(String tagId) {

		// get current message list selection
		IMailFolderCommandReference r = ((MailFrameMediator) getFrameMediator())
				.getTableSelection();

		CommandProcessor.getInstance().addOp(
				new TagMessageCommand(r, TagMessageCommand.REMOVE_TAG, tagId));
	}

}
