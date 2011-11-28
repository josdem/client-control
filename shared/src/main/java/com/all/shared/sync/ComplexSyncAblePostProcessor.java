package com.all.shared.sync;

import java.util.Map;

public interface ComplexSyncAblePostProcessor {

	void process(ComplexSyncAble postSyncAble, Map<String, Object> attributes,
			Map<Class<? extends SyncAble>, Map<String, ? extends SyncAble>> cachedEntities);

}
