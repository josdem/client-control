package com.all.shared.download;

public class RestUploadRequest {

	private String trackId;

	private String requester;

	private int fromChunk;

	public RestUploadRequest() {
	}

	public RestUploadRequest(String requester, String trackId) {
		this.trackId = trackId;
		this.requester = requester;
	}

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public String getRequester() {
		return requester;
	}

	public void setRequester(String requester) {
		this.requester = requester;
	}

	public int getFromChunk() {
		return fromChunk;
	}

	public void setFromChunk(int fromChunk) {
		this.fromChunk = fromChunk;
	}

}
