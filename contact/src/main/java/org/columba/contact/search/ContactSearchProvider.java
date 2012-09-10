package org.columba.contact.search;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.ImageIcon;

import org.columba.addressbook.folder.AddressbookFolder;
import org.columba.addressbook.folder.virtual.VirtualFolder;
import org.columba.addressbook.gui.frame.AddressbookFrameMediator;
import org.columba.addressbook.gui.search.BasicResultPanel;
import org.columba.addressbook.gui.search.ComplexResultPanel;
import org.columba.addressbook.model.IContactModel;
import org.columba.api.gui.frame.IFrameMediator;
import org.columba.api.plugin.PluginLoadingFailedException;
import org.columba.core.gui.frame.FrameManager;
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

public class ContactSearchProvider implements ISearchProvider {
	public static final String CRITERIA_DISPLAYNAME_CONTAINS = "displayname_contains";

	public static final String CRITERIA_EMAIL_CONTAINS = "email_contains";

	private ResourceBundle bundle;

	private int totalResultCount = 0;

	private VirtualFolder virtualFolder;

	public ContactSearchProvider() {
		bundle = ResourceBundle
				.getBundle("org.columba.addressbook.i18n.search");
	}

	public String getTechnicalName() {
		return "ContactSearchProvider";
	}

	public String getName() {
		return bundle.getString("provider_title");
	}

	public String getDescription() {
		return bundle.getString("provider_description");
	}

	public ImageIcon getIcon() {
		return ImageLoader.getSmallIcon(IconKeys.ADDRESSBOOK);
	}

	public List<ISearchCriteria> getAllCriteria(String searchTerm) {

		List<ISearchCriteria> list = new Vector<ISearchCriteria>();

		list.add(getCriteria(ContactSearchProvider.CRITERIA_EMAIL_CONTAINS,
				searchTerm));
		list
				.add(getCriteria(
						ContactSearchProvider.CRITERIA_DISPLAYNAME_CONTAINS,
						searchTerm));
		return list;
	}

	public IResultPanel getResultPanel(String searchCriteriaTechnicalName) {
		return new BasicResultPanel(getTechnicalName(),
				searchCriteriaTechnicalName);
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

	public List<ISearchResult> query(String searchTerm,
			String criteriaTechnicalName, boolean searchInside, int startIndex,
			int resultCount) {
		if (searchTerm == null)
			throw new IllegalArgumentException("searchTerm == null");
		if (criteriaTechnicalName == null)
			throw new IllegalArgumentException("criteriaTechnicalName == null");

		List<ISearchResult> result = new Vector<ISearchResult>();

		// create list of contact folders
		List<AddressbookFolder> v = SearchUtility.createContactFolderList();

		virtualFolder = new VirtualFolder();
		Iterator<AddressbookFolder> it = v.iterator();
		while (it.hasNext()) {
			AddressbookFolder f = it.next();
			String id = null;

			if (criteriaTechnicalName
					.equals(ContactSearchProvider.CRITERIA_DISPLAYNAME_CONTAINS)) {
				id = f.findByName(searchTerm);
			} else if (criteriaTechnicalName
					.equals(ContactSearchProvider.CRITERIA_EMAIL_CONTAINS)) {
				id = f.findByEmailAddress(searchTerm);
			}

			if (id != null) {
				IContactModel model = f.get(id);

				result.add(new ContactSearchResult(model.getSortString(), model
						.getPreferredEmail(), SearchResultBuilder.createURI(f
						.getId(), id), model));

				// remember search result
				virtualFolder.add(f, id);
			}

		}

		totalResultCount = result.size();

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

	public int getTotalResultCount() {
		return totalResultCount;
	}

	public void showAllResults(IFrameMediator mediator, String searchTerm,
			String searchCriteriaTechnicalName) {

		// ensure that we are currently in the contact component
		IFrameMediator newMediator = null;
		try {
			newMediator = FrameManager.getInstance().switchView(
					mediator.getContainer(), "Addressbook");
		} catch (PluginLoadingFailedException e) {
			e.printStackTrace();
		}

		// update folder selection
		((AddressbookFrameMediator) newMediator).getTree().setSelectedFolder(
				virtualFolder);
	}

	public ICriteriaRenderer getCriteriaRenderer(String criteriaTechnicalName) {
		ICriteriaRenderer r = null;
		if (criteriaTechnicalName
				.equals(ContactSearchProvider.CRITERIA_DISPLAYNAME_CONTAINS)) {
			r = new StringCriteriaRenderer(getCriteria(
					ContactSearchProvider.CRITERIA_DISPLAYNAME_CONTAINS, ""),
					this);
		} else if (criteriaTechnicalName
				.equals(ContactSearchProvider.CRITERIA_EMAIL_CONTAINS)) {
			r = new StringCriteriaRenderer(getCriteria(
					ContactSearchProvider.CRITERIA_EMAIL_CONTAINS, ""), this);
		}

		return r;
	}

	public ISearchCriteria getDefaultCriteria(String searchTerm) {
		return getCriteria(ContactSearchProvider.CRITERIA_EMAIL_CONTAINS,
				searchTerm);
	}

	public boolean hasSingleCriteriaSearchResult() {
		// doesn't support search inside yet
		return false;
	}

}
