package com.all.client.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.all.shared.model.Folder;
import com.all.shared.model.Playlist;
import com.all.shared.model.Track;

// understands a container for deleted objects
public class LocalTrash implements Trash {

//	private Log log = LogFactory.getLog(this.getClass());
	private LocalModelDao dao;

	public LocalTrash(LocalModelDao dao) {
		super();
		this.dao = dao;
	}

	@Override
	public boolean contains(Track track) {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("hashcode", track.getHashcode());
		Track t = null;
		t = (Track) dao.findAll("from Track t where t.hashcode = :hashcode", values, false);
		return t != null;
	}

	@Override
	public void addFolder(Folder folder) {
		dao.delete(folder);
	}

	@Override
	public void addFolders(List<Folder> folders) {
		for (Folder folder : folders) {
			addFolderWithReferences(folder);
		}
	}

	@Override
	public void addPlayLists(List<Playlist> playlists) {
		for (Playlist playlist : playlists) {
			dao.delete(playlist);
		}
	}

	@Override
	public void addFolderWithReferences(Folder folder) {
		addPlayLists(folder.getPlaylists());
		addFolder(folder);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void addTrack(Track track) {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("trackFk", track);
		//TODO refactorr this as dao method
		List<PlaylistTrack> playlistTracks = (List<PlaylistTrack>) dao.findAll(
				"from PlaylistTrack pt where pt.track=:trackFk", values, true);

		for (PlaylistTrack playlistTrack : playlistTracks) {
			addPlaylistTrack(playlistTrack);
		}
		dao.delete(track);
	}

	@Override
	public void addPlaylistTrack(PlaylistTrack playlistTrack) {
		dao.delete(playlistTrack);
	}

	@Override
	public void addTracks(List<Track> deletableTracks) {
		for (Track track : deletableTracks) {
			addTrack(track);
		}
	}


}
