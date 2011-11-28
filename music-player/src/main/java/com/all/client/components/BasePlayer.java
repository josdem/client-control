package com.all.client.components;

import java.util.ArrayList;
import java.util.List;

import com.all.shared.model.Track;

public abstract class BasePlayer implements MediaPlayer {
	private List<PlayerListener> playListeners;

	public BasePlayer() {
		playListeners = new ArrayList<PlayerListener>();
	}

	public void addPlayerListener(PlayerListener playerListener) {
		playListeners.add(playerListener);
	}

	public void removePlayerListener(PlayerListener playerListener) {
		playListeners.remove(playerListener);
	}

	protected void notifyTrackDonePlaying(Track track, long totalTime, long elapsedPlaybackTime, boolean wasNaturalEnd) {
		for (PlayerListener listener : playListeners) {
			listener.notifyTrackDonePlaying(track, totalTime, elapsedPlaybackTime, wasNaturalEnd);
		}
	}

	protected void notifyError(PlayerErrorType errorType, Track track) {
		for (PlayerListener listener : playListeners) {
			listener.notifyError(errorType, track);
		}
	}

	protected void notifyProgress(long currentTime, long totalTime) {
		for (PlayerListener listener : playListeners) {
			listener.notifyProgress(currentTime, totalTime);
		}
	}

}
