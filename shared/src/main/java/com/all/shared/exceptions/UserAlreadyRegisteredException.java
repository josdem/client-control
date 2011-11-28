package com.all.shared.exceptions;


public class UserAlreadyRegisteredException extends BusinessException {

	private static final long serialVersionUID = 3188515759322625475L;
	private final String userEmail;

	public String getUserEmail() {
		return userEmail;
	}

	public UserAlreadyRegisteredException(String userEmail) {
		this.userEmail = userEmail;
	}
	
	
}
