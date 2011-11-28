package com.all.client.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;
import org.springframework.util.StringUtils;

import com.all.client.data.Hashcoder;
import com.all.client.util.Formatters;
import com.all.downloader.alllink.AllLink;
import com.all.shared.model.Track;
import com.all.shared.sync.SyncAble;
import com.all.shared.sync.SyncAbleAbstractImpl;
import com.all.shared.sync.SyncUpdateAble;

@Entity(name = "Track")
@Table(name = "Track")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LocalTrack extends SyncAbleAbstractImpl implements Track {

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@SyncUpdateAble
	private String hashcode;
	private String name;
	private String artist;
	private String album;
	private String genre;
	private String bitRate;
	private String sampleRate;
	private String year;
	private int duration;
	private String fileFormat;
	@SyncUpdateAble
	private String trackNumber;
	private boolean vbr;
	@SyncUpdateAble
	private boolean enabled = true;
	@SyncUpdateAble
	private String downloadString;
	@SyncUpdateAble
	private String fileName;
	@SyncUpdateAble
	private int playcount;
	@Temporal(TemporalType.TIMESTAMP)
	@SyncUpdateAble
	private Date lastPlayed;
	@Temporal(TemporalType.TIMESTAMP)
	@SyncUpdateAble
	private Date lastSkipped;
	@SyncUpdateAble
	private int skips;
	@SyncUpdateAble
	private int rating;
	@Temporal(TemporalType.TIMESTAMP)
	@Index(name = "DATE_ADDED_IDX")
	private Date dateAdded;
	private long size;
	@SyncUpdateAble
	private Boolean newContent;
	@SyncUpdateAble
	@Temporal(TemporalType.TIMESTAMP)
	@Index(name = "DATE_DOWNLOADED_IDX")
	private Date dateDownloaded;

	@Deprecated
	public LocalTrack() {
	}

	public LocalTrack(String name, String hashCode) {
		this.hashcode = hashCode;
		this.name = name;
		this.album = "";
		this.artist = "";
		this.genre = "";
		this.sampleRate = "";
		this.trackNumber = "";
		this.year = "";
		this.vbr = false;
		this.dateAdded = new Date();
		this.downloadString = new AllLink(hashCode, null).toString();
		this.newContent = false;
	}

	public final String getAlbum() {
		return album;
	}

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

	public final String getArtist() {
		return artist;
	}

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

	public final String getBitRate() {
		return bitRate;
	}

	@Override
	public int getDuration() {
		return duration;
	}

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

	public String getFileFormat() {
		return fileFormat;
	}

	public final String getGenre() {
		return genre;
	}

	public String getHashcode() {
		return hashcode;
	}

	public String getName() {
		return this.name == null ? "noName" : this.name;
	}

	public final String getSampleRate() {
		return sampleRate;
	}

	public final String getTrackNumber() {
		return trackNumber;
	}

	public final String getYear() {
		return year;
	}

	private boolean isArtistOrAlbumNull() {
		return !StringUtils.hasText(album) && !StringUtils.hasText(artist);
	}

	public final boolean isEnabled() {
		return enabled;
	}

	public final boolean isVBR() {
		return this.vbr;
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

	public final void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public final void setFileFormat(String format) {
		this.fileFormat = format;
	}

	public final void setGenre(String genre) {
		this.genre = genre;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final void setSampleRate(String sampleRate) {
		this.sampleRate = sampleRate;
	}

	public final void setTrackNumber(String num) {
		this.trackNumber = num;
	}

	public final void setVbr() {
		this.vbr = true;
	}

	public final void setVbr(boolean vbr) {
		this.vbr = vbr;
	}

	public boolean isVbr() {
		return vbr;
	}

	public final void setYear(String firstYear) {
		this.year = firstYear;
	}

	public final String toString() {
		return getName();
	}

	@Override
	public final int hashCode() {
		return hashcode == null ? super.hashCode() : hashcode.hashCode();
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

	public final void incrementPlaycount() {
		this.playcount++;
		this.lastPlayed = new Date();
	}

	public final void incrementSkips() {
		this.setSkips(this.getSkips() + 1);
		this.setLastSkipped(new Date());
	}

	public final Date getLastPlayed() {
		return lastPlayed;
	}

	public final Date getLastSkipped() {
		return lastSkipped;
	}

	public final int getPlaycount() {
		return playcount;
	}

	public final int getRating() {
		return rating;
	}

	public final int getSkips() {
		return skips;
	}

	public final void setLastPlayed(Date lastPlayed) {
		this.lastPlayed = lastPlayed;
	}

	public final void setPlaycount(int pc) {
		this.playcount = pc;
	}

	public final void setRating(int rate) {
		this.rating = rate;
	}

	public final Date getDateAdded() {
		return dateAdded;
	}

	public final void setDateAdded(Date date) {
		this.dateAdded = date;
	}

	public final void setSize(long fileSize) {
		this.size = fileSize;
	}

	public final String getFormattedSize() {
		return Formatters.formatDataSize(getSize(), false);
	}

	public long getSize() {
		return this.size;
	}

	@Override
	public String getDownloadString() {
		return downloadString;
	}

	public void setDownloadString(String downloadString) {
		this.downloadString = downloadString;
	}

	public final void setSkips(int skips) {
		this.skips = skips;
	}

	public final void setLastSkipped(Date lastSkipped) {
		this.lastSkipped = lastSkipped;
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

	public String getSyncAbleId() {
		return this.hashcode;
	}

	public void clone(SyncAble entity) {
		LocalTrack track = (LocalTrack) entity;
		this.setTrackNumber(track.getTrackNumber());
		this.setEnabled(track.isEnabled());
		this.setDownloadString(track.getDownloadString());
		this.setLastPlayed(track.getLastPlayed());
		this.setDateDownloaded(track.getDateDownloaded());
		this.setLastSkipped(track.getLastSkipped());
		this.setPlaycount(track.getPlaycount());
		this.setSkips(track.getSkips());
		this.setRating(track.getRating());
		this.setFileName(track.getFileName());
		this.setNewContent(track.isNewContent());
	}

	@Override
	public boolean isNewContent() {
		return newContent == null ? false : newContent;
	}

	public void setNewContent(boolean newContent) {
		this.newContent = newContent;
	}

	public Date getDateDownloaded() {
		return dateDownloaded;
	}
	
	public void setDateDownloaded(Date dateDownloaded) {
		this.dateDownloaded = dateDownloaded;
	}

	@Deprecated
	public static LocalTrack createEmptyTrack() {
		return createEmptyTrack("RandomTrackName-" + (Math.random() * 1000));
	}

	@Deprecated
	public static LocalTrack createEmptyTrack(String name) {
		String hash = "1";
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			md.update(name.getBytes());
			hash = Hashcoder.toHex(md.digest());
		} catch (NoSuchAlgorithmException e) {
		}
		return new LocalTrack(name, hash);
	}


}
