package com.all.core.actions;

import com.all.action.ActionObject;
import com.all.shared.model.Track;

public class UpdateTrackRatingAction extends ActionObject {

	private final Track track;

	private final Integer rating;

	public UpdateTrackRatingAction(Track track, Integer rating) {
		this.track = track;
		this.rating = rating;
	}

	public Track getTrack() {
		return track;
	}

	public Integer getRating() {
		return rating;
	}
	
	
}
