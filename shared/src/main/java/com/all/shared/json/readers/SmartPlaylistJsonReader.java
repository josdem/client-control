package com.all.shared.json.readers;

import net.sf.json.JSONObject;

import com.all.shared.json.JsonConverter;
import com.all.shared.model.RemotePlaylist;
import com.all.shared.model.SimpleSmartPlaylist;

public class SmartPlaylistJsonReader implements JsonReader<SimpleSmartPlaylist> {
	
	private static final SmartPlaylistJsonReader INSTANCE = new SmartPlaylistJsonReader();
	
	private SmartPlaylistJsonReader(){}

	@Override
	public SimpleSmartPlaylist read(String json) {
		JSONObject jsonSmartPlaylist = JSONObject.fromObject(json);
		return new SimpleSmartPlaylist(jsonSmartPlaylist.getString("label"), JsonConverter.toBean(jsonSmartPlaylist.getJSONObject("playlist").toString(), RemotePlaylist.class), jsonSmartPlaylist.getBoolean("readOnly"));
	}

	public static JsonReader<SimpleSmartPlaylist> getInstance() {
		return INSTANCE;
	}

}
