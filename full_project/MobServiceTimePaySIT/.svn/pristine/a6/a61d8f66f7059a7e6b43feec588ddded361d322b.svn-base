package com.npst.upi.hor;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Table(name = "MANDATES")
@Entity
public class MandatesEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long idMandates;
	private String txnId;
	private String txnIdPrf;
	private String txnNote;
	private String txnRefid;
	private String txnType;
	private String txnCustRef;
	private String txnInitiationMode;
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
private String purpose;
	private String cbsMandateNo;
	private Date reqInsert;
	private Date respInsert;

	private String txnOrgId;
private String bidRefNo;

private String     unblockStatus; 

	@Column(name = "payeeHandal")
	private String payeeHandal;
	@Column(name = "payeeAddr")
	private String payeeAddr;
	@Column(name = "payeeName")
	private String payeeName;
	@Column(name = "payeeSeqNum")
	private String payeeSeqNum;
	@Column(name = "payeeType")
	private String payeeType;
	@Column(name = "payeeCode")
	private String payeeCode;
	@Column(name = "payeeinfoIdType")
	private String payeeinfoIdType;
	@Column(name = "payeeinfoId")
	private String payeeinfoId;
	@Column(name = "payeeinfoIdVerifiedName")
	private String payeeinfoIdVerifiedName;
	@Column(name = "payeeinfoIdRatingvaddr")
	private String payeeinfoIdRatingvaddr;
	@Column(name = "payeeacAddrType")
	private String payeeacAddrType;
	@Column(name = "payeeacAddrTypeDetail1")
	private String payeeacAddrTypeDetail1;
	@Column(name = "payeeacAddrTypeDetail2")
	private String payeeacAddrTypeDetail2;
	@Column(name = "payeeacAddrTypeDetail3")
	private String payeeacAddrTypeDetail3;

	@Column(name = "MandateSignId")
	private String mandateSignId;

	private String mandateSignValue;

	@Column(name = "merchantSubCode")
	private String merchantSubCode;
	@Column(name = "merchantMid")
	private String merchantMid;
	@Column(name = "merchantSid")
	private String merchantSid;
	@Column(name = "merchantTid")
	private String merchantTid;
	@Column(name = "merchantType")
	private String merchantType;
	@Column(name = "merchantGenre")
	private String merchantGenre;
	@Column(name = "merchantOnboardingType")
	private String merchantOnboardingType;
	@Column(name = "merchantBrandName")
	private String merchantBrandName;
	@Column(name = "merchantLegalName")
	private String merchantLegalName;
	@Column(name = "merchantFranchiseName")
	private String merchantFranchiseName;
	@Column(name = "merchantOwnershipType")
	private String merchantOwnershipType;
	@Column(name = "sign", length = 3900)
	private String sign;

	private Integer status;
	
	
	
	@Column(name = "unblockStatus")
	public String getUnblockStatus() {
		return unblockStatus;
	}


	public void setUnblockStatus(String unblockStatus) {
		this.unblockStatus = unblockStatus;
	}


	@Column(name = "cbsmandateno")
	public String getCbsMandateNo() {
		return cbsMandateNo;
	}


	public void setCbsMandateNo(String cbsMandateNo) {
		this.cbsMandateNo = cbsMandateNo;
	}

	
	@Column(name = "bidRefNo")
	public String getBidRefNo() {
		return bidRefNo;
	}

	public void setBidRefNo(String bidRefNo) {
		this.bidRefNo = bidRefNo;
	}

	@Column(name = "OrgId")
	public String getTxnOrgId() {
		return txnOrgId;
	}

	public void setTxnOrgId(String txnOrgId) {
		this.txnOrgId = txnOrgId;
	}

	@Column(name = "AcAddrType", length = 21)
	public String getAcAddrType() {
		return this.acAddrType;
	}

	@Column(name = "purpose")
	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
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

	@Column(name = "CredSubType", length = 21)
	public String getCredSubType() {
		return this.credSubType;
	}

	@Column(name = "CredType", length = 21)
	public String getCredType() {
		return this.credType;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IdMandates")
	public Long getIdMandates() {
		return this.idMandates;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ReqInsert", length = 19)
	public Date getReqInsert() {
		return this.reqInsert;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RespInsert", length = 19)
	public Date getRespInsert() {
		return this.respInsert;
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

	@Column(name = "TxnInitiationMode")
	public String getTxnInitiationMode() {
		return txnInitiationMode;
	}

	@Column(name = "TxnNote", length = 50)
	public String getTxnNote() {
		return this.txnNote;
	}

	@Column(name = "TxnRefid", length = 36)
	public String getTxnRefid() {
		return this.txnRefid;
	}

	@Column(name = "TxnType", length = 21)
	public String getTxnType() {
		return this.txnType;
	}
	
	@Column(name = "AmountBlock", length = 21)
	private String amountBlock;


	public String getAmountBlock() {
		return amountBlock;
	}

	public void setAmountBlock(String amountBlock) {
		this.amountBlock = amountBlock;
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

	public void setCredSubType(final String credSubType) {
		this.credSubType = credSubType;
	}

	public void setCredType(final String credType) {
		this.credType = credType;
	}

	public void setIdMandates(final Long idMandates) {
		this.idMandates = idMandates;
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

	public void setPayerAddr(final String payerAddr) {
		this.payerAddr = payerAddr;
	}

	public void setPayerCode(final String payerCode) {
		this.payerCode = payerCode;
	}

	public void setPayerHandal(final String payerHandal) {
		this.payerHandal = payerHandal;
	}

	public void setPayerName(final String payerName) {
		this.payerName = payerName;
	}

	public void setPayerSeqNum(final String payerSeqNum) {
		this.payerSeqNum = payerSeqNum;
	}

	public void setPayerType(final String payerType) {
		this.payerType = payerType;
	}

	public void setTxnCustRef(final String txnCustRef) {
		this.txnCustRef = txnCustRef;
	}

	public void setTxnId(final String txnId) {
		this.txnId = txnId;
	}

	public void setTxnIdPrf(final String txnIdPrf) {
		this.txnIdPrf = txnIdPrf;
	}

	public void setTxnInitiationMode(final String txnInitiationMode) {
		this.txnInitiationMode = txnInitiationMode;
	}

	public void setTxnNote(final String txnNote) {
		this.txnNote = txnNote;
	}

	public void setTxnRefid(final String txnRefid) {
		this.txnRefid = txnRefid;
	}

	public void setTxnType(final String txnType) {
		this.txnType = txnType;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Mandates [");
		if (idMandates != null) {
			builder.append("idReqRespMandate=").append(idMandates).append(", ");
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

		if (txnType != null) {
			builder.append("txnType=").append(txnType).append(", ");
		}
		if (txnCustRef != null) {
			builder.append("txnCustRef=").append(txnCustRef).append(", ");
		}
		if (txnInitiationMode != null) {
			builder.append("txnInitiationMode=").append(txnInitiationMode).append(", ");
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
			builder.append("mandateRecurrenceRuletype=").append(mandateRecurrenceRuletype).append(", ");
		}

		if (reqInsert != null) {
			builder.append("reqInsert=").append(reqInsert).append(", ");
		}
		if (respInsert != null) {
			builder.append("respInsert=").append(respInsert).append(", ");
		}

		builder.append("]");
		return builder.toString();
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMandateSignId() {
		return mandateSignId;
	}

	@Lob
	@Column(name = "MandateSignValue")
	public String getMandateSignValue() {
		return mandateSignValue;
	}

	public void setMandateSignId(String mandateSignId) {
		this.mandateSignId = mandateSignId;
	}

	public void setMandateSignValue(String mandateSignValue) {
		this.mandateSignValue = mandateSignValue;
	}

	public String getPayeeHandal() {
		return payeeHandal;
	}

	public String getPayeeAddr() {
		return payeeAddr;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public String getPayeeSeqNum() {
		return payeeSeqNum;
	}

	public String getPayeeType() {
		return payeeType;
	}

	public String getPayeeCode() {
		return payeeCode;
	}

	public String getPayeeinfoIdType() {
		return payeeinfoIdType;
	}

	public String getPayeeinfoId() {
		return payeeinfoId;
	}

	public String getPayeeinfoIdVerifiedName() {
		return payeeinfoIdVerifiedName;
	}

	public String getPayeeinfoIdRatingvaddr() {
		return payeeinfoIdRatingvaddr;
	}

	public String getPayeeacAddrType() {
		return payeeacAddrType;
	}

	public String getPayeeacAddrTypeDetail1() {
		return payeeacAddrTypeDetail1;
	}

	public String getPayeeacAddrTypeDetail2() {
		return payeeacAddrTypeDetail2;
	}

	public String getPayeeacAddrTypeDetail3() {
		return payeeacAddrTypeDetail3;
	}

	public String getMerchantSubCode() {
		return merchantSubCode;
	}

	public String getMerchantMid() {
		return merchantMid;
	}

	public String getMerchantSid() {
		return merchantSid;
	}

	public String getMerchantTid() {
		return merchantTid;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public String getMerchantGenre() {
		return merchantGenre;
	}

	public String getMerchantOnboardingType() {
		return merchantOnboardingType;
	}

	public String getMerchantBrandName() {
		return merchantBrandName;
	}

	public String getMerchantLegalName() {
		return merchantLegalName;
	}

	public String getMerchantFranchiseName() {
		return merchantFranchiseName;
	}

	public String getMerchantOwnershipType() {
		return merchantOwnershipType;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public void setReqInsert(Date reqInsert) {
		this.reqInsert = reqInsert;
	}

	public void setRespInsert(Date respInsert) {
		this.respInsert = respInsert;
	}

	public void setPayeeHandal(String payeeHandal) {
		this.payeeHandal = payeeHandal;
	}

	public void setPayeeAddr(String payeeAddr) {
		this.payeeAddr = payeeAddr;
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

	public void setPayeeCode(String payeeCode) {
		this.payeeCode = payeeCode;
	}

	public void setPayeeinfoIdType(String payeeinfoIdType) {
		this.payeeinfoIdType = payeeinfoIdType;
	}

	public void setPayeeinfoId(String payeeinfoId) {
		this.payeeinfoId = payeeinfoId;
	}

	public void setPayeeinfoIdVerifiedName(String payeeinfoIdVerifiedName) {
		this.payeeinfoIdVerifiedName = payeeinfoIdVerifiedName;
	}

	public void setPayeeinfoIdRatingvaddr(String payeeinfoIdRatingvaddr) {
		this.payeeinfoIdRatingvaddr = payeeinfoIdRatingvaddr;
	}

	public void setPayeeacAddrType(String payeeacAddrType) {
		this.payeeacAddrType = payeeacAddrType;
	}

	public void setPayeeacAddrTypeDetail1(String payeeacAddrTypeDetail1) {
		this.payeeacAddrTypeDetail1 = payeeacAddrTypeDetail1;
	}

	public void setPayeeacAddrTypeDetail2(String payeeacAddrTypeDetail2) {
		this.payeeacAddrTypeDetail2 = payeeacAddrTypeDetail2;
	}

	public void setPayeeacAddrTypeDetail3(String payeeacAddrTypeDetail3) {
		this.payeeacAddrTypeDetail3 = payeeacAddrTypeDetail3;
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
