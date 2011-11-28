package com.all.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class TestExceptions {
	@Test
	public void shouldTestBeanAlreadyRegisteredException() throws Exception {
		assertNotNull(new BeanAlreadyRegisteredException());
	}

	@Test
	public void shouldTestMoreThanOneDefinitionException() throws Exception {
		assertNotNull(new MoreThanOneDefinitionException());
	}

	@Test
	public void shouldTestApplicationAbortException() throws Exception {
		assertNotNull(new ApplicationAbortException());
		Exception cause = new Exception();
		assertEquals(cause, new ApplicationAbortException(cause).getCause());
	}

	@Test
	public void shouldTestNoBeanDefinedException() throws Exception {
		assertNotNull(new NoBeanDefinedException());
	}
}
