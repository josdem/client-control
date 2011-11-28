package com.all.client.components;

import java.util.Collections;
import java.util.List;

import com.all.shared.model.Track;

public class DefaultShuffleProvider implements ShuffleProvider {

	@Override
	public void shuffle(List<Track> tracks) {
		Collections.shuffle(tracks);
	}

}
