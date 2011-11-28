package com.all.core.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.all.action.ActionObject;

public class SendEmailInvitationAction extends ActionObject {

	private final List<String> recipients;

	private final String body;

	public SendEmailInvitationAction(List<String> recipients, String body) {
		this.recipients = recipients;
		this.body = body;
	}

	public SendEmailInvitationAction(String recipient, String body) {
		this.recipients = Arrays.asList(recipient);
		this.body = body;
	}

	public List<String> getRecipients() {
		return new ArrayList<String>(recipients);
	}

	public String getBody() {
		return body;
	}

}
