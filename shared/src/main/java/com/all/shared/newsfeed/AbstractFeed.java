package com.all.shared.newsfeed;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import com.all.shared.model.ContactInfo;

public class AbstractFeed implements AllFeed, Comparable<AllFeed> {
	private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("UTC");

	private Date date;
	private ContactInfo owner;
	private Integer type;

	@Deprecated
	public AbstractFeed() {
	}

	public AbstractFeed(ContactInfo owner, int type) {
		updateTimestamp();
		this.owner = owner;
		this.type = type;
	}

	public final void updateTimestamp() {
		this.date = GregorianCalendar.getInstance(TIME_ZONE).getTime();
	}

	@Override
	public final Date getDate() {
		return date;
	}

	@Override
	public ContactInfo getOwner() {
		return owner;
	}

	@Override
	public int getType() {
		return type;
	}

	@Override
	public Class<AllFeed> getTypedClass() {
		return AllFeed.class;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setOwner(ContactInfo owner) {
		this.owner = owner;
	}

	@Deprecated
	public void setType(int type) {
		this.type = type;
	}

	public int compareTo(AllFeed feed2) {
		return this.getDate().compareTo(feed2.getDate());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AbstractFeed other = (AbstractFeed) obj;
		if (date == null) {
			if (other.date != null) {
				return false;
			}
		} else if (!date.equals(other.date)) {
			return false;
		}
		if (owner == null) {
			if (other.owner != null) {
				return false;
			}
		} else if (!owner.equals(other.owner)) {
			return false;
		}
		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!type.equals(other.type)) {
			return false;
		}
		return true;
	}

	@Override
	public List<ContactInfo> contacts() {
		List<ContactInfo> contacts = new ArrayList<ContactInfo>();
		contacts.add(owner);
		return contacts;
	}

}
