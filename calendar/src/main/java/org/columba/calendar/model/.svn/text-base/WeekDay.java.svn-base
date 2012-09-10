package org.columba.calendar.model;

import org.columba.calendar.model.api.IWeekDay;

public class WeekDay implements IWeekDay {
	
	private String day;
	private int offset;
	
	public WeekDay(String day) {
		this(day, 0);
	}
	
	public WeekDay(String day, int offset) {
		if (day == null)
			throw new IllegalArgumentException("day == null");
		this.day = day;
		this.offset = offset;
	}

	public String getDay() {
		return day;
	}

	public int getOffset() {
		return offset;
	}

	public void setDay(String day) {
		if (day == null)
			throw new IllegalArgumentException("day == null");
		this.day = day;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

}
