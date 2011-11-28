package com.all.client.data;

import com.all.client.model.InvalidFileException;
import com.all.client.model.TrackFile;
import com.all.shared.model.Track;

public interface MetadataStrategy {
	Track createTrack(TrackFile trackFile) throws InvalidFileException;
}
