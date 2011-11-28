package com.all.appControl;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.all.action.ActionType;
import com.all.action.ValueAction;
import com.all.appControl.control.TestEngine;
import com.all.event.EventMethod;
import com.all.event.EventType;
import com.all.event.ValueEvent;

public class TestUnboxing {
	private TestEngine engine = new TestEngine();
	private EventListener ev = new EventListener();
	private ActionListener ac = new ActionListener();

	@Before
	public void setup() {
		engine.setup(ev);
		engine.setup(ac);
	}

	@After
	public void teardown() {
		engine.reset(ev);
		engine.reset(ac);
	}

	@Test
	public void shouldUnboxAction() throws Exception {
		engine.send(new ActionType<ValueAction<String>>("actionUnboxing"), new ValueAction<String>("aaa"));
		assertEquals("aaa", ac.action);
	}

	@Test
	public void shouldUnboxEvent() throws Exception {
		engine.fireEvent(new EventType<ValueEvent<String>>("eventUnboxing"), new ValueEvent<String>("bbb"));
		assertEquals("bbb", ev.event);
	}

}

class EventListener {
	public String event;

	@EventMethod("eventUnboxing")
	public void onEvent(String event) {
		this.event = event;
	}
}

class ActionListener {
	public String action;

	@ActionMethod("actionUnboxing")
	public void onAction(String action) {
		this.action = action;
	}
}