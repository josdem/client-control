package com.all.shared.json.readers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.all.shared.json.JsonConverter;
import com.all.shared.messages.FeedsResponse;
import com.all.shared.newsfeed.AllFeed;

public class FeedsResponseJsonReader implements JsonReader<FeedsResponse> {

	private static final FeedsResponseJsonReader INSTANCE = new FeedsResponseJsonReader();
	
	private FeedsResponseJsonReader(){}
	
	@SuppressWarnings("deprecation")
	@Override
	public FeedsResponse read(String json) {
		JSONObject jsonObject = JSONObject.fromObject(json);
		FeedsResponse response = (FeedsResponse) JSONObject.toBean(jsonObject, FeedsResponse.class);
		if (jsonObject.has("feeds")) {
			JSONArray jsonArray = jsonObject.getJSONArray("feeds");
			@SuppressWarnings("unchecked")
			Iterator iterator = jsonArray.iterator();
			List<AllFeed> feeds = new ArrayList<AllFeed>();
			while (iterator.hasNext()) {
				feeds.add((AllFeed) JsonConverter.toBean(iterator.next().toString(), AllFeed.class));
			}
			response.setFeeds(feeds);
		}
		return response;
	}

	public static JsonReader<FeedsResponse> getInstance() {
		return INSTANCE;
	}
}
