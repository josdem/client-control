package com.all.client.itunes;

public class UnableReadMetadataException extends Exception {

	private static final long serialVersionUID = 8066452117863255904L;

	public UnableReadMetadataException(String message){
		super(message);
	}
	public UnableReadMetadataException(String message,Throwable t){
		super(message,t);
	}
}
