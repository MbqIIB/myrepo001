package com.npst.upiserver.entity;


import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "reqrespauthdetailspayees")
public class ReqRespAuthDetailsPayees implements java.io.Serializable {
	private static final long	serialVersionUID	= 1L;
	private Long				idpayeescol;
	private Long				idReqRespAuthDetails;
	private String				txnId;
	private String				headMsgId;
	private String				payeeHandal;
	private String				payeeAddr;
	private String				payeeName;
	private String				payeeSeqNum;
	private String				payeeType;
	private String				payeeCode;
	private String				infoIdType;
	private String				infoId;
	private String				infoIdVerifiedName;
	private String				infoIdRatingvaddr;
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
	private String				amountCrr;
	private String				amountVal;

	
	private String merchantType;
	private String mid;
	private String sid;
	private String tid;
	private String merachantName;
	private String  merchantGenreType;

	public ReqRespAuthDetailsPayees() {}

	public ReqRespAuthDetailsPayees(String txnId, String headMsgId, String payeeHandal, String payeeAddr,
			String payeeName, String payeeSeqNum, String payeeType, String payeeCode, String infoIdType, String infoId,
			String infoIdVerifiedName, String infoIdRatingvaddr, String acAddrType, String acAddrTypeDetail1,
			String acAddrTypeDetail2, String acAddrTypeDetail3, String deviceMobile, String deviceGeocode,
			String deviceLocation, String deviceIp, String deviceType, String deviceId, String deviceOs,
			String deviceApp, String deviceCapability, String amountCrr, String amountVal) {
		this.txnId = txnId;
		this.headMsgId = headMsgId;
		this.payeeHandal = payeeHandal;
		this.payeeAddr = payeeAddr;
		this.payeeName = payeeName;
		this.payeeSeqNum = payeeSeqNum;
		this.payeeType = payeeType;
		this.payeeCode = payeeCode;
		this.infoIdType = infoIdType;
		this.infoId = infoId;
		this.infoIdVerifiedName = infoIdVerifiedName;
		this.infoIdRatingvaddr = infoIdRatingvaddr;
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
		this.amountCrr = amountCrr;
		this.amountVal = amountVal;
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
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idpayeescol", unique = true, nullable = false)
	public Long getIdpayeescol() {
		return this.idpayeescol;
	}

	@Column(name = "idReqRespAuthDetails")
	public Long getIdReqRespAuthDetails() {
		return idReqRespAuthDetails;
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

	@Column(name = "PayeeHandal")
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

	@Column(name = "merchantGenreType", length = 21)
	public String getMerchantGenreType() {
		return merchantGenreType;
	}

	public void setMerchantGenreType(String merchantGenreType) {
		this.merchantGenreType = merchantGenreType;
	}
	@Column(name = "MerchantType", length = 10)
	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}
	@Column(name = "Mid", length = 45)
	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	@Column(name = "Sid", length = 45)

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}
	
	@Column(name = "Tid", length = 45)
	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}
	@Column(name = "MerchantName", length = 256)
	public String getMerachantName() {
		return merachantName;
	}

	public void setMerachantName(String merachantName) {
		this.merachantName = merachantName;
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

	public void setIdReqRespAuthDetails(Long idReqRespAuthDetails) {
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
		builder.append("ReqrespauthdetailsPayees [");
		if (idpayeescol != null) {
			builder.append("idpayeescol=");
			builder.append(idpayeescol);
			builder.append(", ");
		}
		if (idReqRespAuthDetails != null) {
			builder.append("idReqRespAuthDetails=");
			builder.append(idReqRespAuthDetails);
			builder.append(", ");
		}
		if (txnId != null) {
			builder.append("txnId=");
			builder.append(txnId);
			builder.append(", ");
		}
		if (headMsgId != null) {
			builder.append("headMsgId=");
			builder.append(headMsgId);
			builder.append(", ");
		}
		if (payeeHandal != null) {
			builder.append("payeeHandal=");
			builder.append(payeeHandal);
			builder.append(", ");
		}
		if (payeeAddr != null) {
			builder.append("payeeAddr=");
			builder.append(payeeAddr);
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
		if (payeeCode != null) {
			builder.append("payeeCode=");
			builder.append(payeeCode);
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
		}
		builder.append("]");
		return builder.toString();
	}

}
