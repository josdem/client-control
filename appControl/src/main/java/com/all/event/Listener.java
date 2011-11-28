package com.all.event;

public interface Listener<T extends EventObject> {
	void handleEvent(T eventArgs);

	EventExecutionMode getMode();

	boolean isEager();
}
