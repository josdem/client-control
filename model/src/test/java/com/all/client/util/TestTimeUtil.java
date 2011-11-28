package com.all.client.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class TestTimeUtil {
	
	@Test
	public void shouldPassOneSecond() throws Exception {
		assertFalse(TimeUtil.hasPassedOneSecond(1001L, 1L));
		assertTrue(TimeUtil.hasPassedOneSecond(1001L, 0L));
	}
	

	@Test
	public void shouldFormatTimeFromSeconds() throws Exception {
		int seconds = 61;
		assertEquals("1:01", TimeUtil.convertSecondsToTime(seconds));
	}

}
