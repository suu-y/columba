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
package org.columba.core.io;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;


public class StreamCacheTest {

	static File tempDir = new File("test_temp/");

    @Test
	public void testAdd() throws IOException {
		byte[] random = new byte[1000];
		new Random().nextBytes(random);
		
		StreamCache cache = new StreamCache(tempDir);
		
		InputStream in = cache.passiveAdd("test1",new ByteArrayInputStream(random));
		
		byte[] test = new byte[1000];

		in.read(test);		
		in.close();
		
		Assert.assertEquals(1000, cache.getActSize());
		
		in = cache.get("test1");
		in.read(test);
		in.close();
		
		for( int i=0; i<1000; i++) {
			Assert.assertEquals(test[i], random[i]);
		}
		
		cache.clear();
		
		Assert.assertEquals( 0, cache.getActSize());
		
		Assert.assertEquals( 0, tempDir.list().length);
	}

    @Test
	public void testMaxsize() throws IOException, InterruptedException {
		byte[] random1 = new byte[1000];
		byte[] random2 = new byte[1000];
		byte[] random3 = new byte[1000];
		new Random().nextBytes(random1);
		new Random().nextBytes(random2);
		new Random().nextBytes(random3);
		
		StreamCache cache = new StreamCache(tempDir, 1700);
		
		InputStream in = cache.passiveAdd("test1",new ByteArrayInputStream(random1));
		
		byte[] test = new byte[1000];

		in.read(test);		
		in.close();
		
		Assert.assertEquals(1000, cache.getActSize());

		Thread.sleep(100);

		in = cache.passiveAdd("test2", new ByteArrayInputStream(random2));
		in.read(test);		
		in.close();

		Assert.assertEquals(1000, cache.getActSize());
		Assert.assertEquals(null, cache.get("test1"));
		
		
		in = cache.get("test2");
		in.read(test);
		in.close();
		
		for( int i=0; i<1000; i++) {
			Assert.assertEquals(test[i], random2[i]);
		}

		cache.setMaxSize(2000);		
		in = cache.passiveAdd("test3", new ByteArrayInputStream(random3));
		in.read(test);		
		in.close();

		Assert.assertEquals(2000, cache.getActSize());

		in = cache.get("test3");
		in.read(test);
		in.close();
		
		for( int i=0; i<1000; i++) {
			Assert.assertEquals(test[i], random3[i]);
		}
		
		cache.clear();
		
		Assert.assertEquals( 0, cache.getActSize());
		
		Assert.assertEquals( 0, tempDir.list().length);
	}

    @AfterClass
	public static void tearDown() throws Exception {
		File[] rest = tempDir.listFiles();
		for( int i=0; i<rest.length; i++) {
			rest[i].delete();
		}
		
	}
}
