package com.all.shared.crypt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

public class TestCryptModel {
	
	private Log log = LogFactory.getLog(this.getClass());

	private final String BASE = "src/test/resources/";
	private String TEMP_ENCRYPTED_FILE = BASE + "tempEncryptedFile.txt";
	private String TEMP_DECRYPTED_FILE = BASE + "tempDecryptedFile.txt";
	private String TEMP_FILE = BASE + "tempFile.txt";
	private byte[] buffer;
	private CryptModel crypt;
	private byte[] inputBytes;

	@Before
	public void setup() throws Exception {
		inputBytes = new byte[10000];
		Arrays.fill(inputBytes, (byte) 4);
		crypt = new CryptModelBlowfishImpl(new FileInputStream("src/test/resources/secretKeyFile"));
		File file = new File(TEMP_FILE);
		FileInputStream in = new FileInputStream(file);
		buffer = new byte[(int) file.length()];
		in.read(buffer);
		in.close();
	}

	@Test
	public void shouldEncryptByteArray() throws Exception {
		byte[] encryptedBytes = crypt.encryptModel(inputBytes);
		assertTrue(inputBytes.length < encryptedBytes.length);
	}

	@Test
	public void shouldDecryptByteArray() throws Exception {
		byte[] encryptedBytes = crypt.encryptModel(inputBytes);
		byte[] decryptedBytes = crypt.decryptModel(encryptedBytes);
		assertEquals(inputBytes.length, decryptedBytes.length);
	}

	@Test
	public void shouldEncryptFile() {
		byte[] encryptedBytes = encryptedBytes();
		assertTrue(buffer.length < encryptedBytes.length);
		File tempFile = new File(TEMP_ENCRYPTED_FILE);
		assertTrue(tempFile.exists());
	}

	@Test
	public void shouldDecryptFile() {
		try {
			byte[] encryptedBytes = encryptedBytes();
			assertTrue(buffer.length < encryptedBytes.length);
			File tempFile = new File(TEMP_ENCRYPTED_FILE);
			FileInputStream in = new FileInputStream(tempFile);
			byte[] inBuffer = new byte[(int) tempFile.length()];
			in.read(inBuffer);
			in.close();
			byte[] decryptedBytes = decryptedBytes(inBuffer);
			tempFile = new File(TEMP_DECRYPTED_FILE);
			assertTrue(tempFile.exists());
			assertEquals(buffer.length, decryptedBytes.length);
		} catch (Exception e) {
			log.error(e,e);
		}
	}

	private byte[] decryptedBytes(byte[] inBuffer) throws Exception {
		byte[] decryptedBytes;
		decryptedBytes = crypt.decryptModel(inBuffer);
		FileOutputStream out = new FileOutputStream(TEMP_DECRYPTED_FILE);
		out.write(decryptedBytes);
		out.close();
		return decryptedBytes;
	}

	private byte[] encryptedBytes() {
		byte[] encryptedBytes = null;
		try {
			encryptedBytes = crypt.encryptModel(buffer);
			FileOutputStream out = new FileOutputStream(TEMP_ENCRYPTED_FILE);
			out.write(encryptedBytes);
			out.close();
		} catch (Exception e) {
			log.error(e,e);
		}
		return encryptedBytes;
	}
}
