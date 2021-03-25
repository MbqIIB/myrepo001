package com.npst.upiserver.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;

import javax.xml.bind.JAXB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.TransNoDao;
import com.npst.upiserver.dao.impl.TransNoDaoImpl;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.MandatesEntity;
import com.npst.upiserver.npcischema.AccountType;
import com.npst.upiserver.npcischema.AccountType.Detail;
import com.npst.upiserver.npcischema.DeviceTagNameType;
import com.npst.upiserver.npcischema.DeviceType;
import com.npst.upiserver.npcischema.DeviceType.Tag;
import com.npst.upiserver.npcischema.ReqMandate;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

public class Util {
	private static final Logger log = LoggerFactory.getLogger(Util.class);
	private static final Properties pspprop = new Properties();
	private static final Properties smsprop = new Properties();
	private static final DateTimeFormatter ddMMyyyyFormat = DateTimeFormatter.ofPattern("ddMMyyyy");

	static {
		try (InputStream pspin = new FileInputStream("config/application-psp.properties");
				InputStream smsin = new FileInputStream("config/application-sms.properties")) {
			pspprop.load(pspin);
			log.debug("application-psp.properties loaded {}", pspprop);
			smsprop.load(smsin);
			log.debug("application-sms.properties loaded {}", smsprop);
		} catch (Exception e) {
			ErrorLog.sendError(e, Util.class);
			log.error(
					"error while loading static properties application-psp.properties or application-sms.properties {}",
					e);
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static String getCurrent_ddMMyyyy() {
		return ddMMyyyyFormat.format(LocalDate.now());
	}
	
	
	
	public static String getProperty(String propName) {
		return pspprop.getProperty(propName);
	}

	public static String getMessageProperty(String propName) {
		return smsprop.getProperty(propName);
	}

	public static <T> T unmarshal(String xmlStr, Class<T> t) {
		try (StringReader sr = new StringReader(xmlStr);) {
			return JAXB.unmarshal(sr, t);
		} catch (Exception e) {
			ErrorLog.sendError(e, Util.class);
			log.error("error while Unmarsheling {}", e);
			log.error("error while Unmarshalling apiClass={} ,errorMsg={} ,xmlStr={} ", t.getName(), e.getMessage(),
					xmlStr);
			e.printStackTrace();
		}
		return null;
	}

	public static String filterMobNumber(String linkValue) {
		// TODO Auto-generated method stub
		return linkValue;
	}

	public static String uuidGen() {
		// log.trace("");
		String uuid = Constant.bKPrf + UUID.randomUUID().toString()
				.replaceAll(ConstantI.CONST_SPCL_HIFN, ConstantI.CONST_BLANK).toUpperCase();
		log.trace("Return {} ", uuid);
		return uuid;
	}

	public static String getTS() {
		log.trace("");
		SimpleDateFormat sdfDate = new SimpleDateFormat(ConstantI.CONST_DATE_TIME_FORMAT_2);
		Date now = new Date();
		String strDate = sdfDate.format(now);
		strDate = strDate.substring(0, strDate.length() - 2) + ConstantI.CONST_SPCL_COLLON
				+ strDate.substring(strDate.length() - 2);
		return strDate;
	}
	
	public static ArrayList<String> strSplit(String str, char s) {
		String[] fields = str.split("\\" + s);
		ArrayList<String> strList = new ArrayList<>();
		for (String field : fields) {
			strList.add(field.trim());
		}
		log.trace("str[" + str + "]" + "s[" + s + "] return=[" + strList.toString() + "]");
		return strList;
	}
	
	
	
	/*public static String field11() {
		Random random = new Random();
		Integer J1 = 100000 + random.nextInt(900000);
		return J1 + "";
	}*/

	/*public static String rrn() {
		log.info("Inside the rrn method ");
		
		//TransNoDao transDao = BeanUtil.getBean(TransNoDao.class);
		TransNoDaoImpl transDao = new TransNoDaoImpl();
		String stanNo = transDao.getTransNo(Integer.parseInt(Constant.orgId));
		//String stanNo=getRandomNumber(100000, 10000);
		String transNo = String.format("%06d", Integer.parseInt(stanNo));
		SimpleDateFormat f = new SimpleDateFormat("HH");
		f.setTimeZone(TimeZone.getTimeZone("IST"));
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("IST"));
		String yy = String.valueOf(c.get(Calendar.YEAR)).substring(3);
		String dd = String.valueOf(c.get(Calendar.DAY_OF_YEAR));
		dd = String.format("%03d", Integer.parseInt(dd));
		String hh = f.format(new Date());
		hh = String.format("%02d", Integer.parseInt(hh));
		String J1 = yy + dd + hh;
		log.info("end the rrn method [" + J1 + "" + transNo + "]");
		return J1 + transNo;
	}*/
	
/*	public static String getUmnNum() {
		log.info("Inside the rrn method ");
		//TransNoDao transDao = BeanUtil.getBean(TransNoDao.class);
		//TransNoDao transDao = new TransNoDaoImpl();
		//String stanNo = transDao.getTransNo(Integer.parseInt(Constant.orgId)).trim();
		String stanNo=getRandomNumber(100000, 10000);
		String transNo = String.format("%06d", Integer.parseInt(stanNo));
		SimpleDateFormat f = new SimpleDateFormat("HH");
		f.setTimeZone(TimeZone.getTimeZone("IST"));
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("IST"));
		String yy = String.valueOf(c.get(Calendar.YEAR)).substring(3);
		String dd = String.valueOf(c.get(Calendar.DAY_OF_YEAR));
		dd = String.format("%03d", Integer.parseInt(dd));
		String hh = f.format(new Date());
		hh = String.format("%02d", Integer.parseInt(hh));
		String J1 = yy + dd + hh;
		log.info("end the rrn method [" + J1 + "" + transNo + "]");
		return J1 + transNo;
	}*/
	private static String getRandomNumber(int max, int min) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		Random r = new Random();
		return String.valueOf((r.nextInt((max - min) + 1) + min));
	}
	
	public static String formBal(String message) {
		String actualBalWithoutINR[] = null;
		String messageBalWithoutINR[] = null;
		String actualBal = "1001356", afterDecimal = "";
		messageBalWithoutINR = message.split("INR");
		Double balDouble = Double.valueOf(messageBalWithoutINR[0].trim());
		if (balDouble >= 0) {
			actualBal = actualBal + "C";
		} else {
			actualBal = actualBal + "D";
			balDouble = balDouble * (-1);
		}
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		afterDecimal = decimalFormat.format(balDouble);
		String tempBal = afterDecimal.replace(".", "");
		actualBal = actualBal + Util.padLeft(tempBal, 12, '0');
		String avilBal = "1002356", afterAvailDecimal = "";
		Double availBalDouble = Double.valueOf(messageBalWithoutINR[0].trim());
		if (availBalDouble >= 0) {
			avilBal = avilBal + "C";
		} else {
			avilBal = avilBal + "D";
			availBalDouble = availBalDouble * (-1);
		}
		afterAvailDecimal = decimalFormat.format(availBalDouble);
		String tempAvilBal = afterAvailDecimal.replace(".", "");
		avilBal = avilBal + Util.padLeft(tempAvilBal, 12, '0');
		actualBal = actualBal.concat(avilBal);
		actualBalWithoutINR = actualBal.split("INR");
		return Util.base64Encode(actualBalWithoutINR[0]);
	}
	
	public static String base64Encode(byte[] encryptedData) {
		Util.log.trace("encryptedData[" + encryptedData + "]");
		String encodedString = null;
		try {
			encodedString=Base64.getEncoder().encodeToString(encryptedData);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			Util.log.error(s.toString());
		}
		Util.log.trace("return encodedString[" + encodedString + "]");
		return encodedString;
	}
	public static String base64Encode(String normalStr) {
		Util.log.trace("normalStr[" + normalStr + "]");
		String encodedString = new String();
		try {
			encodedString=Base64.getEncoder().encodeToString(normalStr.getBytes());
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			Util.log.error(s.toString());
		}
		Util.log.trace("return encodedString[" + encodedString + "]");
		return encodedString;
	}
	public static String padLeft(String str, int n, char s) {
		return String.format("%" + n + "s", str).replace(' ', s);
	}
	/*public static String field11() {
		Random random = new Random();
		Integer J1 = 100000 + random.nextInt(900000);
		return J1 + "";
	}*/
	public static Long getDateDiffInMinutes(Date startDate,Date endDate ){
		try {
			if(startDate!=null && endDate!=null) {
				long diff= endDate.getTime() - startDate.getTime();
				return diff / (60 * 1000) % 60;
			}
		}
		catch (Exception e) {
			ErrorLog.sendError(e, Util.class);
			log.error(e.getMessage(),e);
		}
		return 0l;
	}
	
	
	public static DeviceType getPayerDeviceTypeDefault(final ReqResp reqResp) {
		DeviceType device = new DeviceType();
		List<Tag> tags = device.getTag();
		{
			{
				
				Tag tag = new Tag();
				tag.setName(DeviceTagNameType.MOBILE);
				tag.setValue(reqResp.getLinkValue());
				tags.add(tag);
				tag = new Tag();
				
				tag.setName(DeviceTagNameType.GEOCODE);
				tag.setValue("18.5477691,73.8257334");
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.LOCATION);
				//tag.setValue(reqResp.getPayerDeviceLocation());
				tag.setValue("PUNE");
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.IP);
				tag.setValue("192.168.43.59");
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.TYPE);
				//tag.setValue(reqResp.getPayerDeviceType());
				tag.setValue("MOB");
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.ID);
				tag.setValue("b533cb659df3ef69bc1d892a67ff49e9e");
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.OS);
				tag.setValue("Android 1.0.2");
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.APP);
				tag.setValue("in.upi.npst.cosmostest");
				tags.add(tag);
				tag = new Tag();
				tag.setName(DeviceTagNameType.CAPABILITY);
				tag.setValue("1234123456745");
				tags.add(tag);
				/*tag = new Tag();
				tag.setName(DeviceTagNameType.ID);
				tag.setValue("432377D68751651AGFTBWVYAGVHZAAGFTBW");
				tags.add(tag);*/
				//tag = new Tag();
				/*tag.setName(DeviceTagNameType.TELECOM);
				tag.setValue("Airtel");
				tags.add(tag);*/
				/*tag.setName(DeviceTagNameType.TELECOM);
				if (reqResp.getPayerDeviceTelecom() != null && !reqResp.getPayerDeviceTelecom().isEmpty()) {
					tag.setValue(reqResp.getPayerDeviceTelecom());
					tags.add(tag);
				}else {
					tag.setValue("Airtel");
					tags.add(tag);
				}*/
			}
		}
		return device;
	}
	
	public static String maskNumber(String payerAccNo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static long fromNumberObj(Object obj) {
		long l = 0;
		try {
			if (obj instanceof BigDecimal) {
				l = ((BigDecimal) obj).longValue();
			}else if(obj instanceof BigInteger) {
				l = ((BigInteger) obj).longValue();
			}
			else if(obj instanceof Long) {
				l = (Long) obj;
			}else{
				l=Long.valueOf(obj.toString());
			}
		}catch(Exception e) {
			ErrorLog.sendError(e, Util.class);
			log.error("long value fromNumberObject {}",e);
			ErrorLog.sendError(e, Util.class);
		}
		return l;
	}
	
	public static long convertAmountInPaisa(String amount){
		long amt = 0;
		try {
			BigDecimal _a = new BigDecimal(amount);
			_a = _a.multiply(new BigDecimal(100));
			amt = _a.longValue();
		} catch (Exception e) {
			log.error("error in converting paisa from amount={}", amount);
		}
		return amt;
	}
	public static boolean isMandateExpired(MandatesEntity mandates) {
		LocalDate today = LocalDate.now();
		String validityend = mandates.getMandateValidityEnd();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
		LocalDate validityenddate = LocalDate.parse(validityend, formatter);
		if (validityenddate.isBefore(today)) {
			return true;
		}
		
		return false;
	}
	
	public static String getAccpuntType(ReqMandate reqMandate) {
		
		
		AccountType ac = reqMandate.getPayer().getAc();
		if (ConstantI.ACCOUNT.equalsIgnoreCase(ac.getAddrType().value())) {
			List<Detail> details = ac.getDetail();
			for (Detail detail : details) {
				
				if (ConstantI.ACTYPE.equalsIgnoreCase(detail.getName().value())) {
					return detail.getValue();
				}
			}
		}
		
		return null;
	}
	
}
