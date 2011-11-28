package com.all.event;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Test;

import com.all.appControl.control.DefaultEventDelegate;

public class TestEvents {
	public static final EventType<CustomEvent> aType = new EventType<CustomEvent>("a");

	@Test
	public void shouldTestEventEngine() throws Exception {
		final AtomicBoolean invoked = new AtomicBoolean(false);

		DefaultEventDelegate engine = new DefaultEventDelegate();

		engine.addListener(aType, new Listener<CustomEvent>() {
			@Override
			public void handleEvent(CustomEvent eventArgs) {
				invoked.set(true);
			}

			@Override
			public EventExecutionMode getMode() {
				return EventExecutionMode.NORMAL;
			}

			@Override
			public boolean isEager() {
				return false;
			}
		});

		engine.fireEvent(aType, new CustomEvent());
		assertTrue(invoked.get());
	}
}

class CustomEvent extends EventObject {
}
