package com.all.login.view;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class TestDuplicateEmailPanel {

	private DuplicateEmailPanel duplicateEmailPanel;

	@Test
	public void shouldCreateDuplicateEmailPanel() throws Exception {
		duplicateEmailPanel = new DuplicateEmailPanel();
		assertNotNull(duplicateEmailPanel);
	}
}
