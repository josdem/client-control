package com.all.messengine.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.all.messengine.Message;
import com.all.messengine.MessageListener;

class MessageConsumer<T extends Message<?>> implements Runnable {
	private final T message;
	private final List<MessageListener<T>> listeners;

	private static final Log LOG = LogFactory.getLog(MessageConsumer.class);

	public MessageConsumer(T message, List<MessageListener<T>> listeners) {
		this.message = message;
		this.listeners = listeners;
	}

	@Override
	public void run() {
		for (MessageListener<T> messageListener : listeners) {
			try {
				LOG.info("\nMESSAGE:"+message.getType()+":"+messageListener.getClass().getSimpleName());
				messageListener.onMessage(message);
			} catch (Exception e) {
				LOG.error("Unhandled exception for a message of type " + message.getType() + " in listener " + messageListener,
						e);
			}
		}
	}
}