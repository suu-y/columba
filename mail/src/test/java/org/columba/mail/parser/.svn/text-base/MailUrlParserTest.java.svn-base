/*
 * Created on Jul 3, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package org.columba.mail.parser;

import java.util.Map;


import org.columba.ristretto.parser.ParserException;
import org.junit.Assert;
import org.junit.Test;

public class MailUrlParserTest {

    @Test
	public void testParser1() throws ParserException {
		String testData = "mailto:chris@example.com";
		Map result = MailUrlParser.parse(testData);
		
		Assert.assertEquals("chris@example.com", ((String[])result.get("to"))[0]);
	}

    @Test
	public void testParser2() throws ParserException {
		String testData = "mailto:infobot@example.com?subject=current-issue";
		Map result = MailUrlParser.parse(testData);
		
		Assert.assertEquals("infobot@example.com", ((String[])result.get("to"))[0]);
		Assert.assertEquals("current-issue",result.get("subject"));
	}

    @Test
	public void testParser3() throws ParserException {
		String testData = "mailto:infobot@example.com?body=send%20current-issue%0D%0Asend%20index";
		Map result = MailUrlParser.parse(testData);
		
		Assert.assertEquals("infobot@example.com", ((String[])result.get("to"))[0]);
		Assert.assertEquals("send current-issue\r\nsend index",result.get("body"));
	}

    @Test
	public void testParser4() throws ParserException {
		String testData = "mailto:joe@example.com?cc=bob@example.com&body=hello";
		Map result = MailUrlParser.parse(testData);
		
		Assert.assertEquals("joe@example.com", ((String[])result.get("to"))[0]);
		Assert.assertEquals("bob@example.com", ((String[])result.get("cc"))[0]);
		Assert.assertEquals("hello",result.get("body"));
	}

    @Test
	public void testParser5() throws ParserException {
		String testData = "mailto:?to=joe@example.com&cc=bob@example.com&body=hello";
		Map result = MailUrlParser.parse(testData);
		
		Assert.assertEquals("joe@example.com", ((String[])result.get("to"))[0]);
		Assert.assertEquals("bob@example.com", ((String[])result.get("cc"))[0]);
		Assert.assertEquals("hello",result.get("body"));
	}

    @Test
	public void testParser6() throws ParserException {
		String testData = "mailto:gorby%25kremvax@example.com";
		Map result = MailUrlParser.parse(testData);
		
		Assert.assertEquals("gorby%kremvax@example.com", ((String[])result.get("to"))[0]);
	}


}
