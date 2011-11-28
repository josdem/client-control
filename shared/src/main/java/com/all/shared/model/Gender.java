package com.all.shared.model;

import com.all.shared.util.Imageable;

public enum Gender implements Imageable {
	MALE("MaleAvatar.jpg", "Male"), FEMALE("FemaleAvatar.jpg", "Female"), SHEMALE("", "Shemale"), UNKNOWN("", "Indefinite");

	private final String imageName;
	private final String label;

	private Gender(String imageName, String label) {
		this.imageName = imageName;
		this.label = label;
	}

	@Override
	public String getImageName() {
		return imageName;
	}

	public String getLabel(){
		return label;
	}

}
