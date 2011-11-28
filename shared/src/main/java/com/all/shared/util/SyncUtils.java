package com.all.shared.util;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bouncycastle.util.encoders.Base64;

import SevenZip.SevenZipProcess;

import com.all.shared.json.JsonConverter;
import com.all.shared.model.SyncEventEntity;

public class SyncUtils {

	private static Log LOGGER = LogFactory.getLog(SyncUtils.class);

	private static Charset UTF_CHARSET = Charset.forName("UTF-8");

	private static CharsetEncoder UTF_ENCODER = UTF_CHARSET.newEncoder();

	private static CharsetDecoder UTF_DECODER = UTF_CHARSET.newDecoder();

	private static final SevenZipProcess ZIPPER = new SevenZipProcess();

	public static String encodeAndZip(List<SyncEventEntity> events) {
		String json = JsonConverter.toJson(events);
		byte[] encodedBytes = null;
		try {
			ByteBuffer byteBuffer = UTF_ENCODER.encode(CharBuffer.wrap(json));
			encodedBytes = byteBuffer.array();
		} catch (CharacterCodingException e) {
			LOGGER.warn("Could not encode message with UTF-8.");
			encodedBytes = json.getBytes();
		}
		return new String(Base64.encode(zip(encodedBytes)));
	}

	@SuppressWarnings("unchecked")
	public static List<SyncEventEntity> decodeAndUnzip(String encodedEvents) {
		byte[] unzippedBytes = unzip(Base64.decode(encodedEvents.getBytes()));
		String json = null;
		try {
			CharBuffer charBuff = UTF_DECODER.decode(ByteBuffer.wrap(unzippedBytes));
			json = charBuff.toString();
		} catch (Exception e) {
			LOGGER.warn("Could not decode message with UTF-8.");
			json = new String(unzippedBytes);
		}
		return JsonConverter.toTypedCollection(json, ArrayList.class, SyncEventEntity.class);
	}

	private static byte[] zip(byte[] input) {
		try {
			ZIPPER.setInputBytes(input);
			return ZIPPER.compressByteArrayInputStream();
		} catch (Exception e) {
			LOGGER.error("7Zip exception : " + e);
			return null;
		}
	}

	private static byte[] unzip(byte[] input) {
		try {
			ZIPPER.setArrayToDecompress(input);
			return ZIPPER.decompressByteArray();
		} catch (Exception e) {
			LOGGER.error("7Zip exception : " + e);
			return null;
		}
	}

}
