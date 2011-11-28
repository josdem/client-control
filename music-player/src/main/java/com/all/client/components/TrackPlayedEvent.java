package com.all.client.components;

import com.all.observ.ObserveObject;
import com.all.shared.model.Track;

public class TrackPlayedEvent extends ObserveObject {
	private static final long serialVersionUID = 1L;
	private final Track track;
	private final long playedTime;
	private final long duration;

	public TrackPlayedEvent(Track track, long duration, long playedTime) {
		this.track = track;
		this.duration = duration;
		this.playedTime = playedTime;
	}

	public Track getTrack() {
		return track;
	}

	public long getPlayedTime() {
		return playedTime;
	}

	public long getDuration() {
		return duration;
	}

}
