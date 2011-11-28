package com.all.login;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LoginLock {
	private static final Log log = LogFactory.getLog(LoginLock.class);

	public static final boolean getLock() {
		FileLock lock = null;
		try {
			File file = new File("allClientLock");
			FileChannel channel = new RandomAccessFile(file, "rw").getChannel();
			for (int i = 0; i < 10 && lock == null; i++) {
				try {
					lock = channel.tryLock();
				} catch (Exception e) {
				}
				if (lock == null) {
					log.warn("All application is currently running unable to continue... try " + (i + 1) + " of 10");
				}
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			return false;
		}
		return lock != null;
	}

}
