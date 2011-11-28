package com.all.networking;

import org.apache.mina.core.session.IoSession;

import com.all.shared.model.AllMessage;

public interface NetworkingMessageWriter {

	boolean write(IoSession session, String sender, AllMessage<?> message);
}
