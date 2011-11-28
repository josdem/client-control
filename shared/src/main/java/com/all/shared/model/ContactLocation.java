package com.all.shared.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ContactLocation implements Serializable {

	private static final long serialVersionUID = 1L;

	private Set<String> privateAddresses = new HashSet<String>();

	private Set<String> publicAddresses = new HashSet<String>();

	private String contactId;

	public ContactLocation() {
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public Set<String> getPrivateAddresses() {
		return new HashSet<String>(privateAddresses);
	}

	public Set<String> getPublicAddresses() {
		return new HashSet<String>(publicAddresses);
	}

	public void setPublicAddresses(Set<String> publicAddresses) {
		this.publicAddresses = publicAddresses;
	}

	public void setPrivateAddresses(Set<String> privateAddresses) {
		this.privateAddresses = privateAddresses;
	}

	public void add(ContactLocation contactLocation) {
		publicAddresses.addAll(contactLocation.publicAddresses);
		privateAddresses.addAll(contactLocation.privateAddresses);
	}

	public void remove(ContactLocation contactLocation) {
		publicAddresses.removeAll(contactLocation.publicAddresses);
		privateAddresses.removeAll(contactLocation.privateAddresses);
	}

	public void addPublicAddress(String publicIp) {
		publicAddresses.add(publicIp);
	}

	public boolean isEmpty() {
		return publicAddresses.isEmpty() && privateAddresses.isEmpty();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		return sb.append(contactId).append(" : PUBLIC ").append(publicAddresses.toString()).append("\tPRIVATE ").append(
				privateAddresses.toString()).toString();
	}
}
