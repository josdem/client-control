package com.all.client.components;

public class EnumTransition<T extends Enum<?>> {
	T[] destination;
	T origin;

	public EnumTransition(T origin, T... destination) {
		this.origin = origin;
		this.destination = destination;
	}

	public boolean isValid(T origin, T destination) {
		if (origin == this.origin) {
			for (T t : this.destination) {
				if (t == destination) {
					return true;
				}
			}
		}
		return false;
	}
}
