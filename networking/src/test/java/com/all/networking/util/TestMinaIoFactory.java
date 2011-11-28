package com.all.networking.util;

import static org.junit.Assert.assertTrue;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.junit.Test;

public class TestMinaIoFactory {
	@Test
	public void shouldCreateAIoAcceptor() throws Exception {
		IoAcceptor acceptor = new MinaIoFactory().createAcceptor();
		assertTrue(acceptor instanceof NioSocketAcceptor);
	}
	
	@Test
	public void shouldCreateAIoCOnnector() throws Exception {
		IoConnector connector = new MinaIoFactory().createConnector();
		assertTrue(connector instanceof NioSocketConnector);
	}

}
