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
package org.columba.calendar.model;

import org.columba.calendar.base.UUIDGenerator;
import org.columba.calendar.model.api.IEvent;
import org.columba.calendar.model.api.IEventInfo;
import org.columba.calendar.model.api.IComponent.TYPE;

public class EventInfo extends ComponentInfo implements IEventInfo {
	
	private IEvent event;
	
	public EventInfo(String id, String calendarId, IEvent event) {
		super(id, TYPE.EVENT, calendarId, event);
		this.event = event;
	}
	
	public IEvent getEvent() {
		return event;
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		
		// create new event with new UUID
		String newId = new UUIDGenerator().newUUID();
		
		Event newEvent = new Event(newId);
		// copy all attributes
		newEvent.setDtStart(getEvent().getDtStart());
		newEvent.setDtEnd(getEvent().getDtEnd());
		newEvent.setDtStamp(getEvent().getDtStamp());
		newEvent.setSummary(getEvent().getSummary());
		newEvent.setLocation(getEvent().getLocation());
		newEvent.setCalendar(getCalendar());

		EventInfo eventInfo = new EventInfo(newId, getCalendar(), newEvent);

		return eventInfo;
	}
	
	/**
	 * @see org.columba.calendar.model.api.IEvent#createCopy()
	 */
	public IEventInfo createCopy() {
		try {
			return (IEventInfo) clone();
		} catch (CloneNotSupportedException e) {
		}
		return null;
	}

}
