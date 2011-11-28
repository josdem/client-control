package com.all.shared.model.constraints;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.regex.Pattern;

import org.junit.Ignore;
import org.junit.Test;

public class TestEmail {

	//TODO: it was ment to test more restrictive validation that standard on email
	//will be ignored until email validation issues (standard or more restricted) be addressed
	@Ignore
	@Test
	public void shouldValidateEmail() throws Exception {
		 assertFalse(Pattern.matches(Email.patternString, "hol}aa@all.com"));
		assertFalse(Pattern.matches(Email.patternString, "hol]a@all.com"));
		assertTrue(Pattern.matches(Email.patternString, "hola@all.com"));
		assertTrue(Pattern.matches(Email.patternString, "hola+como+estas@all.com"));
		assertTrue(Pattern.matches(Email.patternString, "hola.como@yahoo.com"));
		assertFalse(Pattern.matches(Email.patternString, "!#$%~&*+={}?'@all.com_"));
	}
}
