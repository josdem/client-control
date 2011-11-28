package com.all.shared.json.readers;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.all.shared.json.JsonConverter;
import com.all.shared.model.Playlist;
import com.all.shared.model.RemoteFolder;
import com.all.shared.model.RemotePlaylist;

public class FolderJsonReader implements JsonReader<RemoteFolder>{

	private static final FolderJsonReader INSTANCE = new FolderJsonReader();
	
	private FolderJsonReader(){}
	
	@Override
	public RemoteFolder read(String json) {
		JSONObject jsonFolder = JSONObject.fromObject(json);
		RemoteFolder folder = new RemoteFolder();
		folder.setHashcode(jsonFolder.getString("hashcode"));
		folder.setName(jsonFolder.getString("name"));
		JSONArray jsonPlaylists = jsonFolder.getJSONArray("playlists");
		@SuppressWarnings("unchecked")
		List<Playlist> playlists = JsonConverter.toTypedCollection(jsonPlaylists.toString(), ArrayList.class, RemotePlaylist.class);
		folder.setPlaylists(playlists);
		return folder;
	}

	public static JsonReader<RemoteFolder> getInstance() {
		return INSTANCE;
	}

}
