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
package org.columba.calendar.ui.comp;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JComboBox;

public class TimePicker extends JComboBox {

	int hour;

	int minutes;

	final static String defaultValue = "00:00";

	public TimePicker() {

		for (int i = 0; i <= 23; i++) {
			addItem(i + ":00");
			addItem(i + ":15");
			addItem(i + ":30");
			addItem(i + ":45");
		}

		hour = 0;
		minutes = 0;

		setSelectedItem("00:00");
		
		setEditable(true);
		
		addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if (arg0.getActionCommand().equals("comboBoxChanged")) {
					TimePicker.this.setSelectedItem(validateInput((String) getSelectedItem()));
				} else if (arg0.getActionCommand().equals("comboBoxEdited")) {
					TimePicker.this.setSelectedItem(validateInput((String) getSelectedItem()));
				}
			}
			
		});

	}

	public static String validateInput(String value) {
				
		// regexp for correct timestamp
		boolean valid = value.matches("[0-2][0-9]:[0-5][0-9]");
		
		if (valid)
			return value;
		
		if (value.indexOf(":") > -1) {
			// check value before :
			String before = value.substring(0, value.indexOf(":"));
			int iBefore = -1;
			int iAfter = -1;
			try {
				int testBefore = Integer.parseInt(before);
				if ((testBefore > -1) && (testBefore < 24)) {
					// correct value!
					iBefore = testBefore;
				}
			} catch (NumberFormatException e) {
				return defaultValue;
			}

			// check value after :
			String after = value.substring(value.indexOf(":") + 1);
			try {
				int testAfter = Integer.parseInt(after);
				if ((testAfter > -1) && (testAfter < 60)) {
					// correct value!
					iAfter = testAfter;
				}
			} catch (NumberFormatException e) {
				return defaultValue;
			}
			
			if ((iBefore < 0) || (iAfter < 0))
				return defaultValue;
			
			String correctedValue = "";
			// both values are okay, so create a correct time
			if (iBefore < 10)
				correctedValue += "0";
			correctedValue += iBefore + ":";
			if (iAfter < 10)
				correctedValue += "0";
			correctedValue += iAfter;
			return correctedValue;
		}
		
		// there is no :
		try {
			int iValue = Integer.parseInt(value);
			if (iValue > -1) {
				if (iValue < 10) {
					return "0" + iValue + ":00";
				} else if (iValue < 24) {
					return iValue + ":00";
				}
			}
			// no correct value!
			return defaultValue;
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public int getHour() {
		String value = (String) getSelectedItem();
		int h = Integer.parseInt(value.substring(0, value.indexOf(":")));

		return h;
	}

	public int getMinutes() {
		String value = (String) getSelectedItem();

		int m = Integer.parseInt(value.substring(value.indexOf(":")+1, value.length()));

		return m;
	}

	public void setTime(int hour, int minutes) {
		
		StringBuffer buf = new StringBuffer();
		buf.append(hour);
		buf.append(":");
		// in case we have to add another "0"
		if ( minutes == 0)
			buf.append("00");
		else
			buf.append(minutes);

		setSelectedItem(buf.toString());
	}

}
