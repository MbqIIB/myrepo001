package com.npst.upiserver.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "reqrespotp")
public class ReqRespOtp implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Long idReqRespOtp;
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
	private String payerAddr;
	private String payerName;
	private String payerSeqNum;
	private String payerType;
	private String payerCode;
	private String acAddrType;
	private String acAddrTypeDetail1;
	private String acAddrTypeDetail2;
	private String acAddrTypeDetail3;
	private String deviceMobile;
	private String deviceGeocode;
	private String deviceLocation;
	private String deviceIp;
	private String deviceType;
	private String deviceId;
	private String deviceOs;
	private String deviceApp;
	private String deviceCapability;
	private String respResult;
	private String respErrCode;
	private Date reqInsertDate;
	private Date resInsertDate;
	private String ackReq;
	private String ackResp;
	private String payerHandal;

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
	@Column(name = "idReqRespOtp", unique = true, nullable = false)
	public Long getIdReqRespOtp() {
		return this.idReqRespOtp;
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

	public void setIdReqRespOtp(Long idReqRespOtp) {
		this.idReqRespOtp = idReqRespOtp;
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
		builder.append("Reqrespotp [");
		if (idReqRespOtp != null) {
			builder.append("idReqRespOtp=").append(idReqRespOtp).append(", ");
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
		if (respResult != null) {
			builder.append("respResult=").append(respResult).append(", ");
		}
		if (respErrCode != null) {
			builder.append("respErrCode=").append(respErrCode).append(", ");
		}
		if (reqInsertDate != null) {
			builder.append("reqInsertDate=").append(reqInsertDate).append(", ");
		}
		if (resInsertDate != null) {
			builder.append("resInsertDate=").append(resInsertDate).append(", ");
		}
		if (ackReq != null) {
			builder.append("ackReq=").append(ackReq).append(", ");
		}
		if (ackResp != null) {
			builder.append("ackResp=").append(ackResp).append(", ");
		}
		if (payerHandal != null) {
			builder.append("payerHandal=").append(payerHandal);
		}
		builder.append("]");
		return builder.toString();
	}

}
