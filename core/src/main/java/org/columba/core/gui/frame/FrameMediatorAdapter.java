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
package org.columba.core.gui.frame;

import org.columba.api.gui.frame.event.FrameEvent;
import org.columba.api.gui.frame.event.IFrameMediatorListener;

public class FrameMediatorAdapter implements IFrameMediatorListener {

	public FrameMediatorAdapter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void titleChanged(FrameEvent event) {
		System.out.println("SATD ID: 29");

	}

	public void statusMessageChanged(FrameEvent event) {
		System.out.println("SATD ID: 10");

	}

	public void taskStatusChanged(FrameEvent event) {
		System.out.println("SATD ID: 44");

	}

	public void visibilityChanged(FrameEvent event) {
		System.out.println("SATD ID: 48");

	}

	public void layoutChanged(FrameEvent event) {
		System.out.println("SATD ID: 78");

	}

	public void closed(FrameEvent event) {
		System.out.println("SATD ID: 39");

	}

	public void toolBarVisibilityChanged(FrameEvent event) {
		System.out.println("SATD ID: 56");

	}

	public void switchedComponent(FrameEvent event) {
		System.out.println("SATD ID: 146");

	}

}
