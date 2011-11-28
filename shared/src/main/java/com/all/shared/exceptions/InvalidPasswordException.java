package com.all.shared.exceptions;

public class InvalidPasswordException extends BusinessException {
	private static final long serialVersionUID = 5698163262104718747L;

	public InvalidPasswordException(String message) {
		super(message);
	}
}
