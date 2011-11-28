package com.all.client.itunes;

public class UnableReadITunesLibException extends Exception {
	
	private static final long serialVersionUID = 5450371834216255555L;

	public UnableReadITunesLibException(String message){
		super(message);
	}
	public UnableReadITunesLibException(String message, Throwable t){
		super(message,t);
	}

}
