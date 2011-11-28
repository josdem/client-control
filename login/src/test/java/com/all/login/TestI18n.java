package com.all.login;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Test;

public class TestI18n {
	@Test
	public void testResourceBundle() throws Exception {
		ResourceBundle.getBundle("com.all.login.i18n.Messages", Locale.US);
	}
}
