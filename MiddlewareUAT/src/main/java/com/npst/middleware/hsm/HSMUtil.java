package com.npst.middleware.hsm;

import java.security.Key;
import java.security.PrivateKey;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HSMUtil
{
	private static final Logger LOG = LoggerFactory.getLogger(HSMUtil.class);
	private static final String cCipherTransformation = "RSA/ECB/PKCS1Padding";
	private static final String cCipherProvider = "LunaProvider";
	private static final String ZERO_PAD = "0000000000000000";
	private static final String PIN_PAD = "FFFFFFFFFFFFFF";

	// final static String key = "15EA4CA20131C2FD2315208C9110AD40";
	public static String decryptData(final byte[] encryptedData)
	{
		LOG.trace(" ");
		HSMService hsmService = HSMService.getInstance();
		String decryptedText = "";
		try
		{
			LOG.trace("Before going to getInstance:");
//			HSMService hsmService = HSMService.getInstance();
			LOG.trace("Before going to check PrivateKey:");
			PrivateKey key = hsmService.getKey();
			Cipher rsaCipher = Cipher.getInstance(cCipherTransformation, cCipherProvider);
			rsaCipher.init(2, key);
			LOG.trace("Before going to check decryptedData:");
			byte[] decryptedData = rsaCipher.doFinal(encryptedData);
			LOG.trace("After check decryptedData with decryptedText:");
			decryptedText = new String(decryptedData);
		}
		catch (Exception e)
		{
			//hsmService.reSetHSM();
			LOG.error(e.getMessage(),e);
		}
		return decryptedText;
	}

	public static String getSensitiveData(final byte[] encryptedData, String cardNumber)
	{
		LOG.info("Starting from getSensitiveData with encryptedData as {} and card no {} " , encryptedData , cardNumber);
		String decryptedText = "";
		try
		{
			HSMService hsmService = HSMService.getInstance();
			Key skey = hsmService.getSKey();
			byte[] pinBlock = getPinBlock(cardNumber, decryptData(encryptedData));
			Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, skey);
			byte[] encryptedPinBlock = cipher.doFinal(pinBlock);
			return getHexString(encryptedPinBlock);
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage(),e);
		}
		return decryptedText;
	}

	private static byte[] getPinBlock(String cardNumber, String pin) throws IllegalBlockSizeException
	{
		LOG.trace("Starting from getPinBlock with cardNumber {}  and pin {}" , cardNumber,pin);
		int[] paddedPin = padPin(pin);
		int[] paddedCard = padCard(cardNumber);
		byte[] pinBlock = new byte[8];
		for (int cnt = 0; cnt < 8; cnt++)
		{
			pinBlock[cnt] = (byte) (paddedPin[cnt] ^ paddedCard[cnt]);
		}
		return pinBlock;
	}

	private static int[] padPin(String pin) throws IllegalBlockSizeException
	{
		String pinBlockString = "0" + pin.length() + pin + PIN_PAD;
		pinBlockString = pinBlockString.substring(0, 16);
		return getHexIntArray(pinBlockString);

	}

	private static int[] padCard(String cardNumber) throws IllegalBlockSizeException
	{
		cardNumber = ZERO_PAD + cardNumber;
		int cardNumberLength = cardNumber.length();
		int beginIndex = cardNumberLength - 13;
		String acctNumber = "0000" + cardNumber.substring(beginIndex, cardNumberLength - 1);
		return getHexIntArray(acctNumber);
	}

	private static String getHexString(byte[] input)
	{
		StringBuilder strBuilder = new StringBuilder();
		for (byte hexByte : input)
		{
			int res = 0xFF & hexByte;
			String hexString = Integer.toHexString(res);
			if (hexString.length() == 1)
			{
				strBuilder.append(0);
			}
			strBuilder.append(hexString);

		}
		return strBuilder.toString().toUpperCase();
	}

	private static int[] getHexIntArray(String input) throws IllegalBlockSizeException
	{
		if (input.length() % 2 != 0)
		{
			throw new IllegalBlockSizeException("Invalid Hex String, Hex representation length is not a multiple of 2");
		}
		int[] resultHex = new int[input.length() / 2];
		for (int iCnt1 = 0; iCnt1 < input.length(); iCnt1++)
		{
			String byteString = input.substring(iCnt1++, iCnt1 + 1);
			int hexOut = Integer.parseInt(byteString, 16);
			resultHex[(iCnt1 / 2)] = (hexOut & 0xFF);
		}
		return resultHex;
	}

}
