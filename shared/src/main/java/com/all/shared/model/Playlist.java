package com.all.shared.model;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public interface Playlist extends MusicEntity, TrackContainer, Comparable<Playlist> {

	int NAME_MAXLENGTH = 80;

	int NAME_MINLENGTH = 1;

	boolean contains(Track song);

	List<Track> getTracks();

	String getOwner();

	boolean isEmpty();

	int trackCount();

	Track getTrack(int position);

	Folder getParentFolder();

	Date getModifiedDate();

	Date getCreationDate();

	Date getLastPlayed();

	int trackPosition(Track track);

	boolean isSmartPlaylist();

	String getName();

	String getHashcode();

	boolean isNewContent();

	public static final Playlist EMPTY = new Playlist() {
		private static final long serialVersionUID = 1L;

		@Override
		public int compareTo(Playlist that) {
			return 0;
		}

		@Override
		public boolean contains(Track song) {
			return false;
		}

		@Override
		public Date getCreationDate() {
			return new Date();
		}

		@Override
		public String getHashcode() {
			return "";
		}

		@Override
		public Date getLastPlayed() {
			return new Date();
		}

		@Override
		public Date getModifiedDate() {
			return new Date();
		}

		@Override
		public String getName() {
			return "";
		}

		@Override
		public String getOwner() {
			return "";
		}

		@Override
		public Folder getParentFolder() {
			return null;
		}

		@Override
		public Track getTrack(int position) {
			return null;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<Track> getTracks() {
			return Collections.EMPTY_LIST;
		}

		@Override
		public boolean isEmpty() {
			return true;
		}

		@Override
		public boolean isSmartPlaylist() {
			return true;
		}

		@Override
		public int trackCount() {
			return 0;
		}

		@Override
		public int trackPosition(Track track) {
			return 0;
		}

		@Override
		public boolean isNewContent() {
			return false;
		}

	};

}