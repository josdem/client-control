package com.all.shared.stats;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class SkipCountStat extends AbstractAllStat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private long timestamp;
	
	private String email;

	private String hashcode;
	
	private int skipcount;
	
	
	@Deprecated
	public SkipCountStat() {
	}

	public SkipCountStat(String email, String hashcode, int skipcount) {
		this.email = email;
		this.hashcode = hashcode;
		this.skipcount = skipcount;
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
	
	public int getSkipcount() {
		return skipcount;
	}

	@Deprecated
	public void setSkipcount(int skipcount) {
		this.skipcount = skipcount;
	}
}
