package com.all.shared.stats;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class SearchP2PWordStat extends AbstractAllStat{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private long timestamp;
	
	private String email;
	
	private String toSearch;

	@Deprecated
	public SearchP2PWordStat() {
	}
	
	public SearchP2PWordStat(String email, String toSearch){
		this.email = email;
		this.toSearch = toSearch;
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
	public void setEmail(String email){
		this.email = email;
	}

	@Deprecated
	public void setToSearch(String toSearch) {
		this.toSearch = toSearch;
	}

	public String getToSearch() {
		return toSearch;
	}
}
