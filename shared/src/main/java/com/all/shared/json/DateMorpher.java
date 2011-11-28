package com.all.shared.json;

import java.util.Date;

import net.sf.ezmorph.ObjectMorpher;

final class DateMorpher implements ObjectMorpher {
	@Override
	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
		return Long.class.isAssignableFrom(clazz) || String.class.isAssignableFrom(clazz);
	}

	@Override
	public Class<?> morphsTo() {
		return Date.class;
	}

	@Override
	public Object morph(Object value) {
		return new Date(Long.valueOf(value.toString()).longValue());
	}
}