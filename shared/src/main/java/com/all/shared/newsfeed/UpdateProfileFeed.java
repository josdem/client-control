package com.all.shared.newsfeed;

import com.all.shared.model.ContactInfo;

public class UpdateProfileFeed extends AbstractFeed {

	@Deprecated
	public UpdateProfileFeed() {
	}

	public UpdateProfileFeed(ContactInfo owner) {
		super(owner, FeedType.UPDATE_PROFILE);
	}
	
}
