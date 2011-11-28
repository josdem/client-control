package com.all.shared.model;

import java.util.Date;

public interface Track extends  MusicEntity {

	String getAlbum();

	String getAlbumArtist();

	String getArtist();

	String getArtistAlbum();

	String getBitRate();

	int getDuration();

	String getDurationMinutes();

	String getFileFormat();

	String getGenre();

	String getHashcode();

	String getName();

	String getSampleRate();

	String getTrackNumber();

	String getYear();

	boolean isEnabled();

	boolean isVBR();

	String getDownloadString();

	long getSize();

	Date getLastPlayed();

	Date getLastSkipped();

	int getPlaycount();

	int getRating();

	int getSkips();

	Date getDateAdded();

	String getFormattedSize();

	String getBitRateDesc();

	String getFileName();

	boolean isNewContent();

}