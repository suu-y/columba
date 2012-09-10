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
package org.columba.calendar.ui.base;

import org.columba.calendar.model.DateRange;
import org.columba.calendar.model.Recurrence;
import org.columba.calendar.model.api.IComponentInfo;
import org.columba.calendar.model.api.IEvent;
import org.columba.calendar.model.api.IEventInfo;
import org.columba.calendar.model.api.IRecurrence;
import org.columba.calendar.store.api.ICalendarStore;

import com.miginfocom.calendar.activity.Activity;
import com.miginfocom.calendar.activity.DefaultActivity;
import com.miginfocom.calendar.activity.recurrence.RecurrenceRule;
import com.miginfocom.util.dates.DateRangeI;
import com.miginfocom.util.dates.ImmutableDateRange;

public class CalendarHelper {

	public static String getActivityId(IComponentInfo model, ICalendarStore store) {
		return store.getId() + ':' + model.getId();
	}

	public static Activity createActivity(IEventInfo model, ICalendarStore store) {

		long startMillis = model.getEvent().getDtStart().getTimeInMillis();
		long endMillis = model.getEvent().getDtEnd().getTimeInMillis();
		
		ImmutableDateRange dr = new ImmutableDateRange(startMillis, endMillis,
				false, null, null);

		// A recurring event
		Activity act = new DefaultActivity(dr, getActivityId(model, store));
		act.setSummary(model.getEvent().getSummary());
		act.setLocation(model.getEvent().getLocation());
		act.setDescription(model.getEvent().getDescription());
		IRecurrence columbaRecurrence = model.getEvent().getRecurrence();
		if (columbaRecurrence != null && columbaRecurrence.getType() != IRecurrence.RECURRENCE_NONE) {
			RecurrenceRule r = new RecurrenceRule();
			r.setFrequency(Recurrence.toFrequency(columbaRecurrence.getType()));
			r.setInterval(columbaRecurrence.getInterval());
			if (columbaRecurrence.getEndType() == IRecurrence.RECURRENCE_END_MAXOCCURRENCES)
				r.setRepetitionCount(columbaRecurrence.getEndMaxOccurrences());
			if (columbaRecurrence.getEndType() == IRecurrence.RECURRENCE_END_ENDDATE)
				r.setUntilDate(columbaRecurrence.getEndDate());
			act.setRecurrence(r);
		}

		String calendar = model.getCalendar();
		// this is for the calendar component and only used internally
		act.setCategoryIDs(new Object[] { calendar });
		
		return act;
	}

	public static void updateDateRange(final Activity activity, IEvent model) {
		DateRangeI dateRange = activity.getDateRangeForReading();
		DateRange cRange = new DateRange(dateRange.getStartMillis(), dateRange
				.getEndMillis(false));

		model.setDtStart(cRange.getStartTime());
		model.setDtEnd(cRange.getEndTime());

	}

}
