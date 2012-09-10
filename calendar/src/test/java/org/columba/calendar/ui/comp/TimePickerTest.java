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
package org.columba.calendar.ui.comp;

import org.junit.Assert;
import org.junit.Test;

public class TimePickerTest {

    @Test
    public void testValidateInput() {
        // valid inputs
        String value = "11:20";
        Assert.assertEquals(value, TimePicker.validateInput(value));
        value = "23:59";
        Assert.assertEquals(value, TimePicker.validateInput(value));
        value = "00:00";
        Assert.assertEquals(value, TimePicker.validateInput(value));
        value = "10:09";
        Assert.assertEquals(value, TimePicker.validateInput(value));
        value = "20:50";
        Assert.assertEquals(value, TimePicker.validateInput(value));

        // recoverable inputs
        value = "00:000";
        Assert.assertEquals("00:00", TimePicker.validateInput(value));
        value = "23:000";
        Assert.assertEquals("23:00", TimePicker.validateInput(value));
        value = "2:000";
        Assert.assertEquals("02:00", TimePicker.validateInput(value));
        value = "000:0";
        Assert.assertEquals("00:00", TimePicker.validateInput(value));
        value = "2";
        Assert.assertEquals("02:00", TimePicker.validateInput(value));
        value = "11";
        Assert.assertEquals("11:00", TimePicker.validateInput(value));
        value = "23";
        Assert.assertEquals("23:00", TimePicker.validateInput(value));
        value = "0:1";
        Assert.assertEquals("00:01", TimePicker.validateInput(value));
        value = "0:9";
        Assert.assertEquals("00:09", TimePicker.validateInput(value));
        value = "14:9";
        Assert.assertEquals("14:09", TimePicker.validateInput(value));

        // unrecoverable inputs
        value = "235:9";
        Assert.assertEquals("00:00", TimePicker.validateInput(value));
        value = "asdasdl_jk";
        Assert.assertEquals("00:00", TimePicker.validateInput(value));
        value = "123";
        Assert.assertEquals("00:00", TimePicker.validateInput(value));
        value = "-3:2";
        Assert.assertEquals("00:00", TimePicker.validateInput(value));
        value = "-3";
        Assert.assertEquals("00:00", TimePicker.validateInput(value));
    }
}
