package com.all.core.actions;

import java.awt.Image;

import com.all.action.ActionObject;
import com.all.shared.model.User;

public class UpdateProfileAction extends ActionObject {

	private final User user;
	private final Image avatar;

	public UpdateProfileAction(User user, Image avatar) {
		this.user = user;
		this.avatar = avatar;
	}

	public User getUser() {
		return user;
	}

	public Image getAvatar() {
		return avatar;
	}
	
	
}
