package com.all.networking.util;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.springframework.stereotype.Service;

@Service
public final class MinaIoFactory implements IoFactory {
	@Override
	public IoAcceptor createAcceptor() {
		return new NioSocketAcceptor();
	}

	@Override
	public IoConnector createConnector() {
		return new NioSocketConnector();
	}

}
