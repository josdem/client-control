package com.all.shared.stats;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class MediaContainerStat extends AbstractAllStat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private long timestamp;
	@NotNull
	private String email;

	private int newPlaylists;

	private int newFolders;
	
	@Deprecated
	public MediaContainerStat(){}

	public MediaContainerStat(String email, int newPlaylists, int newFolders){
		this.email = email;
		this.newPlaylists = newPlaylists;
		this.newFolders = newFolders;
		
	}
	
	@Override
	public String getEmail() {
		return email;
	}

	@Deprecated
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public Serializable getId() {
		return id;
	}

	@Override
	public long getTimestamp() {
		return timestamp;
	}

	@Override
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public int getNewPlaylists() {
		return newPlaylists;
	}
	
	@Deprecated
	public void setNewPlaylists(int newPlaylists) {
		this.newPlaylists = newPlaylists;
	}
	
	public int getNewFolders() {
		return newFolders;
	}
	
	@Deprecated
	public void setNewFolders(int newFolders) {
		this.newFolders = newFolders;
	}

}
