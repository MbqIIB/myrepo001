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
@Table(name = "reqresphbt")
public class ReqRespHbtEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDREQRESPHBT", unique = true, nullable = false)
	private Long idReqRespHbt;
	@Column(name = "REQHEADTS", length = 30)
	private String reqHeadTs;
	@Column(name = "REQHEADORGID", length = 21)
	private String reqHeadOrgId;
	@Column(name = "REQHEADMSGID", length = 36)
	private String reqHeadMsgId;
	@Column(name = "RESPHEADTS", length = 30)
	private String respHeadTs;
	@Column(name = "RESPHEADORGID", length = 21)
	private String respHeadOrgId;
	@Column(name = "RESPHEADMSGID", length = 36)
	private String respHeadMsgId;
	@Column(name = "TXNID", length = 36)
	private String txnId;
	@Column(name = "TXNIDPRF", length = 3)
	private String txnIdPrf;
	@Column(name = "TXNNOTE", length = 50)
	private String txnNote;
	@Column(name = "TXNREFID", length = 36)
	private String txnRefid;
	@Column(name = "TXNREFURL", length = 36)
	private String txnRefurl;
	@Column(name = "TXNTS", length = 30)
	private String txnTs;
	@Column(name = "TXNTYPE", length = 21)
	private String txnType;
	@Column(name = "HBTMSGTYPE", length = 50)
	private String hbtMsgType;
	@Column(name = "HBTMSGVALUE", length = 50)
	private String hbtMsgValue;
	@Column(name = "RESPRESULT", length = 21)
	private String respResult;
	@Column(name = "RESPERRCODE", length = 5)
	private String respErrCode;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REQINSERT", length = 19)
	private Date reqInsert;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RESPINSERT", length = 19)
	private Date respInsert;
	@Column(name = "ACKREQ", length = 1000)
	private String ackReq;
	@Column(name = "ACKRESP", length = 1000)
	private String ackResp;

	public String getAckReq() {
		return ackReq;
	}

	public String getAckResp() {
		return ackResp;
	}

	public String getHbtMsgType() {
		return hbtMsgType;
	}

	public String getHbtMsgValue() {
		return hbtMsgValue;
	}

	public Long getIdReqRespHbt() {
		return idReqRespHbt;
	}

	public String getReqHeadMsgId() {
		return reqHeadMsgId;
	}

	public String getReqHeadOrgId() {
		return reqHeadOrgId;
	}

	public String getReqHeadTs() {
		return reqHeadTs;
	}

	public Date getReqInsert() {
		return reqInsert;
	}

	public String getRespErrCode() {
		return respErrCode;
	}

	public String getRespHeadMsgId() {
		return respHeadMsgId;
	}

	public String getRespHeadOrgId() {
		return respHeadOrgId;
	}

	public String getRespHeadTs() {
		return respHeadTs;
	}

	public Date getRespInsert() {
		return respInsert;
	}

	public String getRespResult() {
		return respResult;
	}

	public String getTxnId() {
		return txnId;
	}

	public String getTxnIdPrf() {
		return txnIdPrf;
	}

	public String getTxnNote() {
		return txnNote;
	}

	public String getTxnRefid() {
		return txnRefid;
	}

	public String getTxnRefurl() {
		return txnRefurl;
	}

	public String getTxnTs() {
		return txnTs;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setAckReq(String ackReq) {
		this.ackReq = ackReq;
	}

	public void setAckResp(String ackResp) {
		this.ackResp = ackResp;
	}

	public void setHbtMsgType(String hbtMsgType) {
		this.hbtMsgType = hbtMsgType;
	}

	public void setHbtMsgValue(String hbtMsgValue) {
		this.hbtMsgValue = hbtMsgValue;
	}

	public void setIdReqRespHbt(Long idReqRespHbt) {
		this.idReqRespHbt = idReqRespHbt;
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

	public void setReqInsert(Date reqInsert) {
		this.reqInsert = reqInsert;
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

	public void setRespInsert(Date respInsert) {
		this.respInsert = respInsert;
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
		builder.append("Reqresphbt [");
		if (idReqRespHbt != null) {
			builder.append("idReqRespHbt=");
			builder.append(idReqRespHbt);
			builder.append(", ");
		}
		if (reqHeadTs != null) {
			builder.append("reqHeadTs=");
			builder.append(reqHeadTs);
			builder.append(", ");
		}
		if (reqHeadOrgId != null) {
			builder.append("reqHeadOrgId=");
			builder.append(reqHeadOrgId);
			builder.append(", ");
		}
		if (reqHeadMsgId != null) {
			builder.append("reqHeadMsgId=");
			builder.append(reqHeadMsgId);
			builder.append(", ");
		}
		if (respHeadTs != null) {
			builder.append("respHeadTs=");
			builder.append(respHeadTs);
			builder.append(", ");
		}
		if (respHeadOrgId != null) {
			builder.append("respHeadOrgId=");
			builder.append(respHeadOrgId);
			builder.append(", ");
		}
		if (respHeadMsgId != null) {
			builder.append("respHeadMsgId=");
			builder.append(respHeadMsgId);
			builder.append(", ");
		}
		if (txnId != null) {
			builder.append("txnId=");
			builder.append(txnId);
			builder.append(", ");
		}
		if (txnIdPrf != null) {
			builder.append("txnIdPrf=");
			builder.append(txnIdPrf);
			builder.append(", ");
		}
		if (txnNote != null) {
			builder.append("txnNote=");
			builder.append(txnNote);
			builder.append(", ");
		}
		if (txnRefid != null) {
			builder.append("txnRefid=");
			builder.append(txnRefid);
			builder.append(", ");
		}
		if (txnRefurl != null) {
			builder.append("txnRefurl=");
			builder.append(txnRefurl);
			builder.append(", ");
		}
		if (txnTs != null) {
			builder.append("txnTs=");
			builder.append(txnTs);
			builder.append(", ");
		}
		if (txnType != null) {
			builder.append("txnType=");
			builder.append(txnType);
			builder.append(", ");
		}
		if (hbtMsgType != null) {
			builder.append("hbtMsgType=");
			builder.append(hbtMsgType);
			builder.append(", ");
		}
		if (hbtMsgValue != null) {
			builder.append("hbtMsgValue=");
			builder.append(hbtMsgValue);
			builder.append(", ");
		}
		if (respResult != null) {
			builder.append("respResult=");
			builder.append(respResult);
			builder.append(", ");
		}
		if (respErrCode != null) {
			builder.append("respErrCode=");
			builder.append(respErrCode);
			builder.append(", ");
		}
		if (reqInsert != null) {
			builder.append("reqInsert=");
			builder.append(reqInsert);
			builder.append(", ");
		}
		if (respInsert != null) {
			builder.append("respInsert=");
			builder.append(respInsert);
			builder.append(", ");
		}
		if (ackReq != null) {
			builder.append("ackReq=");
			builder.append(ackReq);
			builder.append(", ");
		}
		if (ackResp != null) {
			builder.append("ackResp=");
			builder.append(ackResp);
		}
		builder.append("]");
		return builder.toString();
	}
}