package com.all.shared.mc;


public interface TrackStatus {
	
	int COMPLETE_UPLOAD = 99;

	enum Status{
		UPLOADED, UPLOADING, INCOMPLETE, NOT_AVAILABLE;
	}

	String getTrackId();
	
	Status getTrackStatus();

	int getLastChunkNumber();
}
