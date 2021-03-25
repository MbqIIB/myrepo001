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
@Table(name = "LIST_PSP_TAB")
public class ListPspEntity implements Serializable {
	private static final long serialVersionUID = 603688981426353712L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_LISTPSP")
	private long id;
	@Column(name = "IS_ACTIVE")
	private String isActive;
	@Column(name = "CODES")
	private String codes;
	@Column(name = "LAST_MODIFED_TS")
	private String lastModifedTs;
	@Column(name = "NAME")
	private String name;
	@Column(name = "SPOCE_MAIL")
	private String spocEmail;
	@Column(name = "SPOC_NAME")
	private String spocName;
	@Column(name = "SPOC_PHONE")
	private String spocPhone;
	@Column(name = "URL")
	private String url;
	@Column(name = "VERSION_SUPPORTED")
	private String versionSupported;
	@Column(name = "RECORDED_DATE")
	private Date recordDate;
	@Column(name = "ID_REQRESP_VAEPSPKEYS")
	private long idParant;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getCodes() {
		return codes;
	}

	public void setCodes(String codes) {
		this.codes = codes;
	}

	public String getLastModifedTs() {
		return lastModifedTs;
	}

	public void setLastModifedTs(String lastModifedTs) {
		this.lastModifedTs = lastModifedTs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpocEmail() {
		return spocEmail;
	}

	public void setSpocEmail(String spocEmail) {
		this.spocEmail = spocEmail;
	}

	public String getSpocName() {
		return spocName;
	}

	public void setSpocName(String spocName) {
		this.spocName = spocName;
	}

	public String getSpocPhone() {
		return spocPhone;
	}

	public void setSpocPhone(String spocPhone) {
		this.spocPhone = spocPhone;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVersionSupported() {
		return versionSupported;
	}

	public void setVersionSupported(String versionSupported) {
		this.versionSupported = versionSupported;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public long getIdParant() {
		return idParant;
	}

	public void setIdParant(long idParant) {
		this.idParant = idParant;
	}
}
