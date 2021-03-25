package com.npst.upiserver.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "MANDATES_PAYEES_REQ_RESP")
public class ReqRespMandatesPayeesEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idMandatesPayees;
	private Long idMandates;
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
	private String deviceTelecom;
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
	private String payerDeviceTelecom;
	private String payeeDeviceTelecom;
	private String amountSplit;
	private String amountValue;

	private String merchantSubCode;
	private String merchantMid;
	private String merchantSid;
	private String merchantTid;
	private String merchantType;
	private String merchantGenre;
	private String merchantOnboardingType;
	private String merchantBrandName;
	private String merchantLegalName;
	private String merchantFranchiseName;
	private String merchantOwnershipType;

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

	public String getDeviceTelecom() {
		return deviceTelecom;
	}

	@Column(name = "DeviceType", length = 256)
	public String getDeviceType() {
		return this.deviceType;
	}

	@Column(name = "HeadMsgId")
	public String getHeadMsgId() {
		return headMsgId;
	}

	@Column(name = "IdMandates")
	public Long getIdMandates() {
		return this.idMandates;
	}

	@Id
	@Column(name = "IdMandatesPayees", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getIdMandatesPayees() {
		return idMandatesPayees;
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

	@Column(name = "PayeeAddr")
	public String getPayeeAddr() {
		return payeeAddr;
	}

	@Column(name = "PayeeCode")
	public String getPayeeCode() {
		return payeeCode;
	}

	@Column(name = "PayeeHandal")
	public String getPayeeHandal() {
		return payeeHandal;
	}

	@Column(name = "PayeeName")
	public String getPayeeName() {
		return payeeName;
	}

	@Column(name = "PayeeSeqNum")
	public String getPayeeSeqNum() {
		return payeeSeqNum;
	}

	@Column(name = "PayeeType")
	public String getPayeeType() {
		return payeeType;
	}

	@Column(name = "TxnId")
	public String getTxnId() {
		return txnId;
	}

	@Column(name = "MerchantSubCode")
	public String getMerchantSubCode() {
		return merchantSubCode;
	}

	@Column(name = "MerchantMid")
	public String getMerchantMid() {
		return merchantMid;
	}

	@Column(name = "MerchantSid")
	public String getMerchantSid() {
		return merchantSid;
	}

	@Column(name = "MerchantTid")
	public String getMerchantTid() {
		return merchantTid;
	}

	@Column(name = "MerchantType")
	public String getMerchantType() {
		return merchantType;
	}

	@Column(name = "MerchantGenre")
	public String getMerchantGenre() {
		return merchantGenre;
	}

	@Column(name = "MerchantOnboardingType")
	public String getMerchantOnboardingType() {
		return merchantOnboardingType;
	}

	@Column(name = "MerchantBrandName")
	public String getMerchantBrandName() {
		return merchantBrandName;
	}

	@Column(name = "MerchantLegalName")
	public String getMerchantLegalName() {
		return merchantLegalName;
	}

	@Column(name = "MerchantFranchiseName")
	public String getMerchantFranchiseName() {
		return merchantFranchiseName;
	}

	@Column(name = "MerchantOwnershipType")
	public String getMerchantOwnershipType() {
		return merchantOwnershipType;
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

	public void setAmountCrr(final String amountCrr) {
		this.amountCrr = amountCrr;
	}

	public void setAmountVal(final String amountVal) {
		this.amountVal = amountVal;
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

	public void setDeviceTelecom(final String deviceTelecom) {
		this.deviceTelecom = deviceTelecom;
	}

	public void setDeviceType(final String deviceType) {
		this.deviceType = deviceType;
	}

	public void setHeadMsgId(final String headMsgId) {
		this.headMsgId = headMsgId;
	}

	public void setIdMandates(final Long idMandates) {
		this.idMandates = idMandates;
	}

	public void setIdMandatesPayees(final Long idMandatesPayees) {
		this.idMandatesPayees = idMandatesPayees;
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

	public void setPayeeAddr(final String payeeAddr) {
		this.payeeAddr = payeeAddr;
	}

	public void setPayeeCode(final String payeeCode) {
		this.payeeCode = payeeCode;
	}

	public void setPayeeHandal(final String payeeHandal) {
		this.payeeHandal = payeeHandal;
	}

	public void setPayeeName(final String payeeName) {
		this.payeeName = payeeName;
	}

	public void setPayeeSeqNum(final String payeeSeqNum) {
		this.payeeSeqNum = payeeSeqNum;
	}

	public void setPayeeType(final String payeeType) {
		this.payeeType = payeeType;
	}

	public void setTxnId(final String txnId) {
		this.txnId = txnId;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("ReqRespMandatePayee [");
		if (idMandatesPayees != null) {
			builder.append("idpayeescol=").append(idMandatesPayees).append(", ");
		}
		if (idMandates != null) {
			builder.append("idReqRespMandate=").append(idMandates).append(", ");
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
			builder.append("mandateRecurrenceRuletype=").append(mandateRecurrenceRuletype);
		}
		builder.append("]");
		return builder.toString();
	}

	public void setMerchantSubCode(String merchantSubCode) {
		this.merchantSubCode = merchantSubCode;
	}

	public void setMerchantMid(String merchantMid) {
		this.merchantMid = merchantMid;
	}

	public void setMerchantSid(String merchantSid) {
		this.merchantSid = merchantSid;
	}

	public void setMerchantTid(String merchantTid) {
		this.merchantTid = merchantTid;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	public void setMerchantGenre(String merchantGenre) {
		this.merchantGenre = merchantGenre;
	}

	public void setMerchantOnboardingType(String merchantOnboardingType) {
		this.merchantOnboardingType = merchantOnboardingType;
	}

	public void setMerchantBrandName(String merchantBrandName) {
		this.merchantBrandName = merchantBrandName;
	}

	public void setMerchantLegalName(String merchantLegalName) {
		this.merchantLegalName = merchantLegalName;
	}

	public void setMerchantFranchiseName(String merchantFranchiseName) {
		this.merchantFranchiseName = merchantFranchiseName;
	}

	public void setMerchantOwnershipType(String merchantOwnershipType) {
		this.merchantOwnershipType = merchantOwnershipType;
	}

}
