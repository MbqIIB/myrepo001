package com.npst.mobileservice.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.TimeZone;
import java.util.UUID;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import javax.crypto.Cipher;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.npst.mobileservice.object.ReqJson;
import com.npst.upi.hor.CustomerMandatesEntity;
import com.npst.upi.hor.MandatesHistoryEntity;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

public class Util {
	private static final String	PROPFILENAME	= "MobileService.properties";
	static Logger				log				= Logger.getLogger(Util.class.getName());
	static Properties			prop			= null;
	
	public static byte[] base64Decode(String encodedString) {
		log.trace("encodedString[" + encodedString + "]");
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] decodedBytes = null;
		try {
			decodedBytes = decoder.decodeBuffer(encodedString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decodedBytes;
	}
	
	public static String generateOtp(final int size) {
		log.trace("generate Otp" + size);
		StringBuilder generatedToken = new StringBuilder();
		try {
			SecureRandom number = SecureRandom.getInstance(ConstantI.ALGO);
			for (int i = 0; i < size; i++) {
				generatedToken.append(number.nextInt(9));
			}
		} catch (NoSuchAlgorithmException e) {
			log.error("caught exception at generate Otp");
			e.printStackTrace();
		}
		log.info("generate Otp" + Integer.parseInt(generatedToken.toString()));
		log.info("ending the generate Otp");
		return generatedToken.toString();
	}
	
	public static String base64Encode(String normalStr) {
		Util.log.trace("normalStr[" + normalStr + "]");
		BASE64Encoder encoder = new BASE64Encoder();
		String encodedString = new String();
		try {
			encodedString = encoder.encodeBuffer(normalStr.getBytes());
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			Util.log.error(s);
		}
		Util.log.trace("return encodedString[" + encodedString + "]");
		return encodedString;
	}
	
	public static String CalculateChecksum(String data, String checksumkey) throws IOException {
		String res = data + checksumkey;
		StringBuffer sb = null;
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(res.getBytes());
			byte byteData[] = md.digest();
			// convert the byte to hex format method 1
			sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static String checkNull(String name) {
		if (null == name) { return ""; }
		return name;
	}
	
	public static long getCRC32(InputStream in) throws IOException {
		Checksum sum_control = new CRC32();
		for (int b = in.read(); b != -1; b = in.read()) {
			sum_control.update(b);
		}
		return sum_control.getValue();
	}
	
	public static Timestamp getCurrentTimeStamp() {
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		Timestamp currentTimeStamp = new Timestamp(date.getTime());
		return currentTimeStamp;
	}
	
	public static String getJSONStr(ReqJson req) {
		// log.trace("req[" + req.toString() + "]");
		Gson gson = new GsonBuilder().serializeNulls().create();
		String jsonStr = gson.toJson(req);
		// log.trace("return jsonStr[" + jsonStr + "]");
		return jsonStr;
	}
	
	/**
	 * @param propName
	 * @return
	 */
	// public static String getProperty(String propName) {
	// String propValue = null;
	// FileInputStream fs = null;
	// Properties prop = new Properties();
	// String osName = System.getProperty("os.name");
	// try {
	// if (osName.toLowerCase().contains("windows")) {
	// prop.load(fs = new FileInputStream("C:\\Conf\\" + PROPFILENAME));
	// } else {
	// prop.load(fs = new FileInputStream("/home/npst/upi/conf/" +
	// PROPFILENAME));
	// }
	// propValue = prop.getProperty(propName);
	// } catch (Exception e) {
	// StringWriter s;
	// e.printStackTrace(new PrintWriter(s = new StringWriter()));
	// log.error(s);
	// System.exit(0);
	// } finally {
	// try {
	// fs.close();
	// } catch (IOException e) {
	// StringWriter s;
	// e.printStackTrace(new PrintWriter(s = new StringWriter()));
	// log.error(s);
	// }
	// }
	// log.trace("[" + propName + "]=[" + propValue + "]");
	// return propValue;
	// }
	public static String calQus(String s) {
		String finalStr = "";
		String[] a = s.split(",");
		for (int i = 0; i < a.length; i++) {
			finalStr += ":txnType" + i + ",";
			
		}
		// System.out.println(finalStr.substring(0, finalStr.length() - 1));
		return finalStr.substring(0, finalStr.length() - 1);
		
	}
	
	public static String getProperty(String propName) {
		String propValue = null;
		FileInputStream fs = null;
		try {
			if (null == prop) {
				log.info("Loading Started " + PROPFILENAME);
				prop = new Properties();
				String osName = System.getProperty("os.name");
				if (osName.toLowerCase().contains("windows")) {
					prop.load(fs = new FileInputStream("c:\\conf\\" + PROPFILENAME));
				} else {
					///home/npst/Workspace_Cosmos/full_project/MobServiceTimePaySIT/src/
				//	/home/npst/Workspace_Cosmos/full_project/MobServiceTimePaySIT/src
					//prop.load(fs = new FileInputStream("/home/npst/Workspace_Cosmos/full_project/MobServiceTimePaySIT/src/" + PROPFILENAME));
					prop.load(fs = new FileInputStream("/home/npst/upi/conf/" + PROPFILENAME));
				}
				log.info("Loading Complete " + PROPFILENAME);
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
		} finally {
			try {
				if (null != fs) {
					fs.close();
				}
			} catch (IOException e) {
				StringWriter s;
				e.printStackTrace();
				e.printStackTrace(new PrintWriter(s = new StringWriter()));
				log.error(s);
			}
		}
		propValue = prop.getProperty(propName);
		log.info("[" + propName + "]=[" + propValue + "]");
		log.trace("[" + propName + "]=[" + propValue + "]");
		return (null == propValue) ? "" : propValue.trim();
	}
	
	public static String getStringFromStream(InputStream incomingData) {
		StringBuilder strBuild = new StringBuilder();
		String line = null;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			while ((line = in.readLine()) != null) {
				strBuild.append(line);
			}
			log.debug(strBuild.toString());
		} catch (IOException e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
		}
		return strBuild.toString();
	}
	
	/*public static void main(String[] args) throws ParseException {
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d=dtFormat.parse("2018-08-14 14:24:25");
		System.out.println(compareDateWithCurrntDate(d));
	}*/
	
	public static String nullPending(String respPayStatus) {
		try {
			if (null == respPayStatus) { return "PENDING"; }
		} catch (NullPointerException n) {
			return "PENDING";
		}
		return respPayStatus.toUpperCase();
	}
	
	// public static String getTransNo(String orgId){
	// log.trace("[ifscString][" + orgId + "] ");
	// // log.debug("Seq.getStanNo()");
	// // log.trace("deviceIdString[" + deviceIdString + "]");
	// if(orgId.isEmpty()){
	// orgId = Util.getProperty("ORGID");
	// }
	// Connection conn = DBConnection.getConnection();
	// String stanNo = DbStatement.getTransNoResult(conn, orgId);
	// DBConnection.closeConn(conn);
	// stanNo = String.format("%06d", Integer.parseInt(stanNo));
	// return stanNo;
	// }
	/**
	 * @param transNo
	 * @return RRN
	 */
	public static String rrn(String transNo) {
		log.trace("");
		SimpleDateFormat f = new SimpleDateFormat("HH");
		f.setTimeZone(TimeZone.getTimeZone("IST"));
		// String localTransTimeString =
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("IST"));
		// c.setTimeInMillis(System.currentTimeMillis());
		String yy = String.valueOf(c.get(Calendar.YEAR)).substring(3);
		String dd = String.valueOf(c.get(Calendar.DAY_OF_YEAR));
		dd = String.format("%03d", Integer.parseInt(dd));
		String hh = f.format(new Date());
		hh = String.format("%02d", Integer.parseInt(hh));
		String J1 = yy + dd + hh;
		log.trace("return rrn[" + J1 + "" + transNo + "]");
		return J1 + transNo;
	}
	
	public static ArrayList<String> strSplit(String str, char s) {
		log.trace("str[" + str + "]" + "s[" + s + "]");
		String[] fields = str.split("\\" + s);
		ArrayList<String> strList = new ArrayList<>();
		for (String field : fields) {
			strList.add(field.trim());
		}
		return strList;
	}
	
	public static String uuidGen() {
		log.trace("");
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		log.debug("UUID[" + uuid + "]");
		log.trace("Return[" + uuid + "]");
		return uuid.substring(0, 16);
	}
	
	public static boolean valid(ReqJson reqJson) {
		if (ConstantI.TT_BAL_ENQ.equalsIgnoreCase(reqJson.getTxnType())) { return true; }
		return true;
	}
	
	public static boolean isNullOrEmpty(String limitamount) {
		return limitamount == null || "".equalsIgnoreCase(limitamount.trim());
	}
	
	public static Date dateEndingHours(Date date) {
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(date);
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
		}
		return c.getTime();
	}
	
