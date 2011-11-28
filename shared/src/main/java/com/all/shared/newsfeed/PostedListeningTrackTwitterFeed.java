package com.all.shared.newsfeed;

import com.all.shared.model.ContactInfo;
import com.all.shared.model.FeedTrack;

public class PostedListeningTrackTwitterFeed extends AbstractFeed{

private FeedTrack feedTrack;
	
	@Deprecated
	public PostedListeningTrackTwitterFeed() {
	}

	public PostedListeningTrackTwitterFeed(ContactInfo owner, FeedTrack feedTrack) {
		super(owner, FeedType.POSTED_TRACK_TWITTER);
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
