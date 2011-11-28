package com.all.networking.util;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoConnector;

public interface IoFactory {
	IoAcceptor createAcceptor();

	IoConnector createConnector();
}
