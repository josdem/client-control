package com.all.backend.commons;

import static org.junit.Assert.*;

import java.io.Serializable;

import org.junit.Test;

import com.all.shared.model.AllMessage;

public class TestBackendMessage {

	@Test
	public void shouldBeSerializable() throws Exception {
		AllMessage<String> allMessage = new AllMessage<String>("type", "body");
		BackendMessage backendMessage = new BackendMessage(allMessage);

		AllMessage<?> actual = backendMessage.getMessage();
		assertNotNull(actual);
		assertNotSame(allMessage, actual);
		assertEquals(allMessage.getType(), actual.getType());
		assertEquals(allMessage.getBody(), actual.getBody());
		assertTrue(backendMessage instanceof Serializable);
	}
}
