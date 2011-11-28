package com.all.shared.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.net.URI;

import org.junit.Test;

import com.all.shared.util.UrnKeyGenerator;

public class TestURNKeyGenerator {

	@Test
	public void shouldGenerateCorrectUuidURNKey() throws Exception {
		URI uri = UrnKeyGenerator.generateUrn();
		assertNotNull(uri);
    
		String uriString = uri.toString();
		int colonAt = uriString.indexOf(':');
		assertNotSame(-1, colonAt);
		String uriEncodingName = uriString.substring(0, colonAt);
		assertEquals(UrnKeyGenerator.URIEncodingName, uriEncodingName);
		
		uriString =  uriString.substring(colonAt + 1);
		colonAt = uriString.indexOf(':');
		String namespace = uriString.substring(0, colonAt);
		assertEquals(UrnKeyGenerator.URNNamespace, namespace);
		
		uriString =  uriString.substring(colonAt + 1);
		assertTrue(uriString.startsWith("uuid-"));
	}
}
