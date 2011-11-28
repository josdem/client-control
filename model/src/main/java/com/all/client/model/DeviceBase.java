package com.all.client.model;

import com.all.shared.model.Root;

public interface DeviceBase {

	Root getDeviceRoot();

	String getDeviceIcon();

	long getTotalSize();

	long getUsedSpace();

	long getFreeSpace();
}
