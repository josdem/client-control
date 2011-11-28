package com.all.login.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import com.all.core.common.view.SynthFonts;
import com.all.core.common.view.util.SelectedTextForeground;
import com.all.i18n.Messages;
import com.all.login.controller.LoginController;
import com.all.login.model.Login;
import com.all.observ.ObservValue;
import com.all.observ.Observable;
import com.all.observ.ObserveObject;
import com.all.observ.ObserverCollection;
import com.all.shared.command.LoginCommand;

public final class TopPanelLogin extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final Dimension DEFAULT_SIZE = new Dimension(736, 82);

	private static final Rectangle CHECKBOX_REMEMBER_BOUNDS = new Rectangle(410, 61, 250, 16);

	private static final Rectangle FORGOT_LABEL_BOUNDS = new Rectangle(201, 61, 188, 17);

	private static final Rectangle LOGIN_BUTTON_BOUNDS = new Rectangle(615, 28, 109, 28);

	private static final Rectangle LOGO_PANEL_BOUNDS = new Rectangle(0, 0, 185, 80);

	private static final Rectangle PASSWORD_LABEL_BOUNDS = new Rectangle(401, 5, 200, 22);

	private static final Rectangle PASSWORD_TEXTFIELD_BOUNDS = new Rectangle(401, 28, 200, 28);

	private static final Rectangle USER_COMBOBOX_BOUNDS = new Rectangle(189, 29, 200, 28);

	private static final Rectangle USER_LABEL_BOUNDS = new Rectangle(189, 7, 200, 22);

	private static final String CHECKBOX_REMEMBER_NAME = "loginCheckBox";

	public static final String DEFAULT_PWD = "zAyBxCwDvEu";

	private static final String LOGIN_BUTTON_NAME = "buttonTopLoginLog";

	private static final String PASSWORD_TEXT_FIELD_NAME = "passwordTopLoginField";

	private static final String USER_COMBOBOX_NAME = "emailComboBox";

	private final Observable<ObservValue<LoginCommand>> onTryLoginEvent = new Observable<ObservValue<LoginCommand>>();

	private JButton loginButton;

	private JCheckBox checkBoxRemember;

	private JComboBox userComboBox;

	private JLabel passwordLabel;

	private JLabel userLabel;

	private JPanel logoPanel;

	private JPasswordField passwordTextField;

	private LoginController loginController;

	private JLabel forgotLabel;

	private final Observable<ObserveObject> forgotPasswordEvent = new Observable<ObserveObject>();

	public TopPanelLogin() {
		initialize();
	}

	public TopPanelLogin(LoginController loginController) {
		this.loginController = loginController;
		initialize();
	}

	private void initialize() {

		this.setSize(DEFAULT_SIZE);
		this.setMinimumSize(DEFAULT_SIZE);
		this.setPreferredSize(DEFAULT_SIZE);
		this.setMaximumSize(DEFAULT_SIZE);
		this.setLayout(null);

		this.add(getLogoPanel());

		this.add(getUserLabel());
		this.add(getUserComboBox());

		this.add(getPasswordLabel());
		this.add(getPasswordField());
		this.add(getForgotLabel());
		this.add(getLoginButton());
		this.add(getCheckBoxRemember());
	}

	private JPanel getLogoPanel() {
		if (logoPanel == null) {
			logoPanel = new JPanel();
			logoPanel.setBounds(LOGO_PANEL_BOUNDS);
		}
		return logoPanel;
	}

	public JComboBox getUserComboBox() {
		if (userComboBox == null) {
			if (loginController != null) {
				userComboBox = new JComboBox(loginController.getLastLogins().toArray());
				setPasswordIfAutoLogin();
			} else {
				userComboBox = new JComboBox();
			}
			userComboBox.setBounds(USER_COMBOBOX_BOUNDS);
			userComboBox.setEditable(true);
			userComboBox.setName(USER_COMBOBOX_NAME);
			userComboBox.addFocusListener(new FocusListenerValidator());
			userComboBox.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					setPasswordIfAutoLogin();
				}
			});
		}
		return userComboBox;
	}

	private JLabel getUserLabel() {
		if (userLabel == null) {
			userLabel = new JLabel();
			userLabel.setName(SynthFonts.BOLD_FONT14_GRAY90_90_90);
			userLabel.setBounds(USER_LABEL_BOUNDS);
		}
		return userLabel;
	}

	public JCheckBox getCheckBoxRemember() {
		if (checkBoxRemember == null) {
			checkBoxRemember = new JCheckBox();
			checkBoxRemember.setBounds(CHECKBOX_REMEMBER_BOUNDS);
			checkBoxRemember.setName(CHECKBOX_REMEMBER_NAME);
		}
		return checkBoxRemember;
	}

	public void internationalize(Messages messages) {
		loginButton.setText(messages.getMessage("login.login"));
		userLabel.setText(messages.getMessage("login.userId"));
		passwordLabel.setText(messages.getMessage("login.password"));
		forgotLabel.setText(messages.getMessage("login.forgot"));
		checkBoxRemember.setText(messages.getMessage("login.remember"));
		this.setName(messages.getMessage("login.TopPanelLogin.name"));
		this.invalidate();
		this.revalidate();
	}

	private void setPasswordIfAutoLogin() {
		String email = getEmailSelected();
		if (email != null && email.length() > 0) {
			getCheckBoxRemember().setSelected(false);
			getPasswordField().setText("");
			getLoginButton().setEnabled(false);
			if (loginController.isRememberMe(email)) {
				getCheckBoxRemember().setSelected(true);
				getPasswordField().setText(DEFAULT_PWD);
				getLoginButton().setEnabled(true);
			}
		}
	}

	private String getEmailSelected() {
		Object selectedItem = getUserComboBox().getSelectedItem();
		return selectedItem != null ? selectedItem.toString() : "";
	}

	private JButton getLoginButton() {
		if (loginButton == null) {
			loginButton = new JButton();
			loginButton.setName(LOGIN_BUTTON_NAME);
			loginButton.setBounds(LOGIN_BUTTON_BOUNDS);
			loginButton.setEnabled(false);
			loginButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					onTryLoginEvent.fire(new ObservValue<LoginCommand>(getLoginCommand()));
				}
			});
		}
		return loginButton;
	}

	private JLabel getPasswordLabel() {
		if (passwordLabel == null) {
			passwordLabel = new JLabel();
			passwordLabel.setName(SynthFonts.BOLD_FONT14_GRAY90_90_90);
			passwordLabel.setBounds(PASSWORD_LABEL_BOUNDS);
		}
		return passwordLabel;
	}

	public JPasswordField getPasswordField() {
		if (passwordTextField == null) {
			passwordTextField = new JPasswordField();
			passwordTextField.setBounds(PASSWORD_TEXTFIELD_BOUNDS);
			passwordTextField.setSelectionColor(SelectedTextForeground.SELECTED_FOREGROUND_COLOR);
			passwordTextField.setName(PASSWORD_TEXT_FIELD_NAME);
			passwordTextField.addFocusListener(new FocusListenerValidator());
			passwordTextField.addKeyListener(new PasswordKeyListenerValidator());
			passwordTextField.setEchoChar('\u25CF');
		}
		return passwordTextField;
	}

	private LoginCommand getLoginCommand() {
		String email = getEmailSelected();
		String password = new String(getPasswordField().getPassword());
		return new LoginCommand(email, password);
	}

	private void validateAll() {
		getLoginButton().setEnabled(loginController.isLoginValid(getLoginCommand()));
	}

	class FocusListenerValidator extends FocusAdapter {
		@Override
		public void focusLost(FocusEvent e) {
			validateField((JComponent) e.getSource());
			validateAll();
		}

		@Override
		public void focusGained(FocusEvent e) {
			validateField(getUserComboBox());
			super.focusGained(e);
		}

	}

	private void validateField(JComponent source) {
		String errorMessage = loginController.validateField(getLoginCommand(), source);
		String regex = "invalidTopTextField";
		source.setToolTipText(errorMessage);
		if (errorMessage == null) {
			source.setName(source.getName().replaceAll(regex, ""));
		} else {
			source.setName(regex + source.getName());
		}
	}

	public JLabel getForgotLabel() {
		if (forgotLabel == null) {
			forgotLabel = new JLabel();
			forgotLabel.setName(SynthFonts.PLAIN_FONT12_WHITE);
			forgotLabel.setBounds(FORGOT_LABEL_BOUNDS);
			forgotLabel.addMouseListener(new LinkMouseAdapter());
		}
		return forgotLabel;
	}

	void resetPanel() {
		getPasswordField().setText("");
		getUserComboBox().removeAllItems();
		for (Login login : loginController.getLastLogins()) {
			getUserComboBox().addItem(login);
		}
		getLoginButton().setEnabled(false);
	}

	class LinkMouseAdapter extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			forgotPasswordEvent.fire(ObserveObject.EMPTY);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			setCursor(new Cursor(Cursor.HAND_CURSOR));
			forgotLabel.setName(SynthFonts.BOLD_FONT12_WHITE);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			forgotLabel.setName(SynthFonts.PLAIN_FONT12_WHITE);
		}
	}

	class PasswordKeyListenerValidator extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			if (getPasswordField().getPassword().length > 7) {
				validateField(getPasswordField());
			}
			validateAll();
			if (KeyEvent.VK_ENTER == e.getKeyCode() && getLoginButton().isEnabled()) {
				onTryLoginEvent.fire(new ObservValue<LoginCommand>(getLoginCommand()));
			}
		}
	}

	public void setLogButtonFocus() {
		getLoginButton().requestFocus();
	}

	public ObserverCollection<ObservValue<LoginCommand>> onTryLogin() {
		return onTryLoginEvent;
	}

	public ObserverCollection<ObserveObject> onForgotPassword() {
		return forgotPasswordEvent;
	}

}
