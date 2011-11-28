package com.all.shared.model;

import java.io.File;
import java.io.Serializable;

public interface Root extends Serializable, TrackContainer {

	public enum ContainerType {
		LOCAL, DEVICE, REMOTE, CONTACT
	}

	ContainerType getType();

	String getName();

	void add(MusicEntity object);

	void remove(MusicEntity object);

	int size(Class<? extends MusicEntity> clazz);

	boolean isEmpty(Class<? extends MusicEntity> clazz);

	void clear(Class<? extends MusicEntity> clazz);

	SmartPlaylist getAllMusicSmartPlaylist();

	Iterable<Track> getTracks();

	Iterable<Playlist> getPlaylists();

	Iterable<Folder> getFolders();

	Iterable<SmartPlaylist> getSmartPlaylists();

	Iterable<File> getRootFiles();

	boolean contains(MusicEntity object);

	String getOwnerMail();

}