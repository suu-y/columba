//The contents of this file are subject to the Mozilla Public License Version 1.1
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
//The Initial Developers of the Original Code are Frederik Dietz and Timo Stich.
//Portions created by Frederik Dietz and Timo Stich are Copyright (C) 2003.
//
//All Rights Reserved.undation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
package org.columba.core.filter;

import org.columba.core.config.DefaultItem;
import org.columba.core.xml.XmlElement;

/**
 * A list of filters.
 * 
 */
public class FilterList extends DefaultItem implements IFilterList {
	/**
	 * Creates a FilterList with the specified element as the root.
	 * 
	 * @param root
	 *            the element to use as the root.
	 */
	public FilterList(XmlElement root) {
		super(root);
	}

	/**
	 * Creates an empty filter list.
	 */
	public FilterList() {
		super(new XmlElement(FilterList.XML_NAME));
	}

	/**
	 * Returns an empty default filter.
	 * 
	 * @return an empty default Filter.
	 * @deprecated
	 */
	public static IFilter createDefaultFilter() {
		XmlElement filter = new XmlElement("filter");
		filter.addAttribute("description", "new filter");
		filter.addAttribute("enabled", "true");

		XmlElement rules = new XmlElement("rules");
		rules.addAttribute("condition", "matchall");

		XmlElement criteria = new XmlElement("criteria");
		criteria.addAttribute("type", "Subject");
		criteria.addAttribute("headerfield", "Subject");
		criteria.addAttribute("criteria", "contains");
		criteria.addAttribute("pattern", "pattern");
		rules.addElement(criteria);
		filter.addElement(rules);

		XmlElement actionList = new XmlElement("actionlist");
		XmlElement action = new XmlElement("action");

		/*
		 * action.addAttribute( "class",
		 * "org.columba.mail.filter.action.MarkMessageAsReadFilterAction");
		 */
		action.addAttribute("type", "Mark Message");
		action.addAttribute("markvariant", "read");

		actionList.addElement(action);
		filter.addElement(actionList);

		// XmlElement.printNode(getRoot(),"");
		return new Filter(filter);

		/*
		 * //AdapterNode filterListNode = getFilterListNode();
		 * 
		 * AdapterNode node =
		 * MailInterface.config.getFolderConfig().addEmptyFilterNode(
		 * getFolder().getNode() ); Filter filter = new Filter( node );
		 * 
		 * add( filter );
		 * 
		 * return filter;
		 */
	}

	/* (non-Javadoc)
   * @see org.columba.core.filter.IFilterList#add(org.columba.core.filter.Filter)
   */
	public void add(IFilter f) {
		if (f != null) {
			getRoot().addElement(f.getRoot());
		}

		// list.add(f);
	}

	/* (non-Javadoc)
   * @see org.columba.core.filter.IFilterList#addAll(org.columba.core.filter.FilterList)
   */
	public void addAll(IFilterList list) {
		int size = list.count();

		for (int i = 0; i < size; i++) {
			IFilter newFilter = list.get(i);
			add(newFilter);
		}
	}

	/* (non-Javadoc)
   * @see org.columba.core.filter.IFilterList#remove(org.columba.core.filter.Filter)
   */
	public void remove(IFilter f) {
		if (f != null) {
			getRoot().getElements().remove(f.getRoot());
		}
	}

	/* (non-Javadoc)
   * @see org.columba.core.filter.IFilterList#insert(org.columba.core.filter.Filter, int)
   */
	public void insert(IFilter filter, int index) {
		if (filter != null) {
			getRoot().insertElement(filter.getRoot(), index);
		}
	}

	/* (non-Javadoc)
   * @see org.columba.core.filter.IFilterList#moveUp(org.columba.core.filter.Filter)
   */
	public void moveUp(IFilter filter) {
		move(indexOf(filter), -1);
	}

	/* (non-Javadoc)
   * @see org.columba.core.filter.IFilterList#moveDown(org.columba.core.filter.Filter)
   */
	public void moveDown(IFilter filter) {
		move(indexOf(filter), 1);
	}

	/* (non-Javadoc)
   * @see org.columba.core.filter.IFilterList#move(org.columba.core.filter.Filter, int)
   */
	public void move(IFilter filter, int nrOfPositions) {
		move(indexOf(filter), nrOfPositions);
	}

	/* (non-Javadoc)
   * @see org.columba.core.filter.IFilterList#move(int, int)
   */
	public void move(int filterIndex, int nrOfPositions) {
		if ((filterIndex >= 0) && (filterIndex < count())) {
			XmlElement filterXML = getRoot().getElement(filterIndex);
			int newFilterIndex = filterIndex + nrOfPositions;

			if (newFilterIndex < 0) {
				newFilterIndex = 0;
			}

			getRoot().removeElement(filterIndex);

			if (newFilterIndex > count()) {
				getRoot().addElement(filterXML);
			} else {
				getRoot().insertElement(filterXML, newFilterIndex);
			}
		}
	}

	/* (non-Javadoc)
   * @see org.columba.core.filter.IFilterList#indexOf(org.columba.core.filter.Filter)
   */
	public int indexOf(IFilter filter) {
		int index = -1;

		if (filter != null) {
			int childCount = getChildCount();

			for (int i = 0; (index == -1) && (i < childCount); i++) {
				if (getRoot().getElement(i).equals(filter.getRoot())) {
					index = i;
				}
			}
		}

		return index;
	}

	/* (non-Javadoc)
   * @see org.columba.core.filter.IFilterList#count()
   */
	public int count() {
		return getChildCount();
	}

	/* (non-Javadoc)
   * @see org.columba.core.filter.IFilterList#get(int)
   */
	public IFilter get(int index) {
		Filter filter = new Filter(getRoot().getElement(index));

		return filter;
	}

	/* (non-Javadoc)
   * @see org.columba.core.filter.IFilterList#remove(int)
   */
	public void remove(int index) {
		getRoot().removeElement(index);
	}
}
