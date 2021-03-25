package com.npst.upiserver.entity;


import static javax.persistence.GenerationType.IDENTITY;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "registration")
public class Registration implements java.io.Serializable {
	
	private Long	regid;
	private String	aadhaarno;
	private String	appos;
	private String	appver;
	private Date	blockdt;
	private Date	createddt;
	private String	defvpa;
	private Date	dob;
	private String	emailid;
	private Date	expdt;
	private Integer	faillogincount;
	private String	fname;
	private String	gcmtoken;
	private String	gender;
	private Date	lastchangepassdt;
	private Date	lastlogindt;
	private String	lname;
	private String	loginpin1;
	private String	loginpin2;
	private String	loginpin3;
	private String	mname;
	private String	mobno;
	private String	secans;
	private String	secque;
	private String	servertoken;
	private Date	servertokendt;
	private Integer	status;
	private Date	updateddt;
	private Date	logoutdt;
	private String	deviceid;
	private String	deviceimei;
	
	public Registration() {
	}
	
	public Registration(String aadhaarno, String appos, String appver, Date blockdt, Date createddt, String defvpa,
			Date dob, String emailid, Date expdt, Integer faillogincount, String fname, String gcmtoken, String gender,
			Date lastchangepassdt, Date lastlogindt, String lname, String loginpin1, String loginpin2, String loginpin3,
			String mname, String mobno, String secans, String secque, String servertoken, Date servertokendt,
			Integer status, Date updateddt, Date logoutdt, String deviceid, String deviceimei) {
		this.aadhaarno = aadhaarno;
		this.appos = appos;
		this.appver = appver;
		this.blockdt = blockdt;
		this.createddt = createddt;
		this.defvpa = defvpa;
		this.dob = dob;
		this.emailid = emailid;
		this.expdt = expdt;
		this.faillogincount = faillogincount;
		this.fname = fname;
		this.gcmtoken = gcmtoken;
		this.gender = gender;
		this.lastchangepassdt = lastchangepassdt;
		this.lastlogindt = lastlogindt;
		this.lname = lname;
		this.loginpin1 = loginpin1;
		this.loginpin2 = loginpin2;
		this.loginpin3 = loginpin3;
		this.mname = mname;
		this.mobno = mobno;
		this.secans = secans;
		this.secque = secque;
		this.servertoken = servertoken;
		this.servertokendt = servertokendt;
		this.status = status;
		this.updateddt = updateddt;
		this.logoutdt = logoutdt;
		this.deviceid = deviceid;
		this.deviceimei = deviceimei;
	}
	
	@Column(name = "AADHAARNO", length = 12)
	public String getAadhaarno() {
		return this.aadhaarno;
	}
	
	@Column(name = "APPOS", length = 20)
	public String getAppos() {
		return this.appos;
	}
	
