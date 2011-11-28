package com.all.client.components;

import java.io.File;

import com.all.shared.model.Track;

public interface AllMediaProvider {

	void stop();

	boolean isPlaying();

	void pause();

	boolean isPlaying(Track track);

	double setMediaTime(double time);

	double getDuration();

	void setVolume(float playerVolume);

	double getMediaTime();

	void play();

	void play(Track track, File file);

	Track getTrack();

	void setAllMediaListener(AllMediaListener listener);
}
