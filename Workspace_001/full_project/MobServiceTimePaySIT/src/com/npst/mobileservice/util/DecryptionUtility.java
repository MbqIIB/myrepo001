package com.npst.mobileservice.util;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class DecryptionUtility {

	public static int MAX_KEY_LENGTH = DESedeKeySpec.DES_EDE_KEY_LEN;
	private static String ENCRYPTION_KEY_TYPE = "DESede";
	private static String ENCRYPTION_ALGORITHM = "DESede/CBC/PKCS5Padding";

	private static SecretKeySpec keySpec;
	static {
		String passphrase = Util.getProperty("DECRYPTIONKEY");
		// "123456789123456789123456";
		byte[] key;
		try {
			// get bytes representation of the password
			key = passphrase.getBytes("UTF8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		}
		key = padKeyToLength(key, MAX_KEY_LENGTH);
		keySpec = new SecretKeySpec(key, ENCRYPTION_KEY_TYPE);

	}

	public static String decrypt(String encrypted) throws GeneralSecurityException {
		byte[] decryptedValue = Base64.decodeBase64(encrypted);
		byte[] decryptedData = doCipher(decryptedValue, Cipher.DECRYPT_MODE);
		String Value = new String(decryptedData);
		return Value;
	}

	// standard stuff
	public static String encrypt(String unencrypted) throws GeneralSecurityException, UnsupportedEncodingException {
		byte[] encryptedValue = unencrypted.getBytes("UTF8");
		byte[] encryptedData = doCipher(encryptedValue, Cipher.ENCRYPT_MODE);
		return Base64.encodeBase64String(encryptedData);
	}

	public static void main(String[] args) {
		try {
			String abc = DecryptionUtility.encrypt("{\"virtualId\":\"mrudul@cnrb\"}");
			System.out.println(abc);
			System.out.println(DecryptionUtility.decrypt("WEaMaWCeK15teCHUo7Lq9g3tchLAMhW5VV1RjE8yzGA="));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
	}

	private static byte[] doCipher(byte[] original, int mode) throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
		// IV = 0 is yet another issue, we'll ignore it here
		IvParameterSpec iv = new IvParameterSpec(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 });
		cipher.init(mode, keySpec, iv);
		return cipher.doFinal(original);
	}

	// !!! - see post below
	private static byte[] padKeyToLength(byte[] key, int len) {
		byte[] newKey = new byte[len];
		System.arraycopy(key, 0, newKey, 0, Math.min(key.length, len));
		return newKey;
	}

	// public DecryptionUtility() {
	// byte[] key;
	// try {
	// // get bytes representation of the password
	// key = passphrase.getBytes("UTF8");
	// } catch (UnsupportedEncodingException e) {
	// throw new IllegalArgumentException(e);
	// }
	// key = padKeyToLength(key, MAX_KEY_LENGTH);
	// this.keySpec = new SecretKeySpec(key, ENCRYPTION_KEY_TYPE);
	// }

	// public DecryptionUtility(String passphrase) {
	// byte[] key;
	// try {
	// // get bytes representation of the password
	// key = passphrase.getBytes("UTF8");
	// } catch (UnsupportedEncodingException e) {
	// throw new IllegalArgumentException(e);
	// }
	// key = padKeyToLength(key, MAX_KEY_LENGTH);
	// this.keySpec = new SecretKeySpec(key, ENCRYPTION_KEY_TYPE);
	// }
}
