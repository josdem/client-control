package com.all.client.util;

import com.all.shared.model.Folder;
import com.all.shared.model.ModelCollection;
import com.all.shared.model.ModelTypes;
import com.all.shared.model.Playlist;
import com.all.shared.model.Root;
import com.all.shared.model.TrackContainer;

public class ModelValidation {
	public static boolean isAbleToPaste(ModelCollection source, TrackContainer destination) {
		if (source.isEmpty()) {
			return false;
		}
		if (destination instanceof Folder) {
			if (source.only(ModelTypes.playlists, ModelTypes.tracks)) {
				return true;
			}

		} else if (destination instanceof Playlist) {
			if (source.only(ModelTypes.tracks)) {
				return true;
			}
		} else if (destination instanceof Root && (source.only(ModelTypes.playlists) || source.isRemote())) {
			return true;
		}
		return false;
	}

}
