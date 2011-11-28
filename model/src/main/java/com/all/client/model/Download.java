package com.all.client.model;

import java.io.File;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.all.downloader.bean.DownloadState;
import com.all.shared.model.Track;

@Entity
public class Download implements Serializable {

	static final long serialVersionUID = 1L;
	static final int TEN_MINUTES = 10 * 60 * 1000;
	static final int MIN_BITRATE = 3;
	static final String RESUME_EXTENSION = ".frd"; // fast resume data extension

	@Id
	String downloadId; // base32 hashcode from magnet uri created by the
	// downloader
	String trackId;
	String displayName;
	String downloadString;
	@Enumerated(EnumType.ORDINAL)
	DownloadState status = DownloadState.Queued;
	int progress;
	@Transient
	long rate;
	long size;
	@Transient
	int remainingSeconds;
	@Transient
	int freeNodes;
	@Transient
	int busyNodes;
	int priority;
	@Transient
	boolean started;
	String filepath;
	@Transient
	long moreSourcesTimestamp;
	@Transient
	private String ip;
	@Transient
	private int port;

	/**
	 * @Deprecated used only by hibernate
	 */
	@Deprecated
	public Download() {
	}

	public Download(Track track) {
		this.trackId = track.getHashcode();
		this.displayName = track.getName();
		this.downloadString = track.getDownloadString();
		this.status = DownloadState.Queued;
		this.downloadId = track.getHashcode();
		this.size = track.getSize();
	}

	@Override
	public String toString() {
		return "Name: " + displayName + " - Status: " + status + " - Progress: " + progress;
	}

	public String getDownloadId() {
		return downloadId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public int getProgress() {
		return progress;
	}

	public DownloadState getStatus() {
		return status;
	}

	public long getSize() {
		return size;
	}

	public int getRemainingSeconds() {
		return remainingSeconds;
	}

	public String getTrackId() {
		return trackId;
	}

	public long getRate() {
		return rate;
	}

	public int getFreeNodes() {
		return freeNodes;
	}

	public int getBusyNodes() {
		return busyNodes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((downloadId == null) ? 0 : downloadId.hashCode());
		result = prime * result + ((trackId == null) ? 0 : trackId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Download other = (Download) obj;
		if (downloadId == null) {
			if (other.downloadId != null) {
				return false;
			}
		} else if (!downloadId.equals(other.downloadId)) {
			return false;
		}
		if (trackId == null) {
			if (other.trackId != null) {
				return false;
			}
		} else if (!trackId.equals(other.trackId)) {
			return false;
		}
		return true;
	}

	public void setProgress(int i) {
		this.progress = i;
	}

	public void setStatus(DownloadState status) {
		this.status = status;
	}

	public void setRemainingSeconds(int time) {
		this.remainingSeconds = time;
	}

	public void setRate(long rate) {
		this.rate = rate;
	}

	public void setFreeNodes(int seeds) {
		this.freeNodes = seeds;
	}

	public void setBusyNodes(int leechs) {
		this.busyNodes = leechs;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}

	public String getDownloadString() {
		return downloadString;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public void startMoreSourcesTimer() {
		moreSourcesTimestamp = System.currentTimeMillis();
	}

	public void stopMoreSourcesTimer() {
		moreSourcesTimestamp = 0;
	}

	public void checkResourcesAvailable() {
		if (rate <= MIN_BITRATE) {
			if (moreSourcesTimestamp == 0) {
				moreSourcesTimestamp = System.currentTimeMillis();
			} else {
				long timePassed = System.currentTimeMillis() - moreSourcesTimestamp;
				if (timePassed >= TEN_MINUTES && status != DownloadState.Downloading) {
					status = DownloadState.MoreSourcesNeeded;
				}
			}
		} else {
			moreSourcesTimestamp = 0;
		}
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public File getDownloadFile() {
		File downloadFile = null;
		return (filepath != null && (downloadFile = new File(filepath)).exists()) ? downloadFile : null;
	}

	public File getFastResumeFile() {
		File downloadFile = null;
		return (filepath != null && (downloadFile = new File(filepath + RESUME_EXTENSION)).exists()) ? downloadFile : null;
	}

	public String getFastResumePath() {
		File fastResumeFile = getFastResumeFile();
		if (fastResumeFile != null) {
			return fastResumeFile.getAbsolutePath();
		}
		return null;
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public void complete() {
		status = DownloadState.Complete;
		progress = 100;
		remainingSeconds = 0;
	}

	public void updateTrackInfo(Track track) {
		this.trackId = track.getHashcode();
		this.displayName = track.getName();
		this.downloadString = track.getDownloadString();
		this.size = track.getSize();
	}
}
