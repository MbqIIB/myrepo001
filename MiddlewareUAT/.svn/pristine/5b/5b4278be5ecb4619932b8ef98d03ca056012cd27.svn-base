package com.npst.middleware.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Random;

import javax.crypto.Cipher;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.StringUtils;
import org.jpos.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.infosys.ci.fiusb.object.RespFixml;

public class Util
{
	static SimpleDateFormat sdf7 = new SimpleDateFormat(ConstantNew.DATE_FORMAT_1);
	private static final String PROPFILENAME = "Middleware.properties";
	static Logger log = LoggerFactory.getLogger(Util.class.getName());
	private static final String SMSPROFILENAME = "SmsTemplate.properties";
	static Properties smsprop = null;
	static String mask = "";
	static Properties prop = null;
	private final static String chars = "0123456789";
	private static final Integer	riskTransAmount		= Util.getProperty("MIN_RISK_TXN_AMOUNT") != null
			? Integer.parseInt(Util.getProperty("MIN_RISK_TXN_AMOUNT"))
			: 5000;
	private static final Integer	riskTransDiffTime	= Util.getProperty("MIN_RISK_TXN_TIME") != null
			? Integer.parseInt(Util.getProperty("MIN_RISK_TXN_TIME"))
			: 12;
			
	public static Marshaller mReqFixml;
	// public static Unmarshaller umRespFixml;

	// Temporary till HSM
	//static String				p12Password		= "abcd1234";
	static String p12Password = "Noida@131";
	static PrivateKey key = null;
	static
	{
		try
		{
			KeyStore ks = KeyStore.getInstance("pkcs12", "SunJSSE");
			ks.load(new FileInputStream(Util.getProperty("PRIVATEKEY")), p12Password.toCharArray());
			Enumeration<String> aliases = ks.aliases();
			String keyAlias = null;
			while (aliases.hasMoreElements())
			{
				keyAlias = aliases.nextElement();
			}
			key = (PrivateKey) ks.getKey(keyAlias, p12Password.toCharArray());
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
		}
	}
	// static GenericPackager packager = null;

	// static {
	// try {
	// packager = new GenericPackager(ConstantNew.CONST_9);
	// } catch (ISOException e) {
	// e.printStackTrace();
	// }
	//
	// }

	public static boolean checkUpiPinViolation(final String upiPin)
	{

		return false;
	}

	public static String generateOtp(final int size)
	{
		StringBuilder generatedToken = new StringBuilder();
		try
		{
			SecureRandom number = SecureRandom.getInstance(ConstantNew.ALGO);
			for (int i = 0; i < size; i++)
			{
				generatedToken.append(number.nextInt(9));
			}
		}
		catch (NoSuchAlgorithmException e)
		{
			log.error("caught exception at generate Otp");
			e.printStackTrace();
		}
		return generatedToken.toString();
	}

	public static String genOtp(final int otpLen)
	{

		Random rnd = new SecureRandom();
		StringBuilder pass = new StringBuilder();
		for (int i = 0; i < otpLen; i++)
		{
			pass.append(chars.charAt(rnd.nextInt(chars.length())));
		}
		return pass.toString();
	}

	/**
	 * TRANSMISSION_DATE_TIME(yyyymmddHHMMSS)
	 *
	 * @return
	 */
	public static String getField7()
	{
		String strDate = "";
		try
		{
			Date date = new Date();
			strDate = sdf7.format(date);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
		}
		return strDate;
	}

	/**
	 * @param propName
	 * @return
	 */
	public static String getProperty(final String propName)
	{
		String propValue = null;
		FileInputStream fs = null;
		try
		{
			if (null == prop)
			{
				log.debug("Loading ... ... " + PROPFILENAME);
				prop = new Properties();

				String osName = System.getProperty("os.name");
				if (osName.toLowerCase().contains(ConstantNew.WINDOWS))
				{
					prop.load(fs = new FileInputStream(ConstantNew.PATH_WINDOWS + PROPFILENAME));
				}
				else
				{
					prop.load(fs = new FileInputStream(ConstantNew.PATH_LINUX + PROPFILENAME));
				}

			}
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			System.exit(0);
		}
		finally
		{
			try
			{
				if (null != fs)
				{
					fs.close();
				}
			}
			catch (IOException e)
			{
				log.error(e.getMessage(),e);
			}
		}
		propValue = prop.getProperty(propName);
		return (null == propValue) ? "" : propValue.trim();
	}

