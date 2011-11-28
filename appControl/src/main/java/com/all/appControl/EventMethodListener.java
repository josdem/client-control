package com.all.appControl;

import java.lang.reflect.Method;

import com.all.event.EventExecutionMode;
import com.all.event.EventObject;
import com.all.event.Listener;
import com.all.event.ValueEvent;

public class EventMethodListener extends MethodInvoker implements Listener<EventObject> {
	private final EventExecutionMode eventMode;
	private final boolean eager;

	public EventMethodListener(Object object, Method method, EventExecutionMode eventMode, boolean eager) {
		super(object, method);
		if (method.getParameterTypes().length > 1) {
			throw new IllegalMethodException("Illegal Engine binding on: " + object.getClass().getName() + "." + method.getName());
		}
		this.eventMode = eventMode;
		this.eager = eager;
	}

	@Override
	public void handleEvent(EventObject eventArgs) {
		if (eventArgs instanceof ValueEvent) {
			Object o = ((ValueEvent<?>) eventArgs).getValue();
			if (canInvoke(o)) {
				invoke(o);
				return;
			}
		}
		invoke(eventArgs);
	}

	@Override
	public EventExecutionMode getMode() {
		return eventMode;
	}

	@Override
	public boolean isEager() {
		return eager;
	}
}
