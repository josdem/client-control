package com.all.shared.alert;

import java.util.Date;

import com.all.shared.model.ContactInfo;

public abstract class AbstractAlert implements Comparable<Alert>, Alert {
	private static final long serialVersionUID = 1L;

	private Long time;
	private ContactInfo sender;

	private String id;

	private String type;

	private ContactInfo receiver;

	public AbstractAlert(){}
	
	protected AbstractAlert(ContactInfo sender, ContactInfo receiver, Long date, String type) {
		this.receiver = receiver;
		this.time = date;
		this.sender = sender;
		this.type = type;
		this.id = receiver.getEmail() + "_ALERT_" + type + sender.getEmail() + date;
	}

	public Date getDate() {
		return new Date(time);
	}

	public ContactInfo getSender() {
		return sender;
	}

	public String getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public ContactInfo getReceiver() {
		return receiver;
	}

	@Override
	public int compareTo(Alert o) {
		return getDate().compareTo(o.getDate()) * -1;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Alert) {
			Alert alert = (Alert) obj;
			return id.equals(alert.getId());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}

	public Long getTime() {
		return time;
	}
	
	@Deprecated
	public void setTime(Long time) {
		this.time = time;
	}

	@Deprecated
	public void setSender(ContactInfo sender) {
		this.sender = sender;
	}

	@Deprecated
	public void setId(String id) {
		this.id = id;
	}

	@Deprecated
	public void setType(String type) {
		this.type = type;
	}

	@Deprecated
	public void setReceiver(ContactInfo receiver) {
		this.receiver = receiver;
	}

	@Override
	public Class<Alert> getTypedClass() {
		return Alert.class;
	}
}
