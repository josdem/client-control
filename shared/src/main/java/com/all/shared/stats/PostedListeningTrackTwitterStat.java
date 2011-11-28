package com.all.shared.stats;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class PostedListeningTrackTwitterStat extends AbstractAllStat{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private long timestamp;
	
	private String email;

	private String hashcode;
	
	@Deprecated
	public PostedListeningTrackTwitterStat() {
		super();
	}
	
	public PostedListeningTrackTwitterStat(String email, String hashcode){
		super();
		this.email = email;
		this.hashcode = hashcode;
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
	
	@Deprecated
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getHashcode() {
		return hashcode;
	}
	
	@Deprecated
	public void setHashcode(String hashcode) {
		this.hashcode = hashcode;
	}

}
