/**
 * 
 */
package com.all.client.model;

import java.util.ArrayList;
import java.util.List;

import com.all.shared.model.Playlist;
import com.all.shared.model.SmartPlaylist;
import com.all.shared.model.Track;

public abstract class LocalDefaultSmartPlaylistImpl implements SmartPlaylist {
	private static final long serialVersionUID = 1L;
	private String label;
	private boolean readonly;
	private boolean dropAllowed;
	private LocalModelDao dao;

	private List<Track> trackList = null;
	private LocalPlaylist playlist;

	// private static final Log log =
	// LogFactory.getLog(DefaultLocalDefaultSmartPlaylistImpl.class);

	public LocalDefaultSmartPlaylistImpl(String label, boolean readonly, boolean dropAllowed, LocalModelDao dao) {
		this.label = label;
		this.readonly = readonly;
		this.dropAllowed = dropAllowed;
		this.dao = dao;
	}

	public void reset() {
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public Playlist getPlaylist() {
		if (playlist == null) {
			playlist = new LocalPlaylist(this.getLabel());
			playlist.setSmartPlaylist(true);
			playlist.setSmartTracks(new ArrayList<Track>());
		}
		trackList = getTrackList(dao);
		playlist.setSmartTracks(trackList);
		return playlist;
	}

	public abstract List<Track> getTrackList(LocalModelDao dao);

	@Override
	public boolean isReadOnly() {
		return readonly;
	}

	@Override
	public boolean dropAllowed() {
		return dropAllowed;
	}

	@Override
	public Iterable<Track> getTracks() {
		return getPlaylist().getTracks();
	}

}