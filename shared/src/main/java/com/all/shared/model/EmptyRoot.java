package com.all.shared.model;

import java.io.File;
import java.util.Collections;

public class EmptyRoot implements Root {
	private static final long serialVersionUID = 1L;
	private final ContainerType containerType;
	private final String name;

	public EmptyRoot(String name, ContainerType containerType) {
		this.name = name;
		this.containerType = containerType;
	}

	public EmptyRoot(Root root) {
		this(root.getName(), root.getType());
	}

	@Override
	public void add(MusicEntity object) {
	}

	@Override
	public void clear(Class<? extends MusicEntity> clazz) {
	}

	@Override
	public boolean contains(MusicEntity object) {
		return false;
	}

	@Override
	public SmartPlaylist getAllMusicSmartPlaylist() {
		return SmartPlaylist.EMPTY;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<Folder> getFolders() {
		return Collections.EMPTY_LIST;
	}

	@Override
	public String getName() {
		return name;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<Playlist> getPlaylists() {
		return Collections.EMPTY_LIST;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<File> getRootFiles() {
		return Collections.EMPTY_LIST;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<SmartPlaylist> getSmartPlaylists() {
		return Collections.EMPTY_LIST;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<Track> getTracks() {
		return Collections.EMPTY_LIST;
	}

	@Override
	public ContainerType getType() {
		return containerType;
	}

	@Override
	public boolean isEmpty(Class<? extends MusicEntity> clazz) {
		return true;
	}

	@Override
	public void remove(MusicEntity object) {
	}

	@Override
	public int size(Class<? extends MusicEntity> clazz) {
		return 0;
	}

	@Override
	public String getOwnerMail() {
		return "-2@-2.com";
	}
}
