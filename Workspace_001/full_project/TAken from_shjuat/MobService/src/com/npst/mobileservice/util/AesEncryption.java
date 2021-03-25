package com.npst.mobileservice.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.Security;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class AesEncryption {
	private static final Logger log = Logger.getLogger(AesEncryption.class);

	static {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		
	}
	
	public AesEncryption() {

	}

	public String encrypt(String text, String key) {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", BouncyCastleProvider.PROVIDER_NAME);
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
		} catch (Exception e) {
			log.info("Something went to wrong while ecryption="+e.getMessage());
		}
		return text;
	}

	public String decrypt(String text, String key) {
		try {
			if(!StringUtils.isEmpty(text)) {
				
			}
			log.debug("Encrypted text for authfilter-> " + text);
			log.debug("Key for decrypted text-> " + key);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", BouncyCastleProvider.PROVIDER_NAME);

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
			try {
				results = Base64.getDecoder().decode(text);
			} catch (Exception e) {
				log.info("Something went to wrong while decryption="+e.getMessage());
			}
			byte[] originalBytes = cipher.doFinal(results);
			String original = new String(originalBytes, "UTF8");
			return original;
		} catch (Exception e) {
			log.info("Something went to wrong while decryption="+e.getMessage());
			return text;
		}
	}
}
