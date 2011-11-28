package com.all.appControl.control;

import com.all.action.Action;
import com.all.action.ActionObject;
import com.all.action.ActionProcessor;

public interface ActionRunnerFactory {
	public <T extends ActionObject> ActionRunnable createRunner(ActionProcessor<T> commandProcessor, Action<T> command);

}
