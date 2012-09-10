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
import java.util.Iterator;


import org.junit.Test;
import org.columba.calendar.base.UUIDGenerator;
import org.columba.calendar.model.api.IComponent;
import org.columba.calendar.parser.XCSDocumentParser;
import org.columba.calendar.store.api.StoreException;
import org.jdom.Document;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

public class LocalXMLFileStoreTest {

    private File file;
    private LocalXMLFileStore storage;

    @Before
    public void setUp() throws Exception {
        file = new File("test_calendar");

        storage = new LocalXMLFileStore(file);
    }

    @Test
    public void testAddGet() throws Exception {

        XCSDocumentParser model = new XCSDocumentParser(IComponent.TYPE.EVENT);
        String uuid = model.getId();
        storage.save(uuid, model.getDocument());

        boolean exists = storage.exists(uuid);
        Assert.assertTrue(exists);

        Document result = storage.load(uuid);
        Assert.assertNotNull(result);

    }

    @Test
    public void testIterator() throws Exception {
        String uuid1 = new UUIDGenerator().newUUID();
        XCSDocumentParser model1 = new XCSDocumentParser(IComponent.TYPE.EVENT);
        storage.save(uuid1, model1.getDocument());
        String uuid2 = new UUIDGenerator().newUUID();
        XCSDocumentParser model2 = new XCSDocumentParser(IComponent.TYPE.EVENT);
        storage.save(uuid2, model2.getDocument());

        Iterator it = storage.iterator();
        Document result1 = (Document) it.next();
        Document result2 = (Document) it.next();

        Assert.assertNotNull(result1);
        Assert.assertNotNull(result2);
    }

    @Test
    public void testRemove() throws Exception {
        String uuid = new UUIDGenerator().newUUID();
        XCSDocumentParser model = new XCSDocumentParser(IComponent.TYPE.EVENT);
        storage.save(uuid, model.getDocument());

        storage.remove(uuid);

        try {
            Document result = storage.load(uuid);
        } catch (StoreException e) {
            // this is the expected cases
            return;
        } catch (Exception e) {
            Assert.fail("Expected StoreException, not " + e.getMessage());
        }

        Assert.fail("Expected StoreException, got no exception");

    }

    @After
    public void tearDown() throws Exception {


        // delete all data in directory
        File[] list = file.listFiles();

        for (int i = 0; i < list.length; i++) {
            list[i].delete();
        }

        // delete folder
        file.delete();

    }
}
