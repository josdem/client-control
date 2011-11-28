package com.all.client.data;

import java.io.Serializable;
import java.util.List;

import com.all.shared.model.ModelDao;
import com.all.shared.model.Track;

public final class EmptyDao implements Serializable, ModelDao {

	private static final long serialVersionUID = 2205099148074321950L;
	private static EmptyDao emptyDao;

	private EmptyDao() {
	}

	public static EmptyDao getInstance() {
		if (emptyDao == null) {
			emptyDao = new EmptyDao();
		}
		return emptyDao;
	}

	@Override
	public <T> T delete(T object) {
		return object;
	}

	@Override
	public <T> T merge(T object) {
		return object;
	}

	@Override
	public <T> void refresh(T object) {
	}

	@Override
	public <T> T update(T object) {
		return object;
	}

	@Override
	public <T> T save(T object) {
		return object;
	}

	@Override
	public <T> T findById(Class<T> objectClass, Serializable id) {
		return null;
	}

	@Override
	public <T> List<T> findAll(Class<T> objectClass) {
		return null;
	}

	@Override
	public <T> T saveOrUpdate(T object) {
		return null;
	}

	@Override
	public List<Track> findTracks(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> findByExample(T clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> loadAll(Class<T> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveAll(List<?> values) {
		// TODO Auto-generated method stub

	}

}
