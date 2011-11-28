package com.all.shared.newsfeed;

import com.all.shared.model.ContactInfo;

public class TotalTracksFeed extends AbstractFeed {

	private long trackCount;

	@Deprecated
	public TotalTracksFeed() {
	}

	public TotalTracksFeed(ContactInfo contact, long trackCount) {
		super(contact, FeedType.TOTAL_TRACKS);
		this.trackCount = trackCount;
	}

	public long getTrackCount() {
		return trackCount;
	}

	public void setTrackCount(long trackCount) {
		this.trackCount = trackCount;
	}

}
