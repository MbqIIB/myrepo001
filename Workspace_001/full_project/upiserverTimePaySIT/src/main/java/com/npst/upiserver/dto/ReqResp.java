package com.npst.upiserver.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.npst.upiserver.npcischema.*;
import com.npst.upiserver.util.Util;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqResp implements Serializable {
	public static class Account implements Serializable {
		public static class CredsAllowed implements Serializable {
			/**
			 *
			 */
			private static final long serialVersionUID = -2476993530046549687L;
			private String dLength;
			private String dType;
			private String subType;
			private String type;

			public String getdLength() {
				return dLength;
			}

			public String getdType() {
				return dType;
			}

			public String getSubType() {
				return subType;
			}

			public String getType() {
				return type;
			}

			public void setdLength(String dLength) {
				this.dLength = dLength;
			}

			public void setdType(String dType) {
				this.dType = dType;
			}

			public void setSubType(String subType) {
				this.subType = subType;
			}

			public void setType(String type) {
				this.type = type;
			}

			@Override
			public String toString() {
				return "CredsAllowed [dLength=" + dLength + ", dType=" + dType + ", subType=" + subType + ", type="
						+ type + "]";
			}
		}

		/**
		 *
		 */
		private static final long serialVersionUID = 4973314442300817095L;

		private String accRefNumber;
		private String accType;
		private String aeba;
		private List<CredsAllowed> credsAlloweds;
		private String ifsc;
		private String maskedAccnumber;
		private String mbeba;
		private String mmid;
		private String name;

		public String getAccRefNumber() {
			return accRefNumber;
		}

		public String getAccType() {
			return accType;
		}

		public String getAeba() {
			return aeba;
		}

		public List<CredsAllowed> getCredsAlloweds() {
			return credsAlloweds;
		}

		public String getIfsc() {
			return ifsc;
		}

		public String getMaskedAccnumber() {
			return maskedAccnumber;
		}

		public String getMbeba() {
			return mbeba;
		}

		public String getMmid() {
			return mmid;
		}

		public String getName() {
			return name;
		}

		public void setAccRefNumber(String accRefNumber) {
			this.accRefNumber = accRefNumber;
		}

		public void setAccType(String accType) {
			this.accType = accType;
		}

		public void setAeba(String aeba) {
			this.aeba = aeba;
		}

		public void setCredsAlloweds(List<CredsAllowed> credsAlloweds) {
			this.credsAlloweds = credsAlloweds;
		}

		public void setIfsc(String ifsc) {
			this.ifsc = ifsc;
		}

		public void setMaskedAccnumber(String maskedAccnumber) {
			this.maskedAccnumber = maskedAccnumber;
		}

		public void setMbeba(String mbeba) {
			this.mbeba = mbeba;
		}

		public void setMmid(String mmid) {
			this.mmid = mmid;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "Account [accRefNumber=" + accRefNumber + ", accType=" + accType + ", aeba=" + aeba
					+ ", credsAlloweds=" + credsAlloweds + ", ifsc=" + ifsc + ", maskedAccnumber=" + maskedAccnumber
					+ ", mbeba=" + mbeba + ", mmid=" + mmid + ", name=" + name + "]";
		}
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 125436029528238960L;

	private String headReqMsgId;
	private String bkPrf;
	private String rrn;
	private String txnType;
	private String onusType;
	private String serviceType;
	private boolean onUs;
	private String txnId;
	private String txnRefId;
	private String txnRefUrl;
	///
	private String tokenChallenge;
	private String tokenType;
	private String tokenStr;
	///
	private String txnFlag;

	private String txnNote;

	///
	private String ruleExpireAfter;
	private String ruleMinAmount;
	private List<Account> accounts;

	private String deviceMobile;
	private String deviceType;

private String 										umn;
	///
	private String credAtmpin;
	private String credMpin;

	private String credNMpin;
	private String credOtp;
	///
	private String payerDeviceMobile;
	private String payerDeviceType;
	private String payerDeviceGeoCode;
	private String payerDeviceLocation;
	private String payerDeviceIp;
	private String payerDeviceId;
	private String payerDeviceOsType;
	private String payerDeviceAppId;
	private String payerDeviceCapability;
	///

	private String payeeDeviceMobile;
	private String payeeDeviceType;
	private String payeeDeviceGeoCode;
	private String payeeDeviceLocation;
	private String payeeDeviceIp;
	private String payeeDeviceId;
	private String payeeDeviceOsType;
	private String payeeDeviceAppId;
	private String payeeDeviceCapability;
	///
	private String linkType;
	private String linkValue;
	///
	private String payeeName;
	private String payeeSeqNum;

	private String payeeType;
	private String payeeAddr;
	private String payeeAmount;
	private String payeeAcNum;
	private String payeeAcType;
	private String payeeAddrType;
	private String payeeCode;
	private String payeeIfsc;
	private String payeeIin;
	private String payeeMmid;
	private String payeeMobileNo;
	private String payeeUidNum;
	private String payeeCardNum;
	///
	private String payerName;
	private String payerSeqNum;
	private String payerType;
	private String payerAddr;
	private String payerAmount;
	private String payerAcNum;
	private String payerAcType;
	private String payerAddrType;
	private String payerCode;
	private String payerIfsc;
	private String payerIin;
	private String payerMmid;
	private String payerMobileNo;
	private String payerUidNum;
	private String payerCardNum;
	///
	private String orgRespCode;
	///
	private String regDetailsType;

	private String regCardDigits;
	private String regExpDate;
	private String regMobile;
	///
	private String respBal;
	private String respCode;
	private String respMsg;
	private String msgId;
	private String msg;
	private String upiErrorCode;
	private List<RespListAccount.AccountList.Account> upiAccount;
	private String listKeys;
	private String accPvds;
	private List<CredJson> credJsons;
	private String refApprovalNum;
	private List<PayTrans> respMsgPayTrans;
	private String orgTxnId;
	private String orgTxnType;
	private String field11;
	private String atmCardFiled11;
	private String cbsErrorCode;
	private String regId;
	private String blockTimeline;
	private String settAmount;
	private List<Ref> refCheckTxn;
	private String idPk;
	private String bbpsBillPayReqJson;

	private boolean merchTxn;
    private boolean internalM;
    private boolean bharatQR;
    private String mccCode;
    
    private String initiationMode;
	private String txnInitiatedBy;
	private String txnPurpose;
	private String payerDeviceTelecom;
	private String payeeDeviceTelecom;
	private String amtRuleType;
	private String         unblockStatus; 

	private Mandate mandate;
	
	/*
	 * Merchant specific
	 */
	
	private String merchantSubCode;
	public String getUmn() {
		return umn;
	}

	public void setUmn(String umn) {
		this.umn = umn;
	}

	private String merchantMid;
	private String merchantSid;
	private String merchantTid;
	private String merchantType;
	private String merchantGenre;
	private String merchantOnboardingType;
	private String merchantBrandName;
	private String merchantLegalName;
	private String merchantFranchiseName;
	private String merchantOwnershipType;
	/*
	 * end
	 */

	private String payerAadhaarConsent;
	private String payeeAadhaarConsent;
	private String subType;
	private String listKeyPspOrgId;
	private String respValAddr;
	private String payerAccNum;
	private String payeeDeviceMobileNumber;
	private String addrType;
	private String payeeAccNum;
	private String orgTxnid;
	private String isFIR;
	private String firInfo;
	private String isUPI2;
	private String	operation;
	private String	subOperation;
	private String	isoId;
	private String cbsMandateNo;
	private String bidReferenceNumber;
	private String iPOName;
	private String validityEndDate;
	private String validityStart;
	private String validityEnd;
	private String identity;
	private String previousAmount; // for udate sent amount into middleware
	
	
	public String getPreviousAmount() {
		return previousAmount;
	}

	public void setPreviousAmount(String previousAmount) {
		this.previousAmount = previousAmount;
	}

	public String getUnblockStatus() {
		return unblockStatus;
	}

	public void setUnblockStatus(String unblockStatus) {
		this.unblockStatus = unblockStatus;
	}

	public String getIsoId() {
		return isoId;
	}

	public String getValidityStart() {
		return validityStart;
	}

	public void setValidityStart(String validityStart) {
		this.validityStart = validityStart;
	}

	public String getValidityEnd() {
		return validityEnd;
	}

	public void setValidityEnd(String validityEnd) {
		this.validityEnd = validityEnd;
	}

	public String getiPOName() {
		return iPOName;
	}

	public void setiPOName(String iPOName) {
		this.iPOName = iPOName;
	}

	public String getValidityEndDate() {
		return validityEndDate;
	}

	public void setValidityEndDate(String validityEndDate) {
		this.validityEndDate = validityEndDate;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getBidReferenceNumber() {
		return bidReferenceNumber;
	}

	public void setBidReferenceNumber(String bidReferenceNumber) {
		this.bidReferenceNumber = bidReferenceNumber;
	}

	public void setIsoId(String isoId) {
		this.isoId = isoId;
	}

	public String getSubOperation() {
		return subOperation;
	}

	public void setSubOperation(String subOperation) {
		this.subOperation = subOperation;
	}

	public String getCbsMandateNo() {
		return cbsMandateNo;
	}

	public void setCbsMandateNo(String cbsMandateNo) {
		this.cbsMandateNo = cbsMandateNo;
	}

	
	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getIsUPI2() {
		return isUPI2;
	}

	public void setIsUPI2(String isUPI2) {
		this.isUPI2 = isUPI2;
	}

	public String getIsFIR() {
		return isFIR;
	}

	public void setIsFIR(String isFIR) {
		this.isFIR = isFIR;
	}

	public String getFirInfo() {
		return firInfo;
	}

	public void setFirInfo(String firInfo) {
		this.firInfo = firInfo;
	}

	public String getOrgTxnid() {
		return orgTxnid;
	}

	public void setOrgTxnid(String orgTxnid) {
		this.orgTxnid = orgTxnid;
	}

	public String getPayeeAccNum() {
		return payeeAccNum;
	}

	public void setPayeeAccNum(String payeeAccNum) {
		this.payeeAccNum = payeeAccNum;
	}

	public String getAddrType() {
		return addrType;
	}

	public void setAddrType(String addrType) {
		this.addrType = addrType;
	}

	public String getPayeeDeviceMobileNumber() {
		return payeeDeviceMobileNumber;
	}

	public void setPayeeDeviceMobileNumber(String payeeDeviceMobileNumber) {
		this.payeeDeviceMobileNumber = payeeDeviceMobileNumber;
	}

	public String getPayerAccNum() {
		return payerAccNum;
	}

	public void setPayerAccNum(String payerAccNum) {
		this.payerAccNum = payerAccNum;
	}

	public String getDeviceMobile() {
		return deviceMobile;
	}

	public void setDeviceMobile(String deviceMobile) {
		this.deviceMobile = deviceMobile;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getRespValAddr() {
		return respValAddr;
	}

	public void setRespValAddr(String respValAddr) {
		this.respValAddr = respValAddr;
	}

	public String getListKeyPspOrgId() {
		return listKeyPspOrgId;
	}

	public void setListKeyPspOrgId(String listKeyPspOrgId) {
		this.listKeyPspOrgId = listKeyPspOrgId;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getPayerAadhaarConsent() {
		return payerAadhaarConsent;
	}

	public void setPayerAadhaarConsent(String payerAadhaarConsent) {
		this.payerAadhaarConsent = payerAadhaarConsent;
	}

	public String getPayeeAadhaarConsent() {
		return payeeAadhaarConsent;
	}

	public void setPayeeAadhaarConsent(String payeeAadhaarConsent) {
		this.payeeAadhaarConsent = payeeAadhaarConsent;
	}

	public String getMccCode() {
		return mccCode;
	}

	public void setMccCode(String mccCode) {
		this.mccCode = mccCode;
	}

	public boolean isMerchTxn() {
		return merchTxn;
	}

	public void setMerchTxn(boolean merchTxn) {
		this.merchTxn = merchTxn;
	}

	public boolean isInternalM() {
		return internalM;
	}

	public void setInternalM(boolean internalM) {
		this.internalM = internalM;
	}


	public boolean isBharatQR() {
		return bharatQR;
	}

	public void setBharatQR(boolean bharatQR) {
		this.bharatQR = bharatQR;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public String getAccPvds() {
		return accPvds;
	}

	public String getAtmCardFiled11() {
		return atmCardFiled11;
	}

	public String getBbpsBillPayReqJson() {
		return bbpsBillPayReqJson;
	}

	public String getBkPrf() {
		return bkPrf;
	}

	public String getBlockTimeline() {
		return blockTimeline;
	}

	public String getCbsErrorCode() {
		return cbsErrorCode;
	}

	public String getCredAtmpin() {
		return credAtmpin;
	}

	public List<CredJson> getCredJsons() {
		return credJsons;
	}

	public String getCredMpin() {
		return credMpin;
	}

	public String getCredNMpin() {
		return credNMpin;
	}

	public String getCredOtp() {
		return credOtp;
	}

	public String getField11() {
		return field11;
	}

	public String getHeadReqMsgId() {
		return headReqMsgId;
	}

	public String getIdPk() {
		return idPk;
	}

	public String getLinkType() {
		return linkType;
	}

	public String getLinkValue() {
		if (null != linkValue && !linkValue.equalsIgnoreCase("")) {
			return Util.filterMobNumber(linkValue);
		}
		return linkValue;
	}

	public String getListKeys() {
		return listKeys;
	}

	public String getMsg() {
		return msg;
	}

	public String getMsgId() {
		return msgId;
	}

	public String getOnusType() {
		return onusType;
	}

	public String getOrgRespCode() {
		return orgRespCode;
	}

	public String getOrgTxnId() {
		return orgTxnId;
	}

	public String getOrgTxnType() {
		return orgTxnType;
	}

	public String getPayeeAcNum() {
		return payeeAcNum;
	}

	public String getPayeeAcType() {
		return payeeAcType;
	}

	public String getPayeeAddr() {
		return payeeAddr;
	}

	public String getPayeeAddrType() {
		return payeeAddrType;
	}

	public String getPayeeAmount() {
		return payeeAmount;
	}

	public String getPayeeCardNum() {
		return payeeCardNum;
	}

	public String getPayeeCode() {
		return payeeCode;
	}

	public String getPayeeDeviceAppId() {
		return payeeDeviceAppId;
	}

	public String getPayeeDeviceCapability() {
		return payeeDeviceCapability;
	}

	public String getPayeeDeviceGeoCode() {
		return payeeDeviceGeoCode;
	}

	public String getPayeeDeviceId() {
		return payeeDeviceId;
	}

	public String getPayeeDeviceIp() {
		return payeeDeviceIp;
	}

	public String getPayeeDeviceLocation() {
		return payeeDeviceLocation;
	}

	public String getPayeeDeviceMobile() {
		if (null != payeeDeviceMobile && !payeeDeviceMobile.equalsIgnoreCase("")) {
			return Util.filterMobNumber(payeeDeviceMobile);
		}
		return payeeDeviceMobile;
	}

	public String getPayeeDeviceOsType() {
		return payeeDeviceOsType;
	}

	public String getPayeeDeviceType() {
		return payeeDeviceType;
	}

	public String getPayeeIfsc() {
		return payeeIfsc;
	}

	public String getPayeeIin() {
		return payeeIin;
	}

	public String getPayeeMmid() {
		return payeeMmid;
	}

	public String getPayeeMobileNo() {
		if (null != payeeMobileNo && !payeeMobileNo.equalsIgnoreCase("")) {
			return Util.filterMobNumber(payeeMobileNo);
		}
		return payeeMobileNo;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public String getPayeeSeqNum() {
		return payeeSeqNum;
	}

	public String getPayeeType() {
		return payeeType;
	}

	public String getPayeeUidNum() {
		return payeeUidNum;
	}

	public String getPayerAcNum() {
		return payerAcNum;
	}

	public String getPayerAcType() {
		return payerAcType;
	}

	public String getPayerAddr() {
		return payerAddr;
	}

	public String getPayerAddrType() {
		return payerAddrType;
	}

	public String getPayerAmount() {
		return payerAmount;
	}

	public String getPayerCardNum() {
		return payerCardNum;
	}

	public String getPayerCode() {
		return payerCode;
	}

	public String getPayerDeviceAppId() {
		return payerDeviceAppId;
	}

	public String getPayerDeviceCapability() {
		return payerDeviceCapability;
	}

	public String getPayerDeviceGeoCode() {
		return payerDeviceGeoCode;
	}

	public String getPayerDeviceId() {
		return payerDeviceId;
	}

	public String getPayerDeviceIp() {
		return payerDeviceIp;
	}

	public String getPayerDeviceLocation() {
		return payerDeviceLocation;
	}

	public String getPayerDeviceMobile() {
		if (null != payerDeviceMobile && !payerDeviceMobile.equalsIgnoreCase("")) {
			return Util.filterMobNumber(payerDeviceMobile);
		}
		return payerDeviceMobile;
	}

	public String getPayerDeviceOsType() {
		return payerDeviceOsType;
	}

	public String getPayerDeviceType() {
		return payerDeviceType;
	}

	public String getPayerIfsc() {
		return payerIfsc;
	}

	public String getPayerIin() {
		return payerIin;
	}

	public String getPayerMmid() {
		return payerMmid;
	}

	public String getPayerMobileNo() {
		if (null != payerMobileNo && !payerMobileNo.equalsIgnoreCase("")) {
			return Util.filterMobNumber(payerMobileNo);
		}
		return payerMobileNo;
	}

	public String getPayerName() {
		return payerName;
	}

	public String getPayerSeqNum() {
		return payerSeqNum;
	}

	public String getPayerType() {
		return payerType;
	}

	public String getPayerUidNum() {
		return payerUidNum;
	}

	public String getRefApprovalNum() {
		return refApprovalNum;
	}

	public List<Ref> getRefCheckTxn() {
		return refCheckTxn;
	}

	public String getRegCardDigits() {
		return regCardDigits;
	}

	public String getRegDetailsType() {
		return regDetailsType;
	}

	public String getRegExpDate() {
		return regExpDate;
	}

	public String getRegId() {
		return regId;
	}

	public String getRegMobile() {
		if (null != regMobile && !regMobile.equalsIgnoreCase("")) {
			return Util.filterMobNumber(regMobile);
		}
		return regMobile;
	}

	public String getRespBal() {
		return respBal;
	}

	public String getRespCode() {
		return respCode;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public List<PayTrans> getRespMsgPayTrans() {
		return respMsgPayTrans;
	}

	public String getRrn() {
		return rrn;
	}

	public String getRuleExpireAfter() {
		return ruleExpireAfter;
	}

	public String getRuleMinAmount() {
		return ruleMinAmount;
	}

	public String getServiceType() {
		return serviceType;
	}

	public String getSettAmount() {
		return settAmount;
	}

	public String getTokenChallenge() {
		return tokenChallenge;
	}

	public String getTokenStr() {
		return tokenStr;
	}

	public String getTokenType() {
		return tokenType;
	}

	public String getTxnFlag() {
		return txnFlag;
	}

	public String getTxnId() {
		return txnId;
	}

	public String getTxnNote() {
		return txnNote;
	}

	public String getTxnRefId() {
		return txnRefId;
	}

	public String getTxnRefUrl() {
		return txnRefUrl;
	}

	public String getTxnType() {
		return txnType;
	}

	public List<RespListAccount.AccountList.Account> getUpiAccount() {
		return upiAccount;
	}

	public String getUpiErrorCode() {
		return upiErrorCode;
	}

	public boolean isOnUs() {
		return onUs;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public void setAccPvds(String accPvds) {
		this.accPvds = accPvds;
	}

	public void setAtmCardFiled11(String atmCardFiled11) {
		this.atmCardFiled11 = atmCardFiled11;
	}

	public void setBbpsBillPayReqJson(String bbpsBillPayReqJson) {
		this.bbpsBillPayReqJson = bbpsBillPayReqJson;
	}

	public void setBkPrf(String bkPrf) {
		this.bkPrf = bkPrf;
	}

	public void setBlockTimeline(String blockTimeline) {
		this.blockTimeline = blockTimeline;
	}

	public void setCbsErrorCode(String cbsErrorCode) {
		this.cbsErrorCode = cbsErrorCode;
	}

	public void setCredAtmpin(String credAtmpin) {
		this.credAtmpin = credAtmpin;
	}

	public void setCredJsons(List<CredJson> credJsons) {
		this.credJsons = credJsons;
	}

	public void setCredMpin(String credMpin) {
		this.credMpin = credMpin;
	}

	public void setCredNMpin(String credNMpin) {
		this.credNMpin = credNMpin;
	}

	public void setCredOtp(String credOtp) {
		this.credOtp = credOtp;
	}

	public void setField11(String field11) {
		this.field11 = field11;
	}

	public void setHeadReqMsgId(String headReqMsgId) {
		this.headReqMsgId = headReqMsgId;
	}

	public void setIdPk(String idPk) {
		this.idPk = idPk;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public void setLinkValue(String linkValue) {
		this.linkValue = linkValue;
	}

	public void setListKeys(String listKeys) {
		this.listKeys = listKeys;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public void setOnUs(boolean onUs) {
		this.onUs = onUs;
	}

	public void setOnusType(String onusType) {
		this.onusType = onusType;
	}

	public void setOrgRespCode(String orgRespCode) {
		this.orgRespCode = orgRespCode;
	}

	public void setOrgTxnId(String orgTxnId) {
		this.orgTxnId = orgTxnId;
	}

	public void setOrgTxnType(String orgTxnType) {
		this.orgTxnType = orgTxnType;
	}

	public void setPayeeAcNum(String payeeAcNum) {
		this.payeeAcNum = payeeAcNum;
	}

	public void setPayeeAcType(String payeeAcType) {
		this.payeeAcType = payeeAcType;
	}

	public void setPayeeAddr(String payeeAddr) {
		this.payeeAddr = payeeAddr;
	}

	public void setPayeeAddrType(String payeeAddrType) {
		this.payeeAddrType = payeeAddrType;
	}

	public void setPayeeAmount(String payeeAmount) {
		this.payeeAmount = payeeAmount;
	}

	public void setPayeeCardNum(String payeeCardNum) {
		this.payeeCardNum = payeeCardNum;
	}

	public void setPayeeCode(String payeeCode) {
		this.payeeCode = payeeCode;
	}

	public void setPayeeDeviceAppId(String payeeDeviceAppId) {
		this.payeeDeviceAppId = payeeDeviceAppId;
	}

	public void setPayeeDeviceCapability(String payeeDeviceCapability) {
		this.payeeDeviceCapability = payeeDeviceCapability;
	}

	public void setPayeeDeviceGeoCode(String payeeDeviceGeoCode) {
		this.payeeDeviceGeoCode = payeeDeviceGeoCode;
	}

	public void setPayeeDeviceId(String payeeDeviceId) {
		this.payeeDeviceId = payeeDeviceId;
	}

	public void setPayeeDeviceIp(String payeeDeviceIp) {
		this.payeeDeviceIp = payeeDeviceIp;
	}

	public void setPayeeDeviceLocation(String payeeDeviceLocation) {
		this.payeeDeviceLocation = payeeDeviceLocation;
	}

	public void setPayeeDeviceMobile(String payeeDeviceMobile) {
		this.payeeDeviceMobile = payeeDeviceMobile;
	}

	public void setPayeeDeviceOsType(String payeeDeviceOsType) {
		this.payeeDeviceOsType = payeeDeviceOsType;
	}

	public void setPayeeDeviceType(String payeeDeviceType) {
		this.payeeDeviceType = payeeDeviceType;
	}

	public void setPayeeIfsc(String payeeIfsc) {
		this.payeeIfsc = payeeIfsc;
	}

	public void setPayeeIin(String payeeIin) {
		this.payeeIin = payeeIin;
	}

	public void setPayeeMmid(String payeeMmid) {
		this.payeeMmid = payeeMmid;
	}

	public void setPayeeMobileNo(String payeeMobileNo) {
		this.payeeMobileNo = payeeMobileNo;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public void setPayeeSeqNum(String payeeSeqNum) {
		this.payeeSeqNum = payeeSeqNum;
	}

	public void setPayeeType(String payeeType) {
		this.payeeType = payeeType;
	}

	public void setPayeeUidNum(String payeeUidNum) {
		this.payeeUidNum = payeeUidNum;
	}

	public void setPayerAcNum(String payerAcNum) {
		this.payerAcNum = payerAcNum;
	}

	public void setPayerAcType(String payerAcType) {
		this.payerAcType = payerAcType;
	}

	public void setPayerAddr(String payerAddr) {
		this.payerAddr = payerAddr;
	}

	public void setPayerAddrType(String payerAddrType) {
		this.payerAddrType = payerAddrType;
	}

	public void setPayerAmount(String payerAmount) {
		this.payerAmount = payerAmount;
	}

	public void setPayerCardNum(String payerCardNum) {
		this.payerCardNum = payerCardNum;
	}

	public void setPayerCode(String payerCode) {
		this.payerCode = payerCode;
	}

	public void setPayerDeviceAppId(String payerDeviceAppId) {
		this.payerDeviceAppId = payerDeviceAppId;
	}

	public void setPayerDeviceCapability(String payerDeviceCapability) {
		this.payerDeviceCapability = payerDeviceCapability;
	}

	public void setPayerDeviceGeoCode(String payerDeviceGeoCode) {
		this.payerDeviceGeoCode = payerDeviceGeoCode;
	}

	public void setPayerDeviceId(String payerDeviceId) {
		this.payerDeviceId = payerDeviceId;
	}

	public void setPayerDeviceIp(String payerDeviceIp) {
		this.payerDeviceIp = payerDeviceIp;
	}

	public void setPayerDeviceLocation(String payerDeviceLocation) {
		this.payerDeviceLocation = payerDeviceLocation;
	}

	public void setPayerDeviceMobile(String payerDeviceMobile) {
		this.payerDeviceMobile = payerDeviceMobile;
	}

	public void setPayerDeviceOsType(String payerDeviceOsType) {
		this.payerDeviceOsType = payerDeviceOsType;
	}

	public void setPayerDeviceType(String payerDeviceType) {
		this.payerDeviceType = payerDeviceType;
	}

	public void setPayerIfsc(String payerIfsc) {
		this.payerIfsc = payerIfsc;
	}

	public void setPayerIin(String payerIin) {
		this.payerIin = payerIin;
	}

	public void setPayerMmid(String payerMmid) {
		this.payerMmid = payerMmid;
	}

	public void setPayerMobileNo(String payerMobileNo) {
		this.payerMobileNo = payerMobileNo;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public void setPayerSeqNum(String payerSeqNum) {
		this.payerSeqNum = payerSeqNum;
	}

	public void setPayerType(String payerType) {
		this.payerType = payerType;
	}

	public void setPayerUidNum(String payerUidNum) {
		this.payerUidNum = payerUidNum;
	}

	public void setRefApprovalNum(String refApprovalNum) {
		this.refApprovalNum = refApprovalNum;
	}

	public void setRefCheckTxn(List<Ref> refCheckTxn) {
		this.refCheckTxn = refCheckTxn;
	}

	public void setRegCardDigits(String regCardDigits) {
		this.regCardDigits = regCardDigits;
	}

	public void setRegDetailsType(String regDetailsType) {
		this.regDetailsType = regDetailsType;
	}

	public void setRegExpDate(String regExpDate) {
		this.regExpDate = regExpDate;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public void setRegMobile(String regMobile) {
		this.regMobile = regMobile;
	}

	public void setRespBal(String respBal) {
		this.respBal = respBal;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}

	public void setRespMsgPayTrans(List<PayTrans> respMsgPayTrans) {
		this.respMsgPayTrans = respMsgPayTrans;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
	}

	public void setRuleExpireAfter(String ruleExpireAfter) {
		this.ruleExpireAfter = ruleExpireAfter;
	}

	public void setRuleMinAmount(String ruleMinAmount) {
		this.ruleMinAmount = ruleMinAmount;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public void setSettAmount(String settAmount) {
		this.settAmount = settAmount;
	}

	public void setTokenChallenge(String tokenChallenge) {
		this.tokenChallenge = tokenChallenge;
	}

	public void setTokenStr(String tokenStr) {
		this.tokenStr = tokenStr;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public void setTxnFlag(String txnFlag) {
		this.txnFlag = txnFlag;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public void setTxnNote(String txnNote) {
		this.txnNote = txnNote;
	}

	public void setTxnRefId(String txnRefId) {
		this.txnRefId = txnRefId;
	}

	public void setTxnRefUrl(String txnRefUrl) {
		this.txnRefUrl = txnRefUrl;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public void setUpiAccount(List<RespListAccount.AccountList.Account> upiAccount) {
		this.upiAccount = upiAccount;
	}

	public void setUpiErrorCode(String upiErrorCode) {
		this.upiErrorCode = upiErrorCode;
	}

	public String getInitiationMode() {
		return initiationMode;
	}

	public void setInitiationMode(String initiationMode) {
		this.initiationMode = initiationMode;
	}

	public String getTxnInitiatedBy() {
		return txnInitiatedBy;
	}

	public void setTxnInitiatedBy(String txnInitiatedBy) {
		this.txnInitiatedBy = txnInitiatedBy;
	}

	public String getTxnPurpose() {
		return txnPurpose;
	}

	public void setTxnPurpose(String txnPurpose) {
		this.txnPurpose = txnPurpose;
	}

	public String getPayerDeviceTelecom() {
		return payerDeviceTelecom;
	}

	public void setPayerDeviceTelecom(String payerDeviceTelecom) {
		this.payerDeviceTelecom = payerDeviceTelecom;
	}

	public String getAmtRuleType() {
		return amtRuleType;
	}

	public void setAmtRuleType(String amtRuleType) {
		this.amtRuleType = amtRuleType;
	}

	public String getMerchantSubCode() {
		return merchantSubCode;
	}

	public String getMerchantMid() {
		return merchantMid;
	}

	public String getMerchantSid() {
		return merchantSid;
	}

	public String getMerchantTid() {
		return merchantTid;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public String getMerchantGenre() {
		return merchantGenre;
	}

	public String getMerchantOnboardingType() {
		return merchantOnboardingType;
	}

	public String getMerchantBrandName() {
		return merchantBrandName;
	}

	public String getMerchantLegalName() {
		return merchantLegalName;
	}

	public String getMerchantFranchiseName() {
		return merchantFranchiseName;
	}

	public String getMerchantOwnershipType() {
		return merchantOwnershipType;
	}

	public void setMerchantSubCode(String merchantSubCode) {
		this.merchantSubCode = merchantSubCode;
	}

	public void setMerchantMid(String merchantMid) {
		this.merchantMid = merchantMid;
	}

	public void setMerchantSid(String merchantSid) {
		this.merchantSid = merchantSid;
	}

	public void setMerchantTid(String merchantTid) {
		this.merchantTid = merchantTid;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	public void setMerchantGenre(String merchantGenre) {
		this.merchantGenre = merchantGenre;
	}

	public void setMerchantOnboardingType(String merchantOnboardingType) {
		this.merchantOnboardingType = merchantOnboardingType;
	}

	public void setMerchantBrandName(String merchantBrandName) {
		this.merchantBrandName = merchantBrandName;
	}

	public void setMerchantLegalName(String merchantLegalName) {
		this.merchantLegalName = merchantLegalName;
	}

	public void setMerchantFranchiseName(String merchantFranchiseName) {
		this.merchantFranchiseName = merchantFranchiseName;
	}

	public void setMerchantOwnershipType(String merchantOwnershipType) {
		this.merchantOwnershipType = merchantOwnershipType;
	}
	
	public Mandate getMandate() {
		return mandate;
	}

	public void setMandate(Mandate mandate) {
		this.mandate = mandate;
	}

	public String getPayeeDeviceTelecom() {
		return payeeDeviceTelecom;
	}

	public void setPayeeDeviceTelecom(String payeeDeviceTelecom) {
		this.payeeDeviceTelecom = payeeDeviceTelecom;
	}
	@Override
	public String toString() {
		return "ReqResp [headReqMsgId=" + headReqMsgId + ", bkPrf=" + bkPrf + ", rrn=" + rrn + ", txnType=" + txnType
				+ ", onusType=" + onusType + ", serviceType=" + serviceType + ", onUs=" + onUs + ", txnId=" + txnId
				+ ", txnRefId=" + txnRefId + ", txnRefUrl=" + txnRefUrl + ", tokenChallenge=" + tokenChallenge
				+ ", tokenType=" + tokenType + ", tokenStr=" + tokenStr + ", txnFlag=" + txnFlag + ", txnNote="
				+ txnNote + ", ruleExpireAfter=" + ruleExpireAfter + ", ruleMinAmount=" + ruleMinAmount + ", accounts="
				+ accounts + ", deviceMobile=" + deviceMobile + ", deviceType=" + deviceType + ", credAtmpin="
				+ credAtmpin + ", credMpin=" + credMpin + ", credNMpin=" + credNMpin + ", credOtp=" + credOtp
				+ ", payerDeviceMobile=" + payerDeviceMobile + ", payerDeviceType=" + payerDeviceType
				+ ", payerDeviceGeoCode=" + payerDeviceGeoCode + ", payerDeviceLocation=" + payerDeviceLocation
				+ ", payerDeviceIp=" + payerDeviceIp + ", payerDeviceId=" + payerDeviceId + ", payerDeviceOsType="
				+ payerDeviceOsType + ", payerDeviceAppId=" + payerDeviceAppId + ", payerDeviceCapability="
				+ payerDeviceCapability + ", payeeDeviceMobile=" + payeeDeviceMobile + ", payeeDeviceType="
				+ payeeDeviceType + ", payeeDeviceGeoCode=" + payeeDeviceGeoCode + ", payeeDeviceLocation="
				+ payeeDeviceLocation + ", payeeDeviceIp=" + payeeDeviceIp + ", payeeDeviceId=" + payeeDeviceId
				+ ", payeeDeviceOsType=" + payeeDeviceOsType + ", payeeDeviceAppId=" + payeeDeviceAppId
				+ ", payeeDeviceCapability=" + payeeDeviceCapability + ", linkType=" + linkType + ", linkValue="
				+ linkValue + ", payeeName=" + payeeName + ", payeeSeqNum=" + payeeSeqNum + ", payeeType=" + payeeType
				+ ", payeeAddr=" + payeeAddr + ", payeeAmount=" + payeeAmount + ", payeeAcNum=" + payeeAcNum
				+ ", payeeAcType=" + payeeAcType + ", payeeAddrType=" + payeeAddrType + ", payeeCode=" + payeeCode
				+ ", payeeIfsc=" + payeeIfsc + ", payeeIin=" + payeeIin + ", payeeMmid=" + payeeMmid
				+ ", payeeMobileNo=" + payeeMobileNo + ", payeeUidNum=" + payeeUidNum + ", payeeCardNum=" + payeeCardNum
				+ ", payerName=" + payerName + ", payerSeqNum=" + payerSeqNum + ", payerType=" + payerType
				+ ", payerAddr=" + payerAddr + ", payerAmount=" + payerAmount + ", payerAcNum=" + payerAcNum
				+ ", payerAcType=" + payerAcType + ", payerAddrType=" + payerAddrType + ", payerCode=" + payerCode
				+ ", payerIfsc=" + payerIfsc + ", payerIin=" + payerIin + ", payerMmid=" + payerMmid
				+ ", payerMobileNo=" + payerMobileNo + ", payerUidNum=" + payerUidNum + ", payerCardNum=" + payerCardNum
				+ ", orgRespCode=" + orgRespCode + ", regDetailsType=" + regDetailsType + ", regCardDigits="
				+ regCardDigits + ", regExpDate=" + regExpDate + ", regMobile=" + regMobile + ", respBal=" + respBal
				+ ", respCode=" + respCode + ", respMsg=" + respMsg + ", msgId=" + msgId + ", msg=" + msg
				+ ", upiErrorCode=" + upiErrorCode + ", upiAccount=" + upiAccount + ", listKeys=" + listKeys
				+ ", accPvds=" + accPvds + ", credJsons=" + credJsons + ", refApprovalNum=" + refApprovalNum
				+ ", respMsgPayTrans=" + respMsgPayTrans + ", orgTxnId=" + orgTxnId + ", orgTxnType=" + orgTxnType
				+ ", field11=" + field11 + ", atmCardFiled11=" + atmCardFiled11 + ", cbsErrorCode=" + cbsErrorCode
				+ ", regId=" + regId + ", blockTimeline=" + blockTimeline + ", settAmount=" + settAmount
				+ ", refCheckTxn=" + refCheckTxn + ", idPk=" + idPk + ", bbpsBillPayReqJson=" + bbpsBillPayReqJson
				+ ", merchTxn=" + merchTxn + ", internalM=" + internalM + ", bharatQR=" + bharatQR + ", mccCode="
				+ mccCode + ", initiationMode=" + initiationMode + ", txnInitiatedBy=" + txnInitiatedBy
				+ ", txnPurpose=" + txnPurpose + ", payerDeviceTelecom=" + payerDeviceTelecom + ", payeeDeviceTelecom="
				+ payeeDeviceTelecom + ", amtRuleType=" + amtRuleType + ", mandate=" + mandate + ", merchantSubCode="
				+ merchantSubCode + ", merchantMid=" + merchantMid + ", merchantSid=" + merchantSid + ", merchantTid="
				+ merchantTid + ", merchantType=" + merchantType + ", merchantGenre=" + merchantGenre
				+ ", merchantOnboardingType=" + merchantOnboardingType + ", merchantBrandName=" + merchantBrandName
				+ ", merchantLegalName=" + merchantLegalName + ", merchantFranchiseName=" + merchantFranchiseName
				+ ", merchantOwnershipType=" + merchantOwnershipType + ", payerAadhaarConsent=" + payerAadhaarConsent
				+ ", payeeAadhaarConsent=" + payeeAadhaarConsent + ", subType=" + subType + ", listKeyPspOrgId="
				+ listKeyPspOrgId + ", respValAddr=" + respValAddr + ", payerAccNum=" + payerAccNum
				+ ", payeeDeviceMobileNumber=" + payeeDeviceMobileNumber + ", addrType=" + addrType + ", payeeAccNum="
				+ payeeAccNum + ", orgTxnid=" + orgTxnid + "]";
	}

	public static class Mandate implements Serializable {
		
		private static final long serialVersionUID = -7923417561003818386L;
		private String mandateName;
		private String recurrencePattern;
		private String recurrenceRuleType;
		private String mandateRevokeable;
		private String mandateShareToPayee;
		private String mandateType;
		private String mandateBlockFund;
		private String validityStart;
		private String validityEnd;
		private String mandateUmn;
		private String mandateAmountRule;
		private String mandateAmountValue;
		
		public String getMandateAmountRule() {
			return mandateAmountRule;
		}
		public void setMandateAmountRule(String mandateAmountRule) {
			this.mandateAmountRule = mandateAmountRule;
		}
		public String getMandateAmountValue() {
			return mandateAmountValue;
		}
		public void setMandateAmountValue(String mandateAmountValue) {
			this.mandateAmountValue = mandateAmountValue;
		}
		public String getMandateUmn() {
			return mandateUmn;
		}
		public void setMandateUmn(String mandateUmn) {
			this.mandateUmn = mandateUmn;
		}
		public String getMandateName() {
			return mandateName;
		}
		public void setMandateName(String mandateName) {
			this.mandateName = mandateName;
		}
		public String getRecurrencePattern() {
			return recurrencePattern;
		}
		public void setRecurrencePattern(String recurrencePattern) {
			this.recurrencePattern = recurrencePattern;
		}
		public String getRecurrenceRuleType() {
			return recurrenceRuleType;
		}
		public void setRecurrenceRuleType(String recurrenceRuleType) {
			this.recurrenceRuleType = recurrenceRuleType;
		}
		public String getMandateRevokeable() {
			return mandateRevokeable;
		}
		public void setMandateRevokeable(String mandateRevokeable) {
			this.mandateRevokeable = mandateRevokeable;
		}
		public String getMandateShareToPayee() {
			return mandateShareToPayee;
		}
		public void setMandateShareToPayee(String mandateShareToPayee) {
			this.mandateShareToPayee = mandateShareToPayee;
		}
		public String getMandateType() {
			return mandateType;
		}
		public void setMandateType(String mandateType) {
			this.mandateType = mandateType;
		}
		public String getMandateBlockFund() {
			return mandateBlockFund;
		}
		public void setMandateBlockFund(String mandateBlockFund) {
			this.mandateBlockFund = mandateBlockFund;
		}
		public String getValidityStart() {
			return validityStart;
		}
		public void setValidityStart(String validityStart) {
			this.validityStart = validityStart;
		}
		public String getValidityEnd() {
			return validityEnd;
		}
		public void setValidityEnd(String validityEnd) {
			this.validityEnd = validityEnd;
		}
		@Override
		public String toString() {
			return "Mandate [mandateName=" + mandateName + ", recurrencePattern=" + recurrencePattern
					+ ", recurrenceRuleType=" + recurrenceRuleType + ", mandateRevokeable=" + mandateRevokeable
					+ ", mandateShareToPayee=" + mandateShareToPayee + ", mandateType=" + mandateType
					+ ", mandateBlockFund=" + mandateBlockFund + ", validityStart=" + validityStart + ", validityEnd="
					+ validityEnd + ", mandateUmn=" + mandateUmn + ", mandateAmountRule=" + mandateAmountRule
					+ ", mandateAmountValue=" + mandateAmountValue + "]";
		}
	}
}
