package com.all.client.components;

import java.util.List;

import com.all.shared.model.Track;

public interface ShuffleProvider {
	void shuffle(List<Track> tracks);
}
