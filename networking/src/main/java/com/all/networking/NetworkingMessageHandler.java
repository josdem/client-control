package com.all.networking;

import org.apache.mina.core.service.IoHandler;

import com.all.shared.model.AllMessage;

public interface NetworkingMessageHandler extends IoHandler{

	void onAllMessage(AllMessage<?> allMessage);
}
