package com.all.shared.stats;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class RecommendedTrackFacebookStat extends AbstractAllStat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private long timestamp;
	
	private String email;

	private String hashcode;
	
	@Deprecated
	public RecommendedTrackFacebookStat() {
	}
	
	public RecommendedTrackFacebookStat(String email, String hashcode) {
		this.email = email;
		this.hashcode = hashcode;
	}

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

	public String getHashcode() {
		return hashcode;
	}
	
	public void setHashcode(String hashcode) {
		this.hashcode = hashcode;
	}
	
}
