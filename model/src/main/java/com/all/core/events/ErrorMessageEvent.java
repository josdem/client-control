package com.all.core.events;

import com.all.event.EventObject;

public class ErrorMessageEvent extends EventObject {
	private final Object[] parameters;
	private final String messageKey;
	private final String[] stringParameters;

	public ErrorMessageEvent(String messageKey, Object... parameters) {
		if (parameters == null) {
			parameters = new Object[] {};
		}
		this.messageKey = messageKey;
		this.parameters = parameters;
		if (hasParameters()) {
			this.stringParameters = new String[parameters.length];
			for (int i = 0; i < parameters.length; i++) {
				stringParameters[i] = parameters[i].toString();
			}
		} else {
			this.stringParameters = new String[0];
		}
	}

	public Object[] getParameters() {
		return parameters;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public boolean hasParameters() {
		return parameters != null && parameters.length > 0;
	}

	public String[] getStringParameters() {
		return stringParameters;
	}
}
