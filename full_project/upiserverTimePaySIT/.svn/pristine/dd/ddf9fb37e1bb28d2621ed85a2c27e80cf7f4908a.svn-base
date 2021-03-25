package com.npst.upiserver.dto;

import java.io.Serializable;
import java.util.List;

import com.npst.upiserver.npcischema.PayTrans;
import com.npst.upiserver.npcischema.RefCheckTxn;
import com.npst.upiserver.npcischema.RespListAccount;

public class RespJson implements Serializable {
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
				StringBuilder builder = new StringBuilder();
				builder.append("CredsAllowed [");
				if (dLength != null) {
					builder.append("dLength=");
					builder.append(dLength);
					builder.append(", ");
				}
				if (dType != null) {
					builder.append("dType=");
					builder.append(dType);
					builder.append(", ");
				}
				if (subType != null) {
					builder.append("subType=");
					builder.append(subType);
					builder.append(", ");
				}
				if (type != null) {
					builder.append("type=");
					builder.append(type);
				}
				builder.append("]");
				return builder.toString();
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
			StringBuilder builder = new StringBuilder();
			builder.append("Account [");
			if (accRefNumber != null) {
				builder.append("accRefNumber=");
				builder.append(accRefNumber);
				builder.append(", ");
			}
			if (accType != null) {
				builder.append("accType=");
				builder.append(accType);
				builder.append(", ");
			}
			if (aeba != null) {
				builder.append("aeba=");
				builder.append(aeba);
				builder.append(", ");
			}
			if (credsAlloweds != null) {
				builder.append("credsAlloweds=");
				builder.append(credsAlloweds);
				builder.append(", ");
			}
			if (ifsc != null) {
				builder.append("ifsc=");
				builder.append(ifsc);
				builder.append(", ");
			}
			if (maskedAccnumber != null) {
				builder.append("maskedAccnumber=");
				builder.append(maskedAccnumber);
				builder.append(", ");
			}
			if (mbeba != null) {
				builder.append("mbeba=");
				builder.append(mbeba);
				builder.append(", ");
			}
			if (mmid != null) {
				builder.append("mmid=");
				builder.append(mmid);
				builder.append(", ");
			}
			if (name != null) {
				builder.append("name=");
				builder.append(name);
			}
			builder.append("]");
			return builder.toString();
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

	private String approvalRefNo;
	private String txnRef;
	private String status;
	private String responseCode;
	
	///

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getApprovalRefNo() {
		return approvalRefNo;
	}

	public void setApprovalRefNo(String approvalRefNo) {
		this.approvalRefNo = approvalRefNo;
	}

	public String getTxnRef() {
		return txnRef;
	}

