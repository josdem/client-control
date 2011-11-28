package com.all.login.security;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class TestPasswordValidator {
	PasswordValidator validator = new PasswordValidator();
	
	@Test
	public void shouldRecognizeWeakPassword() throws Exception {
		assertEquals(PasswordStrength.WEAK, validator.validate("abcdefgh"));
		
		assertEquals(PasswordStrength.WEAK, validator.validate("FKGFHJG"));
		
		assertEquals(PasswordStrength.WEAK, validator.validate("853765376"));
	}
	
	@Test
	public void shouldRecognizeMediumPassword() throws Exception {
		assertEquals(PasswordStrength.MEDIUM, validator.validate("jdgjhdgjhdg23456789"));
	}
	

	@Test
	public void shouldRecognizeStrongPassword() throws Exception {
		assertEquals(PasswordStrength.STRONG, validator.validate("jdGGjhd234"));
		assertEquals(PasswordStrength.STRONG, validator.validate("jdGGjhd.234"));
	}
}
