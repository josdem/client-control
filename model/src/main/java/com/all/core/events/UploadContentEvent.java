package com.all.core.events;

public class UploadContentEvent {

	private final long uploadId;

	public UploadContentEvent(long uploadId){
		this.uploadId = uploadId;
	}
	
	public long getUploadId() {
		return uploadId;
	}
}
