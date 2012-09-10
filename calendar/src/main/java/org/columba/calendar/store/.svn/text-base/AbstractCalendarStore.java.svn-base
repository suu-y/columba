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
package org.columba.calendar.store;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.event.EventListenerList;

import org.columba.calendar.model.DateRange;
import org.columba.calendar.model.api.IComponent;
import org.columba.calendar.model.api.IComponentInfo;
import org.columba.calendar.model.api.IComponentInfoList;
import org.columba.calendar.model.api.IDateRange;
import org.columba.calendar.model.api.IEvent;
import org.columba.calendar.model.api.IEventInfo;
import org.columba.calendar.model.api.ITodo;
import org.columba.calendar.store.api.ICalendarStore;
import org.columba.calendar.store.api.IStoreListener;
import org.columba.calendar.store.api.StoreEvent;
import org.columba.calendar.store.api.StoreException;
import org.columba.calendar.ui.base.CalendarHelper;

import com.miginfocom.calendar.activity.Activity;
import com.miginfocom.calendar.activity.ActivityDepository;

public abstract class AbstractCalendarStore implements ICalendarStore {

	private EventListenerList listenerList = new EventListenerList();

	public AbstractCalendarStore() {
		super();

		// register interest on store changes
		// TODO the dependency should be the other way around
		addStorageListener(StoreEventDelegator.getInstance());

	}

	public void initActivities() throws StoreException {
		IComponentInfoList list = getComponentInfoList();
		Iterator<IComponentInfo> it = list.iterator();
		while (it.hasNext()) {
			IComponentInfo item = (IComponentInfo) it.next();

			if (item.getType() == IComponent.TYPE.EVENT) {
				IEventInfo event = (IEventInfo) item;

				Activity act = CalendarHelper.createActivity(event, this);

				ActivityDepository.getInstance().addBrokedActivity(act, this);
			}
		}
	}

	public abstract IComponentInfo get(Object id) throws StoreException;

	public abstract void add(IComponentInfo basicModel) throws StoreException;

	public void modify(Object id, IComponentInfo basicModel) throws StoreException {
		if (id == null)
			throw new IllegalArgumentException("id == null");
		if (basicModel == null)
			throw new IllegalArgumentException("basicModel == null");

		fireItemChanged(id);
	}

	public void remove(Object id) throws StoreException {
		if (id == null)
			throw new IllegalArgumentException("id == null");
		fireItemRemoved(id);
	}

	public abstract IComponentInfoList getComponentInfoList()
			throws StoreException;

	public abstract boolean exists(Object id) throws StoreException;

	/**
	 * *********************** very slow search implementation
	 * **********************
	 */

	/**
	 * @see org.columba.calendar.store.api.ICalendarStore#findByDateRange(org.columba.calendar.model.api.IDateRange)
	 */
	public Iterator<String> findByDateRange(IDateRange dateRange)
			throws StoreException {
		Vector<String> result = new Vector<String>();

		Iterator<String> it = getIdIterator();
		while (it.hasNext()) {
			String id = it.next();
			IComponentInfo c = get(id);
			if (c.getType().equals(IComponent.TYPE.EVENT)) {
				IEvent event = (IEvent) c;
				Calendar startDate = event.getDtStart();
				Calendar endDate = event.getDtEnd();
				IDateRange dr = new DateRange(startDate, endDate);
				if (dateRange.equals(dr))
					result.add(id);
			} else if (c.getType().equals(IComponent.TYPE.TODO)) {
				ITodo todo = (ITodo) c;
				Calendar startDate = todo.getDtStart();
				Calendar endDate = todo.getDue();
				IDateRange dr = new DateRange(startDate, endDate);
				if (dateRange.equals(dr))
					result.add(id);
			} else
				throw new IllegalArgumentException(
						"unsupported component type " + c.getType());
		}

		return result.listIterator();
	}

	/**
	 * @see org.columba.calendar.store.api.ICalendarStore#findByStartDate(java.util.Calendar)
	 */
	public Iterator<String> findByStartDate(Calendar startDate)
			throws StoreException {
		Vector<String> result = new Vector<String>();

		Iterator<String> it = getIdIterator();
		while (it.hasNext()) {
			String id = it.next();
			IComponentInfo c = get(id);
			if (c.getType().equals(IComponent.TYPE.EVENT)) {
				IEvent event = (IEvent) c;
				Calendar sd = event.getDtStart();
				if (startDate.equals(sd))
					result.add(id);
			} else if (c.getType().equals(IComponent.TYPE.TODO)) {
				ITodo todo = (ITodo) c;
				Calendar sd = todo.getDtStart();
				if (startDate.equals(sd))
					result.add(id);
			} else
				throw new IllegalArgumentException(
						"unsupported component type " + c.getType());
		}

		return result.listIterator();
	}

	/**
	 * @see org.columba.calendar.store.api.ICalendarStore#findBySummary(java.lang.String)
	 */
	public Iterator<String> findBySummary(String searchTerm)
			throws StoreException {
		Vector<String> result = new Vector<String>();

		Iterator<String> it = getIdIterator();
		while (it.hasNext()) {
			String id = it.next();
			IComponentInfo c = get(id);
			if (c.getType().equals(IComponent.TYPE.EVENT)) {
				IEventInfo event = (IEventInfo) c;
				String summary = event.getEvent().getSummary();
				if (summary.indexOf(searchTerm) != -1)
					result.add(id);
			} else if (c.getType().equals(IComponent.TYPE.TODO)) {
				ITodo todo = (ITodo) c;
				String summary = todo.getSummary();
				if (summary.indexOf(searchTerm) != -1)
					result.add(id);
			} else
				throw new IllegalArgumentException(
						"unsupported component type " + c.getType());
		}

		return result.listIterator();
	}

	/** ********************** event ****************************** */

	/**
	 * Adds a listener.
	 */
	public void addStorageListener(IStoreListener l) {
		listenerList.add(IStoreListener.class, l);
	}

	/**
	 * Removes a previously registered listener.
	 */
	public void removeStorageListener(IStoreListener l) {
		listenerList.remove(IStoreListener.class, l);
	}

	/**
	 * Propagates an event to all registered listeners notifying them of a item
	 * addition.
	 */
	protected void fireItemAdded(Object uid) {

		StoreEvent e = new StoreEvent(this, uid);
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();

		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == IStoreListener.class) {
				((IStoreListener) listeners[i + 1]).itemAdded(e);
			}
		}
	}

	/**
	 * Propagates an event to all registered listeners notifying them of a item
	 * removal.
	 */
	protected void fireItemRemoved(Object uid) {

		StoreEvent e = new StoreEvent(this, uid);
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();

		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == IStoreListener.class) {
				((IStoreListener) listeners[i + 1]).itemRemoved(e);
			}
		}
	}

	/**
	 * Propagates an event to all registered listeners notifying them of a item
	 * change.
	 */
	protected void fireItemChanged(Object uid) {

		StoreEvent e = new StoreEvent(this, uid);
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();

		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == IStoreListener.class) {
				((IStoreListener) listeners[i + 1]).itemChanged(e);
			}
		}
	}
}
