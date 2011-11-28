package com.all.login.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.all.core.common.LookAndFeelAppListener;
import com.all.login.controller.LoginController;
import com.all.login.view.CenterLoginPanel.ForgotPasswordWorker;
import com.all.shared.messages.ForgotPasswordResult;

public class TestForgotPasswordPanel {

	private CenterLoginPanel forgotPasswordPanel;

	@Mock
	private LoginController loginControllerMock;

	@Mock
	private Validator validatorMock;

	@Before
	public void setup() {
		new LookAndFeelAppListener().initialize();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldCreateForgotPasswordPanel() throws Exception {
		forgotPasswordPanel = new CenterLoginPanel(loginControllerMock, validatorMock);
		assertNotNull(forgotPasswordPanel);
	}

	@Test
	public void shouldNotShowConnectionErrorPanel() throws Exception {
		when(loginControllerMock.isConnected()).thenReturn(true);

		forgotPasswordPanel = new CenterLoginPanel(loginControllerMock, validatorMock);

		ForgotPasswordWorker forgotPasswordWorker = forgotPasswordPanel.new ForgotPasswordWorker("test@all.com");
		ForgotPasswordResult result = forgotPasswordWorker.doInBackground();

		verify(loginControllerMock).forgotPassword(anyString());
		assertFalse(result == ForgotPasswordResult.CONNECTION_ERROR);
	}

	@Test
	public void shouldShowConnectionErrorPanel() throws Exception {
		when(loginControllerMock.isConnected()).thenReturn(false);
		forgotPasswordPanel = new CenterLoginPanel(loginControllerMock, validatorMock);

		ForgotPasswordWorker forgotPasswordWorker = forgotPasswordPanel.new ForgotPasswordWorker("test@all.com");
		ForgotPasswordResult result = forgotPasswordWorker.doInBackground();
		assertEquals(result, ForgotPasswordResult.CONNECTION_ERROR);
	}

}
