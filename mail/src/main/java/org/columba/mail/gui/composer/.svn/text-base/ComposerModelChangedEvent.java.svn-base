package org.columba.mail.gui.composer;

import java.util.EventObject;

public class ComposerModelChangedEvent extends EventObject {

	private boolean htmlEnabled;

	public ComposerModelChangedEvent(ComposerModel model) {
		super(model);
	}

	public ComposerModelChangedEvent(ComposerModel model, boolean htmlEnabled) {
		this(model);

		this.htmlEnabled = htmlEnabled;
	}

	public ComposerModel getModel() {
		return (ComposerModel) getSource();
	}

	public boolean isHtmlEnabled() {
		return htmlEnabled;
	}

}
