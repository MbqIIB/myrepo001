package com.npst.upi.hor;

import java.io.Serializable;
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
@Table(name = "upipin")
public class UpiPin implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "upi_pin_id")
	private Integer upiPinId;

	@Column(name = "mobile_no", length = 13)
	private String mobileNo;

	@Column(name = "acc_no")
	private String accNo;

	@Column(name = "upi_pin", length = 500)
	private String upiPin;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expiry_time", length = 19)
	private Date expiryTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cre_time", length = 19)
	private Date creTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_modified_time", length = 19)
	private Date lastModTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_use_time", length = 19)
	private Date lastUseTime;

	@Column(name = "last_upi_pin")
	private String lastUpiPin;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_action_time", length = 19)
	private Date lastActionTime; // RESET/Change

	@Column(name = "fail_attempt")
	private Integer failAttempt;

	@Column(name = "max_fail_attempt")
	private Integer maxFailAttempt;

	@Column(name = "who_req")
	private String whoReq;

	@Column(name = "reason")
	private String reason;

	@Column(name = "channel")
	private String channel;

	@Column(name = "status")
	private Integer status; // 0 OK/ 1 BLOCK/ 2
							// FAILEXID

	@Column(name = "salt", length = 100)
	private String salt;

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getAccNo() {
		return accNo;
	}

	public String getChannel() {
		return channel;
	}

	public Date getCreTime() {
		return creTime;
	}

	public Date getExpiryTime() {
		return expiryTime;
	}

	public Integer getFailAttempt() {
		return failAttempt;
	}

	public Date getLastActionTime() {
		return lastActionTime;
	}

	public Date getLastModTime() {
		return lastModTime;
	}

	public String getLastUpiPin() {
		return lastUpiPin;
	}

	public Date getLastUseTime() {
		return lastUseTime;
	}

	public Integer getMaxFailAttempt() {
		return maxFailAttempt;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public String getReason() {
		return reason;
	}

	public Integer getStatus() {
		return status;
	}

	public String getUpiPin() {
		return upiPin;
	}

	public Integer getUpiPinId() {
		return upiPinId;
	}

	public String getWhoReq() {
		return whoReq;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public void setCreTime(Date creTime) {
		this.creTime = creTime;
	}

	public void setExpiryTime(Date expiryTime) {
		this.expiryTime = expiryTime;
	}

	public void setFailAttempt(Integer failAttempt) {
		this.failAttempt = failAttempt;
	}

	public void setLastActionTime(Date lastActionTime) {
		this.lastActionTime = lastActionTime;
	}

	public void setLastModTime(Date lastModTime) {
		this.lastModTime = lastModTime;
	}

	public void setLastUpiPin(String lastUpiPin) {
		this.lastUpiPin = lastUpiPin;
	}

	public void setLastUseTime(Date lastUseTime) {
		this.lastUseTime = lastUseTime;
	}

	public void setMaxFailAttempt(Integer maxFailAttempt) {
		this.maxFailAttempt = maxFailAttempt;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setUpiPin(String upiPin) {
		this.upiPin = upiPin;
	}

	public void setUpiPinId(Integer upiPinId) {
		this.upiPinId = upiPinId;
	}

	public void setWhoReq(String whoReq) {
		this.whoReq = whoReq;
	}

}
