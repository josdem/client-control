package com.all.appControl.control;

import com.all.action.Action;
import com.all.action.ActionHandler;
import com.all.action.ActionObject;
import com.all.action.ActionType;
import com.all.action.RequestAction;
import com.all.action.ResponseCallback;

public interface ActionDelegate {

	void send(Action<?> command);

	<V, T> void request(ActionType<RequestAction<V, T>> type, V requestParameter, ResponseCallback<T> callback);

	<T extends ActionObject> void addActionHandler(ActionType<T> actionType, ActionHandler<T> actionHandler);

	<T extends ActionObject> void removeActionHandler(ActionType<T> action);

	void start();

	void stop();

}
