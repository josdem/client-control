package com.all.shared.json.processors;

import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class DateToLongValueProcessor implements JsonValueProcessor {
	@Override
	public Object processArrayValue(Object arg0, JsonConfig arg1) {
		return null;
	}

	@Override
	public Object processObjectValue(String key, Object object, JsonConfig config) {
		if (object instanceof Date) {
			Date date = (Date) object;
			return date.getTime();
		}
		return null;
	}
}

