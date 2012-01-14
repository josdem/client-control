package com.all.backend.commons;

import java.io.Serializable;

import com.all.shared.json.JsonConverter;
import com.all.shared.model.AllMessage;

public class BackendMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	private String body;

	public BackendMessage(AllMessage<?> message) {
		this.body = JsonConverter.toJson(message);
	}

	public AllMessage<?> getMessage() {
		return body != null ? JsonConverter.toBean(body, AllMessage.class) : null;
	}
}
