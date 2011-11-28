package com.all.core.actions;

import com.all.action.ActionObject;

public class LoadContactLibraryAction extends ActionObject {
	private final boolean forceReload;
	private final String mail;

	public static LoadContactLibraryAction reload(String mail) {
		return new LoadContactLibraryAction(mail, true);
	}

	public static LoadContactLibraryAction load(String mail) {
		return new LoadContactLibraryAction(mail, false);
	}

	private LoadContactLibraryAction(String mail, boolean forceReload) {
		this.mail = mail;
		this.forceReload = forceReload;
	}

	public boolean isForceReload() {
		return forceReload;
	}

	public String getMail() {
		return mail;
	}

}
