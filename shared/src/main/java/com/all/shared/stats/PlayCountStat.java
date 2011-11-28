package com.all.shared.stats;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class PlayCountStat extends AbstractAllStat{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private long timestamp;
	
	private String email;
	
	private String hashcode;
	
	private int playcount;

	@Deprecated
	public PlayCountStat() {
	}
	
	public PlayCountStat(String email, String hashcode, int playcount) {
		this.email = email;
		this.hashcode = hashcode;
		this.playcount = playcount;
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

	@Override
	public String getEmail() {
		return email;
	}

	public String getHashcode() {
		return hashcode;
	}

	public int getPlaycount() {
		return playcount;
	}
	
	@Deprecated
	public void setEmail(String email){
		this.email = email;
	}
	
	@Deprecated
	public void setHashcode(String hashcode){
		this.hashcode = hashcode;
	}
	
	@Deprecated
	public void setPlaycount(int playcount){
		this.playcount = playcount;
	}

}
