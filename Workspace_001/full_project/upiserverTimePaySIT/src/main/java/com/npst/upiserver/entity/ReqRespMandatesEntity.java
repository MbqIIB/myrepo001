package com.npst.upiserver.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "MANDATES_REQ_RESP")
public class ReqRespMandatesEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idMandates;
	private String reqHeadTs;
	private String reqHeadOrgId;
	private String reqHeadMsgId;
	private String respHeadTs;
	private String respHeadOrgId;
	private String respHeadMsgId;
	private String txnId;
	private String txnIdPrf;
	private String txnNote;
	private String txnRefid;
	private String txnRefurl;
	private String txnTs;
	private String txnType;
	private String txnCustRef;
	private String txnInitiationMode;
	private String payerHandal;
	private String payerAddr;
	private String payerName;
	private String payerSeqNum;
	private String payerType;
	private String payerCode;
	private String infoIdType;
	private String infoId;
	private String infoIdVerifiedName;
	private String infoIdRatingvaddr;
	private String acAddrType;
	private String acAddrTypeDetail1;
	private String acAddrTypeDetail2;
	private String acAddrTypeDetail3;
	private String credType;
	private String credSubType;
	private String deviceMobile;
	private String deviceGeocode;
	private String deviceLocation;
	private String deviceIp;
	private String deviceType;
	private String deviceId;
	private String deviceOs;
	private String deviceApp;
	private String deviceCapability;
	private String ruleExpireAfter;
	private String ruleMinAmount;
	private String amountCrr;
	private String amountVal;
	private String mandateName;
	private String mandateTxnId;
	private String mandateUmn;
	private String mandateTs;
	private String mandateRevokeable;
	private String mandateShareToPayee;
	private String mandateType;
	private String mandateValidityStart;
	private String mandateValidityEnd;
	private String mandateAmountvalue;
	private String mandateAmountrule;
	private String mandateRecurrencepattern;
	private String mandateRecurrenceRulevalue;
	private String mandateRecurrenceRuletype;
	private String respResult;
	private String respErrCode;
	private String refType;
	private String refSeqNum;
	private String refAddr;
	private String refRegName;
	private String refSettAmount;
	private String refSettCurrency;
	private String refAcNum;
	private String refApprovalNum;
	private String refRespCode;
	private String refReversalRespCode;
	private String refOrgAmount;
	private Date reqInsert;
	private Date respInsert;
	private String ack;
	private String payerDeviceTelecom;
	private String payeeDeviceTelecom;
	private String txnOrgId;
	private String amountSplit;
	private String amountValue;
	private String purpose;
	private String bidRefNo;

	@Column(name = "MandateSignId")
	private String mandateSignId;

	private String mandateSignValue;

	private Integer status;

	@Column(name = "OrgId")
	public String getTxnOrgId() {
		return txnOrgId;
	}

	public void setTxnOrgId(String txnOrgId) {
		this.txnOrgId = txnOrgId;
	}
	
	
	@Column(name = "bidRefNo")
	public String getBidRefNo() {
		return bidRefNo;
	}

	public void setBidRefNo(String bidRefNo) {
		this.bidRefNo = bidRefNo;
	}

	@Column(name = "purpose")
	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	@Column(name = "AmountSplit")
	public String getAmountSplit() {
		return amountSplit;
	}

	public void setAmountSplit(String amountSplit) {
		this.amountSplit = amountSplit;
	}

	@Column(name = "AmountValue")
	public String getAmountValue() {
		return amountValue;
	}

	public void setAmountValue(String amountValue) {
		this.amountValue = amountValue;
	}

	@Column(name = "PayerDeviceTelecom")
	public String getPayerDeviceTelecom() {
		return payerDeviceTelecom;
	}

	public void setPayerDeviceTelecom(String payerDeviceTelecom) {
		this.payerDeviceTelecom = payerDeviceTelecom;
	}

	@Column(name = "PayeeDeviceTelecom")
	public String getPayeeDeviceTelecom() {
		return payeeDeviceTelecom;
	}

	public void setPayeeDeviceTelecom(String payeeDeviceTelecom) {
		this.payeeDeviceTelecom = payeeDeviceTelecom;
	}

	@Column(name = "AcAddrType", length = 21)
	public String getAcAddrType() {
		return this.acAddrType;
	}

	@Column(name = "AcAddrTypeDetail1", length = 256)
	public String getAcAddrTypeDetail1() {
		return this.acAddrTypeDetail1;
	}

	@Column(name = "AcAddrTypeDetail2", length = 256)
	public String getAcAddrTypeDetail2() {
		return this.acAddrTypeDetail2;
	}

	@Column(name = "AcAddrTypeDetail3", length = 256)
	public String getAcAddrTypeDetail3() {
		return this.acAddrTypeDetail3;
	}

	@Column(name = "Ack")
	public String getAck() {
		return ack;
	}

	@Column(name = "AmountCrr", length = 5)
	public String getAmountCrr() {
		return this.amountCrr;
	}

	@Column(name = "AmountVal", length = 21)
	public String getAmountVal() {
		return this.amountVal;
	}

	@Column(name = "CredSubType", length = 21)
	public String getCredSubType() {
		return this.credSubType;
	}

	@Column(name = "CredType", length = 21)
	public String getCredType() {
		return this.credType;
	}

	@Column(name = "DeviceApp", length = 256)
	public String getDeviceApp() {
		return this.deviceApp;
	}

	@Column(name = "DeviceCapability", length = 256)
	public String getDeviceCapability() {
		return this.deviceCapability;
	}

	@Column(name = "DeviceGeocode", length = 256)
	public String getDeviceGeocode() {
		return this.deviceGeocode;
	}

	@Column(name = "DeviceId", length = 256)
	public String getDeviceId() {
		return this.deviceId;
	}

	@Column(name = "DeviceIp", length = 256)
	public String getDeviceIp() {
		return this.deviceIp;
	}

	@Column(name = "DeviceLocation", length = 256)
	public String getDeviceLocation() {
		return this.deviceLocation;
	}

	@Column(name = "DeviceMobile", length = 256)
	public String getDeviceMobile() {
		return this.deviceMobile;
	}

	@Column(name = "DeviceOs", length = 256)
	public String getDeviceOs() {
		return this.deviceOs;
	}

	@Column(name = "DeviceType", length = 256)
	public String getDeviceType() {
		return this.deviceType;
	}

	@Id
	@Column(name = "IdMandates", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getIdMandates() {
		return this.idMandates;
	}

	@Column(name = "InfoId")
	public String getInfoId() {
		return infoId;
	}

	@Column(name = "InfoIdRatingvaddr")
	public String getInfoIdRatingvaddr() {
		return infoIdRatingvaddr;
	}

	@Column(name = "InfoIdType")
	public String getInfoIdType() {
		return infoIdType;
	}

	@Column(name = "InfoIdVerifiedName")
	public String getInfoIdVerifiedName() {
		return infoIdVerifiedName;
	}

	@Column(name = "MandateAmountrule")
	public String getMandateAmountrule() {
		return mandateAmountrule;
	}

	@Column(name = "MandateAmountvalue")
	public String getMandateAmountvalue() {
		return mandateAmountvalue;
	}

	@Column(name = "MandateName")
	public String getMandateName() {
		return mandateName;
	}

	@Column(name = "MandateRecurrencepattern")
	public String getMandateRecurrencepattern() {
		return mandateRecurrencepattern;
	}

	@Column(name = "MandateRecurrenceRuletype")
	public String getMandateRecurrenceRuletype() {
		return mandateRecurrenceRuletype;
	}

	@Column(name = "MandateRecurrenceRulevalue")
	public String getMandateRecurrenceRulevalue() {
		return mandateRecurrenceRulevalue;
	}

	@Column(name = "MandateRevokeable")
	public String getMandateRevokeable() {
		return mandateRevokeable;
	}

	@Column(name = "MandateShareToPayee")
	public String getMandateShareToPayee() {
		return mandateShareToPayee;
	}

	@Column(name = "MandateTs")
	public String getMandateTs() {
		return mandateTs;
	}

	@Column(name = "MandateTxnId")
	public String getMandateTxnId() {
		return mandateTxnId;
	}

	@Column(name = "MandateType")
	public String getMandateType() {
		return mandateType;
	}

	@Column(name = "MandateUmn")
	public String getMandateUmn() {
		return mandateUmn;
	}

	@Column(name = "MandateValidityEnd")
	public String getMandateValidityEnd() {
		return mandateValidityEnd;
	}

	@Column(name = "MandateValidityStart")
	public String getMandateValidityStart() {
		return mandateValidityStart;
	}

	@Column(name = "PayerAddr", length = 256)
	public String getPayerAddr() {
		return this.payerAddr;
	}

	@Column(name = "PayerCode", length = 4)
	public String getPayerCode() {
		return this.payerCode;
	}

	@Column(name = "payerHandal", length = 21)

	public String getPayerHandal() {
		return payerHandal;
	}

	@Column(name = "PayerName", length = 99)
	public String getPayerName() {
		return this.payerName;
	}

	@Column(name = "PayerSeqNum", length = 3)
	public String getPayerSeqNum() {
		return this.payerSeqNum;
	}

	@Column(name = "PayerType", length = 20)
	public String getPayerType() {
		return this.payerType;
	}

	@Column(name = "RefAcNum", length = 21)
	public String getRefAcNum() {
		return this.refAcNum;
	}

	@Column(name = "RefAddr")
	public String getRefAddr() {
		return this.refAddr;
	}

	@Column(name = "RefApprovalNum", length = 7)
	public String getRefApprovalNum() {
		return this.refApprovalNum;
	}

	@Column(name = "RefOrgAmount", length = 45)
	public String getRefOrgAmount() {
		return this.refOrgAmount;
	}

	@Column(name = "RefRegName", length = 99)
	public String getRefRegName() {
		return this.refRegName;
	}

	@Column(name = "RefRespCode", length = 50)
	public String getRefRespCode() {
		return this.refRespCode;
	}

	@Column(name = "RefReversalRespCode", length = 45)
	public String getRefReversalRespCode() {
		return this.refReversalRespCode;
	}

	@Column(name = "RefSeqNum", length = 3)
	public String getRefSeqNum() {
		return this.refSeqNum;
	}

	@Column(name = "RefSettAmount", length = 21)
	public String getRefSettAmount() {
		return this.refSettAmount;
	}

	@Column(name = "RefSettCurrency", length = 5)
	public String getRefSettCurrency() {
		return this.refSettCurrency;
	}

	@Column(name = "RefType", length = 21)
	public String getRefType() {
		return this.refType;
	}

	@Column(name = "ReqHeadMsgId", length = 36)
	public String getReqHeadMsgId() {
		return this.reqHeadMsgId;
	}

	@Column(name = "ReqHeadOrgId", length = 21)
	public String getReqHeadOrgId() {
		return this.reqHeadOrgId;
	}

	@Column(name = "ReqHeadTs", length = 30)
	public String getReqHeadTs() {
		return this.reqHeadTs;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ReqInsert", length = 19)
	public Date getReqInsert() {
		return this.reqInsert;
	}

	@Column(name = "RespErrCode", length = 5)
	public String getRespErrCode() {
		return this.respErrCode;
	}

	@Column(name = "RespHeadMsgId", length = 36)
	public String getRespHeadMsgId() {
		return this.respHeadMsgId;
	}

	@Column(name = "RespHeadOrgId", length = 21)
	public String getRespHeadOrgId() {
		return this.respHeadOrgId;
	}

	@Column(name = "RespHeadTs", length = 30)
	public String getRespHeadTs() {
		return this.respHeadTs;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RespInsert", length = 19)
	public Date getRespInsert() {
		return this.respInsert;
	}

	@Column(name = "RespResult", length = 21)
	public String getRespResult() {
		return this.respResult;
	}

	@Column(name = "RuleExpireAfter")
	public String getRuleExpireAfter() {
		return ruleExpireAfter;
	}

	@Column(name = "RuleMinAmount")
	public String getRuleMinAmount() {
		return ruleMinAmount;
	}

	@Column(name = "TxnCustRef", length = 13)
	public String getTxnCustRef() {
		return this.txnCustRef;
	}

	@Column(name = "TxnId", length = 36)
	public String getTxnId() {
		return this.txnId;
	}

	@Column(name = "TxnIdPrf", length = 3)
	public String getTxnIdPrf() {
		return this.txnIdPrf;
	}

	@Column(name = "TxnInitiationMode")
	public String getTxnInitiationMode() {
		return txnInitiationMode;
	}

	@Column(name = "TxnNote", length = 50)
	public String getTxnNote() {
		return this.txnNote;
	}

	@Column(name = "TxnRefid", length = 36)
	public String getTxnRefid() {
		return this.txnRefid;
	}

	@Column(name = "TxnRefurl", length = 36)
	public String getTxnRefurl() {
		return this.txnRefurl;
	}

	@Column(name = "TxnTs", length = 30)
	public String getTxnTs() {
		return this.txnTs;
	}

	@Column(name = "TxnType", length = 21)
	public String getTxnType() {
		return this.txnType;
	}

	public void setAcAddrType(final String acAddrType) {
		this.acAddrType = acAddrType;
	}

	public void setAcAddrTypeDetail1(final String acAddrTypeDetail1) {
		this.acAddrTypeDetail1 = acAddrTypeDetail1;
	}

	public void setAcAddrTypeDetail2(final String acAddrTypeDetail2) {
		this.acAddrTypeDetail2 = acAddrTypeDetail2;
	}

	public void setAcAddrTypeDetail3(final String acAddrTypeDetail3) {
		this.acAddrTypeDetail3 = acAddrTypeDetail3;
	}

	public void setAck(final String ack) {
		this.ack = ack;
	}

	public void setAmountCrr(final String amountCrr) {
		this.amountCrr = amountCrr;
	}

	public void setAmountVal(final String amountVal) {
		this.amountVal = amountVal;
	}

	public void setCredSubType(final String credSubType) {
		this.credSubType = credSubType;
	}

	public void setCredType(final String credType) {
		this.credType = credType;
	}

	public void setDeviceApp(final String deviceApp) {
		this.deviceApp = deviceApp;
	}

	public void setDeviceCapability(final String deviceCapability) {
		this.deviceCapability = deviceCapability;
	}

	public void setDeviceGeocode(final String deviceGeocode) {
		this.deviceGeocode = deviceGeocode;
	}

	public void setDeviceId(final String deviceId) {
		this.deviceId = deviceId;
	}

	public void setDeviceIp(final String deviceIp) {
		this.deviceIp = deviceIp;
	}

	public void setDeviceLocation(final String deviceLocation) {
		this.deviceLocation = deviceLocation;
	}

	public void setDeviceMobile(final String deviceMobile) {
		this.deviceMobile = deviceMobile;
	}

	public void setDeviceOs(final String deviceOs) {
		this.deviceOs = deviceOs;
	}

	// public void setDeviceTelecom(final String deviceTelecom) {
	// this.deviceTelecom = deviceTelecom;
	// }

	public void setDeviceType(final String deviceType) {
		this.deviceType = deviceType;
	}

	public void setIdMandates(final Long idMandates) {
		this.idMandates = idMandates;
	}

	public void setInfoId(final String infoId) {
		this.infoId = infoId;
	}

	public void setInfoIdRatingvaddr(final String infoIdRatingvaddr) {
		this.infoIdRatingvaddr = infoIdRatingvaddr;
	}

	public void setInfoIdType(final String infoIdType) {
		this.infoIdType = infoIdType;
	}

	public void setInfoIdVerifiedName(final String infoIdVerifiedName) {
		this.infoIdVerifiedName = infoIdVerifiedName;
	}

	public void setMandateAmountrule(final String mandateAmountrule) {
		this.mandateAmountrule = mandateAmountrule;
	}

	public void setMandateAmountvalue(final String mandateAmountvalue) {
		this.mandateAmountvalue = mandateAmountvalue;
	}

	public void setMandateName(final String mandateName) {
		this.mandateName = mandateName;
	}

	public void setMandateRecurrencepattern(final String mandateRecurrencepattern) {
		this.mandateRecurrencepattern = mandateRecurrencepattern;
	}

	public void setMandateRecurrenceRuletype(final String mandateRecurrenceRuletype) {
		this.mandateRecurrenceRuletype = mandateRecurrenceRuletype;
	}

	public void setMandateRecurrenceRulevalue(final String mandateRecurrenceRulevalue) {
		this.mandateRecurrenceRulevalue = mandateRecurrenceRulevalue;
	}

	public void setMandateRevokeable(final String mandateRevokeable) {
		this.mandateRevokeable = mandateRevokeable;
	}

	public void setMandateShareToPayee(final String mandateShareToPayee) {
		this.mandateShareToPayee = mandateShareToPayee;
	}

	public void setMandateTs(final String mandateTs) {
		this.mandateTs = mandateTs;
	}

	public void setMandateTxnId(final String mandateTxnId) {
		this.mandateTxnId = mandateTxnId;
	}

	public void setMandateType(final String mandateType) {
		this.mandateType = mandateType;
	}

	public void setMandateUmn(final String mandateUmn) {
		this.mandateUmn = mandateUmn;
	}

	public void setMandateValidityEnd(final String mandateValidityEnd) {
		this.mandateValidityEnd = mandateValidityEnd;
	}

	public void setMandateValidityStart(final String mandateValidityStart) {
		this.mandateValidityStart = mandateValidityStart;
	}

	public void setPayerAddr(final String payerAddr) {
		this.payerAddr = payerAddr;
	}

	public void setPayerCode(final String payerCode) {
		this.payerCode = payerCode;
	}

	public void setPayerHandal(final String payerHandal) {
		this.payerHandal = payerHandal;
	}

	public void setPayerName(final String payerName) {
		this.payerName = payerName;
	}

	public void setPayerSeqNum(final String payerSeqNum) {
		this.payerSeqNum = payerSeqNum;
	}

	public void setPayerType(final String payerType) {
		this.payerType = payerType;
	}

	public void setRefAcNum(final String refAcNum) {
		this.refAcNum = refAcNum;
	}

	public void setRefAddr(final String refAddr) {
		this.refAddr = refAddr;
	}

	public void setRefApprovalNum(final String refApprovalNum) {
		this.refApprovalNum = refApprovalNum;
	}

	public void setRefOrgAmount(final String refOrgAmount) {
		this.refOrgAmount = refOrgAmount;
	}

	public void setRefRegName(final String refRegName) {
		this.refRegName = refRegName;
	}

	public void setRefRespCode(final String refRespCode) {
		this.refRespCode = refRespCode;
	}

	public void setRefReversalRespCode(final String refReversalRespCode) {
		this.refReversalRespCode = refReversalRespCode;
	}

	public void setRefSeqNum(final String refSeqNum) {
		this.refSeqNum = refSeqNum;
	}

	public void setRefSettAmount(final String refSettAmount) {
		this.refSettAmount = refSettAmount;
	}

	public void setRefSettCurrency(final String refSettCurrency) {
		this.refSettCurrency = refSettCurrency;
	}

	public void setRefType(final String refType) {
		this.refType = refType;
	}

	public void setReqHeadMsgId(final String reqHeadMsgId) {
		this.reqHeadMsgId = reqHeadMsgId;
	}

	public void setReqHeadOrgId(final String reqHeadOrgId) {
		this.reqHeadOrgId = reqHeadOrgId;
	}

	public void setReqHeadTs(final String reqHeadTs) {
		this.reqHeadTs = reqHeadTs;
	}

	public void setReqInsert(final Date reqInsert) {
		this.reqInsert = reqInsert;
	}

	public void setRespErrCode(final String respErrCode) {
		this.respErrCode = respErrCode;
	}

	public void setRespHeadMsgId(final String respHeadMsgId) {
		this.respHeadMsgId = respHeadMsgId;
	}

	public void setRespHeadOrgId(final String respHeadOrgId) {
		this.respHeadOrgId = respHeadOrgId;
	}

	public void setRespHeadTs(final String respHeadTs) {
		this.respHeadTs = respHeadTs;
	}

	public void setRespInsert(final Date respInsert) {
		this.respInsert = respInsert;
	}

	public void setRespResult(final String respResult) {
		this.respResult = respResult;
	}

	public void setRuleExpireAfter(final String ruleExpireAfter) {
		this.ruleExpireAfter = ruleExpireAfter;
	}

	public void setRuleMinAmount(final String ruleMinAmount) {
		this.ruleMinAmount = ruleMinAmount;
	}

	public void setTxnCustRef(final String txnCustRef) {
		this.txnCustRef = txnCustRef;
	}

	public void setTxnId(final String txnId) {
		this.txnId = txnId;
	}

	public void setTxnIdPrf(final String txnIdPrf) {
		this.txnIdPrf = txnIdPrf;
	}

	public void setTxnInitiationMode(final String txnInitiationMode) {
		this.txnInitiationMode = txnInitiationMode;
	}

	public void setTxnNote(final String txnNote) {
		this.txnNote = txnNote;
	}

	public void setTxnRefid(final String txnRefid) {
		this.txnRefid = txnRefid;
	}

	public void setTxnRefurl(final String txnRefurl) {
		this.txnRefurl = txnRefurl;
	}

	public void setTxnTs(final String txnTs) {
		this.txnTs = txnTs;
	}

	public void setTxnType(final String txnType) {
		this.txnType = txnType;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("ReqRespMandate [");
		if (idMandates != null) {
			builder.append("idReqRespMandate=").append(idMandates).append(", ");
		}
		if (reqHeadTs != null) {
			builder.append("reqHeadTs=").append(reqHeadTs).append(", ");
		}
		if (reqHeadOrgId != null) {
			builder.append("reqHeadOrgId=").append(reqHeadOrgId).append(", ");
		}
		if (reqHeadMsgId != null) {
			builder.append("reqHeadMsgId=").append(reqHeadMsgId).append(", ");
		}
		if (respHeadTs != null) {
			builder.append("respHeadTs=").append(respHeadTs).append(", ");
		}
		if (respHeadOrgId != null) {
			builder.append("respHeadOrgId=").append(respHeadOrgId).append(", ");
		}
		if (respHeadMsgId != null) {
			builder.append("respHeadMsgId=").append(respHeadMsgId).append(", ");
		}
		if (txnId != null) {
			builder.append("txnId=").append(txnId).append(", ");
		}
		if (txnIdPrf != null) {
			builder.append("txnIdPrf=").append(txnIdPrf).append(", ");
		}
		if (txnNote != null) {
			builder.append("txnNote=").append(txnNote).append(", ");
		}
		if (txnRefid != null) {
			builder.append("txnRefid=").append(txnRefid).append(", ");
		}
		if (txnRefurl != null) {
			builder.append("txnRefurl=").append(txnRefurl).append(", ");
		}
		if (txnTs != null) {
			builder.append("txnTs=").append(txnTs).append(", ");
		}
		if (txnType != null) {
			builder.append("txnType=").append(txnType).append(", ");
		}
		if (txnCustRef != null) {
			builder.append("txnCustRef=").append(txnCustRef).append(", ");
		}
		if (txnInitiationMode != null) {
			builder.append("txnInitiationMode=").append(txnInitiationMode).append(", ");
		}
		if (payerHandal != null) {
			builder.append("payerHandal=").append(payerHandal).append(", ");
		}
		if (payerAddr != null) {
			builder.append("payerAddr=").append(payerAddr).append(", ");
		}
		if (payerName != null) {
			builder.append("payerName=").append(payerName).append(", ");
		}
		if (payerSeqNum != null) {
			builder.append("payerSeqNum=").append(payerSeqNum).append(", ");
		}
		if (payerType != null) {
			builder.append("payerType=").append(payerType).append(", ");
		}
		if (payerCode != null) {
			builder.append("payerCode=").append(payerCode).append(", ");
		}
		if (infoIdType != null) {
			builder.append("infoIdType=").append(infoIdType).append(", ");
		}
		if (infoId != null) {
			builder.append("infoId=").append(infoId).append(", ");
		}
		if (infoIdVerifiedName != null) {
			builder.append("infoIdVerifiedName=").append(infoIdVerifiedName).append(", ");
		}
		if (infoIdRatingvaddr != null) {
			builder.append("infoIdRatingvaddr=").append(infoIdRatingvaddr).append(", ");
		}
		if (acAddrType != null) {
			builder.append("acAddrType=").append(acAddrType).append(", ");
		}
		if (acAddrTypeDetail1 != null) {
			builder.append("acAddrTypeDetail1=").append(acAddrTypeDetail1).append(", ");
		}
		if (acAddrTypeDetail2 != null) {
			builder.append("acAddrTypeDetail2=").append(acAddrTypeDetail2).append(", ");
		}
		if (acAddrTypeDetail3 != null) {
			builder.append("acAddrTypeDetail3=").append(acAddrTypeDetail3).append(", ");
		}
		if (credType != null) {
			builder.append("credType=").append(credType).append(", ");
		}
		if (credSubType != null) {
			builder.append("credSubType=").append(credSubType).append(", ");
		}
		if (deviceMobile != null) {
			builder.append("deviceMobile=").append(deviceMobile).append(", ");
		}
		if (deviceGeocode != null) {
			builder.append("deviceGeocode=").append(deviceGeocode).append(", ");
		}
		if (deviceLocation != null) {
			builder.append("deviceLocation=").append(deviceLocation).append(", ");
		}
		if (deviceIp != null) {
			builder.append("deviceIp=").append(deviceIp).append(", ");
		}
		if (deviceType != null) {
			builder.append("deviceType=").append(deviceType).append(", ");
		}
		if (deviceId != null) {
			builder.append("deviceId=").append(deviceId).append(", ");
		}
		if (deviceOs != null) {
			builder.append("deviceOs=").append(deviceOs).append(", ");
		}
		if (deviceApp != null) {
			builder.append("deviceApp=").append(deviceApp).append(", ");
		}
		if (deviceCapability != null) {
			builder.append("deviceCapability=").append(deviceCapability).append(", ");
		}
		if (ruleExpireAfter != null) {
			builder.append("ruleExpireAfter=").append(ruleExpireAfter).append(", ");
		}
		if (ruleMinAmount != null) {
			builder.append("ruleMinAmount=").append(ruleMinAmount).append(", ");
		}
		if (amountCrr != null) {
			builder.append("amountCrr=").append(amountCrr).append(", ");
		}
		if (amountVal != null) {
			builder.append("amountVal=").append(amountVal).append(", ");
		}
		if (mandateName != null) {
			builder.append("mandateName=").append(mandateName).append(", ");
		}
		if (mandateTxnId != null) {
			builder.append("mandateTxnId=").append(mandateTxnId).append(", ");
		}
		if (mandateUmn != null) {
			builder.append("mandateUmn=").append(mandateUmn).append(", ");
		}
		if (mandateTs != null) {
			builder.append("mandateTs=").append(mandateTs).append(", ");
		}
		if (mandateRevokeable != null) {
			builder.append("mandateRevokeable=").append(mandateRevokeable).append(", ");
		}
		if (mandateShareToPayee != null) {
			builder.append("mandateShareToPayee=").append(mandateShareToPayee).append(", ");
		}
		if (mandateType != null) {
			builder.append("mandateType=").append(mandateType).append(", ");
		}
		if (mandateValidityStart != null) {
			builder.append("mandateValidityStart=").append(mandateValidityStart).append(", ");
		}
		if (mandateValidityEnd != null) {
			builder.append("mandateValidityEnd=").append(mandateValidityEnd).append(", ");
		}
		if (mandateAmountvalue != null) {
			builder.append("mandateAmountvalue=").append(mandateAmountvalue).append(", ");
		}
		if (mandateAmountrule != null) {
			builder.append("mandateAmountrule=").append(mandateAmountrule).append(", ");
		}
		if (mandateRecurrencepattern != null) {
			builder.append("mandateRecurrencepattern=").append(mandateRecurrencepattern).append(", ");
		}
		if (mandateRecurrenceRulevalue != null) {
			builder.append("mandateRecurrenceRulevalue=").append(mandateRecurrenceRulevalue).append(", ");
		}
		if (mandateRecurrenceRuletype != null) {
			builder.append("mandateRecurrenceRuletype=").append(mandateRecurrenceRuletype).append(", ");
		}
		if (respResult != null) {
			builder.append("respResult=").append(respResult).append(", ");
		}
		if (respErrCode != null) {
			builder.append("respErrCode=").append(respErrCode).append(", ");
		}
		if (refType != null) {
			builder.append("refType=").append(refType).append(", ");
		}
		if (refSeqNum != null) {
			builder.append("refSeqNum=").append(refSeqNum).append(", ");
		}
		if (refAddr != null) {
			builder.append("refAddr=").append(refAddr).append(", ");
		}
		if (refRegName != null) {
			builder.append("refRegName=").append(refRegName).append(", ");
		}
		if (refSettAmount != null) {
			builder.append("refSettAmount=").append(refSettAmount).append(", ");
		}
		if (refSettCurrency != null) {
			builder.append("refSettCurrency=").append(refSettCurrency).append(", ");
		}
		if (refAcNum != null) {
			builder.append("refAcNum=").append(refAcNum).append(", ");
		}
		if (refApprovalNum != null) {
			builder.append("refApprovalNum=").append(refApprovalNum).append(", ");
		}
		if (refRespCode != null) {
			builder.append("refRespCode=").append(refRespCode).append(", ");
		}
		if (refReversalRespCode != null) {
			builder.append("refReversalRespCode=").append(refReversalRespCode).append(", ");
		}
		if (refOrgAmount != null) {
			builder.append("refOrgAmount=").append(refOrgAmount).append(", ");
		}
		if (reqInsert != null) {
			builder.append("reqInsert=").append(reqInsert).append(", ");
		}
		if (respInsert != null) {
			builder.append("respInsert=").append(respInsert).append(", ");
		}
		if (ack != null) {
			builder.append("ack=").append(ack);
		}
		builder.append("]");
		return builder.toString();
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMandateSignId() {
		return mandateSignId;
	}

	@Lob
	@Column(name = "MandateSignValue")
	public String getMandateSignValue() {
		return mandateSignValue;
	}

	public void setMandateSignId(String mandateSignId) {
		this.mandateSignId = mandateSignId;
	}

	public void setMandateSignValue(String mandateSignValue) {
		this.mandateSignValue = mandateSignValue;
	}

}
