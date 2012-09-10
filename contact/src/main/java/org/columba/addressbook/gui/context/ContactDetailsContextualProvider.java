package org.columba.addressbook.gui.context;

import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.columba.addressbook.model.IContactModel;
import org.columba.api.gui.frame.IFrameMediator;
import org.columba.contact.search.ContactSearchProvider;
import org.columba.contact.search.ContactSearchResult;
import org.columba.core.context.api.IContextProvider;
import org.columba.core.context.base.api.IStructureValue;
import org.columba.core.context.semantic.api.ISemanticContext;
import org.columba.core.resourceloader.IconKeys;
import org.columba.core.resourceloader.ImageLoader;
import org.columba.core.search.api.ISearchResult;
import org.jdesktop.swingx.VerticalLayout;

// TODO: fix layout to look good, even if there's not enough information provided
public class ContactDetailsContextualProvider implements IContextProvider {

	private ResourceBundle bundle;

	private ContactSearchProvider searchProvider;

	private List<ISearchResult> result;

	private JPanel panel = new JPanel();

	public ContactDetailsContextualProvider() {
		super();

		panel.setLayout(new VerticalLayout());

		bundle = ResourceBundle
				.getBundle("org.columba.addressbook.i18n.search");

		result = new Vector<ISearchResult>();

		searchProvider = new ContactSearchProvider();
	}

	public String getTechnicalName() {
		return "contact_details_list";
	}

	public String getName() {
		return bundle.getString("provider_related_title");
	}

	public String getDescription() {
		return bundle.getString("provider_related_title");
	}

	public ImageIcon getIcon() {
		return ImageLoader.getSmallIcon(IconKeys.ADDRESSBOOK);
	}

	public int getTotalResultCount() {
		return searchProvider.getTotalResultCount();
	}

	public void search(ISemanticContext context, int startIndex, int resultCount) {

		IStructureValue value = context.getValue();
		if (value == null)
			return;

		Iterator<IStructureValue> it = value.getChildIterator(
				ISemanticContext.CONTEXT_NODE_IDENTITY,
				ISemanticContext.CONTEXT_NAMESPACE_CORE);
		if (it.hasNext()) {
			// can be only one
			IStructureValue identity = it.next();
			if (identity == null)
				return;

			String emailAddress = identity.getString(
					ISemanticContext.CONTEXT_ATTR_EMAIL_ADDRESS,
					ISemanticContext.CONTEXT_NAMESPACE_CORE);
			String displayname = identity.getString(
					ISemanticContext.CONTEXT_ATTR_DISPLAY_NAME,
					ISemanticContext.CONTEXT_NAMESPACE_CORE);

			if (emailAddress == null && displayname == null)
				return;

			List<ISearchResult> temp;

			if (emailAddress != null) {
				temp = searchProvider.query(emailAddress,
						ContactSearchProvider.CRITERIA_EMAIL_CONTAINS, false,
						0, 5);
				result.addAll(temp);
			}

			if (displayname != null) {
				temp = searchProvider.query(displayname,
						ContactSearchProvider.CRITERIA_DISPLAYNAME_CONTAINS,
						false, 0, 5);
				result.addAll(temp);
			}
		}

	}

	public void showResult() {
		panel.removeAll();

		Iterator it2 = result.listIterator();
		while (it2.hasNext()) {
			ContactSearchResult r = (ContactSearchResult) it2.next();
			IContactModel m = r.getModel();

			ContactDetailPanel p = new ContactDetailPanel(m, r);

			panel.add(p);
		}

		panel.revalidate();
		panel.validate();
	}

	public JComponent getView() {
		return panel;
	}

	public void clear() {

		result.clear();
	}

	public boolean isEnabledShowMoreLink() {
		return false;
	}

	public void showMoreResults(IFrameMediator mediator) {
	}

}
