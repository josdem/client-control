package com.all.login.view;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class TestConnectionErrorPanel {

	private ConnectionErrorPanel connectionErrorPanel;

	@Test
	public void shouldCreateConnectionErrorPanel() throws Exception {
		connectionErrorPanel = new ConnectionErrorPanel();
		assertNotNull(connectionErrorPanel);
	}
}
