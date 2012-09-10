package org.columba.core.config;

import java.io.File;

import org.columba.core.base.OSInfo;

/**
 * Path to configuration directory.
 *
 * @author Frederik Dietz
 */
public class DefaultConfigDirectory {

	private static DefaultConfigDirectory instance = new DefaultConfigDirectory();

	private File currentPath;

	private DefaultConfigDirectory() {}

	public static DefaultConfigDirectory getInstance() {
		return instance;
	}

	/**
	 * Set current path to config directory. This is set by org.columba.main.Bootstrap and depends on the
	 * selected profile.
	 *
	 * @param currentPath
	 */
	public void setCurrentPath(File currentPath) {
		this.currentPath = currentPath;
	}

	public File getCurrentPath() {
		if ( currentPath == null) {
			// fall back to default path
			currentPath = DefaultConfigDirectory.getDefaultPath();
		}

		return currentPath;
	}

	/**
	 * Returns the default configuration path. This value depends on the
	 * underlying operating system. This method must never return null.
	 */
	public static File getDefaultPath() {
		if (OSInfo.isWindowsPlatform()) {
			return new File("config"); //$NON-NLS-1$
		}
		return new File(System.getProperty("user.home"), ".columba"); //$NON-NLS-1$//$NON-NLS-2$
	}
}
