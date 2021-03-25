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

@Entity
@Table(name = "customer_mandates")
public class CustomerMandatesEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	@Column(name = "regId")
	private Long regId;
	
	@Column(name = "reqDate")
	private Date reqDate;
	@Column(name = "respDate")
	private Date respDate;

	@Column(name = "txnType")
	private String txnType;
	@Column(name = "txnId")
	private String txnId = "";
	@Column(name = "txncustRef")
	private String txncustRef = "";

	/**
	 * Payer
	 */
	@Column(name = "payerVpa")
	private String payerVpa = "";
	@Column(name = "payerName")
	private String payerName = "";
	@Column(name = "payerAccNo")
	private String payerAccNo = "";
	@Column(name = "payerAccIFSC")
	private String payerAccIFSC = "";
	@Column(name = "payerMobileNo")
	private String payerMobileNo = "";
	@Column(name = "payerMMID")
	private String payerMMID = "";
	@Column(name = "payerAcType")
	private String payerAcType = "";
	@Column(name = "payerBankName")
	private String payerBankName = "";
	@Column(name = "payerCode")
	private String payerCode;
	@Column(name = "payerSeqNum")
	private String payerSeqNum;
	
	/**
	 * Payee
	 */
	@Column(name = "payeeName")
	private String payeeName = "";
	@Column(name = "payeeVpa")
	private String payeeVpa = "";
	@Column(name = "payeeAccNo")
	private String payeeAccNo = "";
	@Column(name = "payeeAccIFSC")
	private String payeeAccIFSC = "";
	@Column(name = "payeeMobileNo")
	private String payeeMobileNo = "";
	@Column(name = "payeeMMID")
	private String payeeMMID = "";
	@Column(name = "payeeAcType")
	private String payeeAcType = "";
	@Column(name = "payeeBankName")
	private String payeeBankName = "";
	@Column(name = "PayeeAddrType")
	private String payeeAddrType;
	@Column(name = "payeeCode")
	private String payeeCode;
	@Column(name = "payeeSeqNum")
	private String payeeSeqNum;
	@Column(name = "payeeCardNum")
	private String payeeCardNum;

	@Column(name = "payerType")
	private String payerType = "";
	@Column(name = "payeeType")
	private String payeeType = "";
	@Column(name = "payeeUidNum")
	private String payeeUidNum = "";
	@Column(name = "payeeIin")
	private String payeeIin = "";
	@Column(name = "payerUidNum")
	private String payerUidNum = "";
	@Column(name = "payerIin")
	private String payerIin = "";
	

	/**
	 * Mandate fields
	 */
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
	private String txnInitiatedBy;
	@Column(name="MandateSignId")
	private String mandateSignId;
	
	private String mandateSignValue;
	
	@Column(name="status")
	private Integer status;
	
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

	private String custis ;
	
	private String mandateNote;
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	@Column(name = "MandateNote")
	public String getMandateNote() {
		return mandateNote;
	}


	public void setMandateNote(String mandateNote) {
		this.mandateNote = mandateNote;
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

	public String getPayerName() {
		return this.payerName;
	}

	public Long getRegId() {
		return regId;
	}
	
	@Column(name = "TxnInitiatedBy", length = 99)
	public String getTxnInitiatedBy() {
		return txnInitiatedBy;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setMandateAmountrule(String mandateAmountrule) {
		this.mandateAmountrule = mandateAmountrule;
	}

	public void setMandateAmountvalue(String mandateAmountvalue) {
		this.mandateAmountvalue = mandateAmountvalue;
	}

	public void setMandateName(String mandateName) {
		this.mandateName = mandateName;
	}

	public void setMandateRecurrencepattern(String mandateRecurrencepattern) {
		this.mandateRecurrencepattern = mandateRecurrencepattern;
	}

	public void setMandateRecurrenceRuletype(String mandateRecurrenceRuletype) {
		this.mandateRecurrenceRuletype = mandateRecurrenceRuletype;
	}

	public void setMandateRecurrenceRulevalue(String mandateRecurrenceRulevalue) {
		this.mandateRecurrenceRulevalue = mandateRecurrenceRulevalue;
	}

	public void setMandateRevokeable(String mandateRevokeable) {
		this.mandateRevokeable = mandateRevokeable;
	}

	public void setMandateShareToPayee(String mandateShareToPayee) {
		this.mandateShareToPayee = mandateShareToPayee;
	}

	public void setMandateTs(String mandateTs) {
		this.mandateTs = mandateTs;
	}

	public void setMandateTxnId(String mandateTxnId) {
		this.mandateTxnId = mandateTxnId;
	}

	public void setMandateType(String mandateType) {
		this.mandateType = mandateType;
	}

	public void setMandateUmn(String mandateUmn) {
		this.mandateUmn = mandateUmn;
	}

	public void setMandateValidityEnd(String mandateValidityEnd) {
		this.mandateValidityEnd = mandateValidityEnd;
	}

	public void setMandateValidityStart(String mandateValidityStart) {
		this.mandateValidityStart = mandateValidityStart;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public void setRegId(Long regId) {
		this.regId = regId;
	}
	
	public void setTxnInitiatedBy(String txnInitiatedBy) {
		this.txnInitiatedBy = txnInitiatedBy;
	}

	public String getTxnType() {
		return txnType;
	}

	public String getTxnId() {
		return txnId;
	}

	public String getTxncustRef() {
		return txncustRef;
	}


	public String getPayerVpa() {
		return payerVpa;
	}

	public String getPayerAccNo() {
		return payerAccNo;
	}

	public String getPayerAccIFSC() {
		return payerAccIFSC;
	}

	public String getPayerMobileNo() {
		return payerMobileNo;
	}

	public String getPayerMMID() {
		return payerMMID;
	}

	public String getPayerAcType() {
		return payerAcType;
	}

	public String getPayerBankName() {
		return payerBankName;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public String getPayeeVpa() {
		return payeeVpa;
	}

	public String getPayeeAccNo() {
		return payeeAccNo;
	}

	public String getPayeeAccIFSC() {
		return payeeAccIFSC;
	}

	public String getPayeeMobileNo() {
		return payeeMobileNo;
	}

	public String getPayeeMMID() {
		return payeeMMID;
	}

	public String getPayeeAcType() {
		return payeeAcType;
	}

	public String getPayeeBankName() {
		return payeeBankName;
	}

	public Date getReqDate() {
		return reqDate;
	}

	public Date getRespDate() {
		return respDate;
	}

	public String getPayerType() {
		return payerType;
	}

	public String getPayeeType() {
		return payeeType;
	}

	public String getPayeeUidNum() {
		return payeeUidNum;
	}

	public String getPayeeIin() {
		return payeeIin;
	}

	public String getPayerUidNum() {
		return payerUidNum;
	}

	public String getPayerIin() {
		return payerIin;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public void setTxncustRef(String txncustRef) {
		this.txncustRef = txncustRef;
	}

	public void setPayerVpa(String payerVpa) {
		this.payerVpa = payerVpa;
	}

	public void setPayerAccNo(String payerAccNo) {
		this.payerAccNo = payerAccNo;
	}

	public void setPayerAccIFSC(String payerAccIFSC) {
		this.payerAccIFSC = payerAccIFSC;
	}

	public void setPayerMobileNo(String payerMobileNo) {
		this.payerMobileNo = payerMobileNo;
	}

	public void setPayerMMID(String payerMMID) {
		this.payerMMID = payerMMID;
	}

	public void setPayerAcType(String payerAcType) {
		this.payerAcType = payerAcType;
	}

	public void setPayerBankName(String payerBankName) {
		this.payerBankName = payerBankName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public void setPayeeVpa(String payeeVpa) {
		this.payeeVpa = payeeVpa;
	}

	public void setPayeeAccNo(String payeeAccNo) {
		this.payeeAccNo = payeeAccNo;
	}

	public void setPayeeAccIFSC(String payeeAccIFSC) {
		this.payeeAccIFSC = payeeAccIFSC;
	}

	public void setPayeeMobileNo(String payeeMobileNo) {
		this.payeeMobileNo = payeeMobileNo;
	}

	public void setPayeeMMID(String payeeMMID) {
		this.payeeMMID = payeeMMID;
	}

	public void setPayeeAcType(String payeeAcType) {
		this.payeeAcType = payeeAcType;
	}

	public void setPayeeBankName(String payeeBankName) {
		this.payeeBankName = payeeBankName;
	}

	public void setReqDate(Date reqDate) {
		this.reqDate = reqDate;
	}

	public void setRespDate(Date respDate) {
		this.respDate = respDate;
	}

	public void setPayerType(String payerType) {
		this.payerType = payerType;
	}

	public void setPayeeType(String payeeType) {
		this.payeeType = payeeType;
	}

	public void setPayeeUidNum(String payeeUidNum) {
		this.payeeUidNum = payeeUidNum;
	}

	public void setPayeeIin(String payeeIin) {
		this.payeeIin = payeeIin;
	}

	public void setPayerUidNum(String payerUidNum) {
		this.payerUidNum = payerUidNum;
	}

	public void setPayerIin(String payerIin) {
		this.payerIin = payerIin;
	}

	public String getMandateSignId() {
		return mandateSignId;
	}

	public void setMandateSignId(String mandateSignId) {
		this.mandateSignId = mandateSignId;
	}

	@Lob
	@Column(name="MandateSignValue")
	public String getMandateSignValue() {
		return mandateSignValue;
	}

	public void setMandateSignValue(String mandateSignValue) {
		this.mandateSignValue = mandateSignValue;
	}

	public String getPayeeAddrType() {
		return payeeAddrType;
	}

	public void setPayeeAddrType(String payeeAddrType) {
		this.payeeAddrType = payeeAddrType;
	}

	public String getPayerCode() {
		return payerCode;
	}

	public void setPayerCode(String payerCode) {
		this.payerCode = payerCode;
	}

	public String getPayerSeqNum() {
		return payerSeqNum;
	}

	public void setPayerSeqNum(String payerSeqNum) {
		this.payerSeqNum = payerSeqNum;
	}

	public String getPayeeCode() {
		return payeeCode;
	}

	public String getPayeeSeqNum() {
		return payeeSeqNum;
	}

	public void setPayeeCode(String payeeCode) {
		this.payeeCode = payeeCode;
	}

	public void setPayeeSeqNum(String payeeSeqNum) {
		this.payeeSeqNum = payeeSeqNum;
	}

	public String getPayeeCardNum() {
		return payeeCardNum;
	}

	public void setPayeeCardNum(String payeeCardNum) {
		this.payeeCardNum = payeeCardNum;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "CustIs")
	public String getCustis() {
		return custis;
	}



	public void setCustis(String custis) {
		this.custis = custis;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mandateUmn == null) ? 0 : mandateUmn.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerMandatesEntity other = (CustomerMandatesEntity) obj;
		if (mandateUmn == null) {
			if (other.mandateUmn != null)
				return false;
		} else if (!mandateUmn.equals(other.mandateUmn))
			return false;
		return true;
	}
	@Column(name="MerchantSubCode")
	public String getMerchantSubCode() {
		return merchantSubCode;
	}

	@Column(name="MerchantMid")
	public String getMerchantMid() {
		return merchantMid;
	}

	@Column(name="MerchantSid")
	public String getMerchantSid() {
		return merchantSid;
	}

	@Column(name="MerchantTid")
	public String getMerchantTid() {
		return merchantTid;
	}

	@Column(name="MerchantType")
	public String getMerchantType() {
		return merchantType;
	}

	@Column(name="MerchantGenre")
	public String getMerchantGenre() {
		return merchantGenre;
	}

	@Column(name="MerchantOnboardingType")
	public String getMerchantOnboardingType() {
		return merchantOnboardingType;
	}

	@Column(name="MerchantBrandName")
	public String getMerchantBrandName() {
		return merchantBrandName;
	}

	@Column(name="MerchantLegalName")
	public String getMerchantLegalName() {
		return merchantLegalName;
	}

	@Column(name="MerchantFranchiseName")
	public String getMerchantFranchiseName() {
		return merchantFranchiseName;
	}

	@Column(name="MerchantOwnershipType")
	public String getMerchantOwnershipType() {
		return merchantOwnershipType;
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
