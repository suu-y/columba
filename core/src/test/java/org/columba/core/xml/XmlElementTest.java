/*
 * Created on 2003-okt-30
 */
package org.columba.core.xml;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for the <code>XmlElement</code> class.
 *
 * @author Erik Mattsson
 */
public class XmlElementTest {
    /*
     * Test for boolean equals(Object)
     */

    @Test
    public void testEquals() {
        XmlElement xml1 = new XmlElement();
        XmlElement xml2 = new XmlElement();
        xml1.setName("ONE");
        xml2.setName("ONE");
        xml1.setData("DATA");
        xml2.setData("DATA");
        xml1.addAttribute("name", "value");
        xml2.addAttribute("name", "value");

        XmlElement child1 = new XmlElement("child1");
        XmlElement sibling1 = new XmlElement("sibling1");
        XmlElement child2 = new XmlElement("child2");
        XmlElement sibling2 = new XmlElement("sibling2");

        child1.addElement((XmlElement) child2.clone());
        child1.addElement((XmlElement) sibling2.clone());

        xml1.addElement((XmlElement) child1.clone());
        xml1.addElement((XmlElement) sibling1.clone());
        xml2.addElement((XmlElement) child1.clone());
        xml2.addElement((XmlElement) sibling1.clone());

        Assert.assertTrue("The XML elements are not equal", xml1.equals(xml2));
        Assert.assertTrue("The XML elements are not equal", xml2.equals(xml1));
        Assert.assertTrue("The XML elements are not equal", xml1.equals(xml1));
        Assert.assertTrue("The XML elements are not equal", xml2.equals(xml2));

        Assert.assertFalse("The XML elements are equal to null ", xml1.equals(null));
        Assert.assertFalse("The XML elements are equal to null ", xml2.equals(null));
    }

    /*
     * Test for boolean equals(Object)
     */
    @Test
    public void testEquals2() {
        XmlElement xml1 = new XmlElement();
        XmlElement xml2 = new XmlElement();
        xml1.setName("ONE");
        xml2.setName("ONE");
        xml1.setData("DATA");
        xml2.setData("DATA");
        xml1.addAttribute("name", "value");
        Assert.assertTrue("The XML elements are equal", !xml1.equals(xml2));
        Assert.assertTrue("The XML elements are equal", !xml2.equals(xml1));
    }

    /*
     * Test for boolean not equals(Object)
     */
    @Test
    public void testNotEqualsObject() {
        XmlElement xml1 = new XmlElement();
        XmlElement xml2 = new XmlElement();
        xml1.setName("ONE");
        xml2.setName("ONE");
        xml1.addElement(new XmlElement("child1"));
        Assert.assertTrue("The XML elements are equal", !xml1.equals(xml2));
        Assert.assertTrue("The XML elements are equal", !xml2.equals(xml1));
    }

    /*
     * Test for hashCode()
     */
    @Test
    public void testHashcode() {
        XmlElement xml1 = new XmlElement();
        XmlElement xml2 = new XmlElement();
        xml1.setName("ONE");
        xml2.setName("ONE");
        xml1.addElement(new XmlElement("child1"));
        xml2.addElement(new XmlElement("child1"));
        Assert.assertEquals("The hashcode are not equal", xml2.hashCode(),
                xml1.hashCode());
    }

    /*
     * Test for clone()
     */
    @Test
    public void testClone() {
        XmlElement xml1 = new XmlElement("a Name");
        XmlElement xml2 = (XmlElement) xml1.clone();
        Assert.assertEquals("The parent and the cloned object are not equal", xml1,
                xml2);
        Assert.assertNotSame("The parent and the cloned object are the same", xml1,
                xml2);
        Assert.assertNotSame("The parent and the cloned Attributes objects are the same object.",
                xml1.getAttributes(), xml2.getAttributes());
        Assert.assertNotSame("The parent and the cloned Sub Element objects are the same object.",
                xml1.getElements(), xml2.getElements());

        xml1 = new XmlElement("a Name", "data");
        xml2 = (XmlElement) xml1.clone();
        Assert.assertEquals("The parent and the cloned object are not equal", xml1,
                xml2);
        Assert.assertNotSame("The parent and the cloned object are the same", xml1,
                xml2);
        Assert.assertNotSame("The parent and the cloned Attributes objects are the same object.",
                xml1.getAttributes(), xml2.getAttributes());
        Assert.assertNotSame("The parent and the cloned Sub Element objects are the same object.",
                xml1.getElements(), xml2.getElements());

        xml1 = new XmlElement();
        xml1.setName("a NAME");
        xml1.addAttribute("key", "values");
        xml1.addAttribute("key2", "other values");
        xml1.addSubElement("child");
        xml1.addSubElement(new XmlElement("child2"));
        xml2 = (XmlElement) xml1.clone();
        Assert.assertEquals("The parent and the cloned object are not equal", xml1,
                xml2);
        Assert.assertNotSame("The parent and the cloned object are the same", xml1,
                xml2);
        Assert.assertNotSame("The parent and the cloned Attributes objects are the same object.",
                xml1.getAttributes(), xml2.getAttributes());
        Assert.assertNotSame("The parent and the cloned Sub Element objects are the same object.",
                xml1.getElements(), xml2.getElements());
        Assert.assertEquals("The Name is not the same", "a NAME", xml2.getName());
        Assert.assertEquals("The value for Attributes key='key' is not the expected",
                "values", xml2.getAttribute("key"));
        Assert.assertEquals("The value for Attributes key='key2' is not the expected",
                "other values", xml2.getAttribute("key2"));
        Assert.assertEquals("The first childs name is not the expected", "child",
                xml2.getElement(0).getName());
        Assert.assertEquals("The second childs name is not the expected", "child2",
                xml2.getElement(1).getName());
        Assert.assertEquals("The parent and cloned object hashCode() methods return different values.",
                xml1.hashCode(), xml2.hashCode());
    }

    /*
     * Test for XmlElement(String,String)
     */
    @Test
    public void testConstructorStrStr() {
        XmlElement xml = new XmlElement("a Name", "a Data");
        xml.addAttribute("key", "values");
        xml.addAttribute("key2", "other values");
        xml.addSubElement("child");
        xml.addSubElement(new XmlElement("child2"));

        Assert.assertEquals("The Name isnt correct", "a Name", xml.getName());
        Assert.assertEquals("The Data isnt correct", "a Data", xml.getData());
        Assert.assertEquals("The attribute 'key' isnt correct", "values",
                xml.getAttribute("key"));
        Assert.assertEquals("The attribute 'key' isnt correct", "other values",
                xml.getAttribute("key2"));
        Assert.assertEquals("The child element isnt correct", "child",
                xml.getElement(0).getName());
    }
}
