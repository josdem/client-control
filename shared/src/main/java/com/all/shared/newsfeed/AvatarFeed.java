package com.all.shared.newsfeed;

import com.all.shared.model.ContactInfo;

/**
 * @understands A class who knows how contain an avatar feed
 */

public class AvatarFeed extends AbstractFeed {

	private byte[] avatar;

	@Deprecated
	public AvatarFeed()	{}

	public AvatarFeed(ContactInfo owner, byte[] avatar) {
		super(owner, FeedType.AVATAR_UPDATE);
		this.avatar = avatar;
	}

	public byte[] getAvatar() {
		return avatar;
	}

	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}
}