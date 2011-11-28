package com.all.client.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.all.shared.model.ModelDao;
import com.all.shared.model.Playlist;
import com.all.shared.model.SyncEventEntity;
import com.all.shared.model.Track;

@Service
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LocalModelDao implements ModelDao {

	private HibernateTemplate hibernateTemplate;

	private DataSource dataSource;

	private final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	public void setHibernateTemplate(@Qualifier("userHibernateTemplate") HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
		// The following lines make hibernate to perform EVERY query using
		// ehcache
		this.hibernateTemplate.setCacheQueries(true);
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Autowired
	@Qualifier("userDatasource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public <T> T delete(T object) {
		try {
			hibernateTemplate.delete(object);
		} catch (Exception ex) {
			log.error("Could not delete object " + object, ex);
		}
		return object;
	}

	@Override
	public <T> T merge(T object) {
		try {
			hibernateTemplate.merge(object);
		} catch (Exception e) {
			log.error("Could not merge object " + object, e);
		}
		return object;
	}

	@Override
	public <T> void refresh(T object) {
		try {
			hibernateTemplate.refresh(object);
		} catch (Exception e) {
			log.warn("Could not refresh " + object);
		}
	}

	public <T> void evict(T object) {
		try {
			hibernateTemplate.evict(object);
		} catch (Exception e) {
			log.error("Could not evict object " + object, e);
		}
	}

	@Override
	public <T> T update(T object) {
		try {
			hibernateTemplate.update(object);
		} catch (Exception e) {
			log.error("Could not update object " + object, e);
		}
		return object;
	}

	@Override
	public <T> T save(T object) {
		try {
			hibernateTemplate.save(object);
		} catch (Exception ex) {
			log.error("Could not save object " + object, ex);
		}
		return object;
	}

	@Override
	public <T> T saveOrUpdate(T object) {
		try {
			hibernateTemplate.saveOrUpdate(object);
		} catch (Exception e) {
			log.error("Could not save or update object " + object, e);
		}
		return object;
	}

	public <T> T findById(Class<T> objectClass, Serializable id) {
		return hibernateTemplate.get(objectClass, id);
	}

	public LocalPlaylist findPlaylistById(Serializable id) {
		return (LocalPlaylist) hibernateTemplate.get(LocalPlaylist.class, id);
	}

	@SuppressWarnings("unchecked")
	public <T> T findByEntityName(String entityName, Serializable id) {
		return (T) hibernateTemplate.get(entityName, id);
	}

	private static final int MAX_QUERY_RESULTS = 100;

	@SuppressWarnings("unchecked")
	static class TrackHibernateCallback implements HibernateCallback {

		private final String sentence;

		public TrackHibernateCallback(String query) {
			this.sentence = query;
		}

		public Object doInHibernate(Session sess) throws SQLException {
			Query query = sess.createQuery(sentence);
			query.setMaxResults(MAX_QUERY_RESULTS);
			return query.list();
		}
	}

	@SuppressWarnings("unchecked")
	public Track findByHashcode(String hashcode) {
		List<Track> tracks = (List<Track>) hibernateTemplate.find("from Track where hashcode='" + hashcode + "'");
		return tracks.isEmpty() ? null : tracks.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<SyncEventEntity> findAllSyncEvents(String email) {
		return (List<SyncEventEntity>) hibernateTemplate.find("from SyncEventEntity where email = '" + email + "'");
	}

	@SuppressWarnings("unchecked")
	public List<Playlist> findAllWithRootParent() {
		return (List<Playlist>) hibernateTemplate.find("FROM Playlist WHERE folderId=NULL");
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findAll(Class<T> objectClass) {
		String hql = "FROM " + objectClass.getSimpleName();
		return hibernateTemplate.find(hql);
	}

	public <T> List<T> loadAll(Class<T> clazz) {
		return hibernateTemplate.loadAll(clazz);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Track> findTracks(String query) {
		return hibernateTemplate.find(query);
	}

	@SuppressWarnings("unchecked")
	public List<Track> findTracks(final List<String> hashcodes) {
		return hibernateTemplate.executeFind(new HibernateCallback<List<LocalTrack>>() {
			@Override
			public List<LocalTrack> doInHibernate(Session session) throws SQLException {
				Criteria criteria = session.createCriteria(LocalTrack.class);
				criteria.add(Restrictions.in("hashcode", hashcodes));
				return criteria.list();
			}
		});
	}

	public TrackFile findTrackFileByPath(final String path) {
		return hibernateTemplate.execute(new HibernateCallback<TrackFile>() {
			@Override
			public TrackFile doInHibernate(Session session) throws SQLException {
				Criteria criteria = session.createCriteria(TrackFile.class);
				criteria.add(Restrictions.eq("filename", path));
				return (TrackFile) criteria.uniqueResult();
			}
		});
	}

	public long count(final Class<?> objectClass) {

		return hibernateTemplate.execute(new HibernateCallback<Long>() {
			@Override
			public Long doInHibernate(Session session) throws SQLException {
				String entityName = objectClass.getSimpleName();
				if (objectClass.isAnnotationPresent(Entity.class)) {
					Entity entity = objectClass.getAnnotation(Entity.class);
					if (StringUtils.isNotEmpty(entity.name())) {
						entityName = entity.name();
					}
				}
				Query q = session.createQuery("select count(*) from " + entityName);
				return Long.valueOf(q.uniqueResult().toString());
			}
		});
	}

	@SuppressWarnings("unchecked")
	public int count(final String hql) {
		return ((Long) hibernateTemplate.execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws SQLException {
				Query q = session.createQuery("select count(*) " + hql);
				return q.uniqueResult();
			}
		})).intValue();
	}

	@SuppressWarnings("unchecked")
	public int countLooseTracks() {
		return ((Integer) hibernateTemplate.execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws SQLException {
				Query q = session.createSQLQuery("SELECT COUNT (track.hashcode) looseTracks "
						+ " FROM Track track LEFT JOIN PlaylistTrack playlistTrack ON playlistTrack.trackfk = track.hashcode "
						+ " WHERE playlistTrack.id is null  ");
				return q.uniqueResult();
			}
		})).intValue();
	}

	public Object find(final String hql, final Map<String, Object> values) {
		return findAll(hql, values, true);
	}

	@SuppressWarnings("unchecked")
	public Object findAll(final String hql, final Map<String, Object> values, final boolean includeAllResults) {
		Object result = hibernateTemplate.execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws SQLException {
				Query q = session.createQuery(hql);
				q.setCacheable(true);
				if (values != null) {
					for (String param : values.keySet()) {
						q.setParameter(param, values.get(param));
					}
				}
				if (includeAllResults) {
					return q.list();
				} else {
					return q.uniqueResult();
				}
			}
		});

		return result;
	}

	@SuppressWarnings("unchecked")
	public List<Playlist> findAllUntitled() {
		List<Playlist> playlists = (List<Playlist>) hibernateTemplate.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session sess) throws SQLException {
				Criteria crit = sess.createCriteria(LocalPlaylist.class);
				crit.add(Restrictions.ilike("name", "%Untitled Playlist%"));
				crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
				return crit.list();
			}
		});
		return playlists;
	}

	@SuppressWarnings("unchecked")
	public List<Track> findByKeywordInName(final String keyword) {
		return (List<Track>) hibernateTemplate.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session sess) throws SQLException {
				Criteria crit = sess.createCriteria(Track.class);
				crit.add(Restrictions.ilike("name", keyword));
				return crit.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<Track> findMostPlayed() {
		String query = "from Track t where t.playcount>0 order by t.playcount desc";
		return (List<Track>) hibernateTemplate.executeFind(new TrackHibernateCallback(query));
	}

	@SuppressWarnings("unchecked")
	public List<Track> findRecentlyAdded() {
		String query = "from Track t order by t.dateAdded desc";
		return (List<Track>) hibernateTemplate.executeFind(new TrackHibernateCallback(query));
	}

	@SuppressWarnings("unchecked")
	public List<Track> findRecentlyPlayed() {
		String query = "from Track t where t.playcount>0 order by t.lastPlayed desc";
		return (List<Track>) hibernateTemplate.executeFind(new TrackHibernateCallback(query));
	}

	@SuppressWarnings("unchecked")
	public List<PlaylistTrack> findPlaylistTracks(final Track track) {
		return hibernateTemplate.executeFind(new HibernateCallback<List<PlaylistTrack>>() {
			@Override
			public List<PlaylistTrack> doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(PlaylistTrack.class);
				criteria.add(Restrictions.eq("track", track));
				return (List<PlaylistTrack>) criteria.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public Download findDownloadByTrackId(String trackId) {
		List<Download> downloads = hibernateTemplate.find("from Download where trackId='" + trackId + "'");
		return downloads.isEmpty() ? null : downloads.get(0);
	}

	public void deleteAll(Class<?> clazz) {
		hibernateTemplate.deleteAll(loadAll(clazz));
	}

	/**
	 * Do not use HQL, OQL or SQL for saving, deleting or updating because the
	 * DaoNotifier won't be able to catch that events, and sync will be in trouble
	 * and finally having unstable libraries.
	 * 
	 * @TODO This case is needed for update only one playlisttrack and not the
	 *       hole playlisttrack which belongs to a specific playlist and there is
	 *       no problem because playlisttrack is not syncAble.
	 */
	@SuppressWarnings("unchecked")
	public int executeSqlUpdate(final String sql, final Map<String, Object> values) {
		return (Integer) hibernateTemplate.execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws SQLException {
				Query q = session.createSQLQuery(sql);
				if (values != null) {
					for (String param : values.keySet()) {
						q.setParameter(param, values.get(param));
					}
				}
				return q.executeUpdate();
			}
		});
	}

	public void flush() {
		this.hibernateTemplate.flush();
	}

	public void close() {
		flush();
		hibernateTemplate.getSessionFactory().close();
		try {
			dataSource.getConnection().close();
		} catch (SQLException ignore) {
			log.warn(ignore, ignore);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findByExample(T clazz) {
		return (List<T>) hibernateTemplate.findByExample(clazz);
	}

	@SuppressWarnings("unchecked")
	public Object deleteSyncEventEntities(Long timestamp) {
		final String sql = "delete from SyncEventEntity where timestamp = " + timestamp;
		return hibernateTemplate.execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws SQLException {
				return session.createSQLQuery(sql).executeUpdate();
			}
		});
	}

	@Override
	public void saveAll(final List<?> values) {
		try {
			hibernateTemplate.execute(new HibernateCallback<Void>() {
				@Override
				public Void doInHibernate(Session session) throws SQLException {
					for (Object value : values) {
						session.save(value);
					}
					return null;
				}
			});
		} catch (Exception e) {
			log.error(e, e);
		}

	}

	@SuppressWarnings("unchecked")
	public List<Track> findRecentlyDownloaded() {
		String query = "from Track t where t.dateDownloaded is not NULL order by t.dateDownloaded desc";
		return (List<Track>) hibernateTemplate.executeFind(new TrackHibernateCallback(query));
	}
	
	public LocalTrack findByUrnSha1(final String hashcode) {
		return hibernateTemplate.execute(new HibernateCallback<LocalTrack>(){
			@Override
			public LocalTrack doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(LocalTrack.class);
				criteria.add(Restrictions.like("downloadString", "%urnsha1="+hashcode+"%"));
				return (LocalTrack) criteria.uniqueResult();
			}
		});
	}


}
