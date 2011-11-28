package com.all.core.events;

import com.all.chat.ChatType;
import com.all.event.EventObject;

public class ChatLoggingEvent extends EventObject {

	private final boolean loggedIn;
	private final ChatType chatType;

	public ChatLoggingEvent(boolean loggedIn, ChatType chatType) {
		this.loggedIn = loggedIn;
		this.chatType = chatType;
	}
	
	public ChatType getChatType() {
		return chatType;
	}
	
	public boolean isLoggedIn() {
		return loggedIn;
	}
	
}
