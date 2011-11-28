package com.all.shared.mc;

import com.all.shared.model.Track;

public class TrackSearchResult {

	private Track track;
	private double score;

	@Deprecated
	public TrackSearchResult(Track track, int hitKeywords, int totalKeywords) {
		this.track = track;
	}
	
	public TrackSearchResult(Track track, double score){
		this.track = track;
		this.score = score;
	}
	

	public Track getTrack() {
		return track;
	}
	
	public double getScore(){
		return score;
	}

}
