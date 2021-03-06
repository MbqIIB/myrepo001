package com.npst.middleware.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESEncryptionUtility {
	public static final String		secretKeys	= "Npst@!@##@@!!NPSt";//UAT
	//public static final String		secretKeys	= "COSMOS_UPI_PUNE_BANK";//prod
	private static SecretKeySpec	secretKey;
	private static byte[]			key;

	public static String decrypt(String strToDecrypt, String secret) {
		try {
			setKey(secret);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
		} catch (Exception e) {
			System.out.println("Error while decrypting: " + e.toString());
		}
		return null;
	}

	public static String encrypt(String strToEncrypt, String secret) {
		try {
			setKey(secret);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}
		return null;
	}

	public static void main(String[] args) {

		String originalString = "npst@1234";
		
		//String encryptedString="MGvCPdQWAyEhypJ+oFr4Og==";
		String encryptedString = AESEncryptionUtility.encrypt(originalString, AESEncryptionUtility.secretKeys);
		String decryptedString = AESEncryptionUtility.decrypt(encryptedString, AESEncryptionUtility.secretKeys);

		System.out.println("original String---->" + originalString);
		System.out.println("encrypted String ----------->" + encryptedString);
		System.out.println("decrypted String------------>" + decryptedString);

		System.out.println(Util.getProperty("QUSERNAME")
				+ AESEncryptionUtility.decrypt(encryptedString, AESEncryptionUtility.secretKeys));
		if ("MGvCPdQWAyEhypJ+oFr4Og==".equalsIgnoreCase(encryptedString)) {
			System.err.println(" ENCRYPT MATCH......");
		} else {
			System.out.println(" NOT ENCRYPT MATCH >>>>>>>>>>>>>>>>>>");
		}
	}

	public static void setKey(String myKey) {
		MessageDigest sha = null;
		try {
			key = myKey.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			secretKey = new SecretKeySpec(key, "AES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
