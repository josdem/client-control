package com.all.core.actions;

import java.io.File;

import com.all.action.ActionObject;
import com.all.shared.model.ModelCollection;

public class DevicesCopyAction extends ActionObject {

	private final ModelCollection model;
	
	private final File file;
	
	public DevicesCopyAction(ModelCollection model, File file) {
		this.model = model;
		this.file = file;
	}
	
	public ModelCollection getModel() {
		return model;
	}
	
	public File getFile() {
		return file;
	}
}
