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
@Table(name = "reqrespregmob")
public class ReqRespRegMob implements java.io.Serializable {

	private static final long	serialVersionUID	= 1L;
	private Long				idReqRespRegMob;
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
	private String				payerSeqNum;
	private String				payerType;
	private String				payerCode;
	private String				acAddrType;
	private String				acAddrTypeDetail1;
	private String				acAddrTypeDetail2;
	private String				acAddrTypeDetail3;
	private String				deviceMobile;
	private String				deviceGeocode;
	private String				deviceLocation;
	private String				deviceIp;
	private String				deviceType;
	private String				deviceId;
	private String				deviceOs;
	private String				deviceApp;
	private String				deviceCapability;
	private String				regDetailstype;
	private String				regDetail1;
	private String				regDetail2;
	private String				regDetail3;
	private String				credsubType;
	private String				credtype;
	private String				respResult;
	private String				respErrCode;
	private Date				reqInsertDate;
	private Date				resInsertDate;
	private String				ackReq;
	private String				ackResp;
	private String				payerHandal;

	public ReqRespRegMob() {}

	public ReqRespRegMob(String txnId) {
		this.txnId = txnId;
	}

	public ReqRespRegMob(String reqHeadTs, String reqHeadOrgId, String reqHeadMsgId, String respHeadTs,
			String respHeadOrgId, String respHeadMsgId, String txnId, String txnIdPrf, String txnNote, String txnRefid,
			String txnRefurl, String txnTs, String txnType, String payerAddr, String payerName, String payerSeqNum,
			String payerType, String payerCode, String acAddrType, String acAddrTypeDetail1, String acAddrTypeDetail2,
			String acAddrTypeDetail3, String deviceMobile, String deviceGeocode, String deviceLocation, String deviceIp,
			String deviceType, String deviceId, String deviceOs, String deviceApp, String deviceCapability,
			String regDetailstype, String regDetail1, String regDetail2, String regDetail3, String credsubType,
			String credtype, String respResult, String respErrCode, Date reqInsertDate, Date resInsertDate,
			String ackReq, String ackResp) {
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
		this.acAddrType = acAddrType;
		this.acAddrTypeDetail1 = acAddrTypeDetail1;
		this.acAddrTypeDetail2 = acAddrTypeDetail2;
		this.acAddrTypeDetail3 = acAddrTypeDetail3;
		this.deviceMobile = deviceMobile;
		this.deviceGeocode = deviceGeocode;
		this.deviceLocation = deviceLocation;
		this.deviceIp = deviceIp;
		this.deviceType = deviceType;
		this.deviceId = deviceId;
		this.deviceOs = deviceOs;
		this.deviceApp = deviceApp;
		this.deviceCapability = deviceCapability;
		this.regDetailstype = regDetailstype;
		this.regDetail1 = regDetail1;
		this.regDetail2 = regDetail2;
		this.regDetail3 = regDetail3;
		this.credsubType = credsubType;
		this.credtype = credtype;
		this.respResult = respResult;
		this.respErrCode = respErrCode;
		this.reqInsertDate = reqInsertDate;
		this.resInsertDate = resInsertDate;
		this.ackReq = ackReq;
		this.ackResp = ackResp;
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

	@Column(name = "AckReq", length = 1000)
	public String getAckReq() {
		return this.ackReq;
	}

	@Column(name = "AckResp", length = 1000)
	public String getAckResp() {
		return this.ackResp;
	}

	@Column(name = "CredsubType", length = 50)
	public String getCredsubType() {
		return this.credsubType;
	}

	@Column(name = "Credtype", length = 50)
	public String getCredtype() {
		return this.credtype;
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
	@Column(name = "idReqRespRegMob", unique = true, nullable = false)
	public Long getIdReqRespRegMob() {
		return this.idReqRespRegMob;
	}

	@Column(name = "PayerAddr", length = 256)
	public String getPayerAddr() {
		return this.payerAddr;
	}

	@Column(name = "PayerCode", length = 4)
	public String getPayerCode() {
		return this.payerCode;
	}

	@Column(name = "PayerHandal", length = 256)
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

	@Column(name = "RegDetail1", length = 50)
	public String getRegDetail1() {
		return this.regDetail1;
	}

	@Column(name = "RegDetail2", length = 50)
	public String getRegDetail2() {
		return this.regDetail2;
	}

	@Column(name = "RegDetail3", length = 50)
	public String getRegDetail3() {
		return this.regDetail3;
	}

	@Column(name = "RegDetailstype", length = 20)
	public String getRegDetailstype() {
		return this.regDetailstype;
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

	@Column(name = "TxnId", nullable = false, length = 36)
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

	public void setCredsubType(String credsubType) {
		this.credsubType = credsubType;
	}

	public void setCredtype(String credtype) {
		this.credtype = credtype;
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

	public void setIdReqRespRegMob(Long idReqRespRegMob) {
		this.idReqRespRegMob = idReqRespRegMob;
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

	public void setRegDetail1(String regDetail1) {
		this.regDetail1 = regDetail1;
	}

	public void setRegDetail2(String regDetail2) {
		this.regDetail2 = regDetail2;
	}

	public void setRegDetail3(String regDetail3) {
		this.regDetail3 = regDetail3;
	}

	public void setRegDetailstype(String regDetailstype) {
		this.regDetailstype = regDetailstype;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Reqrespregmob [");
		if (idReqRespRegMob != null) {
			builder.append("idReqRespRegMob=");
			builder.append(idReqRespRegMob);
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
		if (regDetailstype != null) {
			builder.append("regDetailstype=");
			builder.append(regDetailstype);
			builder.append(", ");
		}
		if (regDetail1 != null) {
			builder.append("regDetail1=");
			builder.append(regDetail1);
			builder.append(", ");
		}
		if (regDetail2 != null) {
			builder.append("regDetail2=");
			builder.append(regDetail2);
			builder.append(", ");
		}
		if (regDetail3 != null) {
			builder.append("regDetail3=");
			builder.append(regDetail3);
			builder.append(", ");
		}
		if (credsubType != null) {
			builder.append("credsubType=");
			builder.append(credsubType);
			builder.append(", ");
		}
		if (credtype != null) {
			builder.append("credtype=");
			builder.append(credtype);
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
		if (reqInsertDate != null) {
			builder.append("reqInsertDate=");
			builder.append(reqInsertDate);
			builder.append(", ");
		}
		if (resInsertDate != null) {
			builder.append("resInsertDate=");
			builder.append(resInsertDate);
			builder.append(", ");
		}
		if (ackReq != null) {
			builder.append("ackReq=");
			builder.append(ackReq);
			builder.append(", ");
		}
		if (ackResp != null) {
			builder.append("ackResp=");
			builder.append(ackResp);
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