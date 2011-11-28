package com.all.client.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import com.all.shared.model.Track;

public class MusicCacheModel {
	private static final String QUERY = "FROM Track";
	int delta = 100;
	private LocalModelDao localModelDao;
	private Ehcache ehcache;
	private Log log = LogFactory.getLog(this.getClass());

	private Track findHashcodeOnCache(String hashcode) {
		if (isTrackInCache(hashcode)) {
			return (Track) ehcache.get(hashcode).getValue();
		}
		Track track = localModelDao.findByHashcode(hashcode);
		putNewElementToCache(hashcode, track);
		return track;
	}

	private Track findTrackOnCache(Track track) {
		String hashcode = track.getHashcode();
		if (isTrackInCache(hashcode)) {
			return track;
		}
		putNewElementToCache(hashcode, track);
		return track;
	}
	
	private void removeTracksOnCache(List<Track> resultList) {
		ehcache.removeAll();
		for (Track track : resultList) {
			putNewElementToCache(track.getHashcode(), track);
		}
	}

	private void putNewElementToCache(String hashcode, Track track) {
		Element element = new Element(hashcode, track);
		ehcache.put(element);
	}

	private boolean isTrackInCache(String hashcode) {
		return ehcache.get(hashcode) == null ? false : true;
	}

	@SuppressWarnings("unchecked")
	public List<Track> fillTracks(List<String> hashcodeTracks, Ehcache ehcache) {
		this.ehcache = ehcache;
		log.debug("ehcache: " + ehcache.getName());

		log.debug("musicCacheSize: " + ehcache.getSize());
		if (hashcodeTracks.size() - ehcache.getSize() > delta) {
			List<Track> resultList = localModelDao.getHibernateTemplate().find(QUERY);
			for (Track track : resultList) {
				findTrackOnCache(track);
			}
			return resultList;
		} else if (hashcodeTracks.size() - ehcache.getSize() < 0){
			List<Track> resultList = localModelDao.getHibernateTemplate().find(QUERY);
			removeTracksOnCache(resultList);
			return resultList;
		} else {
			ArrayList<Track> arrayList = new ArrayList<Track>();
			for (String hashcode : hashcodeTracks) {
				arrayList.add(findHashcodeOnCache(hashcode));
			}
			return arrayList;
		}
	}

	public void initialize(LocalModelDao localModelDao, Ehcache ehcache) {
		this.localModelDao = localModelDao;
		this.ehcache = ehcache;
	}

}
