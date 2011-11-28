package com.all.core.actions;

import com.all.action.ActionObject;
import com.all.shared.model.Root;
import com.all.shared.model.TrackContainer;

public class SelectTrackContainerAction extends ActionObject {
	private final Root root;
	private final TrackContainer trackContainer;

	public SelectTrackContainerAction(Root root, TrackContainer trackContainer) {
		this.root = root;
		this.trackContainer = trackContainer;
	}

	public Root getRoot() {
		return root;
	}

	public TrackContainer getTrackContainer() {
		return trackContainer;
	}

}
