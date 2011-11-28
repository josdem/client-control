package com.all.shared.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestNickNameRefiner {

	@Test
	public void shouldRefine() throws Exception {
		assertEquals("hilda", NickNameRefiner.refine("hi81.lda_945-007"));
	}
	
	@Test
	public void shouldSaidIfIsNullOrEmpty() throws Exception {
		assertEquals(true, NickNameRefiner.isNullOrEmpty(""));
		assertEquals(true, NickNameRefiner.isNullOrEmpty(null));
		assertEquals(false, NickNameRefiner.isNullOrEmpty(" "));
		assertEquals(false, NickNameRefiner.isNullOrEmpty("hilda"));
	}
}
