package org.columba.calendar.item;

import org.columba.calendar.base.CalendarItem;
import org.columba.calendar.store.WebICalCalendarStore;
import org.columba.core.config.DefaultItem;

public class WebICalCalendarItem extends CalendarItem {

	public WebICalCalendarItem(DefaultItem item) {
		super(item);

		store = new WebICalCalendarStore(getId(), item.getString("property", "url"));
	}

	public CATEGORY getCategory() {
		return CATEGORY.WEB;
	}
}
