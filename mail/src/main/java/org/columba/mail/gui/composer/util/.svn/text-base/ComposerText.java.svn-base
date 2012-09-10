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

package org.columba.mail.gui.composer.util;

import org.columba.core.util.IEditableText;
import org.columba.mail.gui.composer.ComposerController;

/**
 * This is an implementation of the IEditableText for the ComposerController.
 * The functions do the same as in the JTextPane class. 
 * @author Dmytro Podalyuk
 */
public class ComposerText implements IEditableText {

	ComposerController controller;

	public ComposerText(ComposerController controller) {
		this.controller = controller;
	}

	public void setCaretPosition(int position) {
		controller.getCurrentEditor().getViewUIComponent().setCaretPosition(
				position);
	}

	public void moveCaretPosition(int position) {
		controller.getCurrentEditor().getViewUIComponent()
				.moveCaretPosition(position);
	}

	public String getText() {
		return controller.getCurrentEditor().getViewUIComponent().getText();
	}

	public void setText(String text) {
		controller.getCurrentEditor().getViewUIComponent().setText(text);
	}

	public void grabFocus() {
		controller.getCurrentEditor().getViewUIComponent().grabFocus();
	}
}
