package com.all.core.events;

public class UploadContentDoneEvent extends UploadContentEvent {

	private final boolean error;
	private final boolean canceled;

	public UploadContentDoneEvent(long uploadId, boolean error, boolean canceled) {
		super(uploadId);
		this.error = error;
		this.canceled = canceled;
	}

	public boolean hasError() {
		return error;
	}

	public boolean isCanceled() {
		return canceled;
	}
}
