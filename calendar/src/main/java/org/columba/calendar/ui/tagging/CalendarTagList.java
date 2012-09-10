package org.columba.calendar.ui.tagging;

import java.util.Collection;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.columba.api.gui.frame.IFrameMediator;
import org.columba.calendar.ui.frame.CalendarFrameMediator;
import org.columba.core.association.AssociationStore;
import org.columba.core.gui.tagging.TagList;
import org.columba.core.tagging.api.ITag;

import com.miginfocom.calendar.activity.Activity;
import com.miginfocom.calendar.activity.ActivityDepository;
import com.miginfocom.calendar.category.Category;
import com.miginfocom.calendar.category.CategoryDepository;
import com.miginfocom.util.MigUtil;

public class CalendarTagList extends TagList {

	private static final java.util.logging.Logger LOG = java.util.logging.Logger
			.getLogger("org.columba.calendar.ui.tagging"); //$NON-NLS-1$

	public static final String PROP_FILTERED = "filterRow";

	private IFrameMediator frameMediator;

	public CalendarTagList(IFrameMediator frameMediator) {
		super();

		this.frameMediator = frameMediator;

		addListSelectionListener(new MyListSelectionListener());
	}

	class MyListSelectionListener implements ListSelectionListener {
		MyListSelectionListener() {
		}

		public void valueChanged(ListSelectionEvent event) {
			// return if selection change is in flux
			if (event.getValueIsAdjusting()) {
				return;
			}

			ITag result = (ITag) getSelectedValue();
			
			//
			// mark all activities with the tag
			// 
			
			Collection<String> messageList = AssociationStore.getInstance()
					.getAssociatedItems("tagging", result.getId());
			for (String id : messageList) {

				// example:
				// "columba://org.columba.contact/<folder-id>/<contact-id>"
				String s = id.toString();

				// TODO: @author fdietz replace with regular expression
				int activityIndex = s.lastIndexOf('/');
				String activityId = s.substring(activityIndex + 1, s.length());
				int folderIndex = s.lastIndexOf('/', activityIndex - 1);
				String folderId = s.substring(folderIndex + 1, activityIndex);
				int componentIndex = s.lastIndexOf('/', folderIndex - 1);
				String componentId = s.substring(componentIndex + 1,
						folderIndex);

				// check if its a calendar component
				if (componentId.equals("org.columba.calendar")) {
					// get calendar ui component depository
					Activity act = ActivityDepository.getInstance()
							.getActivity(activityId);
					// add tag id as last
					act.addCategoryID("tag_" + result.getId(), MigUtil.BIG_INT);
				}
			}

			//
			// now change all global categories
			// 
			
			// set all tags/categories to be *not* selected
			clearAllTagCategories();

			// retrieve tag/category to be selected
			Category category = retrieveTagCategory(result.getId());
			if (category == null) {
				System.out.println("add: " + result.getId());
				// create new one
				category = CategoryDepository.getRoot().addSubCategory(
						"tag_" + result.getId(), result.getName());
			}

                // mark it as selected
			category.setProperty(Category.PROP_IS_HIDDEN, Boolean
					.valueOf(false), Boolean.TRUE);
			System.out.println("tag: " + category.getId().toString());

			// update calendar viewer
			((CalendarFrameMediator) frameMediator).getCalendarView()
					.recreateFilterRows();

		}
	}

	private Category retrieveTagCategory(String tagId) {
		Collection<Category> c = CategoryDepository.getRoot().getChildrenDeep();
		for (Category category : c) {
			String categoryId = (String) category.getId();
			if (categoryId.equals("tag_" + tagId))
				return category;
		}

		return null;
	}

	private void clearAllTagCategories() {
		CategoryDepository.getRoot().setPropertyDeep(Category.PROP_IS_HIDDEN, Boolean
				.valueOf(true), Boolean.TRUE);
	}
}