	public static String getTS()
	{
		log.trace("");
		SimpleDateFormat sdfDate = new SimpleDateFormat(ConstantNew.DATE_FORMAT_2);
		Date now = new Date();
		String strDate = sdfDate.format(now);
		strDate = strDate.substring(0, strDate.length() - 2) + ":" + strDate.substring(strDate.length() - 2);
		return strDate;
	}


	public static StringWriter marshal(final Object obj) throws JAXBException
	{
		JAXBContext context = JAXBContext.newInstance(obj.getClass());
		Marshaller m = context.createMarshaller();
		StringWriter sw = new StringWriter();
		m.marshal(obj, sw);
		return sw;
	}

	public static Object unmarshal(String xmlString, Object obj) throws JAXBException
	{
		Reader reader = new StringReader(xmlString);
		JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Object object = jaxbUnmarshaller.unmarshal(reader);
		return object;
	}

	public static String maskNumber(final String actNumber)
	{
		final String s = actNumber.replaceAll("\\D", "");
		final int start = 0;
		final int end = s.length() - 4;//NPCI OC 57 IMPL
		final String overlay = StringUtils.repeat("X", end - start);
		return StringUtils.overlay(s, overlay, start, end);
	}

	public static String maskNumberOld(final String number)
	{
		int index = 0;
		mask = getProperty("MASK");
		StringBuilder masked = new StringBuilder();
		for (int i = 0; i < mask.length(); i++)
		{
			char c = mask.charAt(i);
			if (c == '#')
			{
				masked.append(number.charAt(index));
				index++;
			}
			else if (c == 'X')
			{
				masked.append(c);
				index++;
			}
			else
			{
				masked.append(c);
			}
		}
		return masked.toString();
	}

	public static ArrayList<String> mbstrSplit(String str, final char s)
	{
		str = str.replace("[", "");
		str = str.replace("]", "");
		String[] fields = str.split("\\" + s);
		ArrayList<String> strList = new ArrayList<>();
		for (String field : fields)
		{
			strList.add(field.trim());
		}
		log.trace("str[" + str + "]" + "s[" + s + "] return=[" + strList.toString() + "]");
		return strList;
	}

	/*
	 * public static String objToXmlStr(ReqFixml reqFixml) { String reqXmlStr =
	 * ""; StringWriter sw = new StringWriter(); try {
	 * mReqFixml.marshal(reqFixml, sw); reqXmlStr = sw.toString(); } catch
	 * (Exception e) { e.printStackTrace(); } return reqXmlStr; }
	 */

	public static String padLeft(final String str, final int n, final char s)
	{
		return String.format("%" + n + "s", str).replace(' ', s);
	}

	public static String padRight(final String str, final int n, final char s)
	{
		return String.format("%-" + n + "s", str).replace(' ', s);
	}

	public static ArrayList<String> strSplit(final String str, final char s)
	{
		String[] fields = str.split("\\" + s);
		ArrayList<String> strList = new ArrayList<>();
		for (String field : fields)
		{
			strList.add(field.trim());
		}
		log.trace("str[" + str + "]" + "s[" + s + "] return=[" + strList.toString() + "]");
		return strList;
	}

	public static String convertAmountInPaisa(String amount) throws Exception
	{
		BigDecimal _a = new BigDecimal(amount);
		_a = _a.multiply(new BigDecimal(100));
		long amountInt = _a.longValue();
		return amountInt + "";
	}


