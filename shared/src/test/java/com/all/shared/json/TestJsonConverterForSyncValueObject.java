package com.all.shared.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.all.shared.model.SyncValueObject;

public class TestJsonConverterForSyncValueObject {

	private String email = "test@email.com";
	private SyncValueObject svo;

	@SuppressWarnings("deprecation")
	@Before
	public void setup(){
		svo = new SyncValueObject();
		svo.setSnapshot(10);
		svo.setDelta(5);
		svo.setEmail(email);
		svo.setTimestamp(System.currentTimeMillis());
	}

	@Test
	public void shouldConvertSimpleSVO() throws Exception {
		String json = JsonConverter.toJson(svo);
		SyncValueObject result = (SyncValueObject) JsonConverter.toBean(json, SyncValueObject.class);
		assertNotNull(result);
		assertSimpleValues(result);
		assertTrue(result.getEvents().size() == 0);
	}

	@Test
	public void shouldConvertSVOWithDeleteEvent() throws Exception {
		String str = "testing";
		String str2 = "sync";
		
		svo.getEvents().add(str);
		svo.getEvents().add(str2);

		String json = JsonConverter.toJson(svo);
		SyncValueObject result = (SyncValueObject) JsonConverter.toBean(json, SyncValueObject.class);
		assertNotNull(result);
		assertSimpleValues(result);
		
		assertTrue(result.getEvents().size() == 2);
		assertEquals("testing", new String( result.getEvents().get(0)));
		assertEquals("sync", new String( result.getEvents().get(1)));
	}
	
	@Test
	public void shouldConvertSVOWithAddEvent() throws Exception {
		String str = "testing sync";
		svo.getEvents().add(str);
		String json = JsonConverter.toJson(svo);
		SyncValueObject result = (SyncValueObject) JsonConverter.toBean(json, SyncValueObject.class);
		assertNotNull(result);
		assertSimpleValues(result);
		assertTrue(result.getEvents().size() == 1);
	}
	
	@Test
	public void shouldNotThrowAnExceptionIfListIsNull() throws Exception {
		svo.setEvents(null);
		
		String json = JsonConverter.toJson(svo);
		SyncValueObject result = (SyncValueObject) JsonConverter.toBean(json, SyncValueObject.class);
		assertNotNull(result);
		assertSimpleValues(result);
		assertTrue(result.getEvents().isEmpty());
	}
	
	private void assertSimpleValues(SyncValueObject result) {
		assertEquals(svo.getSnapshot(), result.getSnapshot());
		assertEquals(svo.getDelta(), result.getDelta());
		assertEquals(svo.getEmail(), result.getEmail());
		assertEquals(svo.getTimestamp(), result.getTimestamp());
	}
}
