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
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.text.html.HTML;

import org.columba.api.gui.frame.IFrameMediator;
import org.columba.core.gui.menu.IMenu;
import org.columba.mail.gui.composer.ComposerController;
import org.columba.mail.gui.composer.ComposerModelChangedEvent;
import org.columba.mail.gui.composer.IComposerModelChangedListener;
import org.columba.mail.gui.composer.html.HtmlEditorController2;
import org.columba.mail.util.MailResourceLoader;
import org.frapuccino.htmleditor.api.IFormatChangedListener;
import org.frapuccino.htmleditor.event.FormatChangedEvent;
import org.frapuccino.htmleditor.event.FormatInfo;

/**
 * Submenu for formatting text.
 * <p>
 * Possible values are: - normal - preformatted - heading 1 - heading 2 -
 * heading 3 - address
 *
 * Note: This is the place to add further formats like lists, etc.
 *
 * Note: The HtmlEditorView and -Controller must of course also support new
 * formats when adding them!
 *
 * @author fdietz, Karl Peder Olesen (karlpeder)
 */
public class ParagraphMenu extends IMenu implements ActionListener,
		IFormatChangedListener, IComposerModelChangedListener {

	/** Html tags corresponding to supported paragraph styles */
	public static final HTML.Tag[] STYLE_TAGS = { HTML.Tag.P, HTML.Tag.PRE,
			HTML.Tag.H1, HTML.Tag.H2, HTML.Tag.H3, HTML.Tag.ADDRESS };

	protected ButtonGroup group;

	/**
	 * @param controller
	 * @param caption
	 */
	public ParagraphMenu(IFrameMediator controller) {
		super(controller, MailResourceLoader.getString("menu", "composer",
				"menu_format_paragraph"), "menu_format_paragraph");

		initMenu();
	}

	public void modelChanged(ComposerModelChangedEvent event) {
	}

	public void htmlModeChanged(ComposerModelChangedEvent event) {
		setEnabled(event.isHtmlEnabled());
	}

	public void formatChanged(FormatChangedEvent event) {
		// select the menu item corresponding to present format
		FormatInfo info = event.getInfo();

		if (info.isHeading1()) {
			selectMenuItem(HTML.Tag.H1);
		} else if (info.isHeading2()) {
			selectMenuItem(HTML.Tag.H2);
		} else if (info.isHeading3()) {
			selectMenuItem(HTML.Tag.H3);
		} else if (info.isPreformattet()) {
			selectMenuItem(HTML.Tag.PRE);
		} else if (info.isAddress()) {
			selectMenuItem(HTML.Tag.ADDRESS);
		} else {
			// select the "Normal" entry as default
			selectMenuItem(HTML.Tag.P);
		}
	}

	/**
	 * Initializes the sub menu by creating a menu item for each available
	 * paragraph style. All menu items are grouped in a ButtonGroup (as radio
	 * buttons).
	 */
	protected void initMenu() {
		group = new ButtonGroup();

		for (int i = 0; i < STYLE_TAGS.length; i++) {
			JRadioButtonMenuItem m = new ParagraphFormatMenuItem(STYLE_TAGS[i]);
			m.addActionListener(this);
			add(m);

			group.add(m);
		}
	}

	/**
	 * Private utility to select a given sub menu, given the corresponding html
	 * tag. If such a sub menu does not exist - nothing happens
	 */
	private void selectMenuItem(HTML.Tag tag) {
		Enumeration e = group.getElements();
		while (e.hasMoreElements()) {
			ParagraphFormatMenuItem item = (ParagraphFormatMenuItem) e
					.nextElement();

			if (item.getAssociatedTag().equals(tag)) {
				item.setSelected(true);
			} else
				item.setSelected(false);
		}
	}

	public void actionPerformed(ActionEvent e) {
		HtmlEditorController2 ctrl = (HtmlEditorController2) ((ComposerController) controller)
				.getCurrentEditor();

		// set paragraph formatting according to the given action
		ParagraphFormatMenuItem source = (ParagraphFormatMenuItem) e
				.getSource();
		ctrl.setParagraphFormat(source.getAssociatedTag());
	}

	/**
	 * A specialized radio button menu item class used to render paragraph
	 * format actions.
	 */
	protected static class ParagraphFormatMenuItem extends JRadioButtonMenuItem {
		protected HTML.Tag tag;

		public ParagraphFormatMenuItem(HTML.Tag tag) {
			super(MailResourceLoader.getString("menu", "composer",
					"menu_format_paragraph_" + tag.toString()));
			this.tag = tag;
		}

		public HTML.Tag getAssociatedTag() {
			return tag;
		}
	}
}
