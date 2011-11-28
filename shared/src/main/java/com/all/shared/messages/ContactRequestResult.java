package com.all.shared.messages;

import java.io.Serializable;

import com.all.shared.model.ContactInfo;

public class ContactRequestResult implements Serializable {
	private static final long serialVersionUID = 1L;
	private final ContactInfo requested;
	private final FriendshipRequestStatus status;

	public ContactRequestResult(ContactInfo requested, FriendshipRequestStatus status) {
		this.requested = requested;
		this.status = status;
	}

	public ContactInfo getRequested() {
		return requested;
	}

	public FriendshipRequestStatus getStatus() {
		return status;
	}

}
