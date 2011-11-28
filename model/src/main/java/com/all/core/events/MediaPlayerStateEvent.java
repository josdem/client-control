package com.all.core.events;

import com.all.event.EventObject;
import com.all.shared.model.Track;

public class MediaPlayerStateEvent extends EventObject {

	private static final long serialVersionUID = -8625845142007897642L;
	private MusicPlayerState state;
	private Track track;
	private int trackPosition;

	public MediaPlayerStateEvent(MusicPlayerState state, Track track, int trackPosition) {
		this.state = state;
		this.track = track;
		this.trackPosition = trackPosition;
	}

	public MusicPlayerState getState() {
		return state;
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
		result = prime * result + ((state == null) ? 0 : state.hashCode());
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
		MediaPlayerStateEvent other = (MediaPlayerStateEvent) obj;
		if (state != other.state) {
			return false;
		}
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
