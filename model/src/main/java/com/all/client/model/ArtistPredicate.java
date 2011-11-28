package com.all.client.model;

import com.all.shared.model.Track;


public class ArtistPredicate extends StringPredicate<Track> {

	public ArtistPredicate(String artist) {
		super(artist);
	}

	@Override
	protected String sourceString(Track track) {
		return track.getArtist();
	}
}
