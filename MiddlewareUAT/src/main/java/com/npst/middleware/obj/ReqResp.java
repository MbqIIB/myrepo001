package com.npst.middleware.obj;

import java.io.Serializable;
import java.util.List;

// import org.npci.upi.schema.PayTrans;
// import org.npci.upi.schema.RespListAccount;
//
// import in.mbyte.upipsp.pspobj.CredJson;
// import in.mbyte.upipsp.util.RespMob;

public class ReqResp implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6283854972664490725L;
	
	public static class Account implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -2878780645630781513L;
		
		public static class CredsAllowed implements Serializable {
			/**
			 * 
			 */
			private static final long	serialVersionUID	= -991683887107135379L;
			private String				dLength;
			private String				dType;
			private String				subType;
			private String				type;
			
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
		
		private String				accRefNumber;
		private String				accType;
		private String				aeba;
		private List<CredsAllowed>	credsAlloweds;
		private String				ifsc;
		private String				maskedAccnumber;
		private String				mbeba;
		private String				mmid;
		private String				name;
		private String				aadhaar;
		private String				mobNumber;
		private String 				accStatus;
		
		public String getAadhaar() {
			return aadhaar;
		}
		
		public void setAadhaar(String aadhaar) {
			this.aadhaar = aadhaar;
		}
		
		public String getMobNumber() {
			return mobNumber;
		}
		
		public void setMobNumber(String mobNumber) {
			this.mobNumber = mobNumber;
		}
		
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
		
		public String getAccStatus() {
			return accStatus;
		}

		public void setAccStatus(String accStatus) {
			this.accStatus = accStatus;
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
				builder.append(", ");
			}
			if (aadhaar != null) {
				builder.append("aadhaar=");
				builder.append(aadhaar);
				builder.append(", ");
			}
			if (accStatus != null) {
				builder.append("accStatus=");
				builder.append(accStatus);
				builder.append(", ");
			}
			if (mobNumber != null) {
				builder.append("mobNumber=");
				builder.append(mobNumber);
			}
			builder.append("]");
			return builder.toString();
		}
		
	}
	
	private String			headReqMsgId;
	private String			bkPrf;
	private String			rrn;
	private String			txnType;
	private String			txnId;
	private String			txnRefId;
	private String			txnRefUrl;
	///
	private String			tokenChallenge;
	private String			tokenType;
	private String			tokenStr;
	///
	private String			txnFlag;
	
	private String			txnNote;
	
	///
	private String			ruleExpireAfter;
	private String			ruleMinAmount;
	private List<Account>	accounts;
	
	///
	
	///
	private String			credAtmpin;
	private String			credMpin;
	
	private String			credNMpin;
	private String			credOtp;
	///
	private String			payerDeviceMobile;
	private String			payerDeviceType;
	private String			payerDeviceGeoCode;
	private String			payerDeviceLocation;
	private String			payerDeviceIp;
	private String			payerDeviceId;
	private String			payerDeviceOsType;
	private String			payerDeviceAppId;
	private String			payerDeviceCapability;
	///
	
	private String			payeeDeviceMobile;
	private String			payeeDeviceType;
	private String			payeeDeviceGeoCode;
	private String			payeeDeviceLocation;
	private String			payeeDeviceIp;
	private String			payeeDeviceId;
	private String			payeeDeviceOsType;
	private String			payeeDeviceAppId;
	private String			payeeDeviceCapability;
	///
	private String			linkType;
	private String			linkValue;
	///
	private String			payeeName;
	private String			payeeSeqNum;
	
	private String			payeeType;
	private String			payeeAddr;
	private String			payeeAmount;
	private String			payeeAcNum;
	private String			payeeAcType;
	private String			payeeAddrType;
	private String			payeeCode;
	private String			payeeIfsc;
	private String			payeeIin;
	private String			payeeMmid;
	private String			payeeMobileNo;
	private String			payeeUidNum;
	private String			payeeCardNum;
	///
	private String			payerName;
	private String			payerSeqNum;
	private String			payerType;
	private String			payerAddr;
	private String			payerAmount;
	private String			payerAcNum;
	private String			payerAcType;
	private String			payerAddrType;
	private String			payerCode;
	private String			payerIfsc;
	private String			payerIin;
	private String			payerMmid;
	private String			payerMobileNo;
	private String			payerUidNum;
	private String			payerCardNum;
	///
	private String			orgRespCode;
	///
	private String			regDetailsType;
	
	private String			regCardDigits;
	private String			regExpDate;
	private String			regMobile;
	///
	private String			respBal;
	private String			respCode;
	private String			respMsg;
	// private RespMob msgId;
	private String			upiErrorCode;
	// private List<RespListAccount.AccountList.Account> upiAccount;
	private String			listKeys;
	private String			accPvds;
	// private List<CredJson> credJsons;
	private String			refApprovalNum;
	// private List<PayTrans> respMsgPayTrans;
	private String			orgTxnId;
	private String			orgTxnType;
	private String			field11;
	private String			atmCardFiled11;
	private String			cbsErrorCode;
	private String			reversalInfo;
	private String          unblockStatus; 
