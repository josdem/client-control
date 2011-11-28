package com.all.client.model;

import javax.swing.Icon;

public class Device {

	private Icon iconFile;
	private String name;
	public Icon getIconFile() {
		return iconFile;
	}

	public void setIconFile(Icon icon) {
		this.iconFile = icon;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
