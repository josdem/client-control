package com.all.shared.json;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import org.apache.commons.codec.binary.Base64;

public class ByteArrayJsonValueProcessor implements JsonValueProcessor {

	@Override
	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		JSONArray jsonArray = new JSONArray();
		Object[] obj = (Object[]) value;
		for (Object object : obj) {
			jsonArray.add(object);
		}
		return jsonArray;
	}

	@Override
	public Object processObjectValue(String key, Object object, JsonConfig config) {
		return new String(Base64.encodeBase64((byte[]) object));
	}

}
