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
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "reqrespbal")
public class ReqRespBal implements java.io.Serializable {
	
	private static final long	serialVersionUID	= 1L;
	private Long				idReqBal;
	private String				reqHeadTs;
	
	private String				reqHeadOrgId;
	private String				reqHeadMsgId;
	
	private String				respHeadTs;
	private String				respHeadOrgId;
	
	private String				respHeadMsgId;
	private String				txnId;
	private String				txnIdPrf;
	private String				txnNote;
	private String				txnRefid;
	private String				txnRefurl;
	private String				txnTs;
	private String				txnType;
	private String				payerAddr;
	private String				payerName;
	private Integer				payerSeqNum;
	private String				payerType;
	private String				payerCode;
	private String				infoIdType;
	private String				infoId;
	private String				infoIdVerifiedName;
	private String				infoIdRatingvaddr;
	private String				deviceMobile;
	private String				deviceGeocode;
	private String				deviceLocation;
	private String				deviceIp;
	private String				deviceType;
	private String				deviceId;
	private String				deviceOs;
	private String				deviceApp;
	private String				deviceCapability;
	private String				acAddrType;
	private String				acAddrTypeDetail1;
	private String				acAddrTypeDetail2;
	private String				acAddrTypeDetail3;
	private String				credType;
	private String				credSubType;
	private String				respResult;
	private String				respErrCode;
	private String				respBalData;
	private Date				reqInsertDate;
	private Date				resInsertDate;
	private String				ackReq;
	private String				ackResp;
	private String				payerHandal;
	
	public ReqRespBal() {
	}
	
	public ReqRespBal(String txnId) {
		this.txnId = txnId;
	}
	
	public ReqRespBal(String reqHeadTs, String reqHeadOrgId, String reqHeadMsgId, String respHeadTs,
			String respHeadOrgId, String respHeadMsgId, String txnId, String txnIdPrf, String txnNote, String txnRefid,
			String txnRefurl, String txnTs, String txnType, String payerAddr, String payerName, Integer payerSeqNum,
			String payerType, String payerCode, String infoIdType, String infoId, String infoIdVerifiedName,
			String infoIdRatingvaddr, String deviceMobile, String deviceGeocode, String deviceLocation, String deviceIp,
			String deviceType, String deviceId, String deviceOs, String deviceApp, String deviceCapability,
			String acAddrType, String acAddrTypeDetail1, String acAddrTypeDetail2, String acAddrTypeDetail3,
			String credType, String credSubType, String respResult, String respErrCode, String respBalData,
			Date reqInsertDate, Date resInsertDate, String ackReq, String ackResp,String payerHandal) {
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
		this.payerAddr = payerAddr;
		this.payerName = payerName;
		this.payerSeqNum = payerSeqNum;
		this.payerType = payerType;
		this.payerCode = payerCode;
		this.infoIdType = infoIdType;
		this.infoId = infoId;
		this.infoIdVerifiedName = infoIdVerifiedName;
		this.infoIdRatingvaddr = infoIdRatingvaddr;
		this.deviceMobile = deviceMobile;
		this.deviceGeocode = deviceGeocode;
		this.deviceLocation = deviceLocation;
		this.deviceIp = deviceIp;
		this.deviceType = deviceType;
		this.deviceId = deviceId;
		this.deviceOs = deviceOs;
		this.deviceApp = deviceApp;
		this.deviceCapability = deviceCapability;
		this.acAddrType = acAddrType;
		this.acAddrTypeDetail1 = acAddrTypeDetail1;
		this.acAddrTypeDetail2 = acAddrTypeDetail2;
		this.acAddrTypeDetail3 = acAddrTypeDetail3;
		this.credType = credType;
		this.credSubType = credSubType;
		this.respResult = respResult;
		this.respErrCode = respErrCode;
		this.respBalData = respBalData;
		this.reqInsertDate = reqInsertDate;
		this.resInsertDate = resInsertDate;
		this.ackReq = ackReq;
		this.ackResp = ackResp;
		this.payerHandal=payerHandal;
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
	
	@Column(name = "AckReq", length = 500)
	public String getAckReq() {
		return this.ackReq;
	}
	
	@Column(name = "AckResp", length = 500)
	public String getAckResp() {
		return this.ackResp;
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
	@Column(name = "IdReqBal", unique = true, nullable = false)
	public Long getIdReqBal() {
		return this.idReqBal;
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
	
	@Column(name = "PayerName", length = 99)
	public String getPayerName() {
		return this.payerName;
	}
	
	@Column(name = "PayerSeqNum")
	public Integer getPayerSeqNum() {
		return this.payerSeqNum;
	}
	
	@Column(name = "PayerType", length = 20)
	public String getPayerType() {
		return this.payerType;
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
	@Column(name = "ReqInsertDate", length = 19)
	public Date getReqInsertDate() {
		return this.reqInsertDate;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ResInsertDate", length = 19)
	public Date getResInsertDate() {
		return this.resInsertDate;
	}
	
	@Column(name = "RespBalData", length = 256)
	public String getRespBalData() {
		return this.respBalData;
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
	
	@Column(name = "RespResult", length = 21)
	public String getRespResult() {
		return this.respResult;
	}
	
	@Column(name = "TxnId", unique = true, nullable = false, length = 36)
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
	
	public void setAckReq(String ackReq) {
		this.ackReq = ackReq;
	}
	
	public void setAckResp(String ackResp) {
		this.ackResp = ackResp;
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
	
	public void setIdReqBal(Long idReqBal) {
		this.idReqBal = idReqBal;
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
	
	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}
	
	public void setPayerSeqNum(Integer payerSeqNum) {
		this.payerSeqNum = payerSeqNum;
	}
	
	public void setPayerType(String payerType) {
		this.payerType = payerType;
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
	
	public void setReqInsertDate(Date reqInsertDate) {
		this.reqInsertDate = reqInsertDate;
	}
	
	public void setResInsertDate(Date resInsertDate) {
		this.resInsertDate = resInsertDate;
	}
	
	public void setRespBalData(String respBalData) {
		this.respBalData = respBalData;
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
	
	public void setRespResult(String respResult) {
		this.respResult = respResult;
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
	@Column(name = "PayerHandal", length = 256)
	public String getPayerHandal() {
		return payerHandal;
	}

	public void setPayerHandal(String payerHandal) {
		this.payerHandal = payerHandal;
	}
	
	
}
