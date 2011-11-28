package com.all.core.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.all.observ.ObserverCollection;
import com.all.observ.ObservedProperty;
import com.all.observ.ObservePropertyChanged;
import com.all.shared.model.ContactInfo;

public class Profile {
	private final ObservedProperty<Profile, List<ContactInfo>> friends = new ObservedProperty<Profile, List<ContactInfo>>(this);
	private final ContactInfo contact;
	private final boolean local;

	public Profile(ContactInfo contact, boolean local) {
		this.contact = contact;
		this.local = local;
		this.friends.setValue(null);
	}

	public List<ContactInfo> getFriends() {
		return friends.getValue();
	}

	public ContactInfo getContact() {
		return contact;
	}

	public void setFriends(Collection<ContactInfo> friends2) {
		friends.setValue(new ArrayList<ContactInfo>(friends2));
	}

	public ObserverCollection<ObservePropertyChanged<Profile, List<ContactInfo>>> onFriendsChanged() {
		return friends.on();
	}

	public boolean isLocal() {
		return local;
	}

}
