package com.all.shared.stats;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class UpdaterStat extends AbstractAllStat {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private long timestamp;
	@NotNull
	private String email;

	private String clientVersion;

	private String osVersion;

	@Deprecated
	public UpdaterStat() {
	}

	public UpdaterStat( String email, String clientVersion, String osVersion) {
		super();
		this.email = email;
		this.clientVersion = clientVersion;
		this.osVersion = osVersion;
	}

	@Deprecated
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String getEmail() {
		return email;
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

	@Deprecated
	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}

	public String getClientVersion() {
		return clientVersion;
	}

	@Deprecated
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getOsVersion() {
		return osVersion;
	}

}
