package com.npst.mobileservice.object;

import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.util.Date;

import com.npst.mobileservice.util.DecryptionUtility;
import com.npst.upi.hor.Registration;

public class RegistrationVO implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 7096821738800071846L;
	private Long regid;
	private String defvpa;
	private String mobno;
	private String fname;
	private String mname;
	private String lname;
	private Date dob;
	private String gender;
	private String secque;
	private String emailid;
	private String aadhaarno;
	private Integer status;
	private Date lastlogindt;
	private Date createddt;
	private Date updateddt;
	private String gcmtoken;
	private String appver;
	private String appos;
	private Date expdt;
	private Date lastchangepassdt;
	private Integer faillogincount;
	private Date blockdt;
	private String deviceid;

	public RegistrationVO(Registration registration) {
		super();
		this.regid = registration.getRegid();
		this.defvpa = registration.getDefvpa();
		this.mobno = registration.getMobno();
		this.fname = registration.getFname();
		this.mname = registration.getMname();
		this.lname = registration.getLname();
		this.dob = registration.getDob();
		this.gender = registration.getGender();
		try {
			this.secque = DecryptionUtility.decrypt(registration.getSecque());
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		this.emailid = registration.getEmailid();
		this.aadhaarno = registration.getAadhaarno();
		this.status = registration.getStatus();
		this.lastlogindt = registration.getLastlogindt();
		this.createddt = registration.getCreateddt();
		this.updateddt = registration.getUpdateddt();
		this.gcmtoken = registration.getGcmtoken();
		this.appver = registration.getAppver();
		this.appos = registration.getAppos();
		this.expdt = registration.getExpdt();
		this.lastchangepassdt = registration.getLastchangepassdt();
		this.faillogincount = registration.getFaillogincount();
		this.blockdt = registration.getBlockdt();
		this.deviceid = registration.getDeviceid();
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public String getAadhaarno() {
		return aadhaarno;
	}

	public String getAppos() {
		return appos;
	}

	public String getAppver() {
		return appver;
	}

	public Date getBlockdt() {
		return blockdt;
	}

	public Date getCreateddt() {
		return createddt;
	}

	public String getDefvpa() {
		return defvpa;
	}

	public Date getDob() {
		return dob;
	}

	public String getEmailid() {
		return emailid;
	}

	public Date getExpdt() {
		return expdt;
	}

	public Integer getFaillogincount() {
		return faillogincount;
	}

	public String getFname() {
		return fname;
	}

	public String getGcmtoken() {
		return gcmtoken;
	}

	public String getGender() {
		return gender;
	}

	public Date getLastchangepassdt() {
		return lastchangepassdt;
	}

	public Date getLastlogindt() {
		return lastlogindt;
	}

	public String getLname() {
		return lname;
	}

	public String getMname() {
		return mname;
	}

	public String getMobno() {
		return mobno;
	}

	public Long getRegid() {
		return regid;
	}

	public String getSecque() {
		return secque;
	}

	public Integer getStatus() {
		return status;
	}

	public Date getUpdateddt() {
		return updateddt;
	}

	public void setAadhaarno(String aadhaarno) {
		this.aadhaarno = aadhaarno;
	}

	public void setAppos(String appos) {
		this.appos = appos;
	}

	public void setAppver(String appver) {
		this.appver = appver;
	}

	public void setBlockdt(Date blockdt) {
		this.blockdt = blockdt;
	}

	public void setCreateddt(Date createddt) {
		this.createddt = createddt;
	}

	public void setDefvpa(String defvpa) {
		this.defvpa = defvpa;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public void setExpdt(Date expdt) {
		this.expdt = expdt;
	}

	public void setFaillogincount(Integer faillogincount) {
		this.faillogincount = faillogincount;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public void setGcmtoken(String gcmtoken) {
		this.gcmtoken = gcmtoken;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setLastchangepassdt(Date lastchangepassdt) {
		this.lastchangepassdt = lastchangepassdt;
	}

	public void setLastlogindt(Date lastlogindt) {
		this.lastlogindt = lastlogindt;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public void setMobno(String mobno) {
		this.mobno = mobno;
	}

	public void setRegid(Long regid) {
		this.regid = regid;
	}

	public void setSecque(String secque) {
		this.secque = secque;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setUpdateddt(Date updateddt) {
		this.updateddt = updateddt;
	}

	@Override
	public String toString() {
		return "RegistrationVO [regid=" + regid + ", defvpa=" + defvpa + ", mobno=" + mobno + ", fname=" + fname
				+ ", mname=" + mname + ", lname=" + lname + ", dob=" + dob + ", gender=" + gender + ", secque=" + secque
				+ ", emailid=" + emailid + ", aadhaarno=" + aadhaarno + ", status=" + status + ", lastlogindt="
				+ lastlogindt + ", createddt=" + createddt + ", updateddt=" + updateddt + ", gcmtoken=" + gcmtoken
				+ ", appver=" + appver + ", appos=" + appos + ", expdt=" + expdt + ", lastchangepassdt="
				+ lastchangepassdt + ", faillogincount=" + faillogincount + ", blockdt=" + blockdt + "]";
	}

}
