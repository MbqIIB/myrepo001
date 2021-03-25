package com.npst.mobileservice.util;

import java.security.Security;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * Created by npst on 25/1/18.
 */

public class Aes {

	public static String Decrypt(String text, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
		byte[] keyBytes = new byte[16];
		byte[] b = key.getBytes("UTF-8");
		int len = b.length;
		if (len > keyBytes.length)
			len = keyBytes.length;
		System.arraycopy(b, 0, keyBytes, 0, len);
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
		byte[] results = new byte[text.length()];
		results = Base64.getDecoder().decode(text);
		byte[] originalBytes = cipher.doFinal(results);
		String original = new String(originalBytes, "UTF8");
		return original;
	}

	public static String Encrypt(String text, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
		byte[] keyBytes = new byte[16];
		byte[] b = key.getBytes("UTF-8");
		int len = b.length;
		if (len > keyBytes.length)
			len = keyBytes.length;
		System.arraycopy(b, 0, keyBytes, 0, len);
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

		byte[] results = cipher.doFinal(text.getBytes("UTF-8"));
		return Base64.getEncoder().encodeToString(results);
	}

	public static void main(String[] args) {
		try {

			Security.addProvider(new BouncyCastleProvider());
			System.out.println(Decrypt("9v2VTiW39epV3RRrc41OAw==", "cosmos"));
			System.out.println(Encrypt("123456", "cosmos"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
