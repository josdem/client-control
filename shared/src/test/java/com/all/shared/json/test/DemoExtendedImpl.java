package com.all.shared.json.test;

import com.all.shared.model.User;

public class DemoExtendedImpl implements DemoInterface {

	private String a;
	private String b;
	private String c;
	private User user;

	public DemoExtendedImpl(String a, String b, String c, User user) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.user = user;
	}

	public DemoExtendedImpl() {
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
