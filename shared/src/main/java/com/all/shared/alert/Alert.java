package com.all.shared.alert;

import java.util.Date;

import com.all.shared.model.ContactInfo;
import com.all.shared.model.TypedClass;

public interface Alert extends TypedClass<Alert>{

	public abstract Date getDate();

	public abstract ContactInfo getSender();

	public abstract String getId();

	public abstract String getType();

	public abstract ContactInfo getReceiver();
	
}