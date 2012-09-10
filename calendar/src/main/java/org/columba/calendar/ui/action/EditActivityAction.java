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
package org.columba.calendar.ui.action;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import org.columba.api.gui.frame.IFrameMediator;
import org.columba.calendar.base.api.IActivity;
import org.columba.calendar.config.CalendarList;
import org.columba.calendar.model.api.IEventInfo;
import org.columba.calendar.store.api.ICalendarStore;
import org.columba.calendar.store.api.StoreException;
import org.columba.calendar.ui.calendar.api.ActivitySelectionChangedEvent;
import org.columba.calendar.ui.calendar.api.IActivitySelectionChangedListener;
import org.columba.calendar.ui.calendar.api.ICalendarView;
import org.columba.calendar.ui.dialog.EditEventDialog;
import org.columba.calendar.ui.frame.api.ICalendarMediator;
import org.columba.core.gui.action.AbstractColumbaAction;
import org.columba.core.gui.frame.FrameManager;

/**
 * Edit activity.
 *
 * @author fdietz
 *
 */

public class EditActivityAction extends AbstractColumbaAction implements
		IActivitySelectionChangedListener {

	public EditActivityAction(IFrameMediator frameMediator) {
		super(frameMediator, "Edit Activity");

		putValue(AbstractColumbaAction.TOOLBAR_NAME, "Edit");
		setShowToolBarText(true);

		// putValue(AbstractColumbaAction.LARGE_ICON, ResourceLoader
		// .getImageIcon("new_appointment-32.png"));
		// putValue(AbstractColumbaAction.SMALL_ICON, ResourceLoader
		// .getImageIcon("new_appointment.png"));
		setEnabled(false);

		ICalendarMediator m = (ICalendarMediator) getFrameMediator();
		m.getCalendarView().addSelectionChangedListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		ICalendarMediator m = (ICalendarMediator) getFrameMediator();

		ICalendarView c = m.getCalendarView();

		IActivity activity = c.getSelectedActivity();

		String id = (String) activity.getId();

		ICalendarStore oldStore = activity.getStore();

		// retrieve event from store
		try {
			IEventInfo model = (IEventInfo) oldStore.get(id);

			EditEventDialog dialog = new EditEventDialog(m.getContainer()
					.getFrame(), model, oldStore.isReadOnly(id));
			if (dialog.success() && !oldStore.isReadOnly(id)) {
				IEventInfo updatedModel = dialog.getModel();
				
				ICalendarStore newStore = CalendarList.getInstance().get(updatedModel
						.getCalendar()).getStore();
				// update
				if (newStore == oldStore) {
					newStore.modify(id, updatedModel);
				} else {
					newStore.add(updatedModel);
					oldStore.remove(model.getId());
				}
			}

		} catch (StoreException e1) {
			JOptionPane.showMessageDialog(FrameManager.getInstance()
					.getActiveFrame(), e1.getMessage());
			e1.printStackTrace();
		}

	}

	public void selectionChanged(ActivitySelectionChangedEvent event) {
		if (event.getSelection().length == 0)
			setEnabled(false);
		else {
			setEnabled(true);
		}
	}

}
