package com.all.core.events;

import com.all.event.EventObject;

public class ProfileLoadEvent extends EventObject {
	public enum ProfileLoadStatus {
		STARTED, FINISHED;
	}

	private final String email;
	private final ProfileLoadStatus status;

	public static ProfileLoadEvent startLoading(String email) {
		return new ProfileLoadEvent(email, ProfileLoadStatus.STARTED);
	}

	public static ProfileLoadEvent finishLoading(String email) {
		return new ProfileLoadEvent(email, ProfileLoadStatus.FINISHED);
	}

	public ProfileLoadEvent(String email, ProfileLoadStatus status) {
		super();
		this.email = email;
		this.status = status;
	}

	public ProfileLoadStatus getStatus() {
		return status;
	}

	public String getEmail() {
		return email;
	}

}
