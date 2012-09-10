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
package org.columba.calendar.parser;

import org.junit.Assert;
import org.junit.Test;

public class XCSParserTest {

    @Test
    public void testGetRValue() {
        String rule = "RRULE:FREQ=YEARLY;COUNT=5;INTERVAL=1";
        Assert.assertEquals("YEARLY", XCSDocumentParser.getRValue(rule, "FREQ"));
        Assert.assertEquals("5", XCSDocumentParser.getRValue(rule, "COUNT"));
        Assert.assertEquals("1", XCSDocumentParser.getRValue(rule, "INTERVAL"));

        rule = "RRULE:FREQ=YEARLY;INTERVAL=1";
        Assert.assertEquals("YEARLY", XCSDocumentParser.getRValue(rule, "FREQ"));
        Assert.assertEquals("", XCSDocumentParser.getRValue(rule, "COUNT"));
        Assert.assertEquals("1", XCSDocumentParser.getRValue(rule, "INTERVAL"));
        Assert.assertEquals("", XCSDocumentParser.getRValue(rule, "UNTIL"));

        rule = "RRULE:FREQ=MONTHLY;UNTIL=20070627T215959;INTERVAL=2;BYDAY=4WE";
        Assert.assertEquals("MONTHLY", XCSDocumentParser.getRValue(rule, "FREQ"));
        Assert.assertEquals("", XCSDocumentParser.getRValue(rule, "COUNT"));
        Assert.assertEquals("2", XCSDocumentParser.getRValue(rule, "INTERVAL"));
        Assert.assertEquals("20070627T215959", XCSDocumentParser.getRValue(rule, "UNTIL"));
        Assert.assertEquals("4WE", XCSDocumentParser.getRValue(rule, "BYDAY"));

    }
}
