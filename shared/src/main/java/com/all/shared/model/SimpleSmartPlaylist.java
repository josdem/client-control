package com.all.shared.model;


public class SimpleSmartPlaylist implements SmartPlaylist {
	private static final long serialVersionUID = 1L;
	private Playlist playlist;
	private boolean readOnly;
	private String label;

	public SimpleSmartPlaylist(String label, Playlist playlist, boolean readOnly) {
		this.label = label;
		this.playlist = playlist;
		this.readOnly = readOnly;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public Playlist getPlaylist() {
		return playlist;
	}

	@Override
	public boolean isReadOnly() {
		return readOnly;
	}
	
	@Override
	public boolean dropAllowed() {
		return false;
	}
	
	public static SimpleSmartPlaylist empty() {
		return new SimpleSmartPlaylist("All Music", new RemotePlaylist(), true);
	}

	@Override
	public Iterable<Track> getTracks() {
		return playlist.getTracks();
	}
	
}
