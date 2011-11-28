package com.all.core.events;

import com.all.event.EventObject;

public class LibrarySyncProgressEvent extends EventObject {
	private final String owner;
	private final int progress;

	public LibrarySyncProgressEvent(String owner, int progress) {
		this.owner = owner;
		this.progress = progress;
	}

	public int getProgress() {
		return progress;
	}

	public String getOwner() {
		return owner;
	}

}
