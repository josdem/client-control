package com.all.core.events;

import com.all.event.EventObject;
import com.all.shared.model.Root;
import com.all.shared.model.TrackContainer;

public class SelectTrackContainerEvent extends EventObject {
	private final Root root;
	private final TrackContainer container;

	public SelectTrackContainerEvent(Root root, TrackContainer container) {
		this.root = root;
		this.container = container;
	}

	public Root getRoot() {
		return root;
	}

	public TrackContainer getContainer() {
		return container;
	}

}
