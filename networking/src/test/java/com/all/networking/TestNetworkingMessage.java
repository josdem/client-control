package com.all.networking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.all.shared.command.LoginCommand;
import com.all.shared.json.JsonConverter;
import com.all.shared.messages.MessEngineConstants;
import com.all.shared.model.AllMessage;

public class TestNetworkingMessage {

	@Test
	public void shouldConvertNetworkingMessageToAndFromJson() throws Exception {
		String email = "somebody@all.com";
		String password = "12345678";

		NetworkingMessage networkingMessage = new NetworkingMessage(email, new AllMessage<LoginCommand>(
				MessEngineConstants.LOGIN_REQUEST_TYPE, new LoginCommand(email, password)));

		String json = JsonConverter.toJson(networkingMessage);
		NetworkingMessage actual = JsonConverter.toBean(json, NetworkingMessage.class);

		assertNotNull(actual);
		assertNotNull(actual.getSender());
		assertEquals(networkingMessage.getSender(), actual.getSender());
		assertNotNull(actual.getBody());
	}

	@Test
	public void shouldConvertNetworkingMessageWithTextMessage() throws Exception {
		AllMessage<String> message = new AllMessage<String>("type", "body");
		NetworkingMessage networkingMessage = new NetworkingMessage("email", message);

		String json = JsonConverter.toJson(networkingMessage);
		NetworkingMessage actual = JsonConverter.toBean(json, NetworkingMessage.class);

		assertNotNull(actual);
		assertNotNull(actual.getSender());
		assertEquals(networkingMessage.getSender(), actual.getSender());
		assertNotNull(actual.getBody());
		assertEquals(message.getBody(), actual.getBody().getBody());
	}

}
