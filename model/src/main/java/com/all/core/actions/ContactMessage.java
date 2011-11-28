package com.all.core.actions;

import com.all.shared.model.ContactInfo;

public class ContactMessage {
	public ContactInfo contact;
	public String message;

	public ContactMessage() {
	}
	
	public ContactMessage(ContactInfo contact, String message) {
		this.contact = contact;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	public ContactInfo getContact() {
		return contact;
	}

}