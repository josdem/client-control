package com.all.shared.stats;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class TrackRatingStat extends AbstractAllStat implements BufferedStat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private long timestamp;

	@NotNull
	private String email;

	@NotNull
	private String hashcode;

	@NotNull
	private int ratingValue;

	@Deprecated
	public TrackRatingStat() {
	}

	public TrackRatingStat(String email, String hashcode, int ratingValue) {
		this.email = email;
		this.hashcode = hashcode;
		this.ratingValue = ratingValue;
	}

	@Deprecated
	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	@Deprecated
	public void setHashcode(String hashcode) {
		this.hashcode = hashcode;
	}

	public String getHashcode() {
		return hashcode;
	}

	@Deprecated
	public void setRatingValue(int ratingValue) {
		this.ratingValue = ratingValue;
	}

	public int getRatingValue() {
		return ratingValue;
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
	public StatKey key() {
		return new StatKey(getClass(), hashcode);
	}
}
