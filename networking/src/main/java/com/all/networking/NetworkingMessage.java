package com.all.networking;

import com.all.shared.json.JsonConverter;
import com.all.shared.model.AllMessage;

public class NetworkingMessage extends AllMessage<AllMessage<?>> {

	private static final long serialVersionUID = -1984258693872155169L;

	private static final String TYPE = "NETWORKING_MESSAGE";

	static {
		JsonConverter.addJsonReader(NetworkingMessage.class, new NetworkingMessageJsonReader());
	}

	private boolean oversized;

	private String sender;

	private String chunk;

	private Integer chunkNumber;

	private String messageId;

	private int totalChunks;
	
	private boolean split;

	public NetworkingMessage(String sender, AllMessage<?> body) {
		super(TYPE, body);
		this.sender = sender;
	}

	NetworkingMessage(String sender, String chunk, int chunkNumber, int totalChunks, String messageId) {
		super(null, null);
		this.sender = sender;
		this.chunk = chunk;
		this.chunkNumber = chunkNumber;
		this.totalChunks = totalChunks;
		this.messageId = messageId;
		this.oversized = false;
		this.split = true;
	}

	@Override
	public String getType() {
		return TYPE;
	}

	public String getSender() {
		return sender;
	}

	public boolean isOversized() {
		return oversized;
	}
	
	public boolean isSplit() {
		return split;
	}

	public String getMessageId() {
		return messageId;
	}

	public int getTotalChunks() {
		return totalChunks;
	}

	public Integer getChunkNumber() {
		return chunkNumber;
	}

	public String getChunk() {
		return chunk;
	}

}
