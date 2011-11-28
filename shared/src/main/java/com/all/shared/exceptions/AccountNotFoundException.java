package com.all.shared.exceptions;

public class AccountNotFoundException extends BusinessException {

	private static final long serialVersionUID = 3974951353228098830L;

	public AccountNotFoundException(String message) {
		super(message);
	}
}
