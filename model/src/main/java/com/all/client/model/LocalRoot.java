package com.all.client.model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.all.client.util.DelegatedIterable;
import com.all.client.util.IteratorDelegate;
import com.all.shared.model.Folder;
import com.all.shared.model.MusicEntity;
import com.all.shared.model.Playlist;
import com.all.shared.model.Root;
import com.all.shared.model.SmartPlaylist;
import com.all.shared.model.Track;

public class LocalRoot implements Root, Serializable, IteratorDelegate<MusicEntity> {
	private static final long serialVersionUID = 1L;

	private List<SmartPlaylist> smartPlaylists = new ArrayList<SmartPlaylist>();

	private ContainerType type = ContainerType.LOCAL;

	private final LocalModelDao localModelDao;

	private String mail = "";

	public LocalRoot(LocalModelDao modelDao) {
		this.localModelDao = modelDao;
		smartPlaylists.add(LocalDefaultSmartPlaylist.LOOSE_TRACKS.create(modelDao));
		smartPlaylists.add(LocalDefaultSmartPlaylist.MOST_PLAYED.create(modelDao));
		smartPlaylists.add(LocalDefaultSmartPlaylist.RECENTLY_ADDED.create(modelDao));
		smartPlaylists.add(LocalDefaultSmartPlaylist.RECENTLY_PLAYED.create(modelDao));
		smartPlaylists.add(LocalDefaultSmartPlaylist.RECENTLY_DOWNLOADS.create(modelDao));
	}

	public Iterable<Track> getTracks() {
		return new DelegatedIterable<MusicEntity, Track>(localModelDao.findAll(Track.class), this);
	}

	public Iterable<Playlist> getPlaylists() {
		return new DelegatedIterable<MusicEntity, Playlist>(localModelDao.findAllWithRootParent(), this);
	}

	public Iterable<Folder> getFolders() {
		return new DelegatedIterable<MusicEntity, Folder>(localModelDao.findAll(Folder.class), this);
	}

	@Override
	public Iterable<SmartPlaylist> getSmartPlaylists() {
		return new DelegatedIterable<MusicEntity, SmartPlaylist>(smartPlaylists, this);
	}

	@Override
	public String toString() {
		return "Music";
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public ContainerType getType() {
		return type;
	}

	@Override
	public SmartPlaylist getAllMusicSmartPlaylist() {
		return LocalDefaultSmartPlaylist.ALL_MUSIC.create(localModelDao);
	}

	@Override
	public void add(MusicEntity object) {
		if (object != null) {
			if (object instanceof Track) {
				Track track = (Track) object;
				localModelDao.saveOrUpdate(track);
			}
			if (object instanceof Playlist) {
				Playlist playlist = (Playlist) object;
				if (playlist.getHashcode() == null || localModelDao.findById(LocalPlaylist.class, playlist.getHashcode()) == null) {
					localModelDao.save(playlist);
				}

			}
			if (object instanceof Folder) {
				Folder folder = (Folder) object;
				if (folder.getHashcode() == null || localModelDao.findById(LocalFolder.class, folder.getHashcode()) == null) {
					localModelDao.save(folder);
				}
			}
			if (object instanceof SmartPlaylist) {
				SmartPlaylist smartPlaylist = (SmartPlaylist) object;
				smartPlaylists.add(smartPlaylist);
			}
		}
	}

	@Override
	public void remove(MusicEntity object) {
		if (object != null) {
			if (object instanceof Track) {
				Track track = (Track) object;
				localModelDao.delete(track);
			}
			if (object instanceof Playlist) {
				Playlist playlist = (Playlist) object;
				localModelDao.delete(playlist);
			}
			if (object instanceof Folder) {
				Folder folder = (Folder) object;
				localModelDao.delete(folder);
			}
			if (object instanceof SmartPlaylist) {
				smartPlaylists.remove(object);
			}
		}

	}

	// TODO: refactor quite repeated
	@Override
	public boolean isEmpty(Class<? extends MusicEntity> clazz) {
		if (clazz != null) {
			if (clazz == Track.class) {
				return localModelDao.count(Track.class) == 0;
			}
			if (clazz == Playlist.class) {
				return localModelDao.count(Playlist.class) == 0;
			}
			if (clazz == Folder.class) {
				return localModelDao.count(Folder.class) == 0;
			}
			if (clazz == SmartPlaylist.class) {
				return smartPlaylists.isEmpty();
			}
		}
		return true;
	}

	@Override
	public int size(Class<? extends MusicEntity> clazz) {
		if (clazz != null) {
			if (clazz == Track.class) {
				return (int) localModelDao.count(Track.class);
			}
			if (clazz == Playlist.class) {
				return (int) localModelDao.count(Playlist.class);
			}
			if (clazz == Folder.class) {
				return (int) localModelDao.count(Folder.class);
			}
			if (clazz == SmartPlaylist.class) {
				return smartPlaylists.size();
			}
		}
		return -1;
	}

	@Override
	public void clear(Class<? extends MusicEntity> clazz) {
		if (clazz != null) {
			if (clazz == Track.class) {
				localModelDao.deleteAll(Track.class);
			}
			if (clazz == Playlist.class) {
				localModelDao.deleteAll(Playlist.class);
			}
			if (clazz == Folder.class) {
				localModelDao.deleteAll(Folder.class);
			}
			if (clazz == SmartPlaylist.class) {
				smartPlaylists.clear();
			}
		}
	}

	@Override
	public boolean contains(MusicEntity object) {
		if (object != null) {
			if (object instanceof Track) {
				return (localModelDao.findAll(Track.class)).contains(object);
			}
			if (object instanceof Playlist) {
				return localModelDao.findAllWithRootParent().contains(object);
			}
			if (object instanceof Folder) {
				return localModelDao.findAll(Folder.class).contains(object);
			}
			if (object instanceof SmartPlaylist) {
				return smartPlaylists.contains(object);
			}
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LocalRoot) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<File> getRootFiles() {
		return Collections.EMPTY_LIST;
	}

	@Override
	public String getOwnerMail() {
		return mail;
	}

	public void setUserMail(String mail) {
		this.mail = mail;
	}
}
