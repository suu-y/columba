package org.columba.calendar.store;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.columba.addressbook.facade.IFolderFacade;
import org.columba.addressbook.folder.FolderListener;
import org.columba.addressbook.folder.IContactFolder;
import org.columba.addressbook.folder.IFolderEvent;
import org.columba.addressbook.gui.tree.AddressbookTreeModel;
import org.columba.addressbook.model.IContactModel;
import org.columba.addressbook.model.IContactModelPartial;
import org.columba.api.exception.ServiceNotFoundException;
import org.columba.calendar.base.UUIDGenerator;
import org.columba.calendar.model.ComponentInfoList;
import org.columba.calendar.model.Event;
import org.columba.calendar.model.EventInfo;
import org.columba.calendar.model.Recurrence;
import org.columba.calendar.model.api.IComponentInfo;
import org.columba.calendar.model.api.IComponentInfoList;
import org.columba.calendar.model.api.IEvent;
import org.columba.calendar.model.api.IEventInfo;
import org.columba.calendar.model.api.IRecurrence;
import org.columba.calendar.store.api.ICalendarStore;
import org.columba.calendar.store.api.StoreException;
import org.columba.calendar.ui.base.CalendarHelper;
import org.columba.core.facade.ServiceFacadeRegistry;

import com.miginfocom.calendar.activity.Activity;

public class BirthdayCalendarStore extends AbstractCalendarStore implements
		ICalendarStore, FolderListener {

	Map<String, IEventInfo> map;
	Map<String, String> contactEventMap;
	IContactFolder folder;

	String id;

	public BirthdayCalendarStore(String id) {
		this.id = id;

		map = new HashMap<String, IEventInfo>();
		contactEventMap = new HashMap<String, String>();

		try {
			IFolderFacade folderFacade = (IFolderFacade)ServiceFacadeRegistry.getInstance().getService(
					IFolderFacade.class);

			folder = (IContactFolder)AddressbookTreeModel.getInstance().getFolder(
					folderFacade.getLocalAddressbook().getId());
			folder.addFolderListener(this);

			List <IContactModelPartial> contacts = folder.getHeaderItemList();
			for (Iterator<IContactModelPartial> iterator = contacts.iterator(); iterator.hasNext();) {
				IContactModelPartial contact = iterator.next();
				IContactModel model = folder.get(contact.getId());

				createEventInfo(model);
			}
		} catch (ServiceNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected IEventInfo createEventInfo(IContactModel contact) {
		if (contact.getBirthday() == null)
			return null;

		Calendar bdate = Calendar.getInstance();
		bdate.setTime(contact.getBirthday());

		Calendar dtStart = (Calendar)bdate.clone();
		dtStart.set(Calendar.HOUR_OF_DAY, 0);
		Calendar dtEnd = (Calendar)dtStart.clone();
		dtEnd.add(Calendar.DATE, 1);

		IEvent model = new Event(dtStart, dtEnd, contact.getFormattedName());
		model.setDescription("");
		model.setAllDayEvent(true);
		Recurrence recurrence = new Recurrence(IRecurrence.RECURRENCE_ANNUALLY);
		recurrence.setInterval(1);
		model.setRecurrence(recurrence);

		IEventInfo eventInfo = new EventInfo(new UUIDGenerator().newUUID(), id, model);
		map.put(eventInfo.getId(), eventInfo);
		contactEventMap.put(contact.getId(), eventInfo.getId());

		return eventInfo;
	}

	public String getId() {
		return id;
	}

	public void add(IComponentInfo basicModel) throws StoreException {
		throw new StoreException("read only");
	}

	public boolean exists(Object id) throws StoreException {
		return map.containsKey(id);
	}

	public IComponentInfo get(Object id) throws StoreException {
		return map.get(id);
	}

	public void modify(Object id, IComponentInfo basicModel) throws StoreException {
		throw new StoreException("read only");
	}

	public IComponentInfoList getComponentInfoList() throws StoreException {
		ComponentInfoList list = new ComponentInfoList();
		Iterator<IEventInfo> it = map.values().iterator();
		while(it.hasNext()) {
			list.add(it.next());
		}
		return list;
	}

	public IComponentInfoList getComponentInfoList(String calendarId)
			throws StoreException {
		ComponentInfoList list = new ComponentInfoList();
		Iterator<IEventInfo> it = map.values().iterator();
		while(it.hasNext()) {
			list.add(it.next());
		}
		return list;
	}

	public Iterator<String> getIdIterator() throws StoreException {
		return map.keySet().iterator();
	}

	public Iterator<String> getIdIterator(String calendarId)
			throws StoreException {
		return map.keySet().iterator();
	}

	public boolean isReadOnly(String uid) throws StoreException {
		return true;
	}

	public void itemAdded(IFolderEvent e) {
		String uid = (String)e.getChanges();
		IContactModel model = folder.get(uid);
		IEventInfo eventInfo = createEventInfo(model);
		if (eventInfo != null)
			fireItemAdded(eventInfo.getId());
	}

	public void itemChanged(IFolderEvent e) {
		String uid = (String)e.getChanges();

		String oldEventUid = contactEventMap.get(uid);
		if (oldEventUid != null) {
			IEventInfo eventInfo = map.get(oldEventUid);
			Activity act = CalendarHelper
					.createActivity((IEventInfo) eventInfo, this);
			map.remove(oldEventUid);
			contactEventMap.remove(uid);
			fireItemRemoved(act.getID());
		}

		IContactModel model = folder.get(uid);
		IEventInfo eventInfo = createEventInfo(model);
		if (eventInfo != null)
			fireItemAdded(eventInfo.getId());
	}

	public void itemRemoved(IFolderEvent e) {
		String uid = (String)e.getChanges();

		String oldEventUid = contactEventMap.get(uid);
		if (oldEventUid != null) {
			IEventInfo eventInfo = map.get(oldEventUid);
			Activity act = CalendarHelper
					.createActivity((IEventInfo) eventInfo, this);
			map.remove(oldEventUid);
			contactEventMap.remove(uid);
			fireItemRemoved(act.getID());
		}
	}

}
