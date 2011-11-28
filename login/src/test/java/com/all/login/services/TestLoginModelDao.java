package com.all.login.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.all.login.model.Login;
import com.all.login.model.LoginDatabase;
import com.all.shared.command.LoginCommand;
import com.all.shared.model.User;

public class TestLoginModelDao {
	@InjectMocks
	private LoginModelDao localModelDao = new LoginModelDao();
	@Mock
	private LoginDatabaseAccess db;
	private LoginDatabase loginDatabase = new LoginDatabase();
	@Mock
	private User user;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		when(db.getDB()).thenReturn(loginDatabase);
	}

	@Test
	public void shouldRegisterLoginAndLocalUser() throws Exception {
		String email = "user";
		when(user.getEmail()).thenReturn(email);
		String password = "password";
		LoginCommand loginCommand = new LoginCommand(email, password);
		User foundUser = localModelDao.getUser(loginCommand.getEmail());
		List<Login> logins = localModelDao.getLastLogins();
		assertNull(foundUser);
		assertNotNull(logins);
		assertTrue(logins.isEmpty());

		localModelDao.registerNewLogin(user, false);

	}

	@Test
	public void shouldRegisterLoginAndUpdateLocalUserPassword() throws Exception {
		String email = "user";
		when(user.getEmail()).thenReturn(email);
		String password = "password";
		when(user.getPassword()).thenReturn(password);

		localModelDao.registerNewLogin(user, true);

	}

}
