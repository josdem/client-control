package com.all.shared.model;

import java.io.Serializable;
import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class SyncEventEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Enumerated(EnumType.ORDINAL)
	private SyncOperation operation;
	@Lob
	private HashMap<String, Object> entity;
	private Long timestamp;

	public SyncEventEntity(SyncOperation syncOperation, HashMap<String, Object> entity) {
		this.operation = syncOperation;
		this.setEntity(entity);
	}

	@Deprecated
	// used only by hibernate
	public SyncEventEntity() {
	}

	public SyncOperation getOperation() {
		return operation;
	}

	public void setOperation(SyncOperation operation) {
		this.operation = operation;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setEntity(HashMap<String, Object> entity) {
		this.entity = entity;
	}

	public HashMap<String, Object> getEntity() {
		return entity;
	}

	public enum SyncOperation {
		SAVE, UPDATE, DELETE;
	}

}
