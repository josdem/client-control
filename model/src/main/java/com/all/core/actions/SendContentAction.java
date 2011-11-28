package com.all.core.actions;

import com.all.action.ActionObject;
import com.all.core.model.ContactCollection;
import com.all.shared.model.ModelCollection;

public class SendContentAction extends ActionObject {

	private final ModelCollection model;
	private final ContactCollection contacts;
	private final String message;

	public SendContentAction(ModelCollection model, ContactCollection contacts, String message) {
		this.model = model;
		this.contacts = contacts;
		this.message = message;
	}

	public ContactCollection getContacts() {
		return contacts;
	}

	public ModelCollection getModel() {
		return model;
	}

	public String getMessage() {
		return message;
	}

}
