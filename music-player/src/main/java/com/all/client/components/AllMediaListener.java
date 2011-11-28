package com.all.client.components;

import com.all.shared.model.Track;

public interface AllMediaListener {
	AllMediaListener EMPTY = new AllMediaListener() {
		@Override
		public void onPlaying(Track track) {
		}

		@Override
		public void mediaDurationChanged(Track track, double duration) {
		}

		@Override
		public void endOfMediaReached(Track track, double playerDuration) {
		}
	};

	void endOfMediaReached(Track track, double playerDuration);

	void onPlaying(Track track);

	void mediaDurationChanged(Track track, double duration);
}
