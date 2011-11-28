package com.all.login.services;

import static com.all.shared.messages.MessEngineConstants.LOGIN_PASSWORD_REQUEST_TYPE;
import static com.all.shared.messages.MessEngineConstants.LOGIN_PASSWORD_RESPONSE_TYPE;
import static com.all.shared.messages.MessEngineConstants.LOGIN_RESPONSE_TYPE;
import static com.all.shared.messages.MessEngineConstants.LOGIN_SIGNUP_REQUEST_TYPE;
import static com.all.shared.messages.MessEngineConstants.LOGIN_SIGNUP_RESPONSE_TYPE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Properties;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.all.core.common.bean.RegisterUserCommand;
import com.all.messengine.Message;
import com.all.messengine.impl.StubMessEngine;
import com.all.shared.messages.ForgotPasswordResult;
import com.all.shared.messages.SignUpResult;
import com.all.shared.model.AllMessage;

public class TestLoginMessageService {

	@InjectMocks
	private LoginMessageService loginMessageService = new LoginMessageService();
	@Spy
	private StubMessEngine stubEngine = new StubMessEngine();
	@Mock
	private Properties clientSettings;
	@Mock
	private CyclicBarrier messageGate;

	private String artifactVersion = "0.0.0";

	@Before
	public void shouldRegisterListeners() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(clientSettings.getProperty("artifact.version")).thenReturn(artifactVersion);
		loginMessageService.registerListeners();
		assertFalse(stubEngine.getMessageListeners(LOGIN_SIGNUP_RESPONSE_TYPE).isEmpty());
		assertFalse(stubEngine.getMessageListeners(LOGIN_RESPONSE_TYPE).isEmpty());
	}

	private void verifyMessageGate() throws InterruptedException, BrokenBarrierException, TimeoutException {
		verify(messageGate).reset();
		// when sending it awaits a few seconds to receive the resp
		verify(messageGate).await(anyInt(), isA(TimeUnit.class));
		verify(messageGate).await();
	}

	@Test
	public void shouldSendSignupMessage() throws Exception {
		stubEngine.addTypeResponse(LOGIN_SIGNUP_REQUEST_TYPE, new AllMessage<String>(LOGIN_SIGNUP_RESPONSE_TYPE,
				SignUpResult.OK.toString()));

		RegisterUserCommand registerUserCommand = new RegisterUserCommand();
		SignUpResult response = loginMessageService.signupRequest(registerUserCommand);

		Message<?> sentMessage = stubEngine.getMessage(LOGIN_SIGNUP_REQUEST_TYPE);
		assertNotNull(sentMessage);
		assertEquals(SignUpResult.OK, response);
		verifyMessageGate();
	}

	@Test
	public void shouldSendForgotPasswordRequest() throws Exception {
		stubEngine.addTypeResponse(LOGIN_PASSWORD_REQUEST_TYPE, new AllMessage<String>(LOGIN_PASSWORD_RESPONSE_TYPE,
				SignUpResult.OK.toString()));

		String email = "some@all.com";
		ForgotPasswordResult response = loginMessageService.forgotPasswordRequest(email);

		Message<?> sentMessage = stubEngine.getMessage(LOGIN_PASSWORD_REQUEST_TYPE);
		assertNotNull(sentMessage);
		assertEquals(ForgotPasswordResult.OK, response);
		verifyMessageGate();

	}

}
