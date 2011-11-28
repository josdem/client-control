package com.all.shared.download;

import java.util.ArrayList;
import java.util.List;

public class TurnSeederInfo {

	private String seederId;
	private List<String> tracks = new ArrayList<String>();

	public TurnSeederInfo() {
	}
	
	public void setSeederId(String requester) {
		this.seederId = requester;
	}

	public String getSeederId() {
		return seederId;
	}

	public void setTracks(List<String> tracks) {
		this.tracks = tracks;
	}

	public List<String> getTracks() {
		return tracks;
	}
	
	@Override
	public String toString(){
		return seederId + " tracks available : " + tracks.size();
	}
	
}
