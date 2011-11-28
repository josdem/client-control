package com.all.shared.json;

import org.bouncycastle.util.encoders.Base64;

import net.sf.ezmorph.ObjectMorpher;
import net.sf.json.JSONString;

public class ByteArrayJsonMorpher implements ObjectMorpher {

	@Override
	public Class<?> morphsTo() {
		return byte[].class;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
		return String.class.isAssignableFrom(clazz) || JSONString.class.isAssignableFrom(clazz);
	}

	@Override
	public Object morph(Object value) {
		if (value instanceof String) {
			return Base64.decode(value.toString().getBytes());
		}
		if (value instanceof JSONString) {
			return Base64.decode(((JSONString) value).toString().getBytes());
		}
		return null;
	}

}
