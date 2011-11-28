package com.all.client.model;

import com.all.downloader.search.SearchData;
import com.all.shared.model.Track;

public class DecoratedSearchData implements SearchData {
	private final SearchData searchData;
	private int index;
	private String allLink;
	private int peers;
	
	public DecoratedSearchData(SearchData searchData, int index, String allLink) {
		this.searchData = searchData;
		this.index = index;
		this.allLink = allLink;
		this.peers = searchData.getPeers();
	}

	@Override
	public String getName() {
		return searchData.getName();
	}

	@Override
	public String getFileType() {
		return searchData.getFileType();
	}

	@Override
	public long getSize() {
		return searchData.getSize();
	}

	@Override
	public int getPeers() {
		return peers;
	}

	@Override
	public String getFileHash() {
		return searchData.getFileHash();
	}

	@Override
	public Track toTrack() {
		return searchData.toTrack();
	}

	public int getIndex() {
		return index;
	}
	
	public String getAllLink() {
		return allLink;
	}

	public void setPeers(int peers) {
		this.peers = peers;
	}
	
	@Override
	public Source getSource() {
		return this.searchData.getSource();
	}
}
