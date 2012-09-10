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
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;



public class SteerableInputStreamTest{

    @Test
	public void test() throws IOException {
		byte[] test = new byte[] {0, 1, 2, 3, 4, 5 , 6, 7, 8, 9, 10};
		byte[] dummy = new byte[5];
		
		SteerableInputStream in = new SteerableInputStream(new ByteArrayInputStream(test));
		
		Assert.assertEquals(test.length,in.getLengthLeft());
		Assert.assertEquals(0, in.getPosition());
		
		in.setLengthLeft(3);
		in.setPosition(2);
		Assert.assertEquals(1,in.getLengthLeft());
		Assert.assertEquals(2, in.getPosition());
		
		Assert.assertEquals(2, in.read());
		Assert.assertEquals(0,in.getLengthLeft());
		Assert.assertEquals(-1, in.read());
		Assert.assertEquals(0,in.read(dummy));
		
		in.setLengthLeft(100);
		Assert.assertEquals(test.length - in.getPosition(), in.getLengthLeft());
		Assert.assertEquals(3, in.read());
		
		Assert.assertEquals(3,in.read(dummy,2,3));
		Assert.assertEquals(4,dummy[2]);
		Assert.assertEquals(6,dummy[4]);
		
		Assert.assertEquals(4,in.read(dummy));
		Assert.assertEquals(10, dummy[3]);
		
	}

}
