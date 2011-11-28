package com.all.client.model;

import com.all.shared.model.Track;

public class GenrePredicate extends StringPredicate<Track> {

	public GenrePredicate(String genre) {
		super(genre);
	}

	@Override
	protected String sourceString(Track track) {
		return track.getGenre();
	}
}
