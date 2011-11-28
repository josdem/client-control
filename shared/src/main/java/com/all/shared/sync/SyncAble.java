package com.all.shared.sync;


public interface SyncAble {

	public boolean isSyncAble();

	public void setSyncAble(boolean syncAble);

	public String getSyncAbleId();

	public void clone(SyncAble updatedEntity);
}
