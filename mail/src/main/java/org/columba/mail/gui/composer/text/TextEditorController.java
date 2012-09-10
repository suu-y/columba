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
package org.columba.mail.gui.composer.text;

import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;
import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.columba.core.config.Config;
import org.columba.core.gui.base.HighlighterDocument;
import org.columba.core.xml.XmlElement;
import org.columba.mail.gui.composer.AbstractEditorController;
import org.columba.mail.gui.composer.ComposerController;


/**
 * Editor controller used when composing plain text mails.
 *
 * @author frd, Karl Peder Olesen (karlpeder)
 *
 */
public class TextEditorController extends AbstractEditorController
    {

    /** The editor view, i.e. the component used for editing text */
    private TextEditorView view;

    /** Document used in the editor view */
    private HighlighterDocument document;



    public TextEditorController(ComposerController controller) {
        super(controller);


        document = new HighlighterDocument();

        view = new TextEditorView(this, document);
        setView(view);

       //  view.addCaretListener(this);


    }


//    public void installListener() {
//        view.installListener(this);
//    }

//    public void updateComponents(boolean b) {
//        if (b) {
//            if (this.getController().getModel().getBodyText() != null) {
//                view.setText(controller.getModel().getBodyText());
//            } else {
//            	view.setText("");
//            }
//        } else {
//            if (view.getText() != null) {
//                this.getController().getModel().setBodyText(view.getText());
//            }
//        }
//    }


    @Override
    public void undo() {
        document.undo();
    }

    @Override
    public void redo() {
        document.redo();
    }

    /************* DocumentListener implementation *******************/
//    public void insertUpdate(DocumentEvent e) {
//    }
//
//    public void removeUpdate(DocumentEvent e) {
//    }
//
//    public void changedUpdate(DocumentEvent e) {
//    }



    /************************** CaretUpdateListener interface *****************/

    /* (non-Javadoc)
     * @see javax.swing.event.CaretListener#caretUpdate(javax.swing.event.CaretEvent)
     */
//    public void caretUpdate(CaretEvent arg0) {
//    	//FocusManager.getInstance().updateActions();
//    }



    /* (non-Javadoc)
     * @see org.columba.mail.gui.composer.AbstractEditorController#setViewText(java.lang.String)
     */
    public void setViewText(String text) {
        view.setText(text);
        view.revalidate();
    }






}
