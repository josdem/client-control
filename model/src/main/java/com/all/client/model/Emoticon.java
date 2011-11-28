package com.all.client.model;

public class Emoticon {
	private String key;
	private String path;

	public Emoticon(String key, String path) {
		this.key = key;
		this.path = path;
	}
	
	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
	@Override
	public boolean equals(Object obj) {
		Emoticon emoticon  = (Emoticon) obj ;
		return emoticon.getKey().equalsIgnoreCase(getKey());
	}
}
