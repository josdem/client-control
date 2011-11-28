package com.all.client.model;

public class Container<T> {
	private String name;
	private T content;

	public Container() {
	}

	public Container(String name) {
		this.name = name;
	}

	public Container(String name, T content) {
		this.name = name;
		this.content = content;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setContent(T content) {
		this.content = content;
	}

	public T getContent() {
		return content;
	}
}
