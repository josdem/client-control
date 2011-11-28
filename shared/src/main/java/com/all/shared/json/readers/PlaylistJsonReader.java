package com.all.shared.json.readers;

import java.util.ArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.all.shared.json.JsonConverter;
import com.all.shared.model.RemotePlaylist;
import com.all.shared.model.RemoteTrack;

public class PlaylistJsonReader implements JsonReader<RemotePlaylist>{

	private static final PlaylistJsonReader INSTANCE = new PlaylistJsonReader();
	
	private PlaylistJsonReader(){}
	
	@SuppressWarnings("unchecked")
	@Override
	public RemotePlaylist read(String json) {
		JSONObject jsonPlaylist = JSONObject.fromObject(json);
		//BE VERY CAREFUL WHEN ADDING OR REMOVING COMPLEX OBJECTS TO THE PLAYLIST INTERFACE
		RemotePlaylist playlist = (RemotePlaylist) JSONObject.toBean(jsonPlaylist, RemotePlaylist.class);
		JSONArray jsonTracks = jsonPlaylist.getJSONArray("tracks");
		playlist.setTracks(JsonConverter.toTypedCollection(jsonTracks.toString(), ArrayList.class, RemoteTrack.class));
		return playlist;
	}

	public static JsonReader<RemotePlaylist> getInstance() {
		return INSTANCE;
	}

}
