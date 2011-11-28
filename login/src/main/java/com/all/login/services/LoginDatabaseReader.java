package com.all.login.services;

import java.util.HashSet;

import net.sf.json.JSONObject;

import com.all.login.model.Login;
import com.all.login.model.LoginDatabase;
import com.all.shared.json.JsonConverter;
import com.all.shared.json.readers.JsonReader;
import com.all.shared.model.User;

public class LoginDatabaseReader implements JsonReader<LoginDatabase> {

	@SuppressWarnings("unchecked")
	@Override
	public LoginDatabase read(String json) {
		JSONObject jsonModel = JSONObject.fromObject(json);
		LoginDatabase db = new LoginDatabase();
		if (jsonModel.containsKey("logins")) {
			String arr = jsonModel.getJSONArray("logins").toString();
			db.setLogins(JsonConverter.toTypedCollection(arr, HashSet.class, Login.class));
		}
		if (jsonModel.containsKey("users")) {
			String arr = jsonModel.getJSONArray("users").toString();
			db.setUsers(JsonConverter.toTypedCollection(arr, HashSet.class, User.class));
		}
		if (jsonModel.containsKey("rememberUser")) {
			db.setRememberUser(jsonModel.getString("rememberUser"));
		}
		return db;
	}

}
