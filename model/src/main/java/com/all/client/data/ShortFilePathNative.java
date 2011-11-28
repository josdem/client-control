package com.all.client.data;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.Kernel32;

public class ShortFilePathNative {
	private static Log log = LogFactory.getLog(ShortFilePathNative.class);

    public static String getShortPath(File mediaFile) {
    	String fileName = mediaFile.getAbsolutePath();
    	char[] shortPath = null;
    	int length = Kernel32.INSTANCE.GetShortPathName(fileName, shortPath, 0);
    	shortPath = new char[length];
    	Kernel32.INSTANCE.GetShortPathName(fileName, shortPath, length);
    	String shortFileName = Native.toString(shortPath);
    	//TODO: refactor to pattern file or file util
    	shortFileName = shortFileName.replaceAll("\\\\", "/");
    	log.debug(shortFileName);
		return shortFileName;
    }

}
