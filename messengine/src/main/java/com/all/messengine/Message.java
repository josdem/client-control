package com.all.messengine;


public interface Message<T> {
	
	String getProperty(String key);

	void putProperty(String key, String value);

	String getType();

	T getBody();
}
