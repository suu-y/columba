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

import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Logger;

import org.columba.calendar.base.UUIDGenerator;
import org.columba.calendar.model.Recurrence;
import org.columba.calendar.model.api.ICALENDAR;
import org.columba.calendar.model.api.IComponent.TYPE;
import org.jdom.Document;
import org.jdom.Element;

import com.miginfocom.calendar.activity.recurrence.ByXXXRuleData;
import com.miginfocom.calendar.activity.recurrence.RecurrenceRule;

public class XCSDocumentParser {

	/** JDK 1.4+ logging framework logger, used for logging. */
	private static final Logger LOG = Logger
			.getLogger("org.columba.calendar.store");

	private Document doc;

	private Element root;

	protected Element parentElement;

	protected Element vcalendarElement;

	protected Element componentElement;

	public XCSDocumentParser(TYPE type) {
		if (type == null)
			throw new IllegalArgumentException("type == null");

		doc = new Document();
		root = new Element(ICALENDAR.ICALENDAR);
		doc.addContent(root);

		// TODO fix doctype
		/*
		 * DocType docType = factory .docType("iCalendar", "-//IETF//DTD
		 * XCAL/iCalendar XML//EN",
		 * "http://www.ietf.org/internet-drafts/draft-hare-xcalendar-01.txt");
		 * doc.setDocType(docType);
		 */

		vcalendarElement = new Element(ICALENDAR.VCALENDAR);
		root.addContent(vcalendarElement);

		vcalendarElement.setAttribute(ICALENDAR.VCALENDAR_METHOD, "PUBLISH");
		vcalendarElement.setAttribute(ICALENDAR.VCALENDAR_VERSION, "2.0");
		vcalendarElement.setAttribute(ICALENDAR.VCALENDAR_PRODID,
				"-//Columba Project //NONSGML Columba v1.0//EN");

		if (type == TYPE.EVENT) {
			componentElement = new Element(ICALENDAR.VEVENT);
			vcalendarElement.addContent(componentElement);

		} else if (type == TYPE.TODO) {
			componentElement = new Element(ICALENDAR.VTODO);
			vcalendarElement.addContent(componentElement);
		} else
			throw new IllegalArgumentException("invalid component specified: "
					+ type);

		String uuid = new UUIDGenerator().newUUID();
		Element uuidElement = new Element(ICALENDAR.UID);
		uuidElement.setText(uuid);

		componentElement.addContent(uuidElement);

		parentElement = componentElement;
	}

	public XCSDocumentParser(Document document)
			throws IllegalArgumentException, SyntaxException {
		if (document == null)
			throw new IllegalArgumentException("document == null");

		this.doc = document;

		this.root = doc.getRootElement();

		if (!root.getName().equalsIgnoreCase("icalendar")) {
			// wrong xml-format
			throw new SyntaxException("Root element must be <icalendar>!");
		}

		vcalendarElement = root.getChild("vcalendar");
		if (vcalendarElement == null)
			throw new SyntaxException("element name <vcalendar> expected");

		componentElement = vcalendarElement.getChild(ICALENDAR.VEVENT);
		if (componentElement == null)
			componentElement = vcalendarElement.getChild(ICALENDAR.VTODO);

		if (componentElement == null)
			throw new SyntaxException(
					"wrong component type. Must be either <vevent> or <vtodo>.");

		parentElement = componentElement;
	}

	public String getComponentType() {
		return componentElement.getName();
	}

	/**
	 * @see org.columba.calendar.model.api.IBasicDocumentModel#getRootElement()
	 */
	public Element getRootElement() {
		return root;
	}

	/**
	 * @see org.columba.calendar.model.api.IBasicModel#set(java.lang.String,
	 *      java.lang.String)
	 */
	protected void set(String key, String value) {
		Element child = getParentElement().getChild(key);
		if (child == null) {
			child = new Element(key);
			getParentElement().addContent(child);
		}
		child.setText(value);
	}

	/**
	 * @see org.columba.calendar.model.api.IBasicModel#set(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	protected void set(String key, String prefix, String value) {
		Element child = getParentElement().getChild(key);
		if (child == null) {
			child = new Element(key);
			getParentElement().addContent(child);
		}
		Element prefixchild = child.getChild(prefix);
		if (prefixchild == null) {
			prefixchild = new Element(prefix);
			child.addContent(prefixchild);
		}
		prefixchild.setText(value);
	}

	/**
	 * @see org.columba.calendar.model.api.IBasicModel#get(java.lang.String)
	 */
	protected String get(String key) {
		Element child = getParentElement().getChild(key);
		if (child == null) {
			child = new Element(key);
			getParentElement().addContent(child);
		}
		return child.getTextNormalize();
	}

