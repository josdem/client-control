package com.all.shared.stats;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class TopHundredStatId implements Serializable {
	private static final long serialVersionUID = 1L;
	private long categoryId;
	private String playlistHash;
	private String email;

	@Deprecated
	public TopHundredStatId() {
	}

	public TopHundredStatId(long categoryId, String playlistHash, String email) {
		this.categoryId = categoryId;
		this.playlistHash = playlistHash;
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public String getPlaylistHash() {
		return playlistHash;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public void setPlaylistHash(String playlistHash) {
		this.playlistHash = playlistHash;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (categoryId ^ (categoryId >>> 32));
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((playlistHash == null) ? 0 : playlistHash.hashCode());
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
		TopHundredStatId other = (TopHundredStatId) obj;
		if (categoryId != other.categoryId) {
			return false;
		}
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (playlistHash == null) {
			if (other.playlistHash != null) {
				return false;
			}
		} else if (!playlistHash.equals(other.playlistHash)) {
			return false;
		}
		return true;
	}

}
