package org.columba.contact.search;

import java.net.URI;

import org.columba.addressbook.model.IContactModel;
import org.columba.core.search.SearchResult;
import org.columba.core.search.api.ISearchResult;

public class ContactSearchResult extends SearchResult implements ISearchResult {

	private IContactModel model;

	public ContactSearchResult(String title, String description, URI location,
			IContactModel model) {
		super(title, description, location);
		this.model = model;
	}

	public IContactModel getModel() {
		return model;
	}
	
	public boolean equals(Object o) {
		ContactSearchResult r = (ContactSearchResult) o;
		
		return getLocation().equals(r.getLocation());
	}

}
