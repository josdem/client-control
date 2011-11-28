package com.all.shared.stats.usage;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.all.shared.stats.AbstractAllStat;
import com.all.shared.stats.IncrementalStat;
import com.all.shared.stats.StatKey;

@Entity
public class UserActionStat extends AbstractAllStat implements IncrementalStat {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private String email;
	@NotNull
	private long timestamp = -1L;
	@NotNull
	private int action;
	@NotNull
	private int times = 1;

	public UserActionStat() {
	}

	@Override
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public Serializable getId() {
		return id;
	}

	@Override
	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	@Override
	public void increment() {
		this.times++;
	}

	@Override
	public String toString() {
		return "UserAction:" + action + ":" + times;
	}

	@Override
	public StatKey key() {
		return new StatKey(getClass(), action);
	}

}
