package com.all.core.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.all.model.ModelType;

public class TestModel extends EngineConfigurationTester {
	private Set<String> ids = new HashSet<String>();
	private Set<ModelType<?>> types = new HashSet<ModelType<?>>();

	@Test
	public void shouldTestStringIdUniquity() throws Exception {
		Class<Model> modelClass = Model.class;
		List<Field> allConstantFields = getAllConstantFields(String.class, modelClass);
		for (Field field : allConstantFields) {
			String id = field.get(null).toString();
			assertFalse("REPEATED ID: " + id + " <" + field.getName() + ">", ids.contains(id));
			ids.add(id);
		}
	}

	@Test
	public void shouldTestTypes() throws Exception {
		Class<Model> modelClass = Model.class;
		List<Field> allConstantFields = getAllConstantFields(ModelType.class, modelClass);
		for (Field field : allConstantFields) {
			ModelType<?> type = (ModelType<?>) field.get(null);
			assertFalse("REPEATED TYPE: " + type + " <" + field.getName() + ">", types.contains(type));
			types.add(type);
		}
	}

	@Test
	public void shouldTestOneToOneRelationship() throws Exception {
		shouldTestStringIdUniquity();
		shouldTestTypes();
		assertEquals(ids.size(), types.size());
		for (Iterator<ModelType<?>> iter = types.iterator(); iter.hasNext();) {
			ModelType<?> type = iter.next();
			if (ids.remove(type.getId())) {
				iter.remove();
			}
		}
		assertTrue("NOT BOUND IDS:" + ids.toString() + " \nNOT BOUND TYPES:" + types.toString(), ids.isEmpty() && types.isEmpty());
	}
}
