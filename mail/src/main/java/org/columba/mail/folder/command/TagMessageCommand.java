package org.columba.mail.folder.command;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.columba.api.command.IWorkerStatusController;
import org.columba.core.association.AssociationStore;
import org.columba.core.command.Command;
import org.columba.core.folder.api.IFolder;
import org.columba.mail.command.IMailFolderCommandReference;
import org.columba.mail.folder.IMailbox;
import org.columba.mail.folder.virtual.VirtualFolder;
import org.columba.mail.folder.virtual.VirtualHeader;
import org.columba.mail.message.IHeaderList;

public class TagMessageCommand extends Command {

	public final static int ADD_TAG = 0;

	public final static int REMOVE_TAG = 1;

	private static final Logger LOG = Logger
	.getLogger("org.columba.mail.folder.command.TagMessageCommand");

	private int type;

	private String id;

	public TagMessageCommand(IMailFolderCommandReference theReference,
			int type, String id) {
		super(theReference);
		this.type = type;
		this.id = id;
	}

	@Override
	public void execute(IWorkerStatusController worker) throws Exception {
		// get array of source references
		IMailFolderCommandReference r = (IMailFolderCommandReference) getReference();

		// does not work: r.getFolderName()
		// does not work: r.getMessage().getUID()

		// get source folder
		IMailbox srcFolder = (IMailbox) r.getSourceFolder();

		Hashtable<Object, IFolder> mails = new Hashtable<Object, IFolder>();

		// check if virtual folder, if yes, do not use these uids, use the
		// real uids instead
		if (srcFolder instanceof VirtualFolder) {
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
			}
		} else {
			for (Object uid : r.getUids()) {
				mails.put(uid, r.getSourceFolder());
			}
		}

		if (type == ADD_TAG) {
			for (Entry<Object, IFolder> entry : mails.entrySet()) {
				AssociationStore.getInstance().addAssociation("tagging", id,
						createURI(entry.getValue().getId(), entry.getKey()).toString());
			}
		} else if (type == REMOVE_TAG) {
			for (Entry<Object, IFolder> entry : mails.entrySet()) {
				AssociationStore.getInstance().removeAssociation("tagging", id,
						createURI(entry.getValue().getId(), entry.getKey()).toString());
			}
		}
	}

	// TODO @author hubms: copied from frederiks search builder
	public static URI createURI(String folderId, Object messageId) {
		URI uri = null;
		try {
			uri = new URI("columba://org.columba.mail/" + folderId + "/"
					+ messageId);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return uri;
	}

}
