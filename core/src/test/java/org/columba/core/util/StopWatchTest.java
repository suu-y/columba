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
package org.columba.core.util;

import org.columba.core.base.StopWatch;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for the StopWatch class.
 * Note that this test relies heavily on time and should not be run at the same time
 * as a huge workload, if that is the case then some of the tests will fail.
 *
 * @author redsolo
 */
public class StopWatchTest {

    /**
     * Tests to start the StopWatch and then stop it.
     * @throws InterruptedException thrown if a <code>Thread.sleep()</code> fail.
     */
    @Test
    public void testStop() throws InterruptedException {
        StopWatch watch = new StopWatch();
        StopWatch watch2 = new StopWatch();
        watch2.stop();

        Thread.sleep(50);

        long difference = watch.getTiming() - watch2.getTiming();

        if (Math.abs(difference) < 40) {
            Assert.fail("Stopwatch wasnt stopped.");
        }
    }

    /**
     * Tests to restarts the StopWatch and then stop it.
     * @throws InterruptedException thrown if a <code>Thread.sleep()</code> fail.
     */
    @Test
    public void testStart() throws InterruptedException {
        StopWatch watch = new StopWatch();
        StopWatch watch2 = new StopWatch();
        watch.stop();
        watch2.stop();
        watch2.start();

        Thread.sleep(50);

        long difference = watch.getTiming() - watch2.getTiming();

        if (Math.abs(difference) < 40) {
            Assert.fail("Stopwatch wasnt restarted correctly.");
        }
    }

    /**
     * Test to start the timer and stop it, and see that it returns a valid time.
     * @throws InterruptedException thrown if the sleep was interrupted.
     */
    @Test
    public void testTiming() throws InterruptedException {
        StopWatch watch = new StopWatch();

        Thread.sleep(100);
        watch.stop();

        if (watch.getTiming() < 100) {
            Assert.fail("Stopwatch returned too small value. expected > 100 but was <" +
                    watch.getTiming() + ">");
        }
    }
}
