package com.npst.mobileservice.object;
// Generated 5 Mar, 2017 5:02:39 PM by Hibernate Tools 5.2.0.CR1

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.npst.upi.hor.Reqrespauthdetails;
import com.npst.upi.hor.ReqrespauthdetailsPayees;

public class ReqrespauthdetailsVO implements Serializable {
	private static final SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static final long serialVersionUID = 1L;
	private Integer idReqRespAuthDetails;
	private Integer regid;
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
	private Integer ruleExpireAfter;
	private String ruleMinAmount;
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
	private String amountCrr;
	private String amountVal;
	private String respResult;
	private String respErrCode;
	private String reqInsert;
	private String respInsert;
	private String respAuthAck;
	private String txnConfOrgErrCode;
	private String txnConfOrgStatus;
	private String refOrgAmount;
	private String refSettAmount;
	private String refRespCode;
	private String refApprovalNum;
	private String refReversalRespCode;
	private String txnConfAck;
	private String payerHandal;
	private List<ReqrespauthdetailsPayees> payees;
	private String collectTime;

	public ReqrespauthdetailsVO() {
	}

	public ReqrespauthdetailsVO(Reqrespauthdetails reqrespauthdetails) {

		this.idReqRespAuthDetails = reqrespauthdetails.getIdReqRespAuthDetails();
		this.regid = reqrespauthdetails.getRegid();
		this.reqHeadTs = reqrespauthdetails.getReqHeadTs();
		this.reqHeadOrgId = reqrespauthdetails.getReqHeadOrgId();
		this.reqHeadMsgId = reqrespauthdetails.getReqHeadMsgId();
		this.respHeadTs = reqrespauthdetails.getRespHeadTs();
		this.respHeadOrgId = reqrespauthdetails.getRespHeadOrgId();
		this.respHeadMsgId = reqrespauthdetails.getRespHeadMsgId();
		this.txnId = reqrespauthdetails.getTxnId();
		this.txnIdPrf = reqrespauthdetails.getTxnIdPrf();
		this.txnNote = reqrespauthdetails.getTxnNote();
		this.txnRefid = reqrespauthdetails.getTxnRefid();
		this.txnRefurl = reqrespauthdetails.getTxnRefurl();
		this.txnTs = reqrespauthdetails.getTxnTs();
		this.txnType = reqrespauthdetails.getTxnType();
		this.txnCustRef = reqrespauthdetails.getTxnCustRef();
		this.ruleExpireAfter = reqrespauthdetails.getRuleExpireAfter();
		this.ruleMinAmount = reqrespauthdetails.getRuleMinAmount();
		this.payerAddr = reqrespauthdetails.getPayerAddr();
		this.payerName = reqrespauthdetails.getPayerName();
		this.payerSeqNum = reqrespauthdetails.getPayerSeqNum();
		this.payerType = reqrespauthdetails.getPayerType();
		this.payerCode = reqrespauthdetails.getPayerCode();
		this.infoIdType = reqrespauthdetails.getInfoIdType();
		this.infoId = reqrespauthdetails.getInfoId();
		this.infoIdVerifiedName = reqrespauthdetails.getInfoIdVerifiedName();
		this.infoIdRatingvaddr = reqrespauthdetails.getInfoIdRatingvaddr();
		this.acAddrType = reqrespauthdetails.getAcAddrType();
		this.acAddrTypeDetail1 = reqrespauthdetails.getAcAddrTypeDetail1();
		this.acAddrTypeDetail2 = reqrespauthdetails.getAcAddrTypeDetail2();
		this.acAddrTypeDetail3 = reqrespauthdetails.getAcAddrTypeDetail3();
		this.credType = reqrespauthdetails.getCredType();
		this.credSubType = reqrespauthdetails.getCredSubType();
		this.deviceMobile = reqrespauthdetails.getDeviceMobile();
		this.deviceGeocode = reqrespauthdetails.getDeviceGeocode();
		this.deviceLocation = reqrespauthdetails.getDeviceLocation();
		this.deviceIp = reqrespauthdetails.getDeviceIp();
		this.deviceType = reqrespauthdetails.getDeviceType();
		this.deviceId = reqrespauthdetails.getDeviceId();
		this.deviceOs = reqrespauthdetails.getDeviceOs();
		this.deviceApp = reqrespauthdetails.getDeviceApp();
		this.deviceCapability = reqrespauthdetails.getDeviceCapability();
		this.amountCrr = reqrespauthdetails.getAmountCrr();
		this.amountVal = reqrespauthdetails.getAmountVal();
		this.respResult = reqrespauthdetails.getRespResult();
		this.respErrCode = reqrespauthdetails.getRespErrCode();
		this.reqInsert = dtFormat.format(reqrespauthdetails.getReqInsert());
		if (null != reqrespauthdetails.getRespInsert()) {
			this.respInsert = dtFormat.format(reqrespauthdetails.getRespInsert());
		}
		this.respAuthAck = reqrespauthdetails.getRespAuthAck();
		this.txnConfOrgErrCode = reqrespauthdetails.getTxnConfOrgErrCode();
		this.txnConfOrgStatus = reqrespauthdetails.getTxnConfOrgStatus();
		this.refOrgAmount = reqrespauthdetails.getRefOrgAmount();
		this.refSettAmount = reqrespauthdetails.getRefSettAmount();
		this.refRespCode = reqrespauthdetails.getRefRespCode();
		this.refApprovalNum = reqrespauthdetails.getRefApprovalNum();
		this.refReversalRespCode = reqrespauthdetails.getRefReversalRespCode();
		this.txnConfAck = reqrespauthdetails.getTxnConfAck();
	}

