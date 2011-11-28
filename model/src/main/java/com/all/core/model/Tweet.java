package com.all.core.model;

import static com.all.shared.stats.usage.UserActions.SocialNetworks.TWITTER_RECOMMENDATION;
import static com.all.shared.stats.usage.UserActions.SocialNetworks.TWITTER_STATUS;
import static com.all.shared.stats.usage.UserActions.SocialNetworks.TWITTER_TRACK;

import java.util.Arrays;
import java.util.List;

public class Tweet {

	private static final List<Integer> VALID_TYPES = Arrays.asList(TWITTER_STATUS, TWITTER_TRACK, TWITTER_RECOMMENDATION);

	private final String status;

	private final Integer actionType;

	public Tweet(String status, Integer actionType) {
		this.status = status;
		if (VALID_TYPES.contains(actionType)) {
			this.actionType = actionType;
		} else {
			throw new IllegalArgumentException("Invalid type for twitter status.");
		}
	}

	public String getStatus() {
		return status;
	}

	public Integer getActionType() {
		return actionType;
	}

}
