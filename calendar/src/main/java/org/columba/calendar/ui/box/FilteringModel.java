package org.columba.calendar.ui.box;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.columba.calendar.model.api.IEventInfo;

public class FilteringModel extends AbstractListModel implements
		DocumentListener {
	List<IEventInfo> list;

	List<IEventInfo> filteredList;

	String lastFilter = "";

	public FilteringModel() {
		list = new ArrayList<IEventInfo>();
		filteredList = new ArrayList<IEventInfo>();
	}

	public void addElement(IEventInfo element) {
		list.add(element);
		filter(lastFilter);
	}

	public int getSize() {
		return filteredList.size();
	}

	public Object getElementAt(int index) {
		Object returnValue;
		if (index < filteredList.size()) {
			returnValue = filteredList.get(index);
		} else {
			returnValue = null;
		}
		return returnValue;
	}

	void filter(String search) {
		filteredList.clear();
		for (IEventInfo element : list) {
			String s = search.toLowerCase();
			if (element.getEvent().getSummary().toLowerCase().indexOf(s) != -1)
				filteredList.add(element);
		}
		fireContentsChanged(this, 0, getSize());
	}

	// DocumentListener Methods

	public void insertUpdate(DocumentEvent event) {
		Document doc = event.getDocument();
		try {
			lastFilter = doc.getText(0, doc.getLength());
			filter(lastFilter);
		} catch (BadLocationException ble) {
			System.err.println("Bad location: " + ble);
		}
	}

	public void removeUpdate(DocumentEvent event) {
		Document doc = event.getDocument();
		try {
			lastFilter = doc.getText(0, doc.getLength());
			filter(lastFilter);
		} catch (BadLocationException ble) {
			System.err.println("Bad location: " + ble);
		}
	}

	public void changedUpdate(DocumentEvent event) {
	}
}
