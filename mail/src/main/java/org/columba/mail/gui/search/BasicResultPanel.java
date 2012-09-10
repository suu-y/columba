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
package org.columba.mail.gui.search;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import org.columba.core.gui.search.api.IResultPanel;
import org.columba.core.search.api.IResultEvent;
import org.columba.core.search.api.ISearchResult;
import org.columba.mail.resourceloader.IconKeys;
import org.columba.mail.resourceloader.MailImageLoader;

public class BasicResultPanel implements IResultPanel {

	private ResourceBundle bundle;

	private String providerTechnicalName;

	private String criteriaTechnicalName;

	private ResultList list;

	public BasicResultPanel(String providerTechnicalName,
			String criteriaTechnicalName) {
		super();

		this.criteriaTechnicalName = criteriaTechnicalName;
		this.providerTechnicalName = providerTechnicalName;

		bundle = ResourceBundle.getBundle("org.columba.mail.i18n.search");

		list = new ResultList();

	}

	public String getSearchCriteriaTechnicalName() {
		return criteriaTechnicalName;
	}

	public String getProviderTechnicalName() {
		return providerTechnicalName;
	}

	public JComponent getView() {
		return list;
	}

	public ImageIcon getIcon() {
		return MailImageLoader.getSmallIcon(IconKeys.MESSAGE_READ);
	}

	public String getTitle(String searchTerm) {
		String result = MessageFormat.format(bundle
				.getString(criteriaTechnicalName + "_title"),
				new Object[] { searchTerm });
		return result;
	}

	public String getDescription(String searchTerm) {
		String result = MessageFormat.format(bundle
				.getString(criteriaTechnicalName + "_description"),
				new Object[] { searchTerm });
		return result;
	}

	public void resultArrived(IResultEvent event) {
		if (!event.getProviderName().equals(this.providerTechnicalName))
			return;
		if (!event.getSearchCriteria().getTechnicalName().equals(
				this.criteriaTechnicalName))
			return;

		List<ISearchResult> result = event.getSearchResults();

		Iterator<ISearchResult> it = result.iterator();
		while (it.hasNext()) {
			list.add(it.next());
		}

		// setPreferredSize(list.getPreferredSize());
		list.revalidate();
	}

	public void clearSearch(IResultEvent event) {

	}

	public void reset(IResultEvent event) {
		list.clear();
	}

	public void finished(IResultEvent event) {
	}

}
