package com.all.shared.messages;

import java.io.Serializable;
import java.util.List;

import com.all.shared.login.LoginError;
import com.all.shared.model.Avatar;
import com.all.shared.model.ContactRequest;
import com.all.shared.model.User;
/*
 * BE CAREFULT WHEN CHANGING THIS CLASS, SINCE IT USES A JSON READER AND IT MAY NOT BE BACKWARD COMPATIBLE
 * IF YOU REMOVE FIELDS.
 * */
public class LoginResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private User user;
	private LoginError error;
	private String sessionId;
	private boolean firstLogin;
	private List<ContactRequest> pendingRequests;

	public LoginResponse(LoginError error) {
		this.error = error;
		user = null;
		sessionId = null;
	}

	public LoginResponse(User user, Avatar avatar, String sessionId) {
		this.user = user;
		this.sessionId = sessionId;
		error = null;
	}

	@Deprecated
	// Used only by JsonConverter
	public LoginResponse() {
	}

	public boolean isSuccessful() {
		return error == null;
	}

	public boolean isFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(boolean firstLogin) {
		this.firstLogin = firstLogin;
	}

	public LoginError getError() {
		return error;
	}

	public String getSessionId() {
		return sessionId;
	}

	public User getUser() {
		return user;
	}

	public List<ContactRequest> getPendingRequests() {
		return pendingRequests;
	}

	public void setPendingRequests(List<ContactRequest> pendingRequests) {
		this.pendingRequests = pendingRequests;
	}

	@Deprecated
	// Used only by JsonConverter
	public void setUser(User user) {
		this.user = user;
	}

	@Deprecated
	// Used only by JsonConverter
	public void setError(LoginError error) {
		this.error = error;
	}

	@Deprecated
	// Used only by JsonConverter
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}
