package com.all.client.model;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class FileContainer extends Container<Collection<File>> implements Iterable<File> {
	
	private final int fileCount;
	
	public FileContainer(String name, List<File> files) {
		super(name, files);
		fileCount = files.size();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<File> iterator() {
		if (getContent() == null || getContent().iterator() == null) {
			return Collections.EMPTY_LIST.iterator();
		}
		return getContent().iterator();
	}
	
	public int getFileCount() {
		return fileCount;
	}
}
