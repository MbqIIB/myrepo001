package com.npst.upiserver.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "payees")
public class Payees implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Long idpayeescol;
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
	/*@Column(name = "IdReqRespDebitCredit")
	private Long idReqRespDebitCredit;*/

	public Payees() {
	}

	public Payees(String txnId, String headMsgId, String payeeHandal, String payeeAddr, String payeeName,
			String payeeSeqNum, String payeeType, String payeeCode, String infoIdType, String infoId,
			String infoIdVerifiedName, String infoIdRatingvaddr, String acAddrType, String acAddrTypeDetail1,
			String acAddrTypeDetail2, String acAddrTypeDetail3, String deviceMobile, String deviceGeocode,
			String deviceLocation, String deviceIp, String deviceType, String deviceId, String deviceOs,
			String deviceApp, String deviceCapability, String amountCrr, String amountVal
			) {
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
		//this.idReqRespDebitCredit = idReqRespDebitCredit;
	}

	@Column(name = "acaddrtype", length = 21)
	public String getAcAddrType() {
		return this.acAddrType;
	}

	@Column(name = "acaddrtypedetail1", length = 256)
	public String getAcAddrTypeDetail1() {
		return this.acAddrTypeDetail1;
	}

	@Column(name = "acaddrtypedetail2", length = 256)
	public String getAcAddrTypeDetail2() {
		return this.acAddrTypeDetail2;
	}

	@Column(name = "acaddrtypedetail3", length = 256)
	public String getAcAddrTypeDetail3() {
		return this.acAddrTypeDetail3;
	}

	@Column(name = "amountcrr", length = 5)
	public String getAmountCrr() {
		return this.amountCrr;
	}

	@Column(name = "amountval", length = 21)
	public String getAmountVal() {
		return this.amountVal;
	}

	@Column(name = "deviceapp", length = 256)
	public String getDeviceApp() {
		return this.deviceApp;
	}

	@Column(name = "devicecapability", length = 256)
	public String getDeviceCapability() {
		return this.deviceCapability;
	}

	@Column(name = "devicegeocode", length = 256)
	public String getDeviceGeocode() {
		return this.deviceGeocode;
	}

	@Column(name = "deviceid", length = 256)
	public String getDeviceId() {
		return this.deviceId;
	}

	@Column(name = "deviceip", length = 256)
	public String getDeviceIp() {
		return this.deviceIp;
	}

	@Column(name = "devicelocation", length = 256)
	public String getDeviceLocation() {
		return this.deviceLocation;
	}

	@Column(name = "devicemobile", length = 256)
	public String getDeviceMobile() {
		return this.deviceMobile;
	}

	@Column(name = "deviceos", length = 256)
	public String getDeviceOs() {
		return this.deviceOs;
	}

	@Column(name = "devicetype", length = 256)
	public String getDeviceType() {
		return this.deviceType;
	}

	@Column(name = "headmsgid", length = 36)
	public String getHeadMsgId() {
		return this.headMsgId;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idpayeescol", unique = true, nullable = false)
	public Long getIdpayeescol() {
		return this.idpayeescol;
	}

	

	@Column(name = "infoid", length = 45)
	public String getInfoId() {
		return this.infoId;
	}

	@Column(name = "infoidratingvaddr", length = 5)
	public String getInfoIdRatingvaddr() {
		return this.infoIdRatingvaddr;
	}

	@Column(name = "infoidtype", length = 21)
	public String getInfoIdType() {
		return this.infoIdType;
	}

	@Column(name = "infoidverifiedname", length = 100)
	public String getInfoIdVerifiedName() {
		return this.infoIdVerifiedName;
	}

	@Column(name = "payeeaddr", length = 256)
	public String getPayeeAddr() {
		return this.payeeAddr;
	}

	@Column(name = "payeecode", length = 4)
	public String getPayeeCode() {
		return this.payeeCode;
	}

	@Column(name = "payeehandal")
	public String getPayeeHandal() {
		return this.payeeHandal;
	}

	@Column(name = "payeename", length = 99)
	public String getPayeeName() {
		return this.payeeName;
	}

	@Column(name = "payeeseqnum", length = 3)
	public String getPayeeSeqNum() {
		return this.payeeSeqNum;
	}

	@Column(name = "payeetype", length = 20)
	public String getPayeeType() {
		return this.payeeType;
	}

	@Column(name = "txnid", length = 36)
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
}