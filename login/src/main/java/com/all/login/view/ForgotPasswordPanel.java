package com.all.login.view;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.all.core.common.view.SynthFonts;
import com.all.core.common.view.util.CopyPasteKeyAdapterForMac;
import com.all.core.common.view.util.SelectedTextForeground;
import com.all.i18n.Messages;
import com.all.login.controller.LoginController;
import com.all.observ.ObservValue;
import com.all.observ.Observable;
import com.all.observ.ObserverCollection;
import com.all.shared.command.LoginCommand;

public class ForgotPasswordPanel extends MessageLoginPanel {

	private static final long serialVersionUID = 1L;

	private static final String NAME = "forgotPasswordPanelBackground";

	private static final Rectangle SEND_BUTTON_BOUNDS = new Rectangle(483, 200, 110, 28);

	private static final Rectangle MAIL_LABEL_BOUNDS = new Rectangle(230, 162, 200, 28);

	private static final Rectangle MAIL_TEXT_FIELD_BOUNDS = new Rectangle(438, 162, 200, 28);

	private static final String SEND_EMAIL_NAME = "buttonTopLoginSendEmail";

	private static final String MAIL_TEXTFIELD_NAME = "textLoginFieldMail";

	private JButton sendButton;

	private JLabel mailLabel;

	private JTextField mailTextField;

	private LoginController controller;

	private final Observable<ObservValue<String>> onTrySendEmailEvent = new Observable<ObservValue<String>>();

	public ForgotPasswordPanel(LoginController controller) {
		this.controller = controller;
		initialize();
	}

	@Override
	protected void initialize() {
		this.setName(NAME);
		this.add(getMailTextField());
		this.add(getMailLabel());
		this.add(getSendButton());
		super.initialize();
	}

	protected JButton getSendButton() {
		if (sendButton == null) {
			sendButton = new JButton();
			sendButton.setBounds(SEND_BUTTON_BOUNDS);
			sendButton.setName(SEND_EMAIL_NAME);
			sendButton.setEnabled(false);
			sendButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					onTrySendEmailEvent.fire(new ObservValue<String>(getMailTextField().getText()));
				}
			});
		}
		return sendButton;
	}

	protected JTextField getMailTextField() {
		if (mailTextField == null) {
			mailTextField = new JTextField();
			mailTextField.setBounds(MAIL_TEXT_FIELD_BOUNDS);
			mailTextField.setSelectionColor(SelectedTextForeground.SELECTED_FOREGROUND_COLOR);
			mailTextField.setName(MAIL_TEXTFIELD_NAME);
			mailTextField.addKeyListener(new KeyListenerValidator());
			mailTextField.addKeyListener(new CopyPasteKeyAdapterForMac());
		}
		return mailTextField;
	}

	private JLabel getMailLabel() {
		if (mailLabel == null) {
			mailLabel = new JLabel();
			mailLabel.setBounds(MAIL_LABEL_BOUNDS);
			mailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			mailLabel.setName(SynthFonts.PLAIN_FONT16_GRAY100_100_100);
		}
		return mailLabel;
	}

	class KeyListenerValidator extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			validateField((JComponent) e.getSource());
		}
	}

	private void validateField(JComponent source) {
		String errorMessage = controller.validateField(getLoginCommand(), source);
		String regex = "invalidTextField28";
		source.setToolTipText(errorMessage);
		if (errorMessage == null) {
			source.setName(source.getName().replaceAll(regex, ""));
			getSendButton().setEnabled(true);
		} else {
			source.setName(regex + source.getName());
			getSendButton().setEnabled(false);
		}
	}

	private LoginCommand getLoginCommand() {
		return new LoginCommand(getMailTextField().getText(), "");
	}

	@Override
	public void internationalize(Messages messages) {
		super.internationalize(messages);
		sendButton.setText(messages.getMessage("forgotPassword.send"));
		mailLabel.setText(messages.getMessage("forgotPassword.mail"));
		getTitleLabel().setText(messages.getMessage("forgotPassword.title"));
		getInstructionsLabel().setText(messages.getMessage("forgotPassword.instructions"));
	}

	public ObserverCollection<ObservValue<String>> onTrySendEmail() {
		return onTrySendEmailEvent;
	}
}
