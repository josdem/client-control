package com.all.core.model;

public class ContainerView {

	private Views views;
	private ModelContainerView modelContainerView = null;
	
	
	public ContainerView(Views views) {
		this.views = views;
	}

	public ContainerView(Views views, ModelContainerView modelContainerView) {
		this.views = views;
		this.modelContainerView  = modelContainerView;
	}
	
	public Views getViews() {
		return views;
	}
	
	public ModelContainerView getModelContainerView() {
		return this.modelContainerView;
	}


}
