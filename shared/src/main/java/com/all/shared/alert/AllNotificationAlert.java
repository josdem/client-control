package com.all.shared.alert;

import com.all.chat.ChatStatus;
import com.all.chat.ChatType;
import com.all.chat.impl.ChatUserImpl;
import com.all.shared.model.ContactInfo;

public class AllNotificationAlert extends AbstractAlert {

	public static final String TYPE = "ALL_NOTIFICATION";

	private static final ContactInfo allInfo = new ContactInfo(new ChatUserImpl("contact@all.com", "All.com",
			ChatStatus.OFFLINE, ChatType.ALL));

	private String header;
	private String description;
	private String link;

	@Deprecated
	public AllNotificationAlert() {
	}

	public AllNotificationAlert(ContactInfo receiver, Long time, String header, String description, String link) {
		super(allInfo, receiver, time, TYPE);
		this.header = header;
		this.description = description;
		this.link = link;
	}

	public String getHeader() {
		return header;
	}

	public String getDescription() {
		return description;
	}

	public String getLink() {
		return link;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
