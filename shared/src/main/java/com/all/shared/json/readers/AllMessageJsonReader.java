package com.all.shared.json.readers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.all.shared.json.JsonConverter;
import com.all.shared.model.AllMessage;
import com.all.shared.model.PushMessage;

@SuppressWarnings("unchecked")
public class AllMessageJsonReader implements JsonReader<AllMessage<?>> {

	private static final AllMessageJsonReader INSTANCE = new AllMessageJsonReader();

	private static final String PROPERTIES_KEY = "properties";

	private static final String CONTENT_TYPE_KEY = "contentType";

	private static final String BODY_KEY = "body";

	private static final String BODY_TYPE_KEY = "bodyType";

	private static final String MESSAGE_TYPE_KEY = "type";

	private static final Log log = LogFactory.getLog(INSTANCE.getClass());

	private static final String ERROR_MESSAGE = "Could not create body from json.";

	public static JsonReader<AllMessage<?>> getInstance(){
		return INSTANCE;
	}
	
	private AllMessageJsonReader(){}
	
	@Override
	public AllMessage<?> read(String json) {
		JSONObject jsonMessage = JSONObject.fromObject(json);
		AllMessage<?> message = null;
		if(PushMessage.TYPE.equals(readType(jsonMessage))){
			JSONArray jsonRecipients = jsonMessage.getJSONArray("recipients");
			List<String> recipients = JsonConverter.toCollection(jsonRecipients.toString(), ArrayList.class);
			message = new PushMessage<Object>((AllMessage<Object>)readBody(jsonMessage), recipients);
		} else {
			message = new AllMessage<Object>(readType(jsonMessage), readBody(jsonMessage));
		}
		Map<String, String> properties = readProperties(jsonMessage);
		for (String property : properties.keySet()) {
			message.putProperty(property, properties.get(property));
		}
		return message;
	}

	private String readType(JSONObject jsonMessage) {
		return jsonMessage.getString(MESSAGE_TYPE_KEY);
	}

	private Object readBody(JSONObject jsonMessage) {
		Serializable body = null;
		try {
			Class<?> bodyType = Class.forName(jsonMessage.getString(BODY_TYPE_KEY));
			if (Collection.class.isAssignableFrom(bodyType)) {
				return readCollection(jsonMessage, bodyType);
			} else if (Map.class.isAssignableFrom(bodyType)) {
				return readMap(jsonMessage, bodyType);
			} if (String.class.isAssignableFrom(bodyType)) {
				return jsonMessage.getString(BODY_KEY);
			} else {
				return JsonConverter.toBean(jsonMessage.getJSONObject(BODY_KEY).toString(), bodyType);
			}
		} catch (ClassNotFoundException e) {
			log.error(ERROR_MESSAGE, e);
		}
		return body;
	}

	private Serializable readMap(JSONObject jsonMessage, Class<?> bodyType) {
		Map map = null;
		try {
			map = (Map) bodyType.newInstance();
		} catch (Exception e) {
			log.error(e, e);
			map = new HashMap();
		}
		JSONObject jsonMap = jsonMessage.getJSONObject(BODY_KEY);
		Iterator iterator = jsonMap.keys();
		try {
			if(jsonMessage.has(CONTENT_TYPE_KEY)){
				Class<?> contentType = Class.forName(jsonMessage.getString(CONTENT_TYPE_KEY));
				while(iterator.hasNext()){
					Object key = iterator.next();
					map.put(key, JsonConverter.toBean(jsonMap.get(key).toString(), contentType));
				}
			}
			return (Serializable) map;
		} catch (ClassNotFoundException e) {
			log.error("Could not read json map.", e);
			return new HashMap();
		}
	}

	private Serializable readCollection(JSONObject jsonMessage, Class collectionType) throws ClassNotFoundException {
		JSONArray jsonCollection = jsonMessage.getJSONArray(BODY_KEY);
			try {
				Class contentType = Class.forName(jsonMessage.getString(CONTENT_TYPE_KEY));
				return (Serializable) JsonConverter.toTypedCollection(jsonCollection.toString(), collectionType, contentType);
			} catch (Exception e) {
				log.error("Could not read collection from json." + collectionType, e);
				return null;
			}
	}

	private Map<String, String> readProperties(JSONObject jsonMessage) {
		return (Map<String, String>) JSONObject.toBean(jsonMessage.getJSONObject(PROPERTIES_KEY), HashMap.class);
	}

}
