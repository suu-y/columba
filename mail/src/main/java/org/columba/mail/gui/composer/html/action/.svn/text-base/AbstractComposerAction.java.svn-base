package org.columba.mail.gui.composer.html.action;

import java.awt.event.ActionEvent;

import org.columba.api.gui.frame.IFrameMediator;
import org.columba.core.gui.action.AbstractSelectableAction;
import org.columba.mail.gui.composer.ComposerController;
import org.columba.mail.gui.composer.ComposerModelChangedEvent;
import org.columba.mail.gui.composer.IComposerModelChangedListener;
import org.columba.mail.gui.composer.html.HtmlEditorController2;
import org.frapuccino.htmleditor.api.IFormatChangedListener;
import org.frapuccino.htmleditor.event.FormatChangedEvent;

public abstract class AbstractComposerAction extends AbstractSelectableAction
		implements IFormatChangedListener, IComposerModelChangedListener {

	public AbstractComposerAction(IFrameMediator frameMediator, String name) {
		super(frameMediator, name);

		ComposerController ctrl = (ComposerController) getFrameMediator();

		// register for text cursor/caret and formatting changes
		// to select/deselect action
		HtmlEditorController2 c = (HtmlEditorController2) ctrl
				.getHtmlEditorController();
		c.addFormatChangedListener(this);

		// register for model changes to enable/disable when
		// user switches between html or text plain mode
		ctrl.getModel().addModelChangedListener(this);

	}

	public void modelChanged(ComposerModelChangedEvent event) {
	}

	public void htmlModeChanged(ComposerModelChangedEvent event) {
		setEnabled(event.isHtmlEnabled());
	}

	public abstract void formatChanged(FormatChangedEvent event);

	public abstract void actionPerformed(ActionEvent e);
}
