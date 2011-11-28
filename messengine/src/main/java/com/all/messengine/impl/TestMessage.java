package com.all.messengine.impl;

import java.io.Serializable;

import com.all.messengine.Message;

public class TestMessage<T extends Serializable> implements Message<T> {

	private static final long serialVersionUID = 1L;

	private String type;
	private T body;

	public TestMessage(String type, T body) {
		super();
		this.type = type;
		this.body = body;
	}

	@Override
	public T getBody() {
		return body;
	}

	@Override
	public String getProperty(String key) {
		return null;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void putProperty(String key, String value) {

	}

}
