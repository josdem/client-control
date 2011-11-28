package com.all.client.model.predicate;

import com.all.shared.model.ContactInfo;

public class ContactMailPredicate extends BaseCollatorPredicate<ContactInfo> {
	public ContactMailPredicate(String target) {
		super(target);
	}

	@Override
	protected String sourceString(ContactInfo contact) {
		return contact.getEmail();
	}

	@Override
	protected void refineRules() {
	}

}
