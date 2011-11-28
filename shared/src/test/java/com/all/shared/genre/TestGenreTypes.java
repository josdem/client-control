package com.all.shared.genre;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;


public class TestGenreTypes {
	@Test
	public void shouldGetBluesAsGenre() throws Exception {
		assertEquals("Blues", GenreTypes.getGenreByCode(0));
	}
	
	@Test
	public void shouldReturnEmptyStringIfNoGenre() throws Exception {
		assertEquals(StringUtils.EMPTY, GenreTypes.getGenreByCode(150));
	}
}
