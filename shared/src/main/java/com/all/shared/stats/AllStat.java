package com.all.shared.stats;

import java.io.Serializable;

import com.all.shared.model.TypedClass;

public interface AllStat extends TypedClass<AllStat> {

	public Serializable getId();

	public long getTimestamp();

	public void setTimestamp(long timestamp);

	public Class<? extends AllStat> getStatType();

	public String toString();

	public String getEmail();
}
