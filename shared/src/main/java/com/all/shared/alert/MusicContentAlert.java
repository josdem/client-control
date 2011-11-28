package com.all.shared.alert;

import java.util.Date;

import com.all.shared.model.ContactInfo;
import com.all.shared.model.ModelCollection;

public class MusicContentAlert extends AbstractAlert {

	public static final String TYPE = "MUSIC_CONTENT";
	private static final long serialVersionUID = 1L;
	private String personalizedMessage;
	private ModelCollection model;
	
	@Deprecated
	public MusicContentAlert(){}
	
	public MusicContentAlert(ContactInfo sender, ContactInfo receiver, Date date,  ModelCollection model, String personalMessage) {	
		super(sender, receiver, date.getTime(), TYPE);
		this.personalizedMessage = personalMessage;
		this.model = model;
	}
	
	public ModelCollection getModel() {
		return model;
	}
	
	@Deprecated
	public void setModel(ModelCollection model) {
		this.model = model;
	}

	public String getPersonalizedMessage() {
		return personalizedMessage;
	}
	
	@Deprecated
	public void setPersonalizedMessage(String personalizedMessage) {
		this.personalizedMessage = personalizedMessage;
	}
	
}
