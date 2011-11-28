package com.all.client.model;

import com.all.shared.model.Track;


public class AlbumPredicate extends StringPredicate<Track> {

	public AlbumPredicate(String album) {
		super(album);
	}

	@Override
	protected String sourceString(Track track) {
		return track.getAlbum();
	}

}
