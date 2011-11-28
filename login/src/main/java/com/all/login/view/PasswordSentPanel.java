package com.all.login.view;

import com.all.i18n.Messages;;

public final class PasswordSentPanel extends MessageLoginPanel {

	private static final long serialVersionUID = 1L;

	private static final String NAME = "passwordSentPanelBackground";

	public PasswordSentPanel() {
		super();
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.setName(NAME);
	}

	@Override
	protected void internationalize(Messages messages) {
		super.internationalize(messages);
		getTitleLabel().setText(messages.getMessage("forgotPassword.sendingSuccess.title"));
		getInstructionsLabel().setText(messages.getMessage("forgotPassword.sendingSuccess"));
	}
}
