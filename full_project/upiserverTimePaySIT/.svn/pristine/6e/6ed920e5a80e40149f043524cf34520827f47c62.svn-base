package com.npst.upiserver.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "reqresppaycollect")
public class ReqRespPayCollect implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private Long idReqRespPayCollect;
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
	private Integer ruleExpireAfter;
	private String ruleMinAmount;
	private String amountCrr;
	private String amountVal;
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
	
	@Column(name = "Ack", length = 50)
	public String getAck() {
		return this.ack;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idReqRespPayCollect", unique = true, nullable = false)
	public Long getIdReqRespPayCollect() {
		return this.idReqRespPayCollect;
	}
	
	@Column(name = "InfoId", length = 45)
	public String getInfoId() {
		return this.infoId;
	}
	
	@Column(name = "InfoIdRatingvaddr", length = 5)
	public String getInfoIdRatingvaddr() {
		return this.infoIdRatingvaddr;
	}
	
	@Column(name = "InfoIdType", length = 21)
	public String getInfoIdType() {
		return this.infoIdType;
	}
	
	@Column(name = "InfoIdVerifiedName", length = 100)
	public String getInfoIdVerifiedName() {
		return this.infoIdVerifiedName;
	}
	
	@Column(name = "PayerAddr", length = 256)
	public String getPayerAddr() {
		return this.payerAddr;
	}
	
	@Column(name = "PayerCode", length = 4)
	public String getPayerCode() {
		return this.payerCode;
	}
	
	@Column(name = "PayerHandal")
	public String getPayerHandal() {
		return this.payerHandal;
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
	
	@Column(name = "RefAcNum")
	public String getRefAcNum() {
		return this.refAcNum;
	}
	
	@Column(name = "RefAddr")
	public String getRefAddr() {
		return this.refAddr;
	}
	
	@Column(name = "RefApprovalNum")
	public String getRefApprovalNum() {
		return this.refApprovalNum;
	}
	
	@Column(name = "RefOrgAmount")
	public String getRefOrgAmount() {
		return this.refOrgAmount;
	}
	
	@Column(name = "RefRegName")
	public String getRefRegName() {
		return this.refRegName;
	}
	
	@Column(name = "RefRespCode")
	public String getRefRespCode() {
		return this.refRespCode;
	}
	
	@Column(name = "RefReversalRespCode")
	public String getRefReversalRespCode() {
		return this.refReversalRespCode;
	}
	
	@Column(name = "RefSeqNum")
	public String getRefSeqNum() {
		return this.refSeqNum;
	}
	
	@Column(name = "RefSettAmount")
	public String getRefSettAmount() {
		return this.refSettAmount;
	}
	
	@Column(name = "RefSettCurrency")
	public String getRefSettCurrency() {
		return this.refSettCurrency;
	}
	
	@Column(name = "RefType")
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
	@Column(name = "ReqInsert", unique = true, length = 19)
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
	
	@Column(name = "RespHeadTs", length = 40)
	public String getRespHeadTs() {
		return this.respHeadTs;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RespInsert", unique = true, length = 19)
	public Date getRespInsert() {
		return this.respInsert;
	}
	
	@Column(name = "RespResult", length = 21)
	public String getRespResult() {
		return this.respResult;
	}
	
	@Column(name = "RuleExpireAfter")
	public Integer getRuleExpireAfter() {
		return this.ruleExpireAfter;
	}
	
	@Column(name = "RuleMinAmount", length = 21)
	public String getRuleMinAmount() {
		return this.ruleMinAmount;
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
	
	@Column(name = "TxnTs", length = 40)
	public String getTxnTs() {
		return this.txnTs;
	}
	
	@Column(name = "TxnType", length = 21)
	public String getTxnType() {
		return this.txnType;
	}
	
	public void setAcAddrType(String acAddrType) {
		this.acAddrType = acAddrType;
	}
	
	public void setAcAddrTypeDetail1(String acAddrTypeDetail1) {
		this.acAddrTypeDetail1 = acAddrTypeDetail1;
	}
	
	public void setAcAddrTypeDetail2(String acAddrTypeDetail2) {
		this.acAddrTypeDetail2 = acAddrTypeDetail2;
	}
	
	public void setAcAddrTypeDetail3(String acAddrTypeDetail3) {
		this.acAddrTypeDetail3 = acAddrTypeDetail3;
	}
	
	public void setAck(String ack) {
		this.ack = ack;
	}
	
	public void setAmountCrr(String amountCrr) {
		this.amountCrr = amountCrr;
	}
	
	public void setAmountVal(String amountVal) {
		this.amountVal = amountVal;
	}
	
	public void setCredSubType(String credSubType) {
		this.credSubType = credSubType;
	}
	
	public void setCredType(String credType) {
		this.credType = credType;
	}
	
	public void setDeviceApp(String deviceApp) {
		this.deviceApp = deviceApp;
	}
	
	public void setDeviceCapability(String deviceCapability) {
		this.deviceCapability = deviceCapability;
	}
	
	public void setDeviceGeocode(String deviceGeocode) {
		this.deviceGeocode = deviceGeocode;
	}
	
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
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
	
	public void setDeviceOs(String deviceOs) {
		this.deviceOs = deviceOs;
	}
	
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	
	public void setIdReqRespPayCollect(Long idReqRespPayCollect) {
		this.idReqRespPayCollect = idReqRespPayCollect;
	}
	
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}
	
	public void setInfoIdRatingvaddr(String infoIdRatingvaddr) {
		this.infoIdRatingvaddr = infoIdRatingvaddr;
	}
	
	public void setInfoIdType(String infoIdType) {
		this.infoIdType = infoIdType;
	}
	
	public void setInfoIdVerifiedName(String infoIdVerifiedName) {
		this.infoIdVerifiedName = infoIdVerifiedName;
	}
	
	public void setPayerAddr(String payerAddr) {
		this.payerAddr = payerAddr;
	}
	
	public void setPayerCode(String payerCode) {
		this.payerCode = payerCode;
	}
	
	public void setPayerHandal(String payerHandal) {
		this.payerHandal = payerHandal;
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
	
	public void setRefAcNum(String refAcNum) {
		this.refAcNum = refAcNum;
	}
	
	public void setRefAddr(String refAddr) {
		this.refAddr = refAddr;
	}
	
	public void setRefApprovalNum(String refApprovalNum) {
		this.refApprovalNum = refApprovalNum;
	}
	
	public void setRefOrgAmount(String refOrgAmount) {
		this.refOrgAmount = refOrgAmount;
	}
	
	public void setRefRegName(String refRegName) {
		this.refRegName = refRegName;
	}
	
	public void setRefRespCode(String refRespCode) {
		this.refRespCode = refRespCode;
	}
	
	public void setRefReversalRespCode(String refReversalRespCode) {
		this.refReversalRespCode = refReversalRespCode;
	}
	
	public void setRefSeqNum(String refSeqNum) {
		this.refSeqNum = refSeqNum;
	}
	
	public void setRefSettAmount(String refSettAmount) {
		this.refSettAmount = refSettAmount;
	}
	
	public void setRefSettCurrency(String refSettCurrency) {
		this.refSettCurrency = refSettCurrency;
	}
	
	public void setRefType(String refType) {
		this.refType = refType;
	}
	
	public void setReqHeadMsgId(String reqHeadMsgId) {
		this.reqHeadMsgId = reqHeadMsgId;
	}
	
	public void setReqHeadOrgId(String reqHeadOrgId) {
		this.reqHeadOrgId = reqHeadOrgId;
	}
	
	public void setReqHeadTs(String reqHeadTs) {
		this.reqHeadTs = reqHeadTs;
	}
	
	public void setReqInsert(Date reqInsert) {
		this.reqInsert = reqInsert;
	}
	
	public void setRespErrCode(String respErrCode) {
		this.respErrCode = respErrCode;
	}
	
	public void setRespHeadMsgId(String respHeadMsgId) {
		this.respHeadMsgId = respHeadMsgId;
	}
	
	public void setRespHeadOrgId(String respHeadOrgId) {
		this.respHeadOrgId = respHeadOrgId;
	}
	
	public void setRespHeadTs(String respHeadTs) {
		this.respHeadTs = respHeadTs;
	}
	
	public void setRespInsert(Date respInsert) {
		this.respInsert = respInsert;
	}
	
	public void setRespResult(String respResult) {
		this.respResult = respResult;
	}
	
	public void setRuleExpireAfter(Integer ruleExpireAfter) {
		this.ruleExpireAfter = ruleExpireAfter;
	}
	
	public void setRuleMinAmount(String ruleMinAmount) {
		this.ruleMinAmount = ruleMinAmount;
	}
	
	public void setTxnCustRef(String txnCustRef) {
		this.txnCustRef = txnCustRef;
	}
	
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	
	public void setTxnIdPrf(String txnIdPrf) {
		this.txnIdPrf = txnIdPrf;
	}
	
	public void setTxnNote(String txnNote) {
		this.txnNote = txnNote;
	}
	
	public void setTxnRefid(String txnRefid) {
		this.txnRefid = txnRefid;
	}
	
	public void setTxnRefurl(String txnRefurl) {
		this.txnRefurl = txnRefurl;
	}
	
	public void setTxnTs(String txnTs) {
		this.txnTs = txnTs;
	}
	
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Reqresppaycollect [");
		if (idReqRespPayCollect != null) {
			builder.append("idReqRespPayCollect=").append(idReqRespPayCollect).append(", ");
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

}