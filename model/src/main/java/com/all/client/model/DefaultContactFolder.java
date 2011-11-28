package com.all.client.model;

public enum DefaultContactFolder {
	Friends, Close_Friends, Family, Work;
	public String getLabel() {
		return this.toString().replaceAll("_", " ");
	}
}
