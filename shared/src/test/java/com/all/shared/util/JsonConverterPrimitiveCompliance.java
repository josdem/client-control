package com.all.shared.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Ignore;

import com.all.shared.json.JsonConverter;

@Ignore
public class JsonConverterPrimitiveCompliance {

	private final Class<?> clazz;

	private enum Primitives {

		Byte(new java.lang.Byte[] { 0XF, 0X0 }), Integer(new java.lang.Integer[] { 1, 2, 3 }), Long(new java.lang.Long[] {
				4L, 5L, 6L }), Float(new java.lang.Float[] { 1F, 2F, 3F }), Double(new java.lang.Double[] { 4.0, 5.0, 6.0 }), Boolean(
				new java.lang.Boolean[] { java.lang.Boolean.TRUE, java.lang.Boolean.FALSE }), Character(
				new java.lang.Character[] { 'a', 'b', 'c' }), String(new String[] { "This", "are", "values." }), Int(
				new java.lang.Integer[] { 1, 2, 3 });

		private final Object[] testValues;

		private Primitives(Object[] testValues) {
			this.testValues = testValues;

		}

		public Object[] testValues() {
			return testValues;
		}

	}

	private JsonConverterPrimitiveCompliance(Class<?> clazz) {
		this.clazz = clazz;
	}

	public static JsonConverterPrimitiveCompliance forClass(Class<?> clazz) {
		return new JsonConverterPrimitiveCompliance(clazz);
	}

	public void verify() throws IllegalArgumentException, SecurityException, InstantiationException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Object instance = clazz.getConstructor().newInstance();
		assertNotNull(serializeToJson(instance));
		List<Method> setters = findSetters();
		List<Method> getters = verifyGetters(instance, setters);
		Object actual = serializeToJson(instance);
		for (Method getter : getters) {
			assertEquals(getter.invoke(instance), getter.invoke(actual));
		}
		assertNotNull(instance.toString());
	}

	private Object serializeToJson(Object o) {
		String json = JsonConverter.toJson(o);
		Object actual = JsonConverter.toBean(json, clazz);
		return actual;
	}

	private List<Method> verifyGetters(Object o, List<Method> setters) throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		List<Method> getters = new ArrayList<Method>();
		for (Method setter : setters) {
			String property = setter.getName().substring("set".length());
			Method getter = null;
			try {
				getter = clazz.getDeclaredMethod("get" + property);
			} catch (Exception e) {
				assertTrue("No getter found for property " + property, setter.getParameterTypes()[0].getName().contains(
						"oolean"));
				getter = clazz.getDeclaredMethod("is" + property);
			}
			getters.add(getter);
			checkGetterAndSetter(getter, setter, o);
		}
		return getters;
	}

	private List<Method> findSetters() {
		List<Method> setters = new ArrayList<Method>();
		for (Method method : clazz.getDeclaredMethods()) {
			if (isSetter(method)) {
				setters.add(method);
			}
		}
		return setters;
	}

	public void checkGetterAndSetter(Method getter, Method setter, Object o) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		Class<?> type = getter.getReturnType();
		String primitiveClazz = capitalize(type.getSimpleName());
		for (Object value : Primitives.valueOf(primitiveClazz).testValues()) {
			checkGetterAndSetter(getter, setter, o, value);
		}

	}

	private String capitalize(String str) {
		if (StringUtils.isEmpty(str)) {
			return str;
		}
		return str.toUpperCase().charAt(0) + str.substring(1);
	}

	public void checkGetterAndSetter(Method getter, Method setter, Object o, Object value)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		setter.invoke(o, value);
		assertEquals(value, getter.invoke(o));
	}

	private static boolean isSetter(Method method) {
		if (!method.getName().startsWith("set")) {
			return false;
		}
		if (method.getParameterTypes().length != 1) {
			return false;
		}
		if (!void.class.equals(method.getReturnType())) {
			return false;
		}
		return true;
	}

}
