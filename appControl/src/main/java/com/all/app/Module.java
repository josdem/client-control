package com.all.app;

public interface Module {
	public void activate();

	public void execute(Attributes attributes);

	public void destroy();
}
