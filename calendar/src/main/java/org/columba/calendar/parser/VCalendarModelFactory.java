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

import org.columba.calendar.model.Event;
import org.columba.calendar.model.EventInfo;
import org.columba.calendar.model.Recurrence;
import org.columba.calendar.model.api.ICALENDAR;
import org.columba.calendar.model.api.IComponentInfo;
import org.columba.calendar.model.api.IEvent;
import org.columba.calendar.model.api.IEventInfo;
import org.columba.calendar.model.api.IRecurrence;
import org.columba.calendar.model.api.IComponent.TYPE;
import org.jdom.Document;

import com.miginfocom.calendar.activity.recurrence.RecurrenceRule;

public final class VCalendarModelFactory {

	public VCalendarModelFactory() {
		super();
	}

	public static Document marshall(IComponentInfo c) throws SyntaxException,
			IllegalArgumentException {

		if (c == null)
			throw new IllegalArgumentException("calendarmodel == null");

		XCSDocumentParser model = new XCSDocumentParser(c.getType());

		model.setId(c.getId());

		if (c.getType() == TYPE.EVENT) {
			IEventInfo eventInfo = (IEventInfo) c;
			model.setDescription(eventInfo.getEvent().getDescription());
			model.setSummary(eventInfo.getEvent().getSummary());

			if (eventInfo.getEvent().getDtStart() != null)
				model.setDTStart(eventInfo.getEvent().getDtStart());

			if (eventInfo.getEvent().getDtEnd() != null)
				model.setDTEnd(eventInfo.getEvent().getDtEnd());

			if (eventInfo.getEvent().getDtStamp() != null)
				model.setDTStamp(eventInfo.getEvent().getDtStamp());

			model.setEventClass(eventInfo.getEvent().getEventClass());
			model.setLocation(eventInfo.getEvent().getLocation());
			
			if (eventInfo.getEvent().getRecurrence() != null && eventInfo.getEvent().getRecurrence().getType() != IRecurrence.RECURRENCE_NONE) {
				RecurrenceRule r = new RecurrenceRule();
				IRecurrence columbaRecurrence = eventInfo.getEvent().getRecurrence();
				r.setFrequency(Recurrence.toFrequency(columbaRecurrence.getType()));
				r.setInterval(columbaRecurrence.getInterval());
				if (columbaRecurrence.getEndType() == IRecurrence.RECURRENCE_END_MAXOCCURRENCES)
					r.setRepetitionCount(columbaRecurrence.getEndMaxOccurrences());
				else if (columbaRecurrence.getEndType() == IRecurrence.RECURRENCE_END_ENDDATE)
					r.setUntilDate(columbaRecurrence.getEndDate());
				System.out.println("SATD ID: 172");
				model.setRecurrenceRule(r);
			}
			
			model.setCategories(eventInfo.getEvent().getCategories());
			
			model.setCalendar(eventInfo.getCalendar());
		}

		System.out.println("SATD ID: 3");
		return model.getDocument();

	}

	public static IComponentInfo unmarshall(Document document)
			throws SyntaxException, IllegalArgumentException {

		if (document == null)
			throw new IllegalArgumentException("document == null");

		XCSDocumentParser model = new XCSDocumentParser(document);

		if (model.getId() == null)
			throw new IllegalArgumentException("id == null");

		if (model.getComponentType().equals(ICALENDAR.VEVENT)) {
			IEvent event = new Event((String) model.getId());

			event.setDescription(model.getDescription());
			event.setSummary(model.getSummary());

			if (model.getDTStart() != null)
				event.setDtStart(model.getDTStart());

			if (model.getDTEnd() != null)
				event.setDtEnd(model.getDTEnd());

			if (model.getDTStamp() != null)
				event.setDtStamp(model.getDTStamp());

			event.setEventClass(model.getEventClass());
			event.setLocation(model.getLocation());

			event.setAllDayEvent(ParserHelper.isAllDayEvent(model.getDTStart(), model.getDTEnd()));
			
			event.setCategories(model.getCategories());
			
			RecurrenceRule r = model.getRecurrenceRule();
			if (r != null) {
				// create recurrence
				Recurrence columbaRecurrence = new Recurrence(Recurrence.fromFrequency(r.getFrequency()));
				columbaRecurrence.setEndType(IRecurrence.RECURRENCE_END_FOREVER);
				columbaRecurrence.setInterval(r.getInterval());
				if (r.getRepetitionCount() != null) {
					columbaRecurrence.setEndType(IRecurrence.RECURRENCE_END_MAXOCCURRENCES);
					columbaRecurrence.setEndMaxOccurrences(r.getRepetitionCount());
				} else if (r.getUntilDate() != null) {
					columbaRecurrence.setEndType(IRecurrence.RECURRENCE_END_ENDDATE);
					columbaRecurrence.setEndDate(r.getUntilDate());
				}
				System.out.println("SATD ID: 182");
				event.setRecurrence(columbaRecurrence);
			}
			
			IEventInfo eventInfo = new EventInfo((String) model.getId(), model.getCalendar(), event);
			eventInfo.setCalendar(model.getCalendar());

			System.out.println("SATD ID: 94");

			return eventInfo;
		}

		else
			throw new IllegalArgumentException("unknown component type");

	}

	// public static VEventModel createVEvent(Document doc)
	// throws SyntaxException, InvocationException {
	// if (doc == null)
	// throw new InvocationException("doc == null");
	//
	// BasicDocumentModel model = new BasicDocumentModel(doc);
	//
	// VEventModel c = new VEventModel();
	// c.setId((String) model.getId());
	//
	// c.setDescription(model.getDescription());
	//
	// // TODO createVEvent
	// return null;
	// }
	//
	// public static Document persistVEvent(VEventModel vEventComponent)
	// throws SyntaxException, InvocationException {
	// // TODO persistVEvent
	// return null;
	// }
	//
	// public static VTodoModel createVTodo(Document doc) throws
	// SyntaxException,
	// InvocationException {
	// if (doc == null)
	// throw new InvocationException("doc == null");
	//
	// // TODO createVTodo
	// return null;
	// }
	//
	// public static Document persistVTodo(VTodoModel vTodoComponent)
	// throws SyntaxException, InvocationException {
	// // TODO persistVTodo
	// return null;
	// }
	//
	// public static VFreeBusyModel createVFreeBusy(Document doc)
	// throws SyntaxException, InvocationException {
	// if (doc == null)
	// throw new InvocationException("doc == null");
	//
	// // TODO createVFreeBusy
	// return null;
	// }
}
