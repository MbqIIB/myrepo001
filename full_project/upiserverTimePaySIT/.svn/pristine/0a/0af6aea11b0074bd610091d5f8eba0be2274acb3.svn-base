package com.npst.upiserver.entity;

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
@Table(name = "regvpa")
public class Regvpa implements java.io.Serializable {
	private Long		regvpaid;
	private String	vpa;
	private String	mobNo;
	private String	fname;
	private String	mname;
	private String	lname;
	private Date	dob;
	private String	gender;
	private String	securityQues;
	private String	securityAns;
	private String	emailId;
	private String	loginPin1;
	private String	loginpin2;
	private String	loginpin3;
	private String	aadhaarNo;
	private Integer	pstatus;
	private String	gcmtoken;
	private String	appver;
	private String	appos;
	private Date	expdate;
	private Integer	failcount;
	private String	servertoken;
	private String	deviceinfo;
	private String	imei;
	private Date	servertokendt;
	private Date	lockedtime;
	private Date	loggedout;
	private Date	deregtime;
	private Date	lastfaillogin;
	private Integer	allowedfailcount;
	private Date	lastLogin;
	private Date	lastChangePass;
	private Date	createddate;
	private Date	updateddate;
	
	public Regvpa() {
	}
	
	public Regvpa(Long regvpaid) {
		this.regvpaid = regvpaid;
	}
	
	public Regvpa(Long regvpaid, String vpa, String mobNo, String fname, String mname, String lname, Date dob,
			String gender, String securityQues, String securityAns, String emailId, String loginPin1, String loginpin2,
			String loginpin3, String aadhaarNo, Integer pstatus, String gcmtoken, String appver, String appos,
			Date expdate, Integer failcount, String servertoken, String deviceinfo, String imei, Date servertokendt,
			Date lockedtime, Date loggedout, Date deregtime, Date lastfaillogin, Integer allowedfailcount,
			Date lastLogin, Date lastChangePass, Date createddate, Date updateddate) {
		this.regvpaid = regvpaid;
		this.vpa = vpa;
		this.mobNo = mobNo;
		this.fname = fname;
		this.mname = mname;
		this.lname = lname;
		this.dob = dob;
		this.gender = gender;
		this.securityQues = securityQues;
		this.securityAns = securityAns;
		this.emailId = emailId;
		this.loginPin1 = loginPin1;
		this.loginpin2 = loginpin2;
		this.loginpin3 = loginpin3;
		this.aadhaarNo = aadhaarNo;
		this.pstatus = pstatus;
		this.gcmtoken = gcmtoken;
		this.appver = appver;
		this.appos = appos;
		this.expdate = expdate;
		this.failcount = failcount;
		this.servertoken = servertoken;
		this.deviceinfo = deviceinfo;
		this.imei = imei;
		this.servertokendt = servertokendt;
		this.lockedtime = lockedtime;
		this.loggedout = loggedout;
		this.deregtime = deregtime;
		this.lastfaillogin = lastfaillogin;
		this.allowedfailcount = allowedfailcount;
		this.lastLogin = lastLogin;
		this.lastChangePass = lastChangePass;
		this.createddate = createddate;
		this.updateddate = updateddate;
	}
	
	@Column(name = "aadhaarno", length = 12)
	public String getAadhaarNo() {
		return this.aadhaarNo;
	}
	
	@Column(name = "allowedfailcount")
	public Integer getAllowedfailcount() {
		return this.allowedfailcount;
	}
	
	@Column(name = "appos", length = 10)
	public String getAppos() {
		return this.appos;
	}
	
