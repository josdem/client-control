package com.all.shared.model;

import java.util.Collections;

public interface TrackContainer {

	public static final TrackContainer EMPTY = new TrackContainer() {
		@SuppressWarnings("unchecked")
		@Override
		public Iterable<Track> getTracks() {
			return Collections.EMPTY_LIST;
		}
	};

	Iterable<Track> getTracks();

}
