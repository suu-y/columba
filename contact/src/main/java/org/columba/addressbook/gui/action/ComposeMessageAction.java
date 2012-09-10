package org.columba.addressbook.gui.action;

import java.awt.event.ActionEvent;

import org.columba.addressbook.folder.AddressbookFolder;
import org.columba.addressbook.gui.frame.AddressbookFrameMediator;
import org.columba.addressbook.model.IContactModel;
import org.columba.api.exception.ServiceNotFoundException;
import org.columba.api.gui.frame.IFrameMediator;
import org.columba.core.facade.ServiceFacadeRegistry;
import org.columba.core.resourceloader.IconKeys;
import org.columba.core.resourceloader.ImageLoader;
import org.columba.mail.facade.IDialogFacade;

public class ComposeMessageAction extends DefaultTableAction {
	/**
	 * @param frameMediator
	 * @param name
	 */
	public ComposeMessageAction(IFrameMediator frameMediator) {
		super(frameMediator, "Compose Message");

		putValue(SMALL_ICON, ImageLoader.getSmallIcon(IconKeys.MAIL_NEW));
		putValue(LARGE_ICON, ImageLoader.getIcon(IconKeys.MAIL_NEW));

		// tooltip text
		putValue(SHORT_DESCRIPTION, "Compose Message");

		putValue(TOOLBAR_NAME, "Compose Message");
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
		AddressbookFrameMediator mediator = (AddressbookFrameMediator) frameMediator;

		// get selected folder
		AddressbookFolder sourceFolder = (AddressbookFolder) mediator.getTree()
				.getSelectedFolder();

		// get selected contact/group card
		String[] uids = mediator.getTable().getUids();

		try {
			String[] adr = new String[uids.length];
			for (int i = 0; i < uids.length; i++) {
				IContactModel contact = sourceFolder.get(uids[i]);
				adr[i] = contact.getPreferredEmail();
			}

			IDialogFacade facade = (IDialogFacade) ServiceFacadeRegistry
					.getInstance().getService(
							org.columba.mail.facade.IDialogFacade.class);
			facade.openComposer(adr);
		} catch (ServiceNotFoundException e) {
			e.printStackTrace();
		}
	}
}
