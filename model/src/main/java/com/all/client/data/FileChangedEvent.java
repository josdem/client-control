package com.all.client.data;

import java.io.File;
import java.util.EventObject;

public class FileChangedEvent extends EventObject {

	private static final long serialVersionUID = 5197270367293806099L;
	private File file;
	private FileEventType type;
	
	public FileChangedEvent(Object src, File fileAbsoluthPath, FileEventType type) {
		super(src);
		this.file = fileAbsoluthPath;
		this.type = type;
	}
	
	public File getFile() {
		return file;
	}
	
	public FileEventType getType() {
		return type;
	}
}
