package com.all.client.data;

import com.all.shared.model.Root;

public interface Database<T extends Root> {
	T getRoot();
}
