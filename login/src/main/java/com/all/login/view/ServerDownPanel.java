package com.all.login.view;

import com.all.i18n.Messages;

public final class ServerDownPanel extends MessageLoginPanel {

	private static final long serialVersionUID = 1L;

	private static final String NAME = "serverDownPanelBackground";

	public ServerDownPanel() {
		super();
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.setName(NAME);
	}

	@Override
	public void internationalize(Messages messages) {
		super.internationalize(messages);
		getTitleLabel().setText(messages.getMessage("forgotPassword.serverDown"));
		getInstructionsLabel().setText(messages.getMessage("forgotPassword.error.serverError"));
	}

}
