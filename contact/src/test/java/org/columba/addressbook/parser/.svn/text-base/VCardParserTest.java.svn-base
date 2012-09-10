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
package org.columba.addressbook.parser;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.columba.addressbook.model.ContactModel;
import org.columba.addressbook.model.IContactModel;
import org.columba.core.xml.XmlNewIO;
import org.jdom.Document;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author fdietz
 *  
 */
public class VCardParserTest  {

    @Test
	public void testWrite() throws Exception {

		File file = new File("src/main/resources/org/columba/addressbook/parser/columba.xml");

		Document doc = XmlNewIO.load(file);
		Assert.assertNotNull(doc);
		
		IContactModel c = new ContactModel(new Integer(0).toString());

		BufferedOutputStream out = new BufferedOutputStream(
				new FileOutputStream("test.vcf"));

		VCardParser.write(c, out);
		new File("test.vcf").delete();
	}

    @Test
	public void testRead() throws Exception {
		File file = new File(
				"src/main/resources/org/columba/addressbook/parser/vcard_evolution1.vcf");

		BufferedInputStream in = new BufferedInputStream(new FileInputStream(
				file));
		IContactModel[] cards = VCardParser.read(in);
		Assert.assertNotNull(cards);
	}

}