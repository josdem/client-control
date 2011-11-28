package com.all.shared.stats;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.all.shared.model.Category;
import com.all.shared.model.Playlist;

@Entity
@Table(name = "TOP_HUNDRED_STATS")
@IdClass(TopHundredStatId.class)
public class TopHundredStat extends AbstractAllStat {
	@Id
	private TopHundredStatId id;
	@NotNull
	private long timestamp = -1L;
	@Transient
	private final transient Category category;
	@Transient
	private final transient Playlist playlist;

	@Deprecated
	public TopHundredStat() {
		category = null;
		playlist = null;
	}

	public TopHundredStat(String email, Category category, Playlist playlist) {
		this.category = category;
		this.playlist = playlist;
		id = new TopHundredStatId(category.getId(), playlist.getHashcode(), email);
	}

	@Override
	public Serializable getId() {
		return id;
	}

	public void setId(TopHundredStatId id) {
		this.id = id;
	}

	@Override
	public long getTimestamp() {
		return timestamp;
	}

	@Override
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;

	}

	@Override
	public String getEmail() {
		return id.getEmail();
	}

	public Category category() {
		return category;
	}

	public Playlist playlist() {
		return playlist;
	}

}
