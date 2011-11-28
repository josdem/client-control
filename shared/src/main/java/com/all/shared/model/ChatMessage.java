package com.all.shared.model;

import java.io.Serializable;
import java.util.Date;

import com.all.chat.ChatType;
import com.all.chat.ChatUser;
import com.all.chat.Message;

public class ChatMessage implements Serializable, Message {
	private static final long serialVersionUID = 1L;
	private String message;
	private ContactInfo sender;
	private ContactInfo recipient;
	private transient Date time = new Date();
	private ChatType chatType;

	public ChatMessage(ContactInfo sender, ContactInfo recipient, String message) {
		this.recipient = recipient;
		this.message = message;
		this.sender = sender;
	}

	public ChatMessage(Message message) {
		this(new ContactInfo(message.getFrom()), new ContactInfo(message.getTo()), message.getMessage());
		this.chatType = message.getChatType();
	}

	@Deprecated
	public ChatMessage() {
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ContactInfo getSender() {
		return sender;
	}

	public void setSender(ContactInfo sender) {
		this.sender = sender;
	}

	public ContactInfo getRecipient() {
		return recipient;
	}

	public void setRecipient(ContactInfo recipient) {
		this.recipient = recipient;
	}

	public Date getTime() {
		if (time == null) {
			time = new Date();
		}
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	public ChatType getChatType() {
		return chatType == null ? ChatType.ALL : chatType;
	}

	public void setChatType(ChatType chatType) {
		this.chatType = chatType;
	}

	@Override
	public ChatUser getFrom() {
		return getSender();
	}

	@Override
	public ChatUser getTo() {
		return getRecipient();
	}
}
