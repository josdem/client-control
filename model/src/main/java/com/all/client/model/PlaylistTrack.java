package com.all.client.model;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.all.client.data.Hashcoder;
import com.all.shared.sync.ComplexSyncAbleAbstractImpl;
import com.all.shared.sync.ComplexSyncAbleField;
import com.all.shared.sync.SyncAble;
import com.all.shared.sync.SyncUpdateAble;

@Entity
public class PlaylistTrack extends ComplexSyncAbleAbstractImpl implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SyncUpdateAble
	private String id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "playlistFk")
	@SyncUpdateAble
	@ComplexSyncAbleField
	private LocalPlaylist playlist;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "trackFk")
	@SyncUpdateAble
	@ComplexSyncAbleField
	private LocalTrack track;

	@Column
	@SyncUpdateAble
	private int trackPosition;

	@Deprecated
	/*
	 * This Method is only used by reflection when SyncGenericConvert tries to
	 * create new instance.
	 */
	public PlaylistTrack() {
	}

	public PlaylistTrack(LocalTrack track, LocalPlaylist localPlaylist) {
		this.track = track;
		playlist = localPlaylist;
		this.id = createHashcode(this.track.getHashcode(), playlist.getHashcode());
	}

	public LocalPlaylist getPlaylist() {
		return playlist;
	}

	public void setPlaylist(LocalPlaylist playlist) {
		this.playlist = playlist;
	}

	public LocalTrack getTrack() {
		return track;
	}

	public void setTrack(LocalTrack track) {
		this.track = track;
	}

	@Override
	public String toString() {
		return "[" + playlist.toString() + "_hashcode_" + playlist.getHashcode() + "][" + track + "_hashcode_"
				+ track.getHashcode() + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PlaylistTrack && id.equals(((PlaylistTrack) obj).getId())) {
			return true;
		}
		return false;
	}

	public int getTrackPosition() {
		return trackPosition;
	}

	public void setTrackPosition(int trackPosition) {
		this.trackPosition = trackPosition;
	}

	@Override
	public int hashCode() {
		return getId() == null ? super.hashCode() : getId().hashCode();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String createHashcode(String trackHashCode, String playlistHashCode) {
		String hashcode = trackHashCode + playlistHashCode + System.currentTimeMillis() + new Random().nextLong();
		return createHashcode(hashcode);
	}

	private String createHashcode(String hashcode) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			md.update(hashcode.getBytes());
			hashcode = Hashcoder.toHex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			// log.error(e, e);
		}
		return hashcode;

	}

	@Override
	public void clone(SyncAble entity) {
		PlaylistTrack pl = (PlaylistTrack) entity;
		this.playlist = pl.getPlaylist();
		this.track = pl.getTrack();
		this.trackPosition = pl.getTrackPosition();
	}

	@Override
	public String getSyncAbleId() {
		return String.valueOf(getId());
	}

}
