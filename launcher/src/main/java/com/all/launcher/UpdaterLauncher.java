package com.all.launcher;

import java.lang.reflect.Constructor;
import java.util.concurrent.Callable;

public class UpdaterLauncher {

	public static final String UPDATER_APPLICATION = "com.all.updater.UpdaterApplication";
	public static final String CHECK_UPDATER_APPLICATION = "com.all.updater.CheckUpdaterApplication";

	private UpdaterFactory updaterFactory = new UpdaterFactory();

	public boolean requiresUpdate() throws Exception {
		return getUpdaterCheckClass().call();
	}

	public void launch() throws Exception {
		getUpdaterClass().call();
	}

	private Callable<Boolean> getUpdaterCheckClass() throws Exception {
		return updaterFactory.create(CHECK_UPDATER_APPLICATION);
	}

	private Callable<?> getUpdaterClass() throws Exception {
		return updaterFactory.create(UPDATER_APPLICATION);
	}

	class UpdaterFactory {
		@SuppressWarnings("all")
		Callable<Boolean> create(String clazz) throws Exception {
			Class<?> clientClazz = Class.forName(clazz);
			clientClazz.getConstructors()[0].setAccessible(true);
			Constructor<?> constructor = clientClazz.getConstructor();
			Object newInstance = constructor.newInstance();
			return (Callable<Boolean>) newInstance;
		}
	}

}
