package com.all.login.view;

import java.awt.CardLayout;

import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.validation.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.all.core.common.bean.RegisterUserCommand;
import com.all.core.common.observables.SignUpEvent;
import com.all.i18n.Messages;
import com.all.login.controller.LoginController;
import com.all.observ.ObservValue;
import com.all.observ.Observable;
import com.all.observ.ObserveObject;
import com.all.observ.Observer;
import com.all.observ.ObserverCollection;
import com.all.shared.command.LoginCommand;
import com.all.shared.exceptions.AccountNotFoundException;
import com.all.shared.messages.ForgotPasswordResult;
import com.all.shared.messages.SignUpResult;

public final class CenterLoginPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	static final String CONNECTION_ERROR_PANEL_NAME = "connectionErrorPanel";

	static final String FORGOT_PASSWORD_PANEL_NAME = "forgotPasswordPanel";

	static final String INVALID_LOGIN_PANEL_NAME = "invalidLoginPanel";

	private static final String NEW_ACCOUNT_PANEL_NAME = "newAccountPanel";

	static final String SERVER_DOWN_PANEL_NAME = "serverDownPanel";

	static final String LOADER_PANEL_NAME = "loaderPanel";

	private static final String WRONG_EMAIL_PANEL_NAME = "wrongEmailPanel";

	private static final String PASSWORD_SENT_PANEL_NAME = "passwordSentPanel";

	private static final String DUPLICATE_EMAIL_PANEL_NAME = "duplicateEmailPanel";

	private CardLayout layout;

	private ConnectionErrorPanel connectionErrorPanel;

	private ForgotPasswordPanel forgotPasswordPanel;

	private InvalidLoginPanel invalidLoginPanel;

	private LoaderLoginPanel loaderLoginPanel;

	private final LoginController loginController;

	private NewAccountFormPanel newAccountFormPanel;

	private ServerDownPanel serverDownPanel;

	private WrongEmailPanel wrongEmailPanel;

	private PasswordSentPanel passwordSentPanel;

	private final Validator validator;

	private final Observable<ObservValue<LoginCommand>> onTryLoginEvent = new Observable<ObservValue<LoginCommand>>();

	private DuplicateEmailPanel duplicateEmailPanel;

	public CenterLoginPanel(LoginController loginController, Validator validator) {
		this.loginController = loginController;
		this.validator = validator;
		initialize();
	}

	private void initialize() {
		layout = new CardLayout();
		this.setLayout(layout);
		this.add(getNewAccountFormPanel(), NEW_ACCOUNT_PANEL_NAME);
		this.add(getServerDownPanel(), SERVER_DOWN_PANEL_NAME);
		this.add(getConnectionErrorPanel(), CONNECTION_ERROR_PANEL_NAME);
		this.add(getForgotPasswordPanel(), FORGOT_PASSWORD_PANEL_NAME);
		this.add(getInvalidLoginPanel(), INVALID_LOGIN_PANEL_NAME);
		this.add(getLoaderLoginPanel(), LOADER_PANEL_NAME);
		this.add(getWrongEmailPanel(), WRONG_EMAIL_PANEL_NAME);
		this.add(getSuccessPanel(), PASSWORD_SENT_PANEL_NAME);
		this.add(getDuplicateEmailPanel(), DUPLICATE_EMAIL_PANEL_NAME);
		setListeners();
	}

	private void setListeners() {
		getServerDownPanel().onSignUp().add(new CreateAccountListener(true));
		getConnectionErrorPanel().onSignUp().add(new CreateAccountListener(true));
		getInvalidLoginPanel().onSignUp().add(new CreateAccountListener(true));
		getForgotPasswordPanel().onSignUp().add(new CreateAccountListener(true));
		getDuplicateEmailPanel().onSignUp().add(new CreateAccountListener(false));
		getForgotPasswordPanel().onTrySendEmail().add(new Observer<ObservValue<String>>() {
			@Override
			public void observe(ObservValue<String> eventArgs) {
				String email = eventArgs.getValue();
				new ForgotPasswordWorker(email).execute();
			}
		});
		getWrongEmailPanel().onTrySendEmail().add(new Observer<ObservValue<String>>() {
			@Override
			public void observe(ObservValue<String> eventArgs) {
				String email = eventArgs.getValue();
				new ForgotPasswordWorker(email).execute();
			}
		});
		getNewAccountFormPanel().onTrySignUp().add(new Observer<SignUpEvent>() {
			@Override
			public void observe(SignUpEvent signUpEvent) {
				RegisterUserCommand userCommand = signUpEvent.getUser();
				int aboutUsIndex = signUpEvent.getAboutUsIndex();
				Submit submit = new Submit(userCommand, aboutUsIndex);
				submit.execute();
			}
		});
		getNewAccountFormPanel().onConnectionErrorEvent().add(new Observer<ObserveObject>() {
			@Override
			public void observe(ObserveObject eventArgs) {
				showPanel(CONNECTION_ERROR_PANEL_NAME);
			}
		});
	}

	void showPanel(String namePanel) {
		layout.show(this, namePanel);
	}

	private NewAccountFormPanel getNewAccountFormPanel() {
		if (newAccountFormPanel == null) {
			newAccountFormPanel = new NewAccountFormPanel(validator, loginController);
		}
		return newAccountFormPanel;
	}

	private ServerDownPanel getServerDownPanel() {
		if (serverDownPanel == null) {
			serverDownPanel = new ServerDownPanel();
		}
		return serverDownPanel;
	}

	private ConnectionErrorPanel getConnectionErrorPanel() {
		if (connectionErrorPanel == null) {
			connectionErrorPanel = new ConnectionErrorPanel();
		}
		return connectionErrorPanel;
	}

	private ForgotPasswordPanel getForgotPasswordPanel() {
		if (forgotPasswordPanel == null) {
			forgotPasswordPanel = new ForgotPasswordPanel(loginController);
		}
		return forgotPasswordPanel;
	}

	private InvalidLoginPanel getInvalidLoginPanel() {
		if (invalidLoginPanel == null) {
			invalidLoginPanel = new InvalidLoginPanel();
		}
		return invalidLoginPanel;
	}

	private LoaderLoginPanel getLoaderLoginPanel() {
		if (loaderLoginPanel == null) {
			loaderLoginPanel = new LoaderLoginPanel();
		}
		return loaderLoginPanel;
	}

	private WrongEmailPanel getWrongEmailPanel() {
		if (wrongEmailPanel == null) {
			wrongEmailPanel = new WrongEmailPanel(loginController);
		}
		return wrongEmailPanel;
	}

	private PasswordSentPanel getSuccessPanel() {
		if (passwordSentPanel == null) {
			passwordSentPanel = new PasswordSentPanel();
		}
		return passwordSentPanel;
	}

	private DuplicateEmailPanel getDuplicateEmailPanel() {
		if (duplicateEmailPanel == null) {
			duplicateEmailPanel = new DuplicateEmailPanel();
		}
		return duplicateEmailPanel;
	}

	void setMessages(Messages messages) {
		newAccountFormPanel.setMessages(messages);
	}

	public void internationalize(Messages messages) {
		newAccountFormPanel.internationalize(messages);
		serverDownPanel.internationalize(messages);
		connectionErrorPanel.internationalize(messages);
		forgotPasswordPanel.internationalize(messages);
		invalidLoginPanel.internationalize(messages);
		loaderLoginPanel.internationalize(messages);
		wrongEmailPanel.internationalize(messages);
		passwordSentPanel.internationalize(messages);
		duplicateEmailPanel.internationalize(messages);
	}

	private class CreateAccountListener implements Observer<ObserveObject> {

		private final boolean reset;

		public CreateAccountListener(boolean reset) {
			this.reset = reset;
		}

		@Override
		public void observe(ObserveObject eventArgs) {
			if (reset) {
				getNewAccountFormPanel().resetFormPanel();
			}
			showPanel(NEW_ACCOUNT_PANEL_NAME);
		}
	}

	public ObserverCollection<ObservValue<LoginCommand>> onTryLogin() {
		return onTryLoginEvent;
	}

	class ForgotPasswordWorker extends SwingWorker<ForgotPasswordResult, Void> {
		private String email;
		private Log log = LogFactory.getLog(ForgotPasswordWorker.class);

		public ForgotPasswordWorker(String email) {
			this.email = email;
		}

		@Override
		protected ForgotPasswordResult doInBackground() throws AccountNotFoundException {
			if (loginController.isConnected()) {
				return loginController.forgotPassword(email);
			} else {
				return ForgotPasswordResult.CONNECTION_ERROR;
			}
		}

		@Override
		protected void done() {
			try {
				ForgotPasswordResult source = get();
				switch (source) {
				case OK:
					showPanel(PASSWORD_SENT_PANEL_NAME);
					break;
				case ACCOUNT_NOT_FOUND:
					email = getForgotPasswordPanel().getMailTextField().getText();
					getWrongEmailPanel().getMailTextField().setText(email);
					showPanel(WRONG_EMAIL_PANEL_NAME);
					break;
				case CONNECTION_ERROR:
					showPanel(CONNECTION_ERROR_PANEL_NAME);
					break;
				default:
					showPanel(SERVER_DOWN_PANEL_NAME);
				}
				getForgotPasswordPanel().getMailTextField().setText("");
			} catch (Exception e) {
				log.error(e, e);
			}
		}
	}

	class Submit extends SwingWorker<SignUpResult, Void> {

		private final RegisterUserCommand userCommand;

		private final int aboutUsIndex;

		public Submit(RegisterUserCommand userCommand, int aboutUsIndex) {
			this.userCommand = userCommand;
			this.aboutUsIndex = aboutUsIndex;
		}

		@Override
		protected SignUpResult doInBackground() {
			showPanel(LOADER_PANEL_NAME);
			return loginController.signUp(userCommand, aboutUsIndex);
		}

		@Override
		protected void done() {
			SignUpResult status = null;
			try {
				status = get();
			} catch (Exception e) {
				// log.error(e, e);
				return;
			}

			switch (status) {
			case OK:
				onTryLoginEvent.fire(new ObservValue<LoginCommand>(getNewAccountFormPanel().getLoginCommand()));
				getNewAccountFormPanel().resetFormPanel();
				break;
			case USER_ALREADY_REGISTERED:
				showPanel(DUPLICATE_EMAIL_PANEL_NAME);
				break;
			default:
				showPanel(SERVER_DOWN_PANEL_NAME);
			}
		}
	}
}
