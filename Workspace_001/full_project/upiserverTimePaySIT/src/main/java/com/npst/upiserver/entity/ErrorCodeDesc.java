package com.npst.upiserver.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "errcodedesc")
public class ErrorCodeDesc implements java.io.Serializable {

	private String upierrcode;
	private String cbserrcode;
	private String errdesc;

	public ErrorCodeDesc() {
	}

	public ErrorCodeDesc(String upierrcode) {
		this.upierrcode = upierrcode;
	}

	public ErrorCodeDesc(String upierrcode, String cbserrcode, String errdesc) {
		this.upierrcode = upierrcode;
		this.cbserrcode = cbserrcode;
		this.errdesc = errdesc;
	}

	@Column(name = "cbserrcode", length = 5)
	public String getCbserrcode() {
		return this.cbserrcode;
	}

	@Column(name = "errdesc", length = 50)
	public String getErrdesc() {
		return this.errdesc;
	}

	@Id

	@Column(name = "upierrcode", unique = true, nullable = false, length = 5)
	public String getUpierrcode() {
		return this.upierrcode;
	}

	public void setCbserrcode(String cbserrcode) {
		this.cbserrcode = cbserrcode;
	}

	public void setErrdesc(String errdesc) {
		this.errdesc = errdesc;
	}

	public void setUpierrcode(String upierrcode) {
		this.upierrcode = upierrcode;
	}

}

