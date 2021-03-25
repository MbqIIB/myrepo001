package com.npst.upiserver.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.npst.upiserver.util.AESEncryptionUtility;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.Util;

public class Constant {

	public static final String MIDDLEWARE_SUCCESS_CODE = null;

	private static final Logger log = LoggerFactory.getLogger(Constant.class);
	public static final int MILLIS_IN_MINUTE = 60000;

	public static final Integer CREATE_MANDATE = 1;

	public static final Integer REQUEST_MANDATE = 2;

	public static String headVer = "";
	public static String orgId = "";
	public static String bKPrf = "";
	public static String bKEnv = "";
	public static String host = "";
	public static String user = "";
	public static String pass = "";
	public static String queueName = "";
	public static String TRANSACTIONDECLINEDBYCUSTOMER = "";
	public static String BLOCKBYCUSTOMER = "";
	public static String EXPIREDVIRTUALADDRESS = "";
	public static String TRANSACTIONDECLINEDBYPSPS0 = "";
	public static String REQLISTIFSC;
	public static String P12FILE = "";
	public static String password = "";
	public static String alias = "";
	public static String CERFILESIGN = "";
	public static String middleWareUrl = "";
	public static String bbpsAdapterUrl = "";
	public static Integer bbpsAdapterConnectTimeOut;
	public static String REQLISTACCPVDNOTE = "";
	public static String REQLISTACCPVDTXNREFID = "";
	public static String ReqTXNREFURL = "";
	public static String REQLISTACCPVDTXNREFURL = "";
	public static String NOTACTIVEVIRTUALADDRESS;
	public static String NOTPERMITTED = "";
	public static String INVALIDVIRTUALADDRESS;
	public static String ORG_ID_ATM = "";
	public static Integer ENABLE_NOTI = 0;

	public static long POOL_INT_DEALY = 0;
	public static long POOL_DEALY = 0;
	public static long THREAD_SLEEP = 0;
	public static Integer CON_TIME_OUT = 0;
	public static Integer REQ_TIME_OUT = 0;
	public static Integer SOCK_TIME_OUT = 0;
	public static Integer POOL_MAX = 0;
	public static Integer DEF_POOL_MAX = 0;

	public static Integer COLLECT_SEND = 0;
	public static Integer COLLECT_RECEIVE_TYPE = 0;
	public static Integer PAY_SEND = 0;
	public static Integer PAY_RECEIVE = 0;
	public static Integer BAL_ENQ = 0;
	public static Integer LIST_ACCOUNT = 0;
	public static Integer REG_MOB = 0;
	public static Integer REQ_OTP = 0;
	public static Integer SET_PIN = 0;
	public static String ONUS_BALANCE = "";
	public static String ONUS_FUNDTRANSFER = "";
	public static Integer CHECK_TXN = 0;
	public static String ACK_ERROR = "";
	public static String LIST_KEYS_GET_TYPE;
	public static int ListKeyOnlineRefreshCountMax;
	public static boolean IS_INTERNAL_MERCHANT_TXN_ALLOWED;
	public static boolean IS_INTERNAL_BTQR_M_TXN_ALLOWED;
	public static String BHARAT_QR_VPA_PREFIX;
	public static String BANK_HANDAL;
	public static String BHARAT_QRM_TYPE;
	public static String DEF_INITIATION_MODE;
	public static String DEFAULT_PURPOSE;
	public static String BANK_IIN;
	public static String BANKMMID_PREFIX;
	public static String BANKIIN_PREFIX;
	public static String BANKIFSC_PREFIX;

	public static Integer COLLECT_RECEIVE = 22;
	public static Integer FRAUD_LIST_ACC_ERR_CODE_XH_COUNT = 10;
	public static String VALIADDERRDESC;
	public static String BALERRDESC;
	public static String LISTERRDESC;
	public static String ERRDESC;
	public static boolean IS_FIR_ALLOW;
	public static boolean IS_MANDATE_ALLOW;
	public static String NOTPERMITTED_2KLIMIT_CUST;
	public static String NOTPERMITTED_NOTVERIFY_MER;
	public static String NOTPERMITTED_FOR_MCC;
	public static String TIMEPAY_HANDAL;
	
