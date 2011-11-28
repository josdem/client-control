package com.all.client.model;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.all.client.data.Hashcoder;
import com.all.shared.sync.SyncAble;
import com.all.shared.sync.SyncAbleAbstractImpl;
import com.all.shared.sync.SyncUpdateAble;

@Entity
public class ContactUserFolder extends SyncAbleAbstractImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@SyncUpdateAble
	private String id;
	@Column
	@SyncUpdateAble
	private String idFolder;
	@Column
	@SyncUpdateAble
	private String email;

	@Transient
	private Log log = LogFactory.getLog(this.getClass());

	public ContactUserFolder() {
		this.id = createId("emptyFolder", new Date());
	}

	public ContactUserFolder(String idFolder, String email) {
		this.id = createId(String.valueOf(idFolder), new Date());
		this.email = email;
		this.idFolder = idFolder;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdFolder() {
		return idFolder;
	}

	public void setIdFolder(String idFolder) {
		this.idFolder = idFolder;
	}

	private String createId(String name, Date creationDate) {
		String hashcode = name + creationDate.toString() + new Random().nextLong();
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			md.update(hashcode.getBytes());
			hashcode = Hashcoder.toHex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			log.error(e, e);
		}
		return hashcode;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String getSyncAbleId() {
		return this.id;
	}

	@Override
	public void clone(SyncAble entity) {
		ContactUserFolder contactUserFolder = (ContactUserFolder) entity;
		this.setIdFolder(contactUserFolder.getIdFolder());
		this.setEmail(contactUserFolder.getEmail());
	}

}