package com.all.shared.model;

import java.util.ArrayList;
import java.util.List;

public class ListTrackContainer implements TrackContainer {
	private final List<Track> tracks;

	@SuppressWarnings("unchecked")
	public ListTrackContainer(List tracks) {
		this.tracks = new ArrayList<Track>(tracks.size());
		for (Object object : tracks) {
			if (object instanceof Track) {
				tracks.add((Track) object);
			} else {
				throw new ClassCastException();
			}
		}
	}

	@Override
	public Iterable<Track> getTracks() {
		return tracks;
	}

}
