package com.all.client.model;

import com.all.shared.model.Track;


public class NamePredicate extends StringPredicate<Track> {
	
	public NamePredicate(String target) {
		super(target);
	}

	@Override
	protected String sourceString(Track track) {
		return track.getName();	
	}

}
