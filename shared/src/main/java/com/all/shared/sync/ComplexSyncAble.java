package com.all.shared.sync;

public interface ComplexSyncAble extends SyncAble {

	boolean requiresPostProcessing(String fieldName) throws SecurityException, NoSuchFieldException;

}
