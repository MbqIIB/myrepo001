package com.npst.upiserver.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "auditupixml")
public class Auditupixml implements java.io.Serializable {
	
	private Long	idauditupixml;
	private String	reqrespfull;
	private String	ackfull;
	private Date	date;
	private String txnType;
	private String reqOrResp;
	
	public Auditupixml() {}
	
	public Auditupixml(String reqrespfull, String ackfull, Date date) {
		this.reqrespfull = reqrespfull;
		this.ackfull = ackfull;
		this.date = date;
	}
	
	@Column(name = "ackfull")
	public String getAckfull() {
		return this.ackfull;
	}
	@Column(name = "txn_type")
	public String getTxnType() {
		return txnType;
	}
	
	@Column(name = "req_or_resp")
	public String getReqOrResp() {
		return reqOrResp;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "date", length = 10)
	public Date getDate() {
		return this.date;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idauditupixml", unique = true, nullable = false)
	public Long getIdauditupixml() {
		return this.idauditupixml;
	}
	
	@Column(name = "reqrespfull")
	public String getReqrespfull() {
		return this.reqrespfull;
	}
	
	public void setAckfull(String ackfull) {
		this.ackfull = ackfull;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public void setIdauditupixml(Long idauditupixml) {
		this.idauditupixml = idauditupixml;
	}
	
	public void setReqrespfull(String reqrespfull) {
		this.reqrespfull = reqrespfull;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public void setReqOrResp(String reqOrResp) {
		this.reqOrResp = reqOrResp;
	}
	
	
	
}