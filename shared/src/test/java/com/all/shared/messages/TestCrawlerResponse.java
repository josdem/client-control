package com.all.shared.messages;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.all.shared.model.ContactInfo;



public class TestCrawlerResponse {
	private CrawlerResponse crawlerResponse = new CrawlerResponse();
	
	private static final String CONTACT_MAIL = "contact@gmail1";
	private static final String CONTACT_NAME = "Contact Name";
	private EmailContact gmailContact;
	private EmailContact yahooContact;
	
	@Before
	public void initEmailContacts() {
		Map<String, String> gmailContacts = getUnregisteredContacts(CONTACT_MAIL, CONTACT_NAME);
		List<ContactInfo> registeredGmailContacts = getRegisteredUsers("User 1");
		gmailContact = new EmailContact("email@gmail", gmailContacts, registeredGmailContacts);
		yahooContact = new EmailContact("email@yahoo", getUnregisteredContacts("contact@yahoo", "Contact Yahoo"), 
				getRegisteredUsers("User2"));
	}

	@SuppressWarnings("deprecation")
	private List<ContactInfo> getRegisteredUsers(String name) {
		ContactInfo contact = new ContactInfo();
		contact.setName(name);
		List<ContactInfo> registeredGmailContacts = Arrays.asList(new ContactInfo[] {contact});
		return registeredGmailContacts;
	}
	
	@Test
	public void shouldGetUnregisteredUsers() throws Exception {
		crawlerResponse.addEmailContact(gmailContact);
		Map<String, String> unregisteredContacts = crawlerResponse.getUnregisteredContacts();
		assertEquals(1, unregisteredContacts.size());
		assertEquals(CONTACT_NAME, unregisteredContacts.get(CONTACT_MAIL));
		crawlerResponse.addEmailContact(yahooContact);
		unregisteredContacts = crawlerResponse.getUnregisteredContacts();
		assertEquals(2, unregisteredContacts.size());
	}

	private Map<String, String> getUnregisteredContacts(String email, String contact) {
		Map<String, String> gmailContacts = new HashMap<String, String>();
		gmailContacts.put(email, contact);
		return gmailContacts;
	}
	
	@Test
	public void shouldGetRegisteredContacts() throws Exception {
		addAllEmailContacts();
		Set<ContactInfo> registeredContacts = crawlerResponse.getRegisteredUsers();
		assertEquals(2, registeredContacts.size());
	}

	private void addAllEmailContacts() {
		crawlerResponse.addEmailContact(gmailContact);
		crawlerResponse.addEmailContact(yahooContact);
	}
	
}
