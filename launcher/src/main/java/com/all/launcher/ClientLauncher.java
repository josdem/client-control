package com.all.launcher;

import java.lang.reflect.Constructor;
import java.util.concurrent.Callable;

public class ClientLauncher {

	public boolean launch() throws Exception {
		Callable<Boolean> client = getClientClass();
		Boolean callResult = client.call();
		if (callResult != null) {
			if (((Boolean) callResult).booleanValue()) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("all")
	private Callable<Boolean> getClientClass() throws Exception {
		Class<?> clientClazz = Class.forName("com.all.login.Client");
		clientClazz.getConstructors()[0].setAccessible(true);
		Constructor<?> constructor = clientClazz.getConstructor();
		Object newInstance = constructor.newInstance();
		return (Callable<Boolean>) newInstance;
	}

}
