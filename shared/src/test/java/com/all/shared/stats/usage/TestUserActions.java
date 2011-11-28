package com.all.shared.stats.usage;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class TestUserActions {
	@Test
	public void shouldNotRepeatAnyConstant() throws Exception {
		Set<Integer> values = new HashSet<Integer>();
		checkValues(values, UserActions.class);
	}

	private void checkValues(Set<Integer> values, Class<?> clazz) throws Exception {
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (field.getType().equals(int.class) || field.getType().equals(Integer.class)) {
				int modifiers = field.getModifiers();
				int searchmods = Modifier.FINAL | Modifier.STATIC;
				if ((modifiers & searchmods) == searchmods) {
					String fieldName = clazz.getName() + ">" + field.getName();
					Integer value = (Integer) field.get(null);
					assertNotNull(fieldName + " IS NULL", value);
					assertFalse(fieldName + " Has a duplicate value!", values.contains(value));
					values.add(value);
				}
			}
		}
		Class<?>[] classes = clazz.getClasses();
		for (Class<?> innerClazz : classes) {
			checkValues(values, innerClazz);
		}
	}
}
