package com.all.client.components;

import com.all.shared.model.Track;

public interface PlayerListener {

	void notifyTrackDonePlaying(Track track, long duration, long netPlayedTime, boolean wasNaturalEnd);

	void notifyError(PlayerErrorType errorType, Track track);

	void notifyProgress(long currentTime, long totalDuration);
}
