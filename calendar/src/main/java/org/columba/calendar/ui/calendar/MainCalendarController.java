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
package org.columba.calendar.ui.calendar;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToolTip;
import javax.swing.event.EventListenerList;

import org.columba.calendar.base.Activity;
import org.columba.calendar.base.api.IActivity;
import org.columba.calendar.model.api.IDateRange;
import org.columba.calendar.ui.calendar.api.ActivitySelectionChangedEvent;
import org.columba.calendar.ui.calendar.api.IActivitySelectionChangedListener;
import org.columba.calendar.ui.calendar.api.ICalendarView;
import org.columba.calendar.ui.frame.api.ICalendarMediator;
import org.columba.core.gui.menu.ExtendablePopupMenu;
import org.columba.core.gui.menu.MenuXMLDecoder;

import com.miginfocom.ashape.interaction.InteractionEvent;
import com.miginfocom.ashape.interaction.InteractionListener;
import com.miginfocom.beans.DateAreaBean;
import com.miginfocom.calendar.activity.view.ActivityView;
import com.miginfocom.calendar.category.Category;
import com.miginfocom.calendar.category.CategoryDepository;
import com.miginfocom.calendar.category.CategoryFilter;
import com.miginfocom.calendar.datearea.ActivityDragResizeEvent;
import com.miginfocom.calendar.datearea.ActivityDragResizeListener;
import com.miginfocom.calendar.datearea.ActivityMoveEvent;
import com.miginfocom.calendar.datearea.ActivityMoveListener;
import com.miginfocom.calendar.datearea.DateArea;
import com.miginfocom.calendar.datearea.DefaultDateArea;
import com.miginfocom.util.MigUtil;
import com.miginfocom.util.PropertyKey;
import com.miginfocom.util.dates.DateRange;
import com.miginfocom.util.dates.DateRangeI;
import com.miginfocom.util.dates.ImmutableDateRange;
import com.miginfocom.util.dates.MutableDateRange;
import com.miginfocom.util.expression.LogicalExpression;
import com.miginfocom.util.filter.ExpressionFilter;
import com.miginfocom.util.filter.Filter;
import com.miginfocom.util.states.ToolTipProvider;

/**
 * @author fdietz
 * 
 */
