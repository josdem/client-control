package com.all.login.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.all.appControl.control.ControlEngine;
import com.all.core.common.bean.RegisterUserCommand;
import com.all.core.common.model.ApplicationModel;
import com.all.core.common.util.PasswordDigest;
import com.all.login.services.LoginMessageService;
import com.all.login.services.LoginModelDao;
import com.all.login.view.TopPanelLogin;
import com.all.shared.command.LoginCommand;
import com.all.shared.login.LoginError;
import com.all.shared.messages.LoginResponse;
import com.all.shared.model.Avatar;
import com.all.shared.model.User;

public class TestLoginController {

	@InjectMocks
	private LoginController controller = new LoginController();
	@Mock
	private ControlEngine controlEngine;
	@Mock
	private LoginMessageService loginMessageService;
	@Mock
	private LoginModelDao loginDao;

	@Mock
	private Validator validator;
	private JTextField emailField = new JTextField();
	private JTextField passwordField = new JPasswordField();

	private LoginCommand credentials;
	private String cryptPassword;

	@Mock
	private User user;

	@Before
	public void initialize() {
		initMocks(this);
		credentials = new LoginCommand("user@all.com", "password");
		cryptPassword = PasswordDigest.md5(credentials.getPassword().getBytes());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void shouldValidateAllFields() throws Exception {
		when(validator.validate(credentials)).thenReturn(Collections.EMPTY_SET);

		assertTrue(controller.isLoginValid(credentials));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void shouldValidateFieldWithoutError() throws Exception {
		when(validator.validateProperty(eq(credentials), anyString())).thenReturn(Collections.EMPTY_SET);

		assertNull(controller.validateField(credentials, emailField));
		verify(validator).validateProperty(credentials, "email");

		assertNull(controller.validateField(credentials, passwordField));
		verify(validator).validateProperty(credentials, "password");
	}

	@Test
	@SuppressWarnings({ "unchecked" })
	public void shouldValidateFieldWithErrors() throws Exception {
		String errorMessage = "error";
		ConstraintViolation constraintViolationMock = mock(ConstraintViolation.class);
		when(constraintViolationMock.getMessage()).thenReturn(errorMessage);
		Set<ConstraintViolation<LoginCommand>> violations = new HashSet<ConstraintViolation<LoginCommand>>();
		violations.add(constraintViolationMock);

		when(validator.validateProperty(eq(credentials), anyString())).thenReturn(violations);
		assertEquals(errorMessage, controller.validateField(credentials, emailField));
		verify(validator).validateProperty(credentials, "email");

		assertEquals(errorMessage, controller.validateField(credentials, passwordField));
		verify(validator).validateProperty(credentials, "password");
	}

	@Test
	public void shouldLogin() throws Exception {
		User user = mock(User.class);
		LoginResponse response = new LoginResponse(user, new Avatar(), "sessionId");
		when(controlEngine.get(ApplicationModel.HAS_INTERNET_CONNECTION)).thenReturn(true);
		when(loginMessageService.loginRequest(credentials)).thenReturn(response);

		assertNull(controller.login(credentials, false));

		verify(loginMessageService).loginRequest(credentials);
		assertEquals(cryptPassword, credentials.getEncryptedPwd());
	}

	@Test
	public void shouldNotLogin() throws Exception {
		LoginResponse response = new LoginResponse(LoginError.INVALID_CREDENTIALS);
		when(controlEngine.get(ApplicationModel.HAS_INTERNET_CONNECTION)).thenReturn(true);
		when(loginMessageService.loginRequest(credentials)).thenReturn(response);

		assertEquals(LoginError.INVALID_CREDENTIALS, controller.login(credentials, false));

		verify(loginMessageService).loginRequest(credentials);
	}

	@Test
	public void shouldRequestForgottenPassword() throws Exception {
		String mail = "some@all.com";
		controller.forgotPassword(mail);

		verify(loginMessageService).forgotPasswordRequest(mail);
	}

	@Test
	public void shouldLoginInOfflineMode() throws Exception {
		when(controlEngine.get(ApplicationModel.HAS_INTERNET_CONNECTION)).thenReturn(false);

		when(user.getEmail()).thenReturn(credentials.getEmail());
		when(user.getPassword()).thenReturn(cryptPassword);

		when(loginDao.getUser(credentials.getEmail())).thenReturn(user);
		boolean rememberPassword = false;

		controller.login(credentials, rememberPassword);

		verify(loginDao).registerNewLogin(user, rememberPassword);
	}

	@Test
	public void shouldReturnInvalidCredentialsInOfflineLogin() throws Exception {
		when(controlEngine.get(ApplicationModel.HAS_INTERNET_CONNECTION)).thenReturn(false);

		when(user.getEmail()).thenReturn(credentials.getEmail());
		when(user.getPassword()).thenReturn("otherPassword");

		when(loginDao.getUser(credentials.getEmail())).thenReturn(user);
		boolean rememberPassword = false;

		assertEquals(LoginError.INVALID_CREDENTIALS, controller.login(credentials, rememberPassword));
		verify(loginDao, never()).registerNewLogin(user, rememberPassword);
	}

	@Test
	public void shouldReturnConnectionErrorIfAttemptToLoginNewUserInOfflineMode() throws Exception {
		when(controlEngine.get(ApplicationModel.HAS_INTERNET_CONNECTION)).thenReturn(false);
		when(loginDao.getUser(credentials.getEmail())).thenReturn(null);
		boolean rememberPassword = false;

		assertEquals(LoginError.CONNECTION_ERROR, controller.login(credentials, rememberPassword));
		verify(loginDao, never()).registerNewLogin(user, rememberPassword);
	}

	@Test
	public void shouldLoginInOfflineModeWithRememberPasswordEnabled() throws Exception {
		credentials.setPassword(TopPanelLogin.DEFAULT_PWD);
		when(controlEngine.get(ApplicationModel.HAS_INTERNET_CONNECTION)).thenReturn(false);

		when(user.getEmail()).thenReturn(credentials.getEmail());
		when(user.getPassword()).thenReturn(cryptPassword);

		when(loginDao.getUser(credentials.getEmail())).thenReturn(user);
		boolean rememberPassword = true;

		controller.login(credentials, rememberPassword);

		verify(loginDao).registerNewLogin(user, rememberPassword);
	}

	@Test
	public void shouldFindAllCities() throws Exception {
		controller.findAllCities();

		verify(loginDao).findAllCities();
	}

	@Test
	public void shouldGetLastLogins() throws Exception {
		controller.getLastLogins();

		verify(loginDao).getLastLogins();
	}

	@Test
	public void shouldSignUp() throws Exception {
		RegisterUserCommand user = mock(RegisterUserCommand.class);
		when(user.getPassword()).thenReturn(credentials.getPassword());

		controller.signUp(user, 1);

		verify(user).setEncryptedPwd(cryptPassword);
		verify(loginMessageService).signupRequest(user);
	}
}
