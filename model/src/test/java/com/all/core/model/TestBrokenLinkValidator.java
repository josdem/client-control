package com.all.core.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.all.client.model.BrokenLinkValidator;

public class TestBrokenLinkValidator {

	File normal = new File("src/test/resources/brokenlinks/TestSong1.mp3");
	File folder = new File("src/test/resources/brokenlinks/folder");
	File notExists = new File("src/test/resources/brokenlinks/thisFileDoNotExists");
	File hola = new File("src/test/resources/brokenlinks/.hola");
	File xxx = new File("src/test/resources/brokenlinks/xxx");//this is hidden in windows

	BrokenLinkValidator v;
	
	@Before
	public void setUp(){
		 v = new BrokenLinkValidator();
	}
	
	@Test
	public void shouldDetectBrokenLinkBecauseItIsStartingWithDot() throws IOException {
		assertTrue(v.isBrokenLink(hola));
	}
	
	@Test
	public void shouldDetectBrokenLinkBecauseItIsFileDoNotExist() throws IOException {
		assertTrue(v.isBrokenLink(notExists));
	}
	
	@Test
	public void shouldDetectBrokenLinkBecauseItIsFolder() throws IOException {
		assertTrue(v.isBrokenLink(folder));
	}
	
	@Test
	public void shouldDetectItIsNotBrokenLink(){
		assertFalse(v.isBrokenLink(normal));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldDetectInvalidArgument(){
		v.isBrokenLink(null);
	}
}
