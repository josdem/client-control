package com.all.appControl.control;


public class TestEventDelegate extends DefaultEventDelegate {
	public TestEventDelegate() {
		super(new TestingExecutorService());
	}
}
