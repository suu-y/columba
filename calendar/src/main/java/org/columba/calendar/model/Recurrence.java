package org.columba.calendar.model;

import java.util.Calendar;
import java.util.List;

import org.columba.calendar.model.api.IRecurrence;
import org.columba.calendar.model.api.IWeekDay;

public class Recurrence implements IRecurrence {

	private int type;
	private int endType;
	private int endMaxOccurrences;
	private Calendar endDate;
	private int interval;
	private List<IWeekDay> weekDays;
	
	public Recurrence(int type) {
		this.type = type;
	}
	
	public Recurrence(int type, int endType, int endMaxOccurrences, Calendar endDate, int interval, List<IWeekDay> weekdays) {
		this.type = type;
		this.endType = endType;
		this.endDate = endDate;
		this.endMaxOccurrences = endMaxOccurrences;
		this.interval = interval;
		this.weekDays = weekdays;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public int getEndMaxOccurrences() {
		return endMaxOccurrences;
	}

	public int getEndType() {
		return endType;
	}

	public int getInterval() {
		return interval;
	}

	public int getType() {
		return type;
	}

	public List getWeekDays() {
		return weekDays;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	public void setEndMaxOccurrences(int max) {
		this.endMaxOccurrences = max;
	}

	public void setEndType(int type) {
		this.endType = type;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setWeekDays(List<IWeekDay> days) {
		this.weekDays = days;
	}

	public static int toFrequency(int recurrenceType2) {
		switch (recurrenceType2) {
		case RECURRENCE_WEEKLY:
			return java.util.Calendar.WEEK_OF_YEAR; 
		case RECURRENCE_DAILY:
			return java.util.Calendar.DAY_OF_YEAR;
		case RECURRENCE_ANNUALLY:
			return java.util.Calendar.YEAR;
		case RECURRENCE_MONTHLY:
			return java.util.Calendar.MONTH;
		}
		return -1;
	}
	
	public static int fromFrequency(int recurrenceType2) {
		switch (recurrenceType2) {
		case java.util.Calendar.WEEK_OF_YEAR:
			return RECURRENCE_WEEKLY; 
		case java.util.Calendar.DAY_OF_YEAR:
			return RECURRENCE_DAILY;
		case java.util.Calendar.YEAR:
			return RECURRENCE_ANNUALLY;
		case java.util.Calendar.MONTH:
			return RECURRENCE_MONTHLY;
		}
		return RECURRENCE_NONE;
	}

}
