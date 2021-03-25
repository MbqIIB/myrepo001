package com.npst.mobileservice.object;

import java.io.Serializable;
import java.util.List;

import org.npci.upi.schema.PayTrans;
import org.npci.upi.schema.RespListAccount;

public class ReqJson implements Serializable {
	private String	serviceType;
	private String	payerDeviceMobile;
	private String	payerDeviceType;
	private String	payerDeviceGeoCode;
	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	
	public String getMandateType() {
		return mandateType;
	}

	public void setMandateType(String mandateType) {
		this.mandateType = mandateType;
	}
	public String getMbNumber() {
		return mbNumber;
	}

	public void setMbNumber(String mbNumber) {
		this.mbNumber = mbNumber;
	}

	private String	payerDeviceLocation;
	private String	payerDeviceIp;
	private String	payerDeviceId;
	private String	payerDeviceOsType;
	private String	payerDeviceAppId;
	private String	payerDeviceCapability;
	private String	otp;
	private String mandateType;
	private String	mode;
	private String	beneid;
	private String	payeeaddr;
	private String	payeeaccno;
	private String	payeename;
	private String	payeeseqno;
	private String	payeetype;
	private String	payeeDeviceMobile;
	private String	payeeDeviceType;
	private String	payeeDeviceGeoCode;
	private String	payeeDeviceLocation;
	private String	payeeDeviceIp;
	private String	payeeDeviceId;
	private String	payeeDeviceOsType;
	private String	payeeDeviceAppId;
	private String	payeeDeviceCapability;
	private String	payeecode;
	
	private String	payeeifsc;
	
	private String	payeeadharno;
	
	private String	payeeiin;
	
	private String	payeemobilen;
	
	private String	payeemmid;
	
	private String	imei;
	
	private String	mobileNo;
	
	private String	mbNumber;
	
	private String	virtualId;
	
	private String	accRefNumber;
	
	private String	accType;
	
	private String	aeba;
	
	private String	bankName;
	
	private String	credsAllowed;
	
	private String	mbeba;
	
	private String	maskedAccnumber;
	
	private String	name;
	private String	ifsc;
	private String	mmid;
	private String	lovType;
	private String	loginPin;
	
	private String	newLoginPin;
	
	private String	fName;
	
	private String	gender;
	
	private String	secQue;
	
	private String	secAns;
	
	private String	email;
	
	private String	addhaarNo;
	private String	dob;
	private String	appOs;
	private String	appVer;
	private String	lName;
	private String	gcmToken;
	private String	mName;
	private String	flag;
	private String	idPk;
	private String	upiApiType;
	private String	changePinFlag;
	private String	amount;
	private String	custRef;
	private String	status;
	private String	type;
	private String	txnDate;
	private String	txnNo;
	private String	complaintDesc;
	private String	historyLimit;
	private String	eDate;
	private String	sDate;
	private String	historyFrom;
	private String	historyTo;
	private String  biometricData;
	private String reqId;
	
	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getBiometricData() {
		return biometricData;
	}

	public void setBiometricData(String biometricData) {
		this.biometricData = biometricData;
	}

	/**
	 * Since 05-02-18
	 */
	private String	limitamount;
	
	private String	txnStatus;
	private String	frmAmount;
	private String	toAmount;
	private String	isDefaultAcc;
	
	// new entries for feedback
	private String	feedbackCategory;
	private String	feedbackSeverity;
	private String	Remarks;
	private String	feedbackText;
	private String	screenPath;
	private String	image;
	private String	appVersion;
	private String	handsetName;
	private String	osName;
	private String	osVersion;
	private String	bbpsBillPayReqJson;
	private String	accPvdFormat;
	private String	accPvdIfsc;
	private String	salt;
	private String	passCodeSalt;
	private String  payerDeviceTelecom;
	private String  payeeDeviceTelecom;	
	private String payerAadhaarConsent;
	private String payeeAadhaarConsent;
	private String listKeyPspOrgId;
    private String initiationMode;
	private String txnPurpose;
	private String 	orgId;
	private String  content ;
	private String  sign;
	private String appInstall;
	private String tid;
	private String societyNo;
	
	public String getSocietyNo() {
		return societyNo;
	}

	public void setSocietyNo(String societyNo) {
		this.societyNo = societyNo;
	}

