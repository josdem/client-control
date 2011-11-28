package com.all.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.all.shared.model.ContactInfo;
import com.all.shared.model.ContactStatus;

public class ContactCollection implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<ContactInfo> contacts = new ArrayList<ContactInfo>();
	private List<ContactInfo> pendingContacts = new ArrayList<ContactInfo>();

	public ContactCollection() {

	}

	public ContactCollection(ContactInfo... contacts) {
		// creating an ArrayList knows exactly which List implementation use,
		// avoiding UnsupportOperationException
			this(new ArrayList<ContactInfo>(Arrays.asList(contacts)));
	}

	public void add(ContactInfo contactInfo) {
		contacts.add(contactInfo);
	}

	public ContactCollection(List<ContactInfo> contacts) {
		super();
		for (ContactInfo contactInfo : contacts) {
			if (contactInfo.getStatus() == ContactStatus.pending) {
				pendingContacts.add(contactInfo);
			} else {
				this.contacts.add(contactInfo);
			}
		}
	}

	public List<ContactInfo> getContacts() {
		return contacts;
	}

	public List<ContactInfo> getPendingContacts() {
		return pendingContacts;
	}

	public void cleanUp() {
		if (!contacts.isEmpty() && !pendingContacts.isEmpty()) {
			pendingContacts.clear();
		}
	}
	
	public boolean contains(ContactInfo contactInfo) {
		return contacts.contains(contactInfo);
	}
}
