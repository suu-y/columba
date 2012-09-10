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

package org.columba.core.config;

import java.io.File;

import org.columba.core.xml.XmlElement;

public class OptionsXmlConfig extends DefaultXmlConfig {

	private GuiItem guiItem;
	
	private static final String OPTIONS_PROXY = "/options/proxy"; //$NON-NLS-1$
	
	private static final String OPTIONS_GUI = "/options/gui"; //$NON-NLS-1$
	
	private static final String HTTP_PROXYHOST = "http.proxyHost"; //$NON-NLS-1$
	
	private static final String HTTP_PROXYPORT = "http.proxyPort"; //$NON-NLS-1$
	
	private static final String PROXY_HOST = "host"; //$NON-NLS-1$
	
	private static final String PROXY_PORT = "port"; //$NON-NLS-1$
	

	public OptionsXmlConfig(final File file) {
		super(file);
	}

	@Override
	public boolean load() {
		final boolean result = super.load();

		
		final XmlElement proxy = getRoot().getElement(OPTIONS_PROXY);
		if ((proxy != null) && (System.getProperty(HTTP_PROXYHOST) != null)) {
			System.setProperty(HTTP_PROXYHOST, proxy.getAttribute(PROXY_HOST));
			System.setProperty(HTTP_PROXYPORT, proxy.getAttribute(PROXY_PORT));
		}

		return result;
	}

	public GuiItem getGuiItem() {
		if (guiItem == null) {
			guiItem = new GuiItem(getRoot().getElement(OPTIONS_GUI));
		}

		return guiItem;
	}
}
