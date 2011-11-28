package com.all.shared.download;

import java.util.List;

public class TrackSeeders {

	private String trackId;

	private List<String> seeders;

	private String requester;

	public TrackSeeders() {
	}

	public TrackSeeders(String trackId, String requester) {
		this.trackId = trackId;
		this.requester = requester;
	}

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public List<String> getSeeders() {
		return seeders;
	}

	public void setSeeders(List<String> seeders) {
		this.seeders = seeders;
	}

	public String getRequester() {
		return requester;
	}

	public void setRequester(String requester) {
		this.requester = requester;
	}

}
