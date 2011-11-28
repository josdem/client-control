package com.all.client.util;

public final class TwitterUtil {

	public static final int MAX_STATUS_LENGTH = 140;

	private TwitterUtil() {
	}

	public static int getRemainingChars(String status) {
		return MAX_STATUS_LENGTH - (status != null ? status.length() : 0);
	}

}
