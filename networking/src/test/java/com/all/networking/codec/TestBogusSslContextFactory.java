package com.all.networking.codec;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestBogusSslContextFactory {
	@Test
	public void shouldCreateASslContext() throws Exception {
		assertNotNull(BogusSslContextFactory.getInstance(true));
		assertNotNull(BogusSslContextFactory.getInstance(false));
	}
}
