package org.columba.calendar.search;

import java.net.URI;
import java.net.URISyntaxException;

public class SearchResultBuilder {


	public static URI createURI(String folderId, String activityId) {
		URI uri=null;
		try {
			uri = new URI("columba://org.columba.calendar/"+folderId+"/"+activityId);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return uri;
	}

}
