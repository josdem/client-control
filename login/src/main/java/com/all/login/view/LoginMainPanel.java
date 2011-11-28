package com.all.login.view;

import java.awt.BorderLayout;
import java.util.concurrent.ExecutionException;

import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.validation.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.all.i18n.Messages;
import com.all.login.controller.LoginController;
import com.all.observ.ObservValue;
import com.all.observ.ObserveObject;
import com.all.observ.Observer;
import com.all.shared.command.LoginCommand;
import com.all.shared.login.LoginError;

public final class LoginMainPanel extends JPanel {

	private static final Log LOG = LogFactory.getLog(LoginMainPanel.class);

	private static final long serialVersionUID = 1L;

	private CenterLoginPanel centerPanel;

	private TopPanelLogin topPanel;

	private Messages messages;

	private LoginController loginController;

	private Validator validator;

	public LoginMainPanel() {
		initialize();
	}

	public LoginMainPanel(LoginController loginController, Messages messages, Validator validator) {
		this.loginController = loginController;
		this.messages = messages;
		this.validator = validator;
		initialize();
	}

	private void initialize() {
		this.setLayout(new BorderLayout());
		this.add(getTopPanel(), BorderLayout.NORTH);
		this.add(getCenterPanel(), BorderLayout.CENTER);
		getTopPanel().onForgotPassword().add(new Observer<ObserveObject>() {
			@Override
			public void observe(ObserveObject eventArgs) {
				getCenterPanel().showPanel(CenterLoginPanel.FORGOT_PASSWORD_PANEL_NAME);
			}
		});
		getTopPanel().onTryLogin().add(new Observer<ObservValue<LoginCommand>>() {
			@Override
			public void observe(ObservValue<LoginCommand> eventArgs) {
				login(eventArgs);
			}

		});
		getCenterPanel().onTryLogin().add(new Observer<ObservValue<LoginCommand>>() {
			@Override
			public void observe(ObservValue<LoginCommand> eventArgs) {
				LoginCommand loginCommand = eventArgs.getValue();
				getTopPanel().getUserComboBox().setSelectedItem(loginCommand.getEmail());
				getTopPanel().getPasswordField().setText(loginCommand.getPassword());
				LoginWorker loginWorker = new LoginWorker(loginCommand);
				loginWorker.execute();
			}
		});
	}

	private void login(ObservValue<LoginCommand> eventArgs) {
		LoginCommand loginCommand = eventArgs.getValue();
		LoginWorker loginWorker = new LoginWorker(loginCommand);
		loginWorker.execute();
	}

	private CenterLoginPanel getCenterPanel() {
		if (centerPanel == null) {
			centerPanel = new CenterLoginPanel(loginController, validator);
			centerPanel.setMessages(messages);
			centerPanel.internationalize(messages);
		}
		return centerPanel;
	}

	public TopPanelLogin getTopPanel() {
		if (topPanel == null) {
			topPanel = new TopPanelLogin(loginController);
			topPanel.internationalize(messages);
		}
		return topPanel;
	}

	public void setLogButtonFocus() {
		getTopPanel().setLogButtonFocus();
	}

	public void showPanel() {
		getCenterPanel().showPanel(CenterLoginPanel.LOADER_PANEL_NAME);
	}

	class LoginWorker extends SwingWorker<LoginError, Void> {
		private final LoginCommand loginCommand;

		public LoginWorker(LoginCommand loginCommand) {
			this.loginCommand = loginCommand;
		}

		@Override
		protected LoginError doInBackground() {
			// onLoaderEvent.fireEvent(new EventObject(this));
			getCenterPanel().showPanel(CenterLoginPanel.LOADER_PANEL_NAME);
			return loginController.login(loginCommand, getTopPanel().getCheckBoxRemember().isSelected());
		}

		@Override
		protected void done() {
			LoginError error = null;
			try {
				error = get();
			} catch (InterruptedException e) {
				LOG.error(e, e);
			} catch (ExecutionException e) {
				LOG.error(e, e);
			}
			if (error == null) {
				getTopPanel().resetPanel();
			} else {
				switch (error) {
				case CONNECTION_ERROR:
					getCenterPanel().showPanel(CenterLoginPanel.CONNECTION_ERROR_PANEL_NAME);
					return;
				case SERVER_DOWN:
					getCenterPanel().showPanel(CenterLoginPanel.SERVER_DOWN_PANEL_NAME);
					return;
				case INVALID_CREDENTIALS:
					getCenterPanel().showPanel(CenterLoginPanel.INVALID_LOGIN_PANEL_NAME);
					return;
					// case NOT_CONFIRMED:
					// showNotConfirmedMailMessage();
					// return;
				case UNEXPECTED:
					break;
				}
			}
		}
	}
}
