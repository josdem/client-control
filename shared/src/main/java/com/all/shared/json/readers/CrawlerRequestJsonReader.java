package com.all.shared.json.readers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.all.shared.command.LoginCommand;
import com.all.shared.external.email.EmailDomain;
import com.all.shared.json.JsonConverter;
import com.all.shared.messages.CrawlerRequest;

public class CrawlerRequestJsonReader implements JsonReader<CrawlerRequest> {
	
	private static final CrawlerRequestJsonReader INSTANCE = new CrawlerRequestJsonReader();
	
	public static JsonReader<CrawlerRequest> getInstance(){
		return INSTANCE;
	}
	
	private CrawlerRequestJsonReader(){}

	@SuppressWarnings("unchecked")
	@Override
	public CrawlerRequest read(String json) {
		CrawlerRequest crawlerRequest = new CrawlerRequest();
		JSONObject jsonRequest = JSONObject.fromObject(json);
		JSONObject jsonAccounts = jsonRequest.getJSONObject("accounts");
		Iterator iterator = jsonAccounts.keys();
		Map<EmailDomain, List<LoginCommand>> accounts = new HashMap<EmailDomain, List<LoginCommand>>();
		while (iterator.hasNext()) {
			Object key = iterator.next();
			JSONArray jsonDomainAccounts = jsonAccounts.getJSONArray(key.toString());
			List<LoginCommand> domainAccounts = JsonConverter.toTypedCollection(jsonDomainAccounts.toString(),
					ArrayList.class, LoginCommand.class);
			accounts.put(EmailDomain.valueOf(key.toString()), domainAccounts);
		}
		crawlerRequest.setAccounts(accounts);
		return crawlerRequest;
	}
}
