package org.columba.calendar.store.api;

import java.util.Calendar;
import java.util.Iterator;

import org.columba.calendar.model.api.IComponentInfo;
import org.columba.calendar.model.api.IComponentInfoList;
import org.columba.calendar.model.api.IDateRange;

/**
 * Calendar store.
 * 
 * @author fdietz
 */
public interface ICalendarStore {
	String getId();

	void initActivities() throws StoreException;

	IComponentInfo get(Object uid) throws StoreException;

	void add(IComponentInfo calendarModel) throws StoreException;

	void modify(Object uid, IComponentInfo calendarModel) throws StoreException;

	void remove(Object uid) throws StoreException;

	IComponentInfoList getComponentInfoList() throws StoreException;

	IComponentInfoList getComponentInfoList(String calendarId) throws StoreException;
	
	Iterator<String> getIdIterator() throws StoreException;
	
	Iterator<String> getIdIterator(String calendarId) throws StoreException;
	
	boolean exists(Object uid) throws StoreException;

	boolean isReadOnly(String uid) throws StoreException;
	
	/**
	 * Find all components including the specific <code>searchTerm</code> in the summary.
	 *  
	 * @param summary
	 * @return					iterator of all component IDs
	 * @throws StoreException
	 */
	Iterator<String> findBySummary(String searchTerm) throws StoreException;
	
	/**
	 * Find all components starting at <code>startDate</code>.
	 * 
	 * @param startDate	
	 * @return					iterator of all component IDs
	 * @throws StoreException
	 */
	Iterator<String> findByStartDate(Calendar startDate) throws StoreException;
	
	/**
	 * Find all components in the date range.
	 * 
	 * @param dateRange
	 * @return					iterator of all component IDs
	 * @throws StoreException
	 */
	Iterator<String> findByDateRange(IDateRange dateRange) throws StoreException;
}
