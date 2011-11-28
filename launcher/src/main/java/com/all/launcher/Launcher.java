package com.all.launcher;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.all.commons.Environment;

public class Launcher {

	private final Log log = LogFactory.getLog(Launcher.class);

	private LauncherConfig launcherConfig = new LauncherConfig();

	public void launch() throws Exception {
		boolean updaterRan = false;
		boolean clientNeedsRestart = false;

		updaterRan = runUpdater();

		log.info("Update run: " + updaterRan);

		if (!updaterRan) {
			log.info("Running client...");
			clientNeedsRestart = runClient();
			log.info("Client process result: " + clientNeedsRestart);
		}

		if (updaterRan || clientNeedsRestart) {
			log.info("Restarting application...");
			createApplicationProcess();
		}
	}

	private boolean runClient() throws Exception {
		ClientLauncher clientLauncher = new ClientLauncher();
		return clientLauncher.launch();
	}

	private boolean runUpdater() throws Exception {
		UpdaterLauncher updaterLauncher = new UpdaterLauncher();
		if (updaterLauncher.requiresUpdate()) {
			updaterLauncher.launch();
			return true;
		}
		return false;
	}

	private void createApplicationProcess() {
		try {
			String applicationComand = launcherConfig.applicationComand();

			log.info("Running client with command: " + applicationComand);

			ProcessBuilder processBuilder = new ProcessBuilder(applicationComand);

			setDirectoryIfMac(processBuilder);
			processBuilder.start();
		} catch (Exception err) {
			log.error(err, err);
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			log.error(e, e);
		}
	}

	// TODO Create a ProcessBuilderFactory, depending on the OS
	private void setDirectoryIfMac(ProcessBuilder processBuilder) {
		if (Environment.isMac()) {
			String appRootPath = launcherConfig.getAppRootPath();
			processBuilder.directory(new File(appRootPath, "/Contents/MacOS/"));
			log.info("Working directory: " + processBuilder.directory() + " " + appRootPath);
		} else {
			processBuilder.directory(new File(System.getProperty("user.dir")));
			log.info("Working directory: " + processBuilder.directory());
		}
	}

}
