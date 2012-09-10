package org.columba.calendar.search;

import java.net.URI;

import org.columba.calendar.model.api.IComponent;
import org.columba.core.search.SearchResult;
import org.columba.core.search.api.ISearchResult;

public class CalendarSearchResult extends SearchResult implements ISearchResult {

	private IComponent model;
	
	public CalendarSearchResult(String title, String description, URI location,
			IComponent model) {
		super(title, description, location);
		this.model = model;
	}

	public IComponent getModel() {
		return model;
	}
	
}
