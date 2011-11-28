package com.all.core.events;

import java.util.List;

import com.all.event.EventObject;
import com.all.shared.model.ContactInfo;
import com.all.shared.model.ModelCollection;

public class SendContentEvent extends EventObject {

	private final ModelCollection model;
	private final List<ContactInfo> contacts;

	public SendContentEvent(ModelCollection model, List<ContactInfo> contacts) {
		this.model = model;
		this.contacts = contacts;
	}

	public List<ContactInfo> getContacts() {
		return contacts;
	}

	public ModelCollection getModel() {
		return model;
	}

}
