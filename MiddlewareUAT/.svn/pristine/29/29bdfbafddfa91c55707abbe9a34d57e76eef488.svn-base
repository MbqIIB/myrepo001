package com.npst.middleware.util;

import org.apache.log4j.Logger;

import com.npst.middleware.util.AESEncryptionUtility;
import com.npst.middleware.util.Util;

public class Constant {
	public static final String MOB = "MOB";

	static Logger			log								= Logger.getLogger(Constant.class.getName());
	
	public static String	headVer							= "";
	public static String	orgId							= "";
	public static String	bKPrf							= "";
	public static String	bKEnv							= "";
	public static String	host							= "";
	public static String	user							= "";
	public static String	pass							= "";
	public static String	queueName						= "";
	public static String	TRANSACTIONDECLINEDBYCUSTOMER	= "";
	public static String	REQLISTIFSC						= "";
	public static String	P12FILE							= "";
	public static String	password						= "";
	public static String	alias							= "";
	public static String	CERFILESIGN						= "";
	public static int		otpLen							= 6;
	
	public static final String SUCCESS_STATUS_ZERO = "0";
	public static final String FAILURE_STATUS_ONE = "1";
	public static final String HS = "HS";
	static {
		try {
			log.trace("Constant loading");
			CERFILESIGN = Util.getProperty("CERFILESIGN");
			password = AESEncryptionUtility.decrypt(Util.getProperty("P12PASS"),
					AESEncryptionUtility.secretKeys);
			alias = Util.getProperty("P12ALIAS");
			P12FILE = Util.getProperty("P12FILE");
			TRANSACTIONDECLINEDBYCUSTOMER = Util.getProperty("TRANSACTIONDECLINEDBYCUSTOMER");
			REQLISTIFSC = Util.getProperty("REQLISTIFSC");
			bKEnv = Util.getProperty("BKENV");
			headVer = Util.getProperty("HEADVER");
			orgId = Util.getProperty("ORGID");
			bKPrf = Util.getProperty("BKPRF");
			host = Util.getProperty("QHOST");
			user = Util.getProperty("QUSERNAME");
			pass = AESEncryptionUtility.decrypt(Util.getProperty("QPASSWORD"),
					AESEncryptionUtility.secretKeys);
			queueName = Util.getProperty("LOGQUEUENAME");
			otpLen = Integer.parseInt(Util.getProperty("OTP.LEN"));
			log.info("Constant loaded");
		} catch (Exception e) {
			log.error("Exception caught at loading constant file"+e);
			e.printStackTrace();
		}
	}
	
}
