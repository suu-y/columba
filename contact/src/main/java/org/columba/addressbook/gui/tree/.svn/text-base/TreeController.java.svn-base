// The contents of this file are subject to the Mozilla Public License Version
// 1.1
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
//The Initial Developers of the Original Code are Frederik Dietz and Timo
// Stich.
//Portions created by Frederik Dietz and Timo Stich are Copyright (C) 2003.
//
//All Rights Reserved.
package org.columba.addressbook.gui.tree;

import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.tree.TreePath;

import org.columba.addressbook.folder.AbstractFolder;
import org.columba.addressbook.folder.GroupFolder;
import org.columba.addressbook.gui.dialog.group.EditGroupDialog;
import org.columba.addressbook.gui.focus.FocusManager;
import org.columba.addressbook.gui.focus.FocusOwner;
import org.columba.addressbook.gui.frame.AddressbookFrameController;
import org.columba.addressbook.model.IGroupModel;
import org.columba.core.gui.base.DoubleClickListener;

/**
 * 
 * 
 * @author fdietz
 */
public class TreeController extends DoubleClickListener implements FocusOwner {

	TreeView view;

	AddressbookFrameController frameController;

	/**
	 *  
	 */
	public TreeController(AddressbookFrameController frameController) {
		super();
		this.frameController = frameController;

		view = new TreeView(frameController);

		view.addMouseListener(this);

//		 register as focus owner
		FocusManager.getInstance().registerComponent(this);
	}

	/**
	 * @return AddressbookTreeView
	 */
	public TreeView getView() {
		return view;
	}

	/**
	 * @return AddressbookFrameController
	 */
	public AddressbookFrameController getFrameController() {
		return frameController;
	}

	public AbstractFolder getSelectedFolder() {
		return (AbstractFolder) getView().getLastSelectedPathComponent();
	}
	
	public void setSelectedFolder(AbstractFolder folder) {
		getView().clearSelection();
		
		TreePath path = new TreePath(folder.getPath());
		
		getView().setSelectionPath(path);
	}

	/** ************* FocusOwner Implementation ****************** */

	/**
	 * @see org.columba.addressbook.gui.focus.FocusOwner#copy()
	 */
	public void copy() {

	}

	/**
	 * @see org.columba.addressbook.gui.focus.FocusOwner#cut()
	 */
	public void cut() {

	}

	/**
	 * @see org.columba.addressbook.gui.focus.FocusOwner#delete()
	 */
	public void delete() {

	}

	/**
	 * @see org.columba.addressbook.gui.focus.FocusOwner#getComponent()
	 */
	public JComponent getComponent() {
		return getView();
	}

	/**
	 * @see org.columba.addressbook.gui.focus.FocusOwner#isCopyActionEnabled()
	 */
	public boolean isCopyActionEnabled() {

		return false;
	}

	/**
	 * @see org.columba.addressbook.gui.focus.FocusOwner#isCutActionEnabled()
	 */
	public boolean isCutActionEnabled() {

		return false;
	}

	/**
	 * @see org.columba.addressbook.gui.focus.FocusOwner#isDeleteActionEnabled()
	 */
	public boolean isDeleteActionEnabled() {

		return false;
	}

	/**
	 * @see org.columba.addressbook.gui.focus.FocusOwner#isPasteActionEnabled()
	 */
	public boolean isPasteActionEnabled() {

		return false;
	}

	/**
	 * @see org.columba.addressbook.gui.focus.FocusOwner#isRedoActionEnabled()
	 */
	public boolean isRedoActionEnabled() {

		return false;
	}

	/**
	 * @see org.columba.addressbook.gui.focus.FocusOwner#isSelectAllActionEnabled()
	 */
	public boolean isSelectAllActionEnabled() {

		return false;
	}

	/**
	 * @see org.columba.addressbook.gui.focus.FocusOwner#isUndoActionEnabled()
	 */
	public boolean isUndoActionEnabled() {

		return false;
	}

	/**
	 * @see org.columba.addressbook.gui.focus.FocusOwner#paste()
	 */
	public void paste() {

	}

	/**
	 * @see org.columba.addressbook.gui.focus.FocusOwner#redo()
	 */
	public void redo() {

	}

	/**
	 * @see org.columba.addressbook.gui.focus.FocusOwner#selectAll()
	 */
	public void selectAll() {

	}

	/**
	 * @see org.columba.addressbook.gui.focus.FocusOwner#undo()
	 */
	public void undo() {

	}


	public void doubleClick(MouseEvent e) {
		if (e.getButton()==MouseEvent.BUTTON1 && e.getClickCount() > 1) {
			AbstractFolder f = getSelectedFolder();
			if (!(f instanceof GroupFolder) )
				return;

			GroupFolder folder = (GroupFolder)f;

			if (folder == null)
				return;

			IGroupModel group = folder.getGroup();

			EditGroupDialog dialog = new EditGroupDialog(
					frameController.getView().getFrame(),
					group, (AbstractFolder) folder.getParent());

			if (dialog.getResult()) {
				folder.modelChanged();
			}
		}
	}
}