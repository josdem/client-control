package com.all.shared.newsfeed;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import com.all.shared.model.ContactInfo;


public class TestRemoteLibraryBrowsingFeed {
	private RemoteLibraryBrowsingFeed feed;
	private ContactInfo owner;
	private ContactInfo requested;
	
	@Before
	public void setup() throws Exception {
		owner = mock(ContactInfo.class);
		requested = mock(ContactInfo.class);
		feed = new RemoteLibraryBrowsingFeed(owner, requested);
	}
	
	@Test
	public void shouldGetOwner() throws Exception {
		assertEquals(owner, feed.getOwner());
	}
	
	@Test
	public void shouldGetRequested() throws Exception {
		assertEquals(requested, feed.getVisited());
	}
	
	@Test
	public void shouldGetFriendshipType() throws Exception {
		assertEquals(FeedType.BROWSE_REMOTE_LIBRARY, feed.getType());
	}
}
