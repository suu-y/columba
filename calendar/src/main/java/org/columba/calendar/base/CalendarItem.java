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
package org.columba.calendar.base;

import java.awt.Color;

import javax.swing.Icon;

import org.columba.api.plugin.IExtensionInterface;
import org.columba.calendar.base.api.ICalendarItem;
import org.columba.calendar.store.api.ICalendarStore;
import org.columba.core.config.DefaultItem;


public class CalendarItem implements ICalendarItem, IExtensionInterface {
	protected DefaultItem item;

	protected ICalendarStore store;
	
	public CalendarItem(DefaultItem item) {
		this.item = item;
	}

	/**
	 * @return Returns the color.
	 */
	public Color getColor() {
		int color = 0;
		try {
			color = Integer.parseInt(item.getString("property", "color"), 16);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return new Color(color);
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return item.get("uid");
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return item.getString("property", "name");
	}

	public void setSelected(boolean selected) {
		item.setBoolean("selected", selected);
	}

	public Icon getIcon() {
		return null;
	}

	public boolean isSelected() {
		return item.getBoolean("selected");
	}

	public String toString() {
		return getName();
	}

	public void setColor(Color color) {
		item.setString("property", "color", Integer.toString(color.getRGB(), 16));
	}

	public CATEGORY getCategory() {
		return CATEGORY.OTHER;
	}

	public ICalendarStore getStore() {
		return store;
	}
}
