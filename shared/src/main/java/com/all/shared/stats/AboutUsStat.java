package com.all.shared.stats;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class AboutUsStat  extends AbstractAllStat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private long timestamp;
	
	private String email;

	private int aboutUsIndex;
	
	@Deprecated
	public AboutUsStat() {
	}

	public AboutUsStat(String email, int aboutUsIndex) {
		this.email = email;
		this.aboutUsIndex = aboutUsIndex;
	}
	
	public String getEmail() {
		return email;
	}

	@Deprecated
	public void setEmail(String email) {
		this.email = email;
	}

	public int getAboutUsIndex() {
		return aboutUsIndex;
	}

	@Deprecated
	public void setAboutUsIndex(int aboutUsIndex) {
		this.aboutUsIndex = aboutUsIndex;
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

}
