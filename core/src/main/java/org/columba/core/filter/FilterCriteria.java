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
package org.columba.core.filter;

import org.columba.core.config.DefaultItem;
import org.columba.core.xml.XmlElement;

public class FilterCriteria extends DefaultItem implements IFilterCriteria {

	// Condition
	private static final String ELEMENT = "criteria";

	private static final String CRITERIA = "criteria";

	private static final String TYPE = "type";

	private static final String PATTERN = "pattern";

	private final String[] criteria = { "contains", "contains not", "is",
			"is not", "begins with", "ends with", "before", "after", "smaller",
			"bigger" };

	public FilterCriteria() {
		super(new XmlElement(FilterCriteria.ELEMENT));
	}

	public FilterCriteria(XmlElement root) {
		super(root);
	}

	/* (non-Javadoc)
   * @see org.columba.core.filter.IFilterCriteria#getCriteriaString()
   */
	public String getCriteriaString() {
		return getRoot().getAttribute(FilterCriteria.CRITERIA);
	}

	/* (non-Javadoc)
   * @see org.columba.core.filter.IFilterCriteria#setCriteria(int)
   */
	public void setCriteria(int c) {
		setCriteriaString(criteria[c]);
	}

	/* (non-Javadoc)
   * @see org.columba.core.filter.IFilterCriteria#getCriteria()
   */
	public int getCriteria() {
		String condition = getCriteriaString();

		int c = -1;

		for (int i = 0; i < criteria.length; i++) {
			if (criteria[i].equals(condition))
				c = i;
		}

		return c;
	}

	/* (non-Javadoc)
   * @see org.columba.core.filter.IFilterCriteria#setCriteriaString(java.lang.String)
   */
	public void setCriteriaString(String s) {
		getRoot().addAttribute(FilterCriteria.CRITERIA, s);
	}

	/* (non-Javadoc)
   * @see org.columba.core.filter.IFilterCriteria#getTypeString()
   */
	public String getTypeString() {
		return getRoot().getAttribute(FilterCriteria.TYPE);
	}

	/* (non-Javadoc)
   * @see org.columba.core.filter.IFilterCriteria#setTypeString(java.lang.String)
   */
	public void setTypeString(String s) {
		getRoot().addAttribute(FilterCriteria.TYPE, s);
	}

	/* (non-Javadoc)
   * @see org.columba.core.filter.IFilterCriteria#getPatternString()
   */
	public String getPatternString() {
		return getRoot().getAttribute(FilterCriteria.PATTERN);
	}

	/* (non-Javadoc)
   * @see org.columba.core.filter.IFilterCriteria#setPatternString(java.lang.String)
   */
	public void setPatternString(String pattern) {
		getRoot().addAttribute(FilterCriteria.PATTERN, pattern);
	}
}