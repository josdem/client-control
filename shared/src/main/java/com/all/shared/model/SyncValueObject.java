package com.all.shared.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

import com.all.shared.sync.MergeLibraryProgressListener;

public class SyncValueObject {

	private final Collection<MergeLibraryProgressListener> listeners = new CopyOnWriteArraySet<MergeLibraryProgressListener>();

	private String email;

	private int snapshot;

	private int delta;

	private List<String> events;

	private long timestamp;

	public SyncValueObject(String email, int snapshot, int delta, long timestamp) {
		this.email = email;
		this.snapshot = snapshot;
		this.delta = delta;
		this.timestamp = timestamp;
		this.events = new ArrayList<String>();
	}

	public String getEmail() {
		return email;
	}

	public int getSnapshot() {
		return snapshot;
	}

	public int getDelta() {
		return delta;
	}

	@Override
	public String toString() {
		return new StringBuilder().append("SVO[").append(email).append("]").append("[").append(snapshot).append("]")
				.append("[").append(delta).append("]").toString();
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setEvents(List<String> events) {
		this.events = events;
	}

	public List<String> getEvents() {
		return events;
	}

	public void addProgressListener(MergeLibraryProgressListener listener) {
		listeners.add(listener);
	}

	public void notifyProgress(int progress) {
		for (MergeLibraryProgressListener listener : listeners) {
			listener.onProgress(progress);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + delta;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + snapshot;
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
		SyncValueObject other = (SyncValueObject) obj;
		if (delta != other.delta) {
			return false;
		}
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (snapshot != other.snapshot) {
			return false;
		}
		return true;
	}

	@Deprecated
	public SyncValueObject() {
		this.events = new ArrayList<String>();
	}

	@Deprecated
	public void setEmail(String email) {
		this.email = email;
	}

	public void setSnapshot(int snapshot) {
		this.snapshot = snapshot;
	}

	public void setDelta(int delta) {
		this.delta = delta;
	}

	@Deprecated
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}