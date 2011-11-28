package com.all.shared.messages;

public enum FriendshipRequestStatus {
	OK("sendInvitation.success.mainMessage", "sendInvitation.success.description", false), ALREADY_REQUESTED(
			"sendInvitation.alreadyRequested.mainMessage", "sendInvitation.alreadyRequested.description", true),
	ALREADY_FRIENDS("sendInvitation.alreadyFriends.mainMessage", "sendInvitation.alreadyFriends.description", true),
	RECIPROCAL_INVITATION("sendInvitation.reciprocalRequest.mainMessage", "sendInvitation.reciprocalRequest.description",
			false), REACTIVATED_FRIENDSHIP("sendInvitation.reciprocalRequest.mainMessage",
			"sendInvitation.reciprocalRequest.description", false),
	// TEMPORAL AUTO FRIEND remove after no auto friend is allowed
	AUTO("sendInvitation.reciprocalRequest.mainMessage", "sendInvitation.reciprocalRequest.description", false);

	private final String mainMessage;
	private final String description;
	private final boolean error;

	private FriendshipRequestStatus(String mainMessage, String description, boolean error) {
		this.mainMessage = mainMessage;
		this.description = description;
		this.error = error;
	}

	public String getMainMessage() {
		return mainMessage;
	}

	public String getDescription() {
		return description;
	}

	public boolean isError() {
		return error;
	}
}
