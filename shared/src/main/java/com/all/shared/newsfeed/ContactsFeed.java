package com.all.shared.newsfeed;

import com.all.shared.model.ContactInfo;

public class ContactsFeed extends AbstractFeed {
	private int totalContacts;

	@Deprecated
	public ContactsFeed() {
	}

	public ContactsFeed(ContactInfo owner, int totalContacts) {
		super(owner, FeedType.CONTACTS_FEED);
		this.totalContacts = totalContacts;
	}
	
	public int getTotalContacts() {
		return totalContacts;
	}

	public void setTotalContacts(int totalContacts) {
		this.totalContacts = totalContacts;
	}
}
