package com.all.shared.newsfeed;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.all.shared.model.ContactInfo;


public class TestAvatarFeed {

	private ContactInfo owner;
	private AvatarFeed feed;
	private byte[] avatar = new byte[48];

	@Before
	public void setup() throws Exception {
		owner = mock(ContactInfo.class);
		feed = new AvatarFeed(owner, avatar);
	}
	
	@Test
	public void shouldGetAvatar() throws Exception {
		feed.getOwner().getAvatar();
		verify(owner).getAvatar();
	}
	
	@Test
	public void shouldGetNickname() throws Exception {
		feed.getOwner().getNickName();
		verify(owner).getNickName();
	}
	
	@Test
	public void shouldGetType() throws Exception {
		assertEquals(FeedType.AVATAR_UPDATE, feed.getType());
	}
}
