package com.all.shared.messages;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.all.shared.model.ContactInfo;

public class EmailContact implements Serializable {
	private static final long serialVersionUID = 1L;

	private String email;

	private Map<String, String> unregisteredContacts;

	private List<ContactInfo> registeredContacts;

	public EmailContact(String email, Map<String, String> unregisteredContacts, List<ContactInfo> registeredContacts) {
		this.email = email;
		this.unregisteredContacts = unregisteredContacts;
		this.registeredContacts = registeredContacts;
	}

	public List<ContactInfo> getRegisteredContacts() {
		return registeredContacts;
	}

	public Map<String, String> getUnregisteredContacts() {
		return unregisteredContacts;
	}

	public String getEmail() {
		return email;
	}

}
