package com.all.shared.json.readers;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.all.shared.newsfeed.FeedType;

public class TestAllFeedJsonReader {
	private Set<Integer> allIntegerConstants;

	@Test
	@SuppressWarnings("unchecked")
	public void shouldHaveAClassTypeForEachFeedTypeIfNotItShouldFailHorribly() throws Exception {
		Class<FeedType> feedType = FeedType.class;
		allIntegerConstants = getAllIntegerConstants(feedType);
		Field field = AllFeedJsonReader.class.getDeclaredField("ALL_FEED_TYPES");
		field.setAccessible(true);
		HashMap<Integer, Class<?>> map = (HashMap<Integer, Class<?>>) field.get(null);
		map = (HashMap<Integer, Class<?>>) map.clone();
		for (Integer i : allIntegerConstants) {
			Class<?> remove = map.remove(i);
			assertNotNull("Feed type " + i
					+ " declared in FeedType does not have a propper declaration on ALL_FEED_TYPES on AllFeedJsonReader", remove);
		}
		assertTrue("Map declares more items than necesary. keys:" + map.keySet(), map.isEmpty());
	}

	private Set<Integer> getAllIntegerConstants(Class<?> clazz) {
		return getAllIntegerConstants(clazz, new HashSet<Integer>());
	}

	private Set<Integer> getAllIntegerConstants(Class<?> clazz, HashSet<Integer> hashSet) {
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (field.getType().equals(int.class)) {
				try {
					field.setAccessible(true);
					hashSet.add((Integer) field.get(null));
				} catch (Exception e) {
				}
			}
		}
		return hashSet;
	}

}
