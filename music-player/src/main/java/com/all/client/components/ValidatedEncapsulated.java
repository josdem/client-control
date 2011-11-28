package com.all.client.components;

public class ValidatedEncapsulated<T extends Validable<T>> extends Encapsulated<T> {

	public ValidatedEncapsulated() {

	}

	public ValidatedEncapsulated(T value) {
		super(value);
	}

	protected final boolean beforeSet(T oldValue, T value) {
		return get().isValid(value);
	}

}
