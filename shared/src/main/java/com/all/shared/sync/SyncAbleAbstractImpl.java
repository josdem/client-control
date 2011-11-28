package com.all.shared.sync;

public abstract class SyncAbleAbstractImpl implements SyncAble {

	private boolean syncAble = true;

	public boolean isSyncAble() {
		return syncAble;
	}

	public void setSyncAble(boolean syncAble) {
		this.syncAble = syncAble;
	}

}
