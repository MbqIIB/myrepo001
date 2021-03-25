package com.npst.upiserver.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "reqresplistaccpvd")
public class ReqRespListAccPvdEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1646155245493198490L;
	private Long idreqresplistaccpvd;
	private String reqheadmsgid;
	private String reqheadorgid;
	private String reqheadts;
	private Date reqinsertdate;
	private Date resinsertdate;
	private String respheadmsgid;
	private String respheadorgid;
	private String respheadts;
	private String txnid;
	private String txnts;
	private String txntype;
	private Set<AccountProvidersEntity> accountproviderses = new HashSet<>(0);

	public ReqRespListAccPvdEntity() {
	}

	public ReqRespListAccPvdEntity(Long idreqresplistaccpvd) {
		this.idreqresplistaccpvd = idreqresplistaccpvd;
	}

	public ReqRespListAccPvdEntity(Long idreqresplistaccpvd, String reqheadmsgid, String reqheadorgid, String reqheadts,
			Date reqinsertdate, Date resinsertdate, String respheadmsgid, String respheadorgid, String respheadts,
			String txnid, String txnts, String txntype, String mobregformat, Set<AccountProvidersEntity> accountproviderses) {
		this.idreqresplistaccpvd = idreqresplistaccpvd;
		this.reqheadmsgid = reqheadmsgid;
		this.reqheadorgid = reqheadorgid;
		this.reqheadts = reqheadts;
		this.reqinsertdate = reqinsertdate;
		this.resinsertdate = resinsertdate;
		this.respheadmsgid = respheadmsgid;
		this.respheadorgid = respheadorgid;
		this.respheadts = respheadts;
		this.txnid = txnid;
		this.txnts = txnts;
		this.txntype = txntype;
		this.accountproviderses = accountproviderses;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "reqresplistaccpvd")
	public Set<AccountProvidersEntity> getAccountproviderses() {
		return this.accountproviderses;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDREQRESPLISTACCPVD", unique = true, nullable = false)
	public Long getIdreqresplistaccpvd() {
		return this.idreqresplistaccpvd;
	}

	@Column(name = "REQHEADMSGID", length = 36)
	public String getReqheadmsgid() {
		return this.reqheadmsgid;
	}

	@Column(name = "REQHEADORGID", length = 21)
	public String getReqheadorgid() {
		return this.reqheadorgid;
	}

	@Column(name = "REQHEADTS", length = 30)
	public String getReqheadts() {
		return this.reqheadts;
	}

	@Column(name = "REQINSERTDATE")
	public Date getReqinsertdate() {
		return this.reqinsertdate;
	}

	@Column(name = "RESINSERTDATE")
	public Date getResinsertdate() {
		return this.resinsertdate;
	}

	@Column(name = "RESPHEADMSGID", length = 36)
	public String getRespheadmsgid() {
		return this.respheadmsgid;
	}

	@Column(name = "RESPHEADORGID", length = 21)
	public String getRespheadorgid() {
		return this.respheadorgid;
	}

	@Column(name = "RESPHEADTS", length = 30)
	public String getRespheadts() {
		return this.respheadts;
	}

	@Column(name = "TXNID", length = 45)
	public String getTxnid() {
		return this.txnid;
	}

	@Column(name = "TXNTS", length = 45)
	public String getTxnts() {
		return this.txnts;
	}

	@Column(name = "TXNTYPE", length = 45)
	public String getTxntype() {
		return this.txntype;
	}

	public void setAccountproviderses(Set<AccountProvidersEntity> accountproviderses) {
		this.accountproviderses = accountproviderses;
	}

	public void setIdreqresplistaccpvd(Long idreqresplistaccpvd) {
		this.idreqresplistaccpvd = idreqresplistaccpvd;
	}

	public void setReqheadmsgid(String reqheadmsgid) {
		this.reqheadmsgid = reqheadmsgid;
	}

	public void setReqheadorgid(String reqheadorgid) {
		this.reqheadorgid = reqheadorgid;
	}

	public void setReqheadts(String reqheadts) {
		this.reqheadts = reqheadts;
	}

	public void setReqinsertdate(Date reqinsertdate) {
		this.reqinsertdate = reqinsertdate;
	}

	public void setResinsertdate(Date resinsertdate) {
		this.resinsertdate = resinsertdate;
	}

	public void setRespheadmsgid(String respheadmsgid) {
		this.respheadmsgid = respheadmsgid;
	}

	public void setRespheadorgid(String respheadorgid) {
		this.respheadorgid = respheadorgid;
	}

	public void setRespheadts(String respheadts) {
		this.respheadts = respheadts;
	}

	public void setTxnid(String txnid) {
		this.txnid = txnid;
	}

	public void setTxnts(String txnts) {
		this.txnts = txnts;
	}

	public void setTxntype(String txntype) {
		this.txntype = txntype;
	}
}