package com.npst.mobileservice.object;

import java.io.Serializable;

public class Data implements Serializable {

	private static final long serialVersionUID = 1L;
	private String ki;
	private String hmac;
	private String skey;
	private String pid;
	private String code;
	private String type;
	private String encryptedBase64String;

	public String getCode() {
		return code;
	}

	public String getEncryptedBase64String() {
		return encryptedBase64String;
	}

	public String getHmac() {
		return hmac;
	}

	public String getKi() {
		return ki;
	}

	public String getPid() {
		return pid;
	}

	public String getSkey() {
		return skey;
	}

	public String getType() {
		return type;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setEncryptedBase64String(String encryptedBase64String) {
		this.encryptedBase64String = encryptedBase64String;
	}

	public void setHmac(String hmac) {
		this.hmac = hmac;
	}

	public void setKi(String ki) {
		this.ki = ki;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public void setSkey(String skey) {
		this.skey = skey;
	}

	public void setType(String type) {
		this.type = type;
	}
}
