package com.all.shared.json.readers;

import net.sf.json.JSONObject;

import com.all.shared.json.JsonConverter;
import com.all.shared.mc.TrackSearchResult;
import com.all.shared.model.RemoteTrack;

public class TrackSearchResultJsonReader implements JsonReader<TrackSearchResult> {

	private static final TrackSearchResultJsonReader INSTANCE = new TrackSearchResultJsonReader();

	public static JsonReader<TrackSearchResult> getInstance() {
		return INSTANCE;
	}

	private TrackSearchResultJsonReader() {
	}

	@Override
	public TrackSearchResult read(String json) {
		JSONObject jsonObject = JSONObject.fromObject(json);
		JSONObject jsonTrack = jsonObject.getJSONObject("track");
		TrackSearchResult response = new TrackSearchResult(JsonConverter.toBean(jsonTrack.toString(), RemoteTrack.class),
				jsonObject.getDouble("score"));
		return response;
	}

}
