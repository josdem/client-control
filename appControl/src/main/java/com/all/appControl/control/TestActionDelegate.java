package com.all.appControl.control;

public class TestActionDelegate extends DefaultActionDelegate {

	public TestActionDelegate() {
		super(new TestingExecutorService(), new TestActionRunnerFactory());
	}
}
