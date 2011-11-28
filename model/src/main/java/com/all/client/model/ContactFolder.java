package com.all.client.model;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.all.client.data.Hashcoder;
import com.all.shared.model.ContactInfo;
import com.all.shared.sync.SyncAble;
import com.all.shared.sync.SyncAbleAbstractImpl;
import com.all.shared.sync.SyncUpdateAble;

@SuppressWarnings("unchecked")
@Entity
public class ContactFolder extends SyncAbleAbstractImpl implements Serializable, Comparable{
	
	@Transient
	private transient Log log = LogFactory.getLog(this.getClass());
	private static final long serialVersionUID = 1L;
	public static final int NAME_MAXLENGTH = 250;
	public static final int NAME_MINLENGTH = 1;
	@Id
	@SyncUpdateAble
	private String id;

	@Column
	@SyncUpdateAble
	private String name;

	@Transient
	private Set<ContactInfo> contacts = new HashSet<ContactInfo>();
	
	public ContactFolder(String name) {
		this.id = createId(name);
		this.name = name;
	}

	private String createId(String name) {
		String hashcode = name + System.currentTimeMillis() + new Random().nextLong();
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			md.update(hashcode.getBytes());
			hashcode = Hashcoder.toHex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			log.error(e.toString());
		}
		return hashcode;
	}

	//"Should only be used by hibernate"
	@Deprecated
	public ContactFolder() {
	}

	public Set<ContactInfo> getContacts() {
		return contacts;
	}

	public void setContacts(Set<ContactInfo> contacts) {
		this.contacts = contacts;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void addContact(ContactInfo contact) {
		contacts.add(contact);
	}

	public void addContacts(List<ContactInfo> contacts) {
		this.contacts.addAll(contacts);
	}

	public int size() {
		return contacts.size();
	}

	public void removeContacts(Set<ContactInfo> contacts) {
		this.contacts.removeAll(contacts);
	}

	@Override
	public String toString() {
		return name;
	}
	
	public String getSyncAbleId() {
		return this.id;
	}
	
	public void clone(SyncAble entity) {
		ContactFolder folder = (ContactFolder) entity;
		this.setName(folder.getName());
	}


	@Override
	public int compareTo(Object o) {
		ContactFolder contactFolder = (ContactFolder) o;
		return name.compareTo(contactFolder.getName());  
	}
}
