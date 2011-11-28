package com.all.networking;

import static com.all.networking.NetworkingConstants.MAX_NETWORKING_MESSAGE_BODY_LENGTH;
import static com.all.networking.NetworkingConstants.MESSAGE_SENDER;
import static com.all.networking.NetworkingConstants.MESSAGE_SENDER_PUBLIC_ADDRESS;
import static com.all.networking.NetworkingConstants.NETWORKING_SESSION_ID;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.bouncycastle.util.encoders.Base64;

import com.all.messengine.MessEngine;
import com.all.shared.json.JsonConverter;
import com.all.shared.model.AllMessage;

public abstract class AbstractNetworkingService extends IoHandlerAdapter implements NetworkingMessageWriter,
		NetworkingMessageHandler {

	private static Charset UTF_CHARSET = Charset.forName("UTF-8");
	private static CharsetEncoder UTF_ENCODER = UTF_CHARSET.newEncoder();
	private static CharsetDecoder UTF_DECODER = UTF_CHARSET.newDecoder();

	static {
		JsonConverter.addJsonReader(NetworkingMessage.class, new NetworkingMessageJsonReader());
	}

	private static final Log LOG = LogFactory.getLog(AbstractNetworkingService.class);

	private static String createMessageId(String sender, AllMessage<?> message) {
		return sender + message.getType() + System.currentTimeMillis();
	}

	protected static List<NetworkingMessage> split(String sender, AllMessage<?> message) {
		String jsonMessage = JsonConverter.toJson(message);
		int messageLength = jsonMessage.length();
		int totalChunks = (messageLength / MAX_NETWORKING_MESSAGE_BODY_LENGTH + (messageLength
				% MAX_NETWORKING_MESSAGE_BODY_LENGTH != 0 ? 1 : 0));
		LOG.info("Splitting " + message.getType() + " in " + totalChunks + " chunks.");
		List<NetworkingMessage> chunks = new ArrayList<NetworkingMessage>();
		String messageId = createMessageId(sender, message);
		for (int i = 0; i < totalChunks; i++) {
			int from = i * MAX_NETWORKING_MESSAGE_BODY_LENGTH;
			int to = (i + 1) == totalChunks ? messageLength : (i + 1) * MAX_NETWORKING_MESSAGE_BODY_LENGTH;
			chunks.add(new NetworkingMessage(sender, jsonMessage.substring(from, to), i, totalChunks, messageId));
		}
		return chunks;
	}

	@Override
	public void onAllMessage(AllMessage<?> allMessage) {
		getMessEngine().send(allMessage);
	}

	@Override
	public boolean write(IoSession session, String sender, AllMessage<?> message) {
		try {
			if (isOversized(message)) {
				return writeChunks(session, split(sender, message));
			} else {
				return write(session, new NetworkingMessage(sender, message));
			}
		} catch (Exception e) {
			LOG.error("Could not send message through the network.", e);
			return false;
		}
	}

	@Override
	public final void messageReceived(IoSession session, Object message) {
		String json = null;
		try {
			byte[] decodedBytes = Base64.decode(message.toString().getBytes());
			CharBuffer charBuff = UTF_DECODER.decode(ByteBuffer.wrap(decodedBytes));
			json = charBuff.toString();
		} catch (Exception e) {
			LOG.warn("Could not decode message with UTF-8.");
			json = new String(Base64.decode(message.toString().getBytes()));
		}
		NetworkingMessage networkingMessage = JsonConverter.toBean(json, NetworkingMessage.class);
		if (networkingMessage.isSplit()) {
			networkingMessage = mergeNetworkingMessageChunk(networkingMessage);
		}
		if (networkingMessage != null) {
			AllMessage<?> actualMessage = networkingMessage.getBody();
			InetSocketAddress remoteAddress = (InetSocketAddress) session.getRemoteAddress();
			actualMessage.putProperty(MESSAGE_SENDER_PUBLIC_ADDRESS, remoteAddress.getAddress().getHostAddress());
			actualMessage.putProperty(MESSAGE_SENDER, networkingMessage.getSender());
			actualMessage.putProperty(NETWORKING_SESSION_ID, Long.toString(session.getId()));
			sessionUpdated(session, networkingMessage);
			onAllMessage(actualMessage);
		}
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) {
		LOG.error("An exception ocurred from " + session.getRemoteAddress() + ".", cause);
	}

	protected void sessionUpdated(IoSession session, NetworkingMessage networkingMessage) {
	}

	protected abstract MessEngine getMessEngine();

	private boolean writeChunks(IoSession session, List<NetworkingMessage> chunks) throws InterruptedException {
		int sentChunks = 0;
		for (NetworkingMessage chunk : chunks) {
			if (write(session, chunk)) {
				sentChunks++;
			}
		}
		return sentChunks == chunks.size();
	}

	private boolean isOversized(AllMessage<?> message) {
		return JsonConverter.toJson(message).length() > MAX_NETWORKING_MESSAGE_BODY_LENGTH;
	}

	private boolean write(IoSession session, NetworkingMessage networkingMessage) throws InterruptedException {
		String json = JsonConverter.toJson(networkingMessage);
		String encodedMessage = null;
		try {
			ByteBuffer byteBuffer = UTF_ENCODER.encode(CharBuffer.wrap(json));
			encodedMessage = new String(Base64.encode(byteBuffer.array()));
		} catch (CharacterCodingException e) {
			LOG.warn("Could not encode message with UTF-8.");
			encodedMessage = new String(Base64.encode(json.getBytes()));
		}
		WriteFuture future = session.write(encodedMessage);
		future.await();
		if (!future.isWritten()) {
			LOG.error("Could not send message through the network.", future.getException());
		}
		return future.isWritten();
	}

	private final Map<String, List<NetworkingMessage>> bufferedMessages = new HashMap<String, List<NetworkingMessage>>();

	private NetworkingMessage mergeNetworkingMessageChunk(NetworkingMessage networkingMessage) {
		List<NetworkingMessage> chunks = bufferedMessages.get(networkingMessage.getMessageId());
		LOG.info("Received chunk " + networkingMessage.getChunkNumber() + " of " + networkingMessage.getTotalChunks()
				+ " for message " + networkingMessage.getMessageId());
		if (chunks == null) {
			chunks = new ArrayList<NetworkingMessage>();
			bufferedMessages.put(networkingMessage.getMessageId(), chunks);
		}
		chunks.add(networkingMessage);
		if (chunks.size() == networkingMessage.getTotalChunks()) {
			bufferedMessages.remove(networkingMessage.getMessageId());
			return join(chunks);
		}
		return null;
	}

	private NetworkingMessage join(List<NetworkingMessage> chunks) {
		Collections.sort(chunks, new Comparator<NetworkingMessage>() {
			@Override
			public int compare(NetworkingMessage o1, NetworkingMessage o2) {
				return o1.getChunkNumber().compareTo(o2.getChunkNumber());
			}
		});
		StringBuilder body = new StringBuilder();
		for (NetworkingMessage networkingMessage : chunks) {
			body.append(networkingMessage.getChunk());
		}
		NetworkingMessage firstChunk = chunks.get(0);
		return new NetworkingMessage(firstChunk.getSender(), JsonConverter.toBean(body.toString(), AllMessage.class));
	}

}
