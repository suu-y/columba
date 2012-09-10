package org.columba.calendar.ui.tagging;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Iterator;

import org.columba.api.gui.frame.IFrameMediator;
import org.columba.calendar.base.api.IActivity;
import org.columba.calendar.resourceloader.ResourceLoader;
import org.columba.calendar.ui.calendar.api.ActivitySelectionChangedEvent;
import org.columba.calendar.ui.calendar.api.IActivitySelectionChangedListener;
import org.columba.calendar.ui.frame.CalendarFrameMediator;
import org.columba.core.association.AssociationStore;
import org.columba.core.association.api.IAssociation;
import org.columba.core.gui.tagging.TaggingMenu;
import org.columba.core.tagging.TagManager;
import org.columba.core.tagging.api.ITag;

public class TagCalendarMenu extends TaggingMenu implements
		IActivitySelectionChangedListener {

	final static private String SERVICE_ID = "tagging";

	private String calendarId;

	/**
	 * @param frameMediator
	 */
	public TagCalendarMenu(IFrameMediator frameMediator) {
		super(frameMediator, ResourceLoader.getString("tagging",
				"calendar_tag_message"), "menu_tag_message");

		((CalendarFrameMediator) frameMediator).getCalendarView()
				.addSelectionChangedListener(this);

	}

	public void selectionChanged(ActivitySelectionChangedEvent object) {
		if (object.getSelection() != null && object.getSelection().length > 0) {
			setEnabled(true);
			removeAll();
			createSubMenu();
		} else
			setEnabled(false);
	}

	@Override
	protected void assignTag(String tagId) {
		IActivity activity = ((CalendarFrameMediator) getFrameMediator())
				.getCalendarView().getSelectedActivity();
		String calendarId = activity.getCalendarId();
		String activityId = activity.getId();

		AssociationStore.getInstance().addAssociation(SERVICE_ID, tagId,
				createURI(calendarId, activityId).toString());

	}

	@Override
	protected boolean isTagged(ITag tag) {
		IActivity activity = ((CalendarFrameMediator) getFrameMediator())
				.getCalendarView().getSelectedActivity();
		if (activity == null)
			return false;

		String calendarId = activity.getCalendarId();
		String activityId = activity.getId();

		boolean tagged = checkAssocation(calendarId, activityId, tag);
		return tagged;
	}

	@Override
	protected void removeAllTags() {
		IActivity activity = ((CalendarFrameMediator) getFrameMediator())
				.getCalendarView().getSelectedActivity();
		String calendarId = activity.getCalendarId();
		String activityId = activity.getId();

		for (Iterator<ITag> iter = TagManager.getInstance().getAllTags(); iter
				.hasNext();) {
			ITag tag = iter.next();

			AssociationStore.getInstance().removeAssociation(SERVICE_ID,
					tag.getId(), createURI(calendarId, activityId).toString());

		}
	}

	@Override
	protected void removeTag(String tagId) {
		IActivity activity = ((CalendarFrameMediator) getFrameMediator())
				.getCalendarView().getSelectedActivity();
		String calendarId = activity.getCalendarId();
		String activityId = activity.getId();

		AssociationStore.getInstance().removeAssociation(SERVICE_ID, tagId,
				createURI(calendarId, activityId).toString());

	}

	// create URI representing the contact
	public static URI createURI(String folderId, Object contactId) {
		URI uri = null;
		try {
			uri = new URI("columba://org.columba.calendar/" + folderId + "/"
					+ contactId);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return uri;
	}

	// check if calendar item is tagged
	private boolean checkAssocation(String folderId, String eventId, ITag tag) {
		URI uri = createURI(folderId, eventId);
		if (AssociationStore.getInstance() == null)
			System.out.println("ASSOCIATION STORE IS NULL!");
		if (uri == null)
			System.out.println("URI IS NULL!");
		
		Collection<IAssociation> c = AssociationStore.getInstance()
				.getAllAssociations(uri.toString());

		for (IAssociation as : c) {
			if (as.getServiceId().equals(SERVICE_ID)
					&& (as.getMetaDataId() != null)
					&& (as.getMetaDataId().equals(tag.getId()))) {
				return true;
			}
		}
		return false;
	}

}
