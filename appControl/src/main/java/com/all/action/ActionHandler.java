package com.all.action;

public interface ActionHandler<T extends ActionObject> {
	void handle(T arg);
}
