package com.all.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ModelCollection implements Serializable, Cloneable {
	private static final long serialVersionUID = -6629240866106334785L;

	private List<Track> tracks = new ArrayList<Track>();
	private List<Playlist> playlists = new ArrayList<Playlist>();
	private List<SmartPlaylist> smartPlaylists = new ArrayList<SmartPlaylist>();
	private List<Folder> folders = new ArrayList<Folder>();
	private List<Object> others = new ArrayList<Object>();

	private boolean isRemote;

	private ModelSource source;

	public ModelCollection() {
	}

	public ModelCollection(Object... objects) {
		this(Arrays.asList(objects));
	}

	public ModelCollection(Iterable<?> objects) {
		addList(objects);
	}

	@Deprecated
	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}

	@Deprecated
	public void setFolders(List<Folder> folders) {
		this.folders = folders;
	}

	@Deprecated
	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}

	@Deprecated
	public void setSmartPlaylists(List<SmartPlaylist> smartPlaylists) {
		this.smartPlaylists = smartPlaylists;
	}

	public void addList(Iterable<?> iterable) {
		for (Object obj : iterable) {
			if (!add(obj) && obj instanceof Iterable<?>) {
				addList((Iterable<?>) obj);
			}
		}
	}

	public boolean add(Object obj) {
		if (obj instanceof Track) {
			tracks.add((Track) obj);
		} else if (obj instanceof SmartPlaylist) {
			smartPlaylists.add((SmartPlaylist) obj);
		} else if (obj instanceof Playlist) {
			playlists.add((Playlist) obj);
		} else if (obj instanceof Folder) {
			folders.add((Folder) obj);
		} else if (!(obj instanceof Iterable<?>)) {
			others.add(obj);
		} else {
			return false;
		}
		return true;
	}

	public boolean has(ModelTypes... types) {
		for (ModelTypes modelTypes : types) {
			switch (modelTypes) {
			case tracks:
				if (tracks.isEmpty()) {
					return false;
				}
				break;
			case playlists:
				if (playlists.isEmpty()) {
					return false;
				}
				break;
			case smartPlaylists:
				if (smartPlaylists.isEmpty()) {
					return false;
				}
				break;
			case folders:
				if (folders.isEmpty()) {
					return false;
				}
				break;
			case others:
				if (others.isEmpty()) {
					return false;
				}
				break;
			default:
				break;
			}
		}
		return true;
	}

	public boolean hasAny(ModelTypes... types) {
		for (ModelTypes modelTypes : types) {
			switch (modelTypes) {
			case tracks:
				if (!tracks.isEmpty()) {
					return true;
				}
				break;
			case playlists:
				if (!playlists.isEmpty()) {
					return true;
				}
				break;
			case smartPlaylists:
				if (!smartPlaylists.isEmpty()) {
					return true;
				}
				break;
			case folders:
				if (!folders.isEmpty()) {
					return true;
				}
				break;
			case others:
				if (!others.isEmpty()) {
					return true;
				}
				break;
			default:
				break;
			}
		}
		return false;
	}

	public boolean one(ModelTypes... types) {
		for (ModelTypes modelTypes : types) {
			switch (modelTypes) {
			case tracks:
				if (tracks.size() != 1) {
					return false;
				}
				break;
			case playlists:
				if (playlists.size() != 1) {
					return false;
				}
				break;
			case smartPlaylists:
				if (smartPlaylists.size() != 1) {
					return false;
				}
				break;
			case folders:
				if (folders.size() != 1) {
					return false;
				}
				break;
			case others:
				if (others.size() != 1) {
					return false;
				}
				break;
			default:
				break;
			}
		}
		return true;
	}

	public boolean empty(ModelTypes... types) {
		for (ModelTypes modelTypes : types) {
			switch (modelTypes) {
			case tracks:
				if (!tracks.isEmpty()) {
					return false;
				}
				break;
			case playlists:
				if (!playlists.isEmpty()) {
					return false;
				}
				break;
			case smartPlaylists:
				if (!smartPlaylists.isEmpty()) {
					return false;
				}
				break;
			case folders:
				if (!folders.isEmpty()) {
					return false;
				}
				break;
			case others:
				if (!others.isEmpty()) {
					return false;
				}
				break;
			default:
				break;
			}
		}
		return true;
	}

	public boolean only(ModelTypes... types) {
		return empty(ModelTypes.invert(types));
	}

	public boolean onlyOne(ModelTypes... types) {
		return one(types) && empty(ModelTypes.invert(types));
	}

	public int trackCount() {
		return rawTracks().size();
	}

	public List<Track> getTracks() {
		return tracks;
	}

	public List<Playlist> getPlaylists() {
		return playlists;
	}

	public List<SmartPlaylist> getSmartPlaylists() {
		return smartPlaylists;
	}

	public List<Folder> getFolders() {
		return folders;
	}

	public List<Object> getOthers() {
		return others;
	}

	public boolean isEmpty() {
		return empty(ModelTypes.values());
	}

	@Override
	public String toString() {
		return "F:" + folders.size() + " P:" + playlists.size() + " S:" + smartPlaylists.size() + " T:" + tracks.size()
				+ " O:" + others.size();
	}

	public void setRemote(boolean isRemote) {
		this.isRemote = isRemote;
	}

	public boolean isRemote() {
		return isRemote;
	}

	public void cleanUp() {
		for (Folder f : folders) {
			for (Playlist p : f.getPlaylists()) {
				this.playlists.remove(p);
			}
		}
	}

	public Collection<Track> rawTracks() {
		Set<Track> magnetlessTracks = new HashSet<Track>();

		for (Folder folder : getFolders()) {
			for (Playlist playlist : folder.getPlaylists()) {
				magnetlessTracks.addAll(playlist.getTracks());
			}
		}
		for (Playlist playlist : getPlaylists()) {
			magnetlessTracks.addAll(playlist.getTracks());
		}
		for (SmartPlaylist smartplaylist : getSmartPlaylists()) {
			magnetlessTracks.addAll(smartplaylist.getPlaylist().getTracks());
		}
		magnetlessTracks.addAll(getTracks());
		return magnetlessTracks;
	}

	public void filterTracksByHashcode(List<String> hashcodes) {
		filterFoldersByHashcode(hashcodes, getFolders());
		filterPlaylistsByHashcode(hashcodes, getPlaylists());
		filterSmartPlaylistsByHashcode(hashcodes, getSmartPlaylists());
		filterTracksByHashcode(hashcodes, tracks);

	}

	private void filterSmartPlaylistsByHashcode(List<String> hashcodes, Iterable<SmartPlaylist> unfilteredSmartPlaylists) {
		Iterator<SmartPlaylist> iterator = unfilteredSmartPlaylists.iterator();
		while (iterator.hasNext()) {
			SmartPlaylist smartPlaylist = iterator.next();
			filterTracksByHashcode(hashcodes, smartPlaylist.getTracks());
			if (smartPlaylist.getPlaylist().isEmpty()) {
				iterator.remove();
			}
		}
	}

	private void filterPlaylistsByHashcode(List<String> hashcodes, Iterable<Playlist> unfilteredPlaylists) {
		Iterator<Playlist> iterator = unfilteredPlaylists.iterator();
		while (iterator.hasNext()) {
			Playlist playlist = iterator.next();
			filterTracksByHashcode(hashcodes, playlist.getTracks());
			if (playlist.isEmpty()) {
				iterator.remove();
			}
		}
	}

	private void filterFoldersByHashcode(List<String> hashcodes, Iterable<Folder> unfilteredFolders) {
		Iterator<Folder> folderIterator = unfilteredFolders.iterator();
		while (folderIterator.hasNext()) {
			Folder folder = folderIterator.next();
			filterTracksByHashcode(hashcodes, folder.getTracks());
			if (folder.getPlaylist().isEmpty()) {
				folderIterator.remove();
				continue;
			}
			filterPlaylistsByHashcode(hashcodes, folder.getPlaylists());
		}
	}

	private void filterTracksByHashcode(List<String> hascodes, Iterable<Track> unfilteredTracks) {
		Iterator<Track> iterator = unfilteredTracks.iterator();
		while (iterator.hasNext()) {
			Track track = iterator.next();
			if (hascodes.contains(track.getHashcode())) {
				continue;
			}
			iterator.remove();
		}
	}

	public void filterTracksWithoutMagnetlink() {
		TrackUtil trackUtil = new TrackUtil();
		for (Folder folder : getFolders()) {
			trackUtil.filterTracksWithoutMagnetLink(folder.getTracks());
			for (Playlist playlist : folder.getPlaylists()) {
				trackUtil.filterTracksWithoutMagnetLink(playlist.getTracks());
			}
		}
		for (Playlist playlist : getPlaylists()) {
			trackUtil.filterTracksWithoutMagnetLink(playlist.getTracks());
		}
		for (SmartPlaylist smartplaylist : getSmartPlaylists()) {
			trackUtil.filterTracksWithoutMagnetLink(smartplaylist.getTracks());
		}
		trackUtil.filterTracksWithoutMagnetLink(tracks);
	}

	public boolean contains(Object obj) {
		for (Track track : tracks) {
			if (track == obj || track.equals(obj)) {
				return true;
			}
		}
		for (Playlist playlist : playlists) {
			if (playlist == obj || playlist.equals(obj)) {
				return true;
			}

		}
		for (Folder folder : folders) {
			if (folder == obj || folder.equals(obj)) {
				return true;
			}
		}
		return false;
	}

	public long size() {
		long size = 0;
		for (Track track : rawTracks()) {
			size += track.getSize();
		}
		return size;
	}

	public void source(ModelSource source) {
		this.source = source;
	}

	public ModelSource source() {
		return this.source;
	}
}
