package com.all.shared.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface Category extends Serializable {

	public Date getCreatedOn();

	public String getDescription();

	public long getId();

	public Date getModifiedOn();

	public String getName();

	public List<Playlist> getPlaylists();

}
