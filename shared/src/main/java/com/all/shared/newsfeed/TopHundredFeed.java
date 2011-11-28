package com.all.shared.newsfeed;

import com.all.shared.model.Category;
import com.all.shared.model.ContactInfo;
import com.all.shared.model.Playlist;

public class TopHundredFeed extends AbstractFeed {
	private String playlistHash;
	private long categoryId;
	private String playlistName;
	private String categoryName;

	@Deprecated
	public TopHundredFeed() {
	}

	public TopHundredFeed(ContactInfo owner, Category category, Playlist playlist) {
		super(owner, FeedType.TOP_HUNDRED);
		this.playlistHash = playlist.getHashcode();
		this.categoryId = category.getId();
		this.playlistName = playlist.getName();
		this.categoryName = category.getName();
	}

	public String getPlaylistHash() {
		return playlistHash;
	}

	public void setPlaylistHash(String playlistHash) {
		this.playlistHash = playlistHash;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getPlaylistName() {
		return playlistName;
	}

	public void setPlaylistName(String playlistName) {
		this.playlistName = playlistName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

}
