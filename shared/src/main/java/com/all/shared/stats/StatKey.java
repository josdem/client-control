package com.all.shared.stats;

public class StatKey {
	private final Class<? extends BufferedStat> clazz;
	private final Object type;

	public StatKey(Class<? extends BufferedStat> statClass, Object key) {
		clazz = statClass;
		type = key;
	}

	public StatKey(Class<? extends BufferedStat> statClass) {
		clazz = statClass;
		type = null;
	}

	@Override
	public boolean equals(Object obj) {
		StatKey other = (StatKey) obj;
		if (type == null) {
			return clazz.equals(other.clazz);
		}
		return clazz.equals(other.clazz) && type.equals(other.type);
	}

	@Override
	public int hashCode() {
		if (type == null) {
			return clazz.hashCode();
		} else {
			return clazz.hashCode() | type.hashCode();
		}
	}

}
