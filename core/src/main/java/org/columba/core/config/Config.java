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
package org.columba.core.config;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.columba.core.base.OSInfo;
import org.columba.core.io.DiskIO;
import org.columba.core.xml.XmlElement;
import org.columba.core.xml.XmlIO;

/**
 * Main entrypoint for configuration management.
 * <p>
 * Stores a list of all xml files in a hashtable. Hashtable key is the name of
 * the xml file. Value is {@link XmlIO} object.
 * <p>
 * Mail and Addressbook components are just wrappers, encapsulating this class.
 * Using these wrapper classes, you don't need to specify the module name (for
 * example: mail, or addressbook) manually.
 * <p>
 * Note that all configuration file have default templates in the /res directory
 * in package org.columba.core.config. These default configuration files are
 * copied into the users's configuration directory the first time Columba is
 * started.
 * <p>
 * Config creates the top-level directory for Columba's configuration in
 * ".columba", which usually resides in the user's home directory or on older
 * Windows versions in Columba's program folder.
 * <p>
 * Saving and loading of all configuration files is handled here, too.
 * <p>
 *
 * @see org.columba.mail.config.MailConfig
 * @see org.columba.addressbook.config.AddressbookConfig
 *
 * @deprecated use XmlConfig instead
 * @author fdietz
 */
public class Config implements IConfig {

	private static final String CORE_STR = "core"; //$NON-NLS-1$

	static final Logger LOG = Logger.getLogger("org.columba.core.config"); //$NON-NLS-1$

	protected Map<String, Map<String, DefaultXmlConfig>> pluginList = new Hashtable<String, Map<String, DefaultXmlConfig>>();

	protected File path;

	protected File optionsFile;

	protected File toolsFile;

	protected File viewsFile;

	private static Config instance;

	/**
	 * Creates a new configuration from the given directory.
	 */
	public Config(File thePath) {
		if (thePath == null) {
			thePath = DefaultConfigDirectory.getDefaultPath();
		}

		this.path = thePath;
		thePath.mkdir();
		optionsFile = new File(thePath, "options.xml"); //$NON-NLS-1$
		toolsFile = new File(thePath, "external_tools.xml"); //$NON-NLS-1$
		viewsFile = new File(thePath, "views.xml"); //$NON-NLS-1$


		registerPlugin(CORE_STR, optionsFile.getName(), new OptionsXmlConfig(
				optionsFile));
		registerPlugin(CORE_STR, toolsFile.getName(), new DefaultXmlConfig(
				toolsFile));
		registerPlugin(CORE_STR, viewsFile.getName(), new DefaultXmlConfig(
				viewsFile));

		instance = this;
	}

	public static Config getInstance() {
		if (instance == null) {
            //timsparg this would fail the junit tests
            //added call to default contructor, shouldnt
            //this be a facory instead?
			//throw new RuntimeException("Must call Constructor first!");

            new Config(null);
		}

		return instance;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.columba.core.config.IConfig#getConfigDirectory()
	 */
	public File getConfigDirectory() {
		return path;
	}

	/**
	 * Method registerPlugin.
	 *
	 * @param moduleName
	 * @param id
	 * @param configPlugin
	 */
	public void registerPlugin(final String moduleName, final String id,
			final DefaultXmlConfig configPlugin) {
		File directory;

		if (moduleName.equals(CORE_STR)) {
			directory = getConfigDirectory();
		} else {
			directory = new File(getConfigDirectory(), moduleName);
		}

		final File destination = new File(directory, id);

		if (!destination.exists()) {
			final String hstr = "org/columba/" + moduleName + "/config/" + id; //$NON-NLS-1$

			try {
				DiskIO.copyResource(hstr, destination);
			} catch (final IOException e) {
			}
		}

		if (!pluginList.containsKey(moduleName)) {
			final Map<String, DefaultXmlConfig> map = new Hashtable<String, DefaultXmlConfig>();
			pluginList.put(moduleName, map);
		}

		addPlugin(moduleName, id, configPlugin);

		// load config-file from disk
		configPlugin.load();
	}

	/**
	 * Method getPlugin.
	 *
	 * @param moduleName
	 * @param id
	 * @return DefaultXmlConfig
	 */
	public DefaultXmlConfig getPlugin(final String moduleName, final String id) {
		if (pluginList.containsKey(moduleName)) {
			final Map<String, DefaultXmlConfig> map = pluginList
					.get(moduleName);

			if (map.containsKey(id)) {
				final DefaultXmlConfig plugin = map.get(id);

				return plugin;
			}
		}

		return null;
	}

	/**
	 * Method addPlugin.
	 *
	 * @param moduleName
	 * @param id
	 * @param configPlugin
	 */
	public void addPlugin(final String moduleName, final String id,
			final DefaultXmlConfig configPlugin) {
		final Map<String, DefaultXmlConfig> map = pluginList.get(moduleName);

		if (map != null) {
			map.put(id, configPlugin);
		}
	}

	/**
	 * Method getPluginList.
	 *
	 * @return List
	 */
	public List<DefaultXmlConfig> getPluginList() {
		final List<DefaultXmlConfig> list = new LinkedList<DefaultXmlConfig>();

		for (String key : pluginList.keySet()) {
			// final String key = (String) keys.next();
			final Map<String, DefaultXmlConfig> map = pluginList.get(key);

			if (map != null) {
				for (String key2 : map.keySet()) {
					final DefaultXmlConfig plugin = map.get(key2);
					list.add(plugin);
				}
			}
		}

		return list;
	}

	/**
	 * Method save.
	 */
	public void save() throws Exception {
		for (DefaultXmlConfig plugin : getPluginList()) {
			if (plugin == null) {
				continue;
			}

			plugin.save();
		}
	}

	/**
	 * Loads all plugins and template plugins.
	 */
	protected void load() {
		for (DefaultXmlConfig plugin : getPluginList()) {
			if (plugin == null) {
				continue;
			}

			plugin.load();
		}
	}

	/**
	 * @see org.columba.core.config.IConfig#get(java.lang.String)
	 */
	public XmlElement get(final String name) {
		final DefaultXmlConfig xml = getPlugin(CORE_STR, name + ".xml");

		return xml.getRoot();
	}

	/**
	 * Method getOptionsMainInterface.config.
	 *
	 * @return OptionsXmlConfig
	 */
	public OptionsXmlConfig getOptionsConfig() {
		return (OptionsXmlConfig) getPlugin(CORE_STR, optionsFile.getName());
	}

	/**
	 * Returns the default configuration path. This value depends on the
	 * underlying operating system. This method must never return null.
	 */
	public static File getDefaultConfigPath() {
		if (OSInfo.isWindowsPlatform()) {
			return new File("config"); //$NON-NLS-1$
		}
		return new File(System.getProperty("user.home"), ".columba"); //$NON-NLS-1$//$NON-NLS-2$
	}
}