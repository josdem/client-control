package com.all.login.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Login implements Comparable<Login> {
	private static final long serialVersionUID = 1L;

	@Id
	private String email;

	private long date;

	public Login(String email) {
		this.email = email;
		date = new Date().getTime();
	}

	public Login() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return email;
	}

	@Override
	public int compareTo(Login that) {
		return Long.valueOf(that.date).compareTo(Long.valueOf(this.date));
	}

	public void refreshDate() {
		date = new Date().getTime();
	}

}
