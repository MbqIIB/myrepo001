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
@Table(name = "LIST_PSP_KEYS_TAB")
public class ListPspKeysEntity implements Serializable {
	private static final long serialVersionUID = 946939084909308974L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PSPKEYS_ID")
	long id;
	@Column(name = "PSP_ORG_ID")
	private String pspOrgId;
	@Column(name = "OWNER")
	private String owner;
	@Column(name = "RECORDED_DATE")
	private Date recordedDate;
	@Column(name = "KEY_VALUE" ,length=1000)
	private String keyValue;
	@Column(name = "KEY_CODE", length = 50)
	private String keyCode;
	@Column(name = "KEY_KI", length = 15)
	private String keyKi;
	@Column(name = "KEY_TYPE", length = 50)
	private String keyType;
	
	@Column(name = "ID_REQRESP_VAEPSPKEYS")
	private Long idParant;
	public String getPspOrgId() {
		return pspOrgId;
	}

	public void setPspOrgId(String pspOrgId) {
		this.pspOrgId = pspOrgId;
	}

	public Date getRecordedDate() {
		return recordedDate;
	}

	public void setRecordedDate(Date recordedDate) {
		this.recordedDate = recordedDate;
	}

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public String getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}

	public String getKeyKi() {
		return keyKi;
	}

	public void setKeyKi(String keyKi) {
		this.keyKi = keyKi;
	}

	public String getKeyType() {
		return keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Long getIdParant() {
		return idParant;
	}

	public void setIdParant(Long idParant) {
		this.idParant = idParant;
	}
}
