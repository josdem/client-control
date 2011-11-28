package com.all.core.model;

public class TopHundredModelContainer implements ModelContainerView{

	private Long categoryId;
	private String playlistHash = null;
	
	public TopHundredModelContainer(Long categoryId) {
		this.categoryId = categoryId;
	}
	
	public TopHundredModelContainer(Long categoryId, String playlistHash) {
		this.categoryId = categoryId;
		this.playlistHash = playlistHash;
	}
	
	
	public Long getCategoryId() {
		return categoryId;
	}
	
	public String getPlaylistHash() {
		return playlistHash;
	}
}
