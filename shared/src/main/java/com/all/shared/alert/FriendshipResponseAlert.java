package com.all.shared.alert;

import java.util.Date;

import com.all.shared.model.ContactRequest;

public class FriendshipResponseAlert extends AbstractAlert {

	public static final String TYPE = "FRIENDSHIP_RESPONSE";
	private static final long serialVersionUID = 1247959122323631713L;
	private ContactRequest request;

	@Deprecated
	public FriendshipResponseAlert(){}
	
	public FriendshipResponseAlert(ContactRequest request) {
		super(request.getRequested(), request.getRequester(), new Date().getTime(), TYPE);
		if(!request.isAccepted()){
			throw new IllegalArgumentException("A friendship response alert only applies for accepted requests.");
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
