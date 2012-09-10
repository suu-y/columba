package org.columba.calendar.item;

import org.columba.calendar.base.CalendarItem;
import org.columba.calendar.store.BirthdayCalendarStore;
import org.columba.core.config.DefaultItem;

public class BirthdayCalendarItem extends CalendarItem {

	public BirthdayCalendarItem(DefaultItem item) {
		super(item);

		store = new BirthdayCalendarStore(getId());
	}

	public CATEGORY getCategory() {
		return CATEGORY.OTHER;
	}
}
