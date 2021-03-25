package com.npst.middleware.entity;

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
@Table(name = "otprecord")
public class OtpRecord implements java.io.Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer	id;
	// uniq id
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creTime", length = 19)
	private Date	creTime;
	// created time
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expiryTime", length = 19)
	private Date	expiryTime;
	// expiry time
	@Column(name = "otp", length = 6)
	private String	otp;
	// actual otp
	@Column(name = "mobileNo", length = 13)
	private String	mobileNo;
	// mobile no who receiving otp
	@Column(name = "reSendCount")
	private Integer	reSendCount;
	// re send request time
	@Column(name = "failAttempt")
	private Integer	failAttempt;
	// fail attempt count
	@Column(name = "maxFailAttempt")
	private Integer	maxFailAttempt;
	// how many attempt allowed
	@Column(name = "whoReq", length = 50)
	private String	whoReq;
	// who requested user or some admin
	@Column(name = "reason", length = 50)
	private String	reason;
	// for what reason like mob reg/bal/transaction
	@Column(name = "channel", length = 10)
	private String	channel;
	
	// channel like ussd or upi
	@Column(name = "response", length = 100)
	private String	response;
	// response from sms sender
	
	@Column(name = "isUsed")
	private Integer	isUsed;
	
	@Column(name = "otp_salt",length=500)
	private String	otpSalt;
	
	public String getOtpSalt() {
		return otpSalt;
	}

	public void setOtpSalt(String otpSalt) {
		this.otpSalt = otpSalt;
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
	
	public Integer getId() {
		return id;
	}
	
	public Integer getIsUsed() {
		return isUsed;
	}
	
	public Integer getMaxFailAttempt() {
		return maxFailAttempt;
	}
	
	public String getMobileNo() {
		return mobileNo;
	}
	
	public String getOtp() {
		return otp;
	}
	
	public String getReason() {
		return reason;
	}
	
	public Integer getReSendCount() {
		return reSendCount;
	}
	
	public String getResponse() {
		return response;
	}
	
	public String getWhoReq() {
		return whoReq;
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
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
	}
	
	public void setMaxFailAttempt(Integer maxFailAttempt) {
		this.maxFailAttempt = maxFailAttempt;
	}
	
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	public void setOtp(String otp) {
		this.otp = otp;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public void setReSendCount(Integer reSendCount) {
		this.reSendCount = reSendCount;
	}
	
	public void setResponse(String response) {
		this.response = response;
	}
	
	public void setWhoReq(String whoReq) {
		this.whoReq = whoReq;
	}
	
}
