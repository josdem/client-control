package com.all.shared.alert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AllVersionNotification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private long timestamp;
	private String header;
	private String description;
	private String link;
	private boolean broadcast;
	private boolean excludeVersion;
	private String version;

	public AllVersionNotification(long timestamp, String header, String description, String link, boolean broadcast,
			boolean excludeVersion, String version) {
		this.timestamp = timestamp;
		this.header = header;
		this.description = description;
		this.link = link;
		this.broadcast = broadcast;
		this.excludeVersion = excludeVersion;
		this.version = version;
	}

	public AllVersionNotification() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public boolean isBroadcast() {
		return broadcast;
	}

	public void setBroadcast(boolean broadcast) {
		this.broadcast = broadcast;
	}

	public boolean isExcludeVersion() {
		return excludeVersion;
	}

	public void setExcludeVersion(boolean excludeVersion) {
		this.excludeVersion = excludeVersion;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
