package com.all.client.model;

import java.awt.Image;
import java.net.URL;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.all.twitter.TwitterStatus;
import com.all.twitter.UserProfile;

public class DecoratedTwitterStatus implements TwitterStatus {

	private static final Log LOG = LogFactory.getLog(DecoratedTwitterStatus.class);
	private final TwitterStatus twitterStatus;
	private final String loggedInScreenName;

	public DecoratedTwitterStatus(TwitterStatus twitterStatus, String loggedInScreenName) {
		this.twitterStatus = twitterStatus;
		this.loggedInScreenName = loggedInScreenName;
	}

	@Override
	public Date getDateCreatedAt() {
		return twitterStatus.getDateCreatedAt();
	}

	@Override
	public long getId() {
		return twitterStatus.getId();
	}

	@Override
	public String getInReplyToScreenName() {
		return twitterStatus.getInReplyToScreenName();
	}

	@Override
	public URL getProfileImageUrl() {
		return twitterStatus.getProfileImageUrl();
	}

	@Override
	public URL getRetweeterProfileImageUrl() {
		return twitterStatus.getRetweeterProfileImageUrl();
	}

	@Override
	public String getRetweeterScreenName() {
		return twitterStatus.getRetweeterScreenName();
	}

	@Override
	public String getScreenName() {
		return twitterStatus.getScreenName();
	}

	@Override
	public long getSenderId() {
		return twitterStatus.getSenderId();
	}

	@Override
	public String getSource() {
		return twitterStatus.getSource();
	}

	@Override
	public String getText() {
		return twitterStatus.getText();
	}

	@Override
	public TwitterStatusType getType() {
		return twitterStatus.getType();
	}

	@Override
	public UserProfile getUserProfile() {
		return twitterStatus.getUserProfile();
	}

	@Override
	public boolean isReplied() {
		return twitterStatus.isReplied();
	}

	@Override
	public boolean isDirect() {
		return twitterStatus.isDirect();
	}

	@Override
	public boolean isRetweeted() {
		return twitterStatus.isRetweeted();
	}

	public Image getProfileImage() {
		return getImage(getProfileImageUrl());
	}

	public Image getRetweeterProfileImage() {
		return getImage(getRetweeterProfileImageUrl());
	}

	public String getLoggedInScreenName() {
		return loggedInScreenName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((twitterStatus == null) ? 0 : twitterStatus.hashCode());
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
		DecoratedTwitterStatus other = (DecoratedTwitterStatus) obj;
		if (twitterStatus == null) {
			if (other.twitterStatus != null) {
				return false;
			}
		} else if (!twitterStatus.equals(other.twitterStatus)) {
			return false;
		}
		return true;
	}

	private static Image getImage(URL url) {
		try {
			return ImageIO.read(url);
		} catch (Exception e) {
			// set to trace bug #1078 DEFECT - En el perfil de twitter, No se
			// visualiza el avatar de algunos usuarios de
			// twitter (ver imagen)
			LOG.error("Error retrieving twitter avatar " + url, e);
		}
		return null;
	}

}
