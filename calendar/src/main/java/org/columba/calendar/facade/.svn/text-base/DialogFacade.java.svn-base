package org.columba.calendar.facade;

import java.net.URI;
import java.util.Iterator;

import javax.swing.JOptionPane;

import org.columba.calendar.config.CalendarList;
import org.columba.calendar.model.api.IEventInfo;
import org.columba.calendar.store.api.ICalendarStore;
import org.columba.calendar.store.api.StoreException;
import org.columba.calendar.ui.dialog.EditEventDialog;
import org.columba.core.gui.frame.FrameManager;

public class DialogFacade {

	public void openEventEditorDialog(URI location) {
		String s = location.toString();
		// TODO: @author fdietz replace with regular expression
		int activityIndex = s.lastIndexOf('/');
		String activityId = s.substring(activityIndex + 1, s.length());
		int folderIndex = s.lastIndexOf('/', activityIndex - 1);
		String folderId = s.substring(folderIndex + 1, activityIndex);
		int componentIndex = s.lastIndexOf('/', folderIndex - 1);
		String componentId = s.substring(componentIndex + 1,
				folderIndex);

		ICalendarStore store = null;
		Iterator<ICalendarStore> it = CalendarList.getInstance().getStores();
		while (it.hasNext()) {
			ICalendarStore st = it.next();
			if (st.exists(activityId)) {
				store = st;
				break;
			}
		}
		if (store == null)
			return;

		// retrieve event from store
		try {
			IEventInfo model = (IEventInfo) store.get(activityId);

			EditEventDialog dialog = new EditEventDialog(null, model, store.isReadOnly(activityId));
			if (dialog.success()) {
				IEventInfo updatedModel = dialog.getModel();

				// update store
				store.modify(activityId, updatedModel);
			}

		} catch (StoreException e1) {
			JOptionPane.showMessageDialog(FrameManager.getInstance()
					.getActiveFrame(), e1.getMessage());
			e1.printStackTrace();
		}
	}
}
