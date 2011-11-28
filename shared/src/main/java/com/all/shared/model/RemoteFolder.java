package com.all.shared.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RemoteFolder implements Folder {

	private static final long serialVersionUID = 1L;
	private String hashcode;
	private String name;
	private List<Playlist> playlists;
	private Playlist playlist;
	private Date modifiedDate;

	public RemoteFolder() {
	}

	public RemoteFolder(Folder folder) {
		this.hashcode = folder.getHashcode();
		this.name = folder.getName();
		this.playlists = new ArrayList<Playlist>(folder.getPlaylists().size());
		this.modifiedDate = folder.getModifiedDate();
	}

	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
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
		if (playlist == null) {
			RemotePlaylist temp = new RemotePlaylist();
			temp.setSmartPlaylist(true);
			List<Track> tracks = new ArrayList<Track>();
			if (playlists != null) {
				for (Playlist pl : playlists) {
					tracks.addAll(pl.getTracks());
				}
			}
			temp.setTracks(tracks);
			playlist = temp;
		}
		return playlist;
	}

	@Override
	public List<Playlist> getPlaylists() {
		return playlists;
	}

	@Override
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void remove(Playlist playlist) {
		playlists.remove(playlist);
	}

	public void setName(String newName) {
		this.name = newName;
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

	public void setHashcode(String hashcode) {
		this.hashcode = hashcode;
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
}
