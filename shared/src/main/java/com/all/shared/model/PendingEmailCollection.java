package com.all.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PendingEmailCollection implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private List<String> toEmails = new ArrayList<String>();
	private String fromMail;
	private String message;
	private String subject;
	private Long userId;

	public PendingEmailCollection() {}
	
	public PendingEmailCollection(List<String> toEmails, String fromMail,
			String message, String subject, Long userId) {
		this.toEmails.addAll(toEmails);
		this.fromMail = fromMail;
		this.message = message;
		this.subject = subject;
		this.userId = userId;
	}

	public List<PendingEmail> getPendingEmails(){
		List<PendingEmail> pendingEmails = new ArrayList<PendingEmail>();
		for(String toEmail: toEmails) {
			pendingEmails.add(new PendingEmail(fromMail, toEmail, subject, message, userId));
		}
		return pendingEmails;
	}
	
	public void remove(String email) {
		toEmails.remove(email);
	}

	public List<String> getToEmails() {
		return toEmails;
	}

	public void setToEmails(List<String> toEmails) {
		this.toEmails = toEmails;
	}

	public String getFromMail() {
		return fromMail;
	}

	public void setFromMail(String fromMail) {
		this.fromMail = fromMail;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
}
