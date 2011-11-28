package com.all.observ;

public interface Observer<T extends ObserveObject> {
	public void observe(T t);

}
