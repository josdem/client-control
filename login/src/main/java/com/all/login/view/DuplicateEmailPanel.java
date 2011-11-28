package com.all.login.view;

import com.all.i18n.Messages;;

public final class DuplicateEmailPanel extends MessageLoginPanel {

	private static final long serialVersionUID = 1L;

	private static final String NAME = "duplicateEmailPanelBackground";

	public DuplicateEmailPanel() {
		super();
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.setName(NAME);
	}

	@Override
	public void internationalize(Messages messages) {
		getSignUpButton().setText(messages.getMessage("createNewAccount.back"));
		getTitleLabel().setText(messages.getMessage("createNewAccount.error.duplicate.title"));
		getInstructionsLabel().setText(messages.getMessage("createNewAccount.error.duplicate.firstParagraph"));
	}

}
