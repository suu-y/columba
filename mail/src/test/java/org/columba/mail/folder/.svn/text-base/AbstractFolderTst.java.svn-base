//The contents of this file are subject to the Mozilla Public License Version
//1.1
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
//Stich.
//Portions created by Frederik Dietz and Timo Stich are Copyright (C) 2003.
//
//All Rights Reserved.
package org.columba.mail.folder;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.columba.core.xml.XmlElement;
import org.columba.mail.config.AccountItem;
import org.columba.mail.config.AccountItemTest;
import org.columba.mail.config.MailConfig;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * Abstract testcase creates a folder in setUp and removes it in tearDown.
 * <p>
 * Create new testcases by subclassing this class and using getSourceFolder()
 * and getDestFolder() directly.
 * 
 * @author fdietz
 * @author redsolo
 */
@RunWith(Parameterized.class)
public class AbstractFolderTst {

    /** A source folder. */
    protected AbstractMessageFolder sourceFolder;
    /** A destination folder. */
    protected AbstractMessageFolder destFolder;
    /** A set with all created folders. */
    private Set folders;
    private static int folderId = 0;
    private MailboxTstFactory factory;

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
    public AbstractFolderTst() {
        this.factory = new MHFolderFactory();
    }

    public AbstractFolderTst(Class clazz) {
        Object obj = null;
        try {
            obj = clazz.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(AbstractFolderTst.class.getName()).log(Level.SEVERE, null, ex);
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
//		// init mail component
//		new MailMain().init();
//		new AddressbookMain().init();
//
//		// now load all available plugins
//		PluginManager.getInstance().initExternalPlugins();

        try {
            folders = new HashSet();
            sourceFolder = factory.createFolder(folderId++);
            folders.add(sourceFolder);
            destFolder = factory.createFolder(folderId++);
            folders.add(destFolder);

            XmlElement xml = new XmlElement("account");
            xml.addAttribute("name", "my account");
            xml.addAttribute("uid", "0");

            XmlElement child = xml.addSubElement("identity");
            child.addAttribute("name", "John Doe");
            child.addAttribute("attach_signature", "false");
            child = xml.addSubElement("popserver");
            child.addAttribute("port", "25");
            child.addAttribute("login_method", "USER");
            child = xml.addSubElement("specialfolders");
            child.addAttribute("inbox", "101");
            child.addAttribute("sent", "104");

            AccountItem item = new AccountItem(xml);

            MailConfig.getInstance().getAccountList().add(item);

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("This is the initialisation error");
        }
    }

    public AbstractMessageFolder createFolder() {
        AbstractMessageFolder folder = factory.createFolder(folderId++);
        folders.add(folder);

        return folder;
    }

    /**
     * @see junit.framework.TestCase#tearDown()
     */
    @After
    public void tearDown() throws Exception {
        recursiveDelete(new File(FolderTstHelper.homeDirectory + "/folders/"));
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
     * @return Returns the folder.
     */
    public AbstractMessageFolder getSourceFolder() {
        return sourceFolder;
    }

    /**
     * @return Returns the destFolder.
     */
    public AbstractMessageFolder getDestFolder() {
        return destFolder;
    }
}