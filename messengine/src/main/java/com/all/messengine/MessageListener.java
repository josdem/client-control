package com.all.messengine;

public interface MessageListener<T extends Message<?>> {
	void onMessage(T message);
}
