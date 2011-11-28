package com.all.client.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import com.all.shared.model.ContactInfo;
import com.all.shared.model.ContactStatus;
import com.all.shared.model.Gender;
import com.all.shared.model.PendingEmail;
import com.all.shared.model.User;

@SuppressWarnings("deprecation")
public class TestContactInfo {

	private User createUser() {
		User user = new User();
		user.setFirstName("Unit");
		user.setLastName("Last name");
		user.setEmail("test@all.com");
		user.setBirthday(new Date());
		user.setPassword("1234567890");
		user.setGender(Gender.MALE);
		user.setIdLocation("12345");
		user.setNickName("testino");

		return user;
	}

	@Test
	public void shouldCreateAContactInfoFromUser() throws Exception {
		User user = createUser();
		ContactInfo contactInfo = new ContactInfo(user);

		assertEquals(user.getFullName(), contactInfo.getName());
		assertEquals(user.getEmail(), contactInfo.getEmail());
		assertEquals(user.getQuote(), contactInfo.getMessage());
		assertEquals(user.getGender(), contactInfo.getGender());
	}

	@Test
	public void shouldCreateAContactInfoFromPendingEmail() throws Exception {
		PendingEmail email = new PendingEmail(1L, "user@all.com");
		email.setId(1L);
		ContactInfo contactInfo = new ContactInfo(email);

		assertNull(contactInfo.getFirstName());
		assertNull(contactInfo.getLastName());
		assertNotNull(contactInfo.getEmail());
		assertNotNull(contactInfo.getId());

	}

	@Test
	public void shouldGetOnlineStatus() throws Exception {
		ContactInfo contactInfo = new ContactInfo();
		assertFalse(contactInfo.isOnline());
		contactInfo.setStatus(ContactStatus.online);
		assertTrue(contactInfo.isOnline());
	}

	@Test
	public void shouldGetFirstAndLastName() throws Exception {
		String name = "John Doe";
		ContactInfo contactInfo = new ContactInfo();
		contactInfo.setName(name);
		assertEquals("John", contactInfo.getFirstName());
	}

	@Test
	public void shouldCompareByNickname() throws Exception {
		ContactInfo contactInfo = new ContactInfo(createUser());
		User user = new User();
		user.setNickName("josdem");
		ContactInfo anotherContact = new ContactInfo(user);
		User firstUser = new User();
		firstUser.setNickName("alexa");
		ContactInfo firstContact = new ContactInfo(firstUser);
		
		assertTrue(contactInfo.compareTo(anotherContact) > 0);
		assertTrue(anotherContact.compareTo(firstContact) > 0);
		assertTrue(contactInfo.compareTo(firstContact) > 0);
	}
	
	@Test
	public void shouldCompareByEmail() throws Exception {
		ContactInfo contactInfo = new ContactInfo(createUser());
		User user = new User();
		user.setEmail("josdem@all.com");
		ContactInfo anotherContact = new ContactInfo(user);
		User firstUser = new User();
		firstUser.setEmail("alexa@all.com");
		ContactInfo firstContact = new ContactInfo(firstUser);
		
		assertTrue(contactInfo.compareTo(anotherContact) > 0);
		assertTrue(anotherContact.compareTo(firstContact) > 0);
		assertTrue(contactInfo.compareTo(firstContact) > 0);
	}

	@Test
	public void shouldKnowIfEquals() throws Exception {
		String email = "user@server";
		ContactInfo contact1 = new ContactInfo();
		contact1.setEmail(email);
		ContactInfo contact2 = new ContactInfo();
		contact2.setEmail(email);
		assertEquals(contact1, contact2);
		assertEquals(contact1.hashCode(), contact2.hashCode());

		long id = 42L;
		contact1 = new ContactInfo();
		contact1.setId(id);
		contact2 = new ContactInfo();
		contact2.setId(id);
		assertEquals(contact1, contact2);
		assertEquals(contact1.hashCode(), contact2.hashCode());
		assertFalse(contact1.equals(new ContactInfo()));
	}
	
	@Test
	public void shoulgGetNameWhenNull() throws Exception {
		ContactInfo contact = new ContactInfo();
		contact.setNickName("nickname");
		
		assertEquals("nickname", contact.getName());
	}

	@Test
	public void shouldGetFirstName() throws Exception {
		ContactInfo contact = new ContactInfo();
		contact.setFirstName("first");
		
		assertEquals("first", contact.getFirstName());
	}
	
	@Test
	public void shouldGetFirstNameWhenNull() throws Exception {
		ContactInfo contact = new ContactInfo();
		contact.setNickName("nickname");
		
		assertEquals("nickname", contact.getFirstName());
	}
	
	@Test
	public void shouldGetInfoWhenNull() throws Exception {
		ContactInfo contact = new ContactInfo();
		
		assertEquals(null, contact.getFirstName());
		assertEquals(null, contact.getLastName());
		assertEquals(null, contact.getName());
		assertEquals(null, contact.getNickName());
	}
	
	@Test
	public void shouldGetLastName() throws Exception {
		ContactInfo contact = new ContactInfo();
		contact.setLastName("last");
		
		assertEquals("last", contact.getLastName());
	}
	
	@Test
	public void shouldGetLastNameWhenNull() throws Exception {
		ContactInfo contact = new ContactInfo();
		contact.setNickName("nickname");
		
		assertEquals("nickname", contact.getLastName());
	}
	
	@Test
	public void shouldGetNickname() throws Exception {
		ContactInfo contact = new ContactInfo();
		contact.setNickName("nickname");
		
		assertEquals("nickname", contact.getNickName());
	}
	
	@Test
	public void shouldGetNicknameWhenFirstAndLastNameAreNull() throws Exception {
		ContactInfo contact = new ContactInfo();
		
		assertEquals(null, contact.getNickName());
	}
	
	@Test
	public void shouldGetNicknameWhenFirstNameIsNull() throws Exception {
        ContactInfo contact = new ContactInfo();
        contact.setLastName("gonzalez");
		
		assertEquals("gonzalez", contact.getNickName());
	}
	
	@Test
	public void shouldGetNicknameWhenLastNameIsNull() throws Exception {
		    ContactInfo contact = new ContactInfo();
	        contact.setLastName("mariadelrosariodelperpetuosocorrodelsagradoninio");
			
			assertEquals("mariadelrosariodelperpetu", contact.getNickName());
	}
	
}
