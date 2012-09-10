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
package org.columba.calendar.ui.calendar;

import com.miginfocom.util.dates.DateRange;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class MainCalendarControllerTest {

    @BeforeClass
    public static void setup() {
        System.out.println("TimeZone is:" + TimeZone.getDefault());
        TimeZone t = new SimpleTimeZone(0, "GMT");
        TimeZone.setDefault(t);
        System.out.println("TimeZone is:" + TimeZone.getDefault());
    }

    private DateRange resetClock(DateRange in) {
        DateRange range = in;
        range.setStartField(java.util.Calendar.HOUR_OF_DAY, 0);
        range.setStartField(java.util.Calendar.MINUTE, 0);
        range.setStartField(java.util.Calendar.SECOND, 0);
        range.setStartField(java.util.Calendar.MILLISECOND, 0);
        range.setEndField(java.util.Calendar.HOUR_OF_DAY, 23);
        range.setEndField(java.util.Calendar.MINUTE, 59);
        range.setEndField(java.util.Calendar.SECOND, 59);
        range.setEndField(java.util.Calendar.MILLISECOND, 999);
        return range;
    }

    // FIXME missing full tests for leap year

    // ************
    // normal cases
    // ************
    @Test
    public void testRollWithYearsNormalCaseDay() {

        DateRange range = new DateRange();
        range.setStartField(java.util.Calendar.YEAR, 2007);
        range.setStartField(java.util.Calendar.MONTH, 0);
        range.setStartField(java.util.Calendar.DAY_OF_YEAR, 1);

        range.setEndField(java.util.Calendar.YEAR, 2007);
        range.setEndField(java.util.Calendar.MONTH, 0);
        range.setEndField(java.util.Calendar.DAY_OF_YEAR, 1);
        range = resetClock(range);

        MainCalendarController.rollWithYears(range, java.util.Calendar.DAY_OF_YEAR, 1);

        Assert.assertEquals(2007, range.getStartField(java.util.Calendar.YEAR));
        Assert.assertEquals(0, range.getStartField(java.util.Calendar.MONTH));
        Assert.assertEquals(2, range.getStartField(java.util.Calendar.DAY_OF_YEAR));

        Assert.assertEquals(2007, range.getEndField(java.util.Calendar.YEAR));
        Assert.assertEquals(0, range.getEndField(java.util.Calendar.MONTH));
        Assert.assertEquals(2, range.getEndField(java.util.Calendar.DAY_OF_YEAR));

    }

    @Test
    public void testRollWithYearsNormalCaseDayNegative() {

        DateRange range = new DateRange();
        range.setStartField(java.util.Calendar.YEAR, 2007);
        range.setStartField(java.util.Calendar.MONTH, 0);
        range.setStartField(java.util.Calendar.DAY_OF_YEAR, 2);

        range.setEndField(java.util.Calendar.YEAR, 2007);
        range.setEndField(java.util.Calendar.MONTH, 0);
        range.setEndField(java.util.Calendar.DAY_OF_YEAR, 2);

        MainCalendarController.rollWithYears(range, java.util.Calendar.DAY_OF_YEAR, -1);

        Assert.assertEquals(2007, range.getStartField(java.util.Calendar.YEAR));
        Assert.assertEquals(0, range.getStartField(java.util.Calendar.MONTH));
        Assert.assertEquals(1, range.getStartField(java.util.Calendar.DAY_OF_YEAR));

        Assert.assertEquals(2007, range.getEndField(java.util.Calendar.YEAR));
        Assert.assertEquals(0, range.getEndField(java.util.Calendar.MONTH));
        Assert.assertEquals(1, range.getEndField(java.util.Calendar.DAY_OF_YEAR));

    }

    @Test
    public void testRollWithYearsNormalCaseWeekNegative() {

        DateRange range = new DateRange();
        range.setStartField(java.util.Calendar.YEAR, 2007);
        range.setStartField(java.util.Calendar.MONTH, 0);
        range.setStartField(java.util.Calendar.DAY_OF_YEAR, 8);

        range.setEndField(java.util.Calendar.YEAR, 2007);
        range.setEndField(java.util.Calendar.MONTH, 0);
        range.setEndField(java.util.Calendar.DAY_OF_YEAR, 14);

        MainCalendarController.rollWithYears(range, java.util.Calendar.WEEK_OF_YEAR, -1);

        Assert.assertEquals(2007, range.getStartField(java.util.Calendar.YEAR));
        Assert.assertEquals(0, range.getStartField(java.util.Calendar.MONTH));
        Assert.assertEquals(1, range.getStartField(java.util.Calendar.DAY_OF_YEAR));

        Assert.assertEquals(2007, range.getEndField(java.util.Calendar.YEAR));
        Assert.assertEquals(0, range.getEndField(java.util.Calendar.MONTH));
        Assert.assertEquals(7, range.getEndField(java.util.Calendar.DAY_OF_YEAR));

    }

    // ***********
    // edge values
    // ***********
    @Test
    public void testRollWithYearsFirstDayCaseDayNegative() {

        DateRange range = new DateRange();
        range.setStartField(java.util.Calendar.YEAR, 2007);
        range.setStartField(java.util.Calendar.MONTH, 0);
        range.setStartField(java.util.Calendar.DAY_OF_YEAR, 1);

        range.setEndField(java.util.Calendar.YEAR, 2007);
        range.setEndField(java.util.Calendar.MONTH, 0);
        range.setEndField(java.util.Calendar.DAY_OF_YEAR, 1);

        MainCalendarController.rollWithYears(range, java.util.Calendar.DAY_OF_YEAR, -1);

        Assert.assertEquals(2006, range.getStartField(java.util.Calendar.YEAR));
        Assert.assertEquals(11, range.getStartField(java.util.Calendar.MONTH));
        Assert.assertEquals(365, range.getStartField(java.util.Calendar.DAY_OF_YEAR));

        Assert.assertEquals(2006, range.getEndField(java.util.Calendar.YEAR));
        Assert.assertEquals(11, range.getEndField(java.util.Calendar.MONTH));
        Assert.assertEquals(365, range.getEndField(java.util.Calendar.DAY_OF_YEAR));

    }

    @Test
    public void testRollWithYearsLastDayCaseDay() {

        DateRange range = new DateRange();
        range.setStartField(java.util.Calendar.YEAR, 2006);
        range.setStartField(java.util.Calendar.MONTH, 11);
        range.setStartField(java.util.Calendar.DAY_OF_YEAR, 365);

        range.setEndField(java.util.Calendar.YEAR, 2006);
        range.setEndField(java.util.Calendar.MONTH, 11);
        range.setEndField(java.util.Calendar.DAY_OF_YEAR, 365);

        MainCalendarController.rollWithYears(range, java.util.Calendar.DAY_OF_YEAR, 1);

        Assert.assertEquals(2007, range.getStartField(java.util.Calendar.YEAR));
        Assert.assertEquals(0, range.getStartField(java.util.Calendar.MONTH));
        Assert.assertEquals(1, range.getStartField(java.util.Calendar.DAY_OF_YEAR));

        Assert.assertEquals(2007, range.getEndField(java.util.Calendar.YEAR));
        Assert.assertEquals(0, range.getEndField(java.util.Calendar.MONTH));
        Assert.assertEquals(1, range.getEndField(java.util.Calendar.DAY_OF_YEAR));

    }

    @Test
    public void testRollWithYearsFirstDayCaseWeekNegative() {

        DateRange range = new DateRange();
        range.setStartField(java.util.Calendar.YEAR, 2007);
        range.setStartField(java.util.Calendar.MONTH, 0);
        range.setStartField(java.util.Calendar.DAY_OF_YEAR, 1);

        range.setEndField(java.util.Calendar.YEAR, 2007);
        range.setEndField(java.util.Calendar.MONTH, 0);
        range.setEndField(java.util.Calendar.DAY_OF_YEAR, 7);

        MainCalendarController.rollWithYears(range, java.util.Calendar.WEEK_OF_YEAR, -1);

        Assert.assertEquals(2006, range.getStartField(java.util.Calendar.YEAR));
        Assert.assertEquals(11, range.getStartField(java.util.Calendar.MONTH));
        Assert.assertEquals(359, range.getStartField(java.util.Calendar.DAY_OF_YEAR));

        Assert.assertEquals(2006, range.getEndField(java.util.Calendar.YEAR));
        Assert.assertEquals(11, range.getEndField(java.util.Calendar.MONTH));
        Assert.assertEquals(365, range.getEndField(java.util.Calendar.DAY_OF_YEAR));

    }

    @Test
    public void testRollWithYearsLastDayCaseWeek() {

        DateRange range = new DateRange();
        range.setStartField(java.util.Calendar.YEAR, 2006);
        range.setStartField(java.util.Calendar.MONTH, 11);
        range.setStartField(java.util.Calendar.DAY_OF_YEAR, 359);

        range.setEndField(java.util.Calendar.YEAR, 2006);
        range.setEndField(java.util.Calendar.MONTH, 11);
        range.setEndField(java.util.Calendar.DAY_OF_YEAR, 365);

        MainCalendarController.rollWithYears(range, java.util.Calendar.WEEK_OF_YEAR, 1);

        Assert.assertEquals(2007, range.getStartField(java.util.Calendar.YEAR));
        Assert.assertEquals(0, range.getStartField(java.util.Calendar.MONTH));
        Assert.assertEquals(1, range.getStartField(java.util.Calendar.DAY_OF_YEAR));

        Assert.assertEquals(2007, range.getEndField(java.util.Calendar.YEAR));
        Assert.assertEquals(0, range.getEndField(java.util.Calendar.MONTH));
        Assert.assertEquals(7, range.getEndField(java.util.Calendar.DAY_OF_YEAR));

    }

    // ******************
    // more than one year
    // ******************
    @Test
    public void testRollWithYearsFirstDayCaseDayNegativeMore() {

        DateRange range = new DateRange();
        range.setStartField(java.util.Calendar.YEAR, 2007);
        range.setStartField(java.util.Calendar.MONTH, 0);
        range.setStartField(java.util.Calendar.DAY_OF_YEAR, 1);

        range.setEndField(java.util.Calendar.YEAR, 2007);
        range.setEndField(java.util.Calendar.MONTH, 0);
        range.setEndField(java.util.Calendar.DAY_OF_YEAR, 1);

        MainCalendarController.rollWithYears(range, java.util.Calendar.DAY_OF_YEAR, -366);

        Assert.assertEquals(2005, range.getStartField(java.util.Calendar.YEAR));
        Assert.assertEquals(11, range.getStartField(java.util.Calendar.MONTH));
        Assert.assertEquals(365, range.getStartField(java.util.Calendar.DAY_OF_YEAR));

        Assert.assertEquals(2005, range.getEndField(java.util.Calendar.YEAR));
        Assert.assertEquals(11, range.getEndField(java.util.Calendar.MONTH));
        Assert.assertEquals(365, range.getEndField(java.util.Calendar.DAY_OF_YEAR));

    }

    @Test
    public void testRollWithYearsLastDayCaseDayMore() {

        DateRange range = new DateRange();
        range.setStartField(java.util.Calendar.YEAR, 2006);
        range.setStartField(java.util.Calendar.MONTH, 11);
        range.setStartField(java.util.Calendar.DAY_OF_YEAR, 365);

        range.setEndField(java.util.Calendar.YEAR, 2006);
        range.setEndField(java.util.Calendar.MONTH, 11);
        range.setEndField(java.util.Calendar.DAY_OF_YEAR, 365);

        MainCalendarController.rollWithYears(range, java.util.Calendar.DAY_OF_YEAR, 366);

        Assert.assertEquals(2008, range.getStartField(java.util.Calendar.YEAR));
        Assert.assertEquals(0, range.getStartField(java.util.Calendar.MONTH));
        Assert.assertEquals(1, range.getStartField(java.util.Calendar.DAY_OF_YEAR));

        Assert.assertEquals(2008, range.getEndField(java.util.Calendar.YEAR));
        Assert.assertEquals(0, range.getEndField(java.util.Calendar.MONTH));
        Assert.assertEquals(1, range.getEndField(java.util.Calendar.DAY_OF_YEAR));

    }

    @Test
    public void testRollWithYearsFirstDayCaseWeekNegativeMore() {

        DateRange range = new DateRange();
        range.setStartField(java.util.Calendar.YEAR, 2007);
        range.setStartField(java.util.Calendar.MONTH, 0);
        range.setStartField(java.util.Calendar.DAY_OF_YEAR, 1);

        range.setEndField(java.util.Calendar.YEAR, 2007);
        range.setEndField(java.util.Calendar.MONTH, 0);
        range.setEndField(java.util.Calendar.DAY_OF_YEAR, 7);

        MainCalendarController.rollWithYears(range, java.util.Calendar.WEEK_OF_YEAR, -54);

        Assert.assertEquals(2005, range.getStartField(java.util.Calendar.YEAR));
        Assert.assertEquals(11, range.getStartField(java.util.Calendar.MONTH));
        Assert.assertEquals(353, range.getStartField(java.util.Calendar.DAY_OF_YEAR));

        Assert.assertEquals(2005, range.getEndField(java.util.Calendar.YEAR));
        Assert.assertEquals(11, range.getEndField(java.util.Calendar.MONTH));
        Assert.assertEquals(359, range.getEndField(java.util.Calendar.DAY_OF_YEAR));

    }

    @Test
    public void testRollWithYearsLastDayCaseWeekMore() {

        DateRange range = new DateRange();
        range.setStartField(java.util.Calendar.YEAR, 2006);
        range.setStartField(java.util.Calendar.MONTH, 11);
        range.setStartField(java.util.Calendar.DAY_OF_YEAR, 359);

        range.setEndField(java.util.Calendar.YEAR, 2006);
        range.setEndField(java.util.Calendar.MONTH, 11);
        range.setEndField(java.util.Calendar.DAY_OF_YEAR, 365);

        MainCalendarController.rollWithYears(range, java.util.Calendar.WEEK_OF_YEAR, 53);

        Assert.assertEquals(2007, range.getStartField(java.util.Calendar.YEAR));
        Assert.assertEquals(11, range.getStartField(java.util.Calendar.MONTH));
        Assert.assertEquals(365, range.getStartField(java.util.Calendar.DAY_OF_YEAR));

        Assert.assertEquals(2008, range.getEndField(java.util.Calendar.YEAR));
        Assert.assertEquals(0, range.getEndField(java.util.Calendar.MONTH));
        Assert.assertEquals(6, range.getEndField(java.util.Calendar.DAY_OF_YEAR));

    }

    @Test
    public void testROllWithYearsViewToday() {

        DateRange range = new DateRange();
        range.setStartField(java.util.Calendar.YEAR, 2011);
        range.setStartField(java.util.Calendar.MONTH, 1);
        range.setStartField(java.util.Calendar.DAY_OF_YEAR, 37);

        range.setEndField(java.util.Calendar.YEAR, 2011);
        range.setEndField(java.util.Calendar.MONTH, 2);
        range.setEndField(java.util.Calendar.DAY_OF_YEAR, 85);

        MainCalendarController.rollWithYears(range, java.util.Calendar.DAY_OF_YEAR, -1510);

        Assert.assertEquals(2006, range.getStartField(java.util.Calendar.YEAR));
        Assert.assertEquals(11, range.getStartField(java.util.Calendar.MONTH));
        Assert.assertEquals(352, range.getStartField(java.util.Calendar.DAY_OF_YEAR));

        Assert.assertEquals(2007, range.getEndField(java.util.Calendar.YEAR));
        Assert.assertEquals(1, range.getEndField(java.util.Calendar.MONTH));
        Assert.assertEquals(35, range.getEndField(java.util.Calendar.DAY_OF_YEAR));

    }

    @Test
    public void testPlusYears() {

        DateRange range = new DateRange();
        range.setStartField(java.util.Calendar.YEAR, 2005);
        range.setStartField(java.util.Calendar.MONTH, 11);
        range.setStartField(java.util.Calendar.DAY_OF_YEAR, 351);

        range.setEndField(java.util.Calendar.YEAR, 2005);
        range.setEndField(java.util.Calendar.MONTH, 11);
        range.setEndField(java.util.Calendar.DAY_OF_YEAR, 361);

        MainCalendarController.rollWithYears(range, java.util.Calendar.DAY_OF_YEAR, 379);

        Assert.assertEquals(2006, range.getStartField(java.util.Calendar.YEAR));
        Assert.assertEquals(11, range.getStartField(java.util.Calendar.MONTH));
        Assert.assertEquals(365, range.getStartField(java.util.Calendar.DAY_OF_YEAR));

        Assert.assertEquals(2007, range.getEndField(java.util.Calendar.YEAR));
        Assert.assertEquals(0, range.getEndField(java.util.Calendar.MONTH));
        Assert.assertEquals(10, range.getEndField(java.util.Calendar.DAY_OF_YEAR));

    }

    // ***************
    // MONTH VIEW TEST
    // ***************
    @Test
    public void testRollWithYearsLastDayCaseMonth() {

        DateRange range = new DateRange();
        range.setStartField(java.util.Calendar.YEAR, 2006);
        range.setStartField(java.util.Calendar.MONTH, 10);
        range.setStartField(java.util.Calendar.DAY_OF_YEAR, 329);

        range.setEndField(java.util.Calendar.YEAR, 2006);
        range.setEndField(java.util.Calendar.MONTH, 11);
        range.setEndField(java.util.Calendar.DAY_OF_YEAR, 365);

        MainCalendarController.rollWithYears(range, java.util.Calendar.WEEK_OF_YEAR, 1);

        Assert.assertEquals(2006, range.getStartField(java.util.Calendar.YEAR));
        Assert.assertEquals(11, range.getStartField(java.util.Calendar.MONTH));
        Assert.assertEquals(336, range.getStartField(java.util.Calendar.DAY_OF_YEAR));

        Assert.assertEquals(2007, range.getEndField(java.util.Calendar.YEAR));
        Assert.assertEquals(0, range.getEndField(java.util.Calendar.MONTH));
        Assert.assertEquals(7, range.getEndField(java.util.Calendar.DAY_OF_YEAR));

    }

    @Test
    public void testRollWithYearsLastDayCaseBack() {

        DateRange range = new DateRange();
        range.setStartField(java.util.Calendar.YEAR, 2006);
        range.setStartField(java.util.Calendar.MONTH, 10);
        range.setStartField(java.util.Calendar.DAY_OF_YEAR, 334);

        range.setEndField(java.util.Calendar.YEAR, 2007);
        range.setEndField(java.util.Calendar.MONTH, 0);
        range.setEndField(java.util.Calendar.DAY_OF_YEAR, 7);

        MainCalendarController.rollWithYears(range, java.util.Calendar.WEEK_OF_YEAR, -1);

        Assert.assertEquals(2006, range.getStartField(java.util.Calendar.YEAR));
        Assert.assertEquals(10, range.getStartField(java.util.Calendar.MONTH));
        Assert.assertEquals(327, range.getStartField(java.util.Calendar.DAY_OF_YEAR));

        Assert.assertEquals(2006, range.getEndField(java.util.Calendar.YEAR));
        Assert.assertEquals(11, range.getEndField(java.util.Calendar.MONTH));
        Assert.assertEquals(365, range.getEndField(java.util.Calendar.DAY_OF_YEAR));

    }

    @Test
    public void testRollWithYearsLastDayCaseForward() {

        DateRange range = new DateRange();
        range.setStartField(java.util.Calendar.YEAR, 2007);
        range.setStartField(java.util.Calendar.MONTH, 11);
        range.setStartField(java.util.Calendar.DAY_OF_YEAR, 358);

        range.setEndField(java.util.Calendar.YEAR, 2008);
        range.setEndField(java.util.Calendar.MONTH, 1);
        range.setEndField(java.util.Calendar.DAY_OF_YEAR, 33);

        MainCalendarController.rollWithYears(range, java.util.Calendar.WEEK_OF_YEAR, 1);

        Assert.assertEquals(2007, range.getStartField(java.util.Calendar.YEAR));
        Assert.assertEquals(11, range.getStartField(java.util.Calendar.MONTH));
        Assert.assertEquals(365, range.getStartField(java.util.Calendar.DAY_OF_YEAR));

        Assert.assertEquals(2008, range.getEndField(java.util.Calendar.YEAR));
        Assert.assertEquals(1, range.getEndField(java.util.Calendar.MONTH));
        Assert.assertEquals(40, range.getEndField(java.util.Calendar.DAY_OF_YEAR));

    }

    @Test
    public void testRollWithYearsLastDayCaseForwardTwo() {

        DateRange range = new DateRange();
        range.setStartField(java.util.Calendar.YEAR, 2004);
        range.setStartField(java.util.Calendar.MONTH, 11);
        range.setStartField(java.util.Calendar.DAY_OF_YEAR, 361);

        range.setEndField(java.util.Calendar.YEAR, 2005);
        range.setEndField(java.util.Calendar.MONTH, 0);
        range.setEndField(java.util.Calendar.DAY_OF_YEAR, 29);

        range = resetClock(range);

        MainCalendarController.rollWithYears(range, java.util.Calendar.WEEK_OF_YEAR, 1);

        Assert.assertEquals(2005, range.getStartField(java.util.Calendar.YEAR));
        Assert.assertEquals(0, range.getStartField(java.util.Calendar.MONTH));
        Assert.assertEquals(2, range.getStartField(java.util.Calendar.DAY_OF_YEAR));

        Assert.assertEquals(2005, range.getEndField(java.util.Calendar.YEAR));
        Assert.assertEquals(1, range.getEndField(java.util.Calendar.MONTH));
        Assert.assertEquals(36, range.getEndField(java.util.Calendar.DAY_OF_YEAR));

    }

    @Test
    public void testRollWithYearsLastDayCaseBackTwo() {

        DateRange range = new DateRange();
        range.setStartField(java.util.Calendar.YEAR, 2005);
        range.setStartField(java.util.Calendar.MONTH, 0);
        range.setStartField(java.util.Calendar.DAY_OF_YEAR, 2);

        range.setEndField(java.util.Calendar.YEAR, 2005);
        range.setEndField(java.util.Calendar.MONTH, 1);
        range.setEndField(java.util.Calendar.DAY_OF_YEAR, 43);

        range = resetClock(range);

        MainCalendarController.rollWithYears(range, java.util.Calendar.WEEK_OF_YEAR, -1);

        Assert.assertEquals(2004, range.getStartField(java.util.Calendar.YEAR));
        Assert.assertEquals(11, range.getStartField(java.util.Calendar.MONTH));
        Assert.assertEquals(361, range.getStartField(java.util.Calendar.DAY_OF_YEAR));

        Assert.assertEquals(2005, range.getEndField(java.util.Calendar.YEAR));
        Assert.assertEquals(1, range.getEndField(java.util.Calendar.MONTH));
        Assert.assertEquals(36, range.getEndField(java.util.Calendar.DAY_OF_YEAR));

    }

    @Test
    public void testRollWithYearsLastDayCaseForwardThree() {

        DateRange range = new DateRange();
        range.setStartField(java.util.Calendar.YEAR, 2007);
        range.setStartField(java.util.Calendar.MONTH, 11);
        range.setStartField(java.util.Calendar.DAY_OF_YEAR, 357);

        range.setEndField(java.util.Calendar.YEAR, 2008);
        range.setEndField(java.util.Calendar.MONTH, 1);
        range.setEndField(java.util.Calendar.DAY_OF_YEAR, 33);

        range = resetClock(range);

        MainCalendarController.rollWithYears(range, java.util.Calendar.WEEK_OF_YEAR, 1);

        Assert.assertEquals(2007, range.getStartField(java.util.Calendar.YEAR));
        Assert.assertEquals(11, range.getStartField(java.util.Calendar.MONTH));
        Assert.assertEquals(364, range.getStartField(java.util.Calendar.DAY_OF_YEAR));

        Assert.assertEquals(2008, range.getEndField(java.util.Calendar.YEAR));
        Assert.assertEquals(1, range.getEndField(java.util.Calendar.MONTH));
        Assert.assertEquals(40, range.getEndField(java.util.Calendar.DAY_OF_YEAR));

    }

    @Test
    public void testRollWithYearsNormalCaseWeek() {

        DateRange range = new DateRange();
        range.setStartField(java.util.Calendar.YEAR, 2007);
        range.setStartField(java.util.Calendar.MONTH, 0);
        range.setStartField(java.util.Calendar.DAY_OF_YEAR, 1);

        range.setEndField(java.util.Calendar.YEAR, 2007);
        range.setEndField(java.util.Calendar.MONTH, 0);
        range.setEndField(java.util.Calendar.DAY_OF_YEAR, 7);

        MainCalendarController.rollWithYears(range, java.util.Calendar.WEEK_OF_YEAR, 1);

        Assert.assertEquals(2007, range.getStartField(java.util.Calendar.YEAR));
        Assert.assertEquals(0, range.getStartField(java.util.Calendar.MONTH));
        Assert.assertEquals(8, range.getStartField(java.util.Calendar.DAY_OF_YEAR));

        Assert.assertEquals(2007, range.getEndField(java.util.Calendar.YEAR));
        Assert.assertEquals(0, range.getEndField(java.util.Calendar.MONTH));
        Assert.assertEquals(14, range.getEndField(java.util.Calendar.DAY_OF_YEAR));

    }

    @Test
    public void testRollWithYearsOverMonthEndWeek1() {

        DateRange range = new DateRange();
        range.setStartField(java.util.Calendar.YEAR, 2009);
        range.setStartField(java.util.Calendar.MONTH, 0);
        range.setStartField(java.util.Calendar.DAY_OF_YEAR, 1);

        range.setEndField(java.util.Calendar.YEAR, 2009);
        range.setEndField(java.util.Calendar.MONTH, 1);
        range.setEndField(java.util.Calendar.DAY_OF_YEAR, 45);

        range = resetClock(range);

        MainCalendarController.rollWithYears(range, java.util.Calendar.WEEK_OF_YEAR, 1);

        Assert.assertEquals(2009, range.getStartField(java.util.Calendar.YEAR));
        Assert.assertEquals(0, range.getStartField(java.util.Calendar.MONTH));
        Assert.assertEquals(8, range.getStartField(java.util.Calendar.DAY_OF_YEAR));

        Assert.assertEquals(2009, range.getEndField(java.util.Calendar.YEAR));
        Assert.assertEquals(1, range.getEndField(java.util.Calendar.MONTH));
        Assert.assertEquals(52, range.getEndField(java.util.Calendar.DAY_OF_YEAR));

    }

    @Test
    public void testRollWithYearsOverMonthEndWeek2() {

        DateRange range = new DateRange();
        range.setStartField(java.util.Calendar.YEAR, 2009);
        range.setStartField(java.util.Calendar.MONTH, 0);
        range.setStartField(java.util.Calendar.DAY_OF_YEAR, 1);

        range.setEndField(java.util.Calendar.YEAR, 2009);
        range.setEndField(java.util.Calendar.MONTH, 3);
        range.setEndField(java.util.Calendar.DAY_OF_YEAR, 95);

        range = resetClock(range);

        MainCalendarController.rollWithYears(range, java.util.Calendar.WEEK_OF_YEAR, 1);

        Assert.assertEquals(2009, range.getStartField(java.util.Calendar.YEAR));
        Assert.assertEquals(0, range.getStartField(java.util.Calendar.MONTH));
        Assert.assertEquals(8, range.getStartField(java.util.Calendar.DAY_OF_YEAR));

        Assert.assertEquals(2009, range.getEndField(java.util.Calendar.YEAR));
        Assert.assertEquals(3, range.getEndField(java.util.Calendar.MONTH));
        Assert.assertEquals(102, range.getEndField(java.util.Calendar.DAY_OF_YEAR));

    }


    // new vis range = Start: Sep 23, 2007 12:00:00 AM (RAW:1.0, DST:-1.0)   End: Nov 3, 2007 11:59:59 PM (RAW:1.0, DST:0.0)  Time zone: null  Locale: null
    // new vis range = Start: Oct 1, 2007 12:00:00 AM (RAW:1.0, DST:-1.0)   End: Nov 11, 2007 11:59:59 PM (RAW:1.0, DST:0.0)  Time zone: null  Locale: null
    // should be
    // new vis range = Start: Oct 1, 2007 12:00:00 AM (RAW:1.0, DST:-1.0)   End: Nov 10, 2007 11:59:59 PM (RAW:1.0, DST:0.0)  Time zone: null  Locale: null
    public void testRollWithYearsLastDayCaseForwardFour() {

        DateRange range = new DateRange();
        range.setStartField(java.util.Calendar.YEAR, 2007);
        range.setStartField(java.util.Calendar.MONTH, 8);
        range.setStartField(java.util.Calendar.DAY_OF_YEAR, 266);

        range.setEndField(java.util.Calendar.YEAR, 2007);
        range.setEndField(java.util.Calendar.MONTH, 10);
        range.setEndField(java.util.Calendar.DAY_OF_YEAR, 307);

        range = resetClock(range);

        MainCalendarController.rollWithYears(range, java.util.Calendar.WEEK_OF_YEAR, 1);

        Assert.assertEquals(2007, range.getStartField(java.util.Calendar.YEAR));
        Assert.assertEquals(8, range.getStartField(java.util.Calendar.MONTH));
        Assert.assertEquals(273, range.getStartField(java.util.Calendar.DAY_OF_YEAR));

        Assert.assertEquals(2007, range.getEndField(java.util.Calendar.YEAR));
        Assert.assertEquals(10, range.getEndField(java.util.Calendar.MONTH));
        Assert.assertEquals(314, range.getEndField(java.util.Calendar.DAY_OF_YEAR));

    }

    // java bugs
    // http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4197699
    //
    // For JDK1.2.1_003, WEEK_OF_YEAR returns 1 instead of 53 for 12/31/1999 and also
    // 1 instead of 54 for 12/31/2000, (It returns 1 for 1/1/1999 and 1/1/2000).
    @Test
    public void testWeekFiftyThree() {

        DateRange range = new DateRange();
        range.setStartField(java.util.Calendar.YEAR, 2004);
        range.setStartField(java.util.Calendar.MONTH, 11);
        range.setStartField(java.util.Calendar.DAY_OF_YEAR, 365);

        Assert.assertEquals(53, MainCalendarController.getWeekOfYear(range.getStart()));

    }

    @Test
    public void testWeekFiftyFour() {

        int[] fiftythree = new int[]{1970, 1976, 1987, 1992, 1998};

        for (int i = 0; i < fiftythree.length; i++) {

            DateRange range = new DateRange();
            range.setStartField(java.util.Calendar.YEAR, fiftythree[i]);
            range.setStartField(java.util.Calendar.MONTH, 11);
            range.setStartField(java.util.Calendar.DAY_OF_YEAR, 365);

            Assert.assertEquals(53, MainCalendarController.getWeekOfYear(range.getStart()));

        }

    }

    @Test
    public void testNonFiftyThree() {
        DateRange range = new DateRange();
        range.setStartField(java.util.Calendar.YEAR, 2005);
        range.setStartField(java.util.Calendar.MONTH, 0);
        range.setStartField(java.util.Calendar.DAY_OF_YEAR, 2);

        Assert.assertEquals(1, MainCalendarController.getWeekOfYear(range.getStart()));
    }
}
