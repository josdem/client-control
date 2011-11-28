package com.all.core.actions;

import com.all.action.ActionObject;
import com.all.shared.model.ModelCollection;
import com.all.shared.model.TrackContainer;

public class ModelMoveAction extends ActionObject {

	private final ModelCollection model;
	private final TrackContainer container;

	public ModelMoveAction(ModelCollection model, TrackContainer container) {
		this.model = model;
		this.container = container;
	}

	public ModelCollection getModel() {
		return model;
	}

	public TrackContainer getContainer() {
		return container;
	}

}
