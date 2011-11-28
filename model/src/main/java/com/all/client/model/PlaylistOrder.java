package com.all.client.model;

import java.util.List;

import com.all.shared.model.Track;
import com.all.shared.model.TrackContainer;

public class PlaylistOrder {

	private final TrackContainer trackContainer;
	private final List<Track> playOrder;

	public PlaylistOrder(TrackContainer trackContainer, List<Track> playOrder) {
		this.trackContainer = trackContainer;
		this.playOrder = playOrder;
	}

	public TrackContainer getTrackContainer() {
		return trackContainer;
	}

	public List<Track> getPlayOrder() {
		return playOrder;
	}

}
