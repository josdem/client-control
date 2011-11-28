package com.all.shared.json.readers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.all.shared.json.JsonConverter;
import com.all.shared.messages.EmailContact;
import com.all.shared.model.ContactInfo;

public class EmailContactJsonReader implements JsonReader<EmailContact> {

	private static final EmailContactJsonReader INSTANCE = new EmailContactJsonReader();

	private EmailContactJsonReader() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public EmailContact read(String json) {
		JSONObject jsonEmailContact = JSONObject.fromObject(json);
		List<ContactInfo> registeredContacts = JsonConverter.toTypedCollection(jsonEmailContact.getJSONArray(
				"registeredContacts").toString(), ArrayList.class, ContactInfo.class);
		Map<String, String> unregisteredContacts = JsonConverter.toBean(jsonEmailContact.getJSONObject(
				"unregisteredContacts").toString(), HashMap.class);
		return new EmailContact(jsonEmailContact.getString("email"), unregisteredContacts, registeredContacts);
	}

	public static JsonReader<EmailContact> getInstance() {
		return INSTANCE;
	}
}
