package com.npst.mobileservice.util;

import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

public class CryptoJSImplementation implements IEncryptor {
	private byte[] keyAsBytes;
	private KeySpec myKeySpec;
	private IvParameterSpec iv;
	private SecretKeyFactory mySecretKeyFactory;
	private Cipher cipher;
	private SecretKey key;
	// private static final Logger LOG;
	private static final String UNICODE_FORMAT = "UTF-8";
	private static volatile CryptoJSImplementation inst;
	private static final Object LOCK;
	private static String KEY;
	private static String IV;

	static {
		// LOG = Logger.getLogger((Class)CryptoJSImplementation.class);
		CryptoJSImplementation.inst = null;
		LOCK = new Object();
		CryptoJSImplementation.KEY = "0a948a068f5d4d8b9cc45df90b58d382d2b916c25822b6f74ea96fe6823132f4";
		CryptoJSImplementation.IV = "3ad5485e60a4fecd";
	}

	public static CryptoJSImplementation getInstance() throws Exception {
		if (CryptoJSImplementation.inst == null) {
			synchronized (CryptoJSImplementation.LOCK) {
				if (CryptoJSImplementation.inst == null) {
					CryptoJSImplementation.inst = new CryptoJSImplementation();
				}
			}
		}
		return CryptoJSImplementation.inst;
	}

	public static void main(String[] args) throws Exception {
		// System.out.println(CryptoJSImplementation.getInstance().decrypt("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"));
		// String decryptSTR =
		// CryptoJSImplementation.getInstance().decrypt("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");
		// DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		// DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		//// decryptSTR = decryptSTR.replace("\\", "%5C");
		// Document doc = dBuilder.parse(new InputSource(new StringReader(decryptSTR)));
		// doc.getDocumentElement().normalize();
		//// System.out.println("Root element :" +
		// doc.getDocumentElement().getNodeName());
		// Node node = doc.getDocumentElement().getFirstChild();
		// while(!"Payer".equalsIgnoreCase(node.getNodeName())){
		// node = node.getNextSibling();
		// }
		//// System.out.println(node.getNodeName());
		// Node childNode = node.getFirstChild();
		// while(!"Ac".equalsIgnoreCase(childNode.getNodeName())){
		// childNode = childNode.getNextSibling();
		// }
		//// System.out.println(childNode.getNodeName());
		// Node accountNode = childNode.getFirstChild();
		// while(accountNode !=null){
		// NamedNodeMap detail=accountNode.getAttributes();
		// Node name= detail.item(0);
		// Node value= detail.item(1);
		// if("ACNUM".equals(name.getNodeValue()))
		// System.out.println(value.getNodeValue());
		// accountNode = accountNode.getNextSibling();
		// }
		// System.out.println(CryptoJSImplementation.getInstance().encrypt(
		// "{\"addhaarNo\":\"\",\"appOs\":\"ANDROID\",\"appVer\":\"1.4\",\"deviceInfo\":{\"appId\":\"upi.npst.com.upicanara\",\"capabilities\":\"520000022003000400063918422044790
		// \",\"deviceId\":\"bd65988338368644\",\"simSerialNumber\":\"89910391110201743026\",\"slotId\":\"0\"},\"dob\":\"01
		// / 01 /
		// 1900\",\"email\":\"aksh@gmail.com\",\"fName\":\"Akshay\",\"gcmToken\":\"gcmToken\",\"gender\":\"\",\"imei\":\"\",\"lName\":\"\",\"mName\":\"\",\"mobileNo\":\"917977857102\",\"pspPin\":\"Akshay@999\",\"secAns1\":\"aa\",\"secAns2\":\"aa\",\"secQuestion1\":\"1\",\"secQuestion2\":\"2\",\"userName\":\"Akshay\",\"virtualId\":\"akshay999123654@cnrb\"}"));
		System.out.println(CryptoJSImplementation.getInstance().decrypt(
				"MGrv/+w4c+O0Rmt6eF7Mpum9OngwK0Z2XSxrarxSFRS2WaDDvfj5LWWzXqaWxKgRd8XYPr9FvLvMdoM4DiZICwYAx38cPzZK8RVj3pfxxuGBJhH5PRKTckX2YkJ9a/yEihy6xgihSHBd7X0VgU90hWEd8WCblM7aNVkJIE7BQ4NuGLFlUGEFSTS7qLa4OpLpcLIto1uBGEhl57nWUk7ujijEBqHBs9PnJP2SQZuAXWrRQxY69gQIUr9T3z3HCcMvNq5DeqfgEDbNDxk1utDs+z5/+9s8wN/UAZzgKMrqLtWHK3zVHWTxfXITWfEN7qp4UY0GBzbyEoxchV/Rbzpq8jROkFTCdw6m/AuKjXIsTsKPjyr8NCMb/LBt0SauzBnYpHp3VtJEEGTXlUtr1yHl2K7k8WTupS1oC+iyydO7hzBiCYYbt0zzv8hfT3fWATHAcIjHVm2tunbYrYdLVrCbbejUBaz0Ijzi9+PFYqAa1SSL5b0psrB9KpbQ1agxZa3NQkdnIxIWjq+iu/qGRIaBwuE5uyuJPKXYw4r/dNgwp9/lQht8qmRiP6xjxgX7O+a9eEth1WWL6vP8ix8bK/VOhsVWvIerimBcxttpcvSPomS2luqhoXpIyI9aOOGFn4mAqlkT0X1hP7PfQAkVYqu3pvmbZy5NnLPjYR9EEIneKBA="));
	}

	private static String bytes2String(final byte[] bytes) {
		final StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < bytes.length; ++i) {
			stringBuffer.append((char) bytes[i]);
		}
		return stringBuffer.toString();
	}

	private CryptoJSImplementation() throws Exception {
		this.keyAsBytes = DatatypeConverter.parseHexBinary(CryptoJSImplementation.KEY);
		this.myKeySpec = new DESedeKeySpec(this.keyAsBytes);
		this.iv = new IvParameterSpec(Hex.decodeHex(CryptoJSImplementation.IV.toCharArray()));
		this.mySecretKeyFactory = SecretKeyFactory.getInstance("DESede");
		this.cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		this.key = this.mySecretKeyFactory.generateSecret(this.myKeySpec);
	}

	@Override
	public String decrypt(final String stringToDecrypt) throws Exception {
		String decryptedText = null;
		try {
			this.cipher.init(2, this.key, this.iv);
			final byte[] encryptedText = org.apache.commons.codec.binary.Base64.decodeBase64(stringToDecrypt);

			final byte[] plainText = this.cipher.doFinal(encryptedText);
			decryptedText = bytes2String(plainText);
		} catch (Exception e) {
			// CryptoJSImplementation.LOG.error((Object)e.getMessage(), (Throwable)e);
		}
		return decryptedText;
	}

	@Override
	public String encrypt(final String stringToEncrypt) throws Exception {
		String encryptedString = null;
		try {
			this.cipher.init(1, this.key, this.iv);
			final byte[] plainText = stringToEncrypt.getBytes("UTF-8");
			final byte[] encryptedText = this.cipher.doFinal(plainText);
			final byte[] enc = Base64.encodeBase64(encryptedText);
			encryptedString = new String(enc, "UTF-8");
		} catch (Exception e) {
			// CryptoJSImplementation.LOG.error((Object)e.getMessage(), (Throwable)e);
		}
		return encryptedString;
	}
}
