package com.all.client.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.all.shared.model.SmartPlaylist;
import com.all.shared.model.Track;

public enum LocalDefaultSmartPlaylist {
	ALL_MUSIC("All Music", true, true), LOOSE_TRACKS("Loose Tracks", true, false), MOST_PLAYED("Most Played", true, false), RECENTLY_ADDED(
			"Recently Added", true, false), RECENTLY_PLAYED("Recently Played", true, false), CRAPPY_KBPS(
			"Low Quality Tracks", false, false), TRACK_IN_TITLE("Untitled Tracks", false, false), RECENTLY_DOWNLOADS(
			"Recently Downloaded", true, false);

	private static final String CRAPPY_TRACKS = "from Track t where t.bitRate < 128";
	private final String label;
	private final boolean readOnly;
	private final boolean dropAllowed;

	private LocalDefaultSmartPlaylist(String label, boolean readOnly, boolean dropAllowed) {
		this.label = label;
		this.readOnly = readOnly;
		this.dropAllowed = dropAllowed;
	}

	public SmartPlaylist create(LocalModelDao dao) {
		switch (this) {
		case ALL_MUSIC:
			return new LocalDefaultSmartPlaylistImpl(label, readOnly, dropAllowed, dao) {
				private static final long serialVersionUID = 1L;

				@Override
				public List<Track> getTrackList(LocalModelDao dao) {
					return findAllActive(dao);
				}

			};
		case LOOSE_TRACKS:
			return new LocalDefaultSmartPlaylistImpl(label, readOnly, dropAllowed, dao) {
				private static final long serialVersionUID = 1L;

				@Override
				public List<Track> getTrackList(LocalModelDao dao) {
					return findLooseTracks(dao);
				}

			};
		case MOST_PLAYED:
			return new LocalDefaultSmartPlaylistImpl(label, readOnly, dropAllowed, dao) {
				private static final long serialVersionUID = 1L;

				@Override
				public List<Track> getTrackList(LocalModelDao dao) {
					return dao.findMostPlayed();
				}

			};
		case RECENTLY_ADDED:
			return new LocalDefaultSmartPlaylistImpl(label, readOnly, dropAllowed, dao) {
				private static final long serialVersionUID = 1L;

				@Override
				public List<Track> getTrackList(LocalModelDao dao) {
					return dao.findRecentlyAdded();
				}

			};
		case RECENTLY_PLAYED:
			return new LocalDefaultSmartPlaylistImpl(label, readOnly, dropAllowed, dao) {
				private static final long serialVersionUID = 1L;

				@Override
				public List<Track> getTrackList(LocalModelDao dao) {
					return dao.findRecentlyPlayed();
				}

			};
		case CRAPPY_KBPS:
			return new LocalDefaultSmartPlaylistImpl(label, readOnly, dropAllowed, dao) {
				private static final long serialVersionUID = 1L;

				@Override
				public List<Track> getTrackList(LocalModelDao dao) {
					return findCrappyTracks(dao);
				}

			};
		case TRACK_IN_TITLE:
			return new LocalDefaultSmartPlaylistImpl(label, readOnly, dropAllowed, dao) {
				private static final long serialVersionUID = 1L;

				@Override
				public List<Track> getTrackList(LocalModelDao dao) {
					return findTrackInName(dao);
				}

			};
		case RECENTLY_DOWNLOADS:
			return new LocalDefaultSmartPlaylistImpl(label, readOnly, dropAllowed, dao) {
				private static final long serialVersionUID = 1L;

				@Override
				public List<Track> getTrackList(LocalModelDao dao) {
					return dao.findRecentlyDownloaded();
				}
			};
		default:
			return null;
		}
	}

	public List<Track> findAllActive(LocalModelDao localModelDao) {
		return localModelDao.findAll(Track.class);
	}

	public List<Track> findLooseTracks(LocalModelDao localModelDao) {
		Set<Track> set = new HashSet<Track>();
		set.addAll(findLooseTracksInRoot(localModelDao));
		return new ArrayList<Track>(set);
	}

	public List<Track> findLooseTracksInRoot(LocalModelDao localModelDao) {
		String query = new StringBuilder("SELECT DISTINCT track FROM Track track ").append(
				" WHERE track.hashcode NOT IN (select playlistTrack.track.hashcode from PlaylistTrack playlistTrack)")
				.toString();

		return localModelDao.findTracks(query);
	}

	public int countLooseTracks(LocalModelDao localModelDao) {
		return localModelDao.countLooseTracks();
	}

	public List<Track> findTrackInName(LocalModelDao localModelDao) {
		return localModelDao.findByKeywordInName("%Track%");
	}

	public List<Track> findCrappyTracks(LocalModelDao localModelDao) {
		return localModelDao.findTracks(CRAPPY_TRACKS);
	}

}
