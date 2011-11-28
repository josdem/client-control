package com.all.shared.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.junit.Test;

import com.all.shared.json.test.DemoExtendedImpl;
import com.all.shared.json.test.DemoImpl;
import com.all.shared.json.test.DemoInterface;
import com.all.shared.model.Gender;
import com.all.shared.model.User;

public class TestJsonConverterOverInterfaces {

	@Test
	public void testInterfaceImplCompatibilityFromNormalImplToExtendedImpl() throws Exception {
		DemoInterface from = new DemoImpl("a", "b");
		String json = JsonConverter.toJson(from);
		DemoInterface to = JsonConverter.toBean(json, DemoExtendedImpl.class);

		assertEquals(from.getA(), to.getA());
		assertEquals(from.getB(), to.getB());
		assertNull(((DemoExtendedImpl) to).getC());
	}

	@Test
	public void testInterfaceImplCompatibilityFromExtendedImplToNormalImpl() throws Exception {
		DemoInterface from = new DemoExtendedImpl("a", "b", "c", new User(1L, "first", "name", "nicknamet", new Date(), Gender.SHEMALE,
				"12345", "12345"));
		String json = JsonConverter.toJson(from);
		DemoInterface to = JsonConverter.toBean(json, DemoImpl.class);
		assertNotNull(to);
		assertEquals(from.getA(), to.getA());
		assertEquals(from.getB(), to.getB());
	}

}