	static {
		try {
			log.info("Constant loading");

			ListKeyOnlineRefreshCountMax = Util.getProperty("LISTKEYS_ONLINE_REFRESH_COUNT_MAX") == null ? 50
					: Integer.parseInt(Util.getProperty("LISTKEYS_ONLINE_REFRESH_COUNT_MAX"));

			NOTACTIVEVIRTUALADDRESS = Util.getProperty("NOTACTIVEVIRTUALADDRESS");
			NOTPERMITTED = Util.getProperty("NOTPERMITTED");
			BLOCKBYCUSTOMER = Util.getProperty("BLOCKBYCUSTOMER");
			EXPIREDVIRTUALADDRESS = Util.getProperty("EXPIREDVIRTUALADDRESS");
			REQLISTACCPVDNOTE = Util.getProperty("REQLISTACCPVDNOTE");
			REQLISTACCPVDTXNREFID = Util.getProperty("REQLISTACCPVDTXNREFID");
			ReqTXNREFURL = Util.getProperty("ReqTXNREFURL");
			INVALIDVIRTUALADDRESS = Util.getProperty("INVALIDVIRTUALADDRESS");
			middleWareUrl = Util.getProperty("MIDDLEWAREURL");
			CERFILESIGN = Util.getProperty("CERFILESIGN");
			password = AESEncryptionUtility.decrypt(Util.getProperty("P12PASS"), AESEncryptionUtility.secretKeys);
			alias = Util.getProperty("P12ALIAS");
			P12FILE = Util.getProperty("P12FILE");
			TRANSACTIONDECLINEDBYCUSTOMER = Util.getProperty("TRANSACTIONDECLINEDBYCUSTOMER");
			TRANSACTIONDECLINEDBYPSPS0 = Util.getProperty("TRANSACTIONDECLINEDBYPSPS0");
			REQLISTIFSC = Util.getProperty("REQLISTIFSC") == null ? "" : Util.getProperty("REQLISTIFSC");
			bKEnv = Util.getProperty("BKENV");
			headVer = Util.getProperty("HEADVER");
			orgId = Util.getProperty("ORGID");
			bKPrf = Util.getProperty("BKPRF");
			host = Util.getProperty("QHOST");
			user = Util.getProperty("QUSERNAME");
			pass = AESEncryptionUtility.decrypt(Util.getProperty("QPASSWORD"), AESEncryptionUtility.secretKeys);
			queueName = Util.getProperty("LOGQUEUENAME");
			ORG_ID_ATM = Util.getProperty("ORG_ID_ATM");
			bbpsAdapterUrl = Util.getProperty("BbpsAdapterUrl");
			bbpsAdapterConnectTimeOut = Util.getProperty("BbpsAdapterConnectTimeOut") == null
					|| Util.getProperty("BbpsAdapterConnectTimeOut").trim().isEmpty() ? 31000
							: Integer.parseInt(Util.getProperty("BbpsAdapterConnectTimeOut"));

			POOL_MAX = Integer.parseInt(Util.getProperty("POOL_MAX"));
			DEF_POOL_MAX = Integer.parseInt(Util.getProperty("DEF_POOL_MAX"));
			CON_TIME_OUT = Integer.parseInt(Util.getProperty("CON_TIME_OUT"));
			REQ_TIME_OUT = Integer.parseInt(Util.getProperty("REQ_TIME_OUT"));
			SOCK_TIME_OUT = Integer.parseInt(Util.getProperty("SOCK_TIME_OUT"));
			POOL_INT_DEALY = Long.parseLong(Util.getProperty("POOL_INT_DEALY"));
			POOL_DEALY = Long.parseLong(Util.getProperty("POOL_DEALY"));
			THREAD_SLEEP = Long.parseLong(Util.getProperty("THREAD_SLEEP"));

			COLLECT_SEND = Integer.parseInt(Util.getProperty("COLLECT_SEND").trim());
			COLLECT_RECEIVE_TYPE = Integer.parseInt(Util.getProperty("COLLECT_RECEIVE_TYPE").trim());
			PAY_SEND = Integer.parseInt(Util.getProperty("PAY_SEND").trim());
			PAY_RECEIVE = Integer.parseInt(Util.getProperty("PAY_RECEIVE"));

			BAL_ENQ = Integer.parseInt(Util.getProperty("BAL_ENQ"));
			LIST_ACCOUNT = Integer.parseInt(Util.getProperty("LIST_ACCOUNT"));
			REG_MOB = Integer.parseInt(Util.getProperty("REG_MOB"));
			REQ_OTP = Integer.parseInt(Util.getProperty("REQ_OTP"));
			ONUS_BALANCE = Util.getProperty("ONUS_BALANCE");
			ONUS_FUNDTRANSFER = Util.getProperty("ONUS_FUNDTRANSFER");
			CHECK_TXN = Integer.parseInt(Util.getProperty("CHECK_TXN"));
			ACK_ERROR = Util.getProperty("ACK_ERROR");
			ENABLE_NOTI = Integer.parseInt(Util.getProperty("ENABLE_NOTI"));
			SET_PIN = Integer.parseInt(Util.getProperty("SET_PIN"));
			LIST_KEYS_GET_TYPE = Util.getProperty("LIST_KEYS_GET_TYPE") == null ? "ONLINE"
					: Util.getProperty("LIST_KEYS_GET_TYPE");
			IS_INTERNAL_MERCHANT_TXN_ALLOWED = Util.getProperty("IS_INTERNAL_MERCHANT_TXN_ALLOWED") == null ? false
					: "YES".equalsIgnoreCase(Util.getProperty("IS_INTERNAL_MERCHANT_TXN_ALLOWED"));
			BANK_HANDAL = Util.getProperty("BKHANDAL") == null ? "@cosb" : Util.getProperty("BKHANDAL");
			IS_INTERNAL_BTQR_M_TXN_ALLOWED = Util.getProperty("IS_INTERNAL_BTQR_M_TXN_ALLOWED") == null ? false
					: "YES".equalsIgnoreCase(Util.getProperty("IS_INTERNAL_BTQR_M_TXN_ALLOWED"));
			BHARAT_QR_VPA_PREFIX = Util.getProperty("BHARAT_QR_VPA_PREFIX") == null ? "btqr"
					: Util.getProperty("BHARAT_QR_VPA_PREFIX").toLowerCase();
			BHARAT_QRM_TYPE = Util.getProperty("BHARAT_QRM_TYPE") == null ? "BTQR"
					: Util.getProperty("BHARAT_QRM_TYPE");
			DEFAULT_PURPOSE = Util.getProperty("DEFAULT_PURPOSE");
			DEF_INITIATION_MODE = Util.getProperty("DEF_INITIATION_MODE");
			BANK_IIN = Util.getProperty("BANK_IIN");
			VALIADDERRDESC=Util.getProperty("VALIADDERRDESC");
			BALERRDESC=Util.getProperty("BALERRDESC");
			LISTERRDESC=Util.getProperty("LISTERRDESC");
			ERRDESC=Util.getProperty("ERRDESC");
			IS_FIR_ALLOW="YES".equalsIgnoreCase(Util.getProperty("IS_FIR_ALLOW"));
			IS_MANDATE_ALLOW="YES".equalsIgnoreCase(Util.getProperty("IS_MANDATE_ALLOW"));
			NOTPERMITTED_2KLIMIT_CUST=Util.getProperty("NOTPERMITTED_2KLIMIT_CUST");
			NOTPERMITTED_NOTVERIFY_MER=Util.getProperty("NOTPERMITTED_NOTVERIFY_MER");
			NOTPERMITTED_FOR_MCC=Util.getProperty("NOTPERMITTED_FOR_MCC");
			TIMEPAY_HANDAL = Util.getProperty("TIMEPAY_HANDAL");
			log.info("Constant loaded");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error while loading Constants :{}", e);
		}
	}
	public static String CONST_RESP_CODE="respCode";
	public static String CONST_LINK="link";
	public static String CONST_MINUTE="minute";
	public static String ERROR_CODE_XH="XH";
	public static String ERROR_CODE_XHH="XHH";
	public static String CONST_TXN_ID="txnid";
	public static String CONST_FAILURE="1";
	public static final String	UPI_SENDLOGS				= "UPISendLogs";
	public static final int StatusSuccess_200               =200;
	public static final String	UPISendLogs					= "UPISendLogs";
	public static final String	MiddleWareSendLogs			= "MiddleWareSendLogs";
	public static final String	EXP1						= "EXP1";
}
