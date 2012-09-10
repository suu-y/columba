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

import java.io.File;
import java.util.Iterator;


import java.util.SimpleTimeZone;
import java.util.TimeZone;
import org.columba.calendar.model.api.IEventInfo;
import org.columba.calendar.model.api.IRecurrence;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CalendarImporterTest {

    @BeforeClass
    public static void setup(){
        System.out.println("TimeZone is:" + TimeZone.getDefault());
        TimeZone t = new SimpleTimeZone(0, "GMT");
        TimeZone.setDefault(t);
        System.out.println("TimeZone is:" + TimeZone.getDefault());
    }

    @Test
    public void testImportCalendar() {
        
        File example1 = new File("src/test/resources/SunbirdEvents.ics");
        CalendarImporter importer = new CalendarImporter();
        Iterator<IEventInfo> i = null;
        try {
            i = importer.importCalendar("example", example1);
        } catch (Exception e) {
            e.printStackTrace();

            Assert.fail("Got following exception:" + e.getMessage());
        }

        // check three entries
        for (; i.hasNext();) {
            IEventInfo entry = i.next();
            // Event: Anniversary
            if ("Anniversary".equals(entry.getEvent().getSummary())) {
                Assert.assertEquals("Anniversary", entry.getEvent().getCategories());
                Assert.assertEquals("Anniversary every year", entry.getEvent().getDescription());
                Assert.assertEquals(15, entry.getEvent().getDtStamp().get(java.util.Calendar.DAY_OF_MONTH));
                Assert.assertEquals(0, entry.getEvent().getDtStamp().get(java.util.Calendar.MONTH));
                Assert.assertEquals(2007, entry.getEvent().getDtStamp().get(java.util.Calendar.YEAR));
                // 204524
                Assert.assertEquals(20, entry.getEvent().getDtStamp().get(java.util.Calendar.HOUR_OF_DAY));
                Assert.assertEquals(45, entry.getEvent().getDtStamp().get(java.util.Calendar.MINUTE));
                Assert.assertEquals(24, entry.getEvent().getDtStamp().get(java.util.Calendar.SECOND));
                Assert.assertEquals("cb5f77cd-655d-45f9-9b78-04bb668abccc", entry.getEvent().getId());
                Assert.assertEquals(IRecurrence.RECURRENCE_ANNUALLY, entry.getEvent().getRecurrence().getType());
                Assert.assertEquals(IRecurrence.RECURRENCE_END_FOREVER, entry.getEvent().getRecurrence().getEndType());
                Assert.assertEquals("20070125", entry.getEvent().getDtStart().get(java.util.Calendar.YEAR) + "0" + (entry.getEvent().getDtStart().get(java.util.Calendar.MONTH) + 1) + "" + entry.getEvent().getDtStart().get(java.util.Calendar.DAY_OF_MONTH) + "");
                Assert.assertEquals("20070126", entry.getEvent().getDtEnd().get(java.util.Calendar.YEAR) + "0" + (entry.getEvent().getDtEnd().get(java.util.Calendar.MONTH) + 1) + "" + entry.getEvent().getDtEnd().get(java.util.Calendar.DAY_OF_MONTH) + "");
            // Event: All Day Event
            } else if ("AllDayEvent".equals(entry.getEvent().getSummary())) {
                Assert.assertEquals("Business", entry.getEvent().getCategories());
                Assert.assertEquals("I will the whole day do some work", entry.getEvent().getDescription());
                Assert.assertEquals("PUBLIC", entry.getEvent().getEventClass());
                Assert.assertEquals("5", entry.getEvent().getPriority());
                Assert.assertEquals(15, entry.getEvent().getDtStamp().get(java.util.Calendar.DAY_OF_MONTH));
                Assert.assertEquals(0, entry.getEvent().getDtStamp().get(java.util.Calendar.MONTH));
                Assert.assertEquals(2007, entry.getEvent().getDtStamp().get(java.util.Calendar.YEAR));
                Assert.assertEquals("CONFIRMED", entry.getEvent().getStatus());
                Assert.assertEquals(23, entry.getEvent().getDtStart().get(java.util.Calendar.DAY_OF_MONTH));
                Assert.assertEquals(0, entry.getEvent().getDtStart().get(java.util.Calendar.MONTH));
                Assert.assertEquals(2007, entry.getEvent().getDtStart().get(java.util.Calendar.YEAR));
                Assert.assertEquals(0, entry.getEvent().getDtStart().get(java.util.Calendar.HOUR_OF_DAY));
                Assert.assertEquals(0, entry.getEvent().getDtStart().get(java.util.Calendar.MINUTE));
                Assert.assertEquals(0, entry.getEvent().getDtStart().get(java.util.Calendar.SECOND));
                Assert.assertEquals(24, entry.getEvent().getDtEnd().get(java.util.Calendar.DAY_OF_MONTH));
                Assert.assertEquals(0, entry.getEvent().getDtEnd().get(java.util.Calendar.MONTH));
                Assert.assertEquals(2007, entry.getEvent().getDtEnd().get(java.util.Calendar.YEAR));
                Assert.assertEquals(0, entry.getEvent().getDtEnd().get(java.util.Calendar.HOUR_OF_DAY));
                Assert.assertEquals(0, entry.getEvent().getDtEnd().get(java.util.Calendar.MINUTE));
                Assert.assertEquals(0, entry.getEvent().getDtEnd().get(java.util.Calendar.SECOND));
                Assert.assertEquals("at home", entry.getEvent().getLocation());
                Assert.assertEquals("8197de12-8a20-4dd0-a13a-4e932745914e", entry.getEvent().getId());
            // Event: Normal Event
            } else if ("NormalEvent".equals(entry.getEvent().getSummary())) {
                System.out.println("Normal Event");
            }
        }
    }

    @Test
    public void testImportCalendarRecurrence() {
        File example1 = new File("src/test/resources/SunbirdRecurrendEvents.ics");
        CalendarImporter importer = new CalendarImporter();
        Iterator<IEventInfo> i = null;
        try {
            i = importer.importCalendar("example", example1);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got following exception:" + e.getMessage());
        }

        // check three entries
        for (; i.hasNext();) {
            IEventInfo entry = i.next();

        }
    }

    @Test
    public void testCorrectUid() {
        String uid = "";
        Assert.assertEquals(true, CalendarImporter.correctUid(uid).length() > 0);
        uid = "uuid:197601031$§%&§$%&&-_";
		//Assert.assertEquals("uuid-197601031----------_", CalendarImporter.correctUid(uid));
        //The previous assertion was false there where 2 extra '-' symbals
        Assert.assertEquals("uuid-197601031----------_", CalendarImporter.correctUid(uid));
    }
}