public class MainCalendarController implements InteractionListener,
		ActivityMoveListener, ICalendarView, ActivityDragResizeListener {

	private static final Logger LOG = Logger
			.getLogger("org.columba.calendar.ui.calendar");

	public static final String MAIN_WEEKS_CONTEXT = "mainWeeks";

	public static final String MAIN_DAYS_CONTEXT = "mainDays";

	private int currentViewMode = ICalendarView.VIEW_MODE_WEEK;

	// private ThemeDateAreaContainer view;

	public static final String PROP_FILTERED = "filterRow";

	private static final int WEEKS_PER_YEAR = 52; // FIXME is this correct for

	// every year?

	private static final int DAYS_PER_YEAR = 365; // FIXME this is not correct

	// for leap years!

	private IActivity selectedActivity;

	private com.miginfocom.beans.DateAreaBean dateAreaBean;

	private com.miginfocom.beans.DateAreaBean monthlyDateAreaBean;

	private com.miginfocom.beans.DateAreaBean currentDateAreaBean;

	private com.miginfocom.calendar.activity.Activity selectedInternalActivitiy;

	private JPanel panel = new JPanel();

	private EventListenerList listenerList = new EventListenerList();

	private ExtendablePopupMenu menu;

	private ICalendarMediator mediator;

	/**
	 * 
	 */
	public MainCalendarController(ICalendarMediator mediator) {
		super();

		this.mediator = mediator;

		panel = new JPanel();
		panel.setLayout(new BorderLayout());

		dateAreaBean = DateAreaBeanFactory.initDailyDateArea();
		registerListeners(dateAreaBean);
		monthlyDateAreaBean = DateAreaBeanFactory.initMonthlyDateArea();
		registerListeners(monthlyDateAreaBean);

		// start with week view
		currentDateAreaBean = dateAreaBean;

		setViewMode(currentViewMode);

		panel.repaint();

	}

	/**
	 * Get popup menu
	 * 
	 * @return popup menu
	 */
	public JPopupMenu getPopupMenu() {
		return menu;
	}

	/**
	 * create the PopupMenu
	 */
	public void createPopupMenu(ICalendarMediator mediator) {

		InputStream is = this.getClass().getResourceAsStream(
				"/org/columba/calendar/action/contextmenu_calendar.xml");

		menu = new MenuXMLDecoder(mediator).createPopupMenu(is);

	}

	private DateAreaBean initComponents(boolean dailyView) {
		panel.removeAll();

		if (dailyView) {
			panel.add(dateAreaBean, BorderLayout.CENTER);
			return dateAreaBean;
		} else {
			panel.add(monthlyDateAreaBean, BorderLayout.CENTER);
			return monthlyDateAreaBean;
		}

	}

	/**
	 * @param localDateAreaBean
	 */
	private void registerListeners(DateAreaBean localDateAreaBean) {
		ToolTipProvider ttp = new ToolTipProvider() {
			public int configureToolTip(JToolTip toolTip, MouseEvent e,
					Object source) {
				if (e.getID() == MouseEvent.MOUSE_MOVED
						&& source instanceof ActivityView) {
					// toolTip.setForeground(Color.DARK_GRAY);
					toolTip.setTipText(((ActivityView) source).getModel()
							.getSummary());
					return ToolTipProvider.SHOW_TOOLTIP;
				} else {
					return ToolTipProvider.HIDE_TOOLTIP;
				}
			}
		};

		localDateAreaBean.getDateArea().setToolTipProvider(ttp);

		((DefaultDateArea) localDateAreaBean.getDateArea())
				.addMouseListener(new MyMouseListener());

		((DefaultDateArea) localDateAreaBean.getDateArea())
				.addInteractionListener(this);

		((DefaultDateArea) localDateAreaBean.getDateArea())
				.addActivityMoveListener(this);

		((DefaultDateArea) localDateAreaBean.getDateArea())
				.addActivityDragResizeListener(this);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.columba.calendar.ui.calendar.api.ICalendarView#getSelectedActivity()
	 */
	public IActivity getSelectedActivity() {
		return selectedActivity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.columba.calendar.ui.calendar.api.ICalendarView#getView()
	 */
	public JComponent getView() {
		return panel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.columba.calendar.ui.calendar.api.ICalendarView#setViewMode(int)
	 */
	public void setViewMode(int mode) {

		this.currentViewMode = mode;

		int viewMode = -1;

		int days = -1;

		DateRange newVisRange = new DateRange(currentDateAreaBean.getDateArea()
				.getVisibleDateRangeCorrected());

		switch (mode) {
		case ICalendarView.VIEW_MODE_DAY:

			viewMode = DateRangeI.RANGE_TYPE_DAY;

			days = 1;

			currentDateAreaBean = initComponents(true);

			break;
		case ICalendarView.VIEW_MODE_WEEK:

			viewMode = DateRangeI.RANGE_TYPE_DAY;

			days = 7;

			currentDateAreaBean = initComponents(true);

			break;
		case ICalendarView.VIEW_MODE_WORK_WEEK:

			viewMode = DateRangeI.RANGE_TYPE_DAY;

			days = 5;

			currentDateAreaBean = initComponents(true);

			break;
		case ICalendarView.VIEW_MODE_MONTH:

			viewMode = DateRangeI.RANGE_TYPE_MONTH;

			days = 1;

			currentDateAreaBean = initComponents(false);

			break;
		}

		DefaultDateArea dateArea = currentDateAreaBean.getDateArea();

		newVisRange.setSize(viewMode, days, MutableDateRange.ALIGN_CENTER_UP);
		dateArea.setVisibleDateRange(newVisRange);

		panel.validate();
		panel.repaint();

	}

	/**
	 * @param dateRange
	 */
	public void printDebug(DateRange dateRange) {
		Calendar todayCalendar = Calendar.getInstance();
		int today = todayCalendar.get(java.util.Calendar.DAY_OF_YEAR);

		Calendar startCalendar = dateRange.getStart();
		int selectedStartDay = startCalendar
				.get(java.util.Calendar.DAY_OF_YEAR);

		Calendar endCalendar = dateRange.getStart();
		int selectedEndDay = endCalendar.get(java.util.Calendar.DAY_OF_YEAR);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.columba.calendar.ui.calendar.api.ICalendarView#recreateFilterRows()
	 */
	public void recreateFilterRows() {
		filterDateArea(dateAreaBean);
		filterDateArea(monthlyDateAreaBean);
	}

	/**
	 * @param localDateAreaBean
	 */
	private void filterDateArea(DateAreaBean localDateAreaBean) {
		Collection cats = CategoryDepository.getRoot().getChildrenDeep();

		DateArea dateArea = localDateAreaBean.getDateArea();

		for (Iterator it = cats.iterator(); it.hasNext();) {
			Category cat = (Category) it.next();
			Object isHidden = cat
					.getProperty(PropertyKey.getKey(PROP_FILTERED));
			if (MigUtil.isTrue(isHidden) == false)
				it.remove();
		}

		Filter showFilter = new CategoryFilter(new ExpressionFilter(
				"hideFilter", new LogicalExpression(Category.PROP_IS_HIDDEN,
						LogicalExpression.NOT_EQUALS, Boolean.TRUE)));
		dateArea.setActivityViewFilter(showFilter);

		if (cats.size() == 0) {

			dateArea.setRowFilters(null);

		} else {

			CategoryFilter[] catRestr = new CategoryFilter[cats.size()];
			int i = 0;
			for (Iterator it = cats.iterator(); it.hasNext();) {
				Category cat = (Category) it.next();
				catRestr[i++] = new CategoryFilter(cat, true, true);
			}

			dateArea.setRowFilters(catRestr);
		}

		localDateAreaBean.revalidate();
		localDateAreaBean.repaint();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miginfocom.ashape.interaction.InteractionListener#interactionOccured(com.miginfocom.ashape.interaction.InteractionEvent)
	 */
	public void interactionOccured(InteractionEvent e) {
		Object value = e.getCommand().getValue();

		if (MigUtil.equals(value, DefaultDateArea.AE_MOUSE_ENTERED)) {
			// mouse hovers over activity
			com.miginfocom.calendar.activity.Activity activity = ((ActivityView) e
					.getInteractor().getInteracted()).getModel();

		}

		final Object o = e.getInteractor().getInteracted();

		if (e.getSourceEvent() instanceof MouseEvent) {
			final Point p = ((MouseEvent) e.getSourceEvent()).getPoint();
			Object commandValue = e.getCommand().getValue();

			// if (DefaultDateArea.AE_CLICKED.equals(commandValue)
			// || DefaultDateArea.AE_DOUBLE_CLICKED.equals(commandValue)) {

			/*
			 * if (o instanceof ActivityView) { // retrieve new selection
			 * selectedInternalActivitiy = ((ActivityView) o).getModel(); //
			 * remember selected activity selectedActivity = new
			 * Activity(selectedInternalActivitiy); // notify all listeners
			 * fireSelectionChanged(new IActivity[] { selectedActivity }); }
			 * else { // clicked on calendar - not activity
			 * selectedInternalActivitiy = null;
			 * 
			 * selectedActivity = null; // fireSelectionChanged(new Activity[]
			 * {}); }
			 */
			// }
			if (o instanceof ActivityView) {
				// retrieve new selection
				selectedInternalActivitiy = ((ActivityView) o).getModel();

				// remember selected activity
				selectedActivity = new Activity(selectedInternalActivitiy);

				// notify all listeners
				fireSelectionChanged(new IActivity[] { selectedActivity });

				// check if happens on the selected activity
				if (DefaultDateArea.AE_POPUP_TRIGGER.equals(commandValue)) {

					// select activity before opening context context-menu
					// selectedInternalActivitiy.getStates().setStates(
					// GenericStates.SELECTED_BIT, true);

					// show context menu
					menu.show(currentDateAreaBean.getDateArea(), p.x, p.y);

				} else if (DefaultDateArea.AE_DOUBLE_CLICKED
						.equals(commandValue)) {

					mediator.fireStartActivityEditing(selectedActivity);
				}
			} else {
				// check if happens in calendar, but not on activity
				System.out.println("---> event in calendar");

				if (DefaultDateArea.AE_DOUBLE_CLICKED.equals(commandValue)) {

					// double-click on empty calendar
					// mediator.fireCreateActivity(null);
				}
			}
		}
	}

	// trigged if activity is moved or daterange is modified
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miginfocom.calendar.datearea.ActivityMoveListener#activityMoved(com.miginfocom.calendar.datearea.ActivityMoveEvent)
	 */
	public void activityMoved(ActivityMoveEvent e) {

		com.miginfocom.calendar.activity.Activity activity = e.getActivity();
		Activity a = new Activity(activity);
		if (a.getStore().isReadOnly(a.getId())) {
			MutableDateRange newRange = e.getNewRange();
			newRange.setToRange(activity.getDateRangeForReading());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.columba.calendar.ui.calendar.api.ICalendarView#viewToday()
	 */
	public void viewToday() {
		DateRange newVisRange = new DateRange(currentDateAreaBean.getDateArea()
				.getVisibleDateRange());
		printDebug(newVisRange);

		Calendar todayCalendar = Calendar.getInstance();
		long difference = todayCalendar.getTimeInMillis()
				- newVisRange.getMiddleMillis();
		long difference_in_days = difference / (1000 * 60 * 60 * 24);

		newVisRange = rollWithYears(newVisRange,
				java.util.Calendar.DAY_OF_YEAR, (int) (difference_in_days));

		currentDateAreaBean.getDateArea().setVisibleDateRange(newVisRange);

		currentDateAreaBean.revalidate();
		currentDateAreaBean.repaint();
	}

	public static int getWeekOfYear(Calendar arg) {
		// the java methods return a nice week of year, we need hard facts
		return ((arg.get(java.util.Calendar.DAY_OF_YEAR) - 1) / 7) + 1;
	}

	public static DateRange rollWithYears(DateRange range, int mode, int amount) {
		DateRange newVisRange = range;
		DateRange rolledMiddle;
		
		switch (mode) {
		case java.util.Calendar.DAY_OF_YEAR:

			rolledMiddle = new DateRange(newVisRange.getMiddle(), newVisRange
					.getMiddle().getTimeZone(), newVisRange.getLocale());

			if ((rolledMiddle.getStartField(java.util.Calendar.DAY_OF_YEAR)
					+ amount <= 0)) {
				int days_of_year = rolledMiddle.getStartField(java.util.Calendar.DAY_OF_YEAR);
				rolledMiddle.roll(java.util.Calendar.DAY_OF_YEAR, amount);
				System.out.println("SATD ID: 43");
				int day_difference = days_of_year - Math.abs(amount);
				int years = (int) (Math.floor((day_difference) / DAYS_PER_YEAR) + (day_difference <= 0 ? -1 : 0) );
				rolledMiddle.roll(java.util.Calendar.YEAR, years);
			} else if (rolledMiddle
					.getStartField(java.util.Calendar.DAY_OF_YEAR)
					+ amount > DAYS_PER_YEAR) {
				rolledMiddle.roll(java.util.Calendar.DAY_OF_YEAR, amount);
				rolledMiddle.roll(java.util.Calendar.YEAR,
						((amount - 1) / DAYS_PER_YEAR) + 1);
			} else {
				rolledMiddle.roll(java.util.Calendar.DAY_OF_YEAR, amount);
			}

			rolledMiddle.setSize(DateRange.RANGE_TYPE_DAY, newVisRange.getSize(
					DateRange.RANGE_TYPE_DAY, true),
					MutableDateRange.ALIGN_CENTER_DOWN);

			newVisRange.setStart(rolledMiddle.getStart());
			newVisRange.setEnd(rolledMiddle.getEnd(), true);

			break;
		case java.util.Calendar.WEEK_OF_YEAR:
			rolledMiddle = rollWithYears(new DateRange(newVisRange.getMiddle(),
					newVisRange.getMiddle().getTimeZone(), newVisRange
							.getLocale()), java.util.Calendar.DAY_OF_YEAR,
					amount * 7);

			rolledMiddle.setSize(DateRange.RANGE_TYPE_DAY, newVisRange.getSize(
					DateRange.RANGE_TYPE_DAY, true),
					MutableDateRange.ALIGN_CENTER_DOWN);

			if (getWeekOfYear(rolledMiddle.getStart()) > getWeekOfYear(rolledMiddle
					.getEnd())) {
				// it will not be done correctly, if the first statement is
				// omitted
				rolledMiddle.setSize(DateRange.RANGE_TYPE_DAY, 1,
						MutableDateRange.ALIGN_CENTER_UP);
				rolledMiddle.setSize(DateRange.RANGE_TYPE_DAY, newVisRange
						.getSize(DateRange.RANGE_TYPE_DAY, true),
						MutableDateRange.ALIGN_CENTER_UP);
			}

			newVisRange.setStart(rolledMiddle.getStart());
			newVisRange.setEnd(rolledMiddle.getEnd(), true);

			break;
		default:
			LOG.severe("Cannot roll! " + mode);
		}

		return newVisRange;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.columba.calendar.ui.calendar.api.ICalendarView#viewNext()
	 */
	public void viewNext() {
		DateRange newVisRange = new DateRange(currentDateAreaBean.getDateArea()
				.getVisibleDateRangeCorrected());

		switch (currentViewMode) {
		case ICalendarView.VIEW_MODE_DAY:

			// roll correctly over years
			newVisRange = rollWithYears(newVisRange,
					java.util.Calendar.DAY_OF_YEAR, 1);

			break;
		case ICalendarView.VIEW_MODE_WEEK:

			// roll correctly over years
			newVisRange = rollWithYears(newVisRange,
					java.util.Calendar.DAY_OF_YEAR, 7);

			break;
		case ICalendarView.VIEW_MODE_WORK_WEEK:

			// roll correctly over years
			newVisRange = rollWithYears(newVisRange,
					java.util.Calendar.DAY_OF_YEAR, 7);

			break;
		case ICalendarView.VIEW_MODE_MONTH:

			// roll correctly over years
			newVisRange = rollWithYears(newVisRange,
					java.util.Calendar.WEEK_OF_YEAR, 1);

			break;
		}

		currentDateAreaBean.getDateArea().setVisibleDateRange(newVisRange);

		currentDateAreaBean.revalidate();
		currentDateAreaBean.repaint();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.columba.calendar.ui.calendar.api.ICalendarView#viewPrevious()
	 */
	public void viewPrevious() {
		DateRange newVisRange = new DateRange(currentDateAreaBean.getDateArea()
				.getVisibleDateRangeCorrected());

		switch (currentViewMode) {
		case ICalendarView.VIEW_MODE_DAY:

			// roll correctly over years
			newVisRange = rollWithYears(newVisRange,
					java.util.Calendar.DAY_OF_YEAR, -1);

			break;
		case ICalendarView.VIEW_MODE_WEEK:

			// roll correctly over years
			newVisRange = rollWithYears(newVisRange,
					java.util.Calendar.DAY_OF_YEAR, -7);

			break;
		case ICalendarView.VIEW_MODE_WORK_WEEK:

			// roll correctly over years
			newVisRange = rollWithYears(newVisRange,
					java.util.Calendar.DAY_OF_YEAR, -7);

			break;
		case ICalendarView.VIEW_MODE_MONTH:

			// roll correctly over years
			newVisRange = rollWithYears(newVisRange,
					java.util.Calendar.WEEK_OF_YEAR, -1);

			break;
		}

		currentDateAreaBean.getDateArea().setVisibleDateRange(newVisRange);

		currentDateAreaBean.revalidate();
		currentDateAreaBean.repaint();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.columba.calendar.ui.calendar.api.ICalendarView#setVisibleDateRange(org.columba.calendar.model.api.IDateRange)
	 */
	public void setVisibleDateRange(IDateRange dateRange) {
		ImmutableDateRange newRange = new ImmutableDateRange(dateRange
				.getStartTime().getTimeInMillis(), dateRange.getEndTime()
				.getTimeInMillis(), false, null, null);

		currentDateAreaBean.getDateArea().setVisibleDateRange(newRange);

		currentDateAreaBean.revalidate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miginfocom.calendar.datearea.ActivityDragResizeListener#activityDragResized(com.miginfocom.calendar.datearea.ActivityDragResizeEvent)
	 */
	public void activityDragResized(ActivityDragResizeEvent e) {
		com.miginfocom.calendar.activity.ActivityList activityList = (com.miginfocom.calendar.activity.ActivityList) e
				.getSource();

		for (int i = 0, size = activityList.size(); i < size; i++) {
			if (selectedActivity.getStore().isReadOnly(selectedActivity.getId()))
				continue;

			mediator.fireActivityMoved(selectedActivity);
		}

	}

	/**
	 * Adds a listener.
	 */
	public void addSelectionChangedListener(
			IActivitySelectionChangedListener listener) {
		listenerList.add(IActivitySelectionChangedListener.class, listener);
	}

	/**
	 * Removes a previously registered listener.
	 */
	public void removeSelectionChangedListener(
			IActivitySelectionChangedListener listener) {
		listenerList.remove(IActivitySelectionChangedListener.class, listener);
	}

	/**
	 * Propagates an event to all registered listeners notifying them that the
	 * selectoin has been changed.
	 */
	private void fireSelectionChanged(IActivity[] selection) {
		ActivitySelectionChangedEvent e = new ActivitySelectionChangedEvent(
				this, selection);
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();

		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == IActivitySelectionChangedListener.class) {
				((IActivitySelectionChangedListener) listeners[i + 1])
						.selectionChanged(e);
			}
		}
	}

	class MyMouseListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			handleEvent(e);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			handlePopupEvent(e);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			handlePopupEvent(e);
		}

		/**
		 * @param e
		 */
		private void handlePopupEvent(MouseEvent e) {
			Point p = e.getPoint();
			if (e.isPopupTrigger()) {
				// show context menu
				menu.show(e.getComponent(), p.x, p.y);
			}
		}

		/**
		 * @param e
		 */
		private void handleEvent(MouseEvent e) {

			Point p = e.getPoint();
			ActivityView view = currentDateAreaBean.getDateArea()
					.getActivityViewAt(p.x, p.y);

			if (view != null) {
				// retrieve new selection
				selectedInternalActivitiy = ((ActivityView) view).getModel();

				// remember selected activity
				selectedActivity = new Activity(selectedInternalActivitiy);

				// notify all listeners
				fireSelectionChanged(new IActivity[] { selectedActivity });
			} else {
				// clicked on calendar - not activity
				selectedInternalActivitiy = null;

				fireSelectionChanged(new Activity[] {});
			}

			if (e.getClickCount() == 2) {
				MutableDateRange range = currentDateAreaBean.getDateArea()
						.getDateRangeForPoint(e.getPoint(), true, true, true);
				mediator
						.fireCreateActivity(new org.columba.calendar.model.DateRange(
								range.getStart(), range.getEnd(true)));
			}
		}
	}
}