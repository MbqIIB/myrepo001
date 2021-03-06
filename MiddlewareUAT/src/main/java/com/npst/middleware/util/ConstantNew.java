package com.npst.middleware.util;

public class ConstantNew {
	public static String		ALGO							= "SHA1PRNG";
	public static final String	PROPFILENAME					= "src//main//resources//basic.xml";
	public static String		CONST_1							= "1200";
	public static int			CONST_2							= 3;
	public static String		CONST_3							= "31";
	public static int			CONST_4							= 4;
	public static int			CONST_5							= 7;
	public static int			CONST_6							= 11;
	public static int			CONST_7							= 12;
	public static int			CONST_8							= 102;
	public static String		CONST_9							= "basic.xml";
	public static int			CONST_10						= 39;
	public static int			CONST_11						= 48;
	public static final String	ENABLE_JPA_REPO					= "com.npst.middleware.dao";
	public static final String	COMPONENT_SCAN					= "com.npst.middleware";
	public static final String	ENTITY_SCAN						= "com.npst.middleware.entity";
	public static int			MAX_FAIL_ATTEMPT				= 3;
	public static String		SUCCESS_CODE					= "000";
	public static String		ATM_SUCCESS_CODE				= "00";
	public static String		SUCCESS_CODE_UPISERVER			= "0";
	public static String		RESP_CODE_01					= "01";
	public static int			TXN_STATUS_1					= 1;
	public static int			TXN_STATUS_2					= 2;
	public static int			TXN_STATUS_3					= 3;
	public static String		CONST_Y							= "Y";
	public static String		CONST_N							= "N";
	public static String		DEBIT							= "DEBIT";
	public static String		CREDIT							= "CREDIT";
	public static String		OTP_TEXT						= "your otp is ";
	public static String		SPLIT_STRING					= "//|";
	public static int			CHK_LIST_SIZE					= 0;
	public static String		HTTP_GET_METHOD					= "GET";
	public static String		NEW_LINE_CHAR					= "\n";
	public static String		BAL_ENQ							= "BalEnq";
	public static String		CONST_12						= "XB";
	public static String		INVALID_TXN_TYPE				= "INVALID TXNTYPE";
	public static String		INVALID_PAYER_ACC_NUM_IFSC		= "INVALID PAYERACCNUM OR PAYERIFSC";
	public static String		INVALID_CUST_REF_RRN			= "INVALID CUSTREF OR RRN";
	public static String		INVALID_MPIN					= "INVALID MPIN";
	public static String		REVERSAL						= "REVERSAL";
	public static String		ACCOUNT							= "ACCOUNT";
	public static String		MOBILE							= "MOBILE";
	public static String		INVALID_PAYER_MOBILE_NO_MMID	= "INVALID PAYERMOBILENO OR PAYERMMID";
	public static String		INVALID_PAYER_ADDR_TYPE			= "INVALID PAYERADDRTYPE";
	public static String		DATE_FORMAT_1					= "yyyymmddHHMMSS";
	public static String		DATE_FORMAT_2					= "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	public static String		WINDOWS							= "windows";
	public static String		INVALID_TXN_XB					= "XB";
	public static String		INVALID_TXN_XC					= "XC";
	public static String		PATH_WINDOWS					= "F:\\UPINOIDA\\conf\\";
	public static String		PATH_LINUX						= "/home/npst/upi/conf/";
	public static String		ENABLE_NOTIFICATION_YES			= "YES";
	public static String		MESSAGE_TYPE_FCM				= "MESSAGE_TYPE_FCM";
	public static String		MESSAGE_TYPE_OTP				= "MESSAGE_TYPE_OTP";
	public static String		MESSAGE_TYPE_SMS				= "MESSAGE_TYPE_SMS";
	public static String        ERROR_CODE_NPCI_96					="96";
	public static String 		NO_RESP							="UKN";
	public static String 		MANDATE_HAS_BEEN_REVOKED							="QC";
	
	// UPI PIN/ACCOUNT STATUS
	public static final int		ISSUER_BLOCK					= 4;
	public static final int		EXPIRED							= 5;
	public static final int		ACTIVE							= 0;
	public static final int		BLOCK							= 2;
	public static final int		INACTIVE						= 1;
	public static final String	notificationTemplate			= "Your Otp Send Successfully :";
	
	public static final int		TXNRISK_STAT_0					= 0;
	public static final int		TXNRISK_STAT_1					= 1;
	public static final int		TXNRISK_STAT_2					= 2;
	public static String ACCOUNT_VERICATION_ALLOW_CONSTANT = "ACCOUNT_VERIFICATION_ALLOW";
	public static String CREDIT_FIR_ALLOW_CONSTANT="CREDIT_FIR_ALLOW_CONSTANT";
	
	public static String        ACC_TYPE_CAA					="CAA";
	public static String        ACC_TYPE_SBA					="SBA";
	public static String        ACC_TYPE_ODA					="ODA";
	public static String        ACC_TYPE_SAVINGS					="SAVINGS";
	public static String        ACC_TYPE_CURRENT					="CURRENT";
	public static String        ACC_TYPE_UOD					="UOD";
	public static String        ACC_TYPE_NRE					="NRE";
	public static String        ACC_TYPE_NRO					="NRO";
	
	public static String        ACC_TYPE_SBNRE					="SBNRE";
	public static String        ACC_TYPE_SBNRO					="SBNRO";
	
	
	public static String        PAYEE_ADDRESS_TYPE_ACCOUNT					="ACCOUNT";
	public static String        PAYEE_ADDRESS_TYPE_MOBILE					="MOBILE";
	public static String        PAYEE_ADDRESS_TYPE_AADHAAR					="AADHAAR";
	
	public static String UNBLOCK_STATUS_SUCC="SUCCESS"; 
	public static String UNBLOCK_STATUS_FAIL="FAILURE"; 

	public static String MANDTAE_MODIFY="MODIFY";
	public static String MANDTAE_UNBLOCK="UNBLOCK";
	public static String MANDTAE_DEBIT="DEBIT";
	public static String MANDTAE_REV="REV";
	public static String MANDATE_BLOCK="BLOCK";
	public static String MANDATE_DEBIT_BLOCK="mandateblock";
	
	public String MANDATE_EXECUTION = "Mandate-Release";
	public static String MANDATE_BLOCKED = "BLOCK Fund";
	public static String MANDATE_MODIFYIED = "UPDATE Fund";
	public static String MANDATE_REVOKED = "REVOKE Fund";
	public String MANDATE_RESERSD = "BLOCK Fund Again";

	
	
}
