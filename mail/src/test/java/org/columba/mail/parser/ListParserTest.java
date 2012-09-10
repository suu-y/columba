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
package org.columba.mail.parser;

import java.util.List;
import java.util.Vector;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author fdietz
 * 
 */
public class ListParserTest {

    @Test
	public void testCreateListFromStringNull() {

		try {
			ListParser.createListFromString(null);
			Assert.fail();
		} catch (IllegalArgumentException e) {
		}
	}

    @Test
	public void testCreateListFromStringEmpty() {
		String s = "";

		List<String> l = ListParser.createListFromString(s);
		Assert.assertEquals("list size 0", 0, l.size());
	}

    @Test
	public void testCreateListFromString() {
		String s = "test@test.de";

		List<String> l = ListParser.createListFromString(s);
		Assert.assertEquals("list size 1", 1, l.size());

		Assert.assertEquals("test@test.de", l.get(0));
	}

    @Test
	public void testCreateListFromString2() {
		String s = "test@test.de, test2@test2.de";

		List<String> l = ListParser.createListFromString(s);
		Assert.assertEquals("list size 2", 2, l.size());

		Assert.assertEquals("test@test.de", l.get(0));
		Assert.assertEquals("test2@test2.de", l.get(1));

	}

	/**
	 * test if leading or trailing whitespaces are trimmed correctly
	 * 
	 */
    @Test
	public void testCreateListFromString3() {
		String s = "test@test.de,test2@test2.de, MyGroup,  My Test Group";

		List<String> l = ListParser.createListFromString(s);
		Assert.assertEquals("list size 4", 4, l.size());

		Assert.assertEquals("test@test.de", l.get(0));
		Assert.assertEquals("test2@test2.de", l.get(1));
		Assert.assertEquals("MyGroup", l.get(2));
		Assert.assertEquals("My Test Group", l.get(3));
	}

	/**
	 * test if a comma doesn't disturb our parser if enclosed in double-quotes
	 * 
	 */
    @Test
	public void testCreateListFromString4() {
		String s = "test@test.de, Firstname Lastname, \"Lastname, Firstname\"";

		List<String> l = ListParser.createListFromString(s);
		Assert.assertEquals("list size 3", 3, l.size());

		Assert.assertEquals("test@test.de", l.get(0));
		Assert.assertEquals("Firstname Lastname", l.get(1));
		Assert.assertEquals("Lastname, Firstname", l.get(2));

	}

	/**
	 * test if \" characters are removed in the list, we only need this in the
	 * String representation
	 */
    @Test
	public void testCreateListFromString5() {
		String s = "test@test.de, \"Firstname Lastname\", \"Lastname, Firstname\"";

		List<String> l = ListParser.createListFromString(s);
		Assert.assertEquals("list size 3", 3, l.size());

		Assert.assertEquals("test@test.de", l.get(0));
		Assert.assertEquals("Firstname Lastname", l.get(1));
		Assert.assertEquals("Lastname, Firstname", l.get(2));
	}

	/**
	 * Test displayname and address with and without comma
	 */
    @Test
	public void testCreateListFromString6() {
		String s = "test@test.de, \"Firstname Lastname\" <mail@mail.org>, \"Lastname, Firstname\" <mail@mail.org>";

		List<String> l = ListParser.createListFromString(s);
		Assert.assertEquals("list size 3", 3, l.size());

		Assert.assertEquals("test@test.de", l.get(0));
		Assert.assertEquals("Firstname Lastname <mail@mail.org>", l.get(1));
		Assert.assertEquals("Lastname, Firstname <mail@mail.org>", l.get(2));
	}

    @Test
	public void testCreateStringFromListNull() {
		try {
			ListParser.createStringFromList(new Vector<String>(), null, false);
			Assert.fail();
		} catch (IllegalArgumentException e) {
		}
	}

    @Test
	public void testCreateStringFromListNull2() {
		try {
			ListParser.createStringFromList(null, ";", false);
			Assert.fail();
		} catch (IllegalArgumentException e) {
		}
	}

    @Test
	public void testCreateStringFromListEmpty() {

		List<String> list = new Vector<String>();

		String result = ListParser.createStringFromList(list, ";", false);
		Assert.assertEquals("", result);
	}

    @Test
	public void testCreateStringFromList() {

		List<String> list = new Vector<String>();
		list.add("test@test.de");
		list.add("test2@test2.de");

		String result = ListParser.createStringFromList(list, ",", true);
		Assert.assertEquals("test@test.de, test2@test2.de, ", result);
	}
	
	/**
	 * Test if \" character disturbs our parser
	 */
    @Test
	public void testCreateStringFromList2() {

		List<String> list = new Vector<String>();
		list.add("test@test.de");
		list.add("\"My yours and he's list\"");

		String result = ListParser.createStringFromList(list, ",", false);
		Assert.assertEquals("test@test.de, My yours and he's list", result);
	}
	

	/**
	 * Test if "," inside a contact item is escaped correctly
	 *
	 */
    @Test
	public void testCreateStringFromList4() {

		List<String> list = new Vector<String>();
		list.add("test@test.de");
		list.add("Firstname Lastname");
		list.add("\"Lastname, Firstname\"");
		
		String result = ListParser.createStringFromList(list, ",", true);
		Assert.assertEquals("test@test.de, Firstname Lastname, \"Lastname, Firstname\", ", result);
	}
	
	/**
	 * Test what parser does if contact item already contains an escaped 
	 * representation
	 */
    @Test
	public void testCreateStringFromList5() {

		List<String> list = new Vector<String>();
		list.add("test@test.de");
		list.add("\"Firstname Lastname\" <mail@mail.org>");
		list.add("\"Lastname, Firstname\" <mail@mail.org>");
		
		String result = ListParser.createStringFromList(list, ",", false);
		Assert.assertEquals("test@test.de, Firstname Lastname <mail@mail.org>, \"Lastname, Firstname\" <mail@mail.org>", result);
	}
}
