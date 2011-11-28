package com.all.client.util;

import java.util.Iterator;

public class DelegatedIterable<T, U extends T> implements Iterable<U> {
	private final Iterable<U> iterable;
	private final IteratorDelegate<T> delegate;

	public DelegatedIterable(Iterable<U> iterable, IteratorDelegate<T> delegate) {
		this.iterable = iterable;
		this.delegate = delegate;

	}

	@Override
	public Iterator<U> iterator() {
		return new DelegatedIterator<T, U>(iterable.iterator(), delegate);
	}

}
