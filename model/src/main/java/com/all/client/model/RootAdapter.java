package com.all.client.model;

import java.io.File;
import java.util.List;

import com.all.shared.model.Folder;
import com.all.shared.model.MusicEntity;
import com.all.shared.model.Playlist;
import com.all.shared.model.Root;
import com.all.shared.model.SmartPlaylist;
import com.all.shared.model.Track;

public abstract class RootAdapter implements Root {
	private static final long serialVersionUID = 1L;

	@Override
	public void add(MusicEntity object) {
		if (object != null) {
			if (object instanceof Track) {
				tracks().add((Track) object);
			}
			if (object instanceof Playlist) {
				playlists().add((Playlist) object);
			}
			if (object instanceof Folder) {
				folders().add((Folder) object);
			}
			if (object instanceof SmartPlaylist) {
				smartPlaylists().add((SmartPlaylist) object);
			}
		}
	}

	@Override
	public void remove(MusicEntity object) {
		if (object != null) {
			if (object instanceof Track) {
				tracks().remove((Track) object);
			}
			if (object instanceof Playlist) {
				playlists().remove((Playlist) object);
			}
			if (object instanceof Folder) {
				folders().remove((Folder) object);
			}
			if (object instanceof SmartPlaylist) {
				smartPlaylists().remove((SmartPlaylist) object);
			}
		}
	}

	@Override
	public boolean contains(MusicEntity object) {
		if (object != null) {
			if (object instanceof Track) {
				return tracks().contains((Track) object);
			}
			if (object instanceof Playlist) {
				return playlists().contains((Playlist) object);
			}
			if (object instanceof Folder) {
				return folders().contains((Folder) object);
			}
			if (object instanceof SmartPlaylist) {
				return smartPlaylists().contains((SmartPlaylist) object);
			}
		}
		return false;
	}

	@Override
	public void clear(Class<? extends MusicEntity> object) {
		if (object != null) {
			if (object == Track.class) {
				tracks().clear();
			}
			if (object == Playlist.class) {
				playlists().clear();
			}
			if (object == Folder.class) {
				folders().clear();
			}
			if (object == SmartPlaylist.class) {
				smartPlaylists().clear();
			}
		}
	}

	@Override
	public int size(Class<? extends MusicEntity> object) {
		if (object != null) {
			if (object == Track.class) {
				return tracks().size();
			}
			if (object == Playlist.class) {
				return playlists().size();
			}
			if (object == Folder.class) {
				return folders().size();
			}
			if (object == SmartPlaylist.class) {
				return smartPlaylists().size();
			}
		}
		return -1;
	}

	@Override
	public boolean isEmpty(Class<? extends MusicEntity> object) {
		if (object != null) {
			if (object == Track.class) {
				return tracks().isEmpty();
			}
			if (object == Playlist.class) {
				return playlists().isEmpty();
			}
			if (object == Folder.class) {
				return folders().isEmpty();
			}
			if (object == SmartPlaylist.class) {
				return smartPlaylists().isEmpty();
			}
		}
		return false;
	}

	@Override
	public Iterable<Folder> getFolders() {
		return folders();
	}

	@Override
	public Iterable<Playlist> getPlaylists() {
		return playlists();
	}

	@Override
	public Iterable<SmartPlaylist> getSmartPlaylists() {
		return smartPlaylists();
	}

	@Override
	public Iterable<Track> getTracks() {
		return tracks();
	}

	@Override
	public Iterable<File> getRootFiles() {
		return rootFiles();
	}

	abstract List<Track> tracks();

	abstract List<Folder> folders();

	abstract List<Playlist> playlists();

	abstract List<SmartPlaylist> smartPlaylists();

	abstract List<File> rootFiles();
}
