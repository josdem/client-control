package com.all.messengine.support;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.Spy;

import com.all.messengine.MessEngine;
import com.all.messengine.impl.StubMessEngine;

public class TestMessEngineConfigurator {
	@Spy
	private MessEngine messEngine = new StubMessEngine();
	private MessEngineConfigurator configurator = new MessEngineConfigurator(messEngine);

	@Test
	public void shouldManageMessage() throws Exception {
		MessageMethodTester annotationTester = new MessageMethodTester();
		configurator.setupMessEngine(annotationTester);
		messEngine.send(new SimpleMessage<String>(TestTypes.messageId, "dea"));
		assertEquals("dea", annotationTester.val);
	}

}
