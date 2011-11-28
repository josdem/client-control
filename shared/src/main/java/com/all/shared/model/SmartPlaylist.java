package com.all.shared.model;

import java.util.Collections;

public interface SmartPlaylist extends MusicEntity, TrackContainer {

	Playlist getPlaylist();

	boolean isReadOnly();

	boolean dropAllowed();

	String getLabel();

	public static final SmartPlaylist EMPTY = new SmartPlaylist() {
		private static final long serialVersionUID = 1L;

		@SuppressWarnings("unchecked")
		@Override
		public Iterable<Track> getTracks() {
			return Collections.EMPTY_LIST;
		}

		@Override
		public boolean isReadOnly() {
			return true;
		}

		@Override
		public Playlist getPlaylist() {
			return Playlist.EMPTY;
		}

		@Override
		public String getLabel() {
			return "";
		}

		@Override
		public boolean dropAllowed() {
			return false;
		}
	};
}
