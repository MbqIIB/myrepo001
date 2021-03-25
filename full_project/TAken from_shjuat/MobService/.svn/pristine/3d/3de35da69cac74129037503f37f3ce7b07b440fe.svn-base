package com.npst.upi.hor;

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
@Table(name = "LIST_VAE_TAB")
public class ListVaeEntity implements Serializable {
	private static final long serialVersionUID = -175769048111247104L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_LISTVAE")
	private long id;
	@Column(name = "VPA")
	private String addr;
	@Column(name = "NAME")
	private String name;
	@Column(name = "LOGO")
	private String logo;
	@Column(name = "URL")
	private String url;
	@Column(name = "RECORDED_DATE")
	private Date recordedDate;
	@Column(name = "KEY_VALUE", length = 1000)
	private String keyValue;
	@Column(name = "KEY_CODE", length = 50)
	private String keyCode;
	@Column(name = "KEY_KI", length = 15)
	private String keyKi;
	@Column(name = "KEY_TYPE", length = 50)
	private String keyType;
	@Column(name = "ID_REQRESP_VAEPSPKEYS")
	private long idParant;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public long getIdParant() {
		return idParant;
	}

	public void setIdParant(long idParant) {
		this.idParant = idParant;
	}
}
