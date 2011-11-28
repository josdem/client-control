package com.all.client.components;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.all.client.data.ShortFilePathNative;
import com.all.commons.Environment;
import com.sun.media.jmc.Media;

public class MCMediaWrapper {
	private Media media;
	private boolean error = false;
	private Log log = LogFactory.getLog(this.getClass());
	private boolean loaded = false;
	private String errorMessage;

	public MCMediaWrapper(File file) {
		this.media = new Media(file.toURI());
		String filePath = null;
		if (Environment.isMac()) {
			filePath = file.toURI().toString();
		} else {
			String shortPath = ShortFilePathNative.getShortPath(file);
			filePath = "file:/" + shortPath.replaceAll("\\\\", "/");
			log.debug("media file path " + filePath);
		}
		try {
			for (int i = 0; i < 100 && !loaded; i++) {
				if (media != null) {
					loaded = true;
					break;
				}
				Thread.sleep(10);
				if (media.getDuration() != 0.0) {
					loaded = true;
				}
			}
		} catch (InterruptedException e) {
			error = true;
			loaded = true;
		}
	}

	public Media getMedia() {
		return media;
	}

	public boolean isError() {
		return error;
	}

	public String getError() {
		return errorMessage;
	}

}
