package com.all.client.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.all.shared.model.Folder;
import com.all.shared.model.Playlist;
import com.all.shared.model.Track;

public final class FailedImportFolder implements Folder {

	private static final long serialVersionUID = 1L;

	private String hashcode;
	private String name;
	private List<Playlist> playlists = new ArrayList<Playlist>();
	private Playlist playlist = new FailedImportPlaylist("");
	private Date modifiedDate;
	public static final int NAME_MAXLENGTH = 80;

	public FailedImportFolder(String name) {
		this.setName(name);
	}

	public void setPlaylist(Playlist playlist) {
		this.playlist = playlist;
	}

	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}

	public Playlist add(FailedImportPlaylist playlist) {
		this.playlists.add(playlist);
		playlist.setParentFolder(this);
		return playlist;
	}

	@Override
	public String getHashcode() {
		return hashcode;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Playlist getPlaylist() {
		return playlist;
	}

	@Override
	public List<Playlist> getPlaylists() {
		return playlists;
	}

	public void setName(String newName) {
		this.name = newName;
		if (this.name != null && this.name.length() > NAME_MAXLENGTH) {
			this.name = this.name.substring(0, NAME_MAXLENGTH);
		}
	}

	@Override
	public int compareTo(Folder that) {
		if (this.name == null && that.getName() == null) {
			return 0;
		}
		if (this.name == null) {
			return 1;
		}
		if (that.getName() == null) {
			return -1;
		}
		return this.name.compareToIgnoreCase(that.getName());
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public Date getCreationDate() {
		return null;
	}

	@Override
	public Iterable<Track> getTracks() {
		return getPlaylist().getTracks();
	}

	@Override
	public boolean isNewContent() {
		return false;
	}

	@Override
	public Date getModifiedDate() {
		return modifiedDate;
	}
}
