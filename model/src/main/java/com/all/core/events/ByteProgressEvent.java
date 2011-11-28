package com.all.core.events;

import com.all.event.EventObject;

public class ByteProgressEvent extends EventObject {

	
	private final int progress;
	private final long size;
	private final long totalSize;

	public ByteProgressEvent(int progress, long size, long totalSize) {
		this.progress = progress;
		this.size = size;
		this.totalSize = totalSize;
		
	}

	public int getProgress() {
		return progress;
	}

	public long getSize() {
		return size;
	}

	public long getTotalSize() {
		return totalSize;
	}
	
}
