package com.all.shared.crypt;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class CryptModelBlowfishImpl implements CryptModel {

	private final String BLOWFISH = "Blowfish";
	private SecretKey secretKey;
	
	public CryptModelBlowfishImpl(InputStream keyStream) throws NoSuchAlgorithmException, FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream keyReader = new ObjectInputStream(keyStream);
		secretKey = (SecretKey) keyReader.readObject();
	}

	@Override
	public byte[] decryptModel(byte[] bytes) throws Exception {
		Cipher cipher = Cipher.getInstance(BLOWFISH);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		return cipher.doFinal(bytes);
	}

	@Override
	public byte[] encryptModel(byte[] bytes) throws Exception {
		Cipher cipher = Cipher.getInstance(BLOWFISH);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		return cipher.doFinal(bytes);
	}

}
