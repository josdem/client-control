package com.all.core.events;

import com.all.event.EventObject;

public class ImportProgressEvent extends EventObject {


	private final int progress;
	private String message;

	public ImportProgressEvent(String message, int progress) {
		this.message=message;
		this.progress = progress;
	}

	public int getProgress() {
		return progress;
	}
	
	public String getMessage() {
		return message;
	}
	
}
