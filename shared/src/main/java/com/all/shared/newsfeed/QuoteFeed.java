package com.all.shared.newsfeed;

import com.all.shared.model.ContactInfo;

/**
 * @understands A class who knows how contain an quote feed
 */
public class QuoteFeed extends AbstractFeed {

	private String quote;

	@Deprecated
	public QuoteFeed()	{
	}

	public QuoteFeed(ContactInfo owner, String quote)	{
		super(owner, FeedType.QUOTE);
		this.quote = quote;
	}

	public String getQuote() {
		return quote;
	}

	public void setQuote(String quote) {
		this.quote = quote;
	}
}