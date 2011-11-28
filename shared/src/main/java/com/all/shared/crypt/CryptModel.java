package com.all.shared.crypt;

public interface CryptModel {

	public byte[] encryptModel(byte[] bytes) throws Exception;
	public byte[] decryptModel(byte[] bytes) throws Exception;
}
