package com.all.client.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.all.shared.model.Folder;
import com.all.shared.model.Playlist;
import com.all.shared.model.SimpleSmartPlaylist;
import com.all.shared.model.SmartPlaylist;
import com.all.shared.model.Track;

public class DeviceRoot extends RootAdapter {

	private static final long serialVersionUID = -6312662640873930629L;

	private List<Playlist> playlists = new ArrayList<Playlist>();
	private List<Track> tracks = new ArrayList<Track>();
	private List<Folder> folders = new ArrayList<Folder>();
	private SimpleSmartPlaylist allMusicSmartPlaylist = SimpleSmartPlaylist.empty();
	private ArrayList<SmartPlaylist> smartPlaylists = new ArrayList<SmartPlaylist>();
	private final String name;

	private final File rootFile;

	public DeviceRoot(String name, File rootFile) {
		this.name = name;
		this.rootFile = rootFile;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public ContainerType getType() {
		return ContainerType.DEVICE;
	}

	@Override
	public SmartPlaylist getAllMusicSmartPlaylist() {
		return allMusicSmartPlaylist;
	}

	@Override
	List<Folder> folders() {
		return folders;
	}

	@Override
	List<Playlist> playlists() {
		return playlists;
	}

	@Override
	List<SmartPlaylist> smartPlaylists() {
		return smartPlaylists;
	}

	@Override
	List<Track> tracks() {
		return tracks;
	}

	@Override
	List<File> rootFiles() {
		ArrayList<File> arrayList = new ArrayList<File>(1);
		arrayList.add(rootFile);
		return arrayList;
	}

	@Override
	public String getOwnerMail() {
		return name + "@localhost.devices";
	}

}
