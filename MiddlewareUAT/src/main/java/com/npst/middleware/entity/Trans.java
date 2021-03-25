package com.npst.middleware.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "trans")
public class Trans implements Serializable {
	/**
	 *
	 */
	private static final long	serialVersionUID	= 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "trans_id")
	Integer						transId;
	@Column(name = "opration")
	String						opration;					// DEBIT/CREDIT
	@Column(name = "rrn")
	String						rrn;
	@Column(name = "txnId")
	String						txnId;
	@Column(name = "account_number")
	String						accNo;
	@Column(name = "ifsc")
	String						ifsc;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "request_date", length = 19)
	Date						req;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "response_date", length = 19)
	Date						resp;
	@Column(name = "request_string",length=1500)
	String						reqStr;
	@Column(name = "response_string",length=1500)
	String						respStr;
	@Column(name = "status")
	Integer						status;						// 1 pending /2 success /3 fail
	@Column(name = "check_txn_count")
	Integer						chkTxnCount;
	
	@Column(name = "cbs_response_code")
	String	cbsResponseCode;

	@Column(name = "is_reversal")
    Integer isReversal;
	
	@Column(name = "amount")
    Long amount;
	
	@Column(name = "txn_time")
    String txnTime;

	@Column(name = "crrn")
	String			crrn;
	
	@Column(name = "ref_id",length=55)
	String	refId;
	@Column(name = "ref_url",length=35)
	String refURL;
	@Column(name = "txn_note",length=100)
	String txnNote;
	
	@Column(name = "mobile_number",length=13) 
	String   mobileNumber;
	
	
	@Column(name="pl_account")
	String poolAccount;
	
	@Column(name="sub_type",length=50)
	String          subType;
	
	@Column(name="initiation_mode" , length=4)
	String initiationMode;
	
	@Column(name="txn_purpose" , length = 20)
	String txnPurpose;
	
	@Column(name="payer_type" , length = 20)
	String payerType;
	
	@Column(name = "account_number1")
	String						accNo1;
	
	@Column(name="payee_type" , length = 20)
	String payeeType;
	
	@Column(name="mcc_code" , length = 20)
	String mccCode;
	
	@Column(name="payer_ac_type" , length = 20)
	String			payerAcType;
	
	@Column(name="payee_ac_type" , length = 20)
	String			payeeAcType;
	
	@Column(name = "sub_Operation", length = 25)
	String subOperation;
	
	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getRefURL() {
		return refURL;
	}

	public void setRefURL(String refURL) {
		this.refURL = refURL;
	}

	public String getTxnNote() {
		return txnNote;
	}

	public void setTxnNote(String txnNote) {
		this.txnNote = txnNote;
	}

	public String getCrrn() {
		return crrn;
	}

	public void setCrrn(String crrn) {
		this.crrn = crrn;
	}

	public String getTxnTime() {
		return txnTime;
	}

	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}

	public String getSubOperation() {
		return subOperation;
	}

	public void setSubOperation(String subOperation) {
		this.subOperation = subOperation;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Integer getIsReversal() {
		return isReversal;
	}

	public void setIsReversal(Integer isReversal) {
		this.isReversal = isReversal;
	}

	public String getAccNo() {
		return accNo;
	}
	
	public Integer getChkTxnCount() {
		return chkTxnCount;
	}
	
	public String getIfsc() {
		return ifsc;
	}
	
	public String getOpration() {
		return opration;
	}
	
	public Date getReq() {
		return req;
	}
	
	public String getReqStr() {
		return reqStr;
	}
	
	public Date getResp() {
		return resp;
	}
	
	public String getRespStr() {
		return respStr;
	}
	
	public String getRrn() {
		return rrn;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public Integer getTransId() {
		return transId;
	}
	
	public String getTxnId() {
		return txnId;
	}
	
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	
	public void setChkTxnCount(Integer chkTxnCount) {
		this.chkTxnCount = chkTxnCount;
	}
	
	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}
	
	public void setOpration(String opration) {
		this.opration = opration;
	}
	
	public void setReq(Date req) {
		this.req = req;
	}
	
	public void setReqStr(String reqStr) {
		this.reqStr = reqStr;
	}
	
	public void setResp(Date resp) {
		this.resp = resp;
	}
	
	public void setRespStr(String respStr) {
		this.respStr = respStr;
	}
	
	public void setRrn(String rrn) {
		this.rrn = rrn;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public void setTransId(Integer transId) {
		this.transId = transId;
	}
	
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public String getCbsResponseCode() {
		return cbsResponseCode;
	}

	public void setCbsResponseCode(String cbsResponseCode) {
		this.cbsResponseCode = cbsResponseCode;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPoolAccount() {
		return poolAccount;
	}

	public void setPoolAccount(String poolAccount) {
		this.poolAccount = poolAccount;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
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

	

	public String getPayerType() {
		return payerType;
	}

	public void setPayerType(String payerType) {
		this.payerType = payerType;
	}


	public String getAccNo1() {
		return accNo1;
	}

	public void setAccNo1(String accNo1) {
		this.accNo1 = accNo1;
	}

	public String getPayerAcType() {
		return payerAcType;
	}

	public void setPayerAcType(String payerAcType) {
		this.payerAcType = payerAcType;
	}

	public String getPayeeAcType() {
		return payeeAcType;
	}

	public void setPayeeAcType(String payeeAcType) {
		this.payeeAcType = payeeAcType;
	}

	public String getPayeeType() {
		return payeeType;
	}

	public void setPayeeType(String payeeType) {
		this.payeeType = payeeType;
	}

	public String getMccCode() {
		return mccCode;
	}

	public void setMccCode(String mccCode) {
		this.mccCode = mccCode;
	}
	
}
