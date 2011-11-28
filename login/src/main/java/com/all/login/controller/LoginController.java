package com.all.login.controller;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JPasswordField;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.all.appControl.control.ControlEngine;
import com.all.commons.SoundPlayer.Sound;
import com.all.core.common.SystemConstants;
import com.all.core.common.bean.RegisterUserCommand;
import com.all.core.common.model.ApplicationModel;
import com.all.core.common.services.reporting.Reporter;
import com.all.core.common.util.PasswordDigest;
import com.all.login.model.Login;
import com.all.login.services.LoginMessageService;
import com.all.login.services.LoginModelDao;
import com.all.login.view.TopPanelLogin;
import com.all.observ.ObservValue;
import com.all.observ.Observable;
import com.all.observ.ObserverCollection;
import com.all.shared.command.LoginCommand;
import com.all.shared.exceptions.AccountNotFoundException;
import com.all.shared.login.LoginError;
import com.all.shared.messages.ForgotPasswordResult;
import com.all.shared.messages.LoginResponse;
import com.all.shared.messages.SignUpResult;
import com.all.shared.model.City;
import com.all.shared.model.User;
import com.all.shared.stats.AboutUsStat;

@Controller
public class LoginController {

	@Autowired
	private ControlEngine controlEngine;
	@Autowired
	private Validator validator;
	@Autowired
	private LoginModelDao loginDao;
	@Autowired
	private LoginMessageService loginMessageService;
	@Autowired
	private Properties clientSettings;

	private Observable<ObservValue<User>> loggedIn = new Observable<ObservValue<User>>();

	@Autowired
	private Reporter reporter;

	public String validateField(LoginCommand loginCommand, JComponent source) {
		Set<ConstraintViolation<LoginCommand>> violations = null;
		if (source instanceof JPasswordField) {
			violations = validator.validateProperty(loginCommand, "password");
		} else {
			violations = validator.validateProperty(loginCommand, "email");
		}
		if (violations.isEmpty()) {
			return null;
		}

		StringBuffer sb = new StringBuffer();
		for (ConstraintViolation<LoginCommand> violation : violations) {
			sb.append(violation.getMessage());
		}
		return sb.toString();
	}

	public boolean isLoginValid(LoginCommand loginCommand) {
		return validator.validate(loginCommand).isEmpty();
	}

	public ForgotPasswordResult forgotPassword(String mail) throws AccountNotFoundException {
		return loginMessageService.forgotPasswordRequest(mail);
	}

	public SignUpResult signUp(RegisterUserCommand user, int aboutUsIndex) {
		user.setEncryptedPwd(PasswordDigest.md5(user.getPassword().getBytes()));
		SignUpResult signupRequest = loginMessageService.signupRequest(user);
		if (signupRequest == SignUpResult.OK) {
			loginMessageService.updateAboutUs(aboutUsIndex);
			reporter.log(new AboutUsStat(user.getEmail(), aboutUsIndex));
		}
		return signupRequest;
	}

	public List<Login> getLastLogins() {
		return loginDao.getLastLogins();
	}

	public LoginError login(LoginCommand credentials, boolean isAutoLoginActivated) {
		LoginResponse response = null;
		credentials.setEncryptedPwd(PasswordDigest.md5(credentials.getPassword().getBytes()));
		if (isAutoLoginActivated && TopPanelLogin.DEFAULT_PWD.equals(credentials.getPassword())) {
			User user = loginDao.getUser(credentials.getEmail());
			if (user != null) {
				credentials.setEncryptedPwd(user.getPassword());
			}
		}
		try {
			if (!controlEngine.get(ApplicationModel.HAS_INTERNET_CONNECTION)) {
				throw new ConnectionException();
			}
			credentials.setPassword("");
			credentials.setVersion(getClientVersion());
			credentials.setOs(System.getProperty(SystemConstants.OS_NAME_PROP_KEY));
			credentials.setJvm(System.getProperty(SystemConstants.JAVA_VERSION_PROP_KEY));
			credentials.setTimezone(System.getProperty(SystemConstants.USER_TIMEZONE_PROP_KEY));
			credentials.setOsVersion(System.getProperty(SystemConstants.OS_VERSION_PROP_KEY));
			try {
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				credentials.setScreenSize(new StringBuilder().append(screenSize.width).append("x").append(screenSize.height)
						.toString());
			} catch (Exception e) {
			}

			response = loginMessageService.loginRequest(credentials);
			if (response.getError() == LoginError.CONNECTION_ERROR || response.getError() == LoginError.SERVER_DOWN) {
				throw new ConnectionException();
			}
			if (response.getError() == LoginError.UNEXPECTED) {
				throw new IllegalStateException();
			}
		} catch (Exception e) {
			response = localLogin(credentials, isAutoLoginActivated);
		}
		return manageLoginResponse(credentials, response, isAutoLoginActivated);
	}

	private LoginError manageLoginResponse(LoginCommand credentials, LoginResponse response, boolean isAutoLoginActivated) {
		if (!response.isSuccessful()) {
			return response.getError();
		} else {
			loginDao.registerNewLogin(response.getUser(), isAutoLoginActivated);
			Sound.LOGIN_WELCOME.play();
			loggedIn.fire(new ObservValue<User>(response.getUser()));
			return null;
		}
	}

	private String getClientVersion() {
		return clientSettings.getProperty(SystemConstants.ARTIFACT_VERSION_KEY);
	}

	private LoginResponse localLogin(LoginCommand credentials, boolean isAutoLoginActivated) {
		User localUser = loginDao.getUser(credentials.getEmail());
		if (localUser == null) {
			return new LoginResponse(LoginError.CONNECTION_ERROR);
		}
		if (isAutoLoginActivated) {
			if (localUser.getPassword().equals(credentials.getEncryptedPwd())) {
				return new LoginResponse(localUser, null, null);
			}
		} else if (comparePassword(localUser.getPassword(), credentials.getPassword())) {
			return new LoginResponse(localUser, null, null);
		}
		return new LoginResponse(LoginError.INVALID_CREDENTIALS);

	}

	private boolean comparePassword(String password, String password2) {
		return password.equals(PasswordDigest.md5(password2.getBytes()));
	}

	public List<City> findAllCities() {
		return loginDao.findAllCities();
	}

	public ObserverCollection<ObservValue<User>> onLoggedIn() {
		return loggedIn;
	}

	public boolean isConnected() {
		return controlEngine.get(ApplicationModel.HAS_INTERNET_CONNECTION);
	}

	public boolean isRememberMe(String email) {
		return loginDao.isRememberMe(email);
	}
}
