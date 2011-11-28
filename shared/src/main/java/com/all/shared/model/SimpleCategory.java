package com.all.shared.model;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class SimpleCategory implements Category {
	private static final long serialVersionUID = 1L;
	private final Date createdOn;
	private final String description;
	private final long id;
	private final Date modifiedOn;
	private final String name;

	public SimpleCategory(Category category) {
		createdOn = category.getCreatedOn();
		description = category.getDescription();
		id = category.getId();
		modifiedOn = category.getModifiedOn();
		name = category.getName();
	}

	@Override
	public Date getCreatedOn() {
		return createdOn;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public Date getModifiedOn() {
		return modifiedOn;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<Playlist> getPlaylists() {
		return Collections.emptyList();
	}

}
