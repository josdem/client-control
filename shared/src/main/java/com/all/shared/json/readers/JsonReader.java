package com.all.shared.json.readers;

public interface JsonReader<T> {
	T read(String json);
}
