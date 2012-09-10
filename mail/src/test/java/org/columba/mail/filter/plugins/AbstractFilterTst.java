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
package org.columba.mail.filter.plugins;

import java.io.ByteArrayInputStream;
import java.io.File;

import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.columba.mail.folder.AbstractMessageFolder;
import org.columba.mail.folder.FolderTstHelper;
import org.columba.mail.folder.MBOXFolderTstFactory;
import org.columba.mail.folder.MHFolderFactory;
import org.columba.mail.folder.MailboxTstFactory;
import org.columba.mail.folder.TempFolderFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * Base class for all filter tests.
 * <p>
 * Provides a test folder.
 * 
 * @author fdietz
 *  
 */
@RunWith(Parameterized.class)
public class AbstractFilterTst {

    protected AbstractMessageFolder sourceFolder;
    protected MailboxTstFactory factory;

    @Parameterized.Parameters
    public static Collection getFactories() {
        return Arrays.asList(
                new Object[][]{{MHFolderFactory.class},
                    {MBOXFolderTstFactory.class},
                    {TempFolderFactory.class}
                /*,{IMAPTstFactory.class}*/
                });
    // disabled IMAP folder tests as they require connection
    // to remote IMAP server
    // setup(suite, new IMAPTstFactory());
    }

    /**
     * Constructor for test.
     * <p>
     * This is used when executing this individual test only or by the ant task.
     * <p>
     */
    public AbstractFilterTst() {
        this.factory = new MHFolderFactory();
    }

    /**
     * Constructor for AbstractFilterTest.
     *
     * @param factory
     */
    public AbstractFilterTst(Class clazz) {
        Object obj = null;
        try {
            obj = clazz.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(AbstractFilterTst.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail("Instantiation Exception");
        } catch (IllegalAccessException ex) {
            Assert.fail("Illegal Access Exception");
        }
        if (obj != null && obj instanceof MailboxTstFactory) {
            this.factory = (MailboxTstFactory) obj;
            System.out.println("Factory now is: " + clazz.getSimpleName());
        } else {
            Assert.fail("Class " + clazz.getSimpleName() + " is not an instance of MailboxTstFactory");
        }
    }

    /**
     * @return Returns the folder.
     */
    public AbstractMessageFolder getSourceFolder() {
        return sourceFolder;
    }

    /**
     * @see TestCase#setUp()
     */
    @Before
    public void setUp() throws Exception {

        // create config-folder
//		File file = new File("test_config");
//		file.mkdir();
//
//		new Config(file);
//
//		Logging.DEBUG = true;
//		Logging.createDefaultHandler();
//
//		//		 init mail component
//		new MailMain().init();
//
//		new AddressbookMain().init();
//
//		// now load all available plugins
//		PluginManager.getInstance().initExternalPlugins();

        sourceFolder = factory.createFolder(1);

    //		 create MH folder
    // -> use homeDirectory as top-level folder
    // -> this has to be an absolute path
		/*
     * sourceFolder = new CachedMHFolder("test", "CachedMHFolder",
     * FolderTstHelper.homeDirectory + "/folders/");
     */
    }

    /**
     * @see TestCase#tearDown()
     */
    @After
    public void tearDown() throws Exception {
        File f = sourceFolder.getDirectoryFile();

        recursiveDelete(f);
    }

    private void recursiveDelete(File folder) {
        // delete all files in folder
        File[] list = folder.listFiles();

        for (int i = 0; i < list.length; i++) {
            if (list[i].isDirectory()) {
                recursiveDelete(list[i]);
            } else {
                list[i].delete();
            }
        }

        folder.delete();
    }

    /**
     * Add message to test folder.
     *
     * @throws Exception
     */
    public Object addMessage() throws Exception {
        // add message "0.eml" as inputstream to folder
        String input = FolderTstHelper.getString(0);


        // create stream from string
        ByteArrayInputStream inputStream = FolderTstHelper.getByteArrayInputStream(input);

        // add stream to folder
        Object uid = getSourceFolder().addMessage(inputStream);

        // close stream
        inputStream.close();

        return uid;
    }
}