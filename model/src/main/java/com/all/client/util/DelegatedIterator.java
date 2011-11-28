package com.all.client.util;

import java.util.Iterator;

public class DelegatedIterator<T, U extends T> implements Iterator<U> {

	private final Iterator<U> iterator;
	private final IteratorDelegate<T> delegate;
	protected U currentElement;

	public DelegatedIterator(Iterator<U> iterator, IteratorDelegate<T> delegate) {
		this.iterator = iterator;
		this.delegate = delegate;
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public U next() {
		currentElement = iterator.next();
		return currentElement;
	}

	@Override
	public void remove() {
		delegate.remove(currentElement);
	}

}
