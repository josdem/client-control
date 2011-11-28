package com.all.shared.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.all.shared.messages.FriendshipRequestStatus;

@Entity
@Table(name = "contactRequests")
public class ContactRequest implements Serializable {	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long idRequester;

	private Long idRequested;
	
	private boolean accepted;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@Transient
	private ContactInfo requester;
	
	@Transient 
	private ContactInfo requested;
	
	@Transient
	private FriendshipRequestStatus requestStatus;
	
	@Transient
	private static final long serialVersionUID = 1L;

	public ContactRequest() {
	}

	public ContactRequest(ContactInfo requester, ContactInfo requested){
		this.requester = requester;
		this.requested = requested;
		this.idRequester = requester.getId();
		this.idRequested = requested.getId();
		this.date = new Date();
	}
	
	public ContactRequest(Long idRequester, Long idRequested) {
		this.idRequester = idRequester;
		this.idRequested = idRequested;
		this.date = new Date();
	}

	public void accept(){
		accepted = true;
	}
	
	public Date getDate() {
		return date;
	}
	
	public Long getId() {
		return id;
	}
	
	public Long getIdRequested() {
		return idRequested;
	}

	public Long getIdRequester() {
		return idRequester;
	}

	public ContactInfo getRequested() {
		return requested;
	}

	public ContactInfo getRequester() {
		return requester;
	}

	public FriendshipRequestStatus getRequestStatus() {
		return requestStatus;
	}

	public boolean isAccepted(){
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public void setDate(Date createdOn) {
		this.date = createdOn;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIdRequested(Long idRequested) {
		this.idRequested = idRequested;
	}

	public void setIdRequester(Long idRequester) {
		this.idRequester = idRequester;
	}

	public void setRequested(ContactInfo requested) {
		this.requested = requested;
	}
	
	public void setRequester(ContactInfo requester) {
		this.requester = requester;
	}
	
	public void setRequestStatus(FriendshipRequestStatus requestStatus) {
		this.requestStatus = requestStatus;
	}
	
}
