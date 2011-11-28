package com.all.core.events;

import com.all.event.EventObject;

public class LibrarySyncEvent extends EventObject {
	private final String owner;
	private final LibrarySyncEventType type;
	private final String rootName;

	public LibrarySyncEvent(String owner, String rootName, LibrarySyncEventType type) {
		this.owner = owner;
		this.rootName = rootName;
		this.type = type;
	}

	public String getOwner() {
		return owner;
	}

	public LibrarySyncEventType getType() {
		return type;
	}

	public String getRootName() {
		return rootName;
	}

}
