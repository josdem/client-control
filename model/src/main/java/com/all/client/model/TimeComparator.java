package com.all.client.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TimeComparator implements Comparator<String> {
	private static final Log log = LogFactory.getLog(TimeComparator.class);
	
	@Override
	public int compare(String o1, String o2) {
		try {
			Date one = parseDate(o1);
			Date two = parseDate(o2);
			return one.compareTo(two);
		} catch (ParseException e) {
			log.error(e.getMessage(), e);
		}
		return 0;
	}

	private Date parseDate(String date) throws ParseException {
		try {
			DateFormat formater = new SimpleDateFormat("h:mm:ss");
			return formater.parse(date);
		} catch (ParseException e) {
			DateFormat formater = new SimpleDateFormat("m:ss");
			return formater.parse(date);
		}
	}
	
}
