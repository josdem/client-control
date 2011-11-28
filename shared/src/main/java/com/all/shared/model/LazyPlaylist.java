package com.all.shared.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class LazyPlaylist implements Playlist {
	private static final Log LOG = LogFactory.getLog(LazyPlaylist.class);
	private static final long serialVersionUID = 1L;
	private List<Track> tracks;
	private transient TrackContainer trackSource;

	public LazyPlaylist() {
	}

	public void setTrackSource(TrackContainer trackSource) {
		this.trackSource = trackSource;
		this.tracks = null;
	}

	@Override
	public int compareTo(Playlist other) {
		if (this.getName() == null && other.getName() == null) {
			return 0;
		}
		if (this.getName() == null) {
			return 1;
		}
		if (other.getName() == null) {
			return -1;
		}
		return this.getName().compareToIgnoreCase(other.getName());
	}

	@Override
	public boolean contains(Track song) {
		return getTracks().contains(song);
	}

	@Override
	public List<Track> getTracks() {
		if (this.tracks == null || this.tracks.isEmpty()) {
			synchronized (this) {
				if (this.tracks == null || this.tracks.isEmpty()) {
					List<Track> tracks = new ArrayList<Track>();
					try {
						for (Track track : trackSource.getTracks()) {
							tracks.add(track);
						}
					} catch (Exception e) {
						LOG.error(e, e);
					}
					this.tracks = tracks;
				}
			}
		}
		return this.tracks;
	}

	@Override
	public boolean isEmpty() {
		return getTracks().isEmpty();
	}

	@Override
	public int trackCount() {
		return getTracks().size();
	}

	@Override
	public Track getTrack(int position) {
		return getTracks().get(position);
	}

	@Override
	public int trackPosition(Track track) {
		return getTracks().indexOf(track);
	}

	@Override
	public String toString() {
		return getName();
	}

	public boolean isTrackSourceSet() {
		return trackSource != null;
	}

}
