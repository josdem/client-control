package com.all.shared.json.readers;

import java.util.ArrayList;

import net.sf.json.JSONObject;

import com.all.shared.json.JsonConverter;
import com.all.shared.model.UltrapeerNode;
import com.all.shared.model.UltrapeerSessionResponse;

public class UltrapeerSessionResponseJsonReader implements JsonReader<UltrapeerSessionResponse> {

	private static final String NEW_ULTRAPEERS_KEY = "newUltrapeers";

	private static final String DEPRECATED_ULTRAPEERS_KEY = "deprecatedUltrapeers";

	private static final String ACCEPTED_KEY = "accepted";
	
	private static final JsonReader<UltrapeerSessionResponse> INSTANCE = new UltrapeerSessionResponseJsonReader();

	private UltrapeerSessionResponseJsonReader(){}
	
	@SuppressWarnings("unchecked")
	@Override
	public UltrapeerSessionResponse read(String json) {
		JSONObject jsonObj = JSONObject.fromObject(json);
		UltrapeerSessionResponse response = new UltrapeerSessionResponse();
		response.setAccepted(jsonObj.getBoolean(ACCEPTED_KEY));
		if(jsonObj.has(DEPRECATED_ULTRAPEERS_KEY)) {
			response.setDeprecatedUltrapeers(JsonConverter.toTypedCollection(jsonObj.getJSONArray(DEPRECATED_ULTRAPEERS_KEY)
					.toString(), ArrayList.class, UltrapeerNode.class));
		}
		if(jsonObj.has(NEW_ULTRAPEERS_KEY)) {
			response.setNewUltrapeers(JsonConverter.toTypedCollection(jsonObj.getJSONArray(NEW_ULTRAPEERS_KEY)
					.toString(), ArrayList.class, UltrapeerNode.class));
		}
		return response;
	}
	
	public static JsonReader<UltrapeerSessionResponse> getInstance(){
		return INSTANCE;
	}

}
