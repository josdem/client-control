package com.all.login.services;

import static com.all.shared.messages.MessEngineConstants.LOGIN_ABOUT_US_REQUEST_TYPE;
import static com.all.shared.messages.MessEngineConstants.LOGIN_PASSWORD_REQUEST_TYPE;
import static com.all.shared.messages.MessEngineConstants.LOGIN_PASSWORD_RESPONSE_TYPE;
import static com.all.shared.messages.MessEngineConstants.LOGIN_REQUEST_TYPE;
import static com.all.shared.messages.MessEngineConstants.LOGIN_RESPONSE_TYPE;
import static com.all.shared.messages.MessEngineConstants.LOGIN_SIGNUP_REQUEST_TYPE;
import static com.all.shared.messages.MessEngineConstants.LOGIN_SIGNUP_RESPONSE_TYPE;

import java.io.Serializable;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.all.core.common.bean.RegisterUserCommand;
import com.all.messengine.MessEngine;
import com.all.messengine.Message;
import com.all.messengine.MessageListener;
import com.all.shared.command.LoginCommand;
import com.all.shared.login.LoginError;
import com.all.shared.messages.ForgotPasswordResult;
import com.all.shared.messages.LoginResponse;
import com.all.shared.messages.SignUpResult;
import com.all.shared.model.AllMessage;

@Service
public class LoginMessageService {

	private static final int REQUEST_TIMEOUT = 20;

	private final Log log = LogFactory.getLog(this.getClass());

	private final CyclicBarrier messageGate = new CyclicBarrier(2);

	private LoginResponse loginResponse;

	private ForgotPasswordResult forgotPasswordResult;

	private String signupResponse;

	@Autowired
	private MessEngine messEngine;

	@PostConstruct
	public void registerListeners() {
		messEngine.addMessageListener(LOGIN_RESPONSE_TYPE, new MessageListener<AllMessage<Serializable>>() {
			@Override
			public void onMessage(AllMessage<Serializable> message) {
				processLoginResponse(message);
			}
		});
		messEngine.addMessageListener(LOGIN_SIGNUP_RESPONSE_TYPE, new MessageListener<AllMessage<String>>() {
			@Override
			public void onMessage(AllMessage<String> message) {
				processSignupResponse(message);
			}
		});
		messEngine.addMessageListener(LOGIN_PASSWORD_RESPONSE_TYPE, new MessageListener<AllMessage<String>>() {
			@Override
			public void onMessage(AllMessage<String> message) {
				forgotPasswordResult = ForgotPasswordResult.valueOf(message.getBody());
				releaseGate();
			}
		});

	}

	public LoginResponse loginRequest(LoginCommand credentials) {
		AllMessage<LoginCommand> message = new AllMessage<LoginCommand>(LOGIN_REQUEST_TYPE, credentials);
		sendAndWait(message);
		return getResult();
	}

	public SignUpResult signupRequest(RegisterUserCommand userCommand) {
		sendAndWait(new AllMessage<Serializable>(LOGIN_SIGNUP_REQUEST_TYPE, userCommand.toUser()));
		return getSignupResult();
	}

	public void updateAboutUs(int aboutUsIndex) {
		messEngine.send(new AllMessage<Serializable>(LOGIN_ABOUT_US_REQUEST_TYPE, "" + aboutUsIndex));
	}

	public ForgotPasswordResult forgotPasswordRequest(String mail) {
		AllMessage<String> forgotPasswordRequest = new AllMessage<String>(LOGIN_PASSWORD_REQUEST_TYPE, mail);
		sendAndWait(forgotPasswordRequest);
		return getForgotPasswordResponse();
	}

	private ForgotPasswordResult getForgotPasswordResponse() {
		return forgotPasswordResult;
	}

	private SignUpResult getSignupResult() {
		return SignUpResult.valueOf(signupResponse);
	}

	private LoginResponse getResult() {
		return loginResponse;
	}

	private void sendAndWait(Message<?> message) {
		messageGate.reset();
		messEngine.send(message);
		try {
			messageGate.await(REQUEST_TIMEOUT, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			log.error(e, e);
			loginResponse = new LoginResponse(LoginError.UNEXPECTED);
		} catch (BrokenBarrierException e) {
			log.error(e, e);
			loginResponse = new LoginResponse(LoginError.UNEXPECTED);
		} catch (TimeoutException e) {
			log.error(e, e);
			loginResponse = new LoginResponse(LoginError.SERVER_DOWN);
		}
	}

	private void processLoginResponse(AllMessage<Serializable> message) {
		loginResponse = (LoginResponse) message.getBody();
		releaseGate();
	}

	private void processSignupResponse(AllMessage<String> message) {
		signupResponse = message.getBody();
		releaseGate();
	}

	private void releaseGate() {
		try {
			messageGate.await();
		} catch (InterruptedException ignore) {
			log.warn("response no longer needed");
		} catch (BrokenBarrierException ignore) {
			log.warn("response no longer needed");
		}
	}
}