	public String getContent() {
		return content;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getPassCodeSalt() {
		return passCodeSalt;
	}

	public void setPassCodeSalt(String passCodeSalt) {
		this.passCodeSalt = passCodeSalt;
	}

	public String getPayerDeviceTelecom() {
		return payerDeviceTelecom;
	}

	public void setPayerDeviceTelecom(String payerDeviceTelecom) {
		this.payerDeviceTelecom = payerDeviceTelecom;
	}

	public String getPayeeDeviceTelecom() {
		return payeeDeviceTelecom;
	}

	public void setPayeeDeviceTelecom(String payeeDeviceTelecom) {
		this.payeeDeviceTelecom = payeeDeviceTelecom;
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

	public String getListKeyPspOrgId() {
		return listKeyPspOrgId;
	}

	public void setListKeyPspOrgId(String listKeyPspOrgId) {
		this.listKeyPspOrgId = listKeyPspOrgId;
	}

	public String getInitiationMode() {
		return initiationMode;
	}

	public void setInitiationMode(String initiationMode) {
		this.initiationMode = initiationMode;
	}

	public String getTxnPurpose() {
		return txnPurpose;
	}

	public void setTxnPurpose(String txnPurpose) {
		this.txnPurpose = txnPurpose;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getAppInstall() {
		return appInstall;
	}

	public void setAppInstall(String appInstall) {
		this.appInstall = appInstall;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getAccPvdFormat() {
		return accPvdFormat;
	}

	public void setAccPvdFormat(String accPvdFormat) {
		this.accPvdFormat = accPvdFormat;
	}

	public String getAccPvdIfsc() {
		return accPvdIfsc;
	}

	public void setAccPvdIfsc(String accPvdIfsc) {
		this.accPvdIfsc = accPvdIfsc;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getBbpsBillPayReqJson() {
		return bbpsBillPayReqJson;
	}
	
	public void setBbpsBillPayReqJson(String bbpsBillPayReqJson) {
		this.bbpsBillPayReqJson = bbpsBillPayReqJson;
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
	
	public String getAppVersion() {
		return appVersion;
	}
	
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	
	public String getHandsetName() {
		return handsetName;
	}
	
	public void setHandsetName(String handsetName) {
		this.handsetName = handsetName;
	}
	
	public String getOsName() {
		return osName;
	}
	
	public void setOsName(String osName) {
		this.osName = osName;
	}
	
	public String getOsVersion() {
		return osVersion;
	}
	
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public String getFeedbackCategory() {
		return feedbackCategory;
	}
	
	public void setFeedbackCategory(String feedbackCategory) {
		this.feedbackCategory = feedbackCategory;
	}
	
	public String getFeedbackSeverity() {
		return feedbackSeverity;
	}
	
	public void setFeedbackSeverity(String feedbackSeverity) {
		this.feedbackSeverity = feedbackSeverity;
	}
	
	public String getRemarks() {
		return Remarks;
	}
	
	public void setRemarks(String remarks) {
		Remarks = remarks;
	}
	
	public String getFeedbackText() {
		return feedbackText;
	}
	
	public void setFeedbackText(String feedbackText) {
		this.feedbackText = feedbackText;
	}
	
	public String getScreenPath() {
		return screenPath;
	}
	
	public void setScreenPath(String screenPath) {
		this.screenPath = screenPath;
	}
	
	public String getHistoryFrom() {
		return historyFrom;
	}
	
	public String getOtp() {
		return otp;
	}
	
	public void setOtp(String otp) {
		this.otp = otp;
	}
	
	public String getMode() {
		return mode;
	}
	
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public void setHistoryFrom(String historyFrom) {
		this.historyFrom = historyFrom;
	}
	
	public String getHistoryTo() {
		return historyTo;
	}
	
	public void setHistoryTo(String historyTo) {
		this.historyTo = historyTo;
	}
	
	public static class Account implements Serializable {
		public static class CredsAllowed implements Serializable {
			/**
			 *
			 */
			private static final long	serialVersionUID	= -2476993530046549687L;
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
		
		/**
		 *
		 */
		private static final long	serialVersionUID	= 4973314442300817095L;
		
		private String				accRefNumber;
		private String				accType;
		private String				aeba;
		private List<CredsAllowed>	credsAlloweds;
		private String				ifsc;
		private String				maskedAccnumber;
		private String				mbeba;
		private String				mmid;
		private String				name;
		
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
	private static final long							serialVersionUID	= 125436029528238960L;
	
	private String										headReqMsgId;
	private String										bkPrf;
	private String										rrn;
	private String										txnType;
	private String										txnId;
	private String										txnRefId;
	private String										txnRefUrl;
	///
	private String										tokenChallenge;
	private String										tokenType;
	private String										tokenStr;
	///
	
	private String										txnNote;
	
	///
	private String										ruleExpireAfter;
	private String										ruleMinAmount;
	private List<Account>								accounts;
	
	///
	
	///
	private String										credAtmpin;
	private String										credMpin;
	
	private String										credNMpin;
	private String										credOtp;
	///
	private String										deviceMobile;
	private String										deviceType;
	private String										deviceGeoCode;
	private String										deviceLocation;
	private String										deviceIp;
	private String										deviceId;
	private String										deviceOsType;
	private String										deviceAppId;
	private String										deviceCapability;
	///
	private String										linkType;
	private String										linkValue;
	///
	private String										payeeName;
	private String										payeeSeqNum;
	
	private String										payeeType;
	private String										payeeAddr;
	private String										payeeAmount;
	private String										payeeAcNum;
	private String										payeeAcType;
	private String										payeeAddrType;
	private String										payeeCode;
	private String										payeeIfsc;
	private String										payeeIin;
	private String										payeeMmid;
	private String										payeeMobileNo;
	private String										payeeUidNum;
	private String										payeeCardNum;
	///
	private String										payeeNickName;
	private String										payerName;
	private String										payerSeqNum;
	private String										payerType;
	private String										payerAddr;
	private String										payerAmount;
	private String										payerAcNum;
	private String										payerAcType;
	private String										payerAddrType;
	private String										payerCode;
	private String										payerIfsc;
	private String										payerIin;
	private String										payerMmid;
	private String										payerMobileNo;
	private String										payerUidNum;
	private String										payerCardNum;
	///
	private String										orgRespCode;
	///
	private String										regDetailsType;
	
	private String										regCardDigits;
	private String										regExpDate;
	private String										regMobile;
	///
	private String										respBal;
	private String										respCode;
	private String										respMsg;
	private String										msgId;
	private String										msg;
	private String										upiErrorCode;
	private List<RespListAccount.AccountList.Account>	upiAccount;
	private String										listKeys;
	private String										accPvds;
	private List<CredJson>								credJsons;
	private String										refApprovalNum;
	private List<PayTrans>								respMsgPayTrans;
	private String										orgTxnId;
	private String										orgTxnType;
	private String										field11;
	private String										atmCardFiled11;
	private String										cbsErrorCode;
	private String										regId;

	private Mandate										mandate;
// CR GST QR/ UPI 2.0 30 March
		private String isAdvancedQR; // value = Y/N
		private String qrVersion;  // value = 01/02/03
		private String qrMode;    // value = 01 (static qr code)/11 (upi mandate)
		private String qrPurpose; // value = 00/01/03
		private String qrTxnRef; // Transaction reference Id. This could be order number, subscription number, Bill Id, Booking Id, Insurance Renewal reference etc.
		private String qrRefURL; // invoice in the box
		private String qrCurrency; // settlement currency
		private String qrMID; // merchant id 
		private String qrMSID; // merchant store id
		private String qrTID; // merchant terminal id
		private String qrCC; // country code
		private String qrGSTBrkUp; // GST break up
		private String qrMerchantType;  // OFFLINE/ONLINE
		private String qrPuchanseAmount; // International QR bAm
		private String qrBaseCurrency; // International QR
		private String qrMedium; // 01=gallery, 02=app, 03=pos, 05=ATM , 06 =WEB
		private String qrInvoice; // invoice number
		private String qrExpire; // QR expire time
		private String qrTS;  // QR creation time stamp 
		private String qrPincode; // merchant pin code
		private String qrTier; // merchant city type
		private String qrConsent; // future use
		private String qrMandateType;// future use
		private String qrMandateValStart; // Mandate validity start 
		private String qrMandateValEnd; // Mandate Validity end
		private String qrAmruleMandate; // Max or Exact default MAX Mandat URL case only
		private String qrRecurrMandate; // mandate URL only  ONETIME
		private String qrRecurrValMandate; // future use
		private String qrRecurrTypeMandate; // BEFORE/ON/AFTER
		private String qrRevMandate; // Mandate revokable Y/N
		private String qrShareMandate; // Y/N
		private String qrBlockMandate; // Y/N default Y
		private String qrSkipMandate; // future use
		private String qrQuery; // future use
		
		//upper parameter may be removed 
		
		private String ifQrStringAvailable;//Yew/NO
		private String qrParam;
		
		
		private String  orgMsgId;
	
	private String source;
	
	private String node;
	
	private String              tpId;
	
	private String tenantId;
	
	private Object privateF;
	
	
	public String getOrgMsgId() {
		return orgMsgId;
	}

	public void setOrgMsgId(String orgMsgId) {
		this.orgMsgId = orgMsgId;
	}
	
		public String getTpId() {
		return tpId;
	}

	public void setTpId(String tpId) {
		this.tpId = tpId;
	}
	
		public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Object getPrivateF() {
		return privateF;
	}

	public void setPrivateF(Object privateF) {
		this.privateF = privateF;
	}
		public String getIfQrStringAvailable() {
			return ifQrStringAvailable;
		}

		public void setIfQrStringAvailable(String ifQrStringAvailable) {
			this.ifQrStringAvailable = ifQrStringAvailable;
		}

		public String getQrParam() {
			return qrParam;
		}

		public void setQrParam(String qrParam) {
			this.qrParam = qrParam;
		}

		public String getIsAdvancedQR() {
			return isAdvancedQR;
		}

		public void setIsAdvancedQR(String isAdvancedQR) {
			this.isAdvancedQR = isAdvancedQR;
		}

		public String getQrVersion() {
			return qrVersion;
		}

		public void setQrVersion(String qrVersion) {
			this.qrVersion = qrVersion;
		}

		public String getQrMode() {
			return qrMode;
		}

		public void setQrMode(String qrMode) {
			this.qrMode = qrMode;
		}

		public String getQrPurpose() {
			return qrPurpose;
		}

		public void setQrPurpose(String qrPurpose) {
			this.qrPurpose = qrPurpose;
		}

		public String getQrTxnRef() {
			return qrTxnRef;
		}

		public void setQrTxnRef(String qrTxnRef) {
			this.qrTxnRef = qrTxnRef;
		}

		public String getQrRefURL() {
			return qrRefURL;
		}

		public void setQrRefURL(String qrRefURL) {
			this.qrRefURL = qrRefURL;
		}

		public String getQrCurrency() {
			return qrCurrency;
		}

		public void setQrCurrency(String qrCurrency) {
			this.qrCurrency = qrCurrency;
		}

		public String getQrMID() {
			return qrMID;
		}

		public void setQrMID(String qrMID) {
			this.qrMID = qrMID;
		}

		public String getQrMSID() {
			return qrMSID;
		}

		public void setQrMSID(String qrMSID) {
			this.qrMSID = qrMSID;
		}

		public String getQrTID() {
			return qrTID;
		}

		public void setQrTID(String qrTID) {
			this.qrTID = qrTID;
		}

		public String getQrCC() {
			return qrCC;
		}

		public void setQrCC(String qrCC) {
			this.qrCC = qrCC;
		}

		public String getQrGSTBrkUp() {
			return qrGSTBrkUp;
		}

		public void setQrGSTBrkUp(String qrGSTBrkUp) {
			this.qrGSTBrkUp = qrGSTBrkUp;
		}

		public String getQrMerchantType() {
			return qrMerchantType;
		}

		public void setQrMerchantType(String qrMerchantType) {
			this.qrMerchantType = qrMerchantType;
		}

		public String getQrPuchanseAmount() {
			return qrPuchanseAmount;
		}

		public void setQrPuchanseAmount(String qrPuchanseAmount) {
			this.qrPuchanseAmount = qrPuchanseAmount;
		}

		public String getQrBaseCurrency() {
			return qrBaseCurrency;
		}

		public void setQrBaseCurrency(String qrBaseCurrency) {
			this.qrBaseCurrency = qrBaseCurrency;
		}

		public String getQrMedium() {
			return qrMedium;
		}

		public void setQrMedium(String qrMedium) {
			this.qrMedium = qrMedium;
		}

		public String getQrInvoice() {
			return qrInvoice;
		}

		public void setQrInvoice(String qrInvoice) {
			this.qrInvoice = qrInvoice;
		}

		public String getQrExpire() {
			return qrExpire;
		}

		public void setQrExpire(String qrExpire) {
			this.qrExpire = qrExpire;
		}

		public String getQrTS() {
			return qrTS;
		}

		public void setQrTS(String qrTS) {
			this.qrTS = qrTS;
		}

		public String getQrPincode() {
			return qrPincode;
		}

		public void setQrPincode(String qrPincode) {
			this.qrPincode = qrPincode;
		}

		public String getQrTier() {
			return qrTier;
		}

		public void setQrTier(String qrTier) {
			this.qrTier = qrTier;
		}

		public String getQrConsent() {
			return qrConsent;
		}

		public void setQrConsent(String qrConsent) {
			this.qrConsent = qrConsent;
		}

		public String getQrMandateType() {
			return qrMandateType;
		}

		public void setQrMandateType(String qrMandateType) {
			this.qrMandateType = qrMandateType;
		}

		public String getQrMandateValStart() {
			return qrMandateValStart;
		}

		public void setQrMandateValStart(String qrMandateValStart) {
			this.qrMandateValStart = qrMandateValStart;
		}

		public String getQrMandateValEnd() {
			return qrMandateValEnd;
		}

		public void setQrMandateValEnd(String qrMandateValEnd) {
			this.qrMandateValEnd = qrMandateValEnd;
		}

		public String getQrAmruleMandate() {
			return qrAmruleMandate;
		}

		public void setQrAmruleMandate(String qrAmruleMandate) {
			this.qrAmruleMandate = qrAmruleMandate;
		}

		public String getQrRecurrMandate() {
			return qrRecurrMandate;
		}

		public void setQrRecurrMandate(String qrRecurrMandate) {
			this.qrRecurrMandate = qrRecurrMandate;
		}

		public String getQrRecurrValMandate() {
			return qrRecurrValMandate;
		}

		public void setQrRecurrValMandate(String qrRecurrValMandate) {
			this.qrRecurrValMandate = qrRecurrValMandate;
		}

		public String getQrRecurrTypeMandate() {
			return qrRecurrTypeMandate;
		}

		public void setQrRecurrTypeMandate(String qrRecurrTypeMandate) {
			this.qrRecurrTypeMandate = qrRecurrTypeMandate;
		}

		public String getQrRevMandate() {
			return qrRevMandate;
		}

		public void setQrRevMandate(String qrRevMandate) {
			this.qrRevMandate = qrRevMandate;
		}

		public String getQrShareMandate() {
			return qrShareMandate;
		}

		public void setQrShareMandate(String qrShareMandate) {
			this.qrShareMandate = qrShareMandate;
		}

		public String getQrBlockMandate() {
			return qrBlockMandate;
		}

		public void setQrBlockMandate(String qrBlockMandate) {
			this.qrBlockMandate = qrBlockMandate;
		}

		public String getQrSkipMandate() {
			return qrSkipMandate;
		}

		public void setQrSkipMandate(String qrSkipMandate) {
			this.qrSkipMandate = qrSkipMandate;
		}

		public String getQrQuery() {
			return qrQuery;
		}

		public void setQrQuery(String qrQuery) {
			this.qrQuery = qrQuery;
		}
		public Mandate getMandate() {
		return mandate;
	}

	public void setMandate(Mandate mandate) {
		this.mandate = mandate;
	}

	
	
	
	
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getPayeeNickName() {
		return payeeNickName;
	}
	
	public void setPayeeNickName(String payeeNickName) {
		this.payeeNickName = payeeNickName;
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
	
	public String getBkPrf() {
		return bkPrf;
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
	
	public String getDeviceAppId() {
		return deviceAppId;
	}
	
	public String getDeviceCapability() {
		return deviceCapability;
	}
	
	public String getDeviceGeoCode() {
		return deviceGeoCode;
	}
	
	public String getDeviceIp() {
		return deviceIp;
	}
	
	public String getDeviceLocation() {
		return deviceLocation;
	}
	
	public String getDeviceMobile() {
		return deviceMobile;
	}
	
	public String getDeviceOsType() {
		return deviceOsType;
	}
	
	public String getDeviceType() {
		return deviceType;
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
	
	public String getTokenChallenge() {
		return tokenChallenge;
	}
	
	public String getTokenStr() {
		return tokenStr;
	}
	
	public String getTokenType() {
		return tokenType;
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
	
	public void setDeviceAppId(String deviceAppId) {
		this.deviceAppId = deviceAppId;
	}
	
	public void setDeviceCapability(String deviceCapability) {
		this.deviceCapability = deviceCapability;
	}
	
	public void setDeviceGeoCode(String deviceGeoCode) {
		this.deviceGeoCode = deviceGeoCode;
	}
	
	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}
	
	public void setDeviceLocation(String deviceLocation) {
		this.deviceLocation = deviceLocation;
	}
	
	public void setDeviceMobile(String deviceMobile) {
		this.deviceMobile = deviceMobile;
	}
	
	public void setDeviceOsType(String deviceOsType) {
		this.deviceOsType = deviceOsType;
	}
	
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
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
	
	public void setTokenChallenge(String tokenChallenge) {
		this.tokenChallenge = tokenChallenge;
	}
	
	public void setTokenStr(String tokenStr) {
		this.tokenStr = tokenStr;
	}
	
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
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
	
	public void setUpiAccount(List<RespListAccount.AccountList.Account> upiAccount) {
		this.upiAccount = upiAccount;
	}
	
	public void setUpiErrorCode(String upiErrorCode) {
		this.upiErrorCode = upiErrorCode;
	}
	
	private String	txnFlag;
	
	private String	disputeType;
	
	public String getDisputeType() {
		return disputeType;
	}
	
	public void setDisputeType(String disputeType) {
		this.disputeType = disputeType;
	}
	
	public String getAccRefNumber() {
		return accRefNumber;
	}
	
	public String getAccType() {
		return accType;
	}
	
	public String getAddhaarNo() {
		return addhaarNo;
	}
	
	public String getAeba() {
		return aeba;
	}
	
	public String getAmount() {
		return amount;
	}
	
	public String getAppOs() {
		return appOs;
	}
	
	public String getAppVer() {
		return appVer;
	}
	
	public String getBankName() {
		return bankName;
	}
	
	public String getBeneid() {
		return beneid;
	}
	
	public String getChangePinFlag() {
		return changePinFlag;
	}
	
	public String getComplaintDesc() {
		return complaintDesc;
	}
	
	public String getCredsAllowed() {
		return credsAllowed;
	}
	
	public String getCustRef() {
		return custRef;
	}
	
	public String getDeviceId() {
		return deviceId;
	}
	
	public String getDob() {
		return dob;
	}
	
	public String geteDate() {
		return eDate;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getFlag() {
		return flag;
	}
	
	public String getfName() {
		return fName;
	}
	
	public String getGcmToken() {
		return gcmToken;
	}
	
	public String getGender() {
		return gender;
	}
	
	public String getHistoryLimit() {
		return historyLimit;
	}
	
	public String getIdPk() {
		return idPk;
	}
	
	public String getIfsc() {
		return ifsc;
	}
	
	public String getImei() {
		return imei;
	}
	
	public String getlName() {
		return lName;
	}
	
	public String getLoginPin() {
		return loginPin;
	}
	
	public String getLovType() {
		return lovType;
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
	
	public String getmName() {
		return mName;
	}
	
	public String getMobileNo() {
		return mobileNo;
	}
	
	public String getName() {
		return name;
	}
	
	public String getNewLoginPin() {
		return newLoginPin;
	}
	
	public String getPayeeaccno() {
		return payeeaccno;
	}
	
	public String getPayeeaddr() {
		return payeeaddr;
	}
	
	public String getPayeeadharno() {
		return payeeadharno;
	}
	
	public String getPayeecode() {
		return payeecode;
	}
	
	public String getPayeeifsc() {
		return payeeifsc;
	}
	
	public String getPayeeiin() {
		return payeeiin;
	}
	
	public String getPayeemmid() {
		return payeemmid;
	}
	
	public String getPayeemobilen() {
		return payeemobilen;
	}
	
	public String getPayeename() {
		return payeename;
	}
	
	public String getPayeeseqno() {
		return payeeseqno;
	}
	
	public String getPayeetype() {
		return payeetype;
	}
	
	public String getPayerAddr() {
		return payerAddr;
	}
	
	public String getRegId() {
		return regId;
	}
	
	public String getsDate() {
		return sDate;
	}
	
	public String getSecAns() {
		return secAns;
	}
	
	public String getSecQue() {
		return secQue;
	}
	
	public String getStatus() {
		return status;
	}
	
	public String getTxnDate() {
		return txnDate;
	}
	
	public String getTxnFlag() {
		return txnFlag;
	}
	
	public String getTxnNo() {
		return txnNo;
	}
	
	public String getTxnType() {
		return txnType;
	}
	
	public String getType() {
		return type;
	}
	
	public String getUpiApiType() {
		return upiApiType;
	}
	
	public String getVirtualId() {
		return virtualId;
	}
	
	public void setAccRefNumber(String accRefNumber) {
		this.accRefNumber = accRefNumber;
	}
	
	public void setAccType(String accType) {
		this.accType = accType;
	}
	
	public void setAddhaarNo(String addhaarNo) {
		this.addhaarNo = addhaarNo;
	}
	
	public void setAeba(String aeba) {
		this.aeba = aeba;
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public void setAppOs(String appOs) {
		this.appOs = appOs;
	}
	
	public void setAppVer(String appVer) {
		this.appVer = appVer;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public void setBeneid(String beneid) {
		this.beneid = beneid;
	}
	
	public void setChangePinFlag(String changePinFlag) {
		this.changePinFlag = changePinFlag;
	}
	
	public void setComplaintDesc(String complaintDesc) {
		this.complaintDesc = complaintDesc;
	}
	
	public void setCredsAllowed(String credsAllowed) {
		this.credsAllowed = credsAllowed;
	}
	
	public void setCustRef(String custRef) {
		this.custRef = custRef;
	}
	
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public void setDob(String dob) {
		this.dob = dob;
	}
	
	public void seteDate(String eDate) {
		this.eDate = eDate;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public void setfName(String fName) {
		this.fName = fName;
	}
	
	public void setGcmToken(String gcmToken) {
		this.gcmToken = gcmToken;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public void setHistoryLimit(String historyLimit) {
		this.historyLimit = historyLimit;
	}
	
	public void setIdPk(String idPk) {
		this.idPk = idPk;
	}
	
	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}
	
	public void setImei(String imei) {
		this.imei = imei;
	}
	
	public void setlName(String lName) {
		this.lName = lName;
	}
	
	public void setLoginPin(String loginPin) {
		this.loginPin = loginPin;
	}
	
	public void setLovType(String lovType) {
		this.lovType = lovType;
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
	
	public void setmName(String mName) {
		this.mName = mName;
	}
	
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setNewLoginPin(String newLoginPin) {
		this.newLoginPin = newLoginPin;
	}
	
	public void setPayeeaccno(String payeeaccno) {
		this.payeeaccno = payeeaccno;
	}
	
	public void setPayeeaddr(String payeeaddr) {
		this.payeeaddr = payeeaddr;
	}
	
	public void setPayeeadharno(String payeeadharno) {
		this.payeeadharno = payeeadharno;
	}
	
	public void setPayeecode(String payeecode) {
		this.payeecode = payeecode;
	}
	
	public void setPayeeifsc(String payeeifsc) {
		this.payeeifsc = payeeifsc;
	}
	
	public void setPayeeiin(String payeeiin) {
		this.payeeiin = payeeiin;
	}
	
	public void setPayeemmid(String payeemmid) {
		this.payeemmid = payeemmid;
	}
	
	public void setPayeemobilen(String payeemobilen) {
		this.payeemobilen = payeemobilen;
	}
	
	public void setPayeename(String payeename) {
		this.payeename = payeename;
	}
	
	public void setPayeeseqno(String payeeseqno) {
		this.payeeseqno = payeeseqno;
	}
	
	public void setPayeetype(String payeetype) {
		this.payeetype = payeetype;
	}
	
	public void setPayerAddr(String payerAddr) {
		this.payerAddr = payerAddr;
	}
	
	public void setRegId(String regId) {
		this.regId = regId;
	}
	
	public void setsDate(String sDate) {
		this.sDate = sDate;
	}
	
	public void setSecAns(String secAns) {
		this.secAns = secAns;
	}
	
	public void setSecQue(String secQue) {
		this.secQue = secQue;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}
	
	public void setTxnFlag(String txnFlag) {
		this.txnFlag = txnFlag;
	}
	
	public void setTxnNo(String txnNo) {
		this.txnNo = txnNo;
	}
	
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setUpiApiType(String upiApiType) {
		this.upiApiType = upiApiType;
	}
	
	public void setVirtualId(String virtualId) {
		this.virtualId = virtualId;
	}
	
	public String getTxnStatus() {
		return txnStatus;
	}
	
	public void setTxnStatus(String txnStatus) {
		this.txnStatus = txnStatus;
	}
	
	public String getFrmAmount() {
		return frmAmount;
	}
	
	public void setFrmAmount(String frmAmount) {
		this.frmAmount = frmAmount;
	}
	
	public String getToAmount() {
		return toAmount;
	}
	
	public void setToAmount(String toAmount) {
		this.toAmount = toAmount;
	}
	
	public String getIsDefaultAcc() {
		return isDefaultAcc;
	}
	
	public void setIsDefaultAcc(String isDefaultAcc) {
		this.isDefaultAcc = isDefaultAcc;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReqJson [");
		if (beneid != null) {
			builder.append("beneid=");
			builder.append(beneid);
			builder.append(", ");
		}
		if (payeeaddr != null) {
			builder.append("payeeaddr=");
			builder.append(payeeaddr);
			builder.append(", ");
		}
		if (payeeaccno != null) {
			builder.append("payeeaccno=");
			builder.append(payeeaccno);
			builder.append(", ");
		}
		if (payeename != null) {
			builder.append("payeename=");
			builder.append(payeename);
			builder.append(", ");
		}
		if (payeeseqno != null) {
			builder.append("payeeseqno=");
			builder.append(payeeseqno);
			builder.append(", ");
		}
		if (payeetype != null) {
			builder.append("payeetype=");
			builder.append(payeetype);
			builder.append(", ");
		}
		if (payeecode != null) {
			builder.append("payeecode=");
			builder.append(payeecode);
			builder.append(", ");
		}
		if (payeeifsc != null) {
			builder.append("payeeifsc=");
			builder.append(payeeifsc);
			builder.append(", ");
		}
		if (payeeadharno != null) {
			builder.append("payeeadharno=");
			builder.append(payeeadharno);
			builder.append(", ");
		}
		if (payeeiin != null) {
			builder.append("payeeiin=");
			builder.append(payeeiin);
			builder.append(", ");
		}
		if (payeemobilen != null) {
			builder.append("payeemobilen=");
			builder.append(payeemobilen);
			builder.append(", ");
		}
		if (payeemmid != null) {
			builder.append("payeemmid=");
			builder.append(payeemmid);
			builder.append(", ");
		}
		if (imei != null) {
			builder.append("imei=");
			builder.append(imei);
			builder.append(", ");
		}
		if (deviceId != null) {
			builder.append("deviceId=");
			builder.append(deviceId);
			builder.append(", ");
		}
		if (mobileNo != null) {
			builder.append("mobileNo=");
			builder.append(mobileNo);
			builder.append(", ");
		}
		if (virtualId != null) {
			builder.append("virtualId=");
			builder.append(virtualId);
			builder.append(", ");
		}
		if (regId != null) {
			builder.append("regId=");
			builder.append(regId);
			builder.append(", ");
		}
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
		if (payerAddr != null) {
			builder.append("payerAddr=");
			builder.append(payerAddr);
			builder.append(", ");
		}
		if (aeba != null) {
			builder.append("aeba=");
			builder.append(aeba);
			builder.append(", ");
		}
		if (bankName != null) {
			builder.append("bankName=");
			builder.append(bankName);
			builder.append(", ");
		}
		if (credsAllowed != null) {
			builder.append("credsAllowed=");
			builder.append(credsAllowed);
			builder.append(", ");
		}
		if (mbeba != null) {
			builder.append("mbeba=");
			builder.append(mbeba);
			builder.append(", ");
		}
		if (maskedAccnumber != null) {
			builder.append("maskedAccnumber=");
			builder.append(maskedAccnumber);
			builder.append(", ");
		}
		if (name != null) {
			builder.append("name=");
			builder.append(name);
			builder.append(", ");
		}
		if (ifsc != null) {
			builder.append("ifsc=");
			builder.append(ifsc);
			builder.append(", ");
		}
		if (mmid != null) {
			builder.append("mmid=");
			builder.append(mmid);
			builder.append(", ");
		}
		if (lovType != null) {
			builder.append("lovType=");
			builder.append(lovType);
			builder.append(", ");
		}
		if (loginPin != null) {
			builder.append("loginPin=");
			builder.append(loginPin);
			builder.append(", ");
		}
		if (newLoginPin != null) {
			builder.append("newLoginPin=");
			builder.append(newLoginPin);
			builder.append(", ");
		}
		if (fName != null) {
			builder.append("fName=");
			builder.append(fName);
			builder.append(", ");
		}
		if (gender != null) {
			builder.append("gender=");
			builder.append(gender);
			builder.append(", ");
		}
		if (secQue != null) {
			builder.append("secQue=");
			builder.append(secQue);
			builder.append(", ");
		}
		if (secAns != null) {
			builder.append("secAns=");
			builder.append(secAns);
			builder.append(", ");
		}
		if (email != null) {
			builder.append("email=");
			builder.append(email);
			builder.append(", ");
		}
		if (addhaarNo != null) {
			builder.append("addhaarNo=");
			builder.append(addhaarNo);
			builder.append(", ");
		}
		if (dob != null) {
			builder.append("dob=");
			builder.append(dob);
			builder.append(", ");
		}
		if (appOs != null) {
			builder.append("appOs=");
			builder.append(appOs);
			builder.append(", ");
		}
		if (appVer != null) {
			builder.append("appVer=");
			builder.append(appVer);
			builder.append(", ");
		}
		if (lName != null) {
			builder.append("lName=");
			builder.append(lName);
			builder.append(", ");
		}
		if (gcmToken != null) {
			builder.append("gcmToken=");
			builder.append(gcmToken);
			builder.append(", ");
		}
		if (mName != null) {
			builder.append("mName=");
			builder.append(mName);
			builder.append(", ");
		}
		if (txnType != null) {
			builder.append("txnType=");
			builder.append(txnType);
			builder.append(", ");
		}
		if (flag != null) {
			builder.append("flag=");
			builder.append(flag);
			builder.append(", ");
		}
		if (idPk != null) {
			builder.append("idPk=");
			builder.append(idPk);
			builder.append(", ");
		}
		if (upiApiType != null) {
			builder.append("upiApiType=");
			builder.append(upiApiType);
			builder.append(", ");
		}
		if (changePinFlag != null) {
			builder.append("changePinFlag=");
			builder.append(changePinFlag);
			builder.append(", ");
		}
		if (amount != null) {
			builder.append("amount=");
			builder.append(amount);
			builder.append(", ");
		}
		if (custRef != null) {
			builder.append("custRef=");
			builder.append(custRef);
			builder.append(", ");
		}
		if (status != null) {
			builder.append("status=");
			builder.append(status);
			builder.append(", ");
		}
		if (type != null) {
			builder.append("type=");
			builder.append(type);
			builder.append(", ");
		}
		if (txnDate != null) {
			builder.append("txnDate=");
			builder.append(txnDate);
			builder.append(", ");
		}
		if (txnNo != null) {
			builder.append("txnNo=");
			builder.append(txnNo);
			builder.append(", ");
		}
		if (complaintDesc != null) {
			builder.append("complaintDesc=");
			builder.append(complaintDesc);
			builder.append(", ");
		}
		if (historyLimit != null) {
			builder.append("historyLimit=");
			builder.append(historyLimit);
			builder.append(", ");
		}
		if (eDate != null) {
			builder.append("eDate=");
			builder.append(eDate);
			builder.append(", ");
		}
		if (sDate != null) {
			builder.append("sDate=");
			builder.append(sDate);
			builder.append(", ");
		}
		if (txnFlag != null) {
			builder.append("txnFlag=");
			builder.append(txnFlag);
		}
		if (accPvdFormat != null) {
			builder.append("accPvdFormat=");
			builder.append(accPvdFormat);
		}
		if (accPvdIfsc != null) {
			builder.append("accPvdIfsc=");
			builder.append(accPvdIfsc);
		}
		if (source != null) {
			builder.append("source=");
			builder.append(source);
		}
		if (node != null) {
			builder.append("node=");
			builder.append(node);
		}
		builder.append("]");
		return builder.toString();
	}
	
	public String getLimitamount() {
		return limitamount;
	}
	
	public void setLimitamount(String limitamount) {
		this.limitamount = limitamount;
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
	}
	
}
