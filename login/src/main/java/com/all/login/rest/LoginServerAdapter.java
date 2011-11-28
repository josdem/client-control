package com.all.login.rest;

import static com.all.shared.messages.MessEngineConstants.LOGIN_ABOUT_US_REQUEST_TYPE;
import static com.all.shared.messages.MessEngineConstants.LOGIN_PASSWORD_REQUEST_TYPE;
import static com.all.shared.messages.MessEngineConstants.LOGIN_PASSWORD_RESPONSE_TYPE;
import static com.all.shared.messages.MessEngineConstants.LOGIN_RESPONSE_TYPE;
import static com.all.shared.messages.MessEngineConstants.LOGIN_SIGNUP_REQUEST_TYPE;
import static com.all.shared.messages.MessEngineConstants.LOGIN_SIGNUP_RESPONSE_TYPE;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.all.core.common.messages.ErrorMessage;
import com.all.messengine.MessEngine;
import com.all.messengine.MessageMethod;
import com.all.shared.command.LoginCommand;
import com.all.shared.json.JsonConverter;
import com.all.shared.login.LoginError;
import com.all.shared.messages.LoginResponse;
import com.all.shared.messages.MessEngineConstants;
import com.all.shared.model.AllMessage;
import com.all.shared.model.PendingEmail;
import com.all.shared.model.User;
import com.all.shared.stats.AllStat;

@Component
public class LoginServerAdapter {

	private final Log log = LogFactory.getLog(this.getClass());

	private static final String EMAIL_SERVER_URI = "email_server_uri";
	private static final String ALL_SERVER_URL = "all.server.url";

	@Autowired
	private Properties clientSettings;
	@Autowired
	private MessEngine messEngine;

	private final RestTemplate defaultTemplate = new RestTemplate();

	@PostConstruct
	public void initialize() {
		log.debug("LOGIN-SERVER-ADAPTER: INIT COMPLETE [All Server] :" + clientSettings.getProperty(ALL_SERVER_URL));
		log.debug("LOGIN-SERVER-ADAPTER: INIT COMPLETE [Email Server] :" + clientSettings.getProperty(EMAIL_SERVER_URI));
	}

	@MessageMethod(MessEngineConstants.LOGIN_REQUEST_TYPE)
	public void postLogin(LoginCommand loginCommand) {
		log.info("Processing login request from " + loginCommand.getEmail());
		LoginResponse loginResponse = null;
		try {
			loginResponse = JsonConverter.toBean(defaultTemplate.postForObject(getUrl(ALL_SERVER_URL, "loginUrl"),
					JsonConverter.toJson(loginCommand), String.class), LoginResponse.class);
		} catch (Exception e) {
			loginResponse = new LoginResponse(LoginError.SERVER_DOWN);
		}
		log.debug("login response : " + ToStringBuilder.reflectionToString(loginResponse));
		messEngine.send(new AllMessage<Serializable>(LOGIN_RESPONSE_TYPE, (Serializable) loginResponse));
	}

	@MessageMethod(LOGIN_SIGNUP_REQUEST_TYPE)
	public void processSignupRequest(User user) {
		log.info("Processing a signup request.");
		String result = defaultTemplate.postForObject(getUrl(ALL_SERVER_URL, "signupUrl"), JsonConverter.toJson(user),
				String.class);
		AllMessage<String> responseMessage = new AllMessage<String>(LOGIN_SIGNUP_RESPONSE_TYPE, result);
		messEngine.send(responseMessage);
		//TODO Move this to all server JC.
		if (result.contains("OK")) {
			defaultTemplate.put(getUrl(EMAIL_SERVER_URI, "emailSignup"), JsonConverter.toJson(createPendingEmail(user)));
		}
	}

	@MessageMethod(LOGIN_PASSWORD_REQUEST_TYPE)
	public void processForgotPasswordRequest(String email) {
		log.info("Processing a forgot password request for user " + email);
		String forgotPasswordResult = defaultTemplate.postForObject("forgotPasswordUrl", email, String.class);
		log.debug("forgotPasswordResult: " + forgotPasswordResult);
		String url = "";
		String result = "";
		String[] resultAsarray = forgotPasswordResult.split(";");
		if (resultAsarray.length == 2) {
			result = resultAsarray[0];
			url = resultAsarray[1];
			PendingEmail pendingEmail = createPasswordPendingEmail(url, email);
			defaultTemplate.put(getUrl(EMAIL_SERVER_URI, "emailPassword"), pendingEmail);
		} else {
			result = forgotPasswordResult;
		}
		AllMessage<String> responseMessage = new AllMessage<String>(LOGIN_PASSWORD_RESPONSE_TYPE, result);
		messEngine.send(responseMessage);
	}

	@MessageMethod(LOGIN_ABOUT_US_REQUEST_TYPE)
	public void registerAboutUsStat(String aboutUs) {
		defaultTemplate.put(getUrl(ALL_SERVER_URL, "aboutUsUrl"), aboutUs);
	}

	@MessageMethod(MessEngineConstants.USAGE_STATS_TYPE)
	public void sendStats(AllMessage<List<AllStat>> message) {
		try {
			defaultTemplate.put(getUrl(ALL_SERVER_URL, "stats.put"), JsonConverter.toJson(message.getBody()));
			// TODO
			// NO BODY IS LISTENING FOR THIS MESSAGE
			// messEngine.send(new ResponseMessage("stats-sent", message));
		} catch (Exception e) {
			messEngine.send(new ErrorMessage(message));
		}
	}

	private PendingEmail createPasswordPendingEmail(String url, String email) {
		PendingEmail pendingEmail = new PendingEmail();
		pendingEmail.setToMail(email);
		pendingEmail.setUrl(url);
		return pendingEmail;
	}

	private PendingEmail createPendingEmail(User user) {
		PendingEmail pendingEmail = new PendingEmail();
		pendingEmail.setFullName(user.getNickName());
		pendingEmail.setToMail(user.getEmail());
		return pendingEmail;
	}

	private String getUrl(String server, String urlKey) {
		return clientSettings.getProperty(server) + clientSettings.getProperty(urlKey);
	}
}
