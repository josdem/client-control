package com.all.login;

import java.awt.Window;
import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.all.app.DefaultApplication;
import com.all.core.common.LookAndFeelAppListener;
import com.all.core.common.util.UserUtils;
import com.all.shared.model.User;

public class LoginApplication extends DefaultApplication<User> implements Callable<User> {
	private static final Log log = LogFactory.getLog(LoginApplication.class);
	private LoginModule loginModule;

	public LoginApplication() {
		super();
		loginModule = new LoginModule();
		this.setMainModule(loginModule);
		this.addAppListener(new LookAndFeelAppListener());
		this.setResultProcessor(new LoginResultProcessor());
	}

	public User call() {
		return execute();
	}

	public static void main(String[] args) throws Exception {
		if (LoginLock.getLock()) {
			User user = new LoginApplication().call();
			if (user != null) {
				UserUtils.saveUser(user, "/defaultUser.dbg");
			}
			log.info("Application stoped with exit code: " + user);
		}
	}

	public Window getLoaderWindow() {
		return loginModule.getLoaderWindow();
	}

}
