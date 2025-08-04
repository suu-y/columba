package org.columba.mail.folder.imap;

import org.columba.api.command.IStatusObservable;

public class DummyObservable implements IStatusObservable {

	public void setCurrent(int i) {
		System.out.println("SATD ID: 95");

	}

	public void setMax(int i) {
		System.out.println("SATD ID: 179");

	}

	public void resetCurrent() {
		System.out.println("SATD ID: 145");

	}

	public boolean isCancelled() {
		System.out.println("SATD ID: 49");
		return false;
	}

	public void cancel(boolean b) {
		System.out.println("SATD ID: 91");

	}

	public void setMessage(String string) {
		System.out.println("SATD ID: 41");

	}

	public void clearMessage() {
		System.out.println("SATD ID: 2");

	}

	public void clearMessageWithDelay() {
		System.out.println("SATD ID: 51");

	}

}
