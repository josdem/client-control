package com.all.shared.model;

import java.io.Serializable;

public class PeerDownloadRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String requester;
	
	private String requested;
	
	private String track;

	public PeerDownloadRequest(){}
	
	
	public PeerDownloadRequest(String requester, String requested, String track) {
		this.requester = requester;
		this.requested = requested;
		this.track = track;
	}


	public String getRequester() {
		return requester;
	}


	public void setRequester(String requester) {
		this.requester = requester;
	}


	public String getRequested() {
		return requested;
	}


	public void setRequested(String requested) {
		this.requested = requested;
	}


	public String getTrack() {
		return track;
	}


	public void setTrack(String track) {
		this.track = track;
	}

}
