package com.all.client.util;

import java.io.File;

import com.all.shared.model.ModelCollection;

public interface TrackRepository {

	boolean isLocallyAvailable(String trackId);

	boolean isRemotelyAvailable(String trackId);

	boolean isAllLocallyAvailable(ModelCollection modelCollection);

	File getFile(String trackId);

	boolean isFormatSupported(File file);

}
