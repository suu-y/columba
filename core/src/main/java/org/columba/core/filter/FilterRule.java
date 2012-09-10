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
//All Rights Reserved.
package org.columba.core.filter;

import org.columba.core.config.DefaultItem;
import org.columba.core.xml.XmlElement;

public class FilterRule extends DefaultItem implements IFilterRule {
	// condition: match all (AND) = 0, match any (OR) = 1
	// private AdapterNode conditionNode;
	public FilterRule(XmlElement root) {
		super(root);

	}

	public FilterRule() {
		super(new XmlElement());

	}

	/* (non-Javadoc)
   * @see org.columba.core.filter.IFilterRule#addEmptyCriteria()
   */
	public IFilterCriteria addEmptyCriteria() {
		XmlElement criteria = new XmlElement("criteria");
		criteria.addAttribute("type", "Subject");
		criteria.addAttribute("criteria", "contains");
		criteria.addAttribute("pattern", "pattern");

		getRoot().addElement(criteria);

		IFilterCriteria c = new FilterCriteria(criteria);

		return c;
	}

	/* (non-Javadoc)
   * @see org.columba.core.filter.IFilterRule#add(org.columba.core.filter.FilterCriteria)
   */
	public void add(IFilterCriteria criteria) {
		getRoot().addElement(criteria.getRoot());
	}

	/* (non-Javadoc)
   * @see org.columba.core.filter.IFilterRule#remove(int)
   */
	public void remove(int index) {
		getRoot().removeElement(index);

		/*
		 * if ((index >= 0) && (index < list.size())) { list.remove(index);
		 * 
		 * int result = -1;
		 * 
		 * for (int i = 0; i < getRootNode().getChildCount(); i++) { AdapterNode
		 * child = (AdapterNode) getRootNode().getChildAt(i); String name =
		 * child.getName();
		 * 
		 * if (name.equals("filtercriteria")) result++;
		 * 
		 * if (result == index) { child.remove(); break; } }
		 * 
		 * //AdapterNode child = getRootNode().getChildAt(index); }
		 */
	}

	/* (non-Javadoc)
   * @see org.columba.core.filter.IFilterRule#removeAll()
   */
	public void removeAll() {
		/*
		 * for (int i = 0; i < count(); i++) { remove(0); }
		 */
		getRoot().removeAllElements();
	}

	/* (non-Javadoc)
   * @see org.columba.core.filter.IFilterRule#removeLast()
   */
	public void removeLast() {
		/*
		 * int index = list.size() - 1;
		 * 
		 * remove(index);
		 */
		getRoot().removeElement(getRoot().count() - 1);
	}

	/* (non-Javadoc)
   * @see org.columba.core.filter.IFilterRule#get(int)
   */
	public IFilterCriteria get(int index) {
		return new FilterCriteria(getRoot().getElement(index));
	}

	/* (non-Javadoc)
   * @see org.columba.core.filter.IFilterRule#count()
   */
	public int count() {
		return getRoot().count();
	}

	/* (non-Javadoc)
   * @see org.columba.core.filter.IFilterRule#getCondition()
   */
	public String getCondition() {
		return getRoot().getAttribute("condition");

		/*
		 * if (conditionNode == null) { System.out.println(
		 * "---------------------------> failure: conditionNode == null !");
		 * 
		 * return new String("matchany"); } else return
		 * getTextValue(conditionNode);
		 */
	}

	/* (non-Javadoc)
   * @see org.columba.core.filter.IFilterRule#setCondition(java.lang.String)
   */
	public void setCondition(String s) {
		getRoot().addAttribute("condition", s);

		/*
		 * setTextValue(conditionNode, s);
		 */
	}

	/* (non-Javadoc)
   * @see org.columba.core.filter.IFilterRule#setCondition(int)
   */
	public void setCondition(int condition) {
		if (condition == MATCH_ALL)
			setCondition("matchall");
		else if (condition == MATCH_ANY)
			setCondition("matchany");
		else
			throw new IllegalArgumentException("condition <" + condition
					+ "> unknown");
	}

	/*
	 * public FilterCriteria getCriteria(int index) { return (FilterCriteria)
	 * list.get(index); }
	 */
	/* (non-Javadoc)
   * @see org.columba.core.filter.IFilterRule#getConditionInt()
   */
	public int getConditionInt() {
		// System.out.println("condigtion: "+ condition );
		if (getCondition().equals("matchall")) {
			return MATCH_ALL;
		}

		if (getCondition().equals("matchany")) {
			return MATCH_ANY;
		}

		return -1;
	}
}
