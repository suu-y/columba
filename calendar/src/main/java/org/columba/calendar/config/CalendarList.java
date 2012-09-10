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
package org.columba.calendar.config;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

import org.columba.api.plugin.IExtension;
import org.columba.api.plugin.IExtensionHandler;
import org.columba.api.plugin.PluginHandlerNotFoundException;
import org.columba.calendar.base.api.IActivity;
import org.columba.calendar.base.api.ICalendarItem;
import org.columba.calendar.config.api.ICalendarList;
import org.columba.calendar.store.api.ICalendarStore;
import org.columba.core.config.DefaultItem;
import org.columba.core.gui.dialog.ErrorDialog;
import org.columba.core.plugin.PluginManager;
import org.columba.core.xml.XmlElement;

public class CalendarList implements ICalendarList {

	protected XmlElement listNode;

	private Hashtable<String, ICalendarItem> hashtable = new Hashtable<String, ICalendarItem>();

	private int nextUid = 1;

	private static CalendarList instance;

	private CalendarList(XmlElement listNode) {
		this.listNode = listNode;

		createCalendars();
	}

	protected void createCalendars() {
		int count = listNode.count();

		XmlElement child;
		for (int i = 0; i < count; i++) {
			child = listNode.getElement(i);
			String name = child.getName();

			if (name.equals("calendar")) {
				DefaultItem item = new DefaultItem(child);
				try {
					int u = Integer.parseInt(item.get("uid"));
					if (u >= nextUid)
						nextUid = u + 1;
				} catch (NumberFormatException e) {
				}

				ICalendarItem calendar = instanciateCalendar(item);
				if (calendar != null)
					hashtable.put(calendar.getId(), calendar);
			}
		}
	}

	protected ICalendarItem instanciateCalendar(DefaultItem item) {
		String type = item.get("type");
		Object[] args = { item };

		IExtensionHandler handler = null;
		try {
			handler =  PluginManager.getInstance()
					.getExtensionHandler("org.columba.calendar.item");
		} catch (PluginHandlerNotFoundException ex) {
			ErrorDialog.createDialog(ex.getMessage(), ex);
		}

		ICalendarItem calendar = null;
		try {
			IExtension extension = handler.getExtension(type);
			if (extension != null)
				calendar = (ICalendarItem) extension.instanciateExtension(args);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return calendar;
	}

	public static CalendarList getInstance() {
		if (instance == null) {
			instance = new CalendarList(CalendarConfig
					.getInstance().getCalendarConfig().getElement("/list"));
		}
		return instance;
	}

	public Enumeration<ICalendarItem> getElements() {
		return hashtable.elements();
	}

	public ICalendarStore getStore(IActivity activity) {
		ICalendarItem calendar = get(activity.getCalendarId());
		if (calendar != null)
			return calendar.getStore();
		return null;
	}

	public Iterator<ICalendarStore> getStores() {
		LinkedList<ICalendarStore> l = new LinkedList<ICalendarStore>();
		Iterator<ICalendarItem> it = hashtable.values().iterator();
		while (it.hasNext()) {
			ICalendarStore store = it.next().getStore();

			if (l.contains(store))
				continue;

			l.add(store);
		}
		return l.iterator();
	}

	public ICalendarItem add(ICalendarItem item) {
		hashtable.put(item.getId(), item);

		return item;
	}

	public ICalendarItem remove(String id) {
		return hashtable.remove(id);
	}

	public ICalendarItem get(String id) {
		return hashtable.get(id);
	}

}
