package com.all.client.model;

import java.util.List;

import com.all.shared.model.Folder;
import com.all.shared.model.Playlist;
import com.all.shared.model.Track;

public interface Trash {

	public abstract void addTracks(List<Track> deletableTracks);

	public abstract void addPlaylistTrack(PlaylistTrack playlistTrack);

	public abstract void addTrack(Track track);

	public abstract void addFolderWithReferences(Folder folder);

	public abstract void addPlayLists(List<Playlist> playlists);

	public abstract void addFolders(List<Folder> folders);

	public abstract void addFolder(Folder folder);

	public abstract boolean contains(Track track);

}
