package com.all.shared.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.all.messengine.Message;

public class AllMessage<T> implements Message<T> {

	private static final long serialVersionUID = 1L;

	private Map<String, String> properties = new HashMap<String, String>();

	private String type;

	private T body;

	private Class<?> contentType;

	public AllMessage(String type, T body) {
		this.type = type;
		if (body != null) {
			this.body = body;
			checkForContentType();
		}
	}

	private void checkForContentType() {
		checkForCollection();
		checkForMap();
	}

	private void checkForMap() {
		if (Map.class.isAssignableFrom(extractType(body))) {
			@SuppressWarnings("unchecked")
			Map<String, ?> map = (Map<String, ?>) this.body;
			if (!map.isEmpty()) {
				Iterator<?> iterator = map.values().iterator();
				Object value = null;
				if (iterator.hasNext()) {
					value = iterator.next();
				}
				contentType = extractType(value);
			}
		}
	}

	private Class<?> extractType(Object value) {
		if (value != null && value instanceof TypedClass<?>) {
			return ((TypedClass<?>) value).getTypedClass();
		} else {
			return value != null ? value.getClass() : Serializable.class;
		}
	}

	private void checkForCollection() {
		if (Collection.class.isAssignableFrom(extractType(body))) {
			Collection<?> collection = (Collection<?>) this.body;
			if (!collection.isEmpty()) {
				contentType = extractType(collection.iterator().next());
			} else {
				contentType = Serializable.class;
			}
		}
	}

	@Override
	public T getBody() {
		return body;
	}

	@Override
	public String getProperty(String key) {
		return properties.get(key);
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void putProperty(String key, String value) {
		properties.put(key, value);
	}

	public Map<String, String> getProperties() {
		return new HashMap<String, String>(properties);
	}

	@SuppressWarnings("unchecked")
	public Class<T> getBodyType() {
		return (Class<T>) extractType(body);
	}

	public Class<?> getContentType() {
		return contentType;
	}

}
