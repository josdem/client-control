package com.all.login.security;

public enum PasswordStrength {
	WEAK("#ED1C24"), MEDIUM("#FAAF40"),STRONG("#37B34A");

	private final String color;
	
	public String getColor() {
		return color;
	}

	public static PasswordStrength valueOf(int score) {
		if (score < 2) {
			return WEAK;
		}
		if (score < 3) {
			return MEDIUM;
		}
		return STRONG;
	}
	
	private PasswordStrength(String color) {
		this.color = color;
	}

}
