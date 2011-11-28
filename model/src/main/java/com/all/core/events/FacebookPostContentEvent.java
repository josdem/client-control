package com.all.core.events;

import com.all.event.EventObject;
import com.all.shared.model.ContactInfo;
import com.all.shared.model.ModelCollection;

public class FacebookPostContentEvent  extends EventObject{

	private final ModelCollection model;
	private final ContactInfo contact;

	public FacebookPostContentEvent(ModelCollection model, ContactInfo contact) {
		this.model = model;
		this.contact = contact;
	}

	public ContactInfo getContact() {
		return contact;
	}

	public ModelCollection getModel() {
		return model;
	}

}
