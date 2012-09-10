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

import java.awt.event.ActionEvent;

import org.columba.api.gui.frame.IFrameMediator;
import org.columba.core.gui.action.AbstractColumbaAction;
import org.columba.mail.gui.composer.ComposerController;
import org.columba.mail.gui.composer.ComposerModelChangedEvent;
import org.columba.mail.gui.composer.IComposerModelChangedListener;
import org.columba.mail.gui.composer.html.HtmlEditorController2;
import org.columba.mail.util.MailResourceLoader;
import org.frapuccino.htmleditor.api.IFormatChangedListener;
import org.frapuccino.htmleditor.event.FormatChangedEvent;

/**
 * Inserts the html element &lt;br&gt (br tag), i.e. a line break.
 *
 * @author Karl Peder Olesen (karlpeder), 20030923
 */
public class InsertBreakAction extends AbstractColumbaAction implements
		IFormatChangedListener, IComposerModelChangedListener {
	/**
	 * @param frameMediator
	 */
	public InsertBreakAction(IFrameMediator frameMediator) {
		super(frameMediator, MailResourceLoader.getString("menu", "composer",
				"menu_format_break"));
		putValue(SHORT_DESCRIPTION, MailResourceLoader.getString("menu",
				"composer", "menu_format_break_tooltip").replaceAll("&", ""));

		ComposerController ctrl = (ComposerController) getFrameMediator();

		// register for text cursor/caret and formatting changes
		// to select/deselect action
		HtmlEditorController2 c = (HtmlEditorController2) ctrl
				.getHtmlEditorController();
		c.addFormatChangedListener(this);
	}

	public void formatChanged(FormatChangedEvent event) {
	}

	public void modelChanged(ComposerModelChangedEvent event) {
	}

	public void htmlModeChanged(ComposerModelChangedEvent event) {
		setEnabled(event.isHtmlEnabled());
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent evt) {
		// this action is disabled when the text/plain editor is used
		// -> so, its safe to just cast to HtmlEditorController here
		HtmlEditorController2 editorController = (HtmlEditorController2) ((ComposerController) frameMediator)
				.getCurrentEditor();

		editorController.insertBreak();
	}

}
