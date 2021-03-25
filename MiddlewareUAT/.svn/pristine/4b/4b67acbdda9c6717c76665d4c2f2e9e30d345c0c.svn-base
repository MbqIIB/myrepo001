package com.npst.middleware.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "errorcode")
public class ErrorCode implements java.io.Serializable {
	/**
	 *
	 */
	private static final long	serialVersionUID	= 1L;
	@Id
	@Column(name = "idErrorCode", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer				id;
	@Column(name = "MBErrorCode", length = 10)
	private String				mbErrorCode;
	@Column(name = "MBErrorDesc", length = 100)
	private String				mbErrorDesc;
	@Column(name = "CBSErrorCode", length = 10)
	private String				cbsErrorCode;
	@Column(name = "CBSErrorDesc", length = 100)
	private String				cbsErrorDesc;
	@Column(name = "CMSErrorCode", length = 10)
	private String				cmsErrorCode;
	@Column(name = "CMSErrorDesc", length = 100)
	private String				cmsErrorDesc;
	@Column(name = "PSPErrorCode", length = 10)
	private String				pspErrorCode;
	@Column(name = "PSPErrorDesc", length = 100)
	private String				pspErrorDesc;
	@Column(name = "UPIErrorCode", length = 10)
	private String				upiErrorCode;
	@Column(name = "UPIErrorDesc", length = 100)
	private String				upiErrorDesc;
	
	public String getCbsErrorCode() {
		return cbsErrorCode;
	}
	
	public String getCbsErrorDesc() {
		return cbsErrorDesc;
	}
	
	public String getCmsErrorCode() {
		return cmsErrorCode;
	}
	
	public String getCmsErrorDesc() {
		return cmsErrorDesc;
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getMbErrorCode() {
		return mbErrorCode;
	}
	
	public String getMbErrorDesc() {
		return mbErrorDesc;
	}
	
	public String getPspErrorCode() {
		return pspErrorCode;
	}
	
	public String getPspErrorDesc() {
		return pspErrorDesc;
	}
	
	public String getUpiErrorCode() {
		return upiErrorCode;
	}
	
	public String getUpiErrorDesc() {
		return upiErrorDesc;
	}
	
	public void setCbsErrorCode(String cbsErrorCode) {
		this.cbsErrorCode = cbsErrorCode;
	}
	
	public void setCbsErrorDesc(String cbsErrorDesc) {
		this.cbsErrorDesc = cbsErrorDesc;
	}
	
	public void setCmsErrorCode(String cmsErrorCode) {
		this.cmsErrorCode = cmsErrorCode;
	}
	
	public void setCmsErrorDesc(String cmsErrorDesc) {
		this.cmsErrorDesc = cmsErrorDesc;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setMbErrorCode(String mbErrorCode) {
		this.mbErrorCode = mbErrorCode;
	}
	
	public void setMbErrorDesc(String mbErrorDesc) {
		this.mbErrorDesc = mbErrorDesc;
	}
	
	public void setPspErrorCode(String pspErrorCode) {
		this.pspErrorCode = pspErrorCode;
	}
	
	public void setPspErrorDesc(String pspErrorDesc) {
		this.pspErrorDesc = pspErrorDesc;
	}
	
	public void setUpiErrorCode(String upiErrorCode) {
		this.upiErrorCode = upiErrorCode;
	}
	
	public void setUpiErrorDesc(String upiErrorDesc) {
		this.upiErrorDesc = upiErrorDesc;
	}
	
}
