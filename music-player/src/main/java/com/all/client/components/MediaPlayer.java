package com.all.client.components;

import com.all.shared.model.Track;

//understands how to play physical files

public interface MediaPlayer {

	void play(Track track, boolean forceRestart);

	void stop();

	void pause();

	void addPlayerListener(PlayerListener player);

	void removePlayerListener(PlayerListener player);

	void updateTime(long currentTime);

	long getCurrentTime();

	void changeVolume(float volume);

	float getVolume();

	void changeVelocity(int velocity);

}
