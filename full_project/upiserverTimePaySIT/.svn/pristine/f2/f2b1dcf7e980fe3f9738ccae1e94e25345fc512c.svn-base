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
@Table(name = "risk_xh")
public class RiskXH {
	private Long regid;
	private Date createUpdDate;
	private String mobNo;
	private int count;
	
	public RiskXH() {
	}
	
	public RiskXH(long riskXHId, Long regid, Date createUpdDate, String mobNo, int count) {
		super();
		this.regid = regid;
		this.createUpdDate = createUpdDate;
		this.mobNo = mobNo;
		this.count = count;
	}
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "regid",unique=true)
	public Long getRegid() {
		return regid;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_updt_date", length = 19)
	public Date getCreateUpdDate() {
		return createUpdDate;
	}
	
	@Column(name = "mob_no")
	public String getMobNo() {
		return mobNo;
	}
	
	@Column(name = "count")
	public int getCount() {
		return count;
	}
	
	public void setRegid(Long regid) {
		this.regid = regid;
	}
	public void setCreateUpdDate(Date createUpdDate) {
		this.createUpdDate = createUpdDate;
	}
	public void setMobNo(String mobNo) {
		this.mobNo = mobNo;
	}
	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "RiskXH [regid=" + regid + ", createUpdDate=" + createUpdDate + ", mobNo=" + mobNo + ", count=" + count
				+ "]";
	}
}