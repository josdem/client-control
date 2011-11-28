package com.all.shared.newsfeed;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.all.shared.model.ContactInfo;

public class TestQuoteFeed {

	private ContactInfo owner;
	private QuoteFeed feed;
	private String quote = "Quote example";

	@Before
	public void setup() throws Exception {
		owner = mock(ContactInfo.class);
		feed = new QuoteFeed(owner, quote);
	}
	
	@Test
	public void shouldGetQuote() throws Exception {
		feed.getOwner().getQuote();
		verify(owner).getQuote();
	}
	
	@Test
	public void shouldGetNickname() throws Exception {
		feed.getOwner().getNickName();
		verify(owner).getNickName();
	}
	
	@Test
	public void shouldGetType() throws Exception {
		assertEquals(FeedType.QUOTE, feed.getType());
	}
}