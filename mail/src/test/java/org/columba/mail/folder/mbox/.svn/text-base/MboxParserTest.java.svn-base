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
package org.columba.mail.folder.mbox;

import java.io.IOException;

import org.columba.ristretto.io.CharSequenceSource;
import org.junit.Assert;
import org.junit.Test;

public class MboxParserTest {

    @Test
    public void test1() throws IOException {
        String testMbox = "From god@heaven.af.mil Sat Jan  3 01:05:34 1996\n" +
                "1\n";

        MboxMessage[] messages = MboxParser.parseMbox(new CharSequenceSource(testMbox));

        Assert.assertEquals(1, messages.length);

        String message = testMbox.substring((int) messages[0].getStart(), (int) (messages[0].getStart() + messages[0].getLength()));

        Assert.assertEquals("1\n", message);

    }

    @Test
    public void test2() throws IOException {
        String testMbox = "From god@heaven.af.mil Sat Jan  3 01:05:34 1996\n" +
                "1\n" +
                "From god@heaven.af.mil Sat Jan  3 01:05:34 1996\n" +
                "2\n";

        MboxMessage[] messages = MboxParser.parseMbox(new CharSequenceSource(testMbox));

        Assert.assertEquals(2, messages.length);

        String message = testMbox.substring((int) messages[0].getStart(), (int) (messages[0].getStart() + messages[0].getLength()));

        Assert.assertEquals("1\n", message);

        message = testMbox.substring((int) messages[1].getStart(), (int) (messages[1].getStart() + messages[1].getLength()));
        Assert.assertEquals("2\n", message);

    }

    @Test
    public void test3() throws IOException {
        String testMbox = "From god@heaven.af.mil Sat Jan  3 01:05:34 1996\n" +
                "From 1\n" +
                "From god@heaven.af.mil Sat Jan  3 01:05:34 1996\n" +
                "2\n";

        MboxMessage[] messages = MboxParser.parseMbox(new CharSequenceSource(testMbox));

        Assert.assertEquals(2, messages.length);

        String message = testMbox.substring((int) messages[0].getStart(), (int) (messages[0].getStart() + messages[0].getLength()));

        Assert.assertEquals("From 1\n", message);

        message = testMbox.substring((int) messages[1].getStart(), (int) (messages[1].getStart() + messages[1].getLength()));
        Assert.assertEquals("2\n", message);

    }
}
