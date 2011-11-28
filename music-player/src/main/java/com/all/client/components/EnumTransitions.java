package com.all.client.components;

import java.util.ArrayList;
import java.util.List;

public class EnumTransitions<T extends Enum<?>> {
	private final List<EnumTransition<T>> transitions = new ArrayList<EnumTransition<T>>();

	public EnumTransitions() {
	}

	public boolean isValid(T origin, T destination) {
		for (EnumTransition<T> transition : transitions) {
			if (transition.isValid(origin, destination)) {
				return true;
			}
		}
		return false;
	}

	public void add(T origin, T... destination) {
		transitions.add(new EnumTransition<T>(origin, destination));
	}
}
