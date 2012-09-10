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
package org.columba.mail.gui.composer.util;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.columba.core.io.StreamUtils;
import org.junit.Assert;
import org.junit.Test;


public class QuoteFilterReaderTest {
    @Test
    public void testOneLiner() throws IOException {
        String line = "This is a test";
        Reader in = new StringReader(line);

        StringBuffer result = StreamUtils.readReader(new QuoteFilterReader(
                    in));
        Assert.assertTrue(result.toString().equals(line.replaceAll("(?m)^(.*)$", "> $1")));
    }

    @Test
    public void testMultiLiner1() throws IOException {
        String line = "This is a test\nForget the rest\n\n";
        Reader in = new StringReader(line);

        StringBuffer result = StreamUtils.readReader(new QuoteFilterReader(
                    in));
        Assert.assertTrue(result.toString().equals(line.replaceAll("(?m)^(.*)$", "> $1")));
    }

    @Test
    public void testMultiLiner2() throws IOException {
        String line = "This is a test\nForget the rest\n\n\n";
        Reader in = new StringReader(line);

        StringBuffer result = StreamUtils.readReader(new QuoteFilterReader(
                    in));
        Assert.assertTrue(result.toString().equals(line.replaceAll("(?m)^(.*)$", "> $1")));
    }

   @Test
    public void testMultiLiner3() throws IOException {
        String line = "\nThis is a test\nForget the rest\n\n\n";
        Reader in = new StringReader(line);

        StringBuffer result = StreamUtils.readReader(new QuoteFilterReader(
                    in));
        Assert.assertTrue(result.toString().equals(line.replaceAll("(?m)^(.*)$", "> $1")));
    }
}
