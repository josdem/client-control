package com.all.client.model;

public class MailList {
	private String email;
	private String name;
	private boolean checked;

	public MailList(String email, String name, boolean checked) {
		super();
		this.email = email;
		this.name = name;
		this.checked = checked;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	@Override
	public String toString() {
		return email;
	}

}
