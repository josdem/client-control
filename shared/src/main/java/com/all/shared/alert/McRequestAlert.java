package com.all.shared.alert;

import java.util.Date;

import com.all.shared.model.ContactInfo;
import com.all.shared.model.ModelCollection;

public class McRequestAlert extends AbstractAlert {

	public static final String TYPE = "MC_REQUEST";
	private static final long serialVersionUID = 1L;
	private ModelCollection model;

	@Deprecated
	public McRequestAlert() {
	}

	public McRequestAlert(ContactInfo sender, ContactInfo receiver, Date date, ModelCollection model) {
		super(sender, receiver, date.getTime(), TYPE);
		this.model = model;
	}

	public ModelCollection getModel() {
		return model;
	}

	@Deprecated
	public void setModel(ModelCollection model) {
		this.model = model;
	}

}
