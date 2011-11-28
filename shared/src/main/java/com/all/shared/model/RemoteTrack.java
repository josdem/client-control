package com.all.shared.model;

import java.util.Date;

import org.springframework.util.StringUtils;

public class RemoteTrack implements Track {

	private static final long serialVersionUID = 1L;
	String album;
	String artist;
	String bitRate;
	int duration;
	long size;
	String fileFormat;
	String genre;
	String hashcode;
	String name;
	String sampleRate;
	String trackNumber;
	String year;
	boolean enabled;
	boolean trashed;
	boolean vbr;
	String downloadString;

	private int playcount;
	private Date lastPlayed;
	private Date lastSkipped;
	private int skips;
	private int rating;
	private Date dateAdded;
	private String fileName;

	public RemoteTrack() {
	}

	public RemoteTrack(Track track) {
		this.album = track.getAlbum();
		this.artist = track.getArtist();
		this.bitRate = track.getBitRate();
		this.duration = track.getDuration();
		this.fileFormat = track.getFileFormat();
		this.genre = track.getGenre();
		this.hashcode = track.getHashcode();
		this.name = track.getName();
		this.sampleRate = track.getSampleRate();
		this.trackNumber = track.getTrackNumber();
		this.year = track.getYear();
		this.enabled = track.isEnabled();
		this.vbr = track.isVBR();
		this.downloadString = track.getDownloadString();
		this.playcount = track.getPlaycount();
		this.lastPlayed = track.getLastPlayed();
		this.lastSkipped = track.getLastSkipped();
		this.skips = track.getSkips();
		this.rating = track.getRating();
		this.dateAdded = track.getDateAdded();
		this.size = track.getSize();
		this.fileName = track.getFileName();
	}

	@Override
	public final String getAlbum() {
		return album;
	}

	@Override
	public final String getAlbumArtist() {
		if (isArtistOrAlbumNull()) {
			return "";
		}
		String albumArtist = this.album;
		if (!StringUtils.hasText(albumArtist)) {
			albumArtist = this.artist;
		}
		return albumArtist;
	}

	@Override
	public final String getArtist() {
		return artist;
	}

	@Override
	public final String getArtistAlbum() {
		if (isArtistOrAlbumNull()) {
			return "";
		}
		String artistAlbum = getArtist();
		if (!StringUtils.hasText(artistAlbum)) {
			artistAlbum = getAlbum();
		}
		return artistAlbum;
	}

	private boolean isArtistOrAlbumNull() {
		return !StringUtils.hasText(album) && !StringUtils.hasText(artist);
	}

	@Override
	public final String getBitRate() {
		return bitRate;
	}

	@Override
	public final int getDuration() {
		return duration;
	}

	@Override
	public final String getDurationMinutes() {
		long currentTime = duration;
		long seconds = currentTime % 60;
		long minutes = (currentTime % 3600) / 60;
		long hour = currentTime / 3600;

		StringBuilder formatedStr = new StringBuilder();

		String fmt = hour > 0 ? "%02d:%02d" : "%d:%02d";
		formatedStr.append(hour > 0 ? hour + ":" : "");
		formatedStr.append(String.format(fmt, minutes, seconds));

		return formatedStr.toString();
	}

	@Override
	public final String getFileFormat() {
		return fileFormat;
	}

	@Override
	public final String getGenre() {
		return genre;
	}

	@Override
	public final String getHashcode() {
		return hashcode;
	}

	@Override
	public final String getName() {
		return name;
	}

	@Override
	public final String getSampleRate() {
		return sampleRate;
	}

	@Override
	public final String getTrackNumber() {
		return trackNumber;
	}

	@Override
	public final String getYear() {
		return year;
	}

	@Override
	public final boolean isEnabled() {
		return enabled;
	}

	@Override
	public final boolean isVBR() {
		return vbr;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof Track) {
			Track t = (Track) obj;
			if (t.getHashcode() != null && this.hashcode != null) {
				return t.getHashcode().equals(hashcode);
			}
		}
		return false;
	}

	@Override
	public final int hashCode() {
		return hashcode == null ? super.hashCode() : hashcode.hashCode();
	}

	public final void setAlbum(String album) {
		this.album = album;
	}

	public final void setArtist(String artist) {
		this.artist = artist;
	}

	public final void setBitRate(String bitRate) {
		this.bitRate = bitRate;
	}

	public final void setDuration(int duration) {
		this.duration = duration;
	}

	public final void setDuration(Integer duration) {
		this.duration = duration;
	}

	public final void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public final void setFileFormat(String format) {
		this.fileFormat = format;
	}

	public final void setGenre(String genre) {
		this.genre = genre;
	}

	public final void setName(String firstTitle) {
		this.name = firstTitle;
	}

	public final void setSampleRate(String sampleRate) {
		this.sampleRate = sampleRate;
	}

	public final void setTrackNumber(String num) {
		this.trackNumber = num;
	}

	public final void setVbr() {
		setVBR(true);
	}

	public void setVBR(boolean vbr) {
		this.vbr = vbr;
	}

	public final void setYear(String firstYear) {
		this.year = firstYear;
	}

	@Override
	public final String toString() {
		return getName();
	}

	@Override
	public final Date getDateAdded() {
		return dateAdded;
	}

	@Override
	public final Date getLastPlayed() {
		return lastPlayed;
	}

	@Override
	public final Date getLastSkipped() {
		return lastSkipped;
	}

	@Override
	public final int getPlaycount() {
		return playcount;
	}

	@Override
	public final int getRating() {
		return rating;
	}

	@Override
	public final int getSkips() {
		return skips;
	}

	public void setSkips(int skips) {
		this.skips = skips;
	}

	public final void setDateAdded(Date date) {
		this.dateAdded = date;
	}

	public final void setLastPlayed(Date lastPlayed) {
		this.lastPlayed = lastPlayed;
	}

	public void setLastSkipped(Date lastSkipped) {
		this.lastSkipped = lastSkipped;
	}

	public final void setPlaycount(int pc) {
		this.playcount = pc;
	}

	public final void setRating(int rate) {
		this.rating = rate;
	}

	public final void setSize(long fileSize) {
		this.size = fileSize;
	}

	public final String getFormattedSize() {
		return null;
	}

	@Override
	public final String getDownloadString() {
		return downloadString;
	}

	public final void setDownloadString(String downloadString) {
		this.downloadString = downloadString;
	}

	@Override
	public final long getSize() {
		return size;
	}

	@Override
	public final String getBitRateDesc() {
		String formattedBitrate = null;
		if (bitRate != null) {
			StringBuilder message = new StringBuilder();
			message.append(bitRate);
			if (isVBR()) {
				message.append(" (VBR)");
			}
			message.append(" kbps");
			formattedBitrate = message.toString();
		}
		return formattedBitrate;
	}

	public final void setHashcode(String hashcode) {
		this.hashcode = hashcode;
	}

	@Override
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String name) {
		fileName = name;
	}

	@Override
	public boolean isNewContent() {
		return false;
	}

}
