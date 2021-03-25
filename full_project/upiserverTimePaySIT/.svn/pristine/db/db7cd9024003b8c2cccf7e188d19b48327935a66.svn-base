package com.npst.upiserver.entity;


import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customertxns")
public class CustomerTxns {
	@Id
	@Column(name = "CUSTOMERTXNSID", unique = true, nullable = false)
	@GeneratedValue(strategy = IDENTITY)
	private Long CustomerTxnsId;
	@Column(name = "regId")
	private Long regId;
	@Column(name = "txnNote")
	private String txnNote = "";
	@Column(name = "txnType")
	private Integer txnType = 0;
	@Column(name = "txnId")
	private String txnId = "";
	@Column(name = "txncustRef")
	private String txncustRef = "";
	@Column(name = "collectExpiry")
	private String collectExpiry = "";
	@Column(name = "customerHistory")
	private String customerHistory = "";
	@Column(name = "status")
	private Integer status = 0;
	@Column(name = "errorcode")
	private String errorCode = "";
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
	@Column(name = "amount")
	private String amount = "";
	@Column(name = "reqDate")
	private Date reqDate;
	@Column(name = "respDate")
	private Date respDate;
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
	@Column(name = "payerBankName")
	private String payerBankName = "";
	@Column(name = "payeeBankName")
	private String payeeBankName = "";
	
	@Column(name = "mandateUmn")
	private String mandateUmn;
	
	@Column(name = "txnInitiationMode")
	private String txnInitiationMode;
	@Column(name = "txnPurpose")
	private String txnPurpose;

	@Column(name = "refUrl")
	private String refUrl = "";
	
	public String getAmount() {
		return amount;
	}

	public String getCollectExpiry() {
		return collectExpiry;
	}

	public String getCustomerHistory() {
		return customerHistory;
	}

