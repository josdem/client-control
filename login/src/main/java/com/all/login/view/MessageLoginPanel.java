package com.all.login.view;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.all.core.common.view.SynthFonts;
import com.all.i18n.Messages;
import com.all.observ.ObserveObject;
import com.all.observ.Observable;
import com.all.observ.ObserverCollection;

public class MessageLoginPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final Rectangle CREATE_ACCOUNT_LABEL_BOUNDS = new Rectangle(336, 281, 400, 22);

	private static final Rectangle INSTRUCTIONS_LABEL_BOUNDS = new Rectangle(336, 87, 400, 132);

	private static final Rectangle NEW_USER_LABEL_BOUNDS = new Rectangle(336, 259, 400, 22);

	private static final Rectangle SIGN_UP_BUTTON_BOUNDS = new Rectangle(461, 319, 150, 34);

	private static final Rectangle TITLE_LABEL_BOUNDS = new Rectangle(336, 38, 400, 33);

	private static final String SIGN_UP_BUTTON_NAME = "buttonCenterLoginSignUp";

	private final Observable<ObserveObject> signUpEvent = new Observable<ObserveObject>();

	private JButton signUpButton;

	private JLabel createAccountLabel;

	private JLabel newUserLabel;

	private JLabel titleLabel;

	private JTextPane instructionsLabel;

	public MessageLoginPanel() {
		initialize();
	}

	protected void initialize() {
		this.setLayout(null);
		this.add(getTitleLabel());
		this.add(getInstructionsLabel());
		this.add(getNewUserLabel());
		this.add(getCreateAccountLabel());
		this.add(getSignUpButton());
	}

	protected JLabel getTitleLabel() {
		if (titleLabel == null) {
			titleLabel = new JLabel();
			titleLabel.setBounds(TITLE_LABEL_BOUNDS);
			titleLabel.setName(SynthFonts.BOLD_FONT28_PURPLE82_33_117);
			titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return titleLabel;
	}

	protected JTextPane getInstructionsLabel() {
		if (instructionsLabel == null) {
			instructionsLabel = new JTextPane();
			instructionsLabel.setBounds(INSTRUCTIONS_LABEL_BOUNDS);
			instructionsLabel.setName(SynthFonts.PLAIN_FONT16_GRAY100_100_100);
			instructionsLabel.setEditable(false);
			instructionsLabel.setEnabled(false);
			StyledDocument doc = instructionsLabel.getStyledDocument();
			SimpleAttributeSet center = new SimpleAttributeSet();
			StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
			doc.setParagraphAttributes(0, doc.getLength(), center, false);
		}
		return instructionsLabel;
	}

	protected JLabel getNewUserLabel() {
		if (newUserLabel == null) {
			newUserLabel = new JLabel();
			newUserLabel.setBounds(NEW_USER_LABEL_BOUNDS);
			newUserLabel.setHorizontalAlignment(SwingConstants.CENTER);
			newUserLabel.setName(SynthFonts.BOLD_FONT18_PURPLE82_33_117);
		}
		return newUserLabel;
	}

	protected JLabel getCreateAccountLabel() {
		if (createAccountLabel == null) {
			createAccountLabel = new JLabel();
			createAccountLabel.setBounds(CREATE_ACCOUNT_LABEL_BOUNDS);
			createAccountLabel.setHorizontalAlignment(SwingConstants.CENTER);
			createAccountLabel.setName(SynthFonts.BOLD_FONT16_PURPLE82_33_117);
		}
		return createAccountLabel;
	}

	protected JButton getSignUpButton() {
		if (signUpButton == null) {
			signUpButton = new JButton();
			signUpButton.setBounds(SIGN_UP_BUTTON_BOUNDS);
			signUpButton.setName(SIGN_UP_BUTTON_NAME);
			signUpButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					signUpEvent.fire(ObserveObject.EMPTY);
				}
			});
		}
		return signUpButton;
	}

	protected void internationalize(Messages messages) {
		getSignUpButton().setText(messages.getMessage("forgotPassword.tryAgain"));
		getCreateAccountLabel().setText(messages.getMessage("login.newUser.createAccount.instructions"));
		getNewUserLabel().setText(messages.getMessage("login.newUser"));
	}

	public ObserverCollection<ObserveObject> onSignUp() {
		return signUpEvent;
	}

}
