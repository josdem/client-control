package com.all.appControl.control;

import com.all.action.ActionHandler;
import com.all.action.ActionObject;
import com.all.action.ActionType;
import com.all.event.EmptyEvent;
import com.all.event.EventObject;
import com.all.event.EventType;
import com.all.event.ValueEvent;
import com.all.model.ModelType;

public interface ControlEngine {
	void fireEvent(EventType<EmptyEvent> type);

	<T extends EventObject> void fireEvent(EventType<T> type, T argument);

	<T> void fireValueEvent(EventType<ValueEvent<T>> type, T argument);

	void start();

	void stop();

	<T> T get(ModelType<T> type);

	<T> void set(ModelType<T> type, T value, EventType<ValueEvent<T>> event);

	<T extends ActionObject> void addActionHandler(ActionType<T> actionType, ActionHandler<T> actionHandler);

	<T extends ActionObject> void removeActionHandler(ActionType<T> action);

}
