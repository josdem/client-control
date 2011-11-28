package com.all.client.components;

public class Encapsulated<T> {
	private T value;

	public Encapsulated() {
	}

	public Encapsulated(T value) {
		this.value = value;
	}

	public final T get() {
		return value;
	}

	public final boolean set(T value) {
		if (beforeSet(this.value, value)) {
			this.value = value;
			afterSet(value);
			return true;
		}
		return false;
	}

	protected boolean beforeSet(T oldValue, T newValue) {
		return true;
	}

	protected void afterSet(T value) {
	}

	public boolean is(T other) {
		return value == other;
	}

	public boolean is(T... others) {
		for (T t : others) {
			if (is(t)) {
				return true;
			}
		}
		return false;
	}

}
