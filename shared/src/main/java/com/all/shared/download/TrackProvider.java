package com.all.shared.download;

import java.io.File;

import com.all.shared.model.Track;

public interface TrackProvider {

	Track getTrack(String trackId);

	File getFile(String trackId);
}
