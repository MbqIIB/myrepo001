package com.npst.mobileservice.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.npst.mobileservice.object.ReqJson;
import com.npst.mobileservice.service.MobupireqrespjsonidService;
import com.npst.upi.hor.Mobupireqrespjsonid;

public class Validations {
	private static final Logger log = Logger.getLogger(Validations.class);
	private static MobupireqrespjsonidService mobupireqrespjsonidService = null;
	
	public static boolean jsonValidation(ReqJson reqJson) {
		switch (reqJson.getTxnFlag()) {
			case "BALENQ":
				return Validations.balEnqVal(reqJson);
			case "COLLECTACCEPT":
				return Validations.collectAcceptVal(reqJson);
			case "COLLECTREJECT":
				return Validations.collectRejectVal(reqJson);
			case "COLLECTBLOCK":
				return Validations.collectRejectVal(reqJson);
			case "LISTACCOUNT":
				return Validations.listAccVal(reqJson);
			case "LISTKEYS":
				return Validations.listKeysVal(reqJson);
			case "OTP":
				return Validations.otpVal(reqJson);
			case "PAYCOLLECT":
				return Validations.payCollectVal(reqJson);
			case "REGMOB":
				return Validations.regMobVal(reqJson);
			case "PENDINGMSG":
				return Validations.pendingMsgVal(reqJson);
			case "SETCRE":
				return Validations.setCreVal(reqJson);
			case "VALADD":
				return Validations.valAddVal(reqJson);
			case "REQMANDATE":
			return Validations.valCreMandate(reqJson);	
			case "RECMANDATE":
			return Validations.valCreMandate(reqJson);
			case "ChkTxn":
			case "CHKTXN":
				return isValidChkTxn(reqJson);
			default:
				log.error("NOT FOUND" + reqJson.getTxnFlag());
				break;
		}
		return false;
	}
	
