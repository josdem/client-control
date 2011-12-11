package com.all.command;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Test;

import com.all.action.ActionHandler;
import com.all.action.ActionObject;
import com.all.action.ActionType;
import com.all.action.DuplicateActionHandlerException;
import com.all.appControl.control.DefaultActionDelegate;
import com.all.appControl.control.TestActionDelegate;

public class TestDefaultCommandEngine {
	public static final ActionType<DemoCommandObject> cType = new ActionType<DemoCommandObject>("c");

	@Test
	public void testeamesta() throws Exception {
		DefaultActionDelegate engine = new TestActionDelegate();
		final AtomicBoolean handled = new AtomicBoolean(false);

		engine.addActionHandler(cType, new ActionHandler<DemoCommandObject>() {
			@Override
			public void handle(DemoCommandObject arg) {
				handled.set(true);
			}
		});
		engine.send(cType.create(new DemoCommandObject()));
		assertTrue(handled.get());
	}

	@Test(expected = DuplicateActionHandlerException.class)
	public void shouldNotAddTwoHandlers() throws Exception {
		DefaultActionDelegate engine = new TestActionDelegate();

		engine.addActionHandler(cType, new ActionHandler<DemoCommandObject>() {
			@Override
			public void handle(DemoCommandObject arg) {
			}
		});
		engine.addActionHandler(cType, new ActionHandler<DemoCommandObject>() {
			@Override
			public void handle(DemoCommandObject arg) {
			}
		});
	}
}

class DemoCommandObject extends ActionObject {
}
