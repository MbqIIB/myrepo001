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
@Table(name = "reqrespdebitcredit")
public class ReqRespDebitCredit {
	private static final long serialVersionUID = 1L;
	private Long idReqRespDebitCredit;
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
	private String payerAddr;
	private String payerName;
	private String payerSeqNum;
	private String payerType;
	private String payerCode;
	private String acAddrType;
	private String acAddrTypeDetail1;
	private String acAddrTypeDetail2;
	private String acAddrTypeDetail3;
	private String credType;
	private String credSubType;
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
	private String cbserrorCode;
	private String mberrorCode;
	private String cmserrorCode;
	private Date reqInsert;
	private Date respInsert;
	private String ackDebitCredit;
	private Integer rev;
	private String revCBSrrn;
	private String revHeadMsgId;
	private String revTxnId;
	private String revRespResult;
	private String revRespErrCode;
	private String revRefType;
	private String revRefSeqNum;
	private String revRefAddr;
	private String revRefRegName;
	private String revRefSettAmount;
	private String revRefSettCurrency;
	private String revRefAcNum;
	private String revRefApprovalNum;
	private String revRefRespCode;
	private String revRefReversalRespCode;
	private String revRefOrgAmount;
	private String revCbserrorCode;
	private String revMberrorCode;
	private Date revReqInsert;
	private Date revRespInsert;
	private String orgRespCode;
	private String ackRev;

	// Added at 27-08-2017
	private String deviceMobile;
	private String deviceGeocode;
	private String deviceLocation;
	private String deviceIp;
	private String deviceType;
	private String deviceId;
	private String deviceOs;
	private String deviceApp;
	private String deviceCapability;
	private String payerHandal;
	private Integer chkTxnCount;
	private Integer revChkTxnCount;
	private String cbsRRN;
	
	@Column(name = "txnInitiationMode")
	private String txnInitiationMode;
	@Column(name = "txnPurpose")
	private String txnPurpose;

	public ReqRespDebitCredit() {
	}

