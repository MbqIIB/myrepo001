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
@Table(name = "reqresp_vaepspkeys_tab")
public class ReqRespVaePspKeysEntity implements Serializable {
	private static final long serialVersionUID = 4364732678930219795L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_reqresp_vaepspkeys")
	private Long id;
	@Column(name = "txn_id")
	private String txnId;
	@Column(name = "req_head_msgid", unique = true)
	private String reqHeadMsgId;
	@Column(name = "req_head_orgid")
	private String reqHeadOrgId;
	@Column(name = "req_head_ts")
	private String reqHeadTs;
	@Column(name = "req_date")
	private Date reqDate;
	@Column(name = "resp_date")
	private Date respDate;
	@Column(name = "resp_head_msgid")
	private String respHeadMsgId;
	@Column(name = "resp_head_orgid")
	private String respHeadOrgId;
	@Column(name = "resp_head_ts")
	private String respHeadTs;
	@Column(name = "txn_ts")
	private String txnTs;
	@Column(name = "txn_type")
	private String txnType;
	@Column(name = "result")
	private String result;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReqHeadMsgId() {
		return reqHeadMsgId;
	}

	public void setReqHeadMsgId(String reqHeadMsgId) {
		this.reqHeadMsgId = reqHeadMsgId;
	}

	public String getReqHeadOrgId() {
		return reqHeadOrgId;
	}

	public void setReqHeadOrgId(String reqHeadOrgId) {
		this.reqHeadOrgId = reqHeadOrgId;
	}

	public String getReqHeadTs() {
		return reqHeadTs;
	}

	public void setReqHeadTs(String reqHeadTs) {
		this.reqHeadTs = reqHeadTs;
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

	public String getRespHeadMsgId() {
		return respHeadMsgId;
	}

	public void setRespHeadMsgId(String respHeadMsgId) {
		this.respHeadMsgId = respHeadMsgId;
	}

	public String getRespHeadOrgId() {
		return respHeadOrgId;
	}

	public void setRespHeadOrgId(String respHeadOrgId) {
		this.respHeadOrgId = respHeadOrgId;
	}

	public String getRespHeadTs() {
		return respHeadTs;
	}

	public void setRespHeadTs(String respHeadTs) {
		this.respHeadTs = respHeadTs;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public String getTxnTs() {
		return txnTs;
	}

	public void setTxnTs(String txnTs) {
		this.txnTs = txnTs;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
