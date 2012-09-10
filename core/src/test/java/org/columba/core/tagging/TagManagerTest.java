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
package org.columba.core.tagging;

import java.util.Iterator;

import org.columba.core.shutdown.ShutdownManager;
import org.columba.core.tagging.api.ITag;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TagManagerTest{

	static TagManager tm;
	static String normalTagId = "";

    @BeforeClass
	public static void setUp() throws Exception {
		tm = TagManager.getInstance();
	}

    @AfterClass
	public static void tearDown() throws Exception {
		ShutdownManager.getInstance().shutdown(0);
	}

	/*
	 * Test method for 'org.columba.core.tagging.TagManager.addTag(String)'
	 */
    @Test
	public void testAddTag() {
		ITag normalTag = tm.addTag("NormalTag");
		normalTagId = normalTag.getId();
		System.out.println("normalTagId = " + normalTagId);
	}

	/*
	 * Test method for 'org.columba.core.tagging.TagManager.getAllTags()'
	 */
    @Test
	public void testGetAllTags() {
		boolean found = false;
		for (Iterator<ITag> iter = tm.getAllTags(); iter.hasNext();) {
			ITag tag = iter.next();
			System.out.println("tag = " + tag.getId() + " | " + tag.getName());
			if (tag.getName().equals("NormalTag")) {
				found = true;
				System.out.println("Found NormalTag");
			}
		}
	}

	/*
	 * Test method for 'org.columba.core.tagging.TagManager.getTag(String)'
	 */
    @Test
	public void testGetTag() {
		if (tm.getTag(normalTagId) == null) // getByName
			Assert.fail("NormalTag not found");
		else
			System.out.println("NormalTag found");
	}
	/*
	 * Test method for 'org.columba.core.tagging.TagManager.removeTag(String)'
	 */
    @Test
	public void testRemoveTag() {
		if (tm.getTag(normalTagId) == null)
			Assert.fail("NormalTag should be there!");
		tm.removeTag(normalTagId);
		if (tm.getTag(normalTagId) != null)
			Assert.fail("NormalTag not deleted!");
	}

}
