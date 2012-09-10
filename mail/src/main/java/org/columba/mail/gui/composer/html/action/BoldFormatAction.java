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
//Portions created by Frederik Dietz and Timo Stich are Copyright (C) 2003.
//
//All Rights Reserved.
package org.columba.mail.gui.composer.html.action;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;

import javax.swing.KeyStroke;

import org.columba.api.gui.frame.IFrameMediator;
import org.columba.mail.gui.composer.ComposerController;
import org.columba.mail.gui.composer.html.HtmlEditorController2;
import org.columba.mail.resourceloader.MailImageLoader;
import org.columba.mail.util.MailResourceLoader;
import org.frapuccino.htmleditor.event.FormatChangedEvent;

/**
 * Format selected text as bold "&lt;b&gt;"
 *
 * @author fdietz
 */
public class BoldFormatAction extends AbstractComposerAction {

	/** JDK 1.4+ logging framework logger, used for logging. */
	private static final Logger LOG = Logger
			.getLogger("org.columba.mail.gui.composer.html.action");

	/**
	 * @param frameMediator
	 */
	public BoldFormatAction(IFrameMediator frameMediator) {
		super(frameMediator, MailResourceLoader.getString("menu", "composer",
				"menu_format_bold"));

		putValue(LARGE_ICON, MailImageLoader.getIcon("format-text-bold.png"));
		putValue(SMALL_ICON, MailImageLoader
				.getSmallIcon("format-text-bold.png"));

		putValue(SHORT_DESCRIPTION, MailResourceLoader.getString("menu",
				"composer", "menu_format_bold_tooltip").replaceAll("&", ""));

		// shortcut key
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_B, Toolkit
				.getDefaultToolkit().getMenuShortcutKeyMask()));

	}

	public void formatChanged(FormatChangedEvent event) {
		setState(event.getInfo().isBold());
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent evt) {
		// this action is disabled when the text/plain editor is used
		// -> so, its safe to just cast to HtmlEditorController here
		HtmlEditorController2 editorController = (HtmlEditorController2) ((ComposerController) frameMediator)
				.getCurrentEditor();

		editorController.toggleBold();
	}
}
