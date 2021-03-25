package com.npst.upiserver.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "reqrespdebitcreditpayees")
public class ReqRespDebitCreditPayeesEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long idpayeescol;
	private Long idReqRespDebitCredit;
	private String txnId;
	private String headMsgId;
	private String payeeHandal;
	private String payeeAddr;
	private String payeeName;
	private String payeeSeqNum;
	private String payeeType;
	private String payeeCode;
	private String infoIdType;
	private String infoId;
	private String infoIdVerifiedName;
	private String infoIdRatingvaddr;
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
	private String amountCrr;
	private String amountVal;

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

	@Column(name = "AmountCrr", length = 5)
	public String getAmountCrr() {
		return this.amountCrr;
	}

	@Column(name = "AmountVal", length = 21)
	public String getAmountVal() {
		return this.amountVal;
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

	@Column(name = "HeadMsgId", length = 36)
	public String getHeadMsgId() {
		return this.headMsgId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idpayeescol", unique = true, nullable = false)
	public Long getIdpayeescol() {
		return this.idpayeescol;
	}

	@Column(name = "idReqRespDebitCredit")
	public Long getIdReqRespDebitCredit() {
		return idReqRespDebitCredit;
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

	@Column(name = "PayeeAddr", length = 256)
	public String getPayeeAddr() {
		return this.payeeAddr;
	}

	@Column(name = "PayeeCode", length = 4)
	public String getPayeeCode() {
		return this.payeeCode;
	}

	@Column(name = "PayeeHandal", length = 256)
	public String getPayeeHandal() {
		return this.payeeHandal;
	}

	@Column(name = "PayeeName", length = 99)
	public String getPayeeName() {
		return this.payeeName;
	}

	@Column(name = "PayeeSeqNum", length = 3)
	public String getPayeeSeqNum() {
		return this.payeeSeqNum;
	}

	@Column(name = "PayeeType", length = 20)
	public String getPayeeType() {
		return this.payeeType;
	}

	@Column(name = "TxnId", length = 36)
	public String getTxnId() {
		return this.txnId;
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

	public void setHeadMsgId(String headMsgId) {
		this.headMsgId = headMsgId;
	}

	public void setIdpayeescol(Long idpayeescol) {
		this.idpayeescol = idpayeescol;
	}

	public void setIdReqRespDebitCredit(Long idReqRespDebitCredit) {
		this.idReqRespDebitCredit = idReqRespDebitCredit;
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

	public void setPayeeAddr(String payeeAddr) {
		this.payeeAddr = payeeAddr;
	}

	public void setPayeeCode(String payeeCode) {
		this.payeeCode = payeeCode;
	}

	public void setPayeeHandal(String payeeHandal) {
		this.payeeHandal = payeeHandal;
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

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReqrespdebitcreditPayees [");
		if (idpayeescol != null) {
			builder.append("idpayeescol=").append(idpayeescol).append(", ");
		}
		if (idReqRespDebitCredit != null) {
			builder.append("idReqRespDebitCredit=").append(idReqRespDebitCredit).append(", ");
		}
		if (txnId != null) {
			builder.append("txnId=").append(txnId).append(", ");
		}
		if (headMsgId != null) {
			builder.append("headMsgId=").append(headMsgId).append(", ");
		}
		if (payeeHandal != null) {
			builder.append("payeeHandal=").append(payeeHandal).append(", ");
		}
		if (payeeAddr != null) {
			builder.append("payeeAddr=").append(payeeAddr).append(", ");
		}
		if (payeeName != null) {
			builder.append("payeeName=").append(payeeName).append(", ");
		}
		if (payeeSeqNum != null) {
			builder.append("payeeSeqNum=").append(payeeSeqNum).append(", ");
		}
		if (payeeType != null) {
			builder.append("payeeType=").append(payeeType).append(", ");
		}
		if (payeeCode != null) {
			builder.append("payeeCode=").append(payeeCode).append(", ");
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
		if (amountCrr != null) {
			builder.append("amountCrr=").append(amountCrr).append(", ");
		}
		if (amountVal != null) {
			builder.append("amountVal=").append(amountVal);
		}
		builder.append("]");
		return builder.toString();
	}

}
