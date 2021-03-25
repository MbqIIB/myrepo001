package com.npst.mobileservice.util;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class PassCodeAuthentication {

	private static String key = "cosmos";
	static int ivSize = 16;
	static int keySize = 16;
	private static String charSet = "UTF-8";
	private static String messageDigestInstance = "SHA-256";
	private static String algo = "AES";
	private static String transformation = "AES/CBC/PKCS7Padding";
	private static String provider = "BC";

	public static String encryptPasscode(String value) {
		String encValue = value;
		try {
			byte[] clean = value.getBytes();
			byte[] iv = new byte[ivSize];
			SecureRandom random = new SecureRandom();
			random.nextBytes(iv);
			IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
			// Hashing key.
			MessageDigest digest = MessageDigest.getInstance(messageDigestInstance);
			digest.update(key.getBytes(charSet));
			byte[] keyBytes = new byte[keySize];
			System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);
			SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, algo);
			// Encrypt.
			Cipher cipher = Cipher.getInstance(transformation, provider);
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
			byte[] encrypted = cipher.doFinal(clean);
			// Combine IV and encrypted part.
			byte[] encryptedIVAndText = new byte[ivSize + encrypted.length];
			System.arraycopy(iv, 0, encryptedIVAndText, 0, ivSize);
			System.arraycopy(encrypted, 0, encryptedIVAndText, ivSize, encrypted.length);
			encValue = Base64.getEncoder().encodeToString(encryptedIVAndText);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return encValue;
	}

	public static void main(String[] args) {
		Security.addProvider(new BouncyCastleProvider());
		System.out.println(decryptPass("ul7sfSqNZS0R9R7iMpYyCIadCiCInDOnTqkzuUtz260="));
		System.out.println(encryptPasscode("ABC@!@#$%^&,:+"));
	}

	public static String decryptPass(String value) {
		try {

			byte[] encValue = Base64.getDecoder().decode(value);
			byte[] iv = new byte[ivSize];
			System.arraycopy(encValue, 0, iv, 0, iv.length);
			IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
			int encryptedSize = encValue.length - ivSize;
			byte[] encryptedBytes = new byte[encryptedSize];
			System.arraycopy(encValue, ivSize, encryptedBytes, 0, encryptedSize);
			byte[] keyBytes = new byte[keySize];
			MessageDigest md = MessageDigest.getInstance(messageDigestInstance);
			md.update(key.getBytes());
			System.arraycopy(md.digest(), 0, keyBytes, 0, keyBytes.length);
			SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, algo);
			Cipher cipherDecrypt = Cipher.getInstance(transformation, provider);
			cipherDecrypt.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
			byte[] decrypted = cipherDecrypt.doFinal(encryptedBytes);
			return new String(decrypted);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
