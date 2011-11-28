package com.all.appControl.control;

import com.all.appControl.ControlEngineConfigurator;
import com.all.appControl.ViewEngineConfigurator;

public class TestEngine extends DefaultEngine {
	private ControlEngineConfigurator controlConfigurator;
	private ViewEngineConfigurator viewConfigurator;

	public void setup(Object object) {
		getControlConfigurator().setupControlEngine(object);
		getViewConfigurator().setupViewEngine(object);
	}

	public void reset(Object object) {
		getViewConfigurator().resetViewEngine(object);
		getControlConfigurator().resetControlEngine(object);
	}

	private ControlEngineConfigurator getControlConfigurator() {
		if (controlConfigurator == null) {
			controlConfigurator = new ControlEngineConfigurator(this);
		}
		return controlConfigurator;
	}

	private ViewEngineConfigurator getViewConfigurator() {
		if (viewConfigurator == null) {
			viewConfigurator = new ViewEngineConfigurator(this);
		}
		return viewConfigurator;
	}

	public TestEngine() {
		super(new TestActionDelegate(), new TestEventDelegate(), new TestModelDelegate());
		start();
	}
}
