package org.columba.mail.parser;

import java.util.List;
import java.util.Vector;
import org.junit.Assert;
import org.junit.Test;


public class NormalizeRecipientListParserTest {

	/*
	 * test with null list
	 */
    @Test
	public void testNormalizeRCPTVectorNull() {
		
		try {
			new NormalizeRecipientListParser().normalizeRCPTVector(null);
			Assert.fail();
		} catch (IllegalArgumentException e) {
		}
	}

	
	/*
	 * test with empty list
	 */
    @Test
	public void testNormalizeRCPTVectorEmptyList() {

		List<String> list = new Vector<String>();
		
		List<String> result = new NormalizeRecipientListParser().normalizeRCPTVector(list);
		Assert.assertEquals(0, result.size());
	}

	
	/*
	 * Test with all kinds of input data
	 */
    @Test
	public void testNormalizeRCPTVector() {

		List<String> list = new Vector<String>();
		list.add("Firstname Lastname <mail@mail.org>");
		list.add("<mail@mail.org>");
		list.add("mail@mail.org");
		list.add("\"Lastname, Firstname\" <mail@mail.org>");
		
		List<String> result = new NormalizeRecipientListParser().normalizeRCPTVector(list);
		Assert.assertEquals(result.get(0),"<mail@mail.org>");
		Assert.assertEquals(result.get(1),"<mail@mail.org>");
		Assert.assertEquals(result.get(2),"<mail@mail.org>");
		Assert.assertEquals(result.get(3),"<mail@mail.org>");
	}

}
