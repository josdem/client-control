package com.all.client.data;

import com.sun.jna.platform.FileMonitor;

public enum FileEventType {
	FILE_DELETED, FILE_NAME_CHANGED_OLD, FILE_NAME_CHANGED_NEW;

	static FileEventType from(int value) {
		switch (value) {
		case FileMonitor.FILE_DELETED:
			return FILE_DELETED;
		case FileMonitor.FILE_NAME_CHANGED_OLD:
			return FILE_NAME_CHANGED_OLD;
		case FileMonitor.FILE_NAME_CHANGED_NEW:
			return FileEventType.FILE_NAME_CHANGED_NEW;
		default:
			return null;
		}
	}
}
