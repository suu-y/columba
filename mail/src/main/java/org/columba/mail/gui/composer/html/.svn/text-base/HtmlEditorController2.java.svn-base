package org.columba.mail.gui.composer.html;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.logging.Logger;

import javax.swing.text.BadLocationException;
import javax.swing.text.ChangedCharSetException;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;

import org.columba.mail.gui.composer.AbstractEditorController;
import org.columba.mail.gui.composer.ComposerController;
import org.frapuccino.htmleditor.api.IFormatChangedListener;

public class HtmlEditorController2 extends AbstractEditorController implements
		org.frapuccino.htmleditor.api.IHtmlEditorController {

	/** JDK 1.4+ logging framework logger, used for logging. */
	private static final Logger LOG = Logger
			.getLogger("org.columba.mail.gui.composer.html");

	private org.frapuccino.htmleditor.api.IHtmlEditorController editor;

	// private JTextPane view;

	public HtmlEditorController2(ComposerController controller) {
		super(controller);

		editor = new org.frapuccino.htmleditor.HtmlEditorController();
		// view = editor.getView();
		setView(editor.getView());
	}

	/** ************* Methods for setting html specific formatting ************ */
	/**
	 * Toggle bold font in the view on/off
	 */
	public void toggleBold() {
		editor.toggleBold();
	}

	/**
	 * Toggle italic font in the view on/off
	 */
	public void toggleItalic() {
		editor.toggleItalic();
	}

	/**
	 * Toggle underline font in the view on/off
	 */
	public void toggleUnderline() {
		editor.toggleUnderline();
	}

	/**
	 * Toggle strikeout font in the view on/off
	 */
	public void toggleStrikeout() {
		editor.toggleStrikeout();
	}

	/**
	 * Toggle teletyper font (type written text) in the view on/off
	 */
	public void toggleTeleTyper() {
		editor.toggleTeleTyper();
	}

	public void setCenterJustify() {
		editor.setCenterJustify();
	}

	public void setLeftJustify() {
		editor.setLeftJustify();
	}

	public void setRightJustify() {
		editor.setRightJustify();
	}

	/**
	 * Sets paragraph format for selected paragraphs or current paragraph if no
	 * text is selected
	 *
	 * @param tag
	 *            Html tag specifying the format to set
	 */
	public void setParagraphFormat(HTML.Tag tag) {
		editor.setParagraphFormat(tag);
	}

	/**
	 * Method for inserting a break (BR) element
	 */
	public void insertBreak() {
		editor.insertBreak();
	}

	/**
	 * @see org.columba.mail.gui.composer.AbstractEditorController#setViewText(java.lang.String)
	 */
	public void setViewText(String text) {
		// // This doesn't handle ChangedCharsetExceptions correctly.
		// view.setText(text);
		try {
			loadHtmlIntoView(text, false);
		} catch (ChangedCharSetException ccse) {
			// try again, but ignore charset specification in the html
			try {
				loadHtmlIntoView(text, true);
			} catch (IOException e) {
				LOG.severe("Error setting view content, "
						+ "even after ignore charset spec: " + e.getMessage());
			}
		} catch (IOException e) {
			// other IOExceptions than ChangedCharsetException
			LOG.severe("Error setting view content: " + e.getMessage());
		}
	}

	/**
	 * Private utility for loading html into the view. Is called from
	 * setViewText. <br>
	 * The method works mostly as calling view.setText() directly, but is
	 * necessary to be able to handle ChangedCharsetExceptions
	 *
	 * @param text
	 *            Text to load into the view
	 * @param ignoreCharset
	 *            If set to true, charset specifications in the html will be
	 *            ignore
	 * @throws IOException
	 */
	private void loadHtmlIntoView(String text, boolean ignoreCharset)
			throws IOException {
		// clear existing text
		Document doc = getView().getDocument();

		try {
			// delete old contents
			doc.remove(0, doc.getLength());

			// if no text is specified, we are done now
			if ((text == null) || (text.equals(""))) {
				return;
			}

			// load contents into document
			if (ignoreCharset) {
				((HTMLDocument) getView().getDocument()).putProperty(
						"IgnoreCharsetDirective", Boolean.TRUE);
			}

			Reader r = new StringReader(text);
			EditorKit kit = getView().getEditorKit();
			kit.read(r, doc, 0); // this can throw a ChangedCharsetException
		} catch (BadLocationException e) {
			LOG.severe("Error deleting old view content: " + e.getMessage());

			return;
		}
	}

	public void addFormatChangedListener(IFormatChangedListener listener) {
		editor.addFormatChangedListener(listener);
	}

	public void removeFormatChangedListener(IFormatChangedListener listener) {
		editor.removeFormatChangedListener(listener);
	}

	public void setCharset(Charset charset) {
		editor.setCharset(charset);
	}

}
