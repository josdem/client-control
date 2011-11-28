package com.all.networking;

public interface NetworkingConstants {
	String SSL_FILTER = "ssl";
	String CODEC_FILTER = "codec";
	String MESSAGE_SENDER = "MESSAGE_SENDER";
	String MESSAGE_SENDER_PUBLIC_ADDRESS = "MESSAGE_SENDER_PUBLIC_ADDRESS";
	String NETWORKING_SESSION_ID = "NETWORKING_SESSION_ID";
	int MAX_NETWORKING_MESSAGE_BODY_LENGTH = 512 * 1024; // 500 K
	int PROTOCOL_CAPACITY = (int)(1024 * 1024 * 1.5); // 1.5 MEGAS
	int FIX_LENGTH = 4;
}
