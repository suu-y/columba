package org.columba.calendar.search;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.ImageIcon;

import org.columba.api.gui.frame.IFrameMediator;
import org.columba.calendar.config.CalendarList;
import org.columba.calendar.model.api.IComponent;
import org.columba.calendar.model.api.IComponentInfo;
import org.columba.calendar.model.api.IEventInfo;
import org.columba.calendar.model.api.ITodoInfo;
import org.columba.calendar.store.api.ICalendarStore;
import org.columba.calendar.ui.search.BasicResultPanel;
import org.columba.core.gui.search.StringCriteriaRenderer;
import org.columba.core.gui.search.api.ICriteriaRenderer;
import org.columba.core.gui.search.api.IResultPanel;
import org.columba.core.resourceloader.IconKeys;
import org.columba.core.resourceloader.ImageLoader;
import org.columba.core.search.SearchCriteria;
import org.columba.core.search.api.ISearchCriteria;
import org.columba.core.search.api.ISearchProvider;
import org.columba.core.search.api.ISearchRequest;
import org.columba.core.search.api.ISearchResult;

public class CalendarSearchProvider implements ISearchProvider {

	public static final String CRITERIA_SUMMARY_CONTAINS = "summary_contains";

	private ResourceBundle bundle;

	private int totalResultCount = 0;

	public CalendarSearchProvider() {
		bundle = ResourceBundle.getBundle("org.columba.calendar.i18n.search");
	}

	public String getTechnicalName() {
		return "CalendarSearchProvider";
	}

	public String getName() {
		return bundle.getString("provider_title");
	}

	public String getDescription() {
		return bundle.getString("provider_description");
	}

	public ImageIcon getIcon() {
		return ImageLoader.getSmallIcon(IconKeys.CALENDAR);
	}

	public List<ISearchCriteria> getAllCriteria(String searchTerm) {
		List<ISearchCriteria> list = new Vector<ISearchCriteria>();

		list.add(getCriteria(CalendarSearchProvider.CRITERIA_SUMMARY_CONTAINS,
				searchTerm));

		return list;
	}

	public IResultPanel getComplexResultPanel() {
		return new ComplexResultPanel(getTechnicalName());
	}

	public ISearchCriteria getCriteria(String technicalName, String searchTerm) {
		String title = MessageFormat.format(bundle.getString(technicalName
				+ "_title"), new Object[] { searchTerm });
		String name = bundle.getString(technicalName + "_name");
		String description = MessageFormat.format(bundle
				.getString(technicalName + "_description"),
				new Object[] { searchTerm });

		return new SearchCriteria(technicalName, name, title, description);
	}

	public ICriteriaRenderer getCriteriaRenderer(String criteriaTechnicalName) {
		ICriteriaRenderer r = null;
		if (criteriaTechnicalName
				.equals(CalendarSearchProvider.CRITERIA_SUMMARY_CONTAINS)) {
			r = new StringCriteriaRenderer(getCriteria(
					CalendarSearchProvider.CRITERIA_SUMMARY_CONTAINS, ""), this);
		}

		return r;
	}

	public ISearchCriteria getDefaultCriteria(String searchTerm) {
		return getCriteria(CalendarSearchProvider.CRITERIA_SUMMARY_CONTAINS,
				searchTerm);
	}

	public IResultPanel getResultPanel(String searchCriteriaTechnicalName) {
		return new BasicResultPanel(getTechnicalName(),
				searchCriteriaTechnicalName);
	}

	public int getTotalResultCount() {
		return totalResultCount;
	}

	public boolean hasSingleCriteriaSearchResult() {
		throw new IllegalArgumentException("not implemented yet");
	}

	public List<ISearchResult> query(String searchTerm,
			String searchCriteriaTechnicalName, boolean searchInside,
			int startIndex, int resultCount) {

		if (searchTerm == null)
			throw new IllegalArgumentException("searchTerm == null");
		if (searchCriteriaTechnicalName == null)
			throw new IllegalArgumentException(
					"searchCriteriaTechnicalName == null");

		List<ISearchResult> result = new Vector<ISearchResult>();

		Iterator<ICalendarStore> itstore = CalendarList.getInstance().getStores();
		while (itstore.hasNext()) {
			ICalendarStore store = itstore.next();

			if (searchCriteriaTechnicalName
					.equals(CalendarSearchProvider.CRITERIA_SUMMARY_CONTAINS)) {
	
				Iterator<String> it = store.findBySummary(searchTerm);
				while (it.hasNext()) {
					String id = it.next();
					IComponentInfo c = store.get(id);
	
					if (c.getType().equals(IComponent.TYPE.EVENT)) {
						IEventInfo event = (IEventInfo) c;
	
						result.add(new CalendarSearchResult(event.getEvent().getSummary(),
								event.getEvent().getLocation(), SearchResultBuilder.createURI(
										event.getCalendar(), event.getId()), c.getComponent()));
					} else if (c.getType().equals(IComponent.TYPE.TODO)) {
						ITodoInfo todo = (ITodoInfo) c;
	
						result.add(new CalendarSearchResult(todo.getTodo().getSummary(), todo
								.getTodo().getDescription(), SearchResultBuilder.createURI(
								todo.getCalendar(), todo.getId()), c.getComponent()));
					} else
						throw new IllegalArgumentException(
								"unsupported component type " + c.getType());
	
				}
			}
		}

		return result;
	}

	public List<ISearchResult> query(List<ISearchRequest> list,
			boolean matchAll, boolean searchInside, int startIndex,
			int resultCount) {
		Vector<ISearchResult> v = new Vector<ISearchResult>();
		Iterator<ISearchRequest> it = list.listIterator();
		while (it.hasNext()) {
			ISearchRequest request = it.next();

			List<ISearchResult> result = query(request.getSearchTerm(), request
					.getCriteria(), searchInside, startIndex, resultCount);

			v.addAll(result);
		}

		return v;

	}

	public void showAllResults(IFrameMediator mediator, String searchTerm,
			String searchCriteriaTechnicalName) {
		throw new IllegalArgumentException("not implemented yet");
	}

}
