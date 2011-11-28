package com.all.client.components;

public interface Validable<T> {
	boolean isValid(T newValue);
}
