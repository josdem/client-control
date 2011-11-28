package com.all.app;

public class ApplicationAbortException extends RuntimeException {
	public ApplicationAbortException() {
	}

	public ApplicationAbortException(Exception e) {
		super(e);
	}

	private static final long serialVersionUID = 1L;

}
