package com.all.shared.stats;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserLibraryOverview extends AbstractAllStat implements  BufferedStat {

	@Id
	private String email;
	private int totalContacts;
	private int totalFolders;
	private int totalPlaylists;
	private int totalTracks;
	private long timestamp;

	public UserLibraryOverview() {
	}

	public UserLibraryOverview(String email, int totalContacts, int totalFolders, int totalPlaylists, int totalTracks) {
		this.email = email;
		this.totalContacts = totalContacts;
		this.totalFolders = totalFolders;
		this.totalPlaylists = totalPlaylists;
		this.totalTracks = totalTracks;
	}

	@Override
	public Serializable getId() {
		return email;
	}

	@Override
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getTotalContacts() {
		return totalContacts;
	}

	public void setTotalContacts(int totalContacts) {
		this.totalContacts = totalContacts;
	}

	public int getTotalFolders() {
		return totalFolders;
	}

	public void setTotalFolders(int totalFolders) {
		this.totalFolders = totalFolders;
	}

	public int getTotalPlaylists() {
		return totalPlaylists;
	}

	public void setTotalPlaylists(int totalPlaylists) {
		this.totalPlaylists = totalPlaylists;
	}

	public int getTotalTracks() {
		return totalTracks;
	}

	public void setTotalTracks(int totalTracks) {
		this.totalTracks = totalTracks;
	}

	@Override
	public long getTimestamp() {
		return timestamp;
	}

	@Override
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public StatKey key() {
		return new StatKey(getClass());
	}

}