	public void setTxnRef(String txnRef) {
		this.txnRef = txnRef;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

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
	private List<RefCheckTxn> refCheckTxn;
	private String idPk;

	public List<Account> getAccounts() {
		return accounts;
	}

	public String getAccPvds() {
		return accPvds;
	}

	public String getAtmCardFiled11() {
		return atmCardFiled11;
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

	public String getLinkType() {
		return linkType;
	}

	public String getLinkValue() {
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

	public List<RefCheckTxn> getRefCheckTxn() {
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

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public void setAccPvds(String accPvds) {
		this.accPvds = accPvds;
	}

	public void setAtmCardFiled11(String atmCardFiled11) {
		this.atmCardFiled11 = atmCardFiled11;
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

	public void setRefCheckTxn(List<RefCheckTxn> refCheckTxn) {
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReqResp [");
		if (headReqMsgId != null) {
			builder.append("headReqMsgId=");
			builder.append(headReqMsgId);
			builder.append(", ");
		}
		if (bkPrf != null) {
			builder.append("bkPrf=");
			builder.append(bkPrf);
			builder.append(", ");
		}
		if (rrn != null) {
			builder.append("rrn=");
			builder.append(rrn);
			builder.append(", ");
		}
		if (txnType != null) {
			builder.append("txnType=");
			builder.append(txnType);
			builder.append(", ");
		}
		if (txnId != null) {
			builder.append("txnId=");
			builder.append(txnId);
			builder.append(", ");
		}
		if (txnRefId != null) {
			builder.append("txnRefId=");
			builder.append(txnRefId);
			builder.append(", ");
		}
		if (txnRefUrl != null) {
			builder.append("txnRefUrl=");
			builder.append(txnRefUrl);
			builder.append(", ");
		}
		if (tokenChallenge != null) {
			builder.append("tokenChallenge=");
			builder.append(tokenChallenge);
			builder.append(", ");
		}
		if (tokenType != null) {
			builder.append("tokenType=");
			builder.append(tokenType);
			builder.append(", ");
		}
		if (tokenStr != null) {
			builder.append("tokenStr=");
			builder.append(tokenStr);
			builder.append(", ");
		}
		if (txnFlag != null) {
			builder.append("txnFlag=");
			builder.append(txnFlag);
			builder.append(", ");
		}
		if (txnNote != null) {
			builder.append("txnNote=");
			builder.append(txnNote);
			builder.append(", ");
		}
		if (ruleExpireAfter != null) {
			builder.append("ruleExpireAfter=");
			builder.append(ruleExpireAfter);
			builder.append(", ");
		}
		if (ruleMinAmount != null) {
			builder.append("ruleMinAmount=");
			builder.append(ruleMinAmount);
			builder.append(", ");
		}
		if (accounts != null) {
			builder.append("accounts=");
			builder.append(accounts);
			builder.append(", ");
		}
		if (credAtmpin != null) {
			builder.append("credAtmpin=");
			builder.append(credAtmpin);
			builder.append(", ");
		}
		if (credMpin != null) {
			builder.append("credMpin=");
			builder.append(credMpin);
			builder.append(", ");
		}
		if (credNMpin != null) {
			builder.append("credNMpin=");
			builder.append(credNMpin);
			builder.append(", ");
		}
		if (credOtp != null) {
			builder.append("credOtp=");
			builder.append(credOtp);
			builder.append(", ");
		}
		if (payerDeviceMobile != null) {
			builder.append("payerDeviceMobile=");
			builder.append(payerDeviceMobile);
			builder.append(", ");
		}
		if (payerDeviceType != null) {
			builder.append("payerDeviceType=");
			builder.append(payerDeviceType);
			builder.append(", ");
		}
		if (payerDeviceGeoCode != null) {
			builder.append("payerDeviceGeoCode=");
			builder.append(payerDeviceGeoCode);
			builder.append(", ");
		}
		if (payerDeviceLocation != null) {
			builder.append("payerDeviceLocation=");
			builder.append(payerDeviceLocation);
			builder.append(", ");
		}
		if (payerDeviceIp != null) {
			builder.append("payerDeviceIp=");
			builder.append(payerDeviceIp);
			builder.append(", ");
		}
		if (payerDeviceId != null) {
			builder.append("payerDeviceId=");
			builder.append(payerDeviceId);
			builder.append(", ");
		}
		if (payerDeviceOsType != null) {
			builder.append("payerDeviceOsType=");
			builder.append(payerDeviceOsType);
			builder.append(", ");
		}
		if (payerDeviceAppId != null) {
			builder.append("payerDeviceAppId=");
			builder.append(payerDeviceAppId);
			builder.append(", ");
		}
		if (payerDeviceCapability != null) {
			builder.append("payerDeviceCapability=");
			builder.append(payerDeviceCapability);
			builder.append(", ");
		}
		if (payeeDeviceMobile != null) {
			builder.append("payeeDeviceMobile=");
			builder.append(payeeDeviceMobile);
			builder.append(", ");
		}
		if (payeeDeviceType != null) {
			builder.append("payeeDeviceType=");
			builder.append(payeeDeviceType);
			builder.append(", ");
		}
		if (payeeDeviceGeoCode != null) {
			builder.append("payeeDeviceGeoCode=");
			builder.append(payeeDeviceGeoCode);
			builder.append(", ");
		}
		if (payeeDeviceLocation != null) {
			builder.append("payeeDeviceLocation=");
			builder.append(payeeDeviceLocation);
			builder.append(", ");
		}
		if (payeeDeviceIp != null) {
			builder.append("payeeDeviceIp=");
			builder.append(payeeDeviceIp);
			builder.append(", ");
		}
		if (payeeDeviceId != null) {
			builder.append("payeeDeviceId=");
			builder.append(payeeDeviceId);
			builder.append(", ");
		}
		if (payeeDeviceOsType != null) {
			builder.append("payeeDeviceOsType=");
			builder.append(payeeDeviceOsType);
			builder.append(", ");
		}
		if (payeeDeviceAppId != null) {
			builder.append("payeeDeviceAppId=");
			builder.append(payeeDeviceAppId);
			builder.append(", ");
		}
		if (payeeDeviceCapability != null) {
			builder.append("payeeDeviceCapability=");
			builder.append(payeeDeviceCapability);
			builder.append(", ");
		}
		if (linkType != null) {
			builder.append("linkType=");
			builder.append(linkType);
			builder.append(", ");
		}
		if (linkValue != null) {
			builder.append("linkValue=");
			builder.append(linkValue);
			builder.append(", ");
		}
		if (payeeName != null) {
			builder.append("payeeName=");
			builder.append(payeeName);
			builder.append(", ");
		}
		if (payeeSeqNum != null) {
			builder.append("payeeSeqNum=");
			builder.append(payeeSeqNum);
			builder.append(", ");
		}
		if (payeeType != null) {
			builder.append("payeeType=");
			builder.append(payeeType);
			builder.append(", ");
		}
		if (payeeAddr != null) {
			builder.append("payeeAddr=");
			builder.append(payeeAddr);
			builder.append(", ");
		}
		if (payeeAmount != null) {
			builder.append("payeeAmount=");
			builder.append(payeeAmount);
			builder.append(", ");
		}
		if (payeeAcNum != null) {
			builder.append("payeeAcNum=");
			builder.append(payeeAcNum);
			builder.append(", ");
		}
		if (payeeAcType != null) {
			builder.append("payeeAcType=");
			builder.append(payeeAcType);
			builder.append(", ");
		}
		if (payeeAddrType != null) {
			builder.append("payeeAddrType=");
			builder.append(payeeAddrType);
			builder.append(", ");
		}
		if (payeeCode != null) {
			builder.append("payeeCode=");
			builder.append(payeeCode);
			builder.append(", ");
		}
		if (payeeIfsc != null) {
			builder.append("payeeIfsc=");
			builder.append(payeeIfsc);
			builder.append(", ");
		}
		if (payeeIin != null) {
			builder.append("payeeIin=");
			builder.append(payeeIin);
			builder.append(", ");
		}
		if (payeeMmid != null) {
			builder.append("payeeMmid=");
			builder.append(payeeMmid);
			builder.append(", ");
		}
		if (payeeMobileNo != null) {
			builder.append("payeeMobileNo=");
			builder.append(payeeMobileNo);
			builder.append(", ");
		}
		if (payeeUidNum != null) {
			builder.append("payeeUidNum=");
			builder.append(payeeUidNum);
			builder.append(", ");
		}
		if (payeeCardNum != null) {
			builder.append("payeeCardNum=");
			builder.append(payeeCardNum);
			builder.append(", ");
		}
		if (payerName != null) {
			builder.append("payerName=");
			builder.append(payerName);
			builder.append(", ");
		}
		if (payerSeqNum != null) {
			builder.append("payerSeqNum=");
			builder.append(payerSeqNum);
			builder.append(", ");
		}
		if (payerType != null) {
			builder.append("payerType=");
			builder.append(payerType);
			builder.append(", ");
		}
		if (payerAddr != null) {
			builder.append("payerAddr=");
			builder.append(payerAddr);
			builder.append(", ");
		}
		if (payerAmount != null) {
			builder.append("payerAmount=");
			builder.append(payerAmount);
			builder.append(", ");
		}
		if (payerAcNum != null) {
			builder.append("payerAcNum=");
			builder.append(payerAcNum);
			builder.append(", ");
		}
		if (payerAcType != null) {
			builder.append("payerAcType=");
			builder.append(payerAcType);
			builder.append(", ");
		}
		if (payerAddrType != null) {
			builder.append("payerAddrType=");
			builder.append(payerAddrType);
			builder.append(", ");
		}
		if (payerCode != null) {
			builder.append("payerCode=");
			builder.append(payerCode);
			builder.append(", ");
		}
		if (payerIfsc != null) {
			builder.append("payerIfsc=");
			builder.append(payerIfsc);
			builder.append(", ");
		}
		if (payerIin != null) {
			builder.append("payerIin=");
			builder.append(payerIin);
			builder.append(", ");
		}
		if (payerMmid != null) {
			builder.append("payerMmid=");
			builder.append(payerMmid);
			builder.append(", ");
		}
		if (payerMobileNo != null) {
			builder.append("payerMobileNo=");
			builder.append(payerMobileNo);
			builder.append(", ");
		}
		if (payerUidNum != null) {
			builder.append("payerUidNum=");
			builder.append(payerUidNum);
			builder.append(", ");
		}
		if (payerCardNum != null) {
			builder.append("payerCardNum=");
			builder.append(payerCardNum);
			builder.append(", ");
		}
		if (orgRespCode != null) {
			builder.append("orgRespCode=");
			builder.append(orgRespCode);
			builder.append(", ");
		}
		if (regDetailsType != null) {
			builder.append("regDetailsType=");
			builder.append(regDetailsType);
			builder.append(", ");
		}
		if (regCardDigits != null) {
			builder.append("regCardDigits=");
			builder.append(regCardDigits);
			builder.append(", ");
		}
		if (regExpDate != null) {
			builder.append("regExpDate=");
			builder.append(regExpDate);
			builder.append(", ");
		}
		if (regMobile != null) {
			builder.append("regMobile=");
			builder.append(regMobile);
			builder.append(", ");
		}
		if (respBal != null) {
			builder.append("respBal=");
			builder.append(respBal);
			builder.append(", ");
		}
		if (respCode != null) {
			builder.append("respCode=");
			builder.append(respCode);
			builder.append(", ");
		}
		if (respMsg != null) {
			builder.append("respMsg=");
			builder.append(respMsg);
			builder.append(", ");
		}
		if (msgId != null) {
			builder.append("msgId=");
			builder.append(msgId);
			builder.append(", ");
		}
		if (msg != null) {
			builder.append("msg=");
			builder.append(msg);
			builder.append(", ");
		}
		if (upiErrorCode != null) {
			builder.append("upiErrorCode=");
			builder.append(upiErrorCode);
			builder.append(", ");
		}
		if (upiAccount != null) {
			builder.append("upiAccount=");
			builder.append(upiAccount);
			builder.append(", ");
		}
		if (listKeys != null) {
			builder.append("listKeys=");
			builder.append(listKeys);
			builder.append(", ");
		}
		if (accPvds != null) {
			builder.append("accPvds=");
			builder.append(accPvds);
			builder.append(", ");
		}
		if (credJsons != null) {
			builder.append("credJsons=");
			builder.append(credJsons);
			builder.append(", ");
		}
		if (refApprovalNum != null) {
			builder.append("refApprovalNum=");
			builder.append(refApprovalNum);
			builder.append(", ");
		}
		if (respMsgPayTrans != null) {
			builder.append("respMsgPayTrans=");
			builder.append(respMsgPayTrans);
			builder.append(", ");
		}
		if (orgTxnId != null) {
			builder.append("orgTxnId=");
			builder.append(orgTxnId);
			builder.append(", ");
		}
		if (orgTxnType != null) {
			builder.append("orgTxnType=");
			builder.append(orgTxnType);
			builder.append(", ");
		}
		if (field11 != null) {
			builder.append("field11=");
			builder.append(field11);
			builder.append(", ");
		}
		if (atmCardFiled11 != null) {
			builder.append("atmCardFiled11=");
			builder.append(atmCardFiled11);
			builder.append(", ");
		}
		if (cbsErrorCode != null) {
			builder.append("cbsErrorCode=");
			builder.append(cbsErrorCode);
			builder.append(", ");
		}
		if (regId != null) {
			builder.append("regId=");
			builder.append(regId);
		}
		builder.append("]");
		return builder.toString();
	}

	public String getIdPk() {
		return idPk;
	}

	public void setIdPk(String idPk) {
		this.idPk = idPk;
	}

}
