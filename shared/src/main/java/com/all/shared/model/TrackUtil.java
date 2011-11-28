package com.all.shared.model;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TrackUtil {
	private Log log = LogFactory.getLog(TrackUtil.class);
	
	public void filterTracksWithoutMagnetLink(Iterable<Track> playlist) {
		Iterator<Track> trackIterator = playlist.iterator();
		while (trackIterator.hasNext()) {
			Track track = trackIterator.next();
			if (track.getDownloadString() == null) {
				log.warn("Track does not contain magnet link, removing it! " + track.getName());
				trackIterator.remove();
			}
		}
	}

}
