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

import org.columba.core.base.Lock;
import org.junit.Assert;
import org.junit.Test;

public class LockTest{

	public boolean testBool;
	public int testInt;
	
	@Test
	public void test1() throws InterruptedException {
		final Lock testLock = new Lock();
		testBool = false;
		
		Assert.assertTrue(testLock.tryToGetLock(this));
		
		Thread t = new Thread() {
            @Override
			public void run() {
				testLock.getLock(this);
				Assert.assertTrue(testBool);
			}
		};
		
		t.start();
		
		Thread.sleep(100);
		
		testBool = true;
		testLock.release(this);
		
		t.join(100);
		Assert.assertFalse(t.isAlive());
		
		Assert.assertFalse(testLock.tryToGetLock(this));
		testLock.release(t);
		Assert.assertTrue(testLock.tryToGetLock(this));
	}

    @Test
	public void test2() throws InterruptedException {
		final Lock testLock = new Lock();
		testInt = 0;
		
		Assert.assertTrue(testLock.tryToGetLock(this));
		
		Thread t1 = new Thread() {
            @Override
			public void run() {
				testLock.getLock(this);
				testInt++;
				testLock.release(this);
			}
		};
		
		Thread t2 = new Thread() {
            @Override
			public void run() {
				testLock.getLock(this);
				testInt++;
				testLock.release(this);
			}
		};

		t1.start();
		t2.start();
		
		Thread.sleep(100);
		
		testLock.release(this);
		
		t1.join(100);
		t2.join(100);
		
		Assert.assertEquals(2,testInt);
	}
	
}
