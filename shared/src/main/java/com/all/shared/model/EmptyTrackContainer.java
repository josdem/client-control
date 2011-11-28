package com.all.shared.model;

import java.util.Collections;

public class EmptyTrackContainer implements TrackContainer {
	public static final EmptyTrackContainer INSTANCE = new EmptyTrackContainer();

	@Override
	public Iterable<Track> getTracks() {
		return Collections.emptyList();
	}

}
