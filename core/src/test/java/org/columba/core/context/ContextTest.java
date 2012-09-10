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
package org.columba.core.context;

import org.columba.core.context.base.ContextFactory;
import org.columba.core.context.base.api.IAttributeType;
import org.columba.core.context.base.api.IStructureType;
import org.columba.core.context.base.api.IStructureValue;
import org.columba.core.context.base.api.MULTIPLICITY;
import org.columba.core.context.base.api.IAttributeType.BASETYPE;
import org.junit.Assert;
import org.junit.Test;

public class ContextTest {

    /**
     *
     * attribute test <code>
     *  <userlist name="STRING" description="STRING">
     *  </userlist>
     * </code>
     */
    @Test
    public void test() throws Exception {
        ContextFactory factory = new ContextFactory();

        // top-level structure
        IStructureType type = factory.createStructure("userlist", "ns");
        type.setCardinality(MULTIPLICITY.ONE_TO_ONE);

        // mandatory String-based attribute
        IAttributeType attr = type.addAttribute("name", "ns");

        // optional String-based attribute
        IAttributeType attr2 = type.addAttribute("description", "ns");

        IAttributeType result1 = type.getAttribute("name", "ns");
        Assert.assertEquals(attr, result1);
    }

    @Test
    public void testWithValue() throws Exception {
        ContextFactory factory = new ContextFactory();

        // top-level structure
        IStructureType type = factory.createStructure("userlist", "ns");
        type.setCardinality(MULTIPLICITY.ONE_TO_ONE);

        // mandatory String-based attribute
        IAttributeType attr = type.addAttribute("name", "ns");
        // optional String-based attribute
        IAttributeType attr2 = type.addAttribute("description", "ns");

        IAttributeType result1 = type.getAttribute("name", "ns");
        Assert.assertEquals(attr, result1);

        IStructureValue value = factory.createValue("userlist", "ns", type);
        value.setString("name", "ns", "Name");
        value.setString("description", "ns", "Description");

        // should throw exception as attribute type "name2" is not defined in
        // type structure
        try {
            value.setString("name2", "ns", "Description");
            Assert.fail();
        } catch (RuntimeException e) {
        }

        try {
            value.addChild("test", "ns");
            Assert.fail();
        } catch (RuntimeException e) {
        }

    }

    /**
     *
     * structure test
     *
     * <pre>
     *   &lt;userlist name=&quot;STRING&quot; description=&quot;STRING&quot;&gt;
     *    &lt;user firstname=&quot;STRING&quot; lastname=&quot;STRING&quot;&gt;
     *    &lt;/user&gt;
     *    &lt;user firstname=&quot;STRING&quot; lastname=&quot;STRING&quot;&gt;
     *    &lt;/user&gt;
     *   &lt;/userlist&gt;
     * </pre>
     */
    @Test
    public void test2() throws Exception {
        ContextFactory factory = new ContextFactory();

        // top-level structure
        IStructureType userList = factory.createStructure("userlist", "ns");
        userList.setCardinality(MULTIPLICITY.ONE_TO_ONE);

        // mandatory String-based attribute
        IAttributeType attr = userList.addAttribute("name", "ns");

        // optional String-based attribute
        IAttributeType attr2 = userList.addAttribute("description", "ns");
        attr2.setBaseType(BASETYPE.STRING);

        // user struct
        IStructureType user = userList.addChild("user", "ns");
        user.setCardinality(MULTIPLICITY.ZERO_TO_MANY);

        IAttributeType attr1_1 = user.addAttribute("firstname", "ns");
        IAttributeType attr1_2 = user.addAttribute("lastname", "ns");
    }