	// @Column(name = "AcAddrType", length = 21)
	public String getAcAddrType() {
		return this.acAddrType;
	}

	// @Column(name = "AcAddrTypeDetail1", length = 256)
	public String getAcAddrTypeDetail1() {
		return this.acAddrTypeDetail1;
	}

	// @Column(name = "AcAddrTypeDetail2", length = 256)
	public String getAcAddrTypeDetail2() {
		return this.acAddrTypeDetail2;
	}

	// @Column(name = "AcAddrTypeDetail3", length = 256)
	public String getAcAddrTypeDetail3() {
		return this.acAddrTypeDetail3;
	}

	// @Column(name = "AmountCrr", length = 5)
	public String getAmountCrr() {
		return this.amountCrr;
	}

	// @Column(name = "AmountVal", length = 21)
	public String getAmountVal() {
		return this.amountVal;
	}

	public String getCollectTime() {
		return collectTime;
	}

	// @Column(name = "CredSubType", length = 21)
	public String getCredSubType() {
		return this.credSubType;
	}

	// @Column(name = "CredType", length = 21)
	public String getCredType() {
		return this.credType;
	}

	// @Column(name = "DeviceApp", length = 256)
	public String getDeviceApp() {
		return this.deviceApp;
	}

	// @Column(name = "DeviceCapability", length = 256)
	public String getDeviceCapability() {
		return this.deviceCapability;
	}

	// @Column(name = "DeviceGeocode", length = 256)
	public String getDeviceGeocode() {
		return this.deviceGeocode;
	}

	// @Column(name = "DeviceId", length = 256)
	public String getDeviceId() {
		return this.deviceId;
	}

	// @Column(name = "DeviceIp", length = 256)
	public String getDeviceIp() {
		return this.deviceIp;
	}

	// @Column(name = "DeviceLocation", length = 256)
	public String getDeviceLocation() {
		return this.deviceLocation;
	}

	// @Column(name = "DeviceMobile", length = 256)
	public String getDeviceMobile() {
		return this.deviceMobile;
	}

	// @Column(name = "DeviceOs", length = 256)
	public String getDeviceOs() {
		return this.deviceOs;
	}

