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

import com.all.action.ActionType;
import com.all.core.actions.Actions;

public class TestActions extends EngineConfigurationTester {
	private Set<String> modelIds = new HashSet<String>();
	private Set<String> actionIds = new HashSet<String>();
	private Set<ActionType<?>> actionTypes = new HashSet<ActionType<?>>();

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
		List<Field> allConstantFields = getAllConstantFields(String.class, Actions.class);
		for (Field field : allConstantFields) {
			String id = field.get(null).toString();
			assertFalse("REPEATED ID IN MODEL: " + id + " <" + field.getName() + ">", modelIds.contains(id));
			assertFalse("REPEATED ID: " + id + " <" + field.getName() + ">", actionIds.contains(id));
			actionIds.add(id);
		}
	}

	@Test
	public void shouldTestTypes() throws Exception {
		List<Field> allConstantFields = getAllConstantFields(ActionType.class, Actions.class);
		for (Field field : allConstantFields) {
			ActionType<?> type = (ActionType<?>) field.get(null);
			assertFalse("REPEATED TYPE: " + type + " <" + field.getName() + ">", actionTypes.contains(type));
			actionTypes.add(type);
		}
	}

	@Test
	public void shouldTestRelationship() throws Exception {
		shouldTestStringIdUniquity();
		shouldTestTypes();
		for (Iterator<ActionType<?>> iter = actionTypes.iterator(); iter.hasNext();) {
			ActionType<?> type = iter.next();
			if (modelIds.remove(type.getId())) {
				iter.remove();
			}
			if (actionIds.remove(type.getId())) {
				iter.remove();
			}
		}
		assertTrue("Not all actions are bound to an id:" + actionTypes, actionTypes.isEmpty());
		assertTrue("Not all ids are bound to an id:" + actionIds, actionIds.isEmpty());
	}
}