	@Column(name = "APPVER", length = 5)
	public String getAppver() {
		return this.appver;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "BLOCKDT", length = 19)
	public Date getBlockdt() {
		return this.blockdt;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDDT", length = 19)
	public Date getCreateddt() {
		return this.createddt;
	}
	
	@Column(name = "DEFVPA", length = 50)
	public String getDefvpa() {
		return this.defvpa;
	}
	
	@Column(name = "DEVICEID", length = 50)
	public String getDeviceid() {
		return this.deviceid;
	}
	
	@Column(name = "DEVICEIMEI", length = 50)
	public String getDeviceimei() {
		return this.deviceimei;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DOB", length = 19)
	public Date getDob() {
		return this.dob;
	}
	
	@Column(name = "EMAILID", length = 50)
	public String getEmailid() {
		return this.emailid;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPDT", length = 19)
	public Date getExpdt() {
		return this.expdt;
	}
	
	@Column(name = "FAILLOGINCOUNT")
	public Integer getFaillogincount() {
		return this.faillogincount;
	}
	
	@Column(name = "FNAME", length = 50)
	public String getFname() {
		return this.fname;
	}
	
	@Column(name = "GCMTOKEN", length = 1000)
	public String getGcmtoken() {
		return this.gcmtoken;
	}
	
	@Column(name = "GENDER", length = 11)
	public String getGender() {
		return this.gender;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LASTCHANGEPASSDT", length = 19)
	public Date getLastchangepassdt() {
		return this.lastchangepassdt;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LASTLOGINDT", length = 19)
	public Date getLastlogindt() {
		return this.lastlogindt;
	}
	
	@Column(name = "LNAME", length = 50)
	public String getLname() {
		return this.lname;
	}
	
	@Column(name = "LOGINPIN1", length = 100)
	public String getLoginpin1() {
		return this.loginpin1;
	}
	
	@Column(name = "LOGINPIN2", length = 100)
	public String getLoginpin2() {
		return this.loginpin2;
	}
	
	@Column(name = "LOGINPIN3", length = 100)
	public String getLoginpin3() {
		return this.loginpin3;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOGOUTDT", length = 19)
	public Date getLogoutdt() {
		return this.logoutdt;
	}
	
	@Column(name = "MNAME", length = 50)
	public String getMname() {
		return this.mname;
	}
	
	@Column(name = "MOBNO", length = 12)
	public String getMobno() {
		return this.mobno;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "REGID", unique = true, nullable = false)
	public Long getRegid() {
		return this.regid;
	}
	
	@Column(name = "SECANS", length = 50)
	public String getSecans() {
		return this.secans;
	}
	
	@Column(name = "SECQUE", length = 80)
	public String getSecque() {
		return this.secque;
	}
	
	@Column(name = "SERVERTOKEN", length = 35)
	public String getServertoken() {
		return this.servertoken;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SERVERTOKENDT", length = 19)
	public Date getServertokendt() {
		return this.servertokendt;
	}
	
	@Column(name = "STATUS")
	public Integer getStatus() {
		return this.status;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATEDDT", length = 19)
	public Date getUpdateddt() {
		return this.updateddt;
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
	
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	
	public void setDeviceimei(String deviceimei) {
		this.deviceimei = deviceimei;
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
	
	public void setLoginpin1(String loginpin1) {
		this.loginpin1 = loginpin1;
	}
	
	public void setLoginpin2(String loginpin2) {
		this.loginpin2 = loginpin2;
	}
	
	public void setLoginpin3(String loginpin3) {
		this.loginpin3 = loginpin3;
	}
	
	public void setLogoutdt(Date logoutdt) {
		this.logoutdt = logoutdt;
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
	
	public void setSecans(String secans) {
		this.secans = secans;
	}
	
	public void setSecque(String secque) {
		this.secque = secque;
	}
	
	public void setServertoken(String servertoken) {
		this.servertoken = servertoken;
	}
	
	public void setServertokendt(Date servertokendt) {
		this.servertokendt = servertokendt;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public void setUpdateddt(Date updateddt) {
		this.updateddt = updateddt;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Registration [");
		if (regid != null) {
			builder.append("regid=").append(regid).append(", ");
		}
		if (aadhaarno != null) {
			builder.append("aadhaarno=").append(aadhaarno).append(", ");
		}
		if (appos != null) {
			builder.append("appos=").append(appos).append(", ");
		}
		if (appver != null) {
			builder.append("appver=").append(appver).append(", ");
		}
		if (blockdt != null) {
			builder.append("blockdt=").append(blockdt).append(", ");
		}
		if (createddt != null) {
			builder.append("createddt=").append(createddt).append(", ");
		}
		if (defvpa != null) {
			builder.append("defvpa=").append(defvpa).append(", ");
		}
		if (dob != null) {
			builder.append("dob=").append(dob).append(", ");
		}
		if (emailid != null) {
			builder.append("emailid=").append(emailid).append(", ");
		}
		if (expdt != null) {
			builder.append("expdt=").append(expdt).append(", ");
		}
		if (faillogincount != null) {
			builder.append("faillogincount=").append(faillogincount).append(", ");
		}
		if (fname != null) {
			builder.append("fname=").append(fname).append(", ");
		}
		if (gcmtoken != null) {
			builder.append("gcmtoken=").append(gcmtoken).append(", ");
		}
		if (gender != null) {
			builder.append("gender=").append(gender).append(", ");
		}
		if (lastchangepassdt != null) {
			builder.append("lastchangepassdt=").append(lastchangepassdt).append(", ");
		}
		if (lastlogindt != null) {
			builder.append("lastlogindt=").append(lastlogindt).append(", ");
		}
		if (lname != null) {
			builder.append("lname=").append(lname).append(", ");
		}
		if (loginpin1 != null) {
			builder.append("loginpin1=").append(loginpin1).append(", ");
		}
		if (loginpin2 != null) {
			builder.append("loginpin2=").append(loginpin2).append(", ");
		}
		if (loginpin3 != null) {
			builder.append("loginpin3=").append(loginpin3).append(", ");
		}
		if (mname != null) {
			builder.append("mname=").append(mname).append(", ");
		}
		if (mobno != null) {
			builder.append("mobno=").append(mobno).append(", ");
		}
		if (secans != null) {
			builder.append("secans=").append(secans).append(", ");
		}
		if (secque != null) {
			builder.append("secque=").append(secque).append(", ");
		}
		if (servertoken != null) {
			builder.append("servertoken=").append(servertoken).append(", ");
		}
		if (servertokendt != null) {
			builder.append("servertokendt=").append(servertokendt).append(", ");
		}
		if (status != null) {
			builder.append("status=").append(status).append(", ");
		}
		if (updateddt != null) {
			builder.append("updateddt=").append(updateddt).append(", ");
		}
		if (logoutdt != null) {
			builder.append("logoutdt=").append(logoutdt).append(", ");
		}
		if (deviceid != null) {
			builder.append("deviceid=").append(deviceid).append(", ");
		}
		if (deviceimei != null) {
			builder.append("deviceimei=").append(deviceimei);
		}
		builder.append("]");
		return builder.toString();
	}
	
}


