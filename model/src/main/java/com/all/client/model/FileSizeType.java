/**
 * 
 */
package com.all.client.model;

public enum FileSizeType {
	KB(1),
	MB(2),
	GB(3);

	int value;

	private FileSizeType(int value) {
		this.value = value;
	}

	public static FileSizeType valueOf(int repeat) {
		for (FileSizeType type : values()) {
			if (type.value == repeat) {
				return type;
			}
		}
		return null;
	}

}