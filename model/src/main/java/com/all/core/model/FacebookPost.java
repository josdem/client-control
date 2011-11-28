package com.all.core.model;

import com.all.shared.model.ContactInfo;

public class FacebookPost {

	private final ContactInfo contactInfo;
	private final String post;

	public FacebookPost(ContactInfo contactInfo, String post) {
		this.contactInfo = contactInfo;
		this.post = post;
	}

	public ContactInfo getContactInfo() {
		return contactInfo;
	}
	
	public String getPost() {
		return post;
	}

}
