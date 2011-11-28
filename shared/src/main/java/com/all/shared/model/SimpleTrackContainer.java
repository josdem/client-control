package com.all.shared.model;


public class SimpleTrackContainer implements TrackContainer {
	private final Iterable<Track> tracks;

	public SimpleTrackContainer(Iterable<Track> tracks) {
		this.tracks = tracks;
	}

	@Override
	public Iterable<Track> getTracks() {
		return tracks;
	}

}
