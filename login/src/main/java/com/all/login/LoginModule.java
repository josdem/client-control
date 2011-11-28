package com.all.login;

import java.awt.BorderLayout;
import java.awt.Window;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.validation.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.all.app.ApplicationContext;
import com.all.app.ApplicationUtils;
import com.all.app.Attributes;
import com.all.app.Module;
import com.all.app.Spring;
import com.all.appControl.control.ControlEngine;
import com.all.core.common.model.ApplicationLanguage;
import com.all.core.common.services.ApplicationConfig;
import com.all.core.common.services.reporting.ReportSender;
import com.all.i18n.ConfigurableMessages;
import com.all.i18n.DefaultMessages;
import com.all.i18n.Messages;
import com.all.login.controller.LoginController;
import com.all.login.view.LoaderLoginPanel;
import com.all.login.view.LoginFrame;
import com.all.login.view.LoginMainPanel;
import com.all.login.view.TopPanelLogin;
import com.all.observ.ObservValue;
import com.all.observ.Observer;
import com.all.shared.model.User;
import com.all.shared.stats.AllStat;
import com.all.shared.stats.usage.UserActionStat;
import com.all.shared.stats.usage.UserActions;

public class LoginModule implements Module {
	private final static Log log = LogFactory.getLog(LoginModule.class);

	private ApplicationContext appContext;
	private ConfigurableMessages messages;
	private LoginFrame loaderFrame;
	private TopPanelLogin loaderTopPanel;

	public LoginModule() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("com.all.login.i18n.Messages");

		messages = new DefaultMessages(messageSource);
		ApplicationLanguage lang = ApplicationLanguage.getLanguage();
		messages.setLocale(lang.locale());
		createLoaderWindow(messages, null, null);
	}

	@Override
	public void activate() {
		log.info("activating module login");
		Spring spring = Spring.load("/com/all/login/LoginAppContext.xml", "/core/common/CommonAppContext.xml");
		spring.addBean(messages);
		appContext = spring.load();

		appContext.getBean(ControlEngine.class).start();

	}

	@Override
	public void execute(final Attributes attributes) {
		boolean isCleanInstall = isCleanInstallation();

		log.info("executing module login");
		LoginController loginController = appContext.getBean(LoginController.class);
		Validator validator = appContext.getBean(Validator.class);

		final LoginFrame loginFrame = new LoginFrame(messages);
		LoginMainPanel loginPanel = new LoginMainPanel(loginController, messages, validator);
		loginFrame.getContentPane().add(loginPanel, BorderLayout.CENTER);
		loginFrame.pack();

		loginController.onLoggedIn().add(new Observer<ObservValue<User>>() {
			@Override
			public void observe(ObservValue<User> t) {
				attributes.setAttribute(LoginAttributes.USER, t.getValue());
				loginFrame.close();
			}
		});
		ApplicationUtils.showFrameAndWaitForClose(loginFrame);
		if (isCleanInstall && attributes.getAttribute(LoginAttributes.USER) == null) {
			ReportSender sender = appContext.getBean(ReportSender.class);
			ArrayList<AllStat> stats = new ArrayList<AllStat>();
			UserActionStat stat = new UserActionStat();
			stat.setEmail("none@all.com");
			stat.setTimes(1);
			stat.setAction(UserActions.Errors.USER_DID_NOT_LOGIN_ON_FIRST_TIME);
			stats.add(stat);
			sender.send(stats);
		}
		createLoaderWindow(messages, loginFrame, loginPanel);
		appContext.getBean(ReportSender.class).waitForTermination();
	}

	@Override
	public void destroy() {
		log.info("destroying module login");
		messages.clean();
		appContext.close();
	}

	private Window createLoaderWindow(Messages messages, LoginFrame originalFrame, LoginMainPanel loginPanel) {
		loaderFrame = new LoginFrame(messages);
		loaderFrame.getContentPane().setName("loginPanel");
		loaderFrame.getContentPane().setLayout(new BorderLayout());
		LoaderLoginPanel loaderLoginPanel = new LoaderLoginPanel();
		loaderLoginPanel.internationalize(messages);

		loaderTopPanel = new TopPanelLogin();
		loaderTopPanel.internationalize(messages);
		loaderTopPanel.getPasswordField().setText("a1b2c3d4e5f6");

		loaderTopPanel.getUserComboBox().setEnabled(false);
		loaderTopPanel.getCheckBoxRemember().setEnabled(false);
		loaderTopPanel.getPasswordField().setEnabled(false);
		loaderTopPanel.getUserComboBox().setEnabled(false);
		loaderTopPanel.getForgotLabel().setEnabled(false);

		loaderFrame.add(loaderTopPanel, BorderLayout.NORTH);
		loaderFrame.add(loaderLoginPanel, BorderLayout.CENTER);

		if (originalFrame != null) {
			loaderFrame.setLocation(originalFrame.getLocation());
		}
		if (loginPanel != null) {
			List<String> comboModel = new ArrayList<String>();
			comboModel.add(loginPanel.getTopPanel().getUserComboBox().getSelectedItem().toString());
			loaderTopPanel.getUserComboBox().setModel(new DefaultComboBoxModel(comboModel.toArray()));
			loaderTopPanel.getCheckBoxRemember().setSelected(loginPanel.getTopPanel().getCheckBoxRemember().isSelected());
		}
		return loaderFrame;
	}

	public Window getLoaderWindow() {
		return loaderFrame;
	}

	private boolean isCleanInstallation() {
		ApplicationConfig appConfig = new ApplicationConfig();
		return isCleanInstallation(appConfig.getUserFolder());
	}

	private boolean isCleanInstallation(File f) {
		if (f.isFile() && !f.isHidden()) {
			return false;
		}
		if (f.isDirectory()) {
			for (File f2 : f.listFiles()) {
				if (!isCleanInstallation(f2)) {
					return false;
				}
			}
		}
		return true;
	}

}
