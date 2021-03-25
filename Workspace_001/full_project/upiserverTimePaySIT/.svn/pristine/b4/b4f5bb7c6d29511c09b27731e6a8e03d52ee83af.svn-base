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
@Table(name = "reqresplistaccount")
public class ReqRespListAccount implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long idReqRespListAccount;
	private String reqHeadTs;
	private String reqHeadOrgId;
	private String reqHeadMsgId;
	private String respHeadTs;
	private String respHeadOrgId;
	private String respHeadMsgId;
	private String txnId;
	private String txnIdPrf;
	private String txnNote;
	private String txnRefid;
	private String txnRefurl;
	private String txnTs;
	private String txnType;
	private String link;
	private String payerAddr;
	private String payerName;
	private String payerSeqNum;
	private String payerType;
	private String payerCode;
	private String payerHandal;
	private String acAddrType;
	private String acAddrTypeDetail1;
	private String acAddrTypeDetail2;
	private String acAddrTypeDetail3;
	private String respResult;
	private String respErrCode;
	private String accountAccRefNumber;
	private String accountAccType;
	private String accountAeba;
	private String accountIfsc;
	private String accountMaskedAccnumber;
	private String accountMbeba;
	private String accountName;
	private String credsAllowedDlength;
	private String credsAllowedDtype;
	private String credsAllowedSubType;
	private String credsAllowedType;
	private Date reqInsertDate;
	private Date resInsertDate;
	private String ackReq;
	private String ackResp;

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

	@Column(name = "AccountAccRefNumber", length = 500)
	public String getAccountAccRefNumber() {
		return this.accountAccRefNumber;
	}

	@Column(name = "AccountAccType", length = 500)
	public String getAccountAccType() {
		return this.accountAccType;
	}

	@Column(name = "AccountAeba", length = 500)
	public String getAccountAeba() {
		return this.accountAeba;
	}

	@Column(name = "AccountIfsc", length = 500)
	public String getAccountIfsc() {
		return this.accountIfsc;
	}

	@Column(name = "AccountMaskedAccnumber", length = 500)
	public String getAccountMaskedAccnumber() {
		return this.accountMaskedAccnumber;
	}

	@Column(name = "AccountMbeba", length = 500)
	public String getAccountMbeba() {
		return this.accountMbeba;
	}

	@Column(name = "AccountName", length = 500)
	public String getAccountName() {
		return this.accountName;
	}

	@Column(name = "AckReq", length = 1000)
	public String getAckReq() {
		return this.ackReq;
	}

	@Column(name = "AckResp", length = 1000)
	public String getAckResp() {
		return this.ackResp;
	}

	@Column(name = "CredsAllowedDLength", length = 500)
	public String getCredsAllowedDlength() {
		return this.credsAllowedDlength;
	}

	@Column(name = "CredsAllowedDType", length = 500)
	public String getCredsAllowedDtype() {
		return this.credsAllowedDtype;
	}

	@Column(name = "CredsAllowedSubType", length = 500)
	public String getCredsAllowedSubType() {
		return this.credsAllowedSubType;
	}

	@Column(name = "CredsAllowedType", length = 500)
	public String getCredsAllowedType() {
		return this.credsAllowedType;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idReqRespListAccount")
	public Long getIdReqRespListAccount() {
		return this.idReqRespListAccount;
	}

	@Column(name = "Link", nullable = false, length = 50)
	public String getLink() {
		return this.link;
	}

	@Column(name = "PayerAddr", nullable = false, length = 256)
	public String getPayerAddr() {
		return this.payerAddr;
	}

	@Column(name = "PayerCode", length = 4)
	public String getPayerCode() {
		return this.payerCode;
	}

	@Column(name = "PayerHandal")
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

	@Column(name = "ReqHeadMsgId", length = 36)
	public String getReqHeadMsgId() {
		return this.reqHeadMsgId;
	}

	@Column(name = "ReqHeadOrgId", length = 21)
	public String getReqHeadOrgId() {
		return this.reqHeadOrgId;
	}

	@Column(name = "ReqHeadTs", length = 30)
	public String getReqHeadTs() {
		return this.reqHeadTs;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ReqInsertDate", length = 19)
	public Date getReqInsertDate() {
		return this.reqInsertDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ResInsertDate", length = 19)
	public Date getResInsertDate() {
		return this.resInsertDate;
	}

	@Column(name = "RespErrCode", length = 5)
	public String getRespErrCode() {
		return this.respErrCode;
	}

	@Column(name = "RespHeadMsgId", length = 36)
	public String getRespHeadMsgId() {
		return this.respHeadMsgId;
	}

	@Column(name = "RespHeadOrgId", length = 21)
	public String getRespHeadOrgId() {
		return this.respHeadOrgId;
	}

	@Column(name = "RespHeadTs", length = 30)
	public String getRespHeadTs() {
		return this.respHeadTs;
	}

	@Column(name = "RespResult", length = 21)
	public String getRespResult() {
		return this.respResult;
	}

	@Column(name = "TxnId", nullable = false, length = 36)
	public String getTxnId() {
		return this.txnId;
	}

	@Column(name = "TxnIdPrf", length = 3)
	public String getTxnIdPrf() {
		return this.txnIdPrf;
	}

	@Column(name = "TxnNote", length = 50)
	public String getTxnNote() {
		return this.txnNote;
	}

	@Column(name = "TxnRefid", length = 36)
	public String getTxnRefid() {
		return this.txnRefid;
	}

	@Column(name = "TxnRefurl", length = 36)
	public String getTxnRefurl() {
		return this.txnRefurl;
	}

	@Column(name = "TxnTs", length = 30)
	public String getTxnTs() {
		return this.txnTs;
	}

	@Column(name = "TxnType", length = 21)
	public String getTxnType() {
		return this.txnType;
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

	public void setAccountAccRefNumber(String accountAccRefNumber) {
		this.accountAccRefNumber = accountAccRefNumber;
	}

	public void setAccountAccType(String accountAccType) {
		this.accountAccType = accountAccType;
	}

	public void setAccountAeba(String accountAeba) {
		this.accountAeba = accountAeba;
	}

	public void setAccountIfsc(String accountIfsc) {
		this.accountIfsc = accountIfsc;
	}

	public void setAccountMaskedAccnumber(String accountMaskedAccnumber) {
		this.accountMaskedAccnumber = accountMaskedAccnumber;
	}

	public void setAccountMbeba(String accountMbeba) {
		this.accountMbeba = accountMbeba;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public void setAckReq(String ackReq) {
		this.ackReq = ackReq;
	}

	public void setAckResp(String ackResp) {
		this.ackResp = ackResp;
	}

	public void setCredsAllowedDlength(String credsAllowedDlength) {
		this.credsAllowedDlength = credsAllowedDlength;
	}

	public void setCredsAllowedDtype(String credsAllowedDtype) {
		this.credsAllowedDtype = credsAllowedDtype;
	}

	public void setCredsAllowedSubType(String credsAllowedSubType) {
		this.credsAllowedSubType = credsAllowedSubType;
	}

	public void setCredsAllowedType(String credsAllowedType) {
		this.credsAllowedType = credsAllowedType;
	}

	public void setIdReqRespListAccount(Long idReqRespListAccount) {
		this.idReqRespListAccount = idReqRespListAccount;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setPayerAddr(String payerAddr) {
		this.payerAddr = payerAddr;
	}

	public void setPayerCode(String payerCode) {
		this.payerCode = payerCode;
	}

	public void setPayerHandal(String payerHandal) {
		this.payerHandal = payerHandal;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public void setPayerSeqNum(String payerSeqNum) {
		this.payerSeqNum = payerSeqNum;
	}

	public void setPayerType(String payerType) {
		this.payerType = payerType;
	}

	public void setReqHeadMsgId(String reqHeadMsgId) {
		this.reqHeadMsgId = reqHeadMsgId;
	}

	public void setReqHeadOrgId(String reqHeadOrgId) {
		this.reqHeadOrgId = reqHeadOrgId;
	}

	public void setReqHeadTs(String reqHeadTs) {
		this.reqHeadTs = reqHeadTs;
	}

	public void setReqInsertDate(Date reqInsertDate) {
		this.reqInsertDate = reqInsertDate;
	}

	public void setResInsertDate(Date resInsertDate) {
		this.resInsertDate = resInsertDate;
	}

	public void setRespErrCode(String respErrCode) {
		this.respErrCode = respErrCode;
	}

	public void setRespHeadMsgId(String respHeadMsgId) {
		this.respHeadMsgId = respHeadMsgId;
	}

	public void setRespHeadOrgId(String respHeadOrgId) {
		this.respHeadOrgId = respHeadOrgId;
	}

	public void setRespHeadTs(String respHeadTs) {
		this.respHeadTs = respHeadTs;
	}

	public void setRespResult(String respResult) {
		this.respResult = respResult;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public void setTxnIdPrf(String txnIdPrf) {
		this.txnIdPrf = txnIdPrf;
	}

	public void setTxnNote(String txnNote) {
		this.txnNote = txnNote;
	}

	public void setTxnRefid(String txnRefid) {
		this.txnRefid = txnRefid;
	}

	public void setTxnRefurl(String txnRefurl) {
		this.txnRefurl = txnRefurl;
	}

	public void setTxnTs(String txnTs) {
		this.txnTs = txnTs;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Reqresplistaccount [");
		if (idReqRespListAccount != null) {
			builder.append("idReqRespListAccount=").append(idReqRespListAccount).append(", ");
		}
		if (reqHeadTs != null) {
			builder.append("reqHeadTs=").append(reqHeadTs).append(", ");
		}
		if (reqHeadOrgId != null) {
			builder.append("reqHeadOrgId=").append(reqHeadOrgId).append(", ");
		}
		if (reqHeadMsgId != null) {
			builder.append("reqHeadMsgId=").append(reqHeadMsgId).append(", ");
		}
		if (respHeadTs != null) {
			builder.append("respHeadTs=").append(respHeadTs).append(", ");
		}
		if (respHeadOrgId != null) {
			builder.append("respHeadOrgId=").append(respHeadOrgId).append(", ");
		}
		if (respHeadMsgId != null) {
			builder.append("respHeadMsgId=").append(respHeadMsgId).append(", ");
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
		if (txnRefurl != null) {
			builder.append("txnRefurl=").append(txnRefurl).append(", ");
		}
		if (txnTs != null) {
			builder.append("txnTs=").append(txnTs).append(", ");
		}
		if (txnType != null) {
			builder.append("txnType=").append(txnType).append(", ");
		}
		if (link != null) {
			builder.append("link=").append(link).append(", ");
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
		if (payerHandal != null) {
			builder.append("payerHandal=").append(payerHandal).append(", ");
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
		if (respResult != null) {
			builder.append("respResult=").append(respResult).append(", ");
		}
		if (respErrCode != null) {
			builder.append("respErrCode=").append(respErrCode).append(", ");
		}
		if (accountAccRefNumber != null) {
			builder.append("accountAccRefNumber=").append(accountAccRefNumber).append(", ");
		}
		if (accountAccType != null) {
			builder.append("accountAccType=").append(accountAccType).append(", ");
		}
		if (accountAeba != null) {
			builder.append("accountAeba=").append(accountAeba).append(", ");
		}
		if (accountIfsc != null) {
			builder.append("accountIfsc=").append(accountIfsc).append(", ");
		}
		if (accountMaskedAccnumber != null) {
			builder.append("accountMaskedAccnumber=").append(accountMaskedAccnumber).append(", ");
		}
		if (accountMbeba != null) {
			builder.append("accountMbeba=").append(accountMbeba).append(", ");
		}
		if (accountName != null) {
			builder.append("accountName=").append(accountName).append(", ");
		}
		if (credsAllowedDlength != null) {
			builder.append("credsAllowedDlength=").append(credsAllowedDlength).append(", ");
		}
		if (credsAllowedDtype != null) {
			builder.append("credsAllowedDtype=").append(credsAllowedDtype).append(", ");
		}
		if (credsAllowedSubType != null) {
			builder.append("credsAllowedSubType=").append(credsAllowedSubType).append(", ");
		}
		if (credsAllowedType != null) {
			builder.append("credsAllowedType=").append(credsAllowedType).append(", ");
		}
		if (reqInsertDate != null) {
			builder.append("reqInsertDate=").append(reqInsertDate).append(", ");
		}
		if (resInsertDate != null) {
			builder.append("resInsertDate=").append(resInsertDate).append(", ");
		}
		if (ackReq != null) {
			builder.append("ackReq=").append(ackReq).append(", ");
		}
		if (ackResp != null) {
			builder.append("ackResp=").append(ackResp);
		}
		builder.append("]");
		return builder.toString();
	}

}
