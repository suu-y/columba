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
package org.columba.calendar.store;

import java.io.File;

import org.columba.calendar.base.UUIDGenerator;
import org.columba.calendar.model.Event;
import org.columba.calendar.model.EventInfo;
import org.columba.calendar.model.api.IComponentInfo;
import org.columba.calendar.store.api.StoreException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LocalCalendarStoreTest {

    private File file;
    private LocalCalendarStore storage;

    @Before
    public void setUp() throws Exception {
        file = new File("test_calendar");

        storage = new LocalCalendarStore(file);
    }

    @Test
    public void testAddGet() throws Exception {
        String uuid = new UUIDGenerator().newUUID();

        Event model = new Event(uuid);
        model.setSummary("summary");
        model.setDescription("description");

        EventInfo eventInfo = new EventInfo(uuid, "calendar1", model);
        storage.add(eventInfo);

        boolean exists = storage.exists(uuid);
        Assert.assertTrue(exists);

        IComponentInfo result = storage.get(uuid);
        Assert.assertNotNull(result);

    }

    @Test
    public void testRemove() throws Exception {
        String uuid = new UUIDGenerator().newUUID();

        Event model = new Event(uuid);
        model.setSummary("summary");
        model.setDescription("description");

        EventInfo eventInfo = new EventInfo(uuid, "calendar1", model);

        storage.add(eventInfo);

        storage.remove(uuid);

        try {
            IComponentInfo result = storage.get(uuid);
        } catch (StoreException e) {
            // that is the expected case
            return;
        } catch (Exception e) {
            Assert.fail("Expected a StoreException, not " + e.getMessage());
        }

        Assert.fail("Expected a StoreException");

    }

    @After
    public void tearDown() throws Exception {
		// delete all data in directory
    	File storeFile = new File(file.getName(), "store");
		File[] list = storeFile.listFiles();

		for (int i = 0; i < list.length; i++) {
			list[i].delete();
		}
		storeFile.delete();

		// delete folder
		file.delete();
    }
}
