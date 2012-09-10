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
 * Created on 2003-nov-02
 */
package org.columba.core.gui.util;

import java.awt.Color;

import org.columba.core.gui.base.ColorFactory;
import org.junit.Assert;
import org.junit.Test;


/**
 * @author Erik Mattsson
 */
public class ColorFactoryTest {
    /*
 * Test for getColor()
 */
    @Test
    public void testGetColor() {
        ColorFactory.clear();

        Color col1 = ColorFactory.getColor(0);
        Assert.assertNotNull("The factory returned a null object", col1);

        Color col2 = ColorFactory.getColor(1);
        Assert.assertNotNull("The factory returned a null object", col2);
        Assert.assertNotSame("The factory returned the same object for different values",
            col1, col2);

        Color col1again = ColorFactory.getColor(0);
        Assert.assertNotNull("The factory returned a null object", col1again);
        Assert.assertSame("The factory did not return the same object for a value",
            col1, col1again);
    }
}
