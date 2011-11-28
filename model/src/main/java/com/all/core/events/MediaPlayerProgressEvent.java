package com.all.core.events;

import com.all.event.EventObject;

public class MediaPlayerProgressEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	final long currentTime;
	final long totalTime;

	public MediaPlayerProgressEvent(long currentTime, long totalTime) {
		this.currentTime = currentTime;
		this.totalTime = totalTime;
	}

	public long getCurrentTime() {
		return currentTime;
	}

	public long getTotalTime() {
		return totalTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (currentTime ^ (currentTime >>> 32));
		result = prime * result + (int) (totalTime ^ (totalTime >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		MediaPlayerProgressEvent other = (MediaPlayerProgressEvent) obj;
		if (currentTime != other.currentTime) {
			return false;
		}
		if (totalTime != other.totalTime) {
			return false;
		}
		return true;
	}

}
