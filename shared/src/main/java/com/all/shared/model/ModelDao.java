package com.all.shared.model;


import java.io.Serializable;
import java.util.List;


public interface ModelDao {
	<T> T merge(T object);

	<T> T update(T object);

	<T> T delete(T object);

	<T> void refresh(T object);

	<T> T save(T object);

	<T> T saveOrUpdate(T object);

	<T> T findById(Class<T> objectClass, Serializable id);

	<T> List<T> findAll(Class<T> objectClass);

	List<Track> findTracks(String query);

	<T> List<T> findByExample(T clazz);
	
	<T> List<T> loadAll(Class<T> clazz);

	void saveAll(final List<?> values);
	
}
