package com.npst.upi.hor;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "reserved_vpa")
public class ReservedVPA implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "virtualid")
	private String virtualId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "merchane_name", length = 50)
	private String	merchantName;

	


	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getVirtualId() {
		return virtualId;
	}

	public void setVirtualId(String virtualId) {
		this.virtualId = virtualId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	
}