	public ReqRespDebitCredit(Long idReqRespDebitCredit, String reqHeadTs, String reqHeadOrgId, String reqHeadMsgId,
			String respHeadTs, String respHeadOrgId, String respHeadMsgId, String txnId, String txnIdPrf,
			String txnNote, String txnRefid, String txnRefurl, String txnTs, String txnType, String txnCustRef,
			String payerAddr, String payerName, String payerSeqNum, String payerType, String payerCode,
			String acAddrType, String acAddrTypeDetail1, String acAddrTypeDetail2, String acAddrTypeDetail3,
			String credType, String credSubType, String amountCrr, String amountVal, String respResult,
			String respErrCode, String refType, String refSeqNum, String refAddr, String refRegName,
			String refSettAmount, String refSettCurrency, String refAcNum, String refApprovalNum, String refRespCode,
			String refReversalRespCode, String refOrgAmount, String cbserrorCode, String mberrorCode,
			String cmserrorCode, Date reqInsert, Date respInsert, String ackDebitCredit, Integer rev,
			String revHeadMsgId, String revTxnId, String revRespResult, String revRespErrCode, String revRefType,
			String revRefSeqNum, String revRefAddr, String revRefRegName, String revRefSettAmount,
			String revRefSettCurrency, String revRefAcNum, String revRefApprovalNum, String revRefRespCode,
			String revRefReversalRespCode, String revRefOrgAmount, String revCbserrorCode, String revMberrorCode,
			Date revReqInsert, Date revRespInsert, String orgRespCode, String ackRev, String deviceMobile,
			String deviceGeocode, String deviceLocation, String deviceIp, String deviceType, String deviceId,
			String deviceOs, String deviceApp, String deviceCapability, String payerHandal, Integer chkTxnCount,
			Integer revChkTxnCount, String revCBSrrn, String cbsRRN) {
		this.idReqRespDebitCredit = idReqRespDebitCredit;
		this.reqHeadTs = reqHeadTs;
		this.reqHeadOrgId = reqHeadOrgId;
		this.reqHeadMsgId = reqHeadMsgId;
		this.respHeadTs = respHeadTs;
		this.respHeadOrgId = respHeadOrgId;
		this.respHeadMsgId = respHeadMsgId;
		this.txnId = txnId;
		this.txnIdPrf = txnIdPrf;
		this.txnNote = txnNote;
		this.txnRefid = txnRefid;
		this.txnRefurl = txnRefurl;
		this.txnTs = txnTs;
		this.txnType = txnType;
		this.txnCustRef = txnCustRef;
		this.payerAddr = payerAddr;
		this.payerName = payerName;
		this.payerSeqNum = payerSeqNum;
		this.payerType = payerType;
		this.payerCode = payerCode;
		this.acAddrType = acAddrType;
		this.acAddrTypeDetail1 = acAddrTypeDetail1;
		this.acAddrTypeDetail2 = acAddrTypeDetail2;
		this.acAddrTypeDetail3 = acAddrTypeDetail3;
		this.credType = credType;
		this.credSubType = credSubType;
		this.amountCrr = amountCrr;
		this.amountVal = amountVal;
		this.respResult = respResult;
		this.respErrCode = respErrCode;
		this.refType = refType;
		this.refSeqNum = refSeqNum;
		this.refAddr = refAddr;
		this.refRegName = refRegName;
		this.refSettAmount = refSettAmount;
		this.refSettCurrency = refSettCurrency;
		this.refAcNum = refAcNum;
		this.refApprovalNum = refApprovalNum;
		this.refRespCode = refRespCode;
		this.refReversalRespCode = refReversalRespCode;
		this.refOrgAmount = refOrgAmount;
		this.cbserrorCode = cbserrorCode;
		this.mberrorCode = mberrorCode;
		this.cmserrorCode = cmserrorCode;
		this.reqInsert = reqInsert;
		this.respInsert = respInsert;
		this.ackDebitCredit = ackDebitCredit;
		this.rev = rev;
		this.revHeadMsgId = revHeadMsgId;
		this.revTxnId = revTxnId;
		this.revRespResult = revRespResult;
		this.revRespErrCode = revRespErrCode;
		this.revRefType = revRefType;
		this.revRefSeqNum = revRefSeqNum;
		this.revRefAddr = revRefAddr;
		this.revRefRegName = revRefRegName;
		this.revRefSettAmount = revRefSettAmount;
		this.revRefSettCurrency = revRefSettCurrency;
		this.revRefAcNum = revRefAcNum;
		this.revRefApprovalNum = revRefApprovalNum;
		this.revRefRespCode = revRefRespCode;
		this.revRefReversalRespCode = revRefReversalRespCode;
		this.revRefOrgAmount = revRefOrgAmount;
		this.revCbserrorCode = revCbserrorCode;
		this.revMberrorCode = revMberrorCode;
		this.revReqInsert = revReqInsert;
		this.revRespInsert = revRespInsert;
		this.orgRespCode = orgRespCode;
		this.ackRev = ackRev;
		this.deviceMobile = deviceMobile;
		this.deviceGeocode = deviceGeocode;
		this.deviceLocation = deviceLocation;
		this.deviceIp = deviceIp;
		this.deviceType = deviceType;
		this.deviceId = deviceId;
		this.deviceOs = deviceOs;
		this.deviceApp = deviceApp;
		this.deviceCapability = deviceCapability;
		this.payerHandal = payerHandal;
		this.chkTxnCount = chkTxnCount;
		this.revChkTxnCount = revChkTxnCount;
		this.cbsRRN = cbsRRN;
		this.revCBSrrn = revCBSrrn;
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

	@Column(name = "AckDebitCredit", length = 500)
	public String getAckDebitCredit() {
		return this.ackDebitCredit;
	}

	@Column(name = "AckRev", length = 500)
	public String getAckRev() {
		return this.ackRev;
	}

	@Column(name = "AmountCrr", length = 5)
	public String getAmountCrr() {
		return this.amountCrr;
	}

	@Column(name = "AmountVal", length = 21)
	public String getAmountVal() {
		return this.amountVal;
	}

	@Column(name = "CBSErrorCode", length = 6)
	public String getCbserrorCode() {
		return this.cbserrorCode;
	}

	@Column(name = "CBSRRN", length = 13)
	public String getCbsRRN() {
		return cbsRRN;
	}

	@Column(name = "ChkTxnCount")
	public Integer getChkTxnCount() {
		return chkTxnCount;
	}

	@Column(name = "CMSErrorCode", length = 6)
	public String getCmserrorCode() {
		return this.cmserrorCode;
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
	@Column(name = "IdReqRespDebitCredit", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getIdReqRespDebitCredit() {
		return this.idReqRespDebitCredit;
	}

	@Column(name = "MBErrorCode", length = 6)
	public String getMberrorCode() {
		return this.mberrorCode;
	}

	@Column(name = "OrgRespCode", length = 5)
	public String getOrgRespCode() {
		return this.orgRespCode;
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

	public String getTxnInitiationMode() {
		return txnInitiationMode;
	}

	public void setTxnInitiationMode(String txnInitiationMode) {
		this.txnInitiationMode = txnInitiationMode;
	}

	public String getTxnPurpose() {
		return txnPurpose;
	}

	public void setTxnPurpose(String txnPurpose) {
		this.txnPurpose = txnPurpose;
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

	@Column(name = "Rev")
	public Integer getRev() {
		return this.rev;
	}

	@Column(name = "RevCBSErrorCode", length = 6)
	public String getRevCbserrorCode() {
		return this.revCbserrorCode;
	}

	@Column(name = "RevCBSrrn", length = 13)
	public String getRevCBSrrn() {
		return revCBSrrn;
	}

	@Column(name = "RevChkTxnCount")
	public Integer getRevChkTxnCount() {
		return revChkTxnCount;
	}

	@Column(name = "RevHeadMsgId", length = 36)
	public String getRevHeadMsgId() {
		return this.revHeadMsgId;
	}

	@Column(name = "RevMBErrorCode", length = 6)
	public String getRevMberrorCode() {
		return this.revMberrorCode;
	}

	@Column(name = "RevRefAcNum", length = 21)
	public String getRevRefAcNum() {
		return this.revRefAcNum;
	}

	@Column(name = "RevRefAddr")
	public String getRevRefAddr() {
		return this.revRefAddr;
	}

	@Column(name = "RevRefApprovalNum", length = 7)
	public String getRevRefApprovalNum() {
		return this.revRefApprovalNum;
	}

	@Column(name = "RevRefOrgAmount", length = 10)
	public String getRevRefOrgAmount() {
		return this.revRefOrgAmount;
	}

	@Column(name = "RevRefRegName", length = 99)
	public String getRevRefRegName() {
		return this.revRefRegName;
	}

	@Column(name = "RevRefRespCode", length = 10)
	public String getRevRefRespCode() {
		return this.revRefRespCode;
	}

	@Column(name = "RevRefReversalRespCode", length = 5)
	public String getRevRefReversalRespCode() {
		return this.revRefReversalRespCode;
	}

	@Column(name = "RevRefSeqNum", length = 3)
	public String getRevRefSeqNum() {
		return this.revRefSeqNum;
	}

	@Column(name = "RevRefSettAmount", length = 21)
	public String getRevRefSettAmount() {
		return this.revRefSettAmount;
	}

	@Column(name = "RevRefSettCurrency", length = 5)
	public String getRevRefSettCurrency() {
		return this.revRefSettCurrency;
	}

	@Column(name = "RevRefType", length = 21)
	public String getRevRefType() {
		return this.revRefType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RevReqInsert", length = 19)
	public Date getRevReqInsert() {
		return this.revReqInsert;
	}

	@Column(name = "RevRespErrCode", length = 5)
	public String getRevRespErrCode() {
		return this.revRespErrCode;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RevRespInsert", length = 19)
	public Date getRevRespInsert() {
		return this.revRespInsert;
	}

	@Column(name = "RevRespResult", length = 21)
	public String getRevRespResult() {
		return this.revRespResult;
	}

	@Column(name = "RevTxnId", length = 36)
	public String getRevTxnId() {
		return this.revTxnId;
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

	@Column(name = "TxnTs", length = 30)
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

	public void setAckDebitCredit(String ackDebitCredit) {
		this.ackDebitCredit = ackDebitCredit;
	}

	public void setAckRev(String ackRev) {
		this.ackRev = ackRev;
	}

	public void setAmountCrr(String amountCrr) {
		this.amountCrr = amountCrr;
	}

	public void setAmountVal(String amountVal) {
		this.amountVal = amountVal;
	}

	public void setCbserrorCode(String cbserrorCode) {
		this.cbserrorCode = cbserrorCode;
	}

	public void setCbsRRN(String cbsRRN) {
		this.cbsRRN = cbsRRN;
	}

	public void setChkTxnCount(Integer chkTxnCount) {
		this.chkTxnCount = chkTxnCount;
	}

	public void setCmserrorCode(String cmserrorCode) {
		this.cmserrorCode = cmserrorCode;
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

	public void setIdReqRespDebitCredit(Long idReqRespDebitCredit) {
		this.idReqRespDebitCredit = idReqRespDebitCredit;
	}

	public void setMberrorCode(String mberrorCode) {
		this.mberrorCode = mberrorCode;
	}

	public void setOrgRespCode(String orgRespCode) {
		this.orgRespCode = orgRespCode;
	}

	public void setPayerAddr(String payerAddr) {
		this.payerAddr = payerAddr;
	}

	public void setPayerCode(String payerCode) {
		this.payerCode = payerCode;
	}

	/**
	 * @param payerHandal
	 *            the payerHandal to set
	 */
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

	public void setRev(Integer rev) {
		this.rev = rev;
	}

	public void setRevCbserrorCode(String revCbserrorCode) {
		this.revCbserrorCode = revCbserrorCode;
	}

	public void setRevCBSrrn(String revCBSrrn) {
		this.revCBSrrn = revCBSrrn;
	}

	public void setRevChkTxnCount(Integer revChkTxnCount) {
		this.revChkTxnCount = revChkTxnCount;
	}

	public void setRevHeadMsgId(String revHeadMsgId) {
		this.revHeadMsgId = revHeadMsgId;
	}

	public void setRevMberrorCode(String revMberrorCode) {
		this.revMberrorCode = revMberrorCode;
	}

	public void setRevRefAcNum(String revRefAcNum) {
		this.revRefAcNum = revRefAcNum;
	}

	public void setRevRefAddr(String revRefAddr) {
		this.revRefAddr = revRefAddr;
	}

	public void setRevRefApprovalNum(String revRefApprovalNum) {
		this.revRefApprovalNum = revRefApprovalNum;
	}

	public void setRevRefOrgAmount(String revRefOrgAmount) {
		this.revRefOrgAmount = revRefOrgAmount;
	}

	public void setRevRefRegName(String revRefRegName) {
		this.revRefRegName = revRefRegName;
	}

	public void setRevRefRespCode(String revRefRespCode) {
		this.revRefRespCode = revRefRespCode;
	}

	public void setRevRefReversalRespCode(String revRefReversalRespCode) {
		this.revRefReversalRespCode = revRefReversalRespCode;
	}

	public void setRevRefSeqNum(String revRefSeqNum) {
		this.revRefSeqNum = revRefSeqNum;
	}

	public void setRevRefSettAmount(String revRefSettAmount) {
		this.revRefSettAmount = revRefSettAmount;
	}

	public void setRevRefSettCurrency(String revRefSettCurrency) {
		this.revRefSettCurrency = revRefSettCurrency;
	}

	public void setRevRefType(String revRefType) {
		this.revRefType = revRefType;
	}

	public void setRevReqInsert(Date revReqInsert) {
		this.revReqInsert = revReqInsert;
	}

	public void setRevRespErrCode(String revRespErrCode) {
		this.revRespErrCode = revRespErrCode;
	}

	public void setRevRespInsert(Date revRespInsert) {
		this.revRespInsert = revRespInsert;
	}

	public void setRevRespResult(String revRespResult) {
		this.revRespResult = revRespResult;
	}

	public void setRevTxnId(String revTxnId) {
		this.revTxnId = revTxnId;
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
		builder.append("Reqrespdebitcredit [");
		if (idReqRespDebitCredit != null) {
			builder.append("idReqRespDebitCredit=");
			builder.append(idReqRespDebitCredit);
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
		if (refType != null) {
			builder.append("refType=");
			builder.append(refType);
			builder.append(", ");
		}
		if (refSeqNum != null) {
			builder.append("refSeqNum=");
			builder.append(refSeqNum);
			builder.append(", ");
		}
		if (refAddr != null) {
			builder.append("refAddr=");
			builder.append(refAddr);
			builder.append(", ");
		}
		if (refRegName != null) {
			builder.append("refRegName=");
			builder.append(refRegName);
			builder.append(", ");
		}
		if (refSettAmount != null) {
			builder.append("refSettAmount=");
			builder.append(refSettAmount);
			builder.append(", ");
		}
		if (refSettCurrency != null) {
			builder.append("refSettCurrency=");
			builder.append(refSettCurrency);
			builder.append(", ");
		}
		if (refAcNum != null) {
			builder.append("refAcNum=");
			builder.append(refAcNum);
			builder.append(", ");
		}
		if (refApprovalNum != null) {
			builder.append("refApprovalNum=");
			builder.append(refApprovalNum);
			builder.append(", ");
		}
		if (refRespCode != null) {
			builder.append("refRespCode=");
			builder.append(refRespCode);
			builder.append(", ");
		}
		if (refReversalRespCode != null) {
			builder.append("refReversalRespCode=");
			builder.append(refReversalRespCode);
			builder.append(", ");
		}
		if (refOrgAmount != null) {
			builder.append("refOrgAmount=");
			builder.append(refOrgAmount);
			builder.append(", ");
		}
		if (cbserrorCode != null) {
			builder.append("cbserrorCode=");
			builder.append(cbserrorCode);
			builder.append(", ");
		}
		if (mberrorCode != null) {
			builder.append("mberrorCode=");
			builder.append(mberrorCode);
			builder.append(", ");
		}
		if (cmserrorCode != null) {
			builder.append("cmserrorCode=");
			builder.append(cmserrorCode);
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
		if (ackDebitCredit != null) {
			builder.append("ackDebitCredit=");
			builder.append(ackDebitCredit);
			builder.append(", ");
		}
		if (rev != null) {
			builder.append("rev=");
			builder.append(rev);
			builder.append(", ");
		}
		if (revHeadMsgId != null) {
			builder.append("revHeadMsgId=");
			builder.append(revHeadMsgId);
			builder.append(", ");
		}
		if (revTxnId != null) {
			builder.append("revTxnId=");
			builder.append(revTxnId);
			builder.append(", ");
		}
		if (revRespResult != null) {
			builder.append("revRespResult=");
			builder.append(revRespResult);
			builder.append(", ");
		}
		if (revRespErrCode != null) {
			builder.append("revRespErrCode=");
			builder.append(revRespErrCode);
			builder.append(", ");
		}
		if (revRefType != null) {
			builder.append("revRefType=");
			builder.append(revRefType);
			builder.append(", ");
		}
		if (revRefSeqNum != null) {
			builder.append("revRefSeqNum=");
			builder.append(revRefSeqNum);
			builder.append(", ");
		}
		if (revRefAddr != null) {
			builder.append("revRefAddr=");
			builder.append(revRefAddr);
			builder.append(", ");
		}
		if (revRefRegName != null) {
			builder.append("revRefRegName=");
			builder.append(revRefRegName);
			builder.append(", ");
		}
		if (revRefSettAmount != null) {
			builder.append("revRefSettAmount=");
			builder.append(revRefSettAmount);
			builder.append(", ");
		}
		if (revRefSettCurrency != null) {
			builder.append("revRefSettCurrency=");
			builder.append(revRefSettCurrency);
			builder.append(", ");
		}
		if (revRefAcNum != null) {
			builder.append("revRefAcNum=");
			builder.append(revRefAcNum);
			builder.append(", ");
		}
		if (revRefApprovalNum != null) {
			builder.append("revRefApprovalNum=");
			builder.append(revRefApprovalNum);
			builder.append(", ");
		}
		if (revRefRespCode != null) {
			builder.append("revRefRespCode=");
			builder.append(revRefRespCode);
			builder.append(", ");
		}
		if (revRefReversalRespCode != null) {
			builder.append("revRefReversalRespCode=");
			builder.append(revRefReversalRespCode);
			builder.append(", ");
		}
		if (revRefOrgAmount != null) {
			builder.append("revRefOrgAmount=");
			builder.append(revRefOrgAmount);
			builder.append(", ");
		}
		if (revCbserrorCode != null) {
			builder.append("revCbserrorCode=");
			builder.append(revCbserrorCode);
			builder.append(", ");
		}
		if (revMberrorCode != null) {
			builder.append("revMberrorCode=");
			builder.append(revMberrorCode);
			builder.append(", ");
		}
		if (revReqInsert != null) {
			builder.append("revReqInsert=");
			builder.append(revReqInsert);
			builder.append(", ");
		}
		if (revRespInsert != null) {
			builder.append("revRespInsert=");
			builder.append(revRespInsert);
			builder.append(", ");
		}
		if (orgRespCode != null) {
			builder.append("orgRespCode=");
			builder.append(orgRespCode);
			builder.append(", ");
		}
		if (ackRev != null) {
			builder.append("ackRev=");
			builder.append(ackRev);
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
		if (payerHandal != null) {
			builder.append("payerHandal=");
			builder.append(payerHandal);
			builder.append(", ");
		}
		if (chkTxnCount != null) {
			builder.append("chkTxnCount=");
			builder.append(chkTxnCount);
			builder.append(", ");
		}
		if (revChkTxnCount != null) {
			builder.append("revChkTxnCount=");
			builder.append(revChkTxnCount);
			builder.append(", ");
		}
		if (revCBSrrn != null) {
			builder.append("revCBSrrn=");
			builder.append(revCBSrrn);
			builder.append(", ");
		}
		if (cbsRRN != null) {
			builder.append("cbsRRN=");
			builder.append(cbsRRN);
		}
		builder.append("]");
		return builder.toString();
	}
}
