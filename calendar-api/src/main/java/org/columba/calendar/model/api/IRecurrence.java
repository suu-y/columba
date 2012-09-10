package org.columba.calendar.model.api;

import java.util.Calendar;
import java.util.List;

public interface IRecurrence {
	
	final static int RECURRENCE_NONE = 0;
	final static int RECURRENCE_DAILY = 1;
	final static int RECURRENCE_WEEKLY = 2;
	final static int RECURRENCE_MONTHLY = 3;
	final static int RECURRENCE_ANNUALLY = 4;
	
	final static int RECURRENCE_END_FOREVER = 0;
	final static int RECURRENCE_END_MAXOCCURRENCES = 1;
	final static int RECURRENCE_END_ENDDATE = 2;
	
	public abstract int getType();
	public abstract void setType(int type);
	
	public abstract int getEndType();
	public abstract void setEndType(int type);
	
	public abstract int getEndMaxOccurrences();
	public abstract void setEndMaxOccurrences(int max);
	
	public abstract Calendar getEndDate();
	public abstract void setEndDate(Calendar endDate);
	
	public abstract int getInterval();
	public abstract void setInterval(int interval);
	
	public abstract List getWeekDays();
	public abstract void setWeekDays(List<IWeekDay> days);

}
