/*package com.npst.upiserver.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ORPHAN_DEBIT_CREDIT")
public class OrphanDebitCreditRevEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IdOrphanDebitCredit")
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Orphandebitcredit [");
		if (idOrphanDebitCredit != null) {
			builder.append("idOrphanDebitCredit=").append(idOrphanDebitCredit).append(", ");
		}
		if (revHeadMsgId != null) {
			builder.append("revHeadMsgId=").append(revHeadMsgId).append(", ");
		}
		if (revTxnId != null) {
			builder.append("revTxnId=").append(revTxnId).append(", ");
		}
		if (revRespResult != null) {
			builder.append("revRespResult=").append(revRespResult).append(", ");
		}
		if (revRespErrCode != null) {
			builder.append("revRespErrCode=").append(revRespErrCode).append(", ");
		}
		if (revRefType != null) {
			builder.append("revRefType=").append(revRefType).append(", ");
		}
		if (revRefSeqNum != null) {
			builder.append("revRefSeqNum=").append(revRefSeqNum).append(", ");
		}
		if (revRefAddr != null) {
			builder.append("revRefAddr=").append(revRefAddr).append(", ");
		}
		if (revRefRegName != null) {
			builder.append("revRefRegName=").append(revRefRegName).append(", ");
		}
		if (revRefSettAmount != null) {
			builder.append("revRefSettAmount=").append(revRefSettAmount).append(", ");
		}
		if (revRefSettCurrency != null) {
			builder.append("revRefSettCurrency=").append(revRefSettCurrency).append(", ");
		}
		if (revRefAcNum != null) {
			builder.append("revRefAcNum=").append(revRefAcNum).append(", ");
		}
		if (revRefApprovalNum != null) {
			builder.append("revRefApprovalNum=").append(revRefApprovalNum).append(", ");
		}
		if (revRefRespCode != null) {
			builder.append("revRefRespCode=").append(revRefRespCode).append(", ");
		}
		if (revRefReversalRespCode != null) {
			builder.append("revRefReversalRespCode=").append(revRefReversalRespCode).append(", ");
		}
		if (revRefOrgAmount != null) {
			builder.append("revRefOrgAmount=").append(revRefOrgAmount).append(", ");
		}
		if (revCbserrorCode != null) {
			builder.append("revCbserrorCode=").append(revCbserrorCode).append(", ");
		}
		if (revMberrorCode != null) {
			builder.append("revMberrorCode=").append(revMberrorCode).append(", ");
		}
		if (revReqInsert != null) {
			builder.append("revReqInsert=").append(revReqInsert).append(", ");
		}
		if (revRespInsert != null) {
			builder.append("revRespInsert=").append(revRespInsert).append(", ");
		}
		if (orgRespCode != null) {
			builder.append("orgRespCode=").append(orgRespCode).append(", ");
		}
		if (ackRev != null) {
			builder.append("ackRev=").append(ackRev).append(", ");
		}
		if (revChkTxnCount != null) {
			builder.append("revChkTxnCount=").append(revChkTxnCount).append(", ");
		}
		if (revCBSrrn != null) {
			builder.append("revCBSrrn=").append(revCBSrrn).append(", ");
		}
		if (txnType != null) {
			builder.append("txnType=").append(txnType).append(", ");
		}
		if (orgTxnId != null) {
			builder.append("orgTxnId=").append(orgTxnId);
		}
		builder.append("]");
		return builder.toString();
	}

}
*/