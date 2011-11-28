package com.all.networking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.bouncycastle.util.encoders.Base64;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import com.all.messengine.MessEngine;
import com.all.messengine.impl.StubMessEngine;
import com.all.shared.json.JsonConverter;
import com.all.shared.model.AllMessage;
import com.all.shared.model.PresenceInfo;
import com.all.testing.MockInyectRunner;
import com.all.testing.Stub;
import com.all.testing.UnderTest;

@RunWith(MockInyectRunner.class)
public class TestPeerNetworkingService {

	@UnderTest
	private PeerNetworkingService service;
	@Stub
	private String defaultAddress = "127.0.0.1";
	@Stub
	private int defaulPort = 10000;


	@Mock
	private NetworkingSocketFactory socketConfigurator;

	@Mock
	private IoSession someSession;
	@Mock
	private IoConnector ioConnector;
	@Mock
	private ConnectFuture connectFuture;
	@Mock
	private WriteFuture writeFuture;

	private StubMessEngine stubEngine = new StubMessEngine();
	@Stub
	private MessEngine messEngine = stubEngine;
	
	
	private InetSocketAddress remoteAddress = new InetSocketAddress("127.0.0.1", 0);

	@Before
	public void setup() {
		when(someSession.getRemoteAddress()).thenReturn(remoteAddress);
	}

	@Test
	public void shouldCreateAPeerNetworkingServiceViaConstructorWithArgs() throws Exception {
		assertNotNull(new PeerNetworkingService(socketConfigurator, messEngine));
	}

	@Test
	public void shouldCreateASession() throws Exception {
		when(socketConfigurator.newConnector()).thenReturn(ioConnector);
		when(ioConnector.connect(any(InetSocketAddress.class))).thenReturn(connectFuture);
		when(connectFuture.getSession()).thenReturn(someSession);

		IoSession actualSession = service.getSession(defaultAddress, defaulPort);

		assertNotNull(actualSession);
		verify(ioConnector).setHandler(service);
		verify(ioConnector).connect(any(InetSocketAddress.class));
		verify(connectFuture).await();
		assertEquals(someSession, actualSession);
		assertEquals(someSession, service.getSession(defaultAddress, defaulPort));

	}

	@Test
	public void shouldSendMessage() throws Exception {
		shouldCreateASession();
		when(someSession.isConnected()).thenReturn(true);
		AllMessage<PresenceInfo> message = new AllMessage<PresenceInfo>("type", new PresenceInfo());
		when(someSession.write(anyString())).thenReturn(writeFuture);
		Boolean expected = true;
		when(writeFuture.isWritten()).thenReturn(expected);

		Boolean actual = service.send("sender", message);
		assertEquals(expected, actual);
		verify(someSession).write(anyString());
	}

	@Test
	public void shouldFailSendingAMessage() throws Exception {
		AllMessage<String> message = new AllMessage<String>("type", "body");
		boolean success = service.send("sender", message);

		assertFalse(success);
	}


	@Test
	public void shouldHandleASessionClosed() throws Exception {
		shouldCreateASession();
		InetSocketAddress socketAddr = new InetSocketAddress(defaultAddress, defaulPort);
		when(someSession.getRemoteAddress()).thenReturn(socketAddr);

		service.sessionClosed(someSession);
	}


	@Test
	public void shouldShutdown() throws Exception {
		shouldCreateASession();

		service.shutdown();

		verify(someSession).close(true);
	}

	@Test
	public void shouldSetDefaultAddressAndPort() throws Exception {
		String otherAddress = "other Address";
		int otherPort = 1;
		when(someSession.isConnected()).thenReturn(true);
		when(socketConfigurator.newConnector()).thenReturn(ioConnector);
		when(ioConnector.connect(any(InetSocketAddress.class))).thenReturn(connectFuture);
		when(connectFuture.getSession()).thenReturn(someSession);

		service.setDefaultDestination(otherAddress, otherPort);
		AllMessage<PresenceInfo> message = new AllMessage<PresenceInfo>("type", new PresenceInfo());
		when(someSession.write(any(byte[].class))).thenReturn(writeFuture);
		Boolean expected = true;
		when(writeFuture.isWritten()).thenReturn(expected);
		boolean actual = service.send("sender", message);

		assertEquals(expected, actual);
		verify(someSession).write(any(byte[].class));
	}

	@Test
	public void shouldHaveDefaultSession() throws Exception {
		assertFalse(service.hasDefaultSession());

		shouldCreateASession();
		when(someSession.isConnected()).thenReturn(true);
		assertTrue(service.hasDefaultSession());

		service.setDefaultDestination(null, defaulPort);
		assertFalse(service.hasDefaultSession());

		service.setDefaultDestination(defaultAddress, -1);
		assertFalse(service.hasDefaultSession());
	}

	@Test
	public void shouldSendAnOversizedMessageUsingChunks() throws Exception {
		shouldCreateASession();
		StringBuilder expectedBody = new StringBuilder();
		for (int i = 0; i < (200 * 1024); i++) {
			expectedBody.append("abcd");
		}
		String expectedType = "expectedType";
		String expectedSender = "sender@all.com";
		AllMessage<String> oversizedMessage = new AllMessage<String>(expectedType, expectedBody.toString());
		String json = JsonConverter.toJson(oversizedMessage);
		int totalChunks = (json.length() / NetworkingConstants.MAX_NETWORKING_MESSAGE_BODY_LENGTH + (json.length()
				% NetworkingConstants.MAX_NETWORKING_MESSAGE_BODY_LENGTH != 0 ? 1 : 0));
		when(someSession.isConnected()).thenReturn(true);
		when(someSession.write(anyString())).thenReturn(writeFuture);
		Boolean expected = true;
		when(writeFuture.isWritten()).thenReturn(expected);

		Boolean actual = service.send(expectedSender, oversizedMessage);
		assertEquals(expected, actual);
		verify(someSession, times(totalChunks)).write(anyString());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldReceiveAnOversizedMessageUsingChunks() throws Exception {
		StringBuilder expectedBody = new StringBuilder();
		for (int i = 0; i < (200 * 1024); i++) {
			expectedBody.append("abcd");
		}
		String expectedType = "expectedType";
		String expectedSender = "sender@all.com";
		AllMessage<String> oversizedMessage = new AllMessage<String>(expectedType, expectedBody.toString());

		for (NetworkingMessage message : PeerNetworkingService.split(expectedSender, oversizedMessage)) {
			service.messageReceived(someSession, new String(Base64.encode(JsonConverter.toJson(message).getBytes())));
		}

		AllMessage actualMessage = (AllMessage) stubEngine.getCurrentMessage();
		assertNotNull(actualMessage);
		assertEquals(expectedBody.toString(), actualMessage.getBody().toString());
	}

}
