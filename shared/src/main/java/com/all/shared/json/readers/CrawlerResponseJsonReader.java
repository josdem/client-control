package com.all.shared.json.readers;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import com.all.shared.json.JsonConverter;
import com.all.shared.messages.CrawlerResponse;
import com.all.shared.messages.EmailContact;

public class CrawlerResponseJsonReader implements JsonReader<CrawlerResponse> {

	private static final CrawlerResponseJsonReader INSTANCE = new CrawlerResponseJsonReader();
	
	private CrawlerResponseJsonReader(){}
	
	@Override
	public CrawlerResponse read(String json) {
		@SuppressWarnings("unchecked")
		List<EmailContact> domainContacts = JsonConverter.toTypedCollection(JSONObject.fromObject(json).getJSONArray(
				"emailContacts").toString(), ArrayList.class, EmailContact.class);
		return new CrawlerResponse(domainContacts);
	}

	public static JsonReader<CrawlerResponse> getInstance() {
		return INSTANCE;
	}
}
