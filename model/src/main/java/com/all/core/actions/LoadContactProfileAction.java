package com.all.core.actions;

import com.all.action.ActionObject;
import com.all.core.model.Views;
import com.all.shared.model.ContactInfo;

public class LoadContactProfileAction extends ActionObject{

	private ComposeView composeView = new ComposeView(Views.PROFILE);
	private ContactInfo contact;
	
	public LoadContactProfileAction(ContactInfo contact, ComposeView composeView) {
		this.contact = contact;
		this.composeView = composeView;
	}
	
	public LoadContactProfileAction(ContactInfo contact) {
		this.contact = contact;
	}
	
	public ComposeView getComposeView() {
		return composeView;
	}
	
	public ContactInfo getContact() {
		return contact;
	}
	
	
}
