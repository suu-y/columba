package org.columba.calendar.parser;

import java.util.Calendar;

public class ParserHelper {

	public static boolean isAllDayEvent(Calendar dtStart, Calendar dtEnd) {
		boolean startAtNullOClock = false;
		boolean endAtNullOClock = false;
		boolean allDayEvent = false;

		startAtNullOClock = ((dtStart.get(java.util.Calendar.MILLISECOND) == 0)
				&& (dtStart.get(java.util.Calendar.SECOND) == 0)
				&& (dtStart.get(java.util.Calendar.MINUTE) == 0) && (dtStart
				.get(java.util.Calendar.HOUR_OF_DAY) == 0));

		endAtNullOClock = ((dtEnd.get(java.util.Calendar.MILLISECOND) == 0)
				&& (dtEnd.get(java.util.Calendar.SECOND) == 0)
				&& (dtEnd.get(java.util.Calendar.MINUTE) == 0) && (dtEnd
				.get(java.util.Calendar.HOUR_OF_DAY) == 0));

		System.out.println("SATD ID: 28");
		// an all day event
		allDayEvent = startAtNullOClock && endAtNullOClock;

		return allDayEvent;

	}

}
