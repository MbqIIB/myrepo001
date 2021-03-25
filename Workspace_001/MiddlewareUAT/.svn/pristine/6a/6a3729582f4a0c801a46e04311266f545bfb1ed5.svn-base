package com.npst.middleware.util;

public enum ErrorCode {
	CBS_NO("NO", "CBS", "", "", "No original Debit/Credit found (ReqchkTxn) "),
	CBS_OC("OC", "CBS", "", "", "No original Credit found (REVERSAL) "),
	CBS_OD("OD", "CBS", "", "", "No original Debitt found (REVERSAL) "),
	CBS_ND("ND", "CBS", "", "", " NOT DEBIT (REVERSAL) "),
	CBS_NC("NC", "CBS", "", "", "NOI CREDIT (REVRSAL)  "),

	CBS_X6("X6", "CBS", "", "", "INVALID MERCHANT (ACQURIER) "),
	CBS_X7("X7", "CBS", "", "", "MERCHANT not reachable (ACQURIER) "),
	CBS_96("96", "CBS", "", "", "Reversal Failure"),
	CBS_XH("XH", "CBS", "", "RespListAccount", "Account does not exist "),
	CBS_BR("BR", "CBS", "", "RespListAccount", "Mobile number registered with multiple customer IDs "),
	CBS_B2("B2", "CBS", "", "RespListAccount", "Account linked with multiple names"),
	CBS_B_ZI("ZI", "CBS", "BENEFICIARY", "",
			"SUSPECTED FRAUD, DECLINE / TRANSACTIONS DECLINED BASED ON RISK SCORE BY BENEFICIARY "),
	CBS_B_Z5("Z5", "CBS", "BENEFICIARY", "", "INVALID BENEFICIARY CREDENTIALS "),
	CBS_B_ZY("ZY", "CBS", "BENEFICIARY", "", "INACTIVE OR DORMANT ACCOUNT (BENEFICIARY) "),
	CBS_B_XE("XE", "CBS", "BENEFICIARY", "", "INVALID AMOUNT (BENEFICIARY) "),
	CBS_B_XG("XG", "CBS", "BENEFICIARY", "", "FORMAT ERROR (INVALID FORMAT) (BENEFICIARY) "),
	CBS_B_XI("XI", "CBS", "BENEFICIARY", "", "ACCOUNT DOES NOT EXIST (BENEFICIARY) "),
	CBS_B_XK("XK", "CBS", "BENEFICIARY", "", "REQUESTED FUNCTION NOT SUPPORTED "),
	CBS_B_XU("XU", "CBS", "BENEFICIARY", "", "CUT-OFF IS IN PROCESS (BENEFICIARY) "),
	CBS_B_XW("XW", "CBS", "BENEFICIARY", "", "TRANSACTION CANNOT BE COMPLETED. COMPLIANCE VIOLATION (BENEFICIARY) "),
	CBS_B_Y1("Y1", "CBS", "BENEFICIARY", "", "BENEFICIARY CBS OFFLINE "),
	CBS_B_YF("YF", "CBS", "BENEFICIARY", "", "BENEFICIARY ACCOUNT BLOCKED/FROZEN "),
	CBS_B_XC("XC", "CBS", "BENEFICIARY", "",
			"INVALID TRANSACTION OR IF MEMBER IS NOT ABLE TO FIND ANY APPROPRIATE RESPONSE CODE (BENEFICIARY) "),
	CBS_R_Z9("Z9", "CBS", "REMITTER", "", "INSUFFICIENT FUNDS IN CUSTOMER (REMITTER) ACCOUNT "),
	CBS_R_K1("K1", "CBS", "REMITTER", "",
			"SUSPECTED FRAUD, DECLINE / TRANSACTIONS DECLINED BASED ON RISK SCORE BY REMITTER "),
	CBS_R_Z8("Z8", "Z8", "REMITTER", "", "PER TRANSACTION LIMIT EXCEEDED AS SET BY REMITTING MEMBER "),
	CBS_R_Z7("Z7", "CBS", "REMITTER", "", "TRANSACTION FREQUENCY LIMIT EXCEEDED AS SET BY REMITTING MEMBER "),
	CBS_R_ZX("ZX", "CBS", "REMITTER", "", "INACTIVE OR DORMANT ACCOUNT (REMITTER) "),
	CBS_R_XD("XD", "CBS", "REMITTER", "", "INVALID AMOUNT (REMITTER) "),
	CBS_R_XF("XF", "CBS", "REMITTER", "", "FORMAT ERROR (INVALID FORMAT) (REMITTER) "),
	CBS_R_XJ("XJ", "114", "REMITTER", "", "REQUESTED FUNCTION NOT SUPPORTED "),
	CBS_R_XT("XT", "CBS", "REMITTER", "", "CUT-OFF IS IN PROCESS (REMITTER) "),
	CBS_R_XV("XV", "CBS", "REMITTER", "", "TRANSACTION CANNOT BE COMPLETED. COMPLIANCE VIOLATION (REMITTER) "),
	CBS_R_XY("XY", "CBS", "REMITTER", "", "REMITTER CBS OFFLINE "),
	CBS_R_YE("YE", "CBS", "REMITTER", "", "REMITTING ACCOUNT BLOCKED/FROZEN "),
	CBS_R_XB("XB", "CBS", "REMITTER", "",
			"INVALID TRANSACTION OR IF MEMBER IS NOT ABLE TO FIND ANY APPROPRIATE RESPONSE CODE (REMITTER) "),
	CMS_XL("XL", "CMS", "", "RespRegMob", "Expired Card Details "),
	CMS_XN("XN", "CMS", "", "RespRegMob", "No Card Record found "),
	CMS_XR("XR", "CMS", "", "RespRegMob", "Restricted Card "),
	CMS_SP("SP", "CMS", "", "RespRegMob", "Invalid/Incorrect ATM PIN"),
	CMS_AJ("AJ", "CMS", "", "RespRegMob", "Card is not active"),
	CMS_B_XM("XM", "CMS", "BENEFICIARY", "", "EXPIRED CARD, DECLINE (BENEFICIARY) "),
	CMS_B_XO("XO", "CMS", "BENEFICIARY", "", "NO CARD RECORD (BENEFICIARY) "),
	CMS_B_XQ("XQ", "CMS", "BENEFICIARY", "", "TRANSACTION NOT PERMITTED TO CARDHOLDER (BENEFICIARY) "),
	CMS_B_XS("XS", "CMS", "BENEFICIARY", "", "RESTRICTED CARD, DECLINE (BENEFICIARY) "),
	CMS_B_YB("YB", "CMS", "BENEFICIARY", "", "LOST OR STOLEN CARD (BENEFICIARY) "),
	CMS_B_YD("YD", "CMS", "BENEFICIARY", "", "DO NOT HONOUR (BENEFICIARY) "),
	CMS_R_YA("YA", "CMS", "REMITTER", "", "LOST OR STOLEN CARD (REMITTER) "),
	CMS_R_YC("YC", "CMS", "REMITTER", "", "DO NOT HONOUR (REMITTER) "),
	MB_B1("B1", "MB", "", "", "Registered Mobile number linked to the account has been changed/removed "),
	MB_B3("B3", "MB", "", "", "Transaction not permitted to the account "),
	MB_ZM("ZM", "MB", "", "RespRegMob", "Invalid / Incorrect MPIN "),
	MB_U76("U76", "MB", "", "RespRegMob", "not live on format2 "),
	MB_Z6("Z6", "MB", "", "RespBalEnq", "No of PIN tries exceeded "),
	MB_HS("HS", "MB", "", "HSMService", "HSM is Down "),
	MB_RM("RM", "MB", "", "RespSetCred", "Invalid MPIN ( Violation of policies while setting/changing MPIN ) "),
	MB_RZ("RZ", "MB", "", "RespListAccount", "Account is already registered with MBEBA flag as 'Y' "),
	MB_AM("AM", "MB", "", "RespBalEnq,SetCre", "MPIN not set by customer "),
	OTP_ZR("ZR", "OTP", "", "RespRegMob", "Invalid / Incorrect OTP "),
	OTP_ZS("ZS", "OTP", "", "RespRegMob", "OTP Time expired "),
	OTP_ZT("ZT", "OTP", "", "RespRegMob", "Number of OTP’s ties has been exceeded "),
	PSP_ZD("ZD", "PSP", "", "", "VALIDATION ERROR "),
	PSP_ZP("ZP", "PSP", "", "", "BANKS AS BENEFICIARY NOT LIVE ON PARTICULAR TXN TYPE "),
	PSP_ZA("ZA", "PSP", "", "", "TRANSACTION DECLINED BY CUSTOMER"),
	PSP_ZH("ZH", "PSP", "", "", "INVALID VIRTUAL ADDRESS"),
	PSP_UX("UX", "PSP", "", "", "EXPIRED VIRTUAL ADDRESS"),
	PSP_ZG("ZG", "PSP", "", "", "VPA RESTRICTED BY CUSTOMER "),
	PSP_ZE("ZE", "PSP", "", "", "TRANSACTION NOT PERMITTED TO VPA by the PSP"),
	PSP_ZB("ZB", "PSP", "", "", "INVALID MERCHANT (PAYEE PSP)"),
	PSP_YG("YG", "PSP", "", "", "MERCHANT ERROR (PAYEE PSP) "),
	PSP_X1("X1", "PSP", "", "", "RESPONSE NOT RECEIVED WITHIN TAT AS SET BY PAYEE "),
	PSP_TM("TM", "PSP", "", "", "Collect request is declined as requestor is blocked by customer"),
	PSP_RN("RN", "PSP", "", "RespRegMob", "Registration is temporary blocked due to maximum no of attempts exceeded "),
	PSP_FL("FL", "CBS", "REMITTER", "RespPay", "First transaction limit exceeded"),
	PSP_FP("FP", "CBS", "REMITTER", "RespPay", "Freeze period for first time user"),
	PSP_FIR_NE("NE", "PSP", "Beneficiary", "RespPay", "FIR not allowed in the system"),
	DUPLICATE_MANDATE_IA("IA","CBS", "REMITTER","ReqMandate", "DUPLICATE BLOCKFUND FOR MANDATE REQUEST"),
	CBS_IR("IR","CBS", "","", "UNABLE TO PROCESS DUE TO INTERNAL EXCEPTION AT  REMITTER SIDE");
	
	
	private final String	upiCode;
	private final String	system;
	private final String	rOrB;
	private final String	api;
	private final String	description;
	
	private ErrorCode(String upiCode, String system, String rOrB, String api, String description) {
		this.upiCode = upiCode;
		this.system = system;
		this.api = api;
		this.rOrB = rOrB;
		this.description = description;
	}
	
	public String getApi() {
		return api;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getrOrB() {
		return rOrB;
	}
	
	public String getSystem() {
		return system;
	}
	
	public String getUpiCode() {
		return upiCode;
	}
	
	@Override
	public String toString() {
		return upiCode + "|" + system + "|" + rOrB + "|" + api + "|" + description;
	}
}
