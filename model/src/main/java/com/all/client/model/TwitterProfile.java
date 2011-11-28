package com.all.client.model;

import java.awt.Image;
import java.net.URL;

import javax.imageio.ImageIO;

import com.all.twitter.UserProfile;

public class TwitterProfile implements UserProfile {

	private final UserProfile userProfile;
	private boolean loggedInUser;
	private final boolean following;

	public TwitterProfile(UserProfile userProfile, boolean loggedInUser, boolean following) {
		if (userProfile == null) {
			throw new IllegalArgumentException("UserProfile is null.");
		}
		this.userProfile = userProfile;
		this.following = following;
		this.loggedInUser = loggedInUser;
	}

	public Image getImageProfile() {
		return getImage(userProfile.getImageProfileUrl());
	}

	public boolean isLoggedInUser() {
		return loggedInUser;
	}

	@Override
	public String getDescription() {
		return userProfile.getDescription();
	}

	@Override
	public int getFollowersCount() {
		return userProfile.getFollowersCount();
	}

	@Override
	public int getFriendsCount() {
		return userProfile.getFriendsCount();
	}

	@Override
	public long getId() {
		return userProfile.getId();
	}

	@Override
	public URL getImageProfileUrl() {
		return userProfile.getImageProfileUrl();
	}

	@Override
	public String getLocation() {
		return userProfile.getLocation();
	}

	@Override
	public String getName() {
		return userProfile.getName();
	}

	@Override
	public String getScreenName() {
		return userProfile.getScreenName();
	}

	@Override
	public int getTweetsCount() {
		return userProfile.getTweetsCount();
	}

	@Override
	public String getURL() {
		return userProfile.getURL();
	}

	@Override
	public boolean isFollowing() {
		return following;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userProfile == null) ? 0 : userProfile.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TwitterProfile other = (TwitterProfile) obj;
		if (userProfile == null) {
			if (other.userProfile != null) {
				return false;
			}
		} else if (!userProfile.equals(other.userProfile)) {
			return false;
		}
		return true;
	}

	private static Image getImage(URL url) {
		try {
			return ImageIO.read(url);
		} catch (Exception e) {
		}
		return null;
	}

}
