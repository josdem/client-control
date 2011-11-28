package com.all.shared.stats;


public abstract class AbstractAllStat implements AllStat {

	@Override
	public Class<? extends AllStat> getStatType() {
		return getClass();
	}

	@Override
	public Class<AllStat> getTypedClass() {
		return AllStat.class;
	}

}
