package com.all.shared.json.readers;

import java.util.ArrayList;

import net.sf.json.JSONObject;

import com.all.shared.json.JsonConverter;
import com.all.shared.model.ModelCollection;
import com.all.shared.model.RemoteFolder;
import com.all.shared.model.RemotePlaylist;
import com.all.shared.model.RemoteTrack;
import com.all.shared.model.SimpleSmartPlaylist;

public class ModelCollectionJsonReader implements JsonReader<ModelCollection>{

	private static final ModelCollectionJsonReader INSTANCE = new ModelCollectionJsonReader();
	
	private ModelCollectionJsonReader(){}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ModelCollection read(String json) {
		JSONObject jsonModel = JSONObject.fromObject(json);
		ModelCollection model = new ModelCollection();
		model.setRemote(true);
		model.setTracks(JsonConverter.toTypedCollection(jsonModel.getJSONArray("tracks").toString(), ArrayList.class, RemoteTrack.class));
		model.setPlaylists(JsonConverter.toTypedCollection(jsonModel.getJSONArray("playlists").toString(), ArrayList.class, RemotePlaylist.class));
		model.setSmartPlaylists(JsonConverter.toTypedCollection(jsonModel.getJSONArray("smartPlaylists").toString(), ArrayList.class, SimpleSmartPlaylist.class));
		model.setFolders(JsonConverter.toTypedCollection(jsonModel.getJSONArray("folders").toString(), ArrayList.class, RemoteFolder.class));
		return model;
	}

	public static JsonReader<ModelCollection> getInstance() {
		return INSTANCE;
	}

}
