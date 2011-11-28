package com.all.appControl;

import java.lang.reflect.Method;
import java.util.List;

import com.all.appControl.control.ViewEngine;
import com.all.event.EventMethod;
import com.all.event.EventObject;
import com.all.event.EventType;

public class ViewEngineConfigurator {
	private ViewEngine viewEngine;

	public ViewEngineConfigurator() {
	}

	public ViewEngineConfigurator(ViewEngine viewEngine) {
		setViewEngine(viewEngine);
	}

	public int setupViewEngine(Object object) {
		List<Method> eventMethods = AppAnnotationUtils.getMethods(object.getClass(), EventMethod.class);
		if (eventMethods.size() == 0) {
			return 0;
		}
		for (Method method : eventMethods) {
			addEventMethodHandler(object, method);
		}
		return eventMethods.size();
	}

	public int resetViewEngine(Object object) {
		List<Method> eventMethods = AppAnnotationUtils.getMethods(object.getClass(), EventMethod.class);
		if (eventMethods.size() == 0) {
			return 0;
		}
		for (Method method : eventMethods) {
			removeEventMethodHandler(object, method);
		}
		return eventMethods.size();
	}

	private void addEventMethodHandler(Object object, Method method) {
		checkViewEngine();
		EventMethod annotation = method.getAnnotation(EventMethod.class);
		String id = annotation.value();
		EventType<EventObject> eventType = EventType.ev(id);
		EventMethodListener listener = new EventMethodListener(object, method, annotation.mode(), annotation.eager());
		viewEngine.addListener(eventType, listener);
	}

	private void removeEventMethodHandler(Object object, Method method) {
		checkViewEngine();
		EventMethod annotation = method.getAnnotation(EventMethod.class);
		String id = annotation.value();
		EventType<EventObject> eventType = EventType.ev(id);
		EventMethodListener listener = new EventMethodListener(object, method, annotation.mode(), annotation.eager());
		viewEngine.removeListener(eventType, listener);
	}

	private void checkViewEngine() {
		if (viewEngine == null) {
			throw new IllegalStateException("Autoconfigure failed no viewEngine set.");
		}
	}

	public ViewEngine getViewEngine() {
		return viewEngine;
	}

	public void setViewEngine(ViewEngine viewEngine) {
		this.viewEngine = viewEngine;
	}
}
