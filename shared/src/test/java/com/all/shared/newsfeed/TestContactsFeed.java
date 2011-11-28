package com.all.shared.newsfeed;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import com.all.shared.model.ContactInfo;


public class TestContactsFeed {
	private ContactInfo owner;
	private ContactsFeed feed;
	private static final int TOTAL_CONTACTS = 3;

	@Before
	public void setup() throws Exception {
		owner = mock(ContactInfo.class);
		feed = new ContactsFeed(owner, TOTAL_CONTACTS);
	}
	
	@Test
	public void shouldGetType() throws Exception {
		assertEquals(FeedType.CONTACTS_FEED, feed.getType());
	}
	
	@Test
	public void shouldGetContactInfo() throws Exception {
		assertEquals(owner, feed.getOwner());
	}
}