	public static String decrypt(byte[] text)
	{
		byte[] dectyptedText = null;
		try
		{
			// get an RSA cipher object and print the provider
			final Cipher cipher = Cipher.getInstance("RSA");
			// decrypt the text using the private key
			cipher.init(Cipher.DECRYPT_MODE, key);
			dectyptedText = cipher.doFinal(text);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return new String(dectyptedText);
	}

	public static String convertPaisaInAmount(String balance)
	{
		Double amountInt = Double.parseDouble(balance) / 100;
		return amountInt + "";
	}

	public static RespFixml xmlStrToObj(String xmlStr)
	{
		RespFixml respFixml = new RespFixml();
		try
		{
			JAXBContext jc = JAXBContext.newInstance(String.class);
			Unmarshaller umRespFixml = jc.createUnmarshaller();

			respFixml = (RespFixml) umRespFixml.unmarshal(new StringReader(xmlStr));

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return respFixml;
	}

	public static byte[] convertObjectIntoByteArray(final Object obj) throws IOException
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		byte[] yourBytes = null;
		try
		{
			out = new ObjectOutputStream(bos);
			out.writeObject(obj);
			out.flush();
			yourBytes = bos.toByteArray();
		}
		finally
		{
			try
			{
				bos.close();
			}
			catch (IOException ex)
			{
			}
		}
		return yourBytes;
	}

	public static String getSMSProperty(final String propName)
	{
		String propValue = null;
		FileInputStream fs = null;
		try
		{
			if (null == smsprop)
			{
				log.info("Loading ... ... " + SMSPROFILENAME);
				smsprop = new Properties();
				String osName = System.getProperty("os.name");
				if (osName.toLowerCase().contains(ConstantNew.WINDOWS))
				{
					smsprop.load(fs = new FileInputStream(ConstantNew.PATH_WINDOWS + SMSPROFILENAME));
				}
				else
				{
					smsprop.load(fs = new FileInputStream(ConstantNew.PATH_LINUX + SMSPROFILENAME));
				}
			}
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			System.exit(0);
		}
		finally
		{
			try
			{
				if (null != fs)
				{
					fs.close();
				}
			}
			catch (IOException e)
			{
				log.error(e.getMessage(),e);
			}
		}
		propValue = smsprop.getProperty(propName);
		return (null == propValue) ? "" : propValue.trim();
	}

	public static String getD125_With6(String rrn_npci, String vpaOrAccountOrAdhaar, String txnNote)
	{
		String out = null;
		try
		{
			out = (null == rrn_npci ? "" : (rrn_npci.length() > 12 ? rrn_npci.substring(0, 12) : rrn_npci)) + "/" + (null == vpaOrAccountOrAdhaar ? "" : (vpaOrAccountOrAdhaar.length() > 15 ? vpaOrAccountOrAdhaar.substring(0, 15) : vpaOrAccountOrAdhaar)) + "/" + (null == txnNote ? "" : (txnNote.length() > 13 ? txnNote.substring(0, 13) : txnNote));
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
		}
		return out;

	}

	public static String getJSONStr(Object req)
	{
		if (null == req)
		{
			return "";
		}
		log.info("return req {}",req.toString());
		Gson gson = new Gson();
		String jsonStr = gson.toJson(req);
		log.trace("return jsonStr {} ",jsonStr);
		return jsonStr;
	}
	
	public static boolean isValidRiskTxnAmount(String txnAmount) {
		try {
			if (riskTransAmount <= (txnAmount != null ? Double.parseDouble(txnAmount) : 0)) {
				log.info("Txn not allowed as it is greater than first txn amount :{}", txnAmount);
				return false;
			} else {
				return true;
			}
		}
		catch(Exception exp) {
			log.error(exp.getMessage(),exp);
			return false;
		}
	}
	
	public static boolean isValidRiskTxnDate(Date firstTransDate) {
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(firstTransDate);
			cal.add(Calendar.HOUR,riskTransDiffTime);
			Date nextTxnDate = cal.getTime();
			Date currentDate=new Date();
			if (nextTxnDate.after(currentDate)) {
				log.info("Txn not allowed as txn time is less than allowed txn time");
				return false;
			} else {
				return true;
			}
		}
		catch(Exception exp) {
			log.error(exp.getMessage(),exp);
			return false;
		}
	}
	
	
	public static String geDateTFormat(String date)
	{
		log.trace("");
		String str = date;
		StringBuilder sb = new StringBuilder();
		sb.append(str.substring(4, 8));
		sb.append("-");
		sb.append(str.subSequence(2, 4));
		sb.append("-");
		sb.append(str.substring(0, 2));
		sb.append("T00:00:00.000");
		return sb.toString();
	}
	
	
	/*public static void main(String[] args) {  
        // Getting encoder  
        //Base64.Encoder encoder = Base64.getUrlEncoder();  
        // Encoding URL  
		String eStr ="%3C%3Fxml+version%3D%221.0%22+encoding%3D%22UTF-8%22%3F%3E%0D%0A%3CFIXML+xsi%3AschemaLocation%3D%22http%3A%2F%2Fwww.finacle.com%2Ffixml+AcctLienMod.xsd%22+xmlns%3D%22http%3A%2F%2Fwww.finacle.com%2Ffixml%22+xmlns%3Axsi%3D%22http%3A%2F%2Fwww.w3.org%2F2001%2FXMLSchema-instance%22%3E%3CHeader%3E%0D%0A%3CRequestHeader%3E%0D%0A%3CMessageKey%3E%0D%0A%3CRequestUUID%3E%7B0%7D%3C%2FRequestUUID%3E%0D%0A%3CServiceRequestId%3EAcctLienMod%3C%2FServiceRequestId%3E%0D%0A%3CServiceRequestVersion%3E10.2%3C%2FServiceRequestVersion%3E%0D%0A%3CChannelId%3ECOR%3C%2FChannelId%3E%0D%0A%3CLanguageId%3E%3C%2FLanguageId%3E%0D%0A%3C%2FMessageKey%3E%0D%0A%3CRequestMessageInfo%3E%0D%0A%3CBankId%3E%3C%2FBankId%3E%0D%0A%3CTimeZone%3E%3C%2FTimeZone%3E%0D%0A%3CEntityId%3E%3C%2FEntityId%3E%0D%0A%3CEntityType%3E%3C%2FEntityType%3E%0D%0A%3CArmCorrelationId%3E%3C%2FArmCorrelationId%3E%0D%0A%3CMessageDateTime%3E%7B1%7D%3C%2FMessageDateTime%3E%0D%0A%3C%2FRequestMessageInfo%3E%0D%0A%3CSecurity%3E%0D%0A%3CToken%3E%0D%0A%3CPasswordToken%3E%0D%0A%3CUserId%3E%3C%2FUserId%3E%0D%0A%3CPassword%3E%3C%2FPassword%3E%0D%0A%3C%2FPasswordToken%3E%0D%0A%3C%2FToken%3E%0D%0A%3CFICertToken%3E%3C%2FFICertToken%3E%0D%0A%3CRealUserLoginSessionId%3E%3C%2FRealUserLoginSessionId%3E%0D%0A%3CRealUser%3E%3C%2FRealUser%3E%0D%0A%3CRealUserPwd%3E%3C%2FRealUserPwd%3E%0D%0A%3CSSOTransferToken%3E%3C%2FSSOTransferToken%3E%0D%0A%3C%2FSecurity%3E%0D%0A%3C%2FRequestHeader%3E%0D%0A%3C%2FHeader%3E%0D%0A%3CBody%3E%0D%0A%3CAcctLienModRequest%3E%0D%0A%3CAcctLienModRq%3E%0D%0A%3CAcctId%3E%0D%0A%3CAcctId%3E%7B2%7D%3C%2FAcctId%3E%0D%0A%3C%2FAcctId%3E%0D%0A%3CModuleType%3EULIEN%3C%2FModuleType%3E%0D%0A%3CLienDtls%3E%0D%0A%3CLienId%3E%7B3%7D%3C%2FLienId%3E%0D%0A%3CNewLienAmt%3E%0D%0A%3CamountValue%3E%7B4%7D%3C%2FamountValue%3E%0D%0A%3CcurrencyCode%3EINR%3C%2FcurrencyCode%3E%0D%0A%3C%2FNewLienAmt%3E%0D%0A%3CLienDt%3E%0D%0A%3CStartDt%3E%7B5%7D%3C%2FStartDt%3E%0D%0A%3CEndDt%3E%7B6%7D%3C%2FEndDt%3E%0D%0A%3C%2FLienDt%3E%0D%0A%3CReasonCode%3E001%3C%2FReasonCode%3E%0D%0A%3C%2FLienDtls%3E%0D%0A%3C%2FAcctLienModRq%3E%0D%0A%3C%2FAcctLienModRequest%3E%0D%0A%3C%2FBody%3E%0D%0A%3C%2FFIXML%3E%0D%0A";
		String decodeURL = null;
		try {
			decodeURL = URLDecoder.decode( eStr, "UTF-8" );
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        //String eStr = "%3C%3Fxml%20version%3D%221.0%22%20encoding%3D%22UTF-8%22%3F%3E%0A%3CFIXML%20xsi%3AschemaLocation%3D%22http%3A//www.finacle.com/fixml%20AcctLienMod.xsd%22%20xmlns%3D%22http%3A//www.finacle.com/fixml%22%20xmlns%3Axsi%3D%22http%3A//www.w3.org/2001/XMLSchema-instance%22%3E%3CHeader%3E%0A%3CRequestHeader%3E%0A%3CMessageKey%3E%0A%3CRequestUUID%3EReq_1514956719841%3C/RequestUUID%3E%0A%3CServiceRequestId%3EAcctLienMod%3C/ServiceRequestId%3E%0A%3CServiceRequestVersion%3E10.2%3C/ServiceRequestVersion%3E%0A%3CChannelId%3ECOR%3C/ChannelId%3E%0A%3CLanguageId%3E%3C/LanguageId%3E%0A%3C/MessageKey%3E%0A%3CRequestMessageInfo%3E%0A%3CBankId%3E%3C/BankId%3E%0A%3CTimeZone%3E%3C/TimeZone%3E%0A%3CEntityId%3E%3C/EntityId%3E%0A%3CEntityType%3E%3C/EntityType%3E%0A%3CArmCorrelationId%3E%3C/ArmCorrelationId%3E%0A%3CMessageDateTime%3E2018-00-03T10%3A48%3A39.841%3C/MessageDateTime%3E%0A%3C/RequestMessageInfo%3E%0A%3CSecurity%3E%0A%3CToken%3E%0A%3CPasswordToken%3E%0A%3CUserId%3E%3C/UserId%3E%0A%3CPassword%3E%3C/Password%3E%0A%3C/PasswordToken%3E%0A%3C/Token%3E%0A%3CFICertToken%3E%3C/FICertToken%3E%0A%3CRealUserLoginSessionId%3E%3C/RealUserLoginSessionId%3E%0A%3CRealUser%3E%3C/RealUser%3E%0A%3CRealUserPwd%3E%3C/RealUserPwd%3E%0A%3CSSOTransferToken%3E%3C/SSOTransferToken%3E%0A%3C/Security%3E%0A%3C/RequestHeader%3E%0A%3C/Header%3E%0A%3CBody%3E%0A%3CAcctLienModRequest%3E%0A%3CAcctLienModRq%3E%0A%3CAcctId%3E%0A%3CAcctId%3E010200101693940%3C/AcctId%3E%0A%3C/AcctId%3E%0A%3CModuleType%3EULIEN%3C/ModuleType%3E%0A%3CLienDtls%3E%0A%3CLienId%3EDC424318%3C/LienId%3E%0A%3CNewLienAmt%3E%0A%3CamountValue%3E0.00%3C/amountValue%3E%0A%3CcurrencyCode%3EINR%3C/currencyCode%3E%0A%3C/NewLienAmt%3E%0A%3CReasonCode%3E999%3C/ReasonCode%3E%0A%3C/LienDtls%3E%0A%3C/AcctLienModRq%3E%0A%3C/AcctLienModRequest%3E%0A%3C/Body%3E%0A%3C/FIXML%3E";
        System.out.println("Encoded URL: "+eStr);  
        // Getting decoder  
       // Base64.Decoder decoder = Base64.getUrlDecoder();  
        // Decoding URl  
       // String dStr = new String(decoder.decode(eStr));  
        System.out.println("Decoded URL: "+decodeURL);  
    }  */
}
