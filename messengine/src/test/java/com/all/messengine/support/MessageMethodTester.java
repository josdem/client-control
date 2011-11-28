package com.all.messengine.support;

import com.all.messengine.Message;
import com.all.messengine.MessageMethod;

public class MessageMethodTester {
	public String val = null;

	@MessageMethod(TestTypes.messageId)
	public void handleEvent(Message<String> valueEvent) {
		val = valueEvent.getBody();
	}

}