	/**
	 * @see org.columba.calendar.model.api.IBasicModel#get(java.lang.String,
	 *      java.lang.String)
	 */
	protected String get(String key, String prefix) {
		Element child = getParentElement().getChild(key);
		if (child == null) {
			child = new Element(key);
			getParentElement().addContent(child);
		}
		Element prefixchild = child.getChild(prefix);
		if (prefixchild == null) {
			prefixchild = new Element(prefix);
			child.addContent(prefixchild);
		}

		return prefixchild.getTextNormalize();
	}

	/**
	 * @see org.columba.calendar.model.api.IBasicModel#getDocument()
	 */
	public Document getDocument() {
		return doc;
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return get(ICALENDAR.UID);
	}

	public void setId(String id) {
		set(ICALENDAR.UID, id);
	}

	/**
	 * @return Returns the parentElement.
	 */
	protected Element getParentElement() {
		return parentElement;
	}

	public void setSummary(String s) {
		set(ICALENDAR.SUMMARY, s);
	}

	public String getSummary() {
		return get(ICALENDAR.SUMMARY);
	}

	public void setDescription(String s) {
		set(ICALENDAR.DESCRIPTION, s);
	}

	public String getDescription() {
		return get(ICALENDAR.DESCRIPTION);
	}
	
	public void setCalendar(String s) {
		set(ICALENDAR.X_COLUMBA_CALENDAR, s);
	}

	public String getCalendar() {
		return get(ICALENDAR.X_COLUMBA_CALENDAR);
	}

	public void setPriority(String s) {
		set(ICALENDAR.PRIORITY, s);
	}

	public String getPriority() {
		return get(ICALENDAR.PRIORITY);
	}

	public void setEventClass(String s) {
		set(ICALENDAR.CLASS, s);
	}

	public String getEventClass() {
		return get(ICALENDAR.CLASS);
	}

	public void setLocation(String s) {
		set(ICALENDAR.LOCATION, s);
	}

	public String getLocation() {
		return get(ICALENDAR.LOCATION);
	}

	public void setDTStart(Calendar c) {
		set(ICALENDAR.DTSTART, DateParser.createDateStringFromCalendar(c));
	}

	public Calendar getDTStart() {
		String s = get(ICALENDAR.DTSTART);
		Calendar c;
		try {
			c = DateParser.createCalendarFromDateString(s);
			return c;
		} catch (IllegalArgumentException e) {
			LOG.severe("date parsing exception");

			e.printStackTrace();
		}

		return Calendar.getInstance();
	}

	public void setDTEnd(Calendar c) {
		set(ICALENDAR.DTEND, DateParser.createDateStringFromCalendar(c));
	}

	public Calendar getDTEnd() {
		String s = get(ICALENDAR.DTEND);
		Calendar c;
		try {
			c = DateParser.createCalendarFromDateString(s);
			return c;
		} catch (IllegalArgumentException e) {
			LOG.severe("date parsing exception");

			e.printStackTrace();
		}

		return Calendar.getInstance();
	}

	public void setDTStamp(Calendar c) {
		set(ICALENDAR.DTSTAMP, DateParser.createDateStringFromCalendar(c));
	}

	public Calendar getDTStamp() {
		String s = get(ICALENDAR.DTSTAMP);

		Calendar c;
		try {
			c = DateParser.createCalendarFromDateString(s);
			return c;
		} catch (IllegalArgumentException e) {
			LOG.severe("date parsing exception");

			e.printStackTrace();
		}

		return Calendar.getInstance();
	}

	public void addCategory(String category) {
		set(ICALENDAR.CATEGORIES, ICALENDAR.ITEM, category);
	}

	public void removeCategory(String category) {
		Element child = getParentElement().getChild(ICALENDAR.CATEGORIES);
		List list = child.getChildren();
		for (int i = 0; i < list.size(); i++) {
			Element e = (Element) list.get(i);
			if (e.getText().equals(category)) {
				// found category
				child.removeContent(e);
			}
		}
	}
	
	public Enumeration getCategoryEnumeration() {
		//Element child = getParentElement().getChild(ICALENDAR.CATEGORIES);
		//List list = child.getChildren();

		// TODO categoryEnumeration
		return null;
	}
	
