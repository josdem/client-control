package com.all.client.components;

public enum AllMediaProviderState implements Validable<AllMediaProviderState> {
	STARTING, PLAYING, PAUSED, STOPED, DESTROYED;

	private static EnumTransitions<AllMediaProviderState> transitions;

	static {
		transitions = new EnumTransitions<AllMediaProviderState>();
		transitions.add(STARTING, PLAYING, DESTROYED);
		transitions.add(PLAYING, PAUSED, STOPED, DESTROYED);
		transitions.add(PAUSED, PLAYING, STOPED, DESTROYED);
		transitions.add(STOPED, STARTING, DESTROYED);
	}

	public static boolean isValid(AllMediaProviderState origin, AllMediaProviderState destination) {
		return transitions.isValid(origin, destination);
	}

	@Override
	public boolean isValid(AllMediaProviderState newValue) {
		return isValid(this, newValue);
	}
}