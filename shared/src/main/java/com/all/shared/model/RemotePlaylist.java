package com.all.shared.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RemotePlaylist implements Playlist {

	private static final long serialVersionUID = 1L;
	private List<Track> tracks;
	private Date creationDate;
	private Date modifiedDate;
	private String name;
	private String owner;
	// private Folder parentFolder;
	private String hashcode;
	private boolean smartPlaylist;
	private Date lastPlayed;

	public RemotePlaylist() {
		this.tracks = new ArrayList<Track>();
	}

	public RemotePlaylist(Playlist playlist) {
		this.tracks = new ArrayList<Track>();
		this.modifiedDate = playlist.getModifiedDate();
		this.name = playlist.getName();
		this.owner = playlist.getOwner();
		this.hashcode = playlist.getHashcode();
		this.smartPlaylist = playlist.isSmartPlaylist();
		this.lastPlayed = playlist.getLastPlayed();
	}

	@Override
	public int compareTo(Playlist that) {
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
	public boolean contains(Track song) {
		return tracks.contains(song);
	}

	@Override
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getOwner() {
		return owner;
	}

	@Override
	public Folder getParentFolder() {
		return null;
	}

	@Override
	public Track getTrack(int position) {
		return tracks.get(position);
	}

	@Override
	public List<Track> getTracks() {
		return tracks;
	}

	@Override
	public boolean isEmpty() {
		return tracks.isEmpty();
	}

	@Override
	public boolean isSmartPlaylist() {
		return smartPlaylist;
	}

	@Override
	public Date getLastPlayed() {
		return lastPlayed;
	}

	public void removeTrack(Track track) {
		tracks.remove(track);
	}

	public void setLastPlayed(Date lastPlayed) {
		this.lastPlayed = lastPlayed;
	}

	public void setName(String newName) {
		this.name = newName;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setSmartPlaylist(boolean isSmartPlaylist) {
		this.smartPlaylist = isSmartPlaylist;
	}

	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}

	@Override
	public int trackCount() {
		return tracks != null ? tracks.size() : 0;
	}

	@Override
	public int trackPosition(Track track) {
		return tracks.indexOf(track);
	}

	@Override
	public String toString() {
		return name == null ? "<Unnamed>" : name;
	}

	@Override
	public String getHashcode() {
		return this.hashcode;
	}

	public void setHashcode(String hashcode) {
		this.hashcode = hashcode;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	@Override
	public boolean isNewContent() {
		return false;
	}

}
