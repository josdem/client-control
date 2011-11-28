package com.all.login.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;



public class TestLogin {
	
	
	@Test
	public void shouldOverrideToString() throws Exception {
		String email = "email";
		assertEquals(email, new Login(email).toString());
	}
}
