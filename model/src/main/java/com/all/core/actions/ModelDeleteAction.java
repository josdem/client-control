package com.all.core.actions;

import com.all.action.ActionObject;
import com.all.shared.model.ModelCollection;
import com.all.shared.model.TrackContainer;

public class ModelDeleteAction extends ActionObject {
	private final ModelCollection model;
	private final DeleteMode mode;
	private final TrackContainer container;

	public enum DeleteMode {
		ONLY_REFERENCES, REF_AND_FILES;
	}

	public ModelDeleteAction(TrackContainer container, ModelCollection model, DeleteMode mode) {
		this.container = container;
		this.model = model;
		this.mode = mode;
	}

	public ModelCollection getModel() {
		return model;
	}

	public DeleteMode getMode() {
		return mode;
	}

	public TrackContainer getContainer() {
		return container;
	}

}
