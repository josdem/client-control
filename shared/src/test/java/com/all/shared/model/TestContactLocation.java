package com.all.shared.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;


public class TestContactLocation {

	private ContactLocation locationA = new ContactLocation();
	private ContactLocation locationB = new ContactLocation();
	
	@Before
	public void setup(){
		locationA.setContactId("somebody@all.com");
		Set<String> privateAddressesA = new HashSet<String>();
		privateAddressesA.add("192.168.1.123");
		locationA.setPrivateAddresses(privateAddressesA);
		Set<String> publicAddressesA = new HashSet<String>();
		publicAddressesA.add("78.56.123.56");
		locationA.setPublicAddresses(publicAddressesA);
		
		locationB.setContactId("somebody@all.com");
		Set<String> privateAddressesB = new HashSet<String>();
		privateAddressesB.add("192.168.1.123");
		locationB.setPrivateAddresses(privateAddressesB);
		Set<String> publicAddressesB = new HashSet<String>();
		publicAddressesB.add("78.56.123.56");
		locationB.setPublicAddresses(publicAddressesB);
	}
	
	@Test
	public void shouldAddOrRemoveContactLocations() throws Exception {
		locationA.add(locationB);
		
		assertTrue(locationA.getPrivateAddresses().containsAll(locationB.getPrivateAddresses()));
		assertTrue(locationA.getPublicAddresses().containsAll(locationB.getPublicAddresses()));
		
		locationA.remove(locationB);
		
		assertFalse(locationA.getPrivateAddresses().removeAll(locationB.getPrivateAddresses()));
		assertFalse(locationA.getPublicAddresses().removeAll(locationB.getPublicAddresses()));
	}
	
	@Test
	public void shouldKnowIfLocationIsEmpty() throws Exception {
		assertFalse(locationA.isEmpty());
		locationA.remove(locationA);
		assertTrue(locationA.isEmpty());
	}
	
}
