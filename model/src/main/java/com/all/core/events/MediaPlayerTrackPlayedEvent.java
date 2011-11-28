package com.all.core.events;

import com.all.event.EventObject;
import com.all.shared.model.Track;

public class MediaPlayerTrackPlayedEvent extends EventObject {

	private static final long serialVersionUID = -2530311228816282442L;
	private Track track;
	private int trackPosition;

	public MediaPlayerTrackPlayedEvent(Track track, int trackPosition) {
		this.track = track;
		this.trackPosition = trackPosition;

	}

	public Track getTrack() {
		return track;
	}

	public int getTrackPosition() {
		return this.trackPosition;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((track == null) ? 0 : track.hashCode());
		result = prime * result + trackPosition;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		MediaPlayerTrackPlayedEvent other = (MediaPlayerTrackPlayedEvent) obj;
		if (track == null) {
			if (other.track != null) {
				return false;
			}
		} else if (!track.equals(other.track)) {
			return false;
		}
		if (trackPosition != other.trackPosition) {
			return false;
		}
		return true;
	}

}
