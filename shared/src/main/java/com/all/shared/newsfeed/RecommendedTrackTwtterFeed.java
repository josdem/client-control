package com.all.shared.newsfeed;

import com.all.shared.model.ContactInfo;
import com.all.shared.model.FeedTrack;

public class RecommendedTrackTwtterFeed extends AbstractFeed {

	private FeedTrack feedTrack;
	
	@Deprecated
	public RecommendedTrackTwtterFeed() {
	}

	public RecommendedTrackTwtterFeed(ContactInfo owner, FeedTrack feedTrack) {
		super(owner, FeedType.RECOMMENDED_TRACK_TWITTER);
		this.feedTrack = feedTrack;
	}

	@Deprecated
	public void setFeedTrack(FeedTrack feedTrack) {
		this.feedTrack = feedTrack;
	}
	
	public FeedTrack getFeedTrack() {
		return feedTrack;
	}
}
