package com.all.shared.download;

import java.util.List;

public class SeederTracks {

	private String seederId;
	
	private List<String> tracks;
	
	private String requester;

	
	
	public SeederTracks() {
		super();
	}

	public SeederTracks(String seederId, String requesterId){
			this.seederId= seederId;
			this.requester = requesterId;
	}

	public void setSeederId(String seederId) {
		this.seederId = seederId;
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

	public void setRequester(String requester) {
		this.requester = requester;
	}

	public String getRequester() {
		return requester;
	}

}