	public Long getCustomerTxnsId() {
		return CustomerTxnsId;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getPayeeAccIFSC() {
		return payeeAccIFSC;
	}

	public String getPayeeAccNo() {
		return payeeAccNo;
	}

	public String getPayeeAcType() {
		return payeeAcType;
	}

	public String getPayeeBankName() {
		return payeeBankName;
	}

	public String getPayeeIin() {
		return payeeIin;
	}

	public String getPayeeMMID() {
		return payeeMMID;
	}

	public String getPayeeMobileNo() {
		return payeeMobileNo;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public String getPayeeType() {
		return payeeType;
	}

	public String getPayeeUidNum() {
		return payeeUidNum;
	}

	public String getPayeeVpa() {
		return payeeVpa;
	}

	public String getPayerAccIFSC() {
		return payerAccIFSC;
	}

	public String getPayerAccNo() {
		return payerAccNo;
	}

	public String getPayerAcType() {
		return payerAcType;
	}

	public String getPayerBankName() {
		return payerBankName;
	}

	public String getPayerIin() {
		return payerIin;
	}

	public String getPayerMMID() {
		return payerMMID;
	}

	public String getPayerMobileNo() {
		return payerMobileNo;
	}

	public String getPayerName() {
		return payerName;
	}

	public String getPayerType() {
		return payerType;
	}

	public String getPayerUidNum() {
		return payerUidNum;
	}

	public String getPayerVpa() {
		return payerVpa;
	}

	public Long getRegId() {
		return regId;
	}

	public Date getReqDate() {
		return reqDate;
	}

	public Date getRespDate() {
		return respDate;
	}

	public Integer getStatus() {
		return status;
	}

	public String getTxncustRef() {
		return txncustRef;
	}

	public String getTxnId() {
		return txnId;
	}

	public String getTxnNote() {
		return txnNote;
	}

	public Integer getTxnType() {
		return txnType;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public void setCollectExpiry(String collectExpiry) {
		this.collectExpiry = collectExpiry;
	}

	public void setCustomerHistory(String customerHistory) {
		this.customerHistory = customerHistory;
	}

	public void setCustomerTxnsId(Long customerTxnsId) {
		CustomerTxnsId = customerTxnsId;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public void setPayeeAccIFSC(String payeeAccIFSC) {
		this.payeeAccIFSC = payeeAccIFSC;
	}

	public void setPayeeAccNo(String payeeAccNo) {
		this.payeeAccNo = payeeAccNo;
	}

	public void setPayeeAcType(String payeeAcType) {
		this.payeeAcType = payeeAcType;
	}

	public void setPayeeBankName(String payeeBankName) {
		this.payeeBankName = payeeBankName;
	}

	public void setPayeeIin(String payeeIin) {
		this.payeeIin = payeeIin;
	}

	public void setPayeeMMID(String payeeMMID) {
		this.payeeMMID = payeeMMID;
	}

	public void setPayeeMobileNo(String payeeMobileNo) {
		this.payeeMobileNo = payeeMobileNo;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public void setPayeeType(String payeeType) {
		this.payeeType = payeeType;
	}

	public void setPayeeUidNum(String payeeUidNum) {
		this.payeeUidNum = payeeUidNum;
	}

	public void setPayeeVpa(String payeeVpa) {
		this.payeeVpa = payeeVpa;
	}

	public void setPayerAccIFSC(String payerAccIFSC) {
		this.payerAccIFSC = payerAccIFSC;
	}

	public void setPayerAccNo(String payerAccNo) {
		this.payerAccNo = payerAccNo;
	}

	public void setPayerAcType(String payerAcType) {
		this.payerAcType = payerAcType;
	}

	public void setPayerBankName(String payerBankName) {
		this.payerBankName = payerBankName;
	}

	public void setPayerIin(String payerIin) {
		this.payerIin = payerIin;
	}

	public void setPayerMMID(String payerMMID) {
		this.payerMMID = payerMMID;
	}

	public void setPayerMobileNo(String payerMobileNo) {
		this.payerMobileNo = payerMobileNo;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public void setPayerType(String payerType) {
		this.payerType = payerType;
	}

	public void setPayerUidNum(String payerUidNum) {
		this.payerUidNum = payerUidNum;
	}

	public void setPayerVpa(String payerVpa) {
		this.payerVpa = payerVpa;
	}

	public void setRegId(Long regId) {
		this.regId = regId;
	}

	public void setReqDate(Date reqDate) {
		this.reqDate = reqDate;
	}

	public void setRespDate(Date respDate) {
		this.respDate = respDate;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setTxncustRef(String txncustRef) {
		this.txncustRef = txncustRef;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public void setTxnNote(String txnNote) {
		this.txnNote = txnNote;
	}

	public void setTxnType(Integer txnType) {
		this.txnType = txnType;
	}

	public String getRefUrl() {
		return refUrl;
	}

	public void setRefUrl(String refUrl) {
		this.refUrl = refUrl;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CustomerTxns [");
		if (CustomerTxnsId != null) {
			builder.append("CustomerTxnsId=").append(CustomerTxnsId).append(", ");
		}
		if (regId != null) {
			builder.append("regId=").append(regId).append(", ");
		}
		if (txnNote != null) {
			builder.append("txnNote=").append(txnNote).append(", ");
		}
		if (txnType != null) {
			builder.append("txnType=").append(txnType).append(", ");
		}
		if (txnId != null) {
			builder.append("txnId=").append(txnId).append(", ");
		}
		if (txncustRef != null) {
			builder.append("txncustRef=").append(txncustRef).append(", ");
		}
		if (collectExpiry != null) {
			builder.append("collectExpiry=").append(collectExpiry).append(", ");
		}
		if (customerHistory != null) {
			builder.append("customerHistory=").append(customerHistory).append(", ");
		}
		if (status != null) {
			builder.append("status=").append(status).append(", ");
		}
		if (errorCode != null) {
			builder.append("errorCode=").append(errorCode).append(", ");
		}
		if (payerVpa != null) {
			builder.append("payerVpa=").append(payerVpa).append(", ");
		}
		if (payerName != null) {
			builder.append("payerName=").append(payerName).append(", ");
		}
		if (payerAccNo != null) {
			builder.append("payerAccNo=").append(payerAccNo).append(", ");
		}
		if (payerAccIFSC != null) {
			builder.append("payerAccIFSC=").append(payerAccIFSC).append(", ");
		}
		if (payerMobileNo != null) {
			builder.append("payerMobileNo=").append(payerMobileNo).append(", ");
		}
		if (payerMMID != null) {
			builder.append("payerMMID=").append(payerMMID).append(", ");
		}
		if (payerAcType != null) {
			builder.append("payerAcType=").append(payerAcType).append(", ");
		}
		if (payerBankName != null) {
			builder.append("payerBankName=").append(payerBankName).append(", ");
		}
		if (payeeName != null) {
			builder.append("payeeName=").append(payeeName).append(", ");
		}
		if (payeeVpa != null) {
			builder.append("payeeVpa=").append(payeeVpa).append(", ");
		}
		if (payeeAccNo != null) {
			builder.append("payeeAccNo=").append(payeeAccNo).append(", ");
		}
		if (payeeAccIFSC != null) {
			builder.append("payeeAccIFSC=").append(payeeAccIFSC).append(", ");
		}
		if (payeeMobileNo != null) {
			builder.append("payeeMobileNo=").append(payeeMobileNo).append(", ");
		}
		if (payeeMMID != null) {
			builder.append("payeeMMID=").append(payeeMMID).append(", ");
		}
		if (payeeAcType != null) {
			builder.append("payeeAcType=").append(payeeAcType).append(", ");
		}
		if (payeeBankName != null) {
			builder.append("payeeBankName=").append(payeeBankName).append(", ");
		}
		if (amount != null) {
			builder.append("amount=").append(amount).append(", ");
		}
		if (reqDate != null) {
			builder.append("reqDate=").append(reqDate).append(", ");
		}
		if (respDate != null) {
			builder.append("respDate=").append(respDate).append(", ");
		}
		if (payerType != null) {
			builder.append("payerType=").append(payerType).append(", ");
		}
		if (payeeType != null) {
			builder.append("payeeType=").append(payeeType).append(", ");
		}
		if (payeeUidNum != null) {
			builder.append("payeeUidNum=").append(payeeUidNum).append(", ");
		}
		if (payeeIin != null) {
			builder.append("payeeIin=").append(payeeIin).append(", ");
		}
		if (payerUidNum != null) {
			builder.append("payerUidNum=").append(payerUidNum).append(", ");
		}
		if (payerIin != null) {
			builder.append("payerIin=").append(payerIin);
		}
		builder.append("]");
		return builder.toString();
	}

	public String getMandateUmn() {
		return mandateUmn;
	}

	public void setMandateUmn(String mandateUmn) {
		this.mandateUmn = mandateUmn;
	}

	public String getTxnInitiationMode() {
		return txnInitiationMode;
	}

	public void setTxnInitiationMode(String txnInitiationMode) {
		this.txnInitiationMode = txnInitiationMode;
	}

	public String getTxnPurpose() {
		return txnPurpose;
	}

	public void setTxnPurpose(String txnPurpose) {
		this.txnPurpose = txnPurpose;
	}


}
