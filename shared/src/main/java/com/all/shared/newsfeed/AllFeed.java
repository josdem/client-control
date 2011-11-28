package com.all.shared.newsfeed;

import java.util.Date;
import java.util.List;

import com.all.shared.model.ContactInfo;
import com.all.shared.model.TypedClass;

public interface AllFeed extends TypedClass<AllFeed>{

	ContactInfo getOwner();

	Date getDate();

	int getType();
	
	List<ContactInfo> contacts();

}
