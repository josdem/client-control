package com.all.core.events;

public class UploadContentUpdateEvent extends UploadContentEvent {

	private int totalTracks;
	private int remainingSeconds;
	private long totalSize;
	private int progress;
	private final int remainingBytes;
	private final int uploadRate;

	public UploadContentUpdateEvent(long uploadId, long totalSize, int totalTracks, int remainingTracks,
			int remainingBytes, int remainingSeconds, int progress, int uploadRate) {
		super(uploadId);
		this.totalTracks = totalTracks;
		this.remainingBytes = remainingBytes;
		this.remainingSeconds = remainingSeconds;
		this.totalSize = totalSize;
		this.progress = progress;
		this.uploadRate = uploadRate;
	}

	public int getTotalTracks() {
		return totalTracks;
	}

	public int getRemainingSeconds() {
		return remainingSeconds;
	}

	public long getTotalSize() {
		return totalSize;
	}

	public int getProgress() {
		return progress;
	}

	public int getRemainingBytes() {
		return remainingBytes;
	}

	public int getUploadRate() {
		return uploadRate;
	}
}
