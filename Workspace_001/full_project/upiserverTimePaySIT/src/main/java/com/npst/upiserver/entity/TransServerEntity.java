package com.npst.upiserver.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TRANS_SERVER")
public class TransServerEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "TXNID")
	private String txnId;

	@Column(name = "MSGID")
	private String msgId;

	@Column(name = "RRN")
	private String rrn;

	@Column(name = "INSERT_DATE")
	private Date insertDate;

	@Column(name = "PAYER_MOBILE_NO")
	private String payerMobileNo;

	@Column(name = "BENE_DETAILS")
	private String beneDetails;

	@Column(name = "ISSUER_ACQ_FLAG")
	private String issuerOrAcqFlag;

	@Column(name = "INTRA_FLAG")
	private String intraFlag;

	@Column(name = "DCID")
	private String dcId;

	@Column(name = "PAYER_PSPCODE")
	private String payerPspCode;

	@Column(name = "PAYEE_PSPCODE")
	private String payeePspCode;

	@Column(name = "REMITTER_BANKCODE")
	private String remitterBankCode;

	@Column(name = "BENE_BANKCODE")
	private String beneBankCode;

	@Column(name = "MERCHANT_ID")
	private String merchantId;

	@Column(name = "AGGREGATOR_CODE")
	private String aggregatorCode;

	@Column(name = "CBS_RESP_CODE")
	private String cbsRespCode;

	@Column(name = "NPCI_RESP_CODE")
	private String npciRespCode;

	@Column(name = "SPLIT_TXN_FLAG")
	private String splitTxnFlag;

	@Column(name = "RECORD_TYPE")
	private String recordType;

	@Column(name = "PAYER_IFSC")
	private String payerIfsc;

	@Column(name = "PAYEE_IFSC")
	private String payeeIfsc;

	@Column(name = "PAYER_MCC_CODE")
	private String payerCode;

	@Column(name = "PAYEE_MCC_CODE")
	private String payeeCode;

	@Column(name = "PAYER_VPA")
	private String payerVpa;

	@Column(name = "PAYEE_VPA")
	private String payeeVpa;

	@Column(name = "PAYER_ACCOUNT")
	private String payerAccount;

	@Column(name = "PAYEE_ACCOUNT")
	private String payeeAccount;

	@Column(name = "PAYER_NAME")
	private String payerName;

	@Column(name = "PAYEE_NAME")
	private String payeeName;

	@Column(name = "TXN_TYPE")
	private String txnType;

	@Column(name = "AMOUNT_PAISA")
	private Long amount;

	@Column(name = "OPERATION")
	private String operation;

	@Column(name = "SUB_OPERATION")
	private String subOperation;

	@Column(name = "ISO_ID")
	private String isoId;
	
	@Column(name = "NARRATION")
	private String narration;
	
	@Column(name = "INITIATION_MODE",length=10)
	private String initiationMode;
	
	@Column(name = "TXN_PURPOSE" ,length=10)
	private String txnPurpose;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getRrn() {
		return rrn;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public String getPayerMobileNo() {
		return payerMobileNo;
	}

	public void setPayerMobileNo(String payerMobileNo) {
		this.payerMobileNo = payerMobileNo;
	}

	public String getBeneDetails() {
		return beneDetails;
	}

	public void setBeneDetails(String beneDetails) {
		this.beneDetails = beneDetails;
	}

	public String getIssuerOrAcqFlag() {
		return issuerOrAcqFlag;
	}

	public void setIssuerOrAcqFlag(String issuerOrAcqFlag) {
		this.issuerOrAcqFlag = issuerOrAcqFlag;
	}

	public String getIntraFlag() {
		return intraFlag;
	}

	public void setIntraFlag(String intraFlag) {
		this.intraFlag = intraFlag;
	}

	public String getDcId() {
		return dcId;
	}

	public void setDcId(String dcId) {
		this.dcId = dcId;
	}

	public String getPayerPspCode() {
		return payerPspCode;
	}

	public void setPayerPspCode(String payerPspCode) {
		this.payerPspCode = payerPspCode;
	}

	public String getPayeePspCode() {
		return payeePspCode;
	}

	public void setPayeePspCode(String payeePspCode) {
		this.payeePspCode = payeePspCode;
	}

	public String getRemitterBankCode() {
		return remitterBankCode;
	}

	public void setRemitterBankCode(String remitterBankCode) {
		this.remitterBankCode = remitterBankCode;
	}

	public String getBeneBankCode() {
		return beneBankCode;
	}

	public void setBeneBankCode(String beneBankCode) {
		this.beneBankCode = beneBankCode;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getAggregatorCode() {
		return aggregatorCode;
	}

	public void setAggregatorCode(String aggregatorCode) {
		this.aggregatorCode = aggregatorCode;
	}

	public String getCbsRespCode() {
		return cbsRespCode;
	}

	public void setCbsRespCode(String cbsRespCode) {
		this.cbsRespCode = cbsRespCode;
	}

	public String getNpciRespCode() {
		return npciRespCode;
	}

	public void setNpciRespCode(String npciRespCode) {
		this.npciRespCode = npciRespCode;
	}

	public String getSplitTxnFlag() {
		return splitTxnFlag;
	}

	public void setSplitTxnFlag(String splitTxnFlag) {
		this.splitTxnFlag = splitTxnFlag;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getPayerIfsc() {
		return payerIfsc;
	}

	public void setPayerIfsc(String payerIfsc) {
		this.payerIfsc = payerIfsc;
	}

	public String getPayeeIfsc() {
		return payeeIfsc;
	}

	public void setPayeeIfsc(String payeeIfsc) {
		this.payeeIfsc = payeeIfsc;
	}

	public String getPayerCode() {
		return payerCode;
	}

	public void setPayerCode(String payerCode) {
		this.payerCode = payerCode;
	}

	public String getPayeeCode() {
		return payeeCode;
	}

	public void setPayeeCode(String payeeCode) {
		this.payeeCode = payeeCode;
	}

	public String getPayerVpa() {
		return payerVpa;
	}

	public void setPayerVpa(String payerVpa) {
		this.payerVpa = payerVpa;
	}

	public String getPayeeVpa() {
		return payeeVpa;
	}

	public void setPayeeVpa(String payeeVpa) {
		this.payeeVpa = payeeVpa;
	}

	public String getPayerAccount() {
		return payerAccount;
	}

	public void setPayerAccount(String payerAccount) {
		this.payerAccount = payerAccount;
	}

	public String getPayeeAccount() {
		return payeeAccount;
	}

	public void setPayeeAccount(String payeeAccount) {
		this.payeeAccount = payeeAccount;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getSubOperation() {
		return subOperation;
	}

	public void setSubOperation(String subOperation) {
		this.subOperation = subOperation;
	}

	public String getIsoId() {
		return isoId;
	}

	public void setIsoId(String isoId) {
		this.isoId = isoId;
	}

	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}

	public String getInitiationMode() {
		return initiationMode;
	}

	public void setInitiationMode(String initiationMode) {
		this.initiationMode = initiationMode;
	}

	public String getTxnPurpose() {
		return txnPurpose;
	}

	public void setTxnPurpose(String txnPurpose) {
		this.txnPurpose = txnPurpose;
	}
}
