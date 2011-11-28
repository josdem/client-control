package com.all.client.model;

import java.io.File;

public class BrokenLinkValidator {

	public boolean isBrokenLink(File file) {
		if(file==null?true:(file.getName()==null)){
			throw new IllegalArgumentException("can not be possible to determine if 'null' is a broken link.");
		}
		return (!file.exists()) || (!file.isFile()) || file.isHidden() || file.getName().startsWith(".");
	}

}
