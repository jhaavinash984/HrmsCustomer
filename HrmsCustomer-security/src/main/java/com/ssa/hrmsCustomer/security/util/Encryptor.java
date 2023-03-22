package com.ssa.hrmsCustomer.security.util;

import com.ssa.hrmsCustomer.security.exception.EncryptionException;

public interface Encryptor {
	

	public String encrypt(String plainText) throws EncryptionException;

	public String decrypt(String cipherText) throws EncryptionException;

}