	/*
	 * Gives starting hours of the day
	 */
	public static Date dateStartingHours(Date date, Integer dateDiff) {
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(date);
			if (dateDiff != 0) {
				c.add(Calendar.DATE, -dateDiff);
			}
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
		}
		return c.getTime();
	}
	
	public static String getStringDate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date today = Calendar.getInstance().getTime();
		String reportDate = df.format(today);
		return reportDate;
	}
	
	public static Date getOnlyDate() throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date today = Calendar.getInstance().getTime();
		String reportDate = df.format(today);
		Date date = df.parse(reportDate);
		return date;
	}
	
	public static int compareDateWithCurrntDate(Date dateToCompare) {
		log.info("To compare date with today " + dateToCompare);
		try {
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal1.setTime(dateToCompare);
			cal2.setTime(new Date());
			if (cal1.after(cal2)) {
				return 1;
			} else if (cal1.before(cal2)) {
				return -1;
			} else if (cal1.equals(cal2)) {
				return 0;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return 0;
	}
	
	public static String tidGen() {
		try {
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			Calendar calendar = Calendar.getInstance();
			Date date = calendar.getTime();
			Timestamp currentTimeStamp = new Timestamp(date.getTime());
			long ud=currentTimeStamp.getTime();
			String ss=uuid+ud;
			System.out.println("TID="+ss);
			log.debug("TID[" + ss.substring(0, 25) + "]");
			return ss.substring(0, 25);
		} catch(Exception e) {
			log.error("Error: "+e);
		}
		return "";
		
	}
	
	
	public static boolean isMandateExpired(MandatesHistoryEntity mandates) {
		LocalDate today = LocalDate.now();
		String validityend = mandates.getMandateValidityEnd();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
		LocalDate validityenddate = LocalDate.parse(validityend, formatter);
		if (validityenddate.isBefore(today)) {
			return false;
		}
		
		return true;
	}
	
	public static boolean isMandateExpired(CustomerMandatesEntity item) {
		
		LocalDate today = LocalDate.now();
		String validityend = item.getMandateValidityEnd();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
		LocalDate validityenddate = LocalDate.parse(validityend, formatter);
		if (validityenddate.isBefore(today)) {
			return false;
		}
		
		return true;
	}
	
	public static String convertAmountInPaisa(String amount) throws Exception {
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
	public static String encript(byte[] text)
	{
		byte[] dectyptedText = null;
		try
		{
			log.info("inside encript method");
			// get an RSA cipher object and print the provider
			final Cipher cipher = Cipher.getInstance("RSA");
			// decrypt the text using the private key
			cipher.init(Cipher.ENCRYPT_MODE, key);
			dectyptedText = cipher.doFinal(text);
			log.info("inside encript done"+dectyptedText);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return new String(dectyptedText);
	}
	
	
	public static boolean compareCheckSum(String request,String generate) {
		log.info("Generated checksum-> " + generate);
		log.info("Requested checksum-> " + request);
		if (!request.equalsIgnoreCase(generate)) {
			return true;
		}
		return false;
	}
	
	public static boolean checkRequestId(String requestId) {
		log.info("Requested RequestId-> " + requestId);
		if(!StringUtils.isEmpty(requestId)) {
			return true;
		}
		return false;
	}
	
	
	
	public static void main(String[] args) {
		  byte[] b = "upi://pay?mc=9319962277@cosmos&type=Exact&pa=7906743578@cosmos&am=5.00&cu=AABD0876543&pn=XXXXXXXXXXXX0123&umn=cob310D06FE79D0415399A3F5E2E7710@cosmos&appname=tarun&txnId=COB8B10F0F3AD9D4A3F9797C902340274BC&tn=vb&validitystart=2020-06-19&validityend=2020-06-30&amrule=Onetime&recur=On&payeeCur=bhh".getBytes();
		//byte[] b ="abcd".getBytes();
         log.info("Encripted qrparam is"+Util.base64Encode(Util.encript(b)));
	}
	
	
	
}
