package com.all.appControl.control;

import com.all.event.EventObject;
import com.all.event.EventType;
import com.all.event.Listener;

public interface EventDelegate {

	<T extends EventObject> void removeListener(EventType<T> type, Listener<T> listener);

	<T extends EventObject> void addListener(EventType<T> type, Listener<T> listener);

	<T extends EventObject> void fireEvent(EventType<T> type, T argument);

	<T extends EventObject> void fireLater(EventType<T> type, T argument);

	void start();

	void stop();

}
