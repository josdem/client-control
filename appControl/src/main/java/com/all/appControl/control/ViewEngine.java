package com.all.appControl.control;

import com.all.action.ActionObject;
import com.all.action.ActionType;
import com.all.action.EmptyAction;
import com.all.action.RequestAction;
import com.all.action.ResponseCallback;
import com.all.action.ValueAction;
import com.all.event.EventObject;
import com.all.event.EventType;
import com.all.event.Listener;
import com.all.model.ModelType;

public interface ViewEngine {
	<T extends ActionObject> void send(ActionType<T> actionType, T parameter);

	<T> void sendValueAction(ActionType<ValueAction<T>> action, T argument);

	void send(ActionType<EmptyAction> action);

	<V, T> void request(ActionType<RequestAction<V, T>> type, V requestParameter, ResponseCallback<T> callback);

	<T> void request(ActionType<RequestAction<Void, T>> type, ResponseCallback<T> callback);

	<T> T get(ModelType<T> type);

	<T extends EventObject> void removeListener(EventType<T> currentviewchanged, Listener<T> listener);

	<T extends EventObject> void addListener(EventType<T> currentviewchanged, Listener<T> listener);

}
