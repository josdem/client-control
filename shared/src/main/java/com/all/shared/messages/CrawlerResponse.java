package com.all.shared.messages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.all.shared.model.ContactInfo;

public class CrawlerResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<EmailContact> emailContacts = new ArrayList<EmailContact>();
	
	public CrawlerResponse(){}

	public CrawlerResponse(List<EmailContact> emailContacts) {
		this.emailContacts = new ArrayList<EmailContact>(emailContacts);
	}
	
	public Map<String,String> getUnregisteredContacts() {
		Map<String,String> unregisteredContacts = new HashMap<String, String>();
		for (EmailContact emailContact : emailContacts) {
			unregisteredContacts.putAll(emailContact.getUnregisteredContacts());
		}
		return unregisteredContacts;
	}

	public void addEmailContact(EmailContact emailContact) {
		emailContacts.add(emailContact);
	}

	public Set<ContactInfo> getRegisteredUsers() {
		Set<ContactInfo> contacts = new HashSet<ContactInfo>();
		for (EmailContact emailContact : emailContacts) {
			contacts.addAll(emailContact.getRegisteredContacts());
		}
		return contacts;
	}

	public List<EmailContact> getEmailContacts() {
		return new ArrayList<EmailContact>(emailContacts);
	}
	
}
