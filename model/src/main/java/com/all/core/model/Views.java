package com.all.core.model;

public enum Views {
	LOCAL_MUSIC, TRASH, DOWNLOAD, PROFILE, SEARCH, BROWSE_MEMBERS, STAGE, CRAWLER, HUNDRED, HOME, SEND_MUSIC, FIND_FRIENDS;

	private Views() {
	}

	public String getCardName() {
		return name();
	}
}