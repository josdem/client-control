package com.all.login.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.all.core.common.util.SequenceUtil;


public class TestSequenceUtil {
	@Test
	public void shouldCreateSequence() throws Exception {
		String name = "test";
		String[] sequence = SequenceUtil.createSequence(name, 0, 5, false);
		assertEquals(7, sequence.length);
		assertEquals(name, sequence[0]);
	}
	
	@Test
	public void shouldCreateRevertedSequence() throws Exception {
		String name = "test";
		String[] sequence = SequenceUtil.createSequence(name, 0, 5, true);
		assertEquals(7, sequence.length);
		assertEquals("5", sequence[1]);
	}
	
	@Test
	public void shouldNotIncludeNameWhenNull() throws Exception {
		String[] sequence = SequenceUtil.createSequence(null, 0, 5, false);
		assertEquals(6, sequence.length);
		assertEquals("5", sequence[5]);
	}
}
