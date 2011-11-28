package com.all.core.events;

import com.all.event.EventObject;

public class NetworkActionErrorEvent extends EventObject {
	private final NetworkActions action;

	public NetworkActionErrorEvent(NetworkActions action) {
		this.action = action;
	}

	public NetworkActions getAction() {
		return action;
	}

}
