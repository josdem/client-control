package com.all.shared.stats;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class DownloadStat extends AbstractAllStat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private long timestamp = -1L;
	@NotNull
	private String email;
	@NotNull
	private int action;
	@NotNull
	private String trackId;

	@Deprecated
	public DownloadStat() {
	}

	public DownloadStat(String email, int action, String trackId) {
		this.email = email;
		this.action = action;
		this.trackId = trackId;
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

	public void setAction(int action) {
		this.action = action;
	}

	public int getAction() {
		return action;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public String getTrackId() {
		return trackId;
	}
}
