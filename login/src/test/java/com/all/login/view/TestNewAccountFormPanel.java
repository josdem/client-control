package com.all.login.view;

import static org.junit.Assert.assertNotNull;

import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.all.core.common.LookAndFeelAppListener;
import com.all.login.controller.LoginController;

public class TestNewAccountFormPanel {

	private NewAccountFormPanel newAccountFormPanel;

	@Mock
	private Validator validatorMock;
	@Mock
	private LoginController controllerMock;

	@Before
	public void setup() {
		new LookAndFeelAppListener().initialize();
		MockitoAnnotations.initMocks(this);
		newAccountFormPanel = new NewAccountFormPanel(validatorMock, controllerMock);
	}

	@Test
	public void shouldCreateNewAccountFormPanel() throws Exception {
		assertNotNull(newAccountFormPanel);
	}

}
