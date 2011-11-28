package com.all.launcher;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.all.commons.AppPathUtil;
import com.all.commons.Environment;

public class LauncherConfig extends AppPathUtil {

	private static final Log LOG = LogFactory.getLog(LauncherConfig.class);

	private static final String CONFIG_PATH = "AllDotComMcClient.properties";
	private static final String EXEC_MAC_KEY = "execPath.mac";
	private static final String EXEC_WINDOWS_KEY = "execPath.windows";
	private static final String LINUX_WINDOWS_KEY = "execPath.linux";

	public String applicationComand() throws IOException, LauncherConfigException {
		Properties properties = new Properties();
		properties.load(getPropertiesAsInputStream());

		String applicationCommand = null;

		if (Environment.isMac()) {
			applicationCommand = properties.get(EXEC_MAC_KEY).toString();
		} else if (Environment.isWindows()) {
			applicationCommand = properties.get(EXEC_WINDOWS_KEY).toString();
		} else if(Environment.isLinux()) {
			applicationCommand = properties.get(LINUX_WINDOWS_KEY).toString();
		}

		LOG.info("Launcher prepared with executable for the application in: " + applicationCommand);

		return applicationCommand;
	}

	private InputStream getPropertiesAsInputStream() throws LauncherConfigException {
		return LauncherConfig.class.getClassLoader().getResourceAsStream(CONFIG_PATH);
	}

}
