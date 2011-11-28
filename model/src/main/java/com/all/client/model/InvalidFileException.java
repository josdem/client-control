package com.all.client.model;

import java.io.File;

public class InvalidFileException extends Exception {
	private static final long serialVersionUID = 1L;
	private final File file;

	public InvalidFileException(File file, Throwable cause) {
		super(cause);
		this.file = file;
	}

	public InvalidFileException(File file, String message, Throwable cause) {
		super(message, cause);
		this.file = file;
	}

	public File getFile() {
		return file;
	}
}
