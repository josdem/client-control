package com.all.core.events;

import com.all.event.EventObject;
import com.all.shared.model.ModelCollection;

public class ModelEvent extends EventObject {
	private final ModelCollection modelCollection;
	private final ModelContainer source;
	private final ModelContainer destination;

	public static ModelEvent added(ModelCollection modelCollection, ModelContainer destination) {
		return new ModelEvent(modelCollection, destination, destination);
	}

	public static ModelEvent removed(ModelCollection modelCollection, ModelContainer source) {
		return new ModelEvent(modelCollection, source, source);
	}

	public static ModelEvent updated(ModelCollection modelCollection, ModelContainer container) {
		return new ModelEvent(modelCollection, container, container);
	}

	public static ModelEvent moved(ModelCollection modelCollection, ModelContainer source, ModelContainer destination) {
		return new ModelEvent(modelCollection, source, destination);
	}

	private ModelEvent(ModelCollection modelCollection, ModelContainer source, ModelContainer destination) {
		this.modelCollection = modelCollection;
		this.source = source;
		this.destination = destination;
	}

	public ModelContainer getSource() {
		return source;
	}

	public ModelContainer getDestination() {
		return destination;
	}

	public ModelCollection getModelCollection() {
		return modelCollection;
	}

}
