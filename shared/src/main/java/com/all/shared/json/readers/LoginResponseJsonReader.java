package com.all.shared.json.readers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.all.shared.json.JsonConverter;
import com.all.shared.messages.LoginResponse;
import com.all.shared.model.ContactRequest;

public class LoginResponseJsonReader implements JsonReader<LoginResponse> {

	private static final LoginResponseJsonReader INSTANCE = new LoginResponseJsonReader();

	public static JsonReader<LoginResponse> getInstance() {
		return INSTANCE;
	}

	private LoginResponseJsonReader() {
	}

	@Override
	public LoginResponse read(String json) {
		JSONObject jsonObject = JSONObject.fromObject(json);
		// 
		LoginResponse response = (LoginResponse) JSONObject.toBean(jsonObject, LoginResponse.class);
		if (jsonObject.has("pendingRequests")) {
			JSONArray jsonArray = jsonObject.getJSONArray("pendingRequests");
			@SuppressWarnings("unchecked")
			Iterator iterator = jsonArray.iterator();
			List<ContactRequest> pendingRequests = new ArrayList<ContactRequest>();
			while (iterator.hasNext()) {
				pendingRequests.add((ContactRequest) JsonConverter.toBean(iterator.next().toString(), ContactRequest.class));
			}
			response.setPendingRequests(pendingRequests);
		}
		return response;
	}

}
