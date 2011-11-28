package com.all.shared.sync;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.all.shared.model.SyncEventEntity.SyncOperation;

public class SyncGenericConverter {

	private static final Log LOGGER = LogFactory.getLog(SyncGenericConverter.class);

	private static final List<Class<?>> PRIMITIVES = new ArrayList<Class<?>>();

	public static final String ENTITY = "ENTITY";

	public static final String SYNC_HASHCODE = "SYNC_HASHCODE";

	static {
		PRIMITIVES.add(Byte.class);
		PRIMITIVES.add(Short.class);
		PRIMITIVES.add(Integer.class);
		PRIMITIVES.add(Long.class);
		PRIMITIVES.add(Float.class);
		PRIMITIVES.add(Double.class);
		PRIMITIVES.add(Boolean.class);
		PRIMITIVES.add(Character.class);
		PRIMITIVES.add(String.class);
	}

	public static <T> HashMap<String, Object> toMap(T clazz, SyncOperation op) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put(ENTITY, clazz.getClass().getSimpleName());
		for (Field field : clazz.getClass().getDeclaredFields()) {
			String name = field.getName();
			if (PropertyUtils.isReadable(clazz, name)) {
				try {
					Object attribute = PropertyUtils.getProperty(clazz, name);
					if (!field.isAnnotationPresent(Transient.class)) {
						switch (op) {
						case SAVE:
							if (!(attribute instanceof Collection<?>)) {
								map.put(name, attribute instanceof Date ? ((Date) attribute).getTime()
										: attribute instanceof SyncAble ? ((SyncAble) attribute).getSyncAbleId() : attribute);
							}
							break;
						case UPDATE:
							if (field.isAnnotationPresent(SyncUpdateAble.class)) {
								map.put(name, attribute instanceof Date ? ((Date) attribute).getTime()
										: attribute instanceof SyncAble ? ((SyncAble) attribute).getSyncAbleId() : attribute);
							}
							break;
						}
						if (field.isAnnotationPresent(Id.class)) {
							map.put(SYNC_HASHCODE, attribute);
						}
					}
				} catch (Exception e) {
					LOGGER.error("Could not execute method for attribute : " + name);
				}
			}
		}
		return map;
	}

	public static <T> T toBean(Map<String, Object> attributes, Class<T> clazz) {
		return fillInstance(createInstance(clazz), attributes);
	}

	private static <T> T fillInstance(T instance, Map<String, Object> attributes) {
		if (instance != null) {
			for (String attribute : attributes.keySet()) {
				if (PropertyUtils.isWriteable(instance, attribute)) {
					try {
						Class<?> type = PropertyUtils.getPropertyType(instance, attribute);
						if (instance instanceof ComplexSyncAble) {
							ComplexSyncAble postSyncAble = (ComplexSyncAble) instance;
							if (postSyncAble.requiresPostProcessing(attribute)) {
								continue;
							}
						}
						Object value = readValue(attribute, type, attributes);
						PropertyUtils.setProperty(instance, attribute, value);
					} catch (Exception e) {
						LOGGER.error("Could not fill attribute " + attribute + " to instance of type " + instance.getClass()
								+ ". This attribute will be ignored.", e);
					}
				}
			}
		}
		return instance;
	}

	private static Object readValue(String attributeName, Class<?> clazz, Map<String, Object> map)
			throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		Object value = map.get(attributeName);
		if (value != null && !clazz.equals(value.getClass())) {
			if (clazz.equals(Date.class) && StringUtils.isNumeric(value.toString())) {
				return new Date(new Long(value.toString()));
			}
			if (clazz.isEnum() && value instanceof String) {
				for (Object enumType : clazz.getEnumConstants()) {
					if (value.equals(enumType.toString())) {
						return enumType;
					}
				}
			}
			if (PRIMITIVES.contains(clazz)) {
				return clazz.getConstructor(String.class).newInstance(value.toString());
			}
		}
		return value;
	}

	private static <T> T createInstance(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException e1) {
			LOGGER.error("Could not instantiate object of class " + clazz, e1);
		} catch (IllegalAccessException e1) {
			LOGGER.error("Could not instantiate object of class " + clazz + " due to access restrictions.", e1);
		} catch (Exception e) {
			LOGGER.error("Unexpected exception trying to create instance.", e);
		}
		return null;
	}
}
