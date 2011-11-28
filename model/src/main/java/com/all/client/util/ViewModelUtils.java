package com.all.client.util;

import com.all.appControl.control.ControlEngine;
import com.all.appControl.control.ViewEngine;
import com.all.core.model.Model;
import com.all.shared.model.Root.ContainerType;

public class ViewModelUtils {

	public static boolean isBrowsingRemoteLibrary(ViewEngine viewEngine) {
		ContainerType type = viewEngine.get(Model.SELECTED_ROOT).getType();
		return type == ContainerType.CONTACT || type == ContainerType.REMOTE;
	}

	public static boolean isBrowsingLocalLibrary(ViewEngine viewEngine) {
		ContainerType type = viewEngine.get(Model.SELECTED_ROOT).getType();
		return type == ContainerType.LOCAL;
	}

	public static boolean isBrowsingRemoteLibrary(ControlEngine viewEngine) {
		ContainerType type = viewEngine.get(Model.SELECTED_ROOT).getType();
		return type == ContainerType.CONTACT || type == ContainerType.REMOTE;
	}

	public static boolean isBrowsingLocalLibrary(ControlEngine viewEngine) {
		ContainerType type = viewEngine.get(Model.SELECTED_ROOT).getType();
		return type == ContainerType.LOCAL;
	}

}
