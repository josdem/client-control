package com.all.core.actions;

import java.util.List;

import com.all.action.ActionObject;
import com.all.chat.ChatType;
import com.all.shared.model.ContactInfo;
import com.all.shared.model.ModelCollection;

public class ShareContentAction extends ActionObject {

	private ModelCollection model;
	private List<ContactInfo> contacts;
	private final ChatType type;

	public ShareContentAction() {
		type = ChatType.ALL;
	}

	public ShareContentAction(ModelCollection model, List<ContactInfo> contacts) {
		this.model = model;
		this.contacts = contacts;
		if (contacts == null || contacts.isEmpty()) {
			this.type = ChatType.ALL;
		} else {
			this.type = contacts.get(0).getChatType();
		}
	}

	public List<ContactInfo> getContacts() {
		return contacts;
	}

	public ModelCollection getModel() {
		return model;
	}

	public ChatType getType() {
		return type;
	}

}
