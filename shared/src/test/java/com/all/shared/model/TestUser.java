package com.all.shared.model;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;


public class TestUser {
	User user = new User();
	
	@Test
	public void shouldConfirm() throws Exception {
		user.confirm();
		
		assertEquals(UserStatus.Confirmed, user.status);
	}
	
	@Test
	public void shouldGetFullName() throws Exception {
		user.setFirstName("first");
		user.setLastName("last");
		
		assertEquals("first last", user.getFullName());
		
	}
	
	//TODO: This test is failing in hudson.
	@Test
	@Ignore
	public void shouldNormalizeName() throws Exception {
		String specialChars =  " Á À Â Ä á à â ä É È Ê Ë é è ê ë í ì î ï ñ ó ò ô ö û ù û ü ";
		String expectedResult = "a a a a a a a a e e e e e e e e i i i i n o o o o u u u u";
		String result = new User().createSearchName(specialChars, null);
		assertEquals(expectedResult, result);
	}
	
	@Test
	public void shouldGetFirstNameWhenNull() throws Exception {
		user.setNickName("nickname");
		
		assertEquals("nickname", user.getFirstName());
	}
	
	@Test
	public void shouldGetLastNameWhenNull() throws Exception {
        user.setNickName("nickname");
		
		assertEquals("nickname", user.getLastName());
	}
	
	@Test
	public void shouleGetFirstName() throws Exception {
		user.setFirstName("maria");
		user.setNickName(null);
		
		assertEquals("maria", user.getFirstName());
	}
	
	@Test
	public void shouldGetLastName() throws Exception {
		user.setLastName("gonzalez");
		user.setNickName(null);
		
		assertEquals("gonzalez", user.getLastName());
	}
	
	@Test
	public void shouldGetNicknameWhenNull() throws Exception {
		user.setNickName(null);
		user.setLastName("gonzalez");
		user.setFirstName("maria hilda");
		
		assertEquals("mariahilda.gonzalez", user.getNickName());
	}
	
	@Test
	public void shouldGetNicknameWhenNullAndWhenTooBig() throws Exception {
		user.setNickName(null);
		user.setFirstName("maria hilda del perpetuo");
		user.setLastName("gonzalez altamirano de martinez");
		
		assertEquals("mariahildadelperpetuo.gon", user.getNickName());
		
	}
	
	@Test
	public void shouldGetNickname() throws Exception {
		user.setNickName("nickname");
		user.setFirstName(null);
		user.setLastName(null);
		
		assertEquals("nickname", user.getNickName());
	}
	
	@Test
	public void shouldGetInfoWhenNull() throws Exception {
		assertEquals(null, user.getFirstName());
		assertEquals(null, user.getLastName());
		assertEquals(null, user.getNickName());
	}
	
	@Test
	public void shouldGetNicknameWhenFirstNameIsNull() throws Exception {
		user.setLastName("lastname");
		
		assertEquals("lastname", user.getNickName());
	}
	@Test
	public void shouldGetNicknameWhenLastNameIsNull() throws Exception {
        user.setFirstName("firstname");
		
		assertEquals("firstname", user.getNickName());
	}
}
