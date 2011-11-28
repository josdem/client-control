package com.all.login.view;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.all.i18n.Messages;

public class TestDisclaimerPanel {

	private DisclaimerPanel disclaimerPanel;

	@Mock
	private Messages messagesMock;

	@Before
	public void mockMessages() {
		MockitoAnnotations.initMocks(this);
		when(messagesMock.getMessage("disclaimer.file")).thenReturn("/");
	}

	@Test
	public void shouldCreateDisclaimerPanel() throws Exception {
		disclaimerPanel = new DisclaimerPanel();
		assertNotNull(disclaimerPanel);
	}

}