	private static boolean balEnqVal(ReqJson reqJson) {
		try {
			if (null == reqJson.getRegId() || "".equalsIgnoreCase(reqJson.getRegId())) {
				log.warn("null == reqJson.getRegId() || \"\".equalsIgnoreCase(reqJson.getRegId())");
				return false;
			} else if (null == reqJson.getTxnId() || "".equalsIgnoreCase(reqJson.getTxnId().trim())) {
				log.warn("null == reqJson.getTxnId() || \"\".equalsIgnoreCase(reqJson.getTxnId().trim())");
				return false;
			} else if (null == reqJson.getTxnNote() || "".equalsIgnoreCase(reqJson.getTxnNote().trim())) {
				log.warn("null == reqJson.getTxnNote() || \"\".equalsIgnoreCase(reqJson.getTxnNote().trim())");
				return false;
			} else if (null == reqJson.getTxnRefId() || "".equalsIgnoreCase(reqJson.getTxnRefId().trim())) {
				log.warn("null == reqJson.getTxnRefId() || \"\".equalsIgnoreCase(reqJson.getTxnRefId().trim())");
				return false;
			} else if (null == reqJson.getTxnRefUrl() || "".equalsIgnoreCase(reqJson.getTxnRefUrl().trim())) {
				log.warn("null == reqJson.getTxnRefUrl() || \"\".equalsIgnoreCase(reqJson.getTxnRefUrl().trim())");
				return false;
			} else if (null == reqJson.getPayerAddr() || "".equalsIgnoreCase(reqJson.getPayerAddr().trim())) {
				log.warn("null == reqJson.getPayerAddr() || \"\".equalsIgnoreCase(reqJson.getPayerAddr().trim())");
				return false;
			} else if (null == reqJson.getPayerName() || "".equalsIgnoreCase(reqJson.getPayerName().trim())) {
				log.warn("null == reqJson.getPayerName() || \"\".equalsIgnoreCase(reqJson.getPayerName().trim())");
				return false;
			} else if (null == reqJson.getPayerSeqNum() || "".equalsIgnoreCase(reqJson.getPayerSeqNum().trim())) {
				log.warn("null == reqJson.getPayerSeqNum() || \"\".equalsIgnoreCase(reqJson.getPayerSeqNum().trim())");
				return false;
			} else if (null == reqJson.getPayerType() || "".equalsIgnoreCase(reqJson.getPayerType().trim())) {
				log.warn("null == reqJson.getPayerType() || \"\".equalsIgnoreCase(reqJson.getPayerType().trim())");
				return false;
			} else if (null == reqJson.getPayerCode() || "".equalsIgnoreCase(reqJson.getPayerCode().trim())) {
				log.warn("null == reqJson.getPayerCode() || \"\".equalsIgnoreCase(reqJson.getPayerCode().trim())");
				return false;
			} else if (null == reqJson.getPayerAcNum() || "".equalsIgnoreCase(reqJson.getPayerAcNum().trim())) {
				log.warn("null == reqJson.getPayerAcNum() || \"\".equalsIgnoreCase(reqJson.getPayerAcNum().trim())");
				return false;
			} else if (null == reqJson.getPayerAddrType() || "".equalsIgnoreCase(reqJson.getPayerAddrType().trim())) {
				log.warn(
						"null == reqJson.getPayerAddrType() || \"\".equalsIgnoreCase(reqJson.getPayerAddrType().trim())");
				return false;
			} else if (null == reqJson.getRegMobile() || "".equalsIgnoreCase(reqJson.getRegMobile().trim())) {
				log.warn("null == reqJson.getRegMobile() || \"\".equalsIgnoreCase(reqJson.getRegMobile().trim())");
				return false;
			} else if (null == reqJson.getPayerDeviceGeoCode()
					|| "".equalsIgnoreCase(reqJson.getPayerDeviceGeoCode().trim())) {
				log.warn(
						"null == reqJson.getDeviceGeoCode() || \"\".equalsIgnoreCase(reqJson.getDeviceGeoCode().trim())");
				return false;
			} else if (null == reqJson.getPayerDeviceLocation()
					|| "".equalsIgnoreCase(reqJson.getPayerDeviceLocation().trim())) {
				log.warn(
						"null == reqJson.getDeviceLocation() || \"\".equalsIgnoreCase(reqJson.getDeviceLocation().trim())");
				return false;
			} else if (null == reqJson.getPayerDeviceIp() || "".equalsIgnoreCase(reqJson.getPayerDeviceIp().trim())) {
				log.warn("null == reqJson.getDeviceIp() || \"\".equalsIgnoreCase(reqJson.getDeviceIp().trim())");
				return false;
			} else if (null == reqJson.getPayerDeviceType()
					|| "".equalsIgnoreCase(reqJson.getPayerDeviceType().trim())) {
				log.warn("null == reqJson.getDeviceType() || \"\".equalsIgnoreCase(reqJson.getDeviceType().trim())");
				return false;
			} else if (null == reqJson.getPayerDeviceOsType()
					|| "".equalsIgnoreCase(reqJson.getPayerDeviceOsType().trim())) {
				log.warn(
						"null == reqJson.getDeviceOsType() || \"\".equalsIgnoreCase(reqJson.getDeviceOsType().trim())");
				return false;
			} else if (null == reqJson.getPayerDeviceId() || "".equalsIgnoreCase(reqJson.getPayerDeviceId().trim())) {
				log.warn("null == reqJson.getDeviceId() || \"\".equalsIgnoreCase(reqJson.getDeviceId().trim())");
				return false;
			} else if (null == reqJson.getPayerDeviceAppId()
					|| "".equalsIgnoreCase(reqJson.getPayerDeviceAppId().trim())) {
				log.warn("null == reqJson.getDeviceAppId() || \"\".equalsIgnoreCase(reqJson.getDeviceAppId().trim())");
				return false;
			} else if (null == reqJson.getPayerDeviceCapability()
					|| "".equalsIgnoreCase(reqJson.getPayerDeviceCapability().trim())) {
				log.warn(
						"null == reqJson.getDeviceCapability()|| \"\".equalsIgnoreCase(reqJson.getDeviceCapability().trim())");
				return false;
			} else if (null == reqJson.getPayerIfsc() || "".equalsIgnoreCase(reqJson.getPayerIfsc().trim())) {
				log.warn("null == reqJson.getPayerIfsc() || \"\".equalsIgnoreCase(reqJson.getPayerIfsc().trim())");
				return false;
			} else if (11 != reqJson.getPayerIfsc().length() || 11 != reqJson.getPayerIfsc().length()) {
				log.warn("11 != reqJson.getPayerIfsc().length() || 11 != reqJson.getPayerIfsc().length()");
				return false;
			} else if (null == reqJson.getPayerAcType() || "".equalsIgnoreCase(reqJson.getPayerAcType().trim())) {
				log.warn("null == reqJson.getPayerAcType() || \"\".equalsIgnoreCase(reqJson.getPayerAcType().trim())");
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
			return false;
		}
	}
	
	private static boolean collectAcceptVal(ReqJson reqJson) {
		try {
			if ((null == reqJson.getRegId() || "".equalsIgnoreCase(reqJson.getRegId()))
					|| (null == reqJson.getTxnId() || "".equalsIgnoreCase(reqJson.getTxnId().trim()))
					//|| (null == reqJson.getTxnNote() || "".equalsIgnoreCase(reqJson.getTxnNote().trim()))
					|| (null == reqJson.getTxnRefId() || "".equalsIgnoreCase(reqJson.getTxnRefId().trim()))
					|| (null == reqJson.getTxnRefUrl() || "".equalsIgnoreCase(reqJson.getTxnRefUrl().trim()))
					|| (null == reqJson.getTxnType() || "".equalsIgnoreCase(reqJson.getTxnType().trim()))
					|| (null == reqJson.getPayerAddr() || "".equalsIgnoreCase(reqJson.getPayerAddr().trim()))
					|| (null == reqJson.getPayerName() || "".equalsIgnoreCase(reqJson.getPayerName().trim()))
					|| (null == reqJson.getPayerSeqNum() || "".equalsIgnoreCase(reqJson.getPayerSeqNum().trim()))
					|| (null == reqJson.getPayerType() || "".equalsIgnoreCase(reqJson.getPayerType().trim()))
					|| (null == reqJson.getMobileNo() || "".equalsIgnoreCase(reqJson.getMobileNo().trim()))
					|| (null == reqJson.getPayerDeviceGeoCode()
							|| "".equalsIgnoreCase(reqJson.getPayerDeviceGeoCode().trim()))
					|| (null == reqJson.getPayerDeviceLocation()
							|| "".equalsIgnoreCase(reqJson.getPayerDeviceLocation().trim()))
					|| (null == reqJson.getPayerDeviceIp() || "".equalsIgnoreCase(reqJson.getPayerDeviceIp().trim()))
					|| (null == reqJson.getPayerDeviceType()
							|| "".equalsIgnoreCase(reqJson.getPayerDeviceType().trim()))
					|| (null == reqJson.getPayerDeviceId() || "".equalsIgnoreCase(reqJson.getPayerDeviceId().trim()))
					|| (null == reqJson.getPayerDeviceOsType()
							|| "".equalsIgnoreCase(reqJson.getPayerDeviceOsType().trim()))
					|| (null == reqJson.getPayerDeviceAppId()
							|| "".equalsIgnoreCase(reqJson.getPayerDeviceAppId().trim()))
					|| (null == reqJson.getPayerDeviceCapability()
							|| "".equalsIgnoreCase(reqJson.getPayerDeviceCapability().trim()))
					|| ((null == reqJson.getPayerIfsc() || "".equalsIgnoreCase(reqJson.getPayerIfsc().trim()))
							|| 11 != reqJson.getPayerIfsc().length())
					|| (null == reqJson.getPayerAcType() || "".equalsIgnoreCase(reqJson.getPayerAcType().trim()))
					|| (null == reqJson.getPayerAcNum() || "".equalsIgnoreCase(reqJson.getPayerAcNum().trim()))
					|| (null == reqJson.getCredJsons())
					|| (null == reqJson.getPayerAmount() || "".equalsIgnoreCase(reqJson.getPayerAmount().trim()))
					|| (null == reqJson.getPayeeAddr() || "".equalsIgnoreCase(reqJson.getPayeeAddr().trim()))
					|| (null == reqJson.getPayeeName() || "".equalsIgnoreCase(reqJson.getPayeeName().trim()))
					|| (null == reqJson.getPayeeSeqNum() || "".equalsIgnoreCase(reqJson.getPayeeSeqNum().trim()))
					|| (null == reqJson.getPayeeCode() || "".equalsIgnoreCase(reqJson.getPayeeCode().trim()))) {
// || (null == reqJson.getMsgId() ||
// "".equalsIgnoreCase(reqJson.getMsgId().trim()))) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
			return false;
		}
	}
	
	private static boolean collectRejectVal(ReqJson reqJson) {
		try {
			if ((null == reqJson.getRegId() || "".equalsIgnoreCase(reqJson.getRegId()))
					|| (null == reqJson.getTxnId() || "".equalsIgnoreCase(reqJson.getTxnId().trim()))
					//|| (null == reqJson.getTxnNote() || "".equalsIgnoreCase(reqJson.getTxnNote().trim()))
					|| (null == reqJson.getTxnRefId() || "".equalsIgnoreCase(reqJson.getTxnRefId().trim()))
					|| (null == reqJson.getTxnRefUrl() || "".equalsIgnoreCase(reqJson.getTxnRefUrl().trim()))
//					|| (null == reqJson.getCustRef() || "".equalsIgnoreCase(reqJson.getCustRef().trim()))
					|| (null == reqJson.getTxnType() || "".equalsIgnoreCase(reqJson.getTxnType().trim()))
					|| (null == reqJson.getPayerAddr() || "".equalsIgnoreCase(reqJson.getPayerAddr().trim()))
//					|| (null == reqJson.getPayerName() || "".equalsIgnoreCase(reqJson.getPayerName().trim()))
					|| (null == reqJson.getPayerSeqNum() || "".equalsIgnoreCase(reqJson.getPayerSeqNum().trim()))
					|| (null == reqJson.getPayerType() || "".equalsIgnoreCase(reqJson.getPayerType().trim()))
					|| (null == reqJson.getPayerCode() || "".equalsIgnoreCase(reqJson.getPayerCode().trim()))
//					|| (null == reqJson.getPayerName() || "".equalsIgnoreCase(reqJson.getPayerName().trim()))
					|| (null == reqJson.getPayerAcNum() || "".equalsIgnoreCase(reqJson.getPayerAcNum().trim()))
					|| (null == reqJson.getMobileNo() || "".equalsIgnoreCase(reqJson.getMobileNo().trim()))
					|| (null == reqJson.getPayerDeviceGeoCode()
							|| "".equalsIgnoreCase(reqJson.getPayerDeviceGeoCode().trim()))
					|| (null == reqJson.getPayerDeviceLocation()
							|| "".equalsIgnoreCase(reqJson.getPayerDeviceLocation().trim()))
					|| (null == reqJson.getPayerDeviceIp() || "".equalsIgnoreCase(reqJson.getPayerDeviceIp().trim()))
					|| (null == reqJson.getPayerDeviceType()
							|| "".equalsIgnoreCase(reqJson.getPayerDeviceType().trim()))
					|| (null == reqJson.getPayerDeviceId() || "".equalsIgnoreCase(reqJson.getPayerDeviceId().trim()))
					|| (null == reqJson.getPayerDeviceOsType()
							|| "".equalsIgnoreCase(reqJson.getPayerDeviceOsType().trim()))
					|| (null == reqJson.getPayerDeviceAppId()
							|| "".equalsIgnoreCase(reqJson.getPayerDeviceAppId().trim()))
					|| (null == reqJson.getPayerDeviceCapability()
							|| "".equalsIgnoreCase(reqJson.getPayerDeviceCapability().trim()))
					|| (null == reqJson.getPayerAmount() || "".equalsIgnoreCase(reqJson.getPayerAmount().trim()))
					|| (null == reqJson.getPayeeAddr() || "".equalsIgnoreCase(reqJson.getPayeeAddr().trim()))
					|| (null == reqJson.getPayeeName() || "".equalsIgnoreCase(reqJson.getPayeeName().trim()))
					|| (null == reqJson.getPayeeSeqNum() || "".equalsIgnoreCase(reqJson.getPayeeSeqNum().trim()))
					|| (null == reqJson.getPayeeType() || "".equalsIgnoreCase(reqJson.getPayeeType().trim()))
					|| (null == reqJson.getPayeeCode() || "".equalsIgnoreCase(reqJson.getPayeeCode().trim())))
// || (null == reqJson.getMsgId() || "".equalsIgnoreCase(reqJson.getMsgId())))
			{
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
			return false;
		}
		
	}
	
	private static boolean listAccVal(ReqJson reqJson) {
		try {
			if ((null == reqJson.getRegId() || "".equalsIgnoreCase(reqJson.getRegId()))
					|| (null == reqJson.getTxnNote() || "".equalsIgnoreCase(reqJson.getTxnNote().trim()))
					|| (null == reqJson.getTxnRefId() || "".equalsIgnoreCase(reqJson.getTxnRefId().trim()))
					|| (null == reqJson.getTxnRefUrl() || "".equalsIgnoreCase(reqJson.getTxnRefUrl().trim()))
					|| (null == reqJson.getTxnType() || "".equalsIgnoreCase(reqJson.getTxnType().trim()))
					// || (null == reqJson.getPayerAddrType() ||
					// "".equalsIgnoreCase(reqJson.getPayerAddrType().trim()))
					|| (null == reqJson.getMobileNo() || "".equalsIgnoreCase(reqJson.getMobileNo().trim()))
					// || (null == reqJson.getAddhaarNo() ||
					// "".equalsIgnoreCase(reqJson.getAddhaarNo().trim()))
					|| ((null == reqJson.getPayerIfsc() || "".equalsIgnoreCase(reqJson.getPayerIfsc().trim())))//|| 4 != reqJson.getPayerIfsc().length()
					|| (null == reqJson.getPayerAcType() || "".equalsIgnoreCase(reqJson.getPayerAcType().trim()))
					|| (null == reqJson.getPayerAddr() || "".equalsIgnoreCase(reqJson.getPayerAddr().trim()))
					|| (null == reqJson.getPayerCode() || "".equalsIgnoreCase(reqJson.getPayerCode().trim()))
					// || (null == reqJson.getPayerName() ||
					// "".equalsIgnoreCase(reqJson.getPayerName().trim()))
					|| (null == reqJson.getPayerSeqNum() || "".equalsIgnoreCase(reqJson.getPayerSeqNum().trim()))
					|| (null == reqJson.getPayerType() || "".equalsIgnoreCase(reqJson.getPayerType().trim()))) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
			return false;
		}
	}
	
	private static boolean listKeysVal(ReqJson reqJson) {
		try {
			if ((null == reqJson.getRegId() || "".equalsIgnoreCase(reqJson.getRegId()))
					|| (null == reqJson.getTxnNote() || "".equalsIgnoreCase(reqJson.getTxnNote().trim()))
					|| (null == reqJson.getTxnRefId() || "".equalsIgnoreCase(reqJson.getTxnRefId().trim()))
					|| (null == reqJson.getTxnRefUrl() || "".equalsIgnoreCase(reqJson.getTxnRefUrl().trim()))
					|| (null == reqJson.getTxnType() || "".equalsIgnoreCase(reqJson.getTxnType().trim()))) {
				return false;
			} else {
				if ("GetToken".equalsIgnoreCase(reqJson.getTxnType())) {
					if ((null == reqJson.getPayerDeviceId() || "".equalsIgnoreCase(reqJson.getPayerDeviceId().trim()))
							|| (null == reqJson.getPayerDeviceAppId()
									|| "".equalsIgnoreCase(reqJson.getPayerDeviceAppId().trim()))
							|| (null == reqJson.getMobileNo() || "".equalsIgnoreCase(reqJson.getMobileNo().trim()))
							|| (null == reqJson.getTokenChallenge()
									|| "".equalsIgnoreCase(reqJson.getTokenChallenge().trim()))
							|| (null == reqJson.getTxnType() || "".equalsIgnoreCase(reqJson.getTxnType().trim()))) {
						return false;
					} else {
						return true;
					}
				} else {
					return true;
				}
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
			return false;
		}
	}
	
	private static boolean otpVal(ReqJson reqJson) {
		try {
			if ((null == reqJson.getRegId() || "".equalsIgnoreCase(reqJson.getRegId()))
					|| (null == reqJson.getTxnNote() || "".equalsIgnoreCase(reqJson.getTxnNote().trim()))
					|| (null == reqJson.getTxnRefId() || "".equalsIgnoreCase(reqJson.getTxnRefId().trim()))
					|| (null == reqJson.getTxnRefUrl() || "".equalsIgnoreCase(reqJson.getTxnRefUrl().trim()))
					|| (null == reqJson.getTxnType() || "".equalsIgnoreCase(reqJson.getTxnType().trim()))
					|| ((null == reqJson.getPayerIfsc() || "".equalsIgnoreCase(reqJson.getPayerIfsc().trim()))
							|| 11 != reqJson.getPayerIfsc().length())
					|| (null == reqJson.getPayerAcType() || "".equalsIgnoreCase(reqJson.getPayerAcType().trim()))
					|| (null == reqJson.getPayerAcNum() || "".equalsIgnoreCase(reqJson.getPayerAcNum().trim()))
					|| (null == reqJson.getPayerAddr() || "".equalsIgnoreCase(reqJson.getPayerAddr().trim()))
					|| (null == reqJson.getPayerName() || "".equalsIgnoreCase(reqJson.getPayerName().trim()))
					|| (null == reqJson.getPayerSeqNum() || "".equalsIgnoreCase(reqJson.getPayerSeqNum().trim()))
					|| (null == reqJson.getPayerType() || "".equalsIgnoreCase(reqJson.getPayerType().trim()))
					|| (null == reqJson.getMobileNo() || "".equalsIgnoreCase(reqJson.getMobileNo().trim()))
					|| (null == reqJson.getPayerDeviceGeoCode()
							|| "".equalsIgnoreCase(reqJson.getPayerDeviceGeoCode().trim()))
					|| (null == reqJson.getPayerDeviceLocation()
							|| "".equalsIgnoreCase(reqJson.getPayerDeviceLocation().trim()))
					|| (null == reqJson.getPayerDeviceIp() || "".equalsIgnoreCase(reqJson.getPayerDeviceIp().trim()))
					|| (null == reqJson.getPayerDeviceType()
							|| "".equalsIgnoreCase(reqJson.getPayerDeviceType().trim()))
					|| (null == reqJson.getPayerDeviceId() || "".equalsIgnoreCase(reqJson.getPayerDeviceId().trim()))
					|| (null == reqJson.getPayerDeviceOsType()
							|| "".equalsIgnoreCase(reqJson.getPayerDeviceOsType().trim()))
					|| (null == reqJson.getPayerDeviceAppId()
							|| "".equalsIgnoreCase(reqJson.getPayerDeviceAppId().trim()))
					|| (null == reqJson.getPayerDeviceCapability()
							|| "".equalsIgnoreCase(reqJson.getPayerDeviceCapability().trim()))) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
			return false;
		}
	}
	
	private static boolean payCollectVal(ReqJson reqJson) {
		try {
			if ((null == reqJson.getRegId() || "".equalsIgnoreCase(reqJson.getRegId()))
					|| (null == reqJson.getTxnId() || "".equalsIgnoreCase(reqJson.getTxnId().trim()))
					|| (null == reqJson.getTxnNote() || "".equalsIgnoreCase(reqJson.getTxnNote().trim()))
					|| (null == reqJson.getTxnRefId() || "".equalsIgnoreCase(reqJson.getTxnRefId().trim()))
					|| (null == reqJson.getTxnRefUrl() || "".equalsIgnoreCase(reqJson.getTxnRefUrl().trim()))
					|| (null == reqJson.getTxnType() || "".equalsIgnoreCase(reqJson.getTxnType().trim()))
					|| (null == reqJson.getMobileNo() || "".equalsIgnoreCase(reqJson.getMobileNo().trim()))
					|| (null == reqJson.getPayeeAddr() || "".equalsIgnoreCase(reqJson.getPayeeAddr().trim()))
					|| (null == reqJson.getPayeeName() || "".equalsIgnoreCase(reqJson.getPayeeName().trim()))
					|| (null == reqJson.getPayeeSeqNum() || "".equalsIgnoreCase(reqJson.getPayeeSeqNum().trim()))
					|| (null == reqJson.getPayeeType() || "".equalsIgnoreCase(reqJson.getPayeeType().trim()))
					// || (null == reqJson.getAccRefNumber() ||
					// "".equalsIgnoreCase(reqJson.getAccRefNumber().trim()))
					|| (null == reqJson.getPayerAddr() || "".equalsIgnoreCase(reqJson.getPayerAddr().trim()))
					|| (null == reqJson.getPayerName() || "".equalsIgnoreCase(reqJson.getPayerName().trim()))
					|| (null == reqJson.getPayerSeqNum() || "".equalsIgnoreCase(reqJson.getPayerSeqNum().trim()))
					|| (null == reqJson.getPayerType() || "".equalsIgnoreCase(reqJson.getPayerType().trim()))
					|| (null == reqJson.getPayerCode() || "".equalsIgnoreCase(reqJson.getPayerCode().trim()))) {
				return false;
			} else {
				if ("PAY".equalsIgnoreCase(reqJson.getTxnType())) {
					if ((null == reqJson.getCredJsons())
							|| (null == reqJson.getPayeeCode() || "".equalsIgnoreCase(reqJson.getPayeeCode().trim()))) {
						return false;
					} else {
						if ("ACCOUNT".equalsIgnoreCase(reqJson.getPayerAddrType())) {
							if (((null == reqJson.getPayerIfsc() || "".equalsIgnoreCase(reqJson.getPayerIfsc().trim()))
									|| 11 != reqJson.getPayerIfsc().length())
									|| (null == reqJson.getPayerAcType()
											|| "".equalsIgnoreCase(reqJson.getPayerAcType().trim()))
									|| (null == reqJson.getPayerAcNum()
											|| "".equalsIgnoreCase(reqJson.getPayerAcNum().trim()))) {
								return false;
							} else {
								return true;
							}
							
						} else if ("MOBILE".equalsIgnoreCase(reqJson.getPayeeAddrType())) {
							if ((null == reqJson.getPayerMmid() || "".equalsIgnoreCase(reqJson.getPayerMmid().trim()))
									|| (null == reqJson.getMobileNo()
											|| "".equalsIgnoreCase(reqJson.getMobileNo().trim()))) {
								return false;
							} else {
								return true;
							}
							
						} else if ("AADHAAR".equalsIgnoreCase(reqJson.getPayeeAddrType())) {
							if ((null == reqJson.getPayerIin() || "".equalsIgnoreCase(reqJson.getPayerIin().trim()))
									|| (null == reqJson.getPayeeadharno()
											|| "".equalsIgnoreCase(reqJson.getPayeeadharno().trim()))) { return false; }
						} else {
							return true;
						}
					}
				} else if ("COLLECT".equalsIgnoreCase(reqJson.getTxnType())) {
					if ((null == reqJson.getRuleExpireAfter()
							|| "".equalsIgnoreCase(reqJson.getRuleExpireAfter().trim()))
							|| (null == reqJson.getPayeeDeviceGeoCode()
									|| "".equalsIgnoreCase(reqJson.getPayeeDeviceGeoCode().trim()))
							|| (null == reqJson.getPayeeDeviceLocation()
									|| "".equalsIgnoreCase(reqJson.getPayeeDeviceLocation().trim()))
							|| (null == reqJson.getPayeeDeviceIp()
									|| "".equalsIgnoreCase(reqJson.getPayeeDeviceIp().trim()))
							|| (null == reqJson.getPayeeDeviceType()
									|| "".equalsIgnoreCase(reqJson.getPayeeDeviceType().trim()))
							|| (null == reqJson.getPayeeDeviceId()
									|| "".equalsIgnoreCase(reqJson.getPayeeDeviceId().trim()))
							|| (null == reqJson.getPayeeDeviceOsType()
									|| "".equalsIgnoreCase(reqJson.getPayeeDeviceOsType().trim()))
							|| (null == reqJson.getPayeeDeviceAppId()
									|| "".equalsIgnoreCase(reqJson.getPayeeDeviceAppId().trim()))
							|| (null == reqJson.getPayeeDeviceCapability()
									|| "".equalsIgnoreCase(reqJson.getPayeeDeviceCapability().trim()))
					// && (null == reqJson.getMinAmount() ||
					// "".equalsIgnoreCase(reqJson.getMinAmount().trim()))
					) {
						return false;
					} else {
						if ("ACCOUNT".equalsIgnoreCase(reqJson.getPayeeAddrType())) {
							if (((null == reqJson.getPayeeIfsc() || "".equalsIgnoreCase(reqJson.getPayeeIfsc().trim()))
									|| 11 != reqJson.getPayeeIfsc().length())
									|| (null == reqJson.getPayeeAcType()
											|| "".equalsIgnoreCase(reqJson.getPayeeAcType().trim()))
									|| (null == reqJson.getPayeeAcNum()
											|| "".equalsIgnoreCase(reqJson.getPayeeAcNum().trim()))) {
								return false;
							} else {
								return true;
							}
							
						} else if ("MOBILE".equalsIgnoreCase(reqJson.getPayeeAddrType())) {
							if ((null == reqJson.getMmid() || "".equalsIgnoreCase(reqJson.getMmid().trim()))
									|| (null == reqJson.getPayeemobilen()
											|| "".equalsIgnoreCase(reqJson.getPayeemobilen().trim()))) {
								return false;
							} else {
								return true;
							}
							
						} else if ("AADHAAR".equalsIgnoreCase(reqJson.getPayeeAddrType())) {
							if ((null == reqJson.getPayeeIin() || "".equalsIgnoreCase(reqJson.getPayeeIin().trim()))
									|| (null == reqJson.getPayeeadharno())
									|| "".equalsIgnoreCase(reqJson.getPayeeadharno().trim())) {
								return false;
							} else {
								return true;
							}
						} else {
							return true;
						}
					}
				}
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
			return false;
		}
		return false;
	}
	
	private static boolean pendingMsgVal(ReqJson reqJson) {
		try {
			if ((null == reqJson.getRegId() || "".equalsIgnoreCase(reqJson.getRegId()))
					|| (null == reqJson.getTxnId() || "".equalsIgnoreCase(reqJson.getTxnId().trim()))
					|| (null == reqJson.getTxnNote() || "".equalsIgnoreCase(reqJson.getTxnNote().trim()))
					|| (null == reqJson.getTxnRefId() || "".equalsIgnoreCase(reqJson.getTxnRefId().trim()))
					|| (null == reqJson.getTxnRefUrl() || "".equalsIgnoreCase(reqJson.getTxnRefUrl().trim()))
					|| (null == reqJson.getTxnType() || "".equalsIgnoreCase(reqJson.getTxnType().trim()))
					|| (null == reqJson.getPayerAddr() || "".equalsIgnoreCase(reqJson.getPayerAddr().trim()))
					|| (null == reqJson.getPayerName() || "".equalsIgnoreCase(reqJson.getPayerName().trim()))
					|| (null == reqJson.getPayerSeqNum() || "".equalsIgnoreCase(reqJson.getPayerSeqNum().trim()))
					|| (null == reqJson.getPayerType() || "".equalsIgnoreCase(reqJson.getPayerType().trim()))
					|| (null == reqJson.getPayerCode() || "".equalsIgnoreCase(reqJson.getPayerCode().trim()))
					|| (null == reqJson.getPayerAddrType() || "".equalsIgnoreCase(reqJson.getPayerAddrType().trim()))
					|| (null == reqJson.getPayerName() || "".equalsIgnoreCase(reqJson.getPayerName().trim()))
					|| (null == reqJson.getDeviceGeoCode() || "".equalsIgnoreCase(reqJson.getDeviceGeoCode().trim()))
					|| (null == reqJson.getDeviceLocation() || "".equalsIgnoreCase(reqJson.getDeviceLocation().trim()))
					|| (null == reqJson.getDeviceIp() || "".equalsIgnoreCase(reqJson.getDeviceIp().trim()))
					|| (null == reqJson.getDeviceType() || "".equalsIgnoreCase(reqJson.getDeviceType().trim()))
					|| (null == reqJson.getDeviceId() || "".equalsIgnoreCase(reqJson.getDeviceId().trim()))
					|| (null == reqJson.getDeviceOsType() || "".equalsIgnoreCase(reqJson.getDeviceOsType().trim()))
					|| (null == reqJson.getDeviceAppId() || "".equalsIgnoreCase(reqJson.getDeviceAppId().trim()))
					|| (null == reqJson.getDeviceCapability()
							|| "".equalsIgnoreCase(reqJson.getDeviceCapability().trim()))
					|| (null == reqJson.getPayeeAddr() || "".equalsIgnoreCase(reqJson.getPayeeAddr().trim()))
					|| (null == reqJson.getPayeeName() || "".equalsIgnoreCase(reqJson.getPayeeName().trim()))
					|| (null == reqJson.getPayeeSeqNum() || "".equalsIgnoreCase(reqJson.getPayeeSeqNum().trim()))
					|| (null == reqJson.getPayeeType() || "".equalsIgnoreCase(reqJson.getPayeeType().trim()))
					|| (null == reqJson.getPayeeCode() || "".equalsIgnoreCase(reqJson.getPayeeCode().trim()))) {
				return false;
			} else {
				if ("ACCOUNT".equalsIgnoreCase(reqJson.getPayerAddrType())) {
					if (((null == reqJson.getIfsc() || "".equalsIgnoreCase(reqJson.getIfsc().trim()))
							|| 11 != reqJson.getIfsc().length())
							|| (null == reqJson.getAccType() || "".equalsIgnoreCase(reqJson.getAccType().trim()))
							|| (null == reqJson.getAccRefNumber()
									|| "".equalsIgnoreCase(reqJson.getAccRefNumber().trim()))) {
						return false;
					} else {
						return true;
					}
					
				} else if ("MOBILE".equalsIgnoreCase(reqJson.getPayerAddrType())) {
					if ((null == reqJson.getMmid() || "".equalsIgnoreCase(reqJson.getMmid().trim()))
							|| (null == reqJson.getMobileNo() || "".equalsIgnoreCase(reqJson.getMobileNo().trim()))) {
						return false;
					} else {
						return true;
					}
					
				} else if ("AADHAAR".equalsIgnoreCase(reqJson.getPayerAddrType())) {
					if ((null == reqJson.getPayerIin() || "".equalsIgnoreCase(reqJson.getPayerIin().trim()))
							|| (null == reqJson.getAddhaarNo() || "".equalsIgnoreCase(reqJson.getAddhaarNo().trim()))) {
						return false;
					} else {
						return true;
					}
				} else {
					return true;
				}
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
			return false;
		}
	}
	
	private static boolean regMobVal(ReqJson reqJson) {
		try {
			if ((null == reqJson.getRegId() || "".equalsIgnoreCase(reqJson.getRegId()))
					|| (null == reqJson.getTxnId() || "".equalsIgnoreCase(reqJson.getTxnId().trim()))
					|| (null == reqJson.getTxnNote() || "".equalsIgnoreCase(reqJson.getTxnNote().trim()))
					|| (null == reqJson.getTxnRefId() || "".equalsIgnoreCase(reqJson.getTxnRefId().trim()))
					|| (null == reqJson.getTxnRefUrl() || "".equalsIgnoreCase(reqJson.getTxnRefUrl().trim()))
					|| (null == reqJson.getTxnType() || "".equalsIgnoreCase(reqJson.getTxnType().trim()))
					|| (null == reqJson.getPayerAddr() || "".equalsIgnoreCase(reqJson.getPayerAddr().trim()))
					|| (null == reqJson.getPayerCode() || "".equalsIgnoreCase(reqJson.getPayerCode().trim()))
					|| (null == reqJson.getPayerName() || "".equalsIgnoreCase(reqJson.getPayerName().trim()))
					|| (null == reqJson.getPayerSeqNum() || "".equalsIgnoreCase(reqJson.getPayerSeqNum().trim()))
					|| (null == reqJson.getPayerType() || "".equalsIgnoreCase(reqJson.getPayerType().trim()))
					|| (null == reqJson.getCredJsons())
					|| (null == reqJson.getMobileNo() || "".equalsIgnoreCase(reqJson.getMobileNo().trim()))
					|| (null == reqJson.getRegCardDigits() || "".equalsIgnoreCase(reqJson.getRegCardDigits().trim()))
					|| (null == reqJson.getRegExpDate() || "".equalsIgnoreCase(reqJson.getRegExpDate().trim()))
					|| (null == reqJson.getPayerDeviceGeoCode()
							|| "".equalsIgnoreCase(reqJson.getPayerDeviceGeoCode().trim()))
					|| (null == reqJson.getPayerDeviceLocation()
							|| "".equalsIgnoreCase(reqJson.getPayerDeviceLocation().trim()))
					|| (null == reqJson.getPayerDeviceIp() || "".equalsIgnoreCase(reqJson.getPayerDeviceIp().trim()))
					|| (null == reqJson.getPayerDeviceType() || "".equalsIgnoreCase(reqJson.getPayerDeviceType().trim()))
					|| (null == reqJson.getPayerDeviceId() || "".equalsIgnoreCase(reqJson.getPayerDeviceId().trim()))
					|| (null == reqJson.getPayerDeviceOsType()
							|| "".equalsIgnoreCase(reqJson.getPayerDeviceOsType().trim()))
					|| (null == reqJson.getPayerDeviceAppId()
							|| "".equalsIgnoreCase(reqJson.getPayerDeviceAppId().trim()))
					|| (null == reqJson.getPayerDeviceCapability()
							|| "".equalsIgnoreCase(reqJson.getPayerDeviceCapability().trim()))) {
				return false;
			} else {
				if ("ACCOUNT".equalsIgnoreCase(reqJson.getPayerAddrType())) {
					if (((null == reqJson.getPayerIfsc() || "".equalsIgnoreCase(reqJson.getPayerIfsc().trim()))
							|| 11 != reqJson.getPayerIfsc().length())
							|| (null == reqJson.getPayerAcType()
									|| "".equalsIgnoreCase(reqJson.getPayerAcType().trim()))
							|| (null == reqJson.getPayerAcNum()
									|| "".equalsIgnoreCase(reqJson.getPayerAcNum().trim()))) {
						return false;
					} else {
						return true;
					}
					
				} else if ("MOBILE".equalsIgnoreCase(reqJson.getPayerAddrType())) {
					if ((null == reqJson.getMmid() || "".equalsIgnoreCase(reqJson.getMmid().trim()))
							|| (null == reqJson.getMobileNo() || "".equalsIgnoreCase(reqJson.getMobileNo().trim()))) {
						return false;
					} else {
						return true;
					}
					
				} else if ("AADHAAR".equalsIgnoreCase(reqJson.getPayerAddrType())) {
					if ((null == reqJson.getPayerIin() || "".equalsIgnoreCase(reqJson.getPayerIin().trim()))
							|| (null == reqJson.getAddhaarNo()
									|| "".equalsIgnoreCase(reqJson.getAddhaarNo().trim()))) { return false; }
				} else {
					return true;
				}
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
			return false;
		}
		return false;
	}
	
	private static boolean setCreVal(ReqJson reqJson) {
		try {
			
			if ((null == reqJson.getTxnId() || "".equalsIgnoreCase(reqJson.getTxnId().trim()))
					|| (null == reqJson.getTxnNote() || "".equalsIgnoreCase(reqJson.getTxnNote().trim()))
					|| (null == reqJson.getTxnRefId() || "".equalsIgnoreCase(reqJson.getTxnRefId().trim()))
					|| (null == reqJson.getTxnRefUrl() || "".equalsIgnoreCase(reqJson.getTxnRefUrl().trim()))
					|| (null == reqJson.getTxnType() || "".equalsIgnoreCase(reqJson.getTxnType().trim()))
					|| (null == reqJson.getPayerAddrType() || "".equalsIgnoreCase(reqJson.getPayerAddrType().trim()))
					|| ((null == reqJson.getPayerIfsc() || "".equalsIgnoreCase(reqJson.getPayerIfsc().trim()))
							|| 11 != reqJson.getPayerIfsc().length())
					|| (null == reqJson.getPayerAcType() || "".equalsIgnoreCase(reqJson.getPayerAcType().trim()))
					|| (null == reqJson.getPayerAcNum() || "".equalsIgnoreCase(reqJson.getPayerAcNum().trim()))
					|| (null == reqJson.getPayerAddr() || "".equalsIgnoreCase(reqJson.getPayerAddr().trim()))
					|| (null == reqJson.getPayerCode() || "".equalsIgnoreCase(reqJson.getPayerCode().trim()))
					|| (null == reqJson.getPayerName() || "".equalsIgnoreCase(reqJson.getPayerName().trim()))
					|| (null == reqJson.getPayerSeqNum() || "".equalsIgnoreCase(reqJson.getPayerSeqNum().trim()))
					|| (null == reqJson.getPayerType() || "".equalsIgnoreCase(reqJson.getPayerType().trim()))
					|| (null == reqJson.getCredJsons())
					|| (null == reqJson.getMobileNo() || "".equalsIgnoreCase(reqJson.getMobileNo().trim()))
					|| (null == reqJson.getPayerDeviceGeoCode()
							|| "".equalsIgnoreCase(reqJson.getPayerDeviceGeoCode().trim()))
					|| (null == reqJson.getPayerDeviceLocation()
							|| "".equalsIgnoreCase(reqJson.getPayerDeviceLocation().trim()))
					|| (null == reqJson.getPayerDeviceIp() || "".equalsIgnoreCase(reqJson.getPayerDeviceIp().trim()))
					|| (null == reqJson.getPayerDeviceType()
							|| "".equalsIgnoreCase(reqJson.getPayerDeviceType().trim()))
					|| (null == reqJson.getPayerDeviceId() || "".equalsIgnoreCase(reqJson.getPayerDeviceId().trim()))
					|| (null == reqJson.getPayerDeviceOsType()
							|| "".equalsIgnoreCase(reqJson.getPayerDeviceOsType().trim()))
					|| (null == reqJson.getPayerDeviceAppId()
							|| "".equalsIgnoreCase(reqJson.getPayerDeviceAppId().trim()))
					|| (null == reqJson.getPayerDeviceCapability()
							|| "".equalsIgnoreCase(reqJson.getPayerDeviceCapability().trim()))) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
			return false;
		}
	}
	
	private static boolean valAddVal(ReqJson reqJson) {
		try {
			if ((null == reqJson.getTxnNote() || "".equalsIgnoreCase(reqJson.getTxnNote().trim()))
					|| (null == reqJson.getTxnRefId() || "".equalsIgnoreCase(reqJson.getTxnRefId().trim()))
					|| (null == reqJson.getTxnRefUrl() || "".equalsIgnoreCase(reqJson.getTxnRefUrl().trim()))
					|| (null == reqJson.getTxnType() || "".equalsIgnoreCase(reqJson.getTxnType().trim()))
					|| (null == reqJson.getPayerAddr() || "".equalsIgnoreCase(reqJson.getPayerAddr().trim()))
					// || (null == reqJson.getPayerName() ||
					// "".equalsIgnoreCase(reqJson.getPayerName().trim()))
					|| (null == reqJson.getPayerSeqNum() || "".equalsIgnoreCase(reqJson.getPayerSeqNum().trim()))
					|| (null == reqJson.getPayerCode() || "".equalsIgnoreCase(reqJson.getPayerCode().trim()))
					|| (null == reqJson.getPayerAddr() || "".equalsIgnoreCase(reqJson.getPayerAddr().trim()))
					|| (null == reqJson.getPayeeAddr() || "".equalsIgnoreCase(reqJson.getPayeeAddr().trim()))
					// || (null == reqJson.getPayeeName() ||
					// "".equalsIgnoreCase(reqJson.getPayeeName().trim()))
					|| (null == reqJson.getPayeeSeqNum() || "".equalsIgnoreCase(reqJson.getPayeeSeqNum().trim()))) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
			return false;
		}
	}
	
	private static boolean valCreMandate(ReqJson reqJson) {
		boolean flag=false;
		{
			try
			{

				if ((null == reqJson.getTxnId() || "".equalsIgnoreCase(reqJson.getTxnId().trim())) || (null == reqJson.getTxnNote() || "".equalsIgnoreCase(reqJson.getTxnNote().trim())) || (null == reqJson.getTxnRefId() || "".equalsIgnoreCase(reqJson.getTxnRefId().trim())) || (null == reqJson.getTxnRefUrl() || "".equalsIgnoreCase(reqJson.getTxnRefUrl().trim())) || (null == reqJson.getTxnType() || "".equalsIgnoreCase(reqJson.getTxnType().trim()))
						|| (null == reqJson.getPayerAddrType() || "".equalsIgnoreCase(reqJson.getPayerAddrType().trim())) || ((null == reqJson.getPayerIfsc() || "".equalsIgnoreCase(reqJson.getPayerIfsc().trim())) || 11 != reqJson.getPayerIfsc().length()) || (null == reqJson.getPayerAcType() || "".equalsIgnoreCase(reqJson.getPayerAcType().trim())) || (null == reqJson.getPayerAcNum() || "".equalsIgnoreCase(reqJson.getPayerAcNum().trim()))
						|| (null == reqJson.getPayerAddr() || "".equalsIgnoreCase(reqJson.getPayerAddr().trim())) || (null == reqJson.getPayerCode() || "".equalsIgnoreCase(reqJson.getPayerCode().trim())) || (null == reqJson.getPayerName() || "".equalsIgnoreCase(reqJson.getPayerName().trim())) || (null == reqJson.getPayerSeqNum() || "".equalsIgnoreCase(reqJson.getPayerSeqNum().trim())) || (null == reqJson.getPayerType() || "".equalsIgnoreCase(reqJson.getPayerType().trim()))
						|| (null == reqJson.getCredJsons()) || (null == reqJson.getMobileNo() || "".equalsIgnoreCase(reqJson.getMobileNo().trim())) || (null == reqJson.getPayerDeviceGeoCode() || "".equalsIgnoreCase(reqJson.getPayerDeviceGeoCode().trim())) || (null == reqJson.getPayerDeviceLocation() || "".equalsIgnoreCase(reqJson.getPayerDeviceLocation().trim())) || (null == reqJson.getPayerDeviceIp() || "".equalsIgnoreCase(reqJson.getPayerDeviceIp().trim()))
						|| (null == reqJson.getPayerDeviceType() || "".equalsIgnoreCase(reqJson.getPayerDeviceType().trim())) || (null == reqJson.getPayerDeviceId() || "".equalsIgnoreCase(reqJson.getPayerDeviceId().trim())) || (null == reqJson.getPayerDeviceOsType() || "".equalsIgnoreCase(reqJson.getPayerDeviceOsType().trim())) || (null == reqJson.getPayerDeviceAppId() || "".equalsIgnoreCase(reqJson.getPayerDeviceAppId().trim())) || (null == reqJson.getPayerDeviceCapability() || "".equalsIgnoreCase(reqJson.getPayerDeviceCapability().trim())))
				{
					flag= true;//bypass
				}
				else
				{
					flag= true;
				}
			}
			catch (Exception ex) {
				log.error(ex.getMessage(),ex);
				flag =false;
			}
		}
		log.info("Flag value for valCreMandate:{}"+flag);
		return flag;
	}
	
	
	public static boolean isValidChkTxn(ReqJson reqJson) {
		try {
			if (StringUtils.isNotBlank(reqJson.getOrgTxnId())) {
				if (mobupireqrespjsonidService == null) {
					mobupireqrespjsonidService = new MobupireqrespjsonidService();
				}
				if (StringUtils.isNotBlank(reqJson.getIdPk())) {
					// idpk of orgTxn
					Mobupireqrespjsonid mobupireqrespjsonid = mobupireqrespjsonidService
							.findById(Integer.parseInt(reqJson.getIdPk()));
					if (mobupireqrespjsonid != null) {
						reqJson.setOrgMsgId(mobupireqrespjsonid.getMsgid());
						reqJson.setTpId(null);
						return true;
					}
				} else if (StringUtils.isNotBlank(reqJson.getTpId())) {
					Mobupireqrespjsonid mobupireqrespjsonid = mobupireqrespjsonidService.findByTpId(reqJson.getTpId());
					if (mobupireqrespjsonid != null) {
						reqJson.setOrgMsgId(mobupireqrespjsonid.getMsgid());
						reqJson.setTpId(null);
						return true;
					}
				}
			}
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s);
		}
		return false;
	}


}
