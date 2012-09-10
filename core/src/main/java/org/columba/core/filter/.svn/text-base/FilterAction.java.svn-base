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

/**
 * 
 * @author frd
 * 
 * wrapper class for filter actions
 *  
 */

// example configuration (tree.xml)
//
// <actionlist>
//  <action uid="122" type="Move Message"></action>
// </actionlist>
public class FilterAction extends DefaultItem implements IFilterAction {
	/**
	 * Constructor for FilterAction
	 * 
	 * XmlElement root is "actionlist"
	 * 
	 * @see org.columba.core.config.DefaultItem#DefaultItem(XmlElement)
	 */
	public FilterAction(XmlElement root) {
		super(root);
	}

	/* (non-Javadoc)
   * @see org.columba.core.filter.IFilterAction#getUid()
   */
	public String getUid() {
		if (contains("uid") == false) {
			// folder uid doesn't exist
			//  -> create default value
			setString("uid", "101");

			return get("uid");
		} else {
			return get("uid");
		}
	}

	/* (non-Javadoc)
   * @see org.columba.core.filter.IFilterAction#setUid(java.lang.String)
   */
	public void setUid(String id) {
		setString("uid", id);
	}

	/* (non-Javadoc)
   * @see org.columba.core.filter.IFilterAction#getAction()
   */
	public String getAction() {
		return get("type");
	}

	/* (non-Javadoc)
   * @see org.columba.core.filter.IFilterAction#setAction(java.lang.String)
   */
	public void setAction(String s) {
		setString("type", s);
	}

}