	public RecurrenceRule getRecurrenceRule() {
		// RRULE:FREQ=YEARLY;COUNT=5;INTERVAL=1
		// RRULE:FREQ=WEEKLY;UNTIL=20060725T215959;INTERVAL=1;BYDAY=TU
		// RRULE:FREQ=YEARLY;INTERVAL=1
		String rrule = get(ICALENDAR.RRULE);
		String freqS = getRValue(rrule, "FREQ");
		String intervalS = getRValue(rrule, "INTERVAL");
		String countS = getRValue(rrule, "COUNT");
		String untilS = getRValue(rrule, "UNTIL");
		int freq = -1;
		int interval = -1;
		int count = -1;
		Calendar untildate = null;
		try {
			if (freqS.equals("YEARLY"))
				freq = Recurrence.RECURRENCE_ANNUALLY;
			else if (freqS.equals("MONTHLY"))
				freq = Recurrence.RECURRENCE_MONTHLY;
			else if (freqS.equals("WEEKLY"))
				freq = Recurrence.RECURRENCE_WEEKLY;
			else if (freqS.equals("DAILY"))
				freq = Recurrence.RECURRENCE_DAILY;
			
			/*
			else if (freqS.equals("HOURLY"))
				freq = Recurrence.RECURRENCE_HOURLY;
			else if (freqS.equals("MINUTELY"))
				freq = Recurrence.RECURRENCE_HOURLY;
			else if (freqS.equals("SECONDLY"))
				freq = Recurrence.RECURRENCE_SECONDLY;
			*/
			
			if (intervalS.length() > 0)
				interval = Integer.parseInt(intervalS);
			if (countS.length() > 0)
				count = Integer.parseInt(countS);
			if (untilS.length() > 0)
			try {
				untildate = DateParser.createCalendarFromDateString(untilS);
			} catch (IllegalArgumentException e) {
				LOG.severe("getRecurrenceRule: date parsing exception");
			}
		} catch (NumberFormatException e) {
			LOG.severe("getRecurrenceRule: number parsing exception");
		}
		
		if (freq < 0)
			return null;
		
		RecurrenceRule r = new RecurrenceRule();
		r.setFrequency(Recurrence.toFrequency(freq));
		if (interval > -1)
			r.setInterval(interval);
		if (count > -1)
			r.setRepetitionCount(count);
		if (untildate != null)
			r.setUntilDate(untildate);
		return r;
	}
	
	public static String getRValue(String rrule, String property) {
		// find the property in the rrule string
		String irrule = rrule.toLowerCase();
		String iproperty = property.toLowerCase() + "=";
		String DELIMITER = ";";
		if (irrule.indexOf(iproperty) > -1) {
			int valuestart = irrule.indexOf(iproperty) + iproperty.length();
			int valueend = irrule.indexOf(DELIMITER, valuestart);
			if (valueend < 0)
				valueend = irrule.length();
			return rrule.substring(valuestart, valueend);
		}
		return "";
	}

	public void setRecurrenceRule(RecurrenceRule r) {
		
		// no recurrency, so nothing in the string
		if (r == null) {
			set(ICALENDAR.RRULE, "");
			return;
		}
		
		// build string
		String freqString = null;
		switch (r.getFrequency()) {  
			case Calendar.YEAR: freqString = "YEARLY"; break;
			case Calendar.MONTH: freqString = "MONTHLY"; break; 
			case Calendar.WEEK_OF_YEAR: freqString = "WEEKLY"; break;
			case Calendar.DAY_OF_YEAR: freqString = "DAILY"; break;
			case Calendar.HOUR: freqString = "HOURLY"; break;
			case Calendar.MINUTE: freqString = "MINUTELY"; break;
			case Calendar.SECOND: freqString = "SECONDLY";
		}
		if (freqString == null)
			throw new IllegalArgumentException("freqString = null");
		
		if (r.getByXXXRules() != null) {
			for (int i = 0; i < r.getByXXXRules().length; i++) {
				ByXXXRuleData rr = r.getByXXXRules()[i];
				System.out.println("weekdayiny = " + rr.getRelativeWeekDayInYearOrMonth());
				for (int j = 0; j < rr.getValues().length; j++) {
					System.out.println(" value " + j + " = " + rr.getValues()[j]);
				}
				System.out.println("calendarfield = " + rr.getCalendarField());
			}
		}
		
		String rruleString = "FREQ="+freqString;
		// optional parts
		if (r.getRepetitionCount() != null) 
			rruleString += ";COUNT="+r.getRepetitionCount();
		if (r.getInterval() > 0)
			rruleString += ";INTERVAL="+r.getInterval();
		if (r.getUntilDate() != null)
			//UNTIL=20070627T215959
			rruleString += ";UNTIL="+dateString(r.getUntilDate());
		
		set(ICALENDAR.RRULE, rruleString);
	}

	private String dateString(Calendar d) {
		String s = "";
		s += d.get(Calendar.YEAR);
		s += (d.get(Calendar.MONTH) < Calendar.OCTOBER ? "0" + (d.get(Calendar.MONTH)+1) : d.get(Calendar.MONTH)+1);
		s += d.get(Calendar.DAY_OF_MONTH);
		s += "T";
		s += (d.get(Calendar.HOUR_OF_DAY) < 10 ? "0" + d.get(Calendar.HOUR_OF_DAY) : d.get(Calendar.HOUR_OF_DAY));
		s += (d.get(Calendar.MINUTE) < 10 ? "0" + d.get(Calendar.MINUTE) : d.get(Calendar.MINUTE));
		s += (d.get(Calendar.SECOND) < 10 ? "0" + d.get(Calendar.SECOND) : d.get(Calendar.SECOND));
		return s;
	}

	public void setCategories(String categories) {
		set(ICALENDAR.CATEGORIES, categories);
	}
	
	public String getCategories() {
		return get(ICALENDAR.CATEGORIES);
	}

}
