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
package org.columba.core.plugin;

import java.io.File;

import org.columba.core.io.DiskIO;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author fdietz
 *
 */
public class PluginFinderTest {

	private static File file;

	/*
	 * @see TestCase#setUp()
	 */
    @BeforeClass
	public static void setUp() throws Exception {
		// create config-folder
		file = new File("test_config");
		file.mkdir();

	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
    @AfterClass
	public static void tearDown() throws Exception {
		// remove configuration directory
		DiskIO.deleteDirectory(file);
	}

    @Test
	public void test() {
		// find all possible plugin directories
		File[] pluginFolders = PluginFinder.searchPlugins();

		if (pluginFolders == null) {
			System.out.println("no plugin directories found");
		} else {
			for (int i = 0; i < pluginFolders.length; i++) {
				System.out.println("plugin-directory=" + pluginFolders[i]);
			}
		}
	}
}