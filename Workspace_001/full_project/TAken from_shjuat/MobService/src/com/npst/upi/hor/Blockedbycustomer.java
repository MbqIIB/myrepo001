package com.npst.upi.hor;
// Generated 10 Jan, 2018 5:08:52 PM by Hibernate Tools 5.2.3.Final

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Blockedbycustomer generated by hbm2java
 */
@Entity
@Table(name = "blockedbycustomer")
public class Blockedbycustomer implements java.io.Serializable {
	
	private int		idblockedbycustomer;
	private Integer	status;
	private Integer	regid;
	private String	blockedvpa;
	private Date	blockeddate;
	private Date	expirydate;
	
	public Blockedbycustomer() {
	}
	
	public Blockedbycustomer(int idblockedbycustomer) {
		this.idblockedbycustomer = idblockedbycustomer;
	}
	
	public Blockedbycustomer(int idblockedbycustomer, Integer status, Integer regid, String blockedvpa,
			Date blockeddate, Date expirydate) {
		this.idblockedbycustomer = idblockedbycustomer;
		this.status = status;
		this.regid = regid;
		this.blockedvpa = blockedvpa;
		this.blockeddate = blockeddate;
		this.expirydate = expirydate;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "blockeddate", length = 19)
	public Date getBlockeddate() {
		return this.blockeddate;
	}
	
	@Column(name = "blockedvpa", length = 100)
	public String getBlockedvpa() {
		return this.blockedvpa;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expirydate", length = 19)
	public Date getExpirydate() {
		return this.expirydate;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idblockedbycustomer", unique = true, nullable = false)
	public int getIdblockedbycustomer() {
		return this.idblockedbycustomer;
	}
	
	@Column(name = "regid")
	public Integer getRegid() {
		return this.regid;
	}
	
	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}
	
	public void setBlockeddate(Date blockeddate) {
		this.blockeddate = blockeddate;
	}
	
	public void setBlockedvpa(String blockedvpa) {
		this.blockedvpa = blockedvpa;
	}
	
	public void setExpirydate(Date expirydate) {
		this.expirydate = expirydate;
	}
	
	public void setIdblockedbycustomer(int idblockedbycustomer) {
		this.idblockedbycustomer = idblockedbycustomer;
	}
	
	public void setRegid(Integer regid) {
		this.regid = regid;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
