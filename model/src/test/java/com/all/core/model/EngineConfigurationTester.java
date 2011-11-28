package com.all.core.model;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public abstract class EngineConfigurationTester {
	public List<Field> getAllConstantFields(Class<?> targetClass, Class<?> clazz) {
		List<Field> fields = new ArrayList<Field>();
		getAllFields(fields, targetClass, clazz);
		return fields;
	}

	private void getAllFields(List<Field> fields, Class<?> targetClass, Class<?> clazz) {
		if (clazz == null) {
			return;
		}
		Field[] declaredFields = clazz.getDeclaredFields();
		for (Field field : declaredFields) {
			if (Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers()) && Modifier.isPublic(field.getModifiers())
					&& field.getType().equals(targetClass)) {
				fields.add(field);
			}
		}
		Class<?>[] declaredClasses = clazz.getDeclaredClasses();
		for (Class<?> subclazz : declaredClasses) {
			getAllFields(fields, targetClass, subclazz);
		}
		getAllConstantFields(targetClass, clazz.getSuperclass());
	}
}
