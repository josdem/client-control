package com.all.login.view.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

import javax.swing.JPasswordField;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.all.i18n.Messages;

public class TestPasswordFieldValidator {
	@InjectMocks
	PasswordFieldValidator fieldValidator = new PasswordFieldValidator();
	@Mock
	JPasswordField confirmPasswordTextField;
	@Mock
	Messages messages;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldVerifyIsMinimumSizeInPassword() throws Exception {
		String confirmed = "password";
		assertTrue(fieldValidator.isPasswordSizeAllowed(confirmed));
	}

	@Test
	public void shouldVerifyIsNotMinimumSizeInPassword() throws Exception {
		String confirmed = "passwd";
		assertFalse(fieldValidator.isPasswordSizeAllowed(confirmed));
	}

	@Test
	// 4179
	public void shouldVerifyIsMaximumSizeInPassword() throws Exception {
		String confirmed = "qwertyuiopasdfghjklnzxcvb";
		assertTrue(fieldValidator.isMaximumSizeAllowed(confirmed));
	}

	@Test
	// 4179
	public void shouldVerifyBigSizeInPassword() throws Exception {
		String confirmed = "qwertyuiopasdfghjklnzxcvba";
		assertFalse(fieldValidator.isMaximumSizeAllowed(confirmed));
	}

	@Test
	public void shouldRenamePasswordField() throws Exception {
		JPasswordField confirmPasswordTextField2 = confirmPasswordTextField;
		String fieldName = "textFieldConfirmPassword";
		boolean validSize = true;
		fieldValidator.passwordRenameValidate(confirmPasswordTextField2, fieldName, validSize, messages);
		verify(confirmPasswordTextField).setName(fieldName);
	}

	@Test
	public void shouldNotRenamePasswordField() throws Exception {
		JPasswordField confirmPasswordTextField2 = confirmPasswordTextField;
		String fieldName = "textFieldConfirmPassword";
		boolean validSize = false;
		fieldValidator.passwordRenameValidate(confirmPasswordTextField2, fieldName, validSize, messages);
		verify(confirmPasswordTextField).setName("invalidtextFieldConfirmPassword");
	}

	@Test
	public void shouldVerifyNotEqualsPasswords() throws Exception {
		String original = "password";
		String confirmed = "Password";
		assertFalse(fieldValidator.isEquals(original, confirmed));

	}

	@Test
	public void shouldVerifyPasswordsAreEquals() throws Exception {
		String original = "password";
		String confirmed = "password";
		assertTrue(fieldValidator.isEquals(original, confirmed));
	}

	@Test
	public void shouldVerifyNotMinimumSize() throws Exception {
		String original = "pass";
		String confirmed = "pass";
		assertFalse(fieldValidator.isPasswordSizeAllowed(original, confirmed));
	}

	@Test
	public void shouldVerifyIsAMinimumSize() throws Exception {
		String original = "password";
		String confirmed = "password";
		assertTrue(fieldValidator.isPasswordSizeAllowed(original, confirmed));
	}
}
