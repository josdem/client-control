package com.all.core.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.all.core.events.Events;
import com.all.event.EventType;

public class TestEvents extends EngineConfigurationTester {
	private Set<String> modelIds = new HashSet<String>();
	private Set<String> eventIds = new HashSet<String>();
	private Set<EventType<?>> eventTypes = new HashSet<EventType<?>>();

	@Before
	public void setup() throws Exception {
		Class<Model> modelClass = Model.class;
		List<Field> allConstantFields = getAllConstantFields(String.class, modelClass);
		for (Field field : allConstantFields) {
			String id = field.get(null).toString();
			assertFalse("PROBLEM IN MODEL CHECK THAT FIRST! ", modelIds.contains(id));
			modelIds.add(id);
		}
	}

	@Test
	public void shouldTestStringIdUniquity() throws Exception {
		List<Field> allConstantFields = getAllConstantFields(String.class, Events.class);
		for (Field field : allConstantFields) {
			String id = field.get(null).toString();
			assertFalse("REPEATED ID IN MODEL: " + id + " <" + field.getName() + ">", modelIds.contains(id));
			assertFalse("REPEATED ID: " + id + " <" + field.getName() + ">", eventIds.contains(id));
			eventIds.add(id);
		}
	}

	@Test
	public void shouldTestTypes() throws Exception {
		List<Field> allConstantFields = getAllConstantFields(EventType.class, Events.class);
		for (Field field : allConstantFields) {
			EventType<?> type = (EventType<?>) field.get(null);
			assertFalse("REPEATED TYPE: " + type + " <" + field.getName() + ">", eventTypes.contains(type));
			eventTypes.add(type);
		}
	}

	@Test
	public void shouldTestRelationship() throws Exception {
		shouldTestStringIdUniquity();
		shouldTestTypes();
		for (Iterator<EventType<?>> iter = eventTypes.iterator(); iter.hasNext();) {
			EventType<?> type = iter.next();
			if (modelIds.remove(type.getId())) {
				iter.remove();
			}
			if (eventIds.remove(type.getId())) {
				iter.remove();
			}
		}
		assertTrue("Not all events are bound to an id:" + eventTypes, eventTypes.isEmpty());
		assertTrue("Not all ids are bound to an id:" + eventIds, eventIds.isEmpty());
	}
}
