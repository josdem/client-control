package com.all.shared.newsfeed;

import java.util.ArrayList;
import java.util.List;

import com.all.shared.model.ContactInfo;

public class RemoteLibraryBrowsingFeed extends AbstractFeed {
	private ContactInfo visited;

	@Deprecated
	public RemoteLibraryBrowsingFeed() {
	}

	public RemoteLibraryBrowsingFeed(ContactInfo owner, ContactInfo visited) {
		super(owner, FeedType.BROWSE_REMOTE_LIBRARY);
		this.visited = visited;
	}

	@Override
	public String toString() {
		return "RemoteLibraryBrowsingFeed [visited=" + visited + "]";
	}

	public ContactInfo getVisited() {
		return visited;
	}

	public void setVisited(ContactInfo visited) {
		this.visited = visited;
	}

	@Override
	public List<ContactInfo> contacts() {
		List<ContactInfo> contacts = new ArrayList<ContactInfo>();
		contacts.add(getOwner());
		contacts.add(visited);
		return contacts;
	}
}
