package com.npst.mobileservice.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ECSignQRUtil {
	public static void main(String[] args) {
		try {
			// generateAndwriteKey("keyfile.txt");
			PrivateKey privateKey = null;
			PublicKey publicKey = null;
			List<Object> list = readKey("keyfile.txt");
			for (Object obj : list) {
				if (obj instanceof PrivateKey) {
					privateKey = (PrivateKey) obj;
				} else if (obj instanceof PublicKey) {
					publicKey = (PublicKey) obj;
				} else {
				}
			}
			System.out.println("privateKey=" + privateKey);
			System.out.println("publicKey=" + publicKey);
			String qrStr = "upi://pay?pa=7906743578@cosmos&pn=PAWAR HANUMANT KHASHABA&mc=0000&tid=&tr=&tn=&am=0&cu=INR&appid=null&appname=null";
			//qrStr.split("&sign");
			String out = signQR(privateKey, qrStr);
			System.out.println("QR signed=" + out);
			verify(qrStr, out, publicKey);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static List<Object> readKey(String fileName) {
		final List<Object> listOut = new ArrayList<Object>();
		try (BufferedReader in = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File(fileName)), "UTF-8"));) {
			String str;
			while ((str = in.readLine()) != null) {
				if ("KEY_PUBLIC".equals(str)) {
					listOut.add(loadPublicKey(in.readLine(), in.readLine(), in.readLine()));
				} else if ("KEY_PRIVATE".equals(str)) {
					listOut.add(loadPrivateKey(in.readLine(), in.readLine(), in.readLine()));
				} else {
					System.out.println("NOP");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOut;
	}

	public static void generateAndwriteKey(String fileName) throws Exception {
		try (BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));) {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC");
			ECGenParameterSpec ecsp = new ECGenParameterSpec("secp256k1");
			kpg.initialize(ecsp);
			KeyPair kyp = kpg.genKeyPair();
			encodeKey(kyp.getPublic(), writer);
			writer.newLine();
			encodeKey(kyp.getPrivate(), writer);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private static void encodeKey(Key key, BufferedWriter writer) throws IOException {
		byte[] enc = key.getEncoded();
		if (key instanceof PrivateKey) {
			writer.write("KEY_PRIVATE");
			writer.newLine();
		} else if (key instanceof PublicKey) {
			writer.write("KEY_PUBLIC");
			writer.newLine();
		} else {
			writer.write("KEY_SECRET");
			writer.newLine();
		}
		writer.write(key.getFormat());
		writer.newLine();
		writer.write(key.getAlgorithm());
		writer.newLine();
		writer.write(Base64.getEncoder().encodeToString(enc));
	}

	private static PrivateKey loadPrivateKey(final String format, String algorithm, final String keyBase64Str)
			throws Exception {
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(keyBase64Str.getBytes()));
		KeyFactory factory = KeyFactory.getInstance("EC");
		PrivateKey privateKey = factory.generatePrivate(spec);
		return privateKey;
	}

	public static PublicKey loadPublicKey(final String format, String algorithm, final String keyBase64Str)
			throws Exception {
		X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.getDecoder().decode(keyBase64Str.getBytes()));
		KeyFactory factory = KeyFactory.getInstance("EC");
		PublicKey publicKey = factory.generatePublic(spec);
		return publicKey;
	}

	public static String signQR(PrivateKey privateKey, String qrStr) throws Exception {
		// Signature with Sha-256
		Signature dsa = Signature.getInstance("SHA256withECDSA");
		dsa.initSign(privateKey);
		byte[] strByte = qrStr.getBytes("UTF-8");
		dsa.update(strByte);
		// Sign with private key
		byte[] realSig = dsa.sign();
		// Encode signed URL with base 64
		String encodedSign = java.util.Base64.getEncoder().encodeToString(realSig);
		return encodedSign;
	}

	public static boolean verify(String orginalMsg, String base64Signature, PublicKey publicKey) throws Exception {
		final Signature signature = Signature.getInstance("SHA256withECDSA");
		signature.initVerify(publicKey);
		signature.update(orginalMsg.getBytes());
		boolean f = signature.verify(Base64.getDecoder().decode(base64Signature));
		System.out.println("verify=" + f);
		return f;
	}
	
	
	
	

}