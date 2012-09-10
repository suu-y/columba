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

package org.columba.mail.main;

import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class MessageOptionParserTest {

    @Test
	public void testSingle() {
		String input = "to=test@star.de";
		Map result = MessageOptionParser.parse(input);
		
		Assert.assertEquals(1, result.keySet().size());
		Assert.assertEquals("test@star.de", result.get("to"));
	}

    @Test
	public void testSingleQuoted() {
		String input = "\"to=test@star.de\"";
		Map result = MessageOptionParser.parse(input);
		
		Assert.assertEquals(1, result.keySet().size());
		Assert.assertEquals("test@star.de", result.get("to"));
	}

    @Test
	public void testSingleEscaped() {
		String input = "subject=\\'High\\' comma!";
		Map result = MessageOptionParser.parse(input);
		
		Assert.assertEquals(1, result.keySet().size());
		Assert.assertEquals("'High' comma!", result.get("subject"));
	}

    @Test
	public void testMultiple() {
		String input = "to=test@star.de,subject=this is amazing!";
		Map result = MessageOptionParser.parse(input);
		
		Assert.assertEquals(2, result.keySet().size());
		Assert.assertEquals("test@star.de", result.get("to"));
		Assert.assertEquals("this is amazing!", result.get("subject") );
	}

    @Test
	public void testSingleWithMultiValue() {
		String input = "to='test@star.de,toast@star.de'";
		Map result = MessageOptionParser.parse(input);
		
		Assert.assertEquals(1, result.keySet().size());
		Assert.assertEquals(2,((String[])result.get("to")).length);
		Assert.assertEquals("test@star.de", ((String[])result.get("to"))[0] );
		Assert.assertEquals("toast@star.de", ((String[])result.get("to"))[1] );
	}

    @Test
	public void testMultipleWithMultiValue() {
		String input = "to='test@star.de,toast@star.de',from='test@star.de,toast@star.de'";
		Map result = MessageOptionParser.parse(input);
		
		Assert.assertEquals(2, result.keySet().size());
		Assert.assertEquals(2,((String[])result.get("to")).length);
		Assert.assertEquals("test@star.de", ((String[])result.get("to"))[0] );
		Assert.assertEquals("toast@star.de", ((String[])result.get("to"))[1] );

		Assert.assertEquals(2,((String[])result.get("from")).length);
		Assert.assertEquals("test@star.de", ((String[])result.get("from"))[0] );
		Assert.assertEquals("toast@star.de", ((String[])result.get("from"))[1] );
	}

    @Test
	public void testMultipleMixed() {
		String input = "to='test@star.de,toast@star.de',subject=Hello World!,from='test@star.de,toast@star.de'";
		Map result = MessageOptionParser.parse(input);
		
		Assert.assertEquals(3, result.keySet().size());
		Assert.assertEquals(2,((String[])result.get("to")).length);
		Assert.assertEquals("test@star.de", ((String[])result.get("to"))[0] );
		Assert.assertEquals("toast@star.de", ((String[])result.get("to"))[1] );

		Assert.assertEquals(2,((String[])result.get("from")).length);
		Assert.assertEquals("test@star.de", ((String[])result.get("from"))[0] );
		Assert.assertEquals("toast@star.de", ((String[])result.get("from"))[1] );
		
		Assert.assertEquals("Hello World!", result.get("subject"));
	}
}
