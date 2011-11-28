package com.all.shared.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "Avatars")
public class Avatar implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private Long idUser;
	@Lob
	@Column(nullable = true)
	private byte[] avatarData;

	public Avatar(){}
	
	public Avatar(Long idUser, byte[] avatarData){
		this.idUser = idUser;
		this.avatarData = avatarData;
	}
	
	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public byte[] getAvatarData() {
		return avatarData;
	}

	public void setAvatarData(byte[] avatarData) {
		this.avatarData = avatarData;
	}

}
