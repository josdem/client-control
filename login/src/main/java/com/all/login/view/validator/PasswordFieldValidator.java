package com.all.login.view.validator;

import javax.swing.JPasswordField;

import com.all.i18n.Messages;

public class PasswordFieldValidator {

	private static final int MINIMUM_SIZE_PASSWORD = 8;
	private static final int MAXIMUM_SIZE_PASSWORD = 25;

	public boolean isPasswordSizeAllowed(String password) {
		return isMinimumSizeAllowed(password) && isMaximumSizeAllowed(password);
	}

	public boolean isPasswordSizeAllowed(String original, String confirmed) {
		return (isPasswordSizeAllowed(original) && isPasswordSizeAllowed(confirmed));
	}

	public boolean isMinimumSizeAllowed(String confirmed) {
		return confirmed.length() >= MINIMUM_SIZE_PASSWORD;
	}

	public boolean isMaximumSizeAllowed(String confirmed) {
		return confirmed.length() <= MAXIMUM_SIZE_PASSWORD;
	}

	public void passwordRenameValidate(JPasswordField confirmPasswordTextField, String fieldName, boolean validSize,
			Messages messages) {
		if (validSize) {
			confirmPasswordTextField.setName(fieldName);
			confirmPasswordTextField.setToolTipText(null);
		} else {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("invalid");
			confirmPasswordTextField.setToolTipText(messages.getMessage("validation.password"));
			stringBuilder.append(fieldName);
			confirmPasswordTextField.setName(stringBuilder.toString());
		}
	}

	public boolean isEquals(String original, String confirmed) {
		return original.equals(confirmed);
	}

}
