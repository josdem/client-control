package com.all.shared.newsfeed;

import java.util.ArrayList;
import java.util.List;

import com.all.shared.model.ContactInfo;
import com.all.shared.model.FeedTrack;
import com.all.shared.model.Track;

public class TrackContentFeed extends AbstractFeed {
	private int trackCount;
	private List<FeedTrack> tracks;

	@Deprecated
	public TrackContentFeed() {
	}

	public TrackContentFeed(ContactInfo owner, List<Track> tracks, int maxCount, int type) {
		super(owner, type);
		trackCount = tracks.size();
		this.tracks = new ArrayList<FeedTrack>();
		for (int i = 0; i < trackCount && i < maxCount; i++) {
			this.tracks.add(new FeedTrack(tracks.get(i).getHashcode(), tracks.get(i).getName(), tracks.get(i).getArtist()));
		}
	}

	public int getTrackCount() {
		return trackCount;
	}

	public void setTrackCount(int trackCount) {
		this.trackCount = trackCount;
	}

	public List<FeedTrack> getTracks() {
		return tracks;
	}

	public void setTracks(List<FeedTrack> tracks) {
		this.tracks = tracks;
	}

}
