package com.all.client.model.predicate;

import com.all.shared.model.ContactInfo;

public class ContactNamePredicate extends BaseCollatorPredicate<ContactInfo> {

	public ContactNamePredicate(String target) {
		super(target);
	}


	@Override
	protected String sourceString(ContactInfo contact) {
		return contact.getNickName();
	}


	@Override
	protected void refineRules() {
	}

}
