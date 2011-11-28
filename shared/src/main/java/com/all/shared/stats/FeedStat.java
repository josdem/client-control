package com.all.shared.stats;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.all.shared.json.JsonConverter;
import com.all.shared.newsfeed.AllFeed;

@Entity
@Table(name = "FEED_STATS")
public class FeedStat extends AbstractAllStat {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private String email;
	@NotNull
	private long timestamp = System.currentTimeMillis();
	@NotNull
	private int action;
	@NotNull
	private int type;
	@Transient
	private String json;

	@Deprecated
	public FeedStat() {
	}

	public FeedStat(AllFeed feed) {
		email = feed.getOwner().getEmail();
		type = feed.getType();
		json = JsonConverter.toJson(feed);
	}

	@Override
	public Serializable getId() {
		return id;
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
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
