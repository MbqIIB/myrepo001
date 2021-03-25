package com.npst.mobileservice.object;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.npst.upi.hor.MandatesHistoryEntity;


public class MandatesHistory implements Serializable,Comparator<MandatesHistory> {

	private static final long serialVersionUID = 1L;
	private Long idMandatesHistory;
	private Long regId;
	private String txnNote = "";
	private String txnType;
	private String txnId = "";
	private String txncustRef = "";
	private String mandateExpiry = "";
	private String customerHistory = "";
	private Integer status = 0;
	private String errorCode = "";
	private String payerVpa = "";
	private String payerName = "";
	private String payerAccNo = "";
	private String payerAccIFSC = "";
	private String payerMobileNo = "";
	private String payerMMID = "";
	private String payerAcType = "";
	private String payerBankName = "";
	private String payeeName = "";
	private String payeeVpa = "";
	private String payeeAccNo = "";
	private String payeeAccIFSC = "";
	private String payeeMobileNo = "";
	private String payeeMMID = "";
	private String payeeAcType = "";
	private String payeeBankName = "";
	private Date reqDate;
	private Date respDate;
	private String payerType = "";
	private String payeeType = "";
	private String payeeUidNum = "";
	private String payeeIin = "";
	private String payerUidNum = "";
	private String payerIin = "";

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
	private String txnInitiationMode;
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
	
