package com.all.core.actions;

import com.all.core.model.SubViews;
import com.all.core.model.Views;

public class ComposeView {
    private Views view;
    private SubViews subView = SubViews.NONE;
    
    public ComposeView(Views view) {
		this.view = view;
	}
    
    public ComposeView(Views view, SubViews subView) {
		this.view = view;
		this.subView = subView;
	}
    
    public Views getView() {
		return view;
	}
    
    public SubViews getSubView() {
		return subView;
	}
}
