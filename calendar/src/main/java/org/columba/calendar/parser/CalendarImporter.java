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
package org.columba.calendar.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.WeekDay;
import net.fortuna.ical4j.model.property.Categories;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStamp;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.RRule;

import org.columba.calendar.base.UUIDGenerator;
import org.columba.calendar.model.Event;
import org.columba.calendar.model.EventInfo;
import org.columba.calendar.model.Recurrence;
import org.columba.calendar.model.api.IEvent;
import org.columba.calendar.model.api.IEventInfo;
import org.columba.calendar.model.api.IRecurrence;
import org.columba.calendar.model.api.IWeekDay;

public class CalendarImporter {

	public CalendarImporter() {
		super();
	}

	public Iterator<IEventInfo> importCalendar(String calendarId, File file) throws Exception {
		FileInputStream in = new FileInputStream(file);

		return importCalendar(calendarId, in);
	}

	public Iterator<IEventInfo> importCalendar(String calendarId, InputStream in) throws Exception {
		Vector<IEventInfo> v = new Vector<IEventInfo>();

		CalendarBuilder builder = new CalendarBuilder();

		net.fortuna.ical4j.model.Calendar calendar = builder.build(in);
		
		Map<String, Integer> frequency = new HashMap<String, Integer>();
		frequency.put("YEARLY", new Integer(IRecurrence.RECURRENCE_ANNUALLY));
		frequency.put("MONTHLY", new Integer(IRecurrence.RECURRENCE_MONTHLY));
		frequency.put("WEEKLY", new Integer(IRecurrence.RECURRENCE_WEEKLY));
		frequency.put("DAILY", new Integer(IRecurrence.RECURRENCE_DAILY));

		for (Iterator i = calendar.getComponents().iterator(); i.hasNext();) {
			net.fortuna.ical4j.model.Component component = (net.fortuna.ical4j.model.Component) i
					.next();

			// only import VEVENT and VTODO for now
			if (component.getName().equals(Component.VEVENT)) {

				Calendar dtStart = null;
				Calendar dtEnd = null;
				Calendar dtStamp = null;
				String summary = null;
				String location = null;
				String description = null;
				String uid = null;
				URL url = null;
				String eventClass = null;
				String priority = null;
				String status = null;
				
				List<String> categories = new LinkedList<String>();
				
				int freq = -1;
				int count = -1;
				int interval = 0;
				Calendar until = null;
				List<WeekDay> weekdays = null;
				
				for (Iterator j = component.getProperties().iterator(); j
						.hasNext();) {
					Property property = (Property) j.next();
					String name = property.getName();
					String value = property.getValue();

					if (name.equals(Property.DTSTART)) {
						DtStart dtStart1 = (DtStart) property;
						if (dtStart1.getDate() instanceof DateTime) {
							DateTime dateTime = (DateTime) dtStart1.getDate();
							// ensure tzid matches date-time timezone..
							// Parameter tzId =
							// dtStart1.getParameters().getParameter(
							// Parameter.TZID);
	
							dtStart = Calendar.getInstance();
							dtStart.setTimeInMillis(dateTime.getTime());
							if (dateTime.getTimeZone() != null)
								dtStart.setTimeZone(dateTime.getTimeZone());
						} else {
							Date dateTime = dtStart1.getDate();
							// ensure tzid matches date-time timezone..
							// Parameter tzId =
							// dtStart1.getParameters().getParameter(
							// Parameter.TZID);
	
							dtStart = Calendar.getInstance();
							dtStart.setTimeInMillis(dateTime.getTime());
						}

					} else if (name.equals(Property.DTEND)) {
						DtEnd dtEnd1 = (DtEnd) property;
						if (dtEnd1.getDate() instanceof DateTime) {
							DateTime dateTime = (DateTime) dtEnd1.getDate();
							// ensure tzid matches date-time timezone..
							// Parameter tzId = dtEnd1.getParameters().getParameter(
							// Parameter.TZID);
	
							dtEnd = Calendar.getInstance();
							dtEnd.setTimeInMillis(dateTime.getTime());
							if (dateTime.getTimeZone() != null)
								dtEnd.setTimeZone(dateTime.getTimeZone());
						} else {
							Date dateTime = dtEnd1.getDate();
							// ensure tzid matches date-time timezone..
							// Parameter tzId = dtEnd1.getParameters().getParameter(
							// Parameter.TZID);
	
							dtEnd = Calendar.getInstance();
							dtEnd.setTimeInMillis(dateTime.getTime());
						}
					} else if (name.equals(Property.SUMMARY)) {
						summary = value;
					} else if (name.equals(Property.LOCATION)) {
						location = value;
					} else if (name.equals(Property.DTSTAMP)) {
						DtStamp dtStamp1 = (DtStamp) property;
						if (dtStamp1.getDate() instanceof DateTime) {
							DateTime dateTime = (DateTime) dtStamp1.getDate();
							// ensure tzid matches date-time timezone..
							// Parameter tzId =
							// dtStamp1.getParameters().getParameter(
							// Parameter.TZID);
	
							dtStamp = Calendar.getInstance();
	
							dtStamp.setTimeInMillis(dateTime.getTime());
							if (dateTime.getTimeZone() != null)
								dtStamp.setTimeZone(dateTime.getTimeZone());
						} else {
							Date dateTime = dtStamp1.getDate();
							// ensure tzid matches date-time timezone..
							// Parameter tzId =
							// dtStamp1.getParameters().getParameter(
							// Parameter.TZID);

							dtStamp = Calendar.getInstance();

							dtStamp.setTimeInMillis(dateTime.getTime());
						}
					} else if (name.equals(Property.UID)) {
						// remove everything which is not A-Za-z0-9-_
						uid = correctUid(value);
					} else if (name.equals(Property.URL)) {
						url = new URL(value);
					} else if (name.equals(Property.DESCRIPTION)) {
						description = value;
					} else if (name.equals(Property.CATEGORIES)) {
						Categories categorylist = (Categories) property;
						for (Iterator iter = categorylist.getCategories().iterator(); iter.hasNext(); ) {
							String c = (String) iter.next();
							categories.add(c);
						}						
					} else if (name.equals(Property.CLASS)) {
						eventClass = value;
					} else if (name.equals(Property.PRIORITY)) {
						priority = value;
					} else if (name.equals(Property.STATUS)) {
						status = value;
					} else if (name.equals(Property.RRULE)) {
						// RRULE:FREQ=YEARLY;COUNT=5;INTERVAL=1
						// RRULE:FREQ=WEEKLY;UNTIL=20060725T215959;INTERVAL=1;BYDAY=TU
						// RRULE:FREQ=YEARLY;INTERVAL=1

						// RRULE:FREQ=WEEKLY;COUNT=8;INTERVAL=2;BYDAY=TU,TH
						// RRULE:FREQ=MONTHLY;UNTIL=20070627T215959;INTERVAL=2;BYDAY=4WE
						// RRULE:FREQ=YEARLY;INTERVAL=1
						// RRULE:FREQ=YEARLY;INTERVAL=1;BYDAY=-1SU;BYMONTH=3
						// RRULE:FREQ=YEARLY;INTERVAL=1;BYDAY=-1SU;BYMONTH=10
						
						RRule rrule = (RRule) property;
						freq = ((Integer) frequency.get(rrule.getRecur().getFrequency())).intValue();
						if (rrule.getRecur().getCount() > 0)
							count = rrule.getRecur().getCount();
						if (rrule.getRecur().getInterval() > 0)
							interval = rrule.getRecur().getInterval();
						else
							interval = 1;
						weekdays = rrule.getRecur().getDayList();
						if (rrule.getRecur().getUntil() != null) {
							until = Calendar.getInstance();
							until.setTime(rrule.getRecur().getUntil());
						}
					}

				}

				// skip, if UID, dtStart or dtEnd is not defined
				if (uid == null || dtStart == null || dtEnd == null)
					continue;
				
				System.out.println("SATD ID: 14");
				// an all day event
				boolean allDayEvent = ParserHelper.isAllDayEvent(dtStart, dtEnd);
				
				IEvent event = new Event(uid);

				event.setDtStart(dtStart);
				event.setDtEnd(dtEnd);
				event.setDtStamp(dtStamp);
				event.setAllDayEvent(allDayEvent);

				if (summary != null)
					event.setSummary(summary);
				if (eventClass != null)
					event.setEventClass(eventClass);
				if (location != null)
					event.setLocation(location);
				if (priority != null)
					event.setPriority(priority);
				if (status != null)
					event.setStatus(status);
				if (url != null)
					event.setUrl(url);
				if (description != null)
					event.setDescription(description);
				
				if (freq > -1) {
					// create recurrence
					Recurrence r = new Recurrence(freq);
					
					r.setInterval(interval);
					
					List<IWeekDay> wdlist = new LinkedList<IWeekDay>();
					if (weekdays != null) {
						for (Iterator iter = weekdays.iterator(); iter.hasNext(); ) {
							WeekDay owd = (WeekDay) iter.next();
							org.columba.calendar.model.WeekDay wd = new org.columba.calendar.model.WeekDay(owd.getDay(), owd.getOffset());
							wdlist.add(wd);
						}
						
						r.setWeekDays(wdlist);
					}

					r.setEndType(IRecurrence.RECURRENCE_END_FOREVER);
					if (until != null) {
						r.setEndDate(until);
						r.setEndType(IRecurrence.RECURRENCE_END_ENDDATE);
					} else if (count > -1) {
						r.setEndMaxOccurrences(count);
						r.setEndType(IRecurrence.RECURRENCE_END_MAXOCCURRENCES);
					}
					
					event.setRecurrence(r);
					
				} else {
					event.setRecurrence(null);
				}
				
				for (String category : categories) 
					event.addCategory(category);
				
				IEventInfo eventInfo = new EventInfo(uid, calendarId, event);

				v.add(eventInfo);
			} else if (component.getName().equals(Component.VTIMEZONE)) {
				for (Iterator j = component.getProperties().iterator(); j
						.hasNext();) {
					Property property = (Property) j.next();
				}
			} else if (component.getName().equals(Component.VTODO)) {
				
				for (Iterator j = component.getProperties().iterator(); j
						.hasNext();) {
					Property property = (Property) j.next();
					String name = property.getName();
					String value = property.getValue();
				}

			}
		}

		in.close();

		return v.iterator();
	}
	
	public static String correctUid(String value) {
		// if there is no string given, return a number
		if (value == null || value.length() == 0)
			return (new UUIDGenerator()).newUUID();
		return value.replaceAll("[^a-z,0-9,A-Z,_-]", "-");
	}

}
