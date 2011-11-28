package com.all.client.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.all.shared.model.Folder;
import com.all.shared.model.Playlist;
import com.all.shared.model.Track;

public class FailedImportPlaylist implements Playlist {

	static final long serialVersionUID = 1L;
	List<Track> tracks = new ArrayList<Track>();
	Date modifiedDate;
	String name;
	String owner;
	Folder parentFolder;
	String hashcode;
	boolean rootPlaylist;
	boolean showingIndexColumn;
	boolean smartPlaylist;
	boolean tranzient;
	Date lastPlayed;
	int trackCount;

	public FailedImportPlaylist(String name) {
		this.setName(name);
	}

	public final void add(Track track) {
		this.tracks.add(track);
	}


	@Override
	public final int compareTo(Playlist that) {
		if (this.getName() == null && that.getName() == null) {
			return 0;
		}
		if (this.getName() == null) {
			return 1;
		}
		if (that.getName() == null) {
			return -1;
		}
		return this.getName().compareToIgnoreCase(that.getName());
	}

	@Override
	public final boolean contains(Track song) {
		return tracks.contains(song);
	}

	@Override
	public final Date getModifiedDate() {
		return modifiedDate;
	}

	@Override
	public final String getName() {
		return name;
	}

	@Override
	public final String getOwner() {
		return owner;
	}

	@Override
	public final Folder getParentFolder() {
		return parentFolder;
	}

	@Override
	public final Track getTrack(int position) {
		return tracks.get(position);
	}

	@Override
	public final List<Track> getTracks() {
		return tracks;
	}

	@Override
	public final boolean isEmpty() {
		return tracks.isEmpty();
	}

	@Override
	public final boolean isSmartPlaylist() {
		return smartPlaylist;
	}

	public final void setName(String newName) {
		// required for the story own a smartplaylist.
		this.name = newName;
	}

	public final void setParentFolder(FailedImportFolder folder) {
		this.parentFolder = folder;
	}

	@Override
	public final int trackCount() {
		return trackCount;
	}

	@Override
	public final int trackPosition(Track track) {
		return tracks.indexOf(track);
	}

	@Override
	public final String toString() {
		return name == null ? "<Unnamed>" : name;
	}

	@Override
	public final String getHashcode() {
		return this.hashcode;
	}

	public final void setHashcode(String hashcode) {
		this.hashcode = hashcode;
	}

	@Override
	public final Date getCreationDate() {
		return null;
	}

	@Override
	public final Date getLastPlayed() {
		return null;
	}

	@Override
	public boolean isNewContent() {
		return false;
	}
}
