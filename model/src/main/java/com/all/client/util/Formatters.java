package com.all.client.util;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.all.client.model.FileSizeType;

public final class Formatters {

	enum SpeedType {
		KBPS(1),
		MBPS(2),
		GBPS(3);

		int value;

		private SpeedType(int value) {
			this.value = value;
		}

		public static SpeedType valueOf(int repeat) {
			for (SpeedType type : values()) {
				if (type.value == repeat) {
					return type;
				}
			}
			return null;
		}

	}

	// TODO: Use this class acording to the User's locale
	private static NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

	private Formatters() {

	}

	public static String formatDataSize(long size, boolean forcedKb) {
		long receivedSize = size;
		int sizeType = 0;
		long factor = 1024;
		if(forcedKb){
			factor = 1024 * 1024;
		}
		do {
			receivedSize /= 1024L;
			sizeType++;
		} while (receivedSize >= factor);

		numberFormat.setMaximumFractionDigits(2);
		return numberFormat.format(receivedSize) + " " + FileSizeType.valueOf(sizeType);
	}

	public static String formatInteger(int value) {
		return numberFormat.format(value);
	}

	public static String formatDate(Date date, String pattern) {
		if (date == null) {
			return "";
		}
		DateFormat dateFormat = new SimpleDateFormat(pattern, Locale.US);
		return dateFormat.format(date);
	}

	public static String formatSpeed(long speed) {
		long receivedSpeed = speed;
		int sizeType = 0;
		do {
			receivedSpeed /= 1024L;
			sizeType++;
		} while (receivedSpeed >= 1024);

		numberFormat.setMaximumFractionDigits(2);
		return numberFormat.format(receivedSpeed) + " " + SpeedType.valueOf(sizeType).toString().toLowerCase();
	}
	
	public static String formatFloat(float value, int decimals) {
		numberFormat.setMaximumFractionDigits(decimals);
		
		return numberFormat.format(value);
	}
	
	public static String formatTimeString(long currentTime) {
		long seconds = currentTime % 60;
		long minutes = (currentTime % 3600) / 60;
		long hour = currentTime / 3600;

		StringBuilder formatedStr = new StringBuilder();

		String fmt = hour > 0 ? "%02d:%02d" : "%d:%02d";
		formatedStr.append(hour > 0 ? hour + ":" : "");
		formatedStr.append(String.format(fmt, minutes, seconds));

		return formatedStr.toString();
	}
}
