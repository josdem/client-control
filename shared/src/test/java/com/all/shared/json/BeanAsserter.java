package com.all.shared.json;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Ignore;

@Ignore
public class BeanAsserter {
	private Object original;
	private Object other;
	private Map<String, BeanAsserter> inDeep = new HashMap<String, BeanAsserter>();
	private Set<String> ignore = new HashSet<String>();
	private String name;

	public BeanAsserter() {
		ignore.add("hashCode");
		ignore.add("toString");
	}

	public BeanAsserter(String name) {
		this();
		this.name = name;
	}

	public BeanAsserter(Object original, Object other) {
		this();
		this.name = original.getClass().getName();
		this.original = original;
		this.other = other;
	}

	public BeanAsserter setName(String name) {
		this.name = name;
		return this;
	}

	public void assertEquals(Object original, Object other) throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		this.original = original;
		this.other = other;
		assertEquals();
	}

	public void assertEquals() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		assertNotNull(name + " original is null!!!", original);
		assertNotNull(name + " other is null!!!", other);
		assertTrue(name + " Not equal classes BAD", original.getClass().equals(other.getClass()));
		Set<Method> methods = getAllMethods(original.getClass());
		for (Method method : methods) {
			String methodName = method.getName();
			if (ignore.contains(methodName)) {
				continue;
			}
			Object resultOriginal = method.invoke(original);
			Object resultOther = method.invoke(other);
			if (inDeep.containsKey(methodName)) {
				BeanAsserter beanAsserter = inDeep.get(methodName);
				beanAsserter.assertEquals(resultOriginal, resultOther);
			}
			String message = name + ">" + methodName + " " + resultOriginal + " " + resultOther;
			Assert.assertEquals(message, resultOriginal, resultOther);
		}
	}

	private Set<Method> getAllMethods(Class<?> clazz) {
		return addMethods(clazz, new HashSet<Method>());
	}

	private Set<Method> addMethods(Class<?> clazz, HashSet<Method> allMethods) {
		if (clazz != null) {
			Method[] methods = clazz.getDeclaredMethods();
			for (Method method : methods) {
				if (method.getParameterTypes().length == 0 && Modifier.isPublic(method.getModifiers())
						&& !method.getReturnType().equals(void.class)) {
					allMethods.add(method);
				}
			}
			addMethods(clazz.getSuperclass(), allMethods);
		}
		return allMethods;
	}

	public BeanAsserter inDeep(String method, BeanAsserter asserter) {
		inDeep.put(method, asserter);
		return this;
	}

	public BeanAsserter ignore(String... methods) {
		for (String method : methods) {
			ignore.add(method);
		}
		return this;
	}
}
