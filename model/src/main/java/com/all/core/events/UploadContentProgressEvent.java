package com.all.core.events;

import com.all.event.EventObject;

public class UploadContentProgressEvent extends EventObject {

	private final Long uploadId;
	private final Integer progress;
	private final Integer remainingSeconds;
	private final Integer uploadRate;

	public UploadContentProgressEvent(Long uploadId, Integer progress, Integer remainingSeconds, Integer uploadRate) {
		this.uploadId = uploadId;
		this.progress = progress;
		this.remainingSeconds = remainingSeconds;
		this.uploadRate = uploadRate;
	}

	public Long getUploadId() {
		return uploadId;
	}

	public Integer getProgress() {
		return progress;
	}

	public Integer getRemainingSeconds() {
		return remainingSeconds;
	}

	public Integer getUploadRate() {
		return uploadRate;
	}

}