	@Column(name = "appver", length = 10)
	public String getAppver() {
		return this.appver;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createddate", length = 19)
	public Date getCreateddate() {
		return this.createddate;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "deregtime", length = 19)
	public Date getDeregtime() {
		return this.deregtime;
	}
	
	@Column(name = "deviceinfo", length = 100)
	public String getDeviceinfo() {
		return this.deviceinfo;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dob", length = 19)
	public Date getDob() {
		return this.dob;
	}
	
	@Column(name = "emailId", length = 50)
	public String getEmailId() {
		return this.emailId;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expdate", length = 19)
	public Date getExpdate() {
		return this.expdate;
	}
	
	@Column(name = "failcount")
	public Integer getFailcount() {
		return this.failcount;
	}
	
	@Column(name = "fname", length = 20)
	public String getFname() {
		return this.fname;
	}
	
	@Column(name = "gcmtoken", length = 500)
	public String getGcmtoken() {
		return this.gcmtoken;
	}
	
	@Column(name = "gender", length = 15)
	public String getGender() {
		return this.gender;
	}
	
	@Column(name = "imei", length = 200)
	public String getImei() {
		return this.imei;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "lastChangePass", length = 19)
	public Date getLastChangePass() {
		return this.lastChangePass;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "lastfaillogin", length = 19)
	public Date getLastfaillogin() {
		return this.lastfaillogin;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "lastLogin", length = 19)
	public Date getLastLogin() {
		return this.lastLogin;
	}
	
	@Column(name = "lname", length = 20)
	public String getLname() {
		return this.lname;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "lockedtime", length = 19)
	public Date getLockedtime() {
		return this.lockedtime;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "loggedout", length = 19)
	public Date getLoggedout() {
		return this.loggedout;
	}
	
	@Column(name = "loginPin1", length = 50)
	public String getLoginPin1() {
		return this.loginPin1;
	}
	
	@Column(name = "loginpin2", length = 50)
	public String getLoginpin2() {
		return this.loginpin2;
	}
	
	@Column(name = "loginpin3", length = 50)
	public String getLoginpin3() {
		return this.loginpin3;
	}
	
	@Column(name = "mname", length = 20)
	public String getMname() {
		return this.mname;
	}
	
	@Column(name = "mobno", length = 12)
	public String getMobNo() {
		return this.mobNo;
	}
	
	@Column(name = "pstatus")
	public Integer getPstatus() {
		return this.pstatus;
	}
	
	@Id
	@Column(name = "regvpaid", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getRegvpaid() {
		return this.regvpaid;
	}
	
	@Column(name = "securityans", length = 50)
	public String getSecurityAns() {
		return this.securityAns;
	}
	
	@Column(name = "securityques", length = 100)
	public String getSecurityQues() {
		return this.securityQues;
	}
	
	@Column(name = "servertoken", length = 35)
	public String getServertoken() {
		return this.servertoken;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "servertokendt", length = 19)
	public Date getServertokendt() {
		return this.servertokendt;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updateddate", length = 19)
	public Date getUpdateddate() {
		return this.updateddate;
	}
	
	@Column(name = "vpa", length = 50)
	public String getVpa() {
		return this.vpa;
	}
	
	public void setAadhaarNo(String aadhaarNo) {
		this.aadhaarNo = aadhaarNo;
	}
	
	public void setAllowedfailcount(Integer allowedfailcount) {
		this.allowedfailcount = allowedfailcount;
	}
	
	public void setAppos(String appos) {
		this.appos = appos;
	}
	
	public void setAppver(String appver) {
		this.appver = appver;
	}
	
	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}
	
	public void setDeregtime(Date deregtime) {
		this.deregtime = deregtime;
	}
	
	public void setDeviceinfo(String deviceinfo) {
		this.deviceinfo = deviceinfo;
	}
	
	public void setDob(Date dob) {
		this.dob = dob;
	}
	
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	public void setExpdate(Date expdate) {
		this.expdate = expdate;
	}
	
	public void setFailcount(Integer failcount) {
		this.failcount = failcount;
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
	
	public void setImei(String imei) {
		this.imei = imei;
	}
	
	public void setLastChangePass(Date lastChangePass) {
		this.lastChangePass = lastChangePass;
	}
	
	public void setLastfaillogin(Date lastfaillogin) {
		this.lastfaillogin = lastfaillogin;
	}
	
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	
	public void setLname(String lname) {
		this.lname = lname;
	}
	
	public void setLockedtime(Date lockedtime) {
		this.lockedtime = lockedtime;
	}
	
	public void setLoggedout(Date loggedout) {
		this.loggedout = loggedout;
	}
	
	public void setLoginPin1(String loginPin1) {
		this.loginPin1 = loginPin1;
	}
	
	public void setLoginpin2(String loginpin2) {
		this.loginpin2 = loginpin2;
	}
	
	public void setLoginpin3(String loginpin3) {
		this.loginpin3 = loginpin3;
	}
	
	public void setMname(String mname) {
		this.mname = mname;
	}
	
	public void setMobNo(String mobNo) {
		this.mobNo = mobNo;
	}
	
	public void setPstatus(Integer pstatus) {
		this.pstatus = pstatus;
	}
	
	public void setRegvpaid(Long regvpaid) {
		this.regvpaid = regvpaid;
	}
	
	public void setSecurityAns(String securityAns) {
		this.securityAns = securityAns;
	}
	
	public void setSecurityQues(String securityQues) {
		this.securityQues = securityQues;
	}
	
	public void setServertoken(String servertoken) {
		this.servertoken = servertoken;
	}
	
	public void setServertokendt(Date servertokendt) {
		this.servertokendt = servertokendt;
	}
	
	public void setUpdateddate(Date updateddate) {
		this.updateddate = updateddate;
	}
	public void setVpa(String vpa) {
		this.vpa = vpa;
	}
}
