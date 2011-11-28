package com.all.login.model;

import java.util.HashSet;
import java.util.Set;

import com.all.shared.model.User;

public class LoginDatabase {
	private Set<Login> logins = new HashSet<Login>();

	private Set<User> users = new HashSet<User>();

	private String rememberUser = "";

	public Set<Login> getLogins() {
		return logins;
	}

	public void setLogins(Set<Login> logins) {
		this.logins = logins;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public User getUser(String mail) {
		for (User user : users) {
			if (mail.equals(user.getEmail())) {
				return user;
			}
		}
		return null;
	}

	public void addAll(LoginDatabase db2) {
		this.users.addAll(db2.users);
		this.logins.addAll(db2.logins);
	}

	@Override
	public String toString() {
		return "LoginDatabase [logins=" + logins + ", remember=" + rememberUser + ", users=" + users + "]";
	}

	public void setRememberUser(String rememberUser) {
		this.rememberUser = rememberUser;
	}

	public String getRememberUser() {
		return rememberUser;
	}

}
