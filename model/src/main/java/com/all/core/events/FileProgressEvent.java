package com.all.core.events;

import com.all.event.EventObject;

public class FileProgressEvent extends EventObject {
	
	private final int progress;
	private final int files;
	private final int totalFiles;
	private final String name;

	public FileProgressEvent(int progress, int files, int totalFiles, String name) {
		this.progress = progress;
		this.files = files;
		this.totalFiles = totalFiles;
		this.name = name;
			
	}

	public int getProgress() {
		return progress;
	}

	public int getFiles() {
		return files;
	}

	public int getTotalFiles() {
		return totalFiles;
	}

	public String getName() {
		return name;
	}

}
