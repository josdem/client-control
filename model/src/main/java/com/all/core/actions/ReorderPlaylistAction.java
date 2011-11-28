package com.all.core.actions;

import java.util.List;

import com.all.action.ActionObject;
import com.all.client.model.LocalPlaylist;
import com.all.shared.model.Track;

public class ReorderPlaylistAction extends ActionObject {

	private final LocalPlaylist playlist;
	private final List<Track> tracks;
	private final int row;

	public ReorderPlaylistAction(LocalPlaylist playlist, List<Track> tracks, int row) {
		this.playlist = playlist;
		this.tracks = tracks;
		this.row = row;
	}

	public LocalPlaylist getPlaylist() {
		return playlist;
	}

	public List<Track> getTracks() {
		return tracks;
	}

	public int getRow() {
		return row;
	}

}
