package com.all.shared.newsfeed;

import com.all.shared.model.ContactInfo;

public class MediaImportFeed extends AbstractFeed {

	private int totalTracks;

	private int totalPlaylists;

	private int totalFolders;

	private boolean isFromItunes;

	@Deprecated
	public MediaImportFeed() {
	}

	public MediaImportFeed(ContactInfo owner, int totalTracks, int totalPlaylists, int totalFolders) {
		super(owner, FeedType.MEDIA_IMPORT);
		this.totalTracks = totalTracks;
		this.totalPlaylists = totalPlaylists;
		this.totalFolders = totalFolders;
	}

	public int getTotalTracks() {
		return totalTracks;
	}

	public void setTotalTracks(int totalTracks) {
		this.totalTracks = totalTracks;
	}

	public int getTotalPlaylists() {
		return totalPlaylists;
	}

	public void setTotalPlaylists(int totalPlaylists) {
		this.totalPlaylists = totalPlaylists;
	}

	public int getTotalFolders() {
		return totalFolders;
	}

	public void setTotalFolders(int totalFolders) {
		this.totalFolders = totalFolders;
	}

	public boolean isFromItunes() {
		return isFromItunes;
	}

	public void setFromItunes(boolean isFromItunes) {
		this.isFromItunes = isFromItunes;
	}
}
