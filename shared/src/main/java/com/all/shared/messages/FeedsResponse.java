package com.all.shared.messages;

import java.util.Date;
import java.util.List;

import com.all.shared.newsfeed.AllFeed;

public class FeedsResponse {
	private List<AllFeed> feeds;
	private Long ownerId;
	private Date currentServerTime;

	public FeedsResponse() {
		// TODO Auto-generated constructor stub
	}

	public FeedsResponse(List<AllFeed> feeds, Long ownerId) {
		this.setFeeds(feeds);
		this.ownerId = ownerId;
	}

	public FeedsResponse(List<AllFeed> feeds, Long ownerId, Date currentServerTime) {
		this.setFeeds(feeds);
		this.ownerId = ownerId;
		this.currentServerTime = currentServerTime;
	}

	@Deprecated
	public void setFeeds(List<AllFeed> feeds) {
		this.feeds = feeds;
	}

	public List<AllFeed> getFeeds() {
		return feeds;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	@Deprecated
	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public Date getCurrentServerTime() {
		return currentServerTime;
	}

	@Deprecated
	public void setCurrentServerTime(Date currentServerTime) {
		this.currentServerTime = currentServerTime;
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
		FeedsResponse other = (FeedsResponse) obj;
		
		if (ownerId == null) {
			if (other.ownerId != null) {
				return false;
			}
		} else if (!ownerId.equals(other.ownerId)) {
			return false;
		}
		if (feeds == null) {
			if (other.getFeeds() != null) {
				return false;
			}
		} else if (!feeds.equals(other.getFeeds())) {
			return false;
		}
		return true;
	}

}
