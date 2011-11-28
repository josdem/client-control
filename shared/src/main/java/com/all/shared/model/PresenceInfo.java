package com.all.shared.model;

import java.io.Serializable;

public class PresenceInfo implements Serializable {

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isOnline() {
		return isOnline;
	}

	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

	@Override
	public String toString() {
		return email + ":" + isOnline;
	}

	private String email;

	private boolean isOnline;

	private static final long serialVersionUID = -5436463365240862483L;

}