	public MandatesHistory(MandatesHistoryEntity mandates) {
		this.idMandatesHistory = mandates.getIdMandatesHistory();
		this.regId = mandates.getRegId();
		this.txnNote = mandates.getTxnNote();
		this.txnType = mandates.getTxnType();
		this.txnId = mandates.getTxnId();
		this.txncustRef = mandates.getTxncustRef();
		this.mandateExpiry = mandates.getMandateExpiry();
		this.customerHistory = mandates.getCustomerHistory();
		this.status = mandates.getStatus();
		this.errorCode = mandates.getErrorCode();
		this.payerVpa = mandates.getPayerVpa();
		this.payerName = mandates.getPayerName();
		this.payerAccNo = mandates.getPayerAccNo();
		this.payerAccIFSC =mandates.getPayerAccIFSC();
		this.payerMobileNo = mandates.getPayerMobileNo();
		this.payerMMID = mandates.getPayerMMID();
		this.payerAcType = mandates.getPayerAcType();
		this.payerBankName = mandates.getPayerBankName();
		this.payeeName = mandates.getPayeeName();
		this.payeeVpa = mandates.getPayeeVpa();
		this.payeeAccNo = mandates.getPayeeAccNo();
		this.payeeAccIFSC = mandates.getPayeeAccIFSC();
		this.payeeMobileNo = mandates.getPayeeMobileNo();
		this.payeeMMID = mandates.getPayeeMMID();
		this.payeeAcType = mandates.getPayeeType();
		this.payeeBankName = mandates.getPayeeBankName();
		this.reqDate = mandates.getReqDate();
		this.respDate = mandates.getRespDate();
		this.payerType = mandates.getPayerType();
		this.payeeType = mandates.getPayeeType();
		this.payeeUidNum = mandates.getPayeeUidNum();
		this.payeeIin = mandates.getPayeeIin();
		this.payerUidNum = mandates.getPayerUidNum();
		this.payerIin = mandates.getPayerIin();
		this.mandateName = mandates.getMandateName();
		this.mandateTxnId = mandates.getMandateTxnId();
		this.mandateUmn = mandates.getMandateUmn();
		this.mandateTs = mandates.getMandateTs();
		this.mandateRevokeable = mandates.getMandateRevokeable();
		this.mandateShareToPayee = mandates.getMandateShareToPayee();
		this.mandateType = mandates.getMandateType();
		this.mandateValidityStart = mandates.getMandateValidityStart();
		this.mandateValidityEnd = mandates.getMandateValidityEnd();
		this.mandateAmountvalue = mandates.getMandateAmountvalue();
		this.mandateAmountrule = mandates.getMandateAmountrule();
		this.mandateRecurrencepattern = mandates.getMandateRecurrencepattern();
		this.mandateRecurrenceRulevalue = mandates.getMandateRecurrenceRulevalue();
		this.mandateRecurrenceRuletype = mandates.getMandateRecurrenceRuletype();
		this.txnInitiatedBy = mandates.getTxnInitiatedBy();
		this.txnInitiationMode = mandates.getTxnInitiationMode();
		this.merchantSubCode = mandates.getMerchantSubCode();
		this.merchantMid = mandates.getMerchantMid();
		this.merchantSid = mandates.getMerchantSid();
		this.merchantTid = mandates.getMerchantTid();
		this.merchantType = mandates.getMerchantType();
		this.merchantGenre = mandates.getMerchantGenre();
		this.merchantOnboardingType = mandates.getMerchantOnboardingType();
		this.merchantBrandName = mandates.getMerchantBrandName();
		this.merchantLegalName = mandates.getMerchantLegalName();
		this.merchantFranchiseName = mandates.getMerchantFranchiseName();
		this.merchantOwnershipType = mandates.getMerchantOwnershipType();
		this.mandateRecurrencepattern=mandates.getMandateRecurrencepattern();
		this.mandateRecurrenceRuletype=mandates.getMandateRecurrenceRuletype();
	}
	public MandatesHistory() {
		// TODO Auto-generated constructor stub
	}
	public Long getIdMandatesHistory() {
		return idMandatesHistory;
	}
	public void setIdMandatesHistory(Long idMandatesHistory) {
		this.idMandatesHistory = idMandatesHistory;
	}
	public Long getRegId() {
		return regId;
	}
	public void setRegId(Long regId) {
		this.regId = regId;
	}
	public String getTxnNote() {
		return txnNote;
	}
	public void setTxnNote(String txnNote) {
		this.txnNote = txnNote;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	public String getTxncustRef() {
		return txncustRef;
	}
	public void setTxncustRef(String txncustRef) {
		this.txncustRef = txncustRef;
	}
	public String getMandateExpiry() {
		return mandateExpiry;
	}
	public void setMandateExpiry(String mandateExpiry) {
		this.mandateExpiry = mandateExpiry;
	}
	public String getCustomerHistory() {
		return customerHistory;
	}
	public void setCustomerHistory(String customerHistory) {
		this.customerHistory = customerHistory;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getPayerVpa() {
		return payerVpa;
	}
	public void setPayerVpa(String payerVpa) {
		this.payerVpa = payerVpa;
	}
	public String getPayerName() {
		return payerName;
	}
	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}
	public String getPayerAccNo() {
		return payerAccNo;
	}
	public void setPayerAccNo(String payerAccNo) {
		this.payerAccNo = payerAccNo;
	}
	public String getPayerAccIFSC() {
		return payerAccIFSC;
	}
	public void setPayerAccIFSC(String payerAccIFSC) {
		this.payerAccIFSC = payerAccIFSC;
	}
	public String getPayerMobileNo() {
		return payerMobileNo;
	}
	public void setPayerMobileNo(String payerMobileNo) {
		this.payerMobileNo = payerMobileNo;
	}
	public String getPayerMMID() {
		return payerMMID;
	}
	public void setPayerMMID(String payerMMID) {
		this.payerMMID = payerMMID;
	}
	public String getPayerAcType() {
		return payerAcType;
	}
	public void setPayerAcType(String payerAcType) {
		this.payerAcType = payerAcType;
	}
	public String getPayerBankName() {
		return payerBankName;
	}
	public void setPayerBankName(String payerBankName) {
		this.payerBankName = payerBankName;
	}
	public String getPayeeName() {
		return payeeName;
	}
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}
	public String getPayeeVpa() {
		return payeeVpa;
	}
	public void setPayeeVpa(String payeeVpa) {
		this.payeeVpa = payeeVpa;
	}
	public String getPayeeAccNo() {
		return payeeAccNo;
	}
	public void setPayeeAccNo(String payeeAccNo) {
		this.payeeAccNo = payeeAccNo;
	}
	public String getPayeeAccIFSC() {
		return payeeAccIFSC;
	}
	public void setPayeeAccIFSC(String payeeAccIFSC) {
		this.payeeAccIFSC = payeeAccIFSC;
	}
	public String getPayeeMobileNo() {
		return payeeMobileNo;
	}
	public void setPayeeMobileNo(String payeeMobileNo) {
		this.payeeMobileNo = payeeMobileNo;
	}
	public String getPayeeMMID() {
		return payeeMMID;
	}
	public void setPayeeMMID(String payeeMMID) {
		this.payeeMMID = payeeMMID;
	}
	public String getPayeeAcType() {
		return payeeAcType;
	}
	public void setPayeeAcType(String payeeAcType) {
		this.payeeAcType = payeeAcType;
	}
	public String getPayeeBankName() {
		return payeeBankName;
	}
	public void setPayeeBankName(String payeeBankName) {
		this.payeeBankName = payeeBankName;
	}
	public Date getReqDate() {
		return reqDate;
	}
	public void setReqDate(Date reqDate) {
		this.reqDate = reqDate;
	}
	public Date getRespDate() {
		return respDate;
	}
	public void setRespDate(Date respDate) {
		this.respDate = respDate;
	}
	public String getPayerType() {
		return payerType;
	}
	public void setPayerType(String payerType) {
		this.payerType = payerType;
	}
	public String getPayeeType() {
		return payeeType;
	}
	public void setPayeeType(String payeeType) {
		this.payeeType = payeeType;
	}
	public String getPayeeUidNum() {
		return payeeUidNum;
	}
	public void setPayeeUidNum(String payeeUidNum) {
		this.payeeUidNum = payeeUidNum;
	}
	public String getPayeeIin() {
		return payeeIin;
	}
	public void setPayeeIin(String payeeIin) {
		this.payeeIin = payeeIin;
	}
	public String getPayerUidNum() {
		return payerUidNum;
	}
	public void setPayerUidNum(String payerUidNum) {
		this.payerUidNum = payerUidNum;
	}
	public String getPayerIin() {
		return payerIin;
	}
	public void setPayerIin(String payerIin) {
		this.payerIin = payerIin;
	}
	public String getMandateName() {
		return mandateName;
	}
	public void setMandateName(String mandateName) {
		this.mandateName = mandateName;
	}
	public String getMandateTxnId() {
		return mandateTxnId;
	}
	public void setMandateTxnId(String mandateTxnId) {
		this.mandateTxnId = mandateTxnId;
	}
	public String getMandateUmn() {
		return mandateUmn;
	}
	public void setMandateUmn(String mandateUmn) {
		this.mandateUmn = mandateUmn;
	}
	public String getMandateTs() {
		return mandateTs;
	}
	public void setMandateTs(String mandateTs) {
		this.mandateTs = mandateTs;
	}
	public String getMandateRevokeable() {
		return mandateRevokeable;
	}
	public void setMandateRevokeable(String mandateRevokeable) {
		this.mandateRevokeable = mandateRevokeable;
	}
	public String getMandateShareToPayee() {
		return mandateShareToPayee;
	}
	public void setMandateShareToPayee(String mandateShareToPayee) {
		this.mandateShareToPayee = mandateShareToPayee;
	}
	public String getMandateType() {
		return mandateType;
	}
	public void setMandateType(String mandateType) {
		this.mandateType = mandateType;
	}
	public String getMandateValidityStart() {
		return mandateValidityStart;
	}
	public void setMandateValidityStart(String mandateValidityStart) {
		this.mandateValidityStart = mandateValidityStart;
	}
	public String getMandateValidityEnd() {
		return mandateValidityEnd;
	}
	public void setMandateValidityEnd(String mandateValidityEnd) {
		this.mandateValidityEnd = mandateValidityEnd;
	}
	public String getMandateAmountvalue() {
		return mandateAmountvalue;
	}
	public void setMandateAmountvalue(String mandateAmountvalue) {
		this.mandateAmountvalue = mandateAmountvalue;
	}
	public String getMandateAmountrule() {
		return mandateAmountrule;
	}
	public void setMandateAmountrule(String mandateAmountrule) {
		this.mandateAmountrule = mandateAmountrule;
	}
	public String getMandateRecurrencepattern() {
		return mandateRecurrencepattern;
	}
	public void setMandateRecurrencepattern(String mandateRecurrencepattern) {
		this.mandateRecurrencepattern = mandateRecurrencepattern;
	}
	public String getMandateRecurrenceRulevalue() {
		return mandateRecurrenceRulevalue;
	}
	public void setMandateRecurrenceRulevalue(String mandateRecurrenceRulevalue) {
		this.mandateRecurrenceRulevalue = mandateRecurrenceRulevalue;
	}
	public String getMandateRecurrenceRuletype() {
		return mandateRecurrenceRuletype;
	}
	public void setMandateRecurrenceRuletype(String mandateRecurrenceRuletype) {
		this.mandateRecurrenceRuletype = mandateRecurrenceRuletype;
	}
	public String getTxnInitiatedBy() {
		return txnInitiatedBy;
	}
	public void setTxnInitiatedBy(String txnInitiatedBy) {
		this.txnInitiatedBy = txnInitiatedBy;
	}
	public String getTxnInitiationMode() {
		return txnInitiationMode;
	}
	public void setTxnInitiationMode(String txnInitiationMode) {
		this.txnInitiationMode = txnInitiationMode;
	}
	public String getMerchantSubCode() {
		return merchantSubCode;
	}
	public void setMerchantSubCode(String merchantSubCode) {
		this.merchantSubCode = merchantSubCode;
	}
	public String getMerchantMid() {
		return merchantMid;
	}
	public void setMerchantMid(String merchantMid) {
		this.merchantMid = merchantMid;
	}
	public String getMerchantSid() {
		return merchantSid;
	}
	public void setMerchantSid(String merchantSid) {
		this.merchantSid = merchantSid;
	}
	public String getMerchantTid() {
		return merchantTid;
	}
	public void setMerchantTid(String merchantTid) {
		this.merchantTid = merchantTid;
	}
	public String getMerchantType() {
		return merchantType;
	}
	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}
	public String getMerchantGenre() {
		return merchantGenre;
	}
	public void setMerchantGenre(String merchantGenre) {
		this.merchantGenre = merchantGenre;
	}
	public String getMerchantOnboardingType() {
		return merchantOnboardingType;
	}
	public void setMerchantOnboardingType(String merchantOnboardingType) {
		this.merchantOnboardingType = merchantOnboardingType;
	}
	public String getMerchantBrandName() {
		return merchantBrandName;
	}
	public void setMerchantBrandName(String merchantBrandName) {
		this.merchantBrandName = merchantBrandName;
	}
	public String getMerchantLegalName() {
		return merchantLegalName;
	}
	public void setMerchantLegalName(String merchantLegalName) {
		this.merchantLegalName = merchantLegalName;
	}
	public String getMerchantFranchiseName() {
		return merchantFranchiseName;
	}
	public void setMerchantFranchiseName(String merchantFranchiseName) {
		this.merchantFranchiseName = merchantFranchiseName;
	}
	public String getMerchantOwnershipType() {
		return merchantOwnershipType;
	}
	public void setMerchantOwnershipType(String merchantOwnershipType) {
		this.merchantOwnershipType = merchantOwnershipType;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int compare(MandatesHistory o1, MandatesHistory o2) {
		if(o1.getReqDate().before(o2.getReqDate())) {
			return +1;
		}
		if(o1.getReqDate().after(o2.getReqDate())) {
			return -1;
		}
		else {
			return 0;
		}
			
		}
	
}

