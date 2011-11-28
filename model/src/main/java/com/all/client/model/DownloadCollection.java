package com.all.client.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DownloadCollection implements Serializable, Cloneable {
	private static final long serialVersionUID = -6629240866106334785L;

	private List<Download> downloads = new ArrayList<Download>();

	public DownloadCollection() {
	}

	public DownloadCollection(Download... objects) {
		this(Arrays.asList(objects));
	}

	public DownloadCollection(Iterable<Download> objects) {
		add(objects);
	}

	public void add(Iterable<Download> iterable) {
		for (Download obj : iterable) {
			add(obj);
		}
	}

	public void add(Download obj) {
		downloads.add(obj);
	}

	public List<Download> getDownloads() {
		return downloads;
	}

}
