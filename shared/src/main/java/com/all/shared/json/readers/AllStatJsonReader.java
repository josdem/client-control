package com.all.shared.json.readers;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.all.shared.json.JsonConverter;
import com.all.shared.stats.AllStat;
import com.all.shared.stats.TopHundredStat;
import com.all.shared.stats.TopHundredStatId;

public class AllStatJsonReader implements JsonReader<AllStat> {
	private static final Log LOG = LogFactory.getLog(JsonConverter.class);

	private static final AllStatJsonReader INSTANCE = new AllStatJsonReader();

	private AllStatJsonReader() {
	}

	public static JsonReader<?> getInstance() {
		return INSTANCE;
	}

	@Override
	public AllStat read(String json) {
		JSONObject jsonAlert = JSONObject.fromObject(json);
		Class<?> clazz;
		try {
			clazz = Class.forName(jsonAlert.getString("statType"));
		} catch (ClassNotFoundException e) {
			LOG.warn(e, e);
			return null;
		}
		AllStat stat = (AllStat) JsonConverter.toBean(json, clazz);
		if (stat instanceof TopHundredStat) {
			String idJson = jsonAlert.getJSONObject("id").toString();
			TopHundredStatId id = JsonConverter.toBean(idJson, TopHundredStatId.class);
			((TopHundredStat) stat).setId(id);
		}
		return stat;
	}

}
