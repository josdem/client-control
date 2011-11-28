package com.all.app;

import org.junit.Test;

public class TestEmptyModule {
	@Test
	public void emptyModuleDoesNothing() throws Exception {
		EmptyModule module = new EmptyModule();
		module.activate();
		module.execute(null);
		module.destroy();
	}
}
