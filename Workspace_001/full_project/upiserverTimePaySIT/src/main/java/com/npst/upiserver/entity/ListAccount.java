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
@Table(name = "listaccount")
public class ListAccount {
	private Long listaccountid;
	private Long regvpaid;
	private String mobileNo;
	private String vpa;
	private String pcode;
	private String pname;
	private String ptype;
	private String acctype;
	private String mbeba;
	private String accrefnumber;
	private String maskedaccnumber;
	private String ifsc;
	private String mmid;
	private String aeba;
	private String credjson;
	private String bankname;
	private Date accaddeddate;
	private Date createddate;
	private Date updatedate;
	private Integer amountlimit;
	private Integer pstatus;
	private Integer defaultAcc;

	public ListAccount() {
	}

	public ListAccount(Long regvpaid) {
		this.regvpaid = regvpaid;
	}

	public ListAccount(Long regvpaid, String mobileNo, String vpa, String pcode, String pname, String ptype,
			String acctype, String mbeba, String accrefnumber, String maskedaccnumber, String ifsc, String mmid,
			String aeba, String credjson, String bankname, Date accaddeddate, Date createddate, Date updatedate,
			Integer amountlimit, Integer pstatus, Integer defaultAcc) {
		this.regvpaid = regvpaid;
		this.mobileNo = mobileNo;
		this.vpa = vpa;
		this.pcode = pcode;
		this.pname = pname;
		this.ptype = ptype;
		this.acctype = acctype;
		this.mbeba = mbeba;
		this.accrefnumber = accrefnumber;
		this.maskedaccnumber = maskedaccnumber;
		this.ifsc = ifsc;
		this.mmid = mmid;
		this.aeba = aeba;
		this.credjson = credjson;
		this.bankname = bankname;
		this.accaddeddate = accaddeddate;
		this.createddate = createddate;
		this.updatedate = updatedate;
		this.amountlimit = amountlimit;
		this.pstatus = pstatus;
		this.defaultAcc = defaultAcc;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "accaddeddate", length = 19)
	public Date getAccaddeddate() {
		return this.accaddeddate;
	}

	@Column(name = "accrefnumber", length = 35)
	public String getAccrefnumber() {
		return this.accrefnumber;
	}

	@Column(name = "acctype", length = 10)
	public String getAcctype() {
		return this.acctype;
	}

	@Column(name = "aeba", length = 1)
	public String getAeba() {
		return this.aeba;
	}

	@Column(name = "amountlimit")
	public Integer getAmountlimit() {
		return this.amountlimit;
	}

	@Column(name = "bankname", length = 50)
	public String getBankname() {
		return this.bankname;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createddate", length = 19)
	public Date getCreateddate() {
		return this.createddate;
	}

	@Column(name = "credjson", length = 16777215)
	public String getCredjson() {
		return this.credjson;
	}

	@Column(name = "defaultAcc")
	public Integer getDefaultAcc() {
		return this.defaultAcc;
	}

	@Column(name = "ifsc", length = 11)
	public String getIfsc() {
		return this.ifsc;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "listaccountid", unique = true, nullable = false)
	public Long getListaccountid() {
		return this.listaccountid;
	}

	@Column(name = "maskedaccnumber", length = 35)
	public String getMaskedaccnumber() {
		return this.maskedaccnumber;
	}

	@Column(name = "mbeba", length = 1)
	public String getMbeba() {
		return this.mbeba;
	}

	@Column(name = "mmid", length = 7)
	public String getMmid() {
		return this.mmid;
	}

	@Column(name = "mobileNo", length = 12)
	public String getMobileNo() {
		return this.mobileNo;
	}

	@Column(name = "pcode", length = 4)
	public String getPcode() {
		return this.pcode;
	}

	@Column(name = "pname", length = 100)
	public String getPname() {
		return this.pname;
	}

	@Column(name = "pstatus")
	public Integer getPstatus() {
		return this.pstatus;
	}

	@Column(name = "ptype", length = 10)
	public String getPtype() {
		return this.ptype;
	}

	@Column(name = "regvpaid", nullable = false)
	public Long getRegvpaid() {
		return this.regvpaid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatedate", length = 19)
	public Date getUpdatedate() {
		return this.updatedate;
	}

	@Column(name = "vpa", length = 50)
	public String getVpa() {
		return this.vpa;
	}

	public void setAccaddeddate(Date accaddeddate) {
		this.accaddeddate = accaddeddate;
	}

	public void setAccrefnumber(String accrefnumber) {
		this.accrefnumber = accrefnumber;
	}

	public void setAcctype(String acctype) {
		this.acctype = acctype;
	}

	public void setAeba(String aeba) {
		this.aeba = aeba;
	}

	public void setAmountlimit(Integer amountlimit) {
		this.amountlimit = amountlimit;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	public void setCredjson(String credjson) {
		this.credjson = credjson;
	}

	public void setDefaultAcc(Integer defaultAcc) {
		this.defaultAcc = defaultAcc;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

	public void setListaccountid(Long listaccountid) {
		this.listaccountid = listaccountid;
	}

	public void setMaskedaccnumber(String maskedaccnumber) {
		this.maskedaccnumber = maskedaccnumber;
	}

	public void setMbeba(String mbeba) {
		this.mbeba = mbeba;
	}

	public void setMmid(String mmid) {
		this.mmid = mmid;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public void setPstatus(Integer pstatus) {
		this.pstatus = pstatus;
	}

	public void setPtype(String ptype) {
		this.ptype = ptype;
	}

	public void setRegvpaid(Long regvpaid) {
		this.regvpaid = regvpaid;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public void setVpa(String vpa) {
		this.vpa = vpa;
	}
}
