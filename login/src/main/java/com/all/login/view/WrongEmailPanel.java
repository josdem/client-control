package com.all.login.view;

import com.all.i18n.Messages;
import com.all.login.controller.LoginController;

public class WrongEmailPanel extends ForgotPasswordPanel {

	private static final long serialVersionUID = 1L;

	private static final String NAME = "wrongEmailPanelBackground";

	public WrongEmailPanel(LoginController controller) {
		super(controller);
		initialize();
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.setName(NAME);
		this.getSendButton().setEnabled(true);
	}

	@Override
	public void internationalize(Messages messages) {
		super.internationalize(messages);
		getTitleLabel().setText(messages.getMessage("forgotPassword.invalidData"));
		getInstructionsLabel().setText(messages.getMessage("forgotPassword.error.invalidData"));
	}

}
