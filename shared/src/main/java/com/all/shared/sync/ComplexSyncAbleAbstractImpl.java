package com.all.shared.sync;

public abstract class ComplexSyncAbleAbstractImpl extends SyncAbleAbstractImpl implements ComplexSyncAble {

	public final boolean requiresPostProcessing(String attribute) throws SecurityException, NoSuchFieldException {
		return getClass().getDeclaredField(attribute).isAnnotationPresent(ComplexSyncAbleField.class);
	}
}
