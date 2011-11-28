package com.all.shared.alert;

import com.all.shared.model.ContactRequest;

public class FriendshipRequestAlert extends AbstractAlert {

	public static final String TYPE = "FRIENDSHIP_REQUEST";

	private static final long serialVersionUID = 1L;

	private ContactRequest request;;
	
	@Deprecated
	public FriendshipRequestAlert(){}
	
	public FriendshipRequestAlert(ContactRequest request) {
		super(request.getRequester(), request.getRequested(), request.getDate().getTime(), TYPE);
		if(request.getId() == null) {
			throw new IllegalArgumentException("Cannot construct an alert with a contact request that has no Id.");
		}
		this.request = request;
	}
	
	public ContactRequest getRequest() {
		return request;
	}
	
	@Deprecated
	public void setRequest(ContactRequest request) {
		this.request = request;
	}
}
