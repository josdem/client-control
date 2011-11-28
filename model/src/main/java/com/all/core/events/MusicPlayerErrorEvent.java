package com.all.core.events;

import com.all.event.EventObject;
import com.all.shared.model.Track;

public class MusicPlayerErrorEvent extends EventObject {

	private final MusicPlayerErrorType errorType;
	private final Track track;

	public MusicPlayerErrorEvent(MusicPlayerErrorType errorType, Track track) {
		this.errorType = errorType;
		this.track = track;
	}

	private static final long serialVersionUID = 1L;

	public MusicPlayerErrorType getErrorType() {
		return errorType;
	}

	public Track getTrack() {
		return track;
	}

}
