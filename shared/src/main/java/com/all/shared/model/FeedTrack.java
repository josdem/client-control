package com.all.shared.model;

public class FeedTrack {

	private String hashcode;
	private String trackname;
	private String artist;

	@Deprecated
	public FeedTrack() {
	}
	
	public FeedTrack(String hashcode, String trackname, String artist) {
		super();
		this.hashcode = hashcode;
		this.trackname = trackname;
		this.artist = artist;
	}

	public String getArtist() {
		return artist;
	}
	
	public String getHashcode() {
		return hashcode;
	}

	public String getTrackname() {
		return trackname;
	}
	
	@Deprecated
	public void setTrackname(String trackname) {
		this.trackname = trackname;
	}
	
	@Deprecated
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	@Deprecated
	public void setHashcode(String hashcode) {
		this.hashcode = hashcode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((trackname == null) ? 0 : trackname.hashCode());
		result = prime * result + ((artist == null) ? 0 : artist.hashCode());
		result = prime * result + ((hashcode == null) ? 0 : hashcode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		FeedTrack other = (FeedTrack) obj;
		if (trackname == null) {
			if (other.trackname != null) {
				return false;
			}
		} else if (!trackname.equals(other.trackname)) {
			return false;
		}
		if (artist == null) {
			if (other.artist != null) {
				return false;
			}
		} else if (!artist.equals(other.artist)) {
			return false;
		}
		if (hashcode == null) {
			if (other.hashcode != null) {
				return false;
			}
		} else if (!hashcode.equals(other.hashcode)) {
			return false;
		}
		return true;
	}

	
}
