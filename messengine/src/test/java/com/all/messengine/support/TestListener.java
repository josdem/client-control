package com.all.messengine.support;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import com.all.messengine.Message;
import com.all.messengine.MessageListener;

@MessageSelector("foo")
public class TestListener implements MessageListener<Message<String>> {
	AtomicInteger counter = new AtomicInteger(0);

	private Message<String> message;

	@Override
	public void onMessage(Message<String> message) {
		this.message = message;
		counter.incrementAndGet();
	}

	public Message<String> getMessage() {
		return message;
	}
	
	@Test
	public void shouldHaveAtLeastOneTestToAllowHudsonBuild() throws Exception {
		assertTrue(true);
	}

}
