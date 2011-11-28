package com.all.client.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.all.shared.sync.SyncAble;
import com.all.shared.sync.SyncAbleAbstractImpl;
import com.all.shared.sync.SyncUpdateAble;

@Entity
public class BugPatch extends SyncAbleAbstractImpl {

	private static final long serialVersionUID = 1L;
	@Id
	@SyncUpdateAble
	private String id;
	@SyncUpdateAble
	@Lob
	private String description;
	@SyncUpdateAble
	private long timestamp;

	public BugPatch(String id, String description) {
		this.id = id;
		this.description = description;
		this.timestamp = System.currentTimeMillis();
	}

	public BugPatch() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public void clone(SyncAble updatedEntity) {
		this.id = updatedEntity.getSyncAbleId();
		this.description = ((BugPatch) updatedEntity).getDescription();
		this.timestamp = ((BugPatch) updatedEntity).getTimestamp();
	}

	@Override
	public String getSyncAbleId() {
		return getId();
	}
}