	// @Column(name = "DeviceType", length = 256)
	public String getDeviceType() {
		return this.deviceType;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// @Column(name = "idReqRespAuthDetails", unique = true, nullable = false)
	public Integer getIdReqRespAuthDetails() {
		return this.idReqRespAuthDetails;
	}

	// @Column(name = "InfoId", length = 45)
	public String getInfoId() {
		return this.infoId;
	}

	// @Column(name = "InfoIdRatingvaddr", length = 5)
	public String getInfoIdRatingvaddr() {
		return this.infoIdRatingvaddr;
	}

	// @Column(name = "InfoIdType", length = 21)
	public String getInfoIdType() {
		return this.infoIdType;
	}

	// @Column(name = "InfoIdVerifiedName", length = 100)
	public String getInfoIdVerifiedName() {
		return this.infoIdVerifiedName;
	}

	public List<ReqrespauthdetailsPayees> getPayees() {
		return payees;
	}

	// @Column(name = "PayerAddr", length = 256)
	public String getPayerAddr() {
		return this.payerAddr;
	}

	// @Column(name = "PayerCode", length = 4)
	public String getPayerCode() {
		return this.payerCode;
	}

	public String getPayerHandal() {
		return payerHandal;
	}

	// @Column(name = "PayerName", length = 99)
	public String getPayerName() {
		return this.payerName;
	}

	// @Column(name = "PayerSeqNum", length = 3)
	public String getPayerSeqNum() {
		return this.payerSeqNum;
	}

	// @Column(name = "PayerType", length = 20)
	public String getPayerType() {
		return this.payerType;
	}

	// @Column(name = "RefApprovalNum", length = 20)
	public String getRefApprovalNum() {
		return this.refApprovalNum;
	}

	// @Column(name = "RefOrgAmount", length = 10)
	public String getRefOrgAmount() {
		return this.refOrgAmount;
	}

	// @Column(name = "RefRespCode", length = 5)
	public String getRefRespCode() {
		return this.refRespCode;
	}

	// @Column(name = "RefReversalRespCode", length = 5)
	public String getRefReversalRespCode() {
		return this.refReversalRespCode;
	}

	// @Column(name = "RefSettAmount", length = 10)
	public String getRefSettAmount() {
		return this.refSettAmount;
	}

	// @Column(name = "RegId")
	public Integer getRegid() {
		return regid;
	}

	// @Column(name = "ReqHeadMsgId", length = 36)
	public String getReqHeadMsgId() {
		return this.reqHeadMsgId;
	}

	// @Column(name = "ReqHeadOrgId", length = 21)
	public String getReqHeadOrgId() {
		return this.reqHeadOrgId;
	}

	// @Column(name = "ReqHeadTs", length = 30)
	public String getReqHeadTs() {
		return this.reqHeadTs;
	}

	@Temporal(TemporalType.TIMESTAMP)
	// @Column(name = "ReqInsert", length = 19)
	public String getReqInsert() {
		return this.reqInsert;
	}

	// @Column(name = "RespAuthAck", length = 50)
	public String getRespAuthAck() {
		return this.respAuthAck;
	}

	// @Column(name = "RespErrCode", length = 5)
	public String getRespErrCode() {
		return this.respErrCode;
	}

	// @Column(name = "RespHeadMsgId", length = 36)
	public String getRespHeadMsgId() {
		return this.respHeadMsgId;
	}

	// @Column(name = "RespHeadOrgId", length = 21)
	public String getRespHeadOrgId() {
		return this.respHeadOrgId;
	}

	// @Column(name = "RespHeadTs", length = 30)
	public String getRespHeadTs() {
		return this.respHeadTs;
	}

	@Temporal(TemporalType.TIMESTAMP)
	// @Column(name = "RespInsert", length = 19)
	public String getRespInsert() {
		return this.respInsert;
	}

	// @Column(name = "RespResult", length = 21)
	public String getRespResult() {
		return this.respResult;
	}

	// @Column(name = "RuleExpireAfter")
	public Integer getRuleExpireAfter() {
		return this.ruleExpireAfter;
	}

	// @Column(name = "RuleMinAmount", length = 21)
	public String getRuleMinAmount() {
		return this.ruleMinAmount;
	}

	// @Column(name = "TxnConfAck", length = 50)
	public String getTxnConfAck() {
		return this.txnConfAck;
	}

	// @Column(name = "TxnConfOrgErrCode", length = 5)
	public String getTxnConfOrgErrCode() {
		return this.txnConfOrgErrCode;
	}

	// @Column(name = "TxnConfOrgStatus", length = 10)
	public String getTxnConfOrgStatus() {
		return this.txnConfOrgStatus;
	}

	// @Column(name = "TxnCustRef", length = 13)
	public String getTxnCustRef() {
		return this.txnCustRef;
	}

	// @Column(name = "TxnId", length = 36)
	public String getTxnId() {
		return this.txnId;
	}

	// @Column(name = "TxnIdPrf", length = 3)
	public String getTxnIdPrf() {
		return this.txnIdPrf;
	}

	// @Column(name = "TxnNote", length = 50)
	public String getTxnNote() {
		return this.txnNote;
	}

	// @Column(name = "TxnRefid", length = 36)
	public String getTxnRefid() {
		return this.txnRefid;
	}

	// @Column(name = "TxnRefurl", length = 36)
	public String getTxnRefurl() {
		return this.txnRefurl;
	}

	// @Column(name = "TxnTs", length = 30)
	public String getTxnTs() {
		return this.txnTs;
	}

	// @Column(name = "TxnType", length = 21)
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

	public void setAmountCrr(String amountCrr) {
		this.amountCrr = amountCrr;
	}

	public void setAmountVal(String amountVal) {
		this.amountVal = amountVal;
	}

	public void setCollectTime(String collectTime) {
		this.collectTime = collectTime;
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

	public void setIdReqRespAuthDetails(Integer idReqRespAuthDetails) {
		this.idReqRespAuthDetails = idReqRespAuthDetails;
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

	public void setPayees(List<ReqrespauthdetailsPayees> payees) {
		this.payees = payees;
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

	public void setRefApprovalNum(String refApprovalNum) {
		this.refApprovalNum = refApprovalNum;
	}

	public void setRefOrgAmount(String refOrgAmount) {
		this.refOrgAmount = refOrgAmount;
	}

	public void setRefRespCode(String refRespCode) {
		this.refRespCode = refRespCode;
	}

	public void setRefReversalRespCode(String refReversalRespCode) {
		this.refReversalRespCode = refReversalRespCode;
	}

	public void setRefSettAmount(String refSettAmount) {
		this.refSettAmount = refSettAmount;
	}

	public void setRegid(Integer regid) {
		this.regid = regid;
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

	public void setReqInsert(String reqInsert) {
		this.reqInsert = reqInsert;
	}

	public void setRespAuthAck(String respAuthAck) {
		this.respAuthAck = respAuthAck;
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

	public void setRespInsert(String respInsert) {
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

	public void setTxnConfAck(String txnConfAck) {
		this.txnConfAck = txnConfAck;
	}

	public void setTxnConfOrgErrCode(String txnConfOrgErrCode) {
		this.txnConfOrgErrCode = txnConfOrgErrCode;
	}

	public void setTxnConfOrgStatus(String txnConfOrgStatus) {
		this.txnConfOrgStatus = txnConfOrgStatus;
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
		builder.append("Reqrespauthdetails [");
		if (idReqRespAuthDetails != null) {
			builder.append("idReqRespAuthDetails=");
			builder.append(idReqRespAuthDetails);
			builder.append(", ");
		}
		if (reqHeadTs != null) {
			builder.append("reqHeadTs=");
			builder.append(reqHeadTs);
			builder.append(", ");
		}
		if (reqHeadOrgId != null) {
			builder.append("reqHeadOrgId=");
			builder.append(reqHeadOrgId);
			builder.append(", ");
		}
		if (reqHeadMsgId != null) {
			builder.append("reqHeadMsgId=");
			builder.append(reqHeadMsgId);
			builder.append(", ");
		}
		if (respHeadTs != null) {
			builder.append("respHeadTs=");
			builder.append(respHeadTs);
			builder.append(", ");
		}
		if (respHeadOrgId != null) {
			builder.append("respHeadOrgId=");
			builder.append(respHeadOrgId);
			builder.append(", ");
		}
		if (respHeadMsgId != null) {
			builder.append("respHeadMsgId=");
			builder.append(respHeadMsgId);
			builder.append(", ");
		}
		if (txnId != null) {
			builder.append("txnId=");
			builder.append(txnId);
			builder.append(", ");
		}
		if (txnIdPrf != null) {
			builder.append("txnIdPrf=");
			builder.append(txnIdPrf);
			builder.append(", ");
		}
		if (txnNote != null) {
			builder.append("txnNote=");
			builder.append(txnNote);
			builder.append(", ");
		}
		if (txnRefid != null) {
			builder.append("txnRefid=");
			builder.append(txnRefid);
			builder.append(", ");
		}
		if (txnRefurl != null) {
			builder.append("txnRefurl=");
			builder.append(txnRefurl);
			builder.append(", ");
		}
		if (txnTs != null) {
			builder.append("txnTs=");
			builder.append(txnTs);
			builder.append(", ");
		}
		if (txnType != null) {
			builder.append("txnType=");
			builder.append(txnType);
			builder.append(", ");
		}
		if (txnCustRef != null) {
			builder.append("txnCustRef=");
			builder.append(txnCustRef);
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
		if (payerAddr != null) {
			builder.append("payerAddr=");
			builder.append(payerAddr);
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
		if (payerCode != null) {
			builder.append("payerCode=");
			builder.append(payerCode);
			builder.append(", ");
		}
		if (infoIdType != null) {
			builder.append("infoIdType=");
			builder.append(infoIdType);
			builder.append(", ");
		}
		if (infoId != null) {
			builder.append("infoId=");
			builder.append(infoId);
			builder.append(", ");
		}
		if (infoIdVerifiedName != null) {
			builder.append("infoIdVerifiedName=");
			builder.append(infoIdVerifiedName);
			builder.append(", ");
		}
		if (infoIdRatingvaddr != null) {
			builder.append("infoIdRatingvaddr=");
			builder.append(infoIdRatingvaddr);
			builder.append(", ");
		}
		if (acAddrType != null) {
			builder.append("acAddrType=");
			builder.append(acAddrType);
			builder.append(", ");
		}
		if (acAddrTypeDetail1 != null) {
			builder.append("acAddrTypeDetail1=");
			builder.append(acAddrTypeDetail1);
			builder.append(", ");
		}
		if (acAddrTypeDetail2 != null) {
			builder.append("acAddrTypeDetail2=");
			builder.append(acAddrTypeDetail2);
			builder.append(", ");
		}
		if (acAddrTypeDetail3 != null) {
			builder.append("acAddrTypeDetail3=");
			builder.append(acAddrTypeDetail3);
			builder.append(", ");
		}
		if (credType != null) {
			builder.append("credType=");
			builder.append(credType);
			builder.append(", ");
		}
		if (credSubType != null) {
			builder.append("credSubType=");
			builder.append(credSubType);
			builder.append(", ");
		}
		if (deviceMobile != null) {
			builder.append("deviceMobile=");
			builder.append(deviceMobile);
			builder.append(", ");
		}
		if (deviceGeocode != null) {
			builder.append("deviceGeocode=");
			builder.append(deviceGeocode);
			builder.append(", ");
		}
		if (deviceLocation != null) {
			builder.append("deviceLocation=");
			builder.append(deviceLocation);
			builder.append(", ");
		}
		if (deviceIp != null) {
			builder.append("deviceIp=");
			builder.append(deviceIp);
			builder.append(", ");
		}
		if (deviceType != null) {
			builder.append("deviceType=");
			builder.append(deviceType);
			builder.append(", ");
		}
		if (deviceId != null) {
			builder.append("deviceId=");
			builder.append(deviceId);
			builder.append(", ");
		}
		if (deviceOs != null) {
			builder.append("deviceOs=");
			builder.append(deviceOs);
			builder.append(", ");
		}
		if (deviceApp != null) {
			builder.append("deviceApp=");
			builder.append(deviceApp);
			builder.append(", ");
		}
		if (deviceCapability != null) {
			builder.append("deviceCapability=");
			builder.append(deviceCapability);
			builder.append(", ");
		}
		if (amountCrr != null) {
			builder.append("amountCrr=");
			builder.append(amountCrr);
			builder.append(", ");
		}
		if (amountVal != null) {
			builder.append("amountVal=");
			builder.append(amountVal);
			builder.append(", ");
		}
		if (respResult != null) {
			builder.append("respResult=");
			builder.append(respResult);
			builder.append(", ");
		}
		if (respErrCode != null) {
			builder.append("respErrCode=");
			builder.append(respErrCode);
			builder.append(", ");
		}
		if (reqInsert != null) {
			builder.append("reqInsert=");
			builder.append(reqInsert);
			builder.append(", ");
		}
		if (respInsert != null) {
			builder.append("respInsert=");
			builder.append(respInsert);
			builder.append(", ");
		}
		if (respAuthAck != null) {
			builder.append("respAuthAck=");
			builder.append(respAuthAck);
			builder.append(", ");
		}
		if (txnConfOrgErrCode != null) {
			builder.append("txnConfOrgErrCode=");
			builder.append(txnConfOrgErrCode);
			builder.append(", ");
		}
		if (txnConfOrgStatus != null) {
			builder.append("txnConfOrgStatus=");
			builder.append(txnConfOrgStatus);
			builder.append(", ");
		}
		if (refOrgAmount != null) {
			builder.append("refOrgAmount=");
			builder.append(refOrgAmount);
			builder.append(", ");
		}
		if (refSettAmount != null) {
			builder.append("refSettAmount=");
			builder.append(refSettAmount);
			builder.append(", ");
		}
		if (refRespCode != null) {
			builder.append("refRespCode=");
			builder.append(refRespCode);
			builder.append(", ");
		}
		if (refApprovalNum != null) {
			builder.append("refApprovalNum=");
			builder.append(refApprovalNum);
			builder.append(", ");
		}
		if (refReversalRespCode != null) {
			builder.append("refReversalRespCode=");
			builder.append(refReversalRespCode);
			builder.append(", ");
		}
		if (txnConfAck != null) {
			builder.append("txnConfAck=");
			builder.append(txnConfAck);
			builder.append(", ");
		}
		if (payerHandal != null) {
			builder.append("payerHandal=");
			builder.append(payerHandal);
		}
		builder.append("]");
		return builder.toString();
	}

}