    @Test
    public void test2WithValue() throws Exception {
        ContextFactory factory = new ContextFactory();

        // top-level structure
        IStructureType userList = factory.createStructure("userlist", "ns");
        userList.setCardinality(MULTIPLICITY.ONE_TO_ONE);

        // mandatory String-based attribute
        IAttributeType attr = userList.addAttribute("name", "ns");

        // optional String-based attribute
        IAttributeType attr2 = userList.addAttribute("description", "ns");
        attr2.setBaseType(BASETYPE.STRING);

        // user struct
        IStructureType user = userList.addChild("user", "ns");
        user.setCardinality(MULTIPLICITY.ZERO_TO_MANY);

        IAttributeType attr1_1 = user.addAttribute("firstname", "ns");
        IAttributeType attr1_2 = user.addAttribute("lastname", "ns");

        // create test userlist data
        IStructureValue value = factory.createValue("userlist", "ns", userList);
        value.setString("name", "ns", "Name");
        value.setString("description", "ns", "Description");

        IStructureValue value1 = value.addChild("user", "ns");
        value1.setString("firstname", "ns", "FirstName");
        value1.setString("lastname", "ns", "LastName");

        IStructureValue value2 = value.addChild("user", "ns");
        value2.setString("firstname", "ns", "FirstName");
        value2.setString("lastname", "ns", "LastName");

    }

    @Test
    public void test3() throws Exception {
        ContextFactory factory = new ContextFactory();

        IStructureType type = factory.createStructure("context",
                "org.columba.core");

        // MULTIPLICITY.ONE is default
        IStructureType core = type.addChild("core", "org.columba.core");

        // identity definition
        IStructureType identity = core.addChild("identity", "org.columba.core");
        // MULTIPLICITY.ZERO_TO_ONE is default
        IAttributeType emailAddress = identity.addAttribute("emailAddress",
                "org.columba.core");
        identity.addAttribute("displayName", "org.columba.core");
        identity.addAttribute("firstName", "org.columba.core");
        identity.addAttribute("lastName", "org.columba.core");
        identity.addAttribute("website", "org.columba.core");

        // date time timezone definition
        IStructureType dateTime = core.addChild("dateTime", "org.columba.core");
        IAttributeType date = dateTime.addAttribute("date", "org.columba.core");
        IAttributeType timeZone = dateTime.addAttribute("timeZone",
                "org.columba.core");
        date.setBaseType(BASETYPE.DATE);

        // date range (start time, end time) definition
        IStructureType dateRange = core.addChild("dateRange",
                "org.columba.core");
        IAttributeType startStart = dateRange.addAttribute("startDate",
                "org.columba.core");
        startStart.setBaseType(BASETYPE.DATE);
        IAttributeType endDate = dateRange.addAttribute("endDate",
                "org.columba.core");
        endDate.setBaseType(BASETYPE.DATE);

        // document definition
        IStructureType document = core.addChild("document", "org.columba.core");
        document.addAttribute("author", "org.columba.core");
        document.addAttribute("title", "org.columba.core");
        document.addAttribute("summary", "org.columba.core");
        document.addAttribute("body", "org.columba.core");

        // locale definition
        IStructureType locale = core.addChild("locale", "org.columba.core");
        locale.addAttribute("language", "org.columba.core");
        locale.addAttribute("country", "org.columba.core");
        locale.addAttribute("variant", "org.columba.core");

        // list of attachments
        IStructureType attachmentList = core.addChild("attachmentList",
                "org.columba.core");
        IStructureType attachment = attachmentList.addChild("attachment",
                "org.columba.core");
        attachment.setCardinality(MULTIPLICITY.ZERO_TO_MANY);
        // single attachment
        attachment.addAttribute("name", "org.columba.core");
        IAttributeType contentType = attachment.addAttribute("content",
                "org.columba.core");
        contentType.setBaseType(BASETYPE.BINARY);

        // message
        IStructureType message = core.addChild("message", "org.columba.core");
        message.addAttribute("subject", "org.columba.core");
        // single sender - re-use identity type
        IStructureType sender = message.addChild("sender", "org.columba.core");
        sender.addChild(identity);
        sender.setCardinality(MULTIPLICITY.ONE_TO_ONE);
        // re-use identity type for recipient list
        IStructureType recipients = message.addChild("recipients",
                "org.columba.core");
        recipients.setCardinality(MULTIPLICITY.ZERO_TO_MANY);
        recipients.addChild(identity);
        // message body
        message.addAttribute("bodyText", "org.columba.core");
        message.addAttribute("selectedBodytext", "org.columba.core");
        // message contains list of attachments
        message.addChild(attachmentList);
    }
}
