package com.all.shared.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="PENDING_EMAILS")
public class PendingEmail implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Transient
	private static final long serialVersionUID = -2032338700388083472L;
	@Transient
	private String fromMail;
	@Transient
	private String message;
	@Transient
	private String subject;
	@Transient
	private String fullName;
	@Transient
	private String url;
	@Transient
	private String nickName;
	
	private String toMail;
	private Long userId;
	
	@Transient
	private final static String DEFAULT_SUBJECT = "Join to All.com";

	public PendingEmail(String fromMail, String toMail, String subject, String message, Long userId) {
		this.fromMail = fromMail;
		this.toMail = toMail;
		this.subject = subject;
		this.message = message;
		this.userId = userId;
	}
	
	public PendingEmail(){}
	
	public PendingEmail(Long userId) {
		this.userId = userId;
	}

	public PendingEmail(Long idRequester, String email) {
		userId = idRequester;
		toMail = email;
	}

	public PendingEmail(String fromMail, String toMail, Long userId, String emailText) {
		this.fromMail = fromMail;
		this.toMail = toMail;
		this.message = emailText;
		this.userId = userId;
		this.subject = DEFAULT_SUBJECT;
	}

	public String getFromMail() {
		return fromMail;
	}
	public String getToMail() {
		return toMail;
	}
	public String getMessage() {
		return message;
	}

	public String getSubject() {
		return subject;
	}

	public Long getUserId() {
		return userId;
	}

	public Long getId() {
		return id;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public String getNickName() {
		return nickName;
	}
	
	public void setToMail(String toMail) {
		this.toMail = toMail;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFromMail(String fromMail) {
		this.fromMail = fromMail;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}
