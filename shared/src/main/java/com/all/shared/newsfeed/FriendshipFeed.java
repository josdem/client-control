package com.all.shared.newsfeed;

import java.util.ArrayList;
import java.util.List;

import com.all.shared.model.ContactInfo;

/**
 * @understands A bean for Friendship Feed
 */

public class FriendshipFeed extends AbstractFeed {
	private ContactInfo requested;
	
	@Deprecated
	public FriendshipFeed() {
	}

	public FriendshipFeed(ContactInfo owner, ContactInfo requested) {
		super(owner, FeedType.FRIENDSHIP);
		this.requested = requested;
	}

	public ContactInfo getRequested() {
		return requested;
	}
	
	public void setRequested(ContactInfo requested) {
		this.requested = requested;
	}
	
	@Override
	public List<ContactInfo> contacts() {
		List<ContactInfo> contacts = new ArrayList<ContactInfo>();
		contacts.add(getOwner());
		contacts.add(requested);
		return contacts;
	}
}
