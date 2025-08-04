package org.columba.mail.facade;

import java.net.URI;

import org.columba.api.gui.frame.IContainer;
import org.columba.api.gui.frame.IFrameMediator;
import org.columba.core.command.CommandProcessor;
import org.columba.core.gui.frame.DefaultContainer;
import org.columba.core.gui.frame.FrameManager;
import org.columba.mail.command.IMailFolderCommandReference;
import org.columba.mail.command.MailFolderCommandReference;
import org.columba.mail.folder.IMailbox;
import org.columba.mail.gui.composer.ComposerController;
import org.columba.mail.gui.composer.ComposerModel;
import org.columba.mail.gui.frame.ThreePaneMailFrameController;
import org.columba.mail.gui.message.command.ViewMessageCommand;
import org.columba.mail.gui.messageframe.MessageFrameController;
import org.columba.mail.gui.tree.FolderTreeModel;
import org.columba.ristretto.message.Address;

public class DialogFacade implements IDialogFacade {

	public DialogFacade() {
		super();
	}

	/**
	 * @see org.columba.mail.facade.IDialogFacade#openComposer()
	 */
	public void openComposer() {
		// Choice btw. text and html will be based on stored option
		ComposerController controller = new ComposerController();
		new DefaultContainer(controller);

		// model -> view
		controller.updateComponents(true);
	}

	/**
	 * @see org.columba.mail.facade.IDialogFacade#openMessage(java.net.URI)
	 */
	public void openMessage(URI location) {
		// example: "columba://org.columba.mail/<folder-id>/<message-id>"
		String s = location.toString();

		System.out.println("SATD ID: 22");
		int index = s.lastIndexOf('/');
		String messageId = s.substring(index + 1, s.length());
		String folderId = s.substring(s.lastIndexOf('/', index - 1) + 1, index);

		IContainer[] container = FrameManager.getInstance().getOpenFrames();
		if (container == null || container.length == 0)
			throw new RuntimeException("No frames available");

		IFrameMediator mailFrameMediator = null;
		for (int i = 0; i < container.length; i++) {
			IFrameMediator mediator = container[i].getFrameMediator();
			if (mediator.getId().equals("ThreePaneMail")) {
				// found mail component frame
				mailFrameMediator = mediator;
			}
		}

		if (mailFrameMediator == null)
			throw new RuntimeException("No mail frame mediator found");

		// type-cast here is safe
		MessageFrameController c = new MessageFrameController(
				(ThreePaneMailFrameController) mailFrameMediator);
		new DefaultContainer(c);

		IMailbox folder = (IMailbox) FolderTreeModel.getInstance().getFolder(
				folderId);
		IMailFolderCommandReference r = new MailFolderCommandReference(folder,
				new Object[] { Integer.parseInt(messageId) });

		c.setTreeSelection(r);

		c.setTableSelection(r);

		CommandProcessor.getInstance().addOp(new ViewMessageCommand(c, r));
	}

	public void openComposer(String contact) {
		// Choice btw. text and html will be based on stored option
		ComposerController controller = new ComposerController();
		new DefaultContainer(controller);
		ComposerModel model = controller.getModel();
		Address adr = new Address(contact);
		model.setTo(new Address[] { adr });
		// model -> view
		controller.updateComponents(true);
	}

	public void openComposer(String[] recipients) {
		// Choice btw. text and html will be based on stored option
		ComposerController controller = new ComposerController();
		new DefaultContainer(controller);
		ComposerModel model = controller.getModel();
		Address[] adr = new Address[recipients.length];
		for (int i = 0; i < recipients.length; i++) {
			adr[i] = new Address(recipients[i]);
		}

		model.setTo(adr);
		// model -> view
		controller.updateComponents(true);
	}

}
