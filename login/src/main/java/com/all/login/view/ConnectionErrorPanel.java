package com.all.login.view;

import com.all.i18n.Messages;

public final class ConnectionErrorPanel extends MessageLoginPanel {

	private static final long serialVersionUID = 1L;

	private static final String NAME = "connectionErrorPanelBackground";

	public ConnectionErrorPanel() {
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
		getTitleLabel().setText(messages.getMessage("forgotPassword.error.connection"));
		getInstructionsLabel().setText(messages.getMessage("createNewAccount.connection.error.instructions"));
	}

}
