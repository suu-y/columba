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
//Portions created by Frederik Dietz and Timo Stich are Copyright (C) 2006. 
//
//All Rights Reserved.

package org.columba.mail.gui.action;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import org.columba.api.gui.frame.IFrameMediator;
import org.columba.core.gui.action.AbstractColumbaAction;
import org.columba.core.gui.util.FindDialog;
import org.columba.core.resourceloader.GlobalResourceLoader;
import org.columba.core.resourceloader.IconKeys;
import org.columba.core.resourceloader.ImageLoader;
import org.columba.mail.gui.frame.ThreePaneMailFrameController;
import org.columba.mail.gui.message.MessageController;
import org.columba.mail.gui.message.util.MessageViewerText;

/**
 * @author Erich Schaer, Dmytro Podalyuk
 */
@SuppressWarnings("serial")
public class FindAction extends AbstractColumbaAction {
	public FindAction(IFrameMediator controller) {
		super(controller, GlobalResourceLoader.getString(null, null,
				"menu_edit_find"));

		// tooltip text
		putValue(SHORT_DESCRIPTION, GlobalResourceLoader.getString(null, null,
				"menu_edit_find_tooltip").replaceAll("&", ""));

		// small icon for menu
		putValue(SMALL_ICON, ImageLoader.getSmallIcon(IconKeys.EDIT_FIND));

		// large icon for toolbar
		putValue(LARGE_ICON, ImageLoader.getIcon(IconKeys.EDIT_FIND));

		// shortcut key
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F,
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

		setEnabled(true);
	}

	public void actionPerformed(ActionEvent e) {
		// if we search in The MessageConroller
		if (getFrameMediator() instanceof ThreePaneMailFrameController) {
			ThreePaneMailFrameController controller = (ThreePaneMailFrameController) getFrameMediator();
			// get the message controller
			MessageController msg = (MessageController) controller
					.getMessageController();
			MessageViewerText text = new MessageViewerText(msg);
			new FindDialog(text);
		}
	}
}
