package com.all.client.data;

import java.util.List;

import com.all.shared.model.Folder;
import com.all.shared.model.Playlist;
import com.all.shared.model.Track;

public interface Connection {

	Folder findFolder(Long id);

	List<Folder> findAllFolders();

	List<Playlist> findAllLoosePlaylists();
	
	List<Track>findAllTracks();
}
