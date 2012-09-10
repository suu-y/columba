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
/*
 * Created on 2003-nov-20
 */
package org.columba.core.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;



/**
 * @author Erik Mattsson
 */
public class XmlIOTest{
    /**
 * Test for writing a Xml Element that has been passed in the constructor XmlIO(XmlElement).
 * @throws IOException thrown if the test fails.
 */
    @Test
    public void testXmlElement() throws IOException {
        // Setup the XML that is to be written
        XmlElement expected = new XmlElement("big_name");
        expected.addAttribute("anattr", "avalue");
        expected.addAttribute("other", "value");

        XmlElement child1 = new XmlElement("child1");
        child1.addAttribute("othername", "nooname");
        child1.addAttribute("onemore", "ok");
        expected.addElement(child1);
        expected.addElement(new XmlElement("child2"));

        XmlIO xmlIO = new XmlIO(expected);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        xmlIO.write(baos);

        xmlIO = new XmlIO();
        Assert.assertTrue("Could not parse the written XML",
            xmlIO.load(new ByteArrayInputStream(baos.toByteArray())));

        XmlElement actual = xmlIO.getRoot().getElement(0);
        Assert.assertEquals("The original and the written XML element are not equal",
            expected, actual);
    }

    /**
 * Test the load(InputStream) method.
 */
    @Test
    public void testReadInputStream() {
        String expected = "<xml attr=\"one\" secAttr=\"two\"><child name=\"other\"/></xml>";
        XmlIO xmlIO = new XmlIO();
        Assert.assertTrue("The XML could not be loaded",
            xmlIO.load(new ByteArrayInputStream(expected.getBytes())));

        XmlElement actualXml = xmlIO.getRoot().getElement("xml");
        Assert.assertEquals("Name isnt correct", "xml", actualXml.getName());
        Assert.assertEquals("The first attribute isnt correct", "one",
            actualXml.getAttribute("attr"));
        Assert.assertEquals("The second attribute isnt correct", "two",
            actualXml.getAttribute("secAttr"));

        XmlElement child = actualXml.getElement(0);
        Assert.assertEquals("The child name isnt correct", "child", child.getName());
        Assert.assertEquals("The childs first attribute isnt correct", "other",
            child.getAttribute("name"));
    }
}
