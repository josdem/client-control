package com.all.shared.newsfeed;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import com.all.shared.model.ContactInfo;


public class TestFriendshipFeed {
	private FriendshipFeed feed;
	private ContactInfo requester;
	private ContactInfo requested;

	@Before
	public void setup() throws Exception {
		requester = mock(ContactInfo.class);
		requested = mock(ContactInfo.class);
		feed = new FriendshipFeed(requester, requested);
	}
	
	@Test
	public void shouldGetOwner() throws Exception {
		assertEquals(requester, feed.getOwner());
	}
	
	@Test
	public void shouldGetRequested() throws Exception {
		assertEquals(requested, feed.getRequested());
	}
	
	@Test
	public void shouldGetFriendshipType() throws Exception {
		assertEquals(FeedType.FRIENDSHIP, feed.getType());
	}
	
}
