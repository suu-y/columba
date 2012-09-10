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
package org.columba.mail.folder;

import java.io.ByteArrayInputStream;
import org.junit.Assert;
import org.junit.Test;


/**
 * Tests for {@link Attribute} methods.
 * 
 * @author fdietz
 *
 */
public class AttributeTest extends AbstractFolderTst {
    /**
     * @param factory
     * @param test
     */
    public AttributeTest(Class factory) {
        super(factory);
        
    }

    /**
     * Check set/get attributes
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
//      add message "0.eml" as inputstream to folder
        String input = FolderTstHelper.getString(0);

        // create stream from string
        ByteArrayInputStream inputStream = FolderTstHelper
                .getByteArrayInputStream(input);

        // add stream to folder
        Object uid = getSourceFolder().addMessage(inputStream);
        
        getSourceFolder().setAttribute(uid, "columba.spam", Boolean.TRUE);
        
        Boolean result = (Boolean) getSourceFolder().getAttribute(uid, "columba.spam");
        
        Assert.assertEquals("attribute columba.spam", Boolean.TRUE, result);
    }
}