// private String payeeDeviceMobileNumber;
	private String firInfo;
	private String isFIR;
	private String isUPI2;
private String initiationMode;
	
	private String txnPurpose;
	
	private String mccCode;
	
	private String cbsMandateNo;
	private String subOperation;
	private String iPOName;
	private String				validityStart;
	private String				validityEnd;
	private Mandate										mandate;
	
	private String isLien;
	
	private String previousAmount;
	
	private String updateAmount;
	
	
	
	
	public String getUpdateAmount() {
		return updateAmount;
	}

	public void setUpdateAmount(String updateAmount) {
		this.updateAmount = updateAmount;
	}

	public String getPreviousAmount() {
		return previousAmount;
	}

	public void setPreviousAmount(String previousAmount) {
		this.previousAmount = previousAmount;
	}

	public String getIsLien() {
		return isLien;
	}

	public void setIsLien(String isLien) {
		this.isLien = isLien;
	}

	public String getUnblockStatus() {
		return unblockStatus;
	}

	public void setUnblockStatus(String unblockStatus) {
		this.unblockStatus = unblockStatus;
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

	public String getIsUPI2() {
		return isUPI2;
	}

	public void setIsUPI2(String isUPI2) {
		this.isUPI2 = isUPI2;
	}

	public String getSubOperation() {
		return subOperation;
	}

	public void setSubOperation(String subOperation) {
		this.subOperation = subOperation;
	}

	public String getiPOName() {
		return iPOName;
	}

	public void setiPOName(String iPOName) {
		this.iPOName = iPOName;
	}

	

	public String getIsFIR() {
		return isFIR;
	}

	public void setIsFIR(String isFIR) {
		this.isFIR = isFIR;
	}

	public String getReversalInfo() {
		return reversalInfo;
	}
	
	public void setReversalInfo(String reversalInfo) {
		this.reversalInfo = reversalInfo;
	}
	
	public List<Account> getAccounts() {
		return accounts;
	}
	
	public String getInitiationMode() {
		return initiationMode;
	}

	public void setInitiationMode(String initiationMode) {
		this.initiationMode = initiationMode;
	}

	public String getMccCode() {
		return mccCode;
	}

	public void setMccCode(String mccCode) {
		this.mccCode = mccCode;
	}

	public String getTxnPurpose() {
		return txnPurpose;
	}

	public String getCbsMandateNo() {
		return cbsMandateNo;
	}

	public void setCbsMandateNo(String cbsMandateNo) {
		this.cbsMandateNo = cbsMandateNo;
	}

	public void setTxnPurpose(String txnPurpose) {
		this.txnPurpose = txnPurpose;
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
	
	public String getCbsErrorCode() {
		return cbsErrorCode;
	}
	
	public String getFirInfo() {
		return firInfo;
	}

	public void setFirInfo(String firInfo) {
		this.firInfo = firInfo;
	}

	public String getCredAtmpin() {
		return credAtmpin;
	}
	
	// public List<CredJson> getCredJsons() {
	// return credJsons;
	// }
	
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
	
	// public RespMob getMsgId() {
	// return msgId;
	// }
	
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
	
	public String getRegCardDigits() {
		return regCardDigits;
	}
	
	public String getRegDetailsType() {
		return regDetailsType;
	}
	
	public String getRegExpDate() {
		return regExpDate;
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
	
	// public List<PayTrans> getRespMsgPayTrans() {
	// return respMsgPayTrans;
	// }
	
	public String getRrn() {
		return rrn;
	}
	
	public String getRuleExpireAfter() {
		return ruleExpireAfter;
	}
	
	public String getRuleMinAmount() {
		return ruleMinAmount;
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
	
	// public List<RespListAccount.AccountList.Account> getUpiAccount() {
	// return upiAccount;
	// }
	
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
	
	public void setCbsErrorCode(String cbsErrorCode) {
		this.cbsErrorCode = cbsErrorCode;
	}
	
	public void setCredAtmpin(String credAtmpin) {
		this.credAtmpin = credAtmpin;
	}
	
	// public void setCredJsons(List<CredJson> credJsons) {
	// this.credJsons = credJsons;
	// }
	
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
	
	// public void setMsgId(RespMob msgId) {
	// this.msgId = msgId;
	// }
	
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
	
	public void setRegCardDigits(String regCardDigits) {
		this.regCardDigits = regCardDigits;
	}
	
	public void setRegDetailsType(String regDetailsType) {
		this.regDetailsType = regDetailsType;
	}
	
	public void setRegExpDate(String regExpDate) {
		this.regExpDate = regExpDate;
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
	
	// public void setRespMsgPayTrans(List<PayTrans> respMsgPayTrans) {
	// this.respMsgPayTrans = respMsgPayTrans;
	// }
	
	public void setRrn(String rrn) {
		this.rrn = rrn;
	}
	
	public Mandate getMandate() {
		return mandate;
	}

	public void setMandate(Mandate mandate) {
		this.mandate = mandate;
	}

	public void setRuleExpireAfter(String ruleExpireAfter) {
		this.ruleExpireAfter = ruleExpireAfter;
	}
	
	public void setRuleMinAmount(String ruleMinAmount) {
		this.ruleMinAmount = ruleMinAmount;
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
	
	// public void setUpiAccount(List<RespListAccount.AccountList.Account>
	// upiAccount) {
	// this.upiAccount = upiAccount;
	// }
	
	public void setUpiErrorCode(String upiErrorCode) {
		this.upiErrorCode = upiErrorCode;
	}
	
// public String getPayeeDeviceMobileNumber() {
// return payeeDeviceMobileNumber;
// }
//
// public void setPayeeDeviceMobileNumber(String payeeDeviceMobileNumber) {
// this.payeeDeviceMobileNumber = payeeDeviceMobileNumber;
// }
	
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
		if (upiErrorCode != null) {
			builder.append("upiErrorCode=");
			builder.append(upiErrorCode);
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
		if (refApprovalNum != null) {
			builder.append("refApprovalNum=");
			builder.append(refApprovalNum);
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
		if (reversalInfo != null) {
			builder.append("reversalInfo=");
			builder.append(reversalInfo);
			builder.append(", ");
		}
		if (subOperation != null) {
			builder.append("subOperation=");
			builder.append(subOperation);
			builder.append(", ");
		}
		builder.append("]");
		return builder.toString();
	}
	
	public String getPayerDeviceMobile() {
		return payerDeviceMobile;
	}
	
	public void setPayerDeviceMobile(String payerDeviceMobile) {
		this.payerDeviceMobile = payerDeviceMobile;
	}
	
	public String getPayerDeviceType() {
		return payerDeviceType;
	}
	
	public void setPayerDeviceType(String payerDeviceType) {
		this.payerDeviceType = payerDeviceType;
	}
	
	public String getPayerDeviceGeoCode() {
		return payerDeviceGeoCode;
	}
	
	public void setPayerDeviceGeoCode(String payerDeviceGeoCode) {
		this.payerDeviceGeoCode = payerDeviceGeoCode;
	}
	
	public String getPayerDeviceLocation() {
		return payerDeviceLocation;
	}
	
	public void setPayerDeviceLocation(String payerDeviceLocation) {
		this.payerDeviceLocation = payerDeviceLocation;
	}
	
	public String getPayerDeviceIp() {
		return payerDeviceIp;
	}
	
	public void setPayerDeviceIp(String payerDeviceIp) {
		this.payerDeviceIp = payerDeviceIp;
	}
	
	public String getPayerDeviceId() {
		return payerDeviceId;
	}
	
	public void setPayerDeviceId(String payerDeviceId) {
		this.payerDeviceId = payerDeviceId;
	}
	
	public String getPayerDeviceOsType() {
		return payerDeviceOsType;
	}
	
	public void setPayerDeviceOsType(String payerDeviceOsType) {
		this.payerDeviceOsType = payerDeviceOsType;
	}
	
	public String getPayerDeviceAppId() {
		return payerDeviceAppId;
	}
	
	public void setPayerDeviceAppId(String payerDeviceAppId) {
		this.payerDeviceAppId = payerDeviceAppId;
	}
	
	public String getPayerDeviceCapability() {
		return payerDeviceCapability;
	}
	
	public void setPayerDeviceCapability(String payerDeviceCapability) {
		this.payerDeviceCapability = payerDeviceCapability;
	}
	
	public String getPayeeDeviceMobile() {
		return payeeDeviceMobile;
	}
	
	public void setPayeeDeviceMobile(String payeeDeviceMobile) {
		this.payeeDeviceMobile = payeeDeviceMobile;
	}
	
	public String getPayeeDeviceType() {
		return payeeDeviceType;
	}
	
	public void setPayeeDeviceType(String payeeDeviceType) {
		this.payeeDeviceType = payeeDeviceType;
	}
	
	public String getPayeeDeviceGeoCode() {
		return payeeDeviceGeoCode;
	}
	
	public void setPayeeDeviceGeoCode(String payeeDeviceGeoCode) {
		this.payeeDeviceGeoCode = payeeDeviceGeoCode;
	}
	
	public String getPayeeDeviceLocation() {
		return payeeDeviceLocation;
	}
	
	public void setPayeeDeviceLocation(String payeeDeviceLocation) {
		this.payeeDeviceLocation = payeeDeviceLocation;
	}
	
	public String getPayeeDeviceIp() {
		return payeeDeviceIp;
	}
	
	public void setPayeeDeviceIp(String payeeDeviceIp) {
		this.payeeDeviceIp = payeeDeviceIp;
	}
	
	public String getPayeeDeviceId() {
		return payeeDeviceId;
	}
	
	public void setPayeeDeviceId(String payeeDeviceId) {
		this.payeeDeviceId = payeeDeviceId;
	}
	
	public String getPayeeDeviceOsType() {
		return payeeDeviceOsType;
	}
	
	public void setPayeeDeviceOsType(String payeeDeviceOsType) {
		this.payeeDeviceOsType = payeeDeviceOsType;
	}
	
	public String getPayeeDeviceAppId() {
		return payeeDeviceAppId;
	}
	
	public void setPayeeDeviceAppId(String payeeDeviceAppId) {
		this.payeeDeviceAppId = payeeDeviceAppId;
	}
	
	public String getPayeeDeviceCapability() {
		return payeeDeviceCapability;
	}
	
	public void setPayeeDeviceCapability(String payeeDeviceCapability) {
		this.payeeDeviceCapability = payeeDeviceCapability;
	}
	
public static class Mandate implements Serializable {
		
		private static final long	serialVersionUID	= -7923417561003818386L;
		private String				mandateName;
		private String				recurrencePattern;
		private String				recurrenceRuleType;
		private String				mandateRevokeable;
		private String				mandateShareToPayee;
		private String				mandateType;
		private String				mandateBlockFund;
		private String				validityStart;
		private String				validityEnd;
		private String				mandateUmn;
		private String				mandateAmountRule;
		private String				mandateAmountValue;
		
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
			StringBuilder builder = new StringBuilder();
			builder.append("Mandate [");
			if (mandateName != null) {
				builder.append("mandateName=");
				builder.append(mandateName);
				builder.append(", ");
			}
			if (recurrencePattern != null) {
				builder.append("recurrencePattern=");
				builder.append(recurrencePattern);
				builder.append(", ");
			}
			if (recurrenceRuleType != null) {
				builder.append("recurrenceRuleType=");
				builder.append(recurrenceRuleType);
				builder.append(", ");
			}
			if (mandateRevokeable != null) {
				builder.append("mandateRevokeable=");
				builder.append(mandateRevokeable);
				builder.append(", ");
			}
			if (mandateShareToPayee != null) {
				builder.append("mandateShareToPayee=");
				builder.append(mandateShareToPayee);
				builder.append(", ");
			}
			if (mandateType != null) {
				builder.append("mandateType=");
				builder.append(mandateType);
				builder.append(", ");
			}
			if (mandateBlockFund != null) {
				builder.append("mandateBlockFund=");
				builder.append(mandateBlockFund);
				builder.append(", ");
			}
			if (validityStart != null) {
				builder.append("validityStart=");
				builder.append(validityStart);
				builder.append(", ");
			}
			if (validityEnd != null) {
				builder.append("validityEnd=");
				builder.append(validityEnd);
			}
			builder.append("]");
			return builder.toString();
		}
		
	}
	
}
