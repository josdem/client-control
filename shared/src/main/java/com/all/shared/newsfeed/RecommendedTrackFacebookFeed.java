package com.all.shared.newsfeed;

import com.all.shared.model.ContactInfo;
import com.all.shared.model.FeedTrack;

public class RecommendedTrackFacebookFeed extends AbstractFeed {

	private FeedTrack feedTrack;

	@Deprecated
	public RecommendedTrackFacebookFeed() {
	}
	
	public RecommendedTrackFacebookFeed(ContactInfo owner, FeedTrack feedTrack) {
		super(owner, FeedType.RECOMMENDED_TRACK_FACEBOOK);
		this.feedTrack = feedTrack;
	}

	public FeedTrack getFeedTrack() {
		return feedTrack;
	}
	
	public void setFeedTrack(FeedTrack feedTrack) {
		this.feedTrack = feedTrack;
	}
	
}
