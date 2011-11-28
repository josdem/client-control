package com.all.login.security;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {

	public PasswordStrength validate(String password) {
		int score = 0;
		score = validatePattern(password, score, "[a-z]");
		score = validatePattern(password, score, "[A-Z]");
		score = validatePattern(password, score, "[0-9]");
		score = validatePattern(password, score, "[-_\\.]");

		return PasswordStrength.valueOf(score);
	}

	private int validatePattern(String password, int score, String pattern) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(password);
		return m.find() ? ++score : score;
	}

}
