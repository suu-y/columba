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

/*
 * Created on 2003-okt-31
 */
package org.columba.core.config;

import org.columa.core.config.IDefaultItem;
import org.columba.core.xml.XmlElement;
import org.junit.Assert;
import org.junit.Test;


/**
 * Test cases for the <code>DefaultItem</code> class.
 *
 * @author redsolo
 */
public class DefaultItemTest {
    /*
 * Test for int hashCode().
 */
    @Test
    public void testHashCode() {
        IDefaultItem item = new DefaultItem(new XmlElement());
        item.setBoolean("boolean", false);
        item.setBoolean("badboolean", true);
        item.setString("key", "value");

        IDefaultItem item2 = new DefaultItem(new XmlElement());
        item2.setBoolean("boolean", false);
        item2.setBoolean("badboolean", true);
        item2.setString("key", "value");
        Assert.assertTrue("The hashcodes are not the same",
            item.hashCode() == item2.hashCode());
        Assert.assertTrue("The hashcodes are the same for different items.",
            item.hashCode() != new DefaultItem(new XmlElement()).hashCode());
    }

    /*
 * Test for boolean equals(Object)
 */
    @Test
    public void testEqualsObject() {
        IDefaultItem item = new DefaultItem(new XmlElement());
        item.setBoolean("boolean", false);
        item.setBoolean("badboolean", true);
        item.setString("key", "value");

        IDefaultItem item2 = new DefaultItem(new XmlElement());
        item2.setBoolean("boolean", false);
        item2.setBoolean("badboolean", true);
        item2.setString("key", "value");
        Assert.assertTrue("The items are not equal", item.equals(item2));
        Assert.assertTrue("The items are not equal", item2.equals(item));
        Assert.assertTrue("The items are not equal", item.equals(item));
        Assert.assertTrue("The items are not equal", item2.equals(item2));
        Assert.assertNotSame("The objects are the same", item, item2);
        Assert.assertTrue("The items are equal",
            !item.equals(new DefaultItem(new XmlElement())));

        Assert.assertFalse("The item is equal to an empty item",
            item.equals(new DefaultItem(null)));
        Assert.assertFalse("The item is equal to a null object", item.equals(null));
        Assert.assertTrue("The items are not equal",
            item.equals(new DefaultItem((XmlElement) item.getRoot().clone())));
    }

    /*
 * Test for clone()
 */
    @Test
    public void testClone() {
        IDefaultItem item1 = new DefaultItem(new XmlElement("EL"));
        IDefaultItem item2 = (IDefaultItem) item1.clone();
        Assert.assertEquals("The parent and the cloned object are not equal", item1,
            item2);
        Assert.assertNotSame("The parent and the cloned object are the same", item1,
            item2);
        Assert.assertNotSame("The parent and the cloned Xml Elements objects are the same object.",
            item1.getRoot(), item2.getRoot());
        Assert.assertEquals("The parent and the cloned Xml Elements objects are not equal.",
            item1.getRoot(), item2.getRoot());
        Assert.assertEquals("The parent and the cloned object did not return the same hashcodes",
            item1.hashCode(), item2.hashCode());

        XmlElement xml = new XmlElement();
        xml.setName("a NAME");
        xml.addAttribute("key", "values");
        xml.addAttribute("key2", "other values");
        xml.addSubElement("child");
        xml.addSubElement(new XmlElement("child2"));

        item1 = new DefaultItem(xml);
        item2 = (IDefaultItem) item1.clone();
        Assert.assertEquals("The parent and the cloned object are not equal", item1,
            item2);
        Assert.assertNotSame("The parent and the cloned object are the same", item1,
            item2);
        Assert.assertSame("The getRoot() method did not return the same object put in",
            xml, item1.getRoot());
        Assert.assertNotSame("The parent and the cloned Xml Elements objects are the same object.",
            item1.getRoot(), item2.getRoot());
        Assert.assertEquals("The parent and the cloned Xml Elements objects are not equal.",
            item1.getRoot(), item2.getRoot());
        Assert.assertNotSame("The parent and the cloned Xml Elements objects are the same object.",
            xml, item2.getRoot());
        Assert.assertEquals("The parent and the cloned object did not return the same hashcodes",
            item1.hashCode(), item2.hashCode());
    }

    @Test
    public void testSet() {
    	XmlElement root = new XmlElement("root");
    	IDefaultItem item = new DefaultItem(root);
    	item.setString("sub/path", "test", "value");
    	
    	Assert.assertTrue( root.getElement("sub/path")!= null );
    	Assert.assertEquals( item.getString("sub/path","test"), "value");
    }
}
