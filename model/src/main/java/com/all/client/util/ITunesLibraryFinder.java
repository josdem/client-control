package com.all.client.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.all.commons.Environment;

@Service
public class ITunesLibraryFinder {

	private static final String SEPARATOR = "/";
	private static final String REGQUERY_UTIL = "reg query ";
	private static final String REGSTR_TOKEN = "REG_SZ";
	private static final String COMPUTER_WINDOWS_MY_MUSIC_FOLDER = REGQUERY_UTIL
			+ "\"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Shell Folders\" /v \"My Music\"";
	private static final String ITUNES_DIR = "iTunes";
	private static final String ITUNES_LIBRARY_FILE = "iTunes Music Library.xml";
	private static final String MAC_MUSIC_DIR = "Music";
	
	private static final Log log = LogFactory.getLog(ITunesLibraryFinder.class);

	private String getCurrentPCMyMusicDirectory() {
		try {
			Process process = Runtime.getRuntime().exec(COMPUTER_WINDOWS_MY_MUSIC_FOLDER);
			StreamReader reader = new StreamReader(process.getInputStream());
			reader.start();
			process.waitFor();
			reader.join();
			String result = reader.getResult();
			int p = result.indexOf(REGSTR_TOKEN);
			if (p == -1) {
				return null;
			}
			return result.substring(p + REGSTR_TOKEN.length()).trim();
		} catch (Exception e) {
			return null;
		}
	}

	class StreamReader extends Thread {
		private InputStream is;
		private StringWriter sw;

		StreamReader(InputStream is) {
			this.is = is;
			sw = new StringWriter();
		}

		public void run() {
			try {
				int c;
				while ((c = is.read()) != -1) {
					sw.write(c);
				}
			} catch (IOException e) {
				log.error(e,e);
			}
		}

		String getResult() {
			return sw.toString().replace("\\", "/");
		}
	}
	
	public File getITunesLibraryFile() {
		File iTunesLibFile = null;
		if(Environment.isMac()) {
			iTunesLibFile = new File(new StringBuilder(System.getProperty("user.home")).append(SEPARATOR).append(MAC_MUSIC_DIR).append(SEPARATOR).append(ITUNES_DIR).append(SEPARATOR).append(ITUNES_LIBRARY_FILE).toString());			
			
			
		} else {
			iTunesLibFile = new File(new StringBuilder(getCurrentPCMyMusicDirectory()).append(SEPARATOR).append(ITUNES_DIR).append(SEPARATOR).append(ITUNES_LIBRARY_FILE).toString());
		}
		return iTunesLibFile.exists()? iTunesLibFile : null;
	}

	public static void main(String s[]) {
		ITunesLibraryFinder iTunesLibraryFinder = new ITunesLibraryFinder();
		log.info("Personal Music directory : " + iTunesLibraryFinder.getCurrentPCMyMusicDirectory());
		log.info(new StringBuilder(System.getProperty("user.home").replace("\\", "/")).append(SEPARATOR).append(MAC_MUSIC_DIR).append(SEPARATOR).append(ITUNES_DIR).append(SEPARATOR).append(ITUNES_LIBRARY_FILE).toString());
		
	}

}
