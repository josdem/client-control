package com.all.launcher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Main {

	private static Log LOG = LogFactory.getLog(Main.class);

	public static void main(String[] args) throws Exception {
		try {
			new Launcher().launch();
		} catch (Exception e) {
			LOG.error(e, e);
		} finally {
			System.exit(0);
		}
	}

}
