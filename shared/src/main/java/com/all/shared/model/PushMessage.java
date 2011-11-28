package com.all.shared.model;

import java.util.ArrayList;
import java.util.List;

public class PushMessage<T> extends AllMessage<AllMessage<T>> {

	public static final String TYPE = "PUSH_ALL_MESSAGE_TYPE";
	private final List<String> recipients;

	public PushMessage(AllMessage<T> body, List<String> recipients) {
		super(body.getType(), body);
		this.recipients = new ArrayList<String>(recipients);
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class getBodyType() {
		return AllMessage.class;
	}

	public List<String> getRecipients() {
		return recipients;
	}
}
