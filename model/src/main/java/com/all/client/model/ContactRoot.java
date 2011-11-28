package com.all.client.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.all.shared.model.ContactInfo;
import com.all.shared.model.Folder;
import com.all.shared.model.ModelCollection;
import com.all.shared.model.Playlist;
import com.all.shared.model.RemotePlaylist;
import com.all.shared.model.SimpleSmartPlaylist;
import com.all.shared.model.SmartPlaylist;
import com.all.shared.model.Track;
import com.all.shared.model.TrackUtil;

public class ContactRoot extends RootAdapter {
	private static final long serialVersionUID = 1L;

	private SmartPlaylist allMusicSmartPlaylist = SimpleSmartPlaylist.empty();
	private List<SmartPlaylist> smartPlaylists = new ArrayList<SmartPlaylist>();
	private List<Folder> folders = new ArrayList<Folder>();
	private List<Playlist> playlists = new ArrayList<Playlist>();
	private List<Track> tracks = new ArrayList<Track>();

	private final ContactInfo owner;

	private final long creationTime = System.currentTimeMillis();

	public ContactRoot(ContactInfo owner) {
		this.owner = owner;
	}

	@Override
	public String getName() {
		return owner.getNickName();
	}

	@Override
	public ContainerType getType() {
		return ContainerType.CONTACT;
	}

	@Override
	public String getOwnerMail() {
		return owner.getEmail();
	}

	@Override
	public int hashCode() {
		return owner.getEmail().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ContactRoot) {
			return owner.getEmail().equals(((ContactRoot) obj).owner.getEmail());
		}
		return super.equals(obj);
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

	public void setContent(ModelCollection model, SmartPlaylist allMusic) {
		if (model != null) {
			TrackUtil trackUtil = new TrackUtil();
			model.filterTracksWithoutMagnetlink();
			folders = model.getFolders();
			playlists = model.getPlaylists();
			tracks = model.getTracks();
			smartPlaylists = model.getSmartPlaylists();
			this.allMusicSmartPlaylist = allMusic;
			trackUtil.filterTracksWithoutMagnetLink(allMusic.getTracks());
		} else {
			folders.clear();
			playlists.clear();
			smartPlaylists.clear();
			tracks.clear();
			allMusicSmartPlaylist = new SimpleSmartPlaylist("All Music", new RemotePlaylist(), true);
		}
	}

	public void setContent(ContactRoot root) {
		ModelCollection model = new ModelCollection(root.getFolders(), root.getPlaylists(), root.getTracks(), root.getSmartPlaylists());
		setContent(model, root.getAllMusicSmartPlaylist());
	}

	@SuppressWarnings("unchecked")
	@Override
	List<File> rootFiles() {
		return Collections.EMPTY_LIST;
	}

	public void copyContent(ContactRoot other) {
		this.tracks = other.tracks;
		this.playlists = other.playlists;
		this.folders = other.folders;
		this.allMusicSmartPlaylist = other.allMusicSmartPlaylist;
		this.smartPlaylists = other.smartPlaylists;
	}

	public long getCreationTime() {
		return creationTime;
	}

	public ContactInfo getOwner() {
		return owner;
	}
}
