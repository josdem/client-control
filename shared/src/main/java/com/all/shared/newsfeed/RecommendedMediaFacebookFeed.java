package com.all.shared.newsfeed;

import java.util.List;

import com.all.shared.model.ContactInfo;
import com.all.shared.model.FeedTrack;

public class RecommendedMediaFacebookFeed extends AbstractFeed {

	public static final int MAXIMUM_ENTITIES = 3;
	private String facebookUsername;
	private int numberOfTracks;
	private List<String> folders;
	private List<String> playlists;
	private List<FeedTrack> tracks;

	@Deprecated
	public RecommendedMediaFacebookFeed() {
	}

	public RecommendedMediaFacebookFeed(ContactInfo owner, String facebookUsername, int numberOfTracks,
			List<String> folders, List<String> playlists, List<FeedTrack> tracks) {
		super(owner, FeedType.RECOMMENDED_MEDIA_FACEBOOK);
		this.facebookUsername = facebookUsername;
		this.numberOfTracks = numberOfTracks;
		this.folders = folders;
		this.playlists = playlists;
		this.tracks = tracks;
	}

	public String getFacebookUsername() {
		return facebookUsername;
	}

	public int getNumberOfTracks() {
		return numberOfTracks;
	}

	public void setFacebookUsername(String facebookUsername) {
		this.facebookUsername = facebookUsername;
	}

	public void setNumberOfTracks(int numberOfTracks) {
		this.numberOfTracks = numberOfTracks;
	}

	public List<String> getFolders() {
		return folders;
	}

	public List<String> getPlaylists() {
		return playlists;
	}

	public List<FeedTrack> getTracks() {
		return tracks;
	}

	@Deprecated
	public void setFolders(List<String> folders) {
		this.folders = folders;
	}

	@Deprecated
	public void setPlaylists(List<String> playlists) {
		this.playlists = playlists;
	}

	public void setTracks(List<FeedTrack> tracks) {
		this.tracks = tracks;
	}

}
