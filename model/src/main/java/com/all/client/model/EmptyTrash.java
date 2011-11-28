package com.all.client.model;

import java.util.List;

import com.all.shared.model.Folder;
import com.all.shared.model.Playlist;
import com.all.shared.model.Track;

public class EmptyTrash implements Trash {

	@Override
	public void addTracks(List<Track> deletableTracks) {

	}

	@Override
	public void addPlaylistTrack(PlaylistTrack playlistTrack) {

	}

	@Override
	public void addTrack(Track track) {

	}

	@Override
	public void addFolderWithReferences(Folder folder) {

	}

	@Override
	public void addPlayLists(List<Playlist> playlists) {

	}

	@Override
	public void addFolders(List<Folder> folders) {

	}

	@Override
	public void addFolder(Folder folder) {

	}

	@Override
	public boolean contains(Track track) {

		return false;
	}

}
