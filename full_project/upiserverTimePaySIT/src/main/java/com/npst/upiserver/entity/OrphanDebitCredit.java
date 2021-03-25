package com.npst.upiserver.entity;

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
@Table(name = "orphandebitcredit")
public class OrphanDebitCredit implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "idorphandebitcredit", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idOrphanDebitCredit;
	private String revHeadMsgId;
	private String revTxnId;
	private String revRespResult;
	private String revRespErrCode;
	private String revRefType;
	private String revRefSeqNum;
	private String revRefAddr;
	private String revRefRegName;
	private String revRefSettAmount;
	private String revRefSettCurrency;
	private String revRefAcNum;
	private String revRefApprovalNum;
	private String revRefRespCode;
	private String revRefReversalRespCode;
	private String revRefOrgAmount;
	private String revCbserrorCode;
	private String revMberrorCode;
	private Date revReqInsert;
	private Date revRespInsert;
	private String orgRespCode;
	private String ackRev;
	private Integer revChkTxnCount;
	private String revCBSrrn;
	@Column(name = "TxnType", length = 21)
	private String txnType;
	@Column(name = "OrgTxnId", length = 36)
	private String orgTxnId;

	@Column(name = "AckRev", length = 500)
	public String getAckRev() {
		return this.ackRev;
	}

	public Long getIdOrphanDebitCredit() {
		return idOrphanDebitCredit;
	}

	@Column(name = "OrgRespCode", length = 5)
	public String getOrgRespCode() {
		return this.orgRespCode;
	}

	public String getOrgTxnId() {
		return orgTxnId;
	}

	@Column(name = "RevCBSErrorCode", length = 6)
	public String getRevCbserrorCode() {
		return this.revCbserrorCode;
	}

	@Column(name = "RevCBSrrn", length = 13)
	public String getRevCBSrrn() {
		return revCBSrrn;
	}

	@Column(name = "RevChkTxnCount")
	public Integer getRevChkTxnCount() {
		return revChkTxnCount;
	}

	@Column(name = "RevHeadMsgId", length = 36)
	public String getRevHeadMsgId() {
		return this.revHeadMsgId;
	}

	@Column(name = "RevMBErrorCode", length = 6)
	public String getRevMberrorCode() {
		return this.revMberrorCode;
	}

	@Column(name = "RevRefAcNum", length = 21)
	public String getRevRefAcNum() {
		return this.revRefAcNum;
	}

	@Column(name = "RevRefAddr")
	public String getRevRefAddr() {
		return this.revRefAddr;
	}

	@Column(name = "RevRefApprovalNum", length = 7)
	public String getRevRefApprovalNum() {
		return this.revRefApprovalNum;
	}

	@Column(name = "RevRefOrgAmount", length = 10)
	public String getRevRefOrgAmount() {
		return this.revRefOrgAmount;
	}

	@Column(name = "RevRefRegName", length = 99)
	public String getRevRefRegName() {
		return this.revRefRegName;
	}

	@Column(name = "RevRefRespCode", length = 10)
	public String getRevRefRespCode() {
		return this.revRefRespCode;
	}

	@Column(name = "RevRefReversalRespCode", length = 5)
	public String getRevRefReversalRespCode() {
		return this.revRefReversalRespCode;
	}

	@Column(name = "RevRefSeqNum", length = 3)
	public String getRevRefSeqNum() {
		return this.revRefSeqNum;
	}

	@Column(name = "RevRefSettAmount", length = 21)
	public String getRevRefSettAmount() {
		return this.revRefSettAmount;
	}

	@Column(name = "RevRefSettCurrency", length = 5)
	public String getRevRefSettCurrency() {
		return this.revRefSettCurrency;
	}

	@Column(name = "RevRefType", length = 21)
	public String getRevRefType() {
		return this.revRefType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RevReqInsert", length = 19)
	public Date getRevReqInsert() {
		return this.revReqInsert;
	}

	@Column(name = "RevRespErrCode", length = 5)
	public String getRevRespErrCode() {
		return this.revRespErrCode;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RevRespInsert", length = 19)
	public Date getRevRespInsert() {
		return this.revRespInsert;
	}

	@Column(name = "RevRespResult", length = 21)
	public String getRevRespResult() {
		return this.revRespResult;
	}

	@Column(name = "RevTxnId", length = 36)
	public String getRevTxnId() {
		return this.revTxnId;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setAckRev(String ackRev) {
		this.ackRev = ackRev;
	}

	public void setIdOrphanDebitCredit(Long idOrphanDebitCredit) {
		this.idOrphanDebitCredit = idOrphanDebitCredit;
	}

	public void setOrgRespCode(String orgRespCode) {
		this.orgRespCode = orgRespCode;
	}

	public void setOrgTxnId(String orgTxnId) {
		this.orgTxnId = orgTxnId;
	}

	public void setRevCbserrorCode(String revCbserrorCode) {
		this.revCbserrorCode = revCbserrorCode;
	}

	public void setRevCBSrrn(String revCBSrrn) {
		this.revCBSrrn = revCBSrrn;
	}

	public void setRevChkTxnCount(Integer revChkTxnCount) {
		this.revChkTxnCount = revChkTxnCount;
	}

	public void setRevHeadMsgId(String revHeadMsgId) {
		this.revHeadMsgId = revHeadMsgId;
	}

	public void setRevMberrorCode(String revMberrorCode) {
		this.revMberrorCode = revMberrorCode;
	}

	public void setRevRefAcNum(String revRefAcNum) {
		this.revRefAcNum = revRefAcNum;
	}

	public void setRevRefAddr(String revRefAddr) {
		this.revRefAddr = revRefAddr;
	}

	public void setRevRefApprovalNum(String revRefApprovalNum) {
		this.revRefApprovalNum = revRefApprovalNum;
	}

	public void setRevRefOrgAmount(String revRefOrgAmount) {
		this.revRefOrgAmount = revRefOrgAmount;
	}

	public void setRevRefRegName(String revRefRegName) {
		this.revRefRegName = revRefRegName;
	}

	public void setRevRefRespCode(String revRefRespCode) {
		this.revRefRespCode = revRefRespCode;
	}

	public void setRevRefReversalRespCode(String revRefReversalRespCode) {
		this.revRefReversalRespCode = revRefReversalRespCode;
	}

	public void setRevRefSeqNum(String revRefSeqNum) {
		this.revRefSeqNum = revRefSeqNum;
	}

	public void setRevRefSettAmount(String revRefSettAmount) {
		this.revRefSettAmount = revRefSettAmount;
	}

	public void setRevRefSettCurrency(String revRefSettCurrency) {
		this.revRefSettCurrency = revRefSettCurrency;
	}

	public void setRevRefType(String revRefType) {
		this.revRefType = revRefType;
	}

	public void setRevReqInsert(Date revReqInsert) {
		this.revReqInsert = revReqInsert;
	}

	public void setRevRespErrCode(String revRespErrCode) {
		this.revRespErrCode = revRespErrCode;
	}

	public void setRevRespInsert(Date revRespInsert) {
		this.revRespInsert = revRespInsert;
	}

	public void setRevRespResult(String revRespResult) {
		this.revRespResult = revRespResult;
	}

	public void setRevTxnId(String revTxnId) {
		this.revTxnId = revTxnId;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

}