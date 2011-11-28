package com.all.shared.json.readers;

import java.util.HashMap;

import net.sf.json.JSONObject;

import com.all.shared.alert.Alert;
import com.all.shared.alert.AllNotificationAlert;
import com.all.shared.alert.FriendshipRequestAlert;
import com.all.shared.alert.FriendshipResponseAlert;
import com.all.shared.alert.McRequestAlert;
import com.all.shared.alert.MusicContentAlert;
import com.all.shared.json.JsonConverter;
import com.all.shared.model.ContactRequest;
import com.all.shared.model.ModelCollection;

public class AlertJsonReader implements JsonReader<Alert> {

	private static final HashMap<String, Class<? extends Alert>> ALL_ALERTS = new HashMap<String, Class<? extends Alert>>();

	private static final AlertJsonReader INSTANCE = new AlertJsonReader();

	static {
		ALL_ALERTS.put(FriendshipRequestAlert.TYPE, FriendshipRequestAlert.class);
		ALL_ALERTS.put(FriendshipResponseAlert.TYPE, FriendshipResponseAlert.class);
		ALL_ALERTS.put(MusicContentAlert.TYPE, MusicContentAlert.class);
		ALL_ALERTS.put(McRequestAlert.TYPE, McRequestAlert.class);
		ALL_ALERTS.put(AllNotificationAlert.TYPE, AllNotificationAlert.class);
	}

	private AlertJsonReader() {
	}

	public static JsonReader<Alert> getInstance() {
		return INSTANCE;
	}

	@SuppressWarnings("deprecation")
	public Alert read(String json) {
		JSONObject jsonAlert = JSONObject.fromObject(json);
		Class<? extends Alert> clazz = ALL_ALERTS.get(jsonAlert.getString("type"));
		Alert alert = null;
		if (clazz.equals(MusicContentAlert.class)) {
			JSONObject model = (JSONObject) jsonAlert.remove("model");
			alert = JsonConverter.toBean(jsonAlert.toString(), clazz);
			((MusicContentAlert) alert).setModel(JsonConverter.toBean(model.toString(), ModelCollection.class));
		} else if (clazz.equals(McRequestAlert.class)) {
			Object model = jsonAlert.remove("model");
			alert = JsonConverter.toBean(jsonAlert.toString(), clazz);
			((McRequestAlert) alert).setModel(JsonConverter.toBean(model.toString(), ModelCollection.class));
		} else if (clazz.equals(FriendshipRequestAlert.class)) {
			Object request = jsonAlert.remove("request");
			alert = JsonConverter.toBean(jsonAlert.toString(), clazz);
			((FriendshipRequestAlert) alert).setRequest(JsonConverter.toBean(request.toString(), ContactRequest.class));
		} else if (clazz.equals(FriendshipResponseAlert.class)) {
			Object request = jsonAlert.remove("request");
			alert = JsonConverter.toBean(jsonAlert.toString(), clazz);
			((FriendshipResponseAlert) alert).setRequest(JsonConverter.toBean(request.toString(), ContactRequest.class));
		} else {
			alert = JsonConverter.toBean(jsonAlert.toString(), clazz);
		}
		return alert;
	};
}
