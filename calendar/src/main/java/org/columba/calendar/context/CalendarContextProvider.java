package org.columba.calendar.context;

import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import org.columba.api.gui.frame.IFrameMediator;
import org.columba.calendar.search.CalendarSearchProvider;
import org.columba.calendar.ui.search.SearchResultList;
import org.columba.core.context.api.IContextProvider;
import org.columba.core.context.base.api.IStructureValue;
import org.columba.core.context.semantic.api.ISemanticContext;
import org.columba.core.resourceloader.IconKeys;
import org.columba.core.resourceloader.ImageLoader;
import org.columba.core.search.api.ISearchResult;

public class CalendarContextProvider implements IContextProvider {
	private ResourceBundle bundle;

	private List<ISearchResult> result;

	private SearchResultList resultList;

	private CalendarSearchProvider searchProvider;

	public CalendarContextProvider() {
		resultList = new SearchResultList();

		bundle = ResourceBundle.getBundle("org.columba.calendar.i18n.search");

		result = new Vector<ISearchResult>();

		searchProvider = new CalendarSearchProvider();
	}

	public void clear() {
		resultList.clear();
		result.clear();
	}

	public String getTechnicalName() {
		return "calendar_list";
	}

	public String getName() {
		return bundle.getString("provider_related_title");
	}

	public String getDescription() {
		return bundle.getString("provider_related_title");
	}

	public ImageIcon getIcon() {
		return ImageLoader.getSmallIcon(IconKeys.CALENDAR);
	}

	public int getTotalResultCount() {
		return searchProvider.getTotalResultCount();
	}

	public JComponent getView() {
		return resultList;
	}

	public boolean isEnabledShowMoreLink() {
		return false;
	}

	public void search(ISemanticContext context, int startIndex, int resultCount) {
		IStructureValue value = context.getValue();
		if (value == null)
			return;

		Iterator<IStructureValue> it = value.getChildIterator(
				ISemanticContext.CONTEXT_NODE_MESSAGE,
				ISemanticContext.CONTEXT_NAMESPACE_CORE);

		if (it.hasNext()) {
			// we only use the first message
			IStructureValue message = it.next();

			// retrieve subject
			String subject = message.getString(
					ISemanticContext.CONTEXT_ATTR_SUBJECT,
					ISemanticContext.CONTEXT_NAMESPACE_CORE);

			if (subject != null) {
				List<ISearchResult> temp = searchProvider.query(subject,
						CalendarSearchProvider.CRITERIA_SUMMARY_CONTAINS,
						false, 0, 5);
				result.addAll(temp);
			}
		}

	}

	public void showMoreResults(IFrameMediator mediator) {
		throw new IllegalArgumentException("not implemented yet");
	}

	public void showResult() {
		resultList.addAll(result);
	}
}
