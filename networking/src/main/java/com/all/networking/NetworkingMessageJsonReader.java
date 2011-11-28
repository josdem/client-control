package com.all.networking;

import net.sf.json.JSONObject;

import com.all.shared.json.JsonConverter;
import com.all.shared.json.readers.JsonReader;
import com.all.shared.model.AllMessage;

public class NetworkingMessageJsonReader implements JsonReader<NetworkingMessage> {

	@Override
	public NetworkingMessage read(String json) {
		JSONObject jsonMessage = JSONObject.fromObject(json);
		String sender = jsonMessage.has("sender") ? jsonMessage.getString("sender") : null;
		if (jsonMessage.has("body")) {
			String jsonBody = jsonMessage.getJSONObject("body").toString();
			NetworkingMessage networkingMessage = new NetworkingMessage(sender, JsonConverter.toBean(jsonBody,
					AllMessage.class));
			return networkingMessage;
		}
		return new NetworkingMessage(sender, jsonMessage.getString("chunk"), jsonMessage.getInt("chunkNumber"), jsonMessage
				.getInt("totalChunks"), jsonMessage.getString("messageId"));
	}
}
