package com.npst.mobileservice.object;

import java.io.Serializable;
import java.util.Date;

import com.npst.upi.hor.Customeraccount;

public class CustomeraccountVO implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 7475380070965590391L;
	private Integer caid;
	private Date accaddeddate;
	private String accrefnumber;
	private String acctype;
	private String accvirtualid;
	private String aeba;
	private String bankname;
	private String credjson;
	private String custcode;
	private String custtype;
	private Integer defaccount;
	private String ifsc;
	private String limitamount;
	private String maskedaccnumber;
	private String mbeba;
	private String mmid;
	private String mobileno;
	private String name;
	private Date pspblockdate;
	private Integer regid;
	private Integer status;

	public CustomeraccountVO(Customeraccount customeraccount) {
		this.caid = customeraccount.getCaid();
		this.accaddeddate = customeraccount.getAccaddeddate();
		this.accrefnumber = customeraccount.getAccrefnumber();
		this.acctype = customeraccount.getAcctype();
		this.accvirtualid = customeraccount.getAccvirtualid();
		this.aeba = customeraccount.getAeba();
		this.bankname = customeraccount.getBankname();
		this.credjson = customeraccount.getCredjson();
		this.custcode = customeraccount.getCustcode();
		this.custtype = customeraccount.getCusttype();
		this.defaccount = customeraccount.getDefaccount();
		this.ifsc = customeraccount.getIfsc();
		this.limitamount = customeraccount.getLimitamount();
		this.maskedaccnumber = customeraccount.getMaskedaccnumber();
		this.mbeba = customeraccount.getMbeba();
		this.mmid = customeraccount.getMmid();
		this.mobileno = customeraccount.getMobileno();
		this.name = customeraccount.getName();
		this.pspblockdate = customeraccount.getPspblockdate();
		this.regid = customeraccount.getRegid();
		this.status = customeraccount.getStatus();
	}

	public Date getAccaddeddate() {
		return accaddeddate;
	}

	public String getAccrefnumber() {
		return accrefnumber;
	}

	public String getAcctype() {
		return acctype;
	}

	public String getAccvirtualid() {
		return accvirtualid;
	}

	public String getAeba() {
		return aeba;
	}

	public String getBankname() {
		return bankname;
	}

	public Integer getCaid() {
		return caid;
	}

	public String getCredjson() {
		return credjson;
	}

	public String getCustcode() {
		return custcode;
	}

	public String getCusttype() {
		return custtype;
	}

	public Integer getDefaccount() {
		return defaccount;
	}

	public String getIfsc() {
		return ifsc;
	}

	public String getLimitamount() {
		return limitamount;
	}

	public String getMaskedaccnumber() {
		return maskedaccnumber;
	}

	public String getMbeba() {
		return mbeba;
	}

	public String getMmid() {
		return mmid;
	}

	public String getMobileno() {
		return mobileno;
	}

	public String getName() {
		return name;
	}

	public Date getPspblockdate() {
		return pspblockdate;
	}

	public Integer getRegid() {
		return regid;
	}

	public Integer getStatus() {
		return status;
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

	public void setAccvirtualid(String accvirtualid) {
		this.accvirtualid = accvirtualid;
	}

	public void setAeba(String aeba) {
		this.aeba = aeba;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public void setCaid(Integer caid) {
		this.caid = caid;
	}

	public void setCredjson(String credjson) {
		this.credjson = credjson;
	}

	public void setCustcode(String custcode) {
		this.custcode = custcode;
	}

	public void setCusttype(String custtype) {
		this.custtype = custtype;
	}

	public void setDefaccount(Integer defaccount) {
		this.defaccount = defaccount;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

	public void setLimitamount(String limitamount) {
		this.limitamount = limitamount;
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

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPspblockdate(Date pspblockdate) {
		this.pspblockdate = pspblockdate;
	}

	public void setRegid(Integer regid) {
		this.regid = regid;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "CustomeraccountVO [caid=" + caid + ", accaddeddate=" + accaddeddate + ", accrefnumber=" + accrefnumber
				+ ", acctype=" + acctype + ", accvirtualid=" + accvirtualid + ", aeba=" + aeba + ", bankname="
				+ bankname + ", credjson=" + credjson + ", custcode=" + custcode + ", custtype=" + custtype
				+ ", defaccount=" + defaccount + ", ifsc=" + ifsc + ", limitamount=" + limitamount
				+ ", maskedaccnumber=" + maskedaccnumber + ", mbeba=" + mbeba + ", mmid=" + mmid + ", mobileno="
				+ mobileno + ", name=" + name + ", pspblockdate=" + pspblockdate + ", regid=" + regid + ", status="
				+ status + "]";
	}

}
