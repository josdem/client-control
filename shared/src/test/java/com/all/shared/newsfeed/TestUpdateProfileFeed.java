package com.all.shared.newsfeed;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import com.all.shared.model.ContactInfo;


public class TestUpdateProfileFeed {
	private ContactInfo owner;
	private UpdateProfileFeed feed;

	@Before
	public void setup() throws Exception {
		owner = mock(ContactInfo.class);
		feed = new UpdateProfileFeed(owner);
	}
	
	@Test
	public void shouldGetOwner() throws Exception {
		assertEquals(owner, feed.getOwner());
	}
	
	@Test
	public void shouldGetType() throws Exception {
		assertEquals(FeedType.UPDATE_PROFILE, feed.getType());
	}
}
