package com.all.client.util;

public final class TimeUtil {
	public static final int ONE_SECOND = 1000;
	public static final int TWO_SECOND = 2000;

	private TimeUtil() {
	}

	public static boolean hasPassedOneSecond(long actualTime, long lastTime) {
		return actualTime - lastTime > ONE_SECOND;
	}

	public static boolean hasPassedTwoSecond(long actualTime, long lastTime) {
		return actualTime - lastTime > TWO_SECOND;
	}

	public static String convertSecondsToTime(int seconds) {
		StringBuffer buffer = new StringBuffer();
		int days = seconds / 86400;
		int hours = (seconds / 3600) % 24;
		int minutes = (seconds / 60) % 60;
		int secs = seconds % 60;

		if (days > 0) // Display days and hours
		{
			buffer.append(Integer.toString(days));
			buffer.append(":");
			if (hours < 10) {
				buffer.append("0");
			}
		}
		if (days > 0 || hours > 0) {
			buffer.append(Integer.toString(hours));
			buffer.append(":");
			if (minutes < 10) {
				buffer.append("0");
			}
		}

		buffer.append(Integer.toString(minutes));
		buffer.append(":");
		if (secs < 10) {
			buffer.append("0");
		}
		buffer.append(Integer.toString(secs));
		return buffer.toString();
	}

}
