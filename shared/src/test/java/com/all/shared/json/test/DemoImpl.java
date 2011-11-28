package com.all.shared.json.test;

public class DemoImpl implements DemoInterface {

	private String a;
	private String b;

	public DemoImpl() {
	}

	public DemoImpl(String a, String b) {
		this.a = a;
		this.b = b;
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

}
