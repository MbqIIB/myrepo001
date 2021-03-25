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
@Table(name = "reqrespvaladd")
public class ReqRespValAdd {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDREQRESPVALADD", unique = true, nullable = false)
	private Long idReqRespValAdd;
	@Column(name = "REQHEADTS", length = 30)
	private String reqHeadTs;
	@Column(name = "reqHeadOrgId", length = 21)
	private String reqHeadOrgId;
	@Column(name = "reqHeadMsgId", length = 36)
	private String reqHeadMsgId;
	@Column(name = "respHeadTs", length = 30)
	private String respHeadTs;
	@Column(name = "respHeadOrgId", length = 21)
	private String respHeadOrgId;
	@Column(name = "respHeadMsgId", length = 36)
	private String respHeadMsgId;
	@Column(name = "txnId", length = 36)
	private String txnId;
	@Column(name = "txnIdPrf", length = 3)
	private String txnIdPrf;
	@Column(name = "txnNote", length = 50)
	private String txnNote;
	@Column(name = "txnRefid", length = 36)
	private String txnRefid;
	@Column(name = "txnRefurl", length = 50)
	private String txnRefurl;
	@Column(name = "txnTs", length = 30)
	private String txnTs;
	@Column(name = "txnType", length = 45)
	private String txnType;
	@Column(name = "payerHandal", length = 20)
	private String payerHandal;
	@Column(name = "payerAddr", length = 256)
	private String payerAddr;
	@Column(name = "payerName", length = 99)
	private String payerName;
	@Column(name = "payerSeqNum")
	private String payerSeqNum;
	@Column(name = "payerType", length = 20)
	private String payerType;
	@Column(name = "payerCode", length = 5)
	private String payerCode;
	@Column(name = "infoIdType", length = 21)
	private String infoIdType;
	@Column(name = "infoId", length = 45)
	private String infoId;
	@Column(name = "infoIdVerifiedName", length = 100)
	private String infoIdVerifiedName;
	@Column(name = "infoIdRatingvaddr", length = 5)
	private String infoIdRatingvaddr;
	@Column(name = "respResult", length = 21)
	private String respResult;
	@Column(name = "respErrCode", length = 5)
	private String respErrCode;
	@Column(name = "maskName", length = 50)
	private String maskName;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "reqInsert", length = 19)
	private Date reqInsert;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "respInsert", length = 19)
	private Date respInsert;
	@Column(name = "ackReq", length = 1000)
	private String ackReq;
	@Column(name = "ackResp", length = 1000)
	private String ackResp;

	public String getAckReq() {
		return ackReq;
	}

	public String getAckResp() {
		return ackResp;
	}

	public Long getIdReqRespValAdd() {
		return idReqRespValAdd;
	}

	public String getInfoId() {
		return infoId;
	}

	public String getInfoIdRatingvaddr() {
		return infoIdRatingvaddr;
	}

	public String getInfoIdType() {
		return infoIdType;
	}

	public String getInfoIdVerifiedName() {
		return infoIdVerifiedName;
	}

	public String getMaskName() {
		return maskName;
	}

	public String getPayerAddr() {
		return payerAddr;
	}

	public String getPayerCode() {
		return payerCode;
	}

	public String getPayerHandal() {
		return payerHandal;
	}

	public String getPayerName() {
		return payerName;
	}

	public String getPayerSeqNum() {
		return payerSeqNum;
	}

	public String getPayerType() {
		return payerType;
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

	public void setIdReqRespValAdd(Long idReqRespValAdd) {
		this.idReqRespValAdd = idReqRespValAdd;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public void setInfoIdRatingvaddr(String infoIdRatingvaddr) {
		this.infoIdRatingvaddr = infoIdRatingvaddr;
	}

	public void setInfoIdType(String infoIdType) {
		this.infoIdType = infoIdType;
	}

	public void setInfoIdVerifiedName(String infoIdVerifiedName) {
		this.infoIdVerifiedName = infoIdVerifiedName;
	}

	public void setMaskName(String maskName) {
		this.maskName = maskName;
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
		builder.append("Reqrespvaladd [");
		if (idReqRespValAdd != 0) {
			builder.append("idReqRespValAdd=");
			builder.append(idReqRespValAdd);
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
		if (payerHandal != null) {
			builder.append("payerHandal=");
			builder.append(payerHandal);
			builder.append(", ");
		}
		if (payerAddr != null) {
			builder.append("payerAddr=");
			builder.append(payerAddr);
			builder.append(", ");
		}
		if (payerName != null) {
			builder.append("payerName=");
			builder.append(payerName);
			builder.append(", ");
		}
		if (payerSeqNum != null) {
			builder.append("payerSeqNum=");
			builder.append(payerSeqNum);
			builder.append(", ");
		}
		if (payerType != null) {
			builder.append("payerType=");
			builder.append(payerType);
			builder.append(", ");
		}
		if (payerCode != null) {
			builder.append("payerCode=");
			builder.append(payerCode);
			builder.append(", ");
		}
		if (infoIdType != null) {
			builder.append("infoIdType=");
			builder.append(infoIdType);
			builder.append(", ");
		}
		if (infoId != null) {
			builder.append("infoId=");
			builder.append(infoId);
			builder.append(", ");
		}
		if (infoIdVerifiedName != null) {
			builder.append("infoIdVerifiedName=");
			builder.append(infoIdVerifiedName);
			builder.append(", ");
		}
		if (infoIdRatingvaddr != null) {
			builder.append("infoIdRatingvaddr=");
			builder.append(infoIdRatingvaddr);
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
		if (maskName != null) {
			builder.append("maskName=");
			builder.append(maskName);
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

