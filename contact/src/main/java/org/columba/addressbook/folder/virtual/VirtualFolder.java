package org.columba.addressbook.folder.virtual;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.tree.TreeNode;

import org.columba.addressbook.folder.AbstractFolder;
import org.columba.addressbook.folder.IContactStorage;
import org.columba.addressbook.model.ContactModelFactory;
import org.columba.addressbook.model.IContactModel;
import org.columba.addressbook.model.IContactModelPartial;
import org.columba.api.exception.StoreException;

public class VirtualFolder extends AbstractFolder implements IContactStorage {

	private Map<String, VirtualItem> map = new Hashtable<String, VirtualItem>();

	public VirtualFolder() {
		super("name", "directory");
	}

	public String add(IContactStorage parentStore, String parentId) {
		if ( parentStore == null ) throw new IllegalArgumentException("parentStore == null");
		if ( parentId == null ) throw new IllegalArgumentException("parentId == null");
		
		VirtualItem item = new VirtualItem(parentStore, parentId);
		String uuid = UUID.randomUUID().toString();
		map.put(uuid, item);

		return uuid;
	}

	public String add(IContactModel contact) throws StoreException {
		throw new IllegalArgumentException("add() not supported");
	}

	public int count() throws StoreException {
		return map.size();
	}

	public boolean exists(String id) throws StoreException {
		if ( id == null ) throw new IllegalArgumentException("id == null");
		return map.containsKey(id);
	}
	
	/**
	 * @see org.columba.addressbook.folder.IContactStorage#getHeaderItemList()
	 * 
	 */
	@Override
	public Map<String, IContactModelPartial> getContactItemMap()
			throws StoreException {
		Map<String, IContactModelPartial> result = new Hashtable<String, IContactModelPartial>();

		Iterator<Map.Entry<String, VirtualItem>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, VirtualItem> entry = it.next();
			VirtualItem item = entry.getValue();
			IContactStorage parentStore = item.getParentStore();
			String parentId = item.getParentId();
			
			IContactModel model = parentStore.get(parentId);
			IContactModelPartial partial = ContactModelFactory
					.createContactModelPartial(model, entry.getKey());
			result.put(entry.getKey(), partial);
		}
		
		return result;
	}

	public IContactModel get(String id) throws StoreException {
		if ( id == null ) throw new IllegalArgumentException("id == null");
		
		VirtualItem item = map.get(id);
		IContactStorage parentStore = item.getParentStore();
		String parentId = item.getParentId();

		IContactModel model = parentStore.get(parentId);
		return model;
	}

	public void modify(String id, IContactModel contact) throws StoreException {
		if ( id == null ) throw new IllegalArgumentException("id == null");
		if ( contact == null ) throw new IllegalArgumentException("contact == null");
		
		VirtualItem item = map.get(id);
		IContactStorage parentStore = item.getParentStore();
		String parentId = item.getParentId();

		parentStore.modify(parentId, contact);
	}

	public void remove(String id) throws StoreException {
		if ( id == null ) throw new IllegalArgumentException("id == null");
		
		VirtualItem item = map.get(id);
		IContactStorage parentStore = item.getParentStore();
		String parentId = item.getParentId();

		parentStore.remove(parentId);
	}

	public ImageIcon getIcon() {
		return null;
	}

	public String getId() {
		return "id";
	}

	public String getName() {
		return "vfolder";
	}

	public Enumeration children() {
		return new Vector().elements();
	}

	public boolean getAllowsChildren() {
		return false;
	}

	public TreeNode getChildAt(int childIndex) {
		return null;
	}

	public int getChildCount() {
		return 0;
	}

	public int getIndex(TreeNode node) {
		return 0;
	}

	public TreeNode getParent() {
		return null;
	}

	public boolean isLeaf() {
		return false;
	}

}
