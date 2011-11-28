package com.all.shared.model;

import java.util.Date;
import java.util.List;

public interface Folder extends MusicEntity, TrackContainer, Comparable<Folder> {

	int MAX_PLAYLIST_ALLOWED = 100;

	Playlist getPlaylist();

	List<Playlist> getPlaylists();

	String getName();

	String getHashcode();

	Date getModifiedDate();

	Date getCreationDate();

	boolean isNewContent();

}
