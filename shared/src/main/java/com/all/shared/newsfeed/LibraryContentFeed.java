package com.all.shared.newsfeed;

import java.util.ArrayList;
import java.util.List;

import com.all.shared.model.ContactInfo;
import com.all.shared.model.ModelCollection;

public abstract class LibraryContentFeed extends TrackContentFeed {
	private int folderCount;
	private int playlistCount;
	private List<String> folderNames;
	private List<String> playlistNames;

	@Deprecated
	public LibraryContentFeed() {
	}

	public LibraryContentFeed(ContactInfo owner, ModelCollection model, int type) {
		super(owner, model.getTracks(), 5, type);
		folderCount = model.getFolders().size();
		playlistCount = model.getPlaylists().size();
		folderNames = new ArrayList<String>();
		playlistNames = new ArrayList<String>();
		for (int i = 0; i < folderCount && i < 3; i++) {
			folderNames.add(model.getFolders().get(i).getName());
		}
		for (int i = 0; i < playlistCount && i < 3; i++) {
			playlistNames.add(model.getPlaylists().get(i).getName());
		}
	}

	public int getFolderCount() {
		return folderCount;
	}

	public void setFolderCount(int folderCount) {
		this.folderCount = folderCount;
	}

	public int getPlaylistCount() {
		return playlistCount;
	}

	public void setPlaylistCount(int playlistCount) {
		this.playlistCount = playlistCount;
	}

	public List<String> getFolderNames() {
		return folderNames;
	}

	public void setFolderNames(List<String> folderNames) {
		this.folderNames = folderNames;
	}

	public List<String> getPlaylistNames() {
		return playlistNames;
	}

	public void setPlaylistNames(List<String> playlistNames) {
		this.playlistNames = playlistNames;
	}

}
