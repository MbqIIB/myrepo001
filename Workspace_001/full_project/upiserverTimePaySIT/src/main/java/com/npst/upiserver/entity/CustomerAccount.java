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
@Table(name = "customeraccount")
public class CustomerAccount implements java.io.Serializable {
	private static final long	serialVersionUID	= 1L;
	private Long caid;
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
	private Long regid;
	private Integer status;
	private Date updatedDate;
	private String migrated;
	private String actype = "1";
	private String accPvdFormat;
	private String accPvdIfsc;
	private String merchantType;
	
	public CustomerAccount() {
	}
	
	public CustomerAccount(String accvirtualid, String mobileno) {
		this.accvirtualid = accvirtualid;
		this.mobileno = mobileno;
	}
	
	@Column(name = "MERCHANT_TYPE" ,length=10)
	public String getMerchantType() {
		return merchantType;
	}

	@Column(name = "ACCPVDIFSC")
	public String getAccPvdIfsc() {
		return accPvdIfsc;
	}

	@Column(name = "ACCPVDFORMAT")
	public String getAccPvdFormat() {
		return accPvdFormat;
	}

	@Column(name = "ACTYPE", length = 1)
	public String getActype() {
		return actype;
	}
	@Column(name = "updated_date")
	public Date getUpdatedDate() {
		return updatedDate;
	}
	
	@Column(name = "MIGRATED", length = 1)
	public String getMigrated() {
		return migrated;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ACCADDEDDATE", length = 19)
	public Date getAccaddeddate() {
		return this.accaddeddate;
	}
	
	@Column(name = "ACCREFNUMBER", length = 35)
	public String getAccrefnumber() {
		return this.accrefnumber;
	}
	
	@Column(name = "ACCTYPE", length = 20)
	public String getAcctype() {
		return this.acctype;
	}
	
	@Column(name = "ACCVIRTUALID", nullable = false, length = 50)
	public String getAccvirtualid() {
		return this.accvirtualid;
	}
	
	@Column(name = "AEBA", length = 20)
	public String getAeba() {
		return this.aeba;
	}
	
	@Column(name = "BANKNAME", length = 50)
	public String getBankname() {
		return this.bankname;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "CAID", unique = true, nullable = false)
	public Long getCaid() {
		return this.caid;
	}
	
	@Column(name = "CREDJSON", length = 500)
	public String getCredjson() {
		return this.credjson;
	}
	
	@Column(name = "CUSTCODE", length = 20)
	public String getCustcode() {
		return this.custcode;
	}
	
	@Column(name = "CUSTTYPE", length = 20)
	public String getCusttype() {
		return this.custtype;
	}
	
	@Column(name = "DEFACCOUNT")
	public Integer getDefaccount() {
		return this.defaccount;
	}
	
	@Column(name = "IFSC", length = 15)
	public String getIfsc() {
		return this.ifsc;
	}
	
	@Column(name = "LIMITAMOUNT", length = 20)
	public String getLimitamount() {
		return this.limitamount;
	}
	
	@Column(name = "MASKEDACCNUMBER", length = 35)
	public String getMaskedaccnumber() {
		return this.maskedaccnumber;
	}
	
	@Column(name = "MBEBA", length = 20)
	public String getMbeba() {
		return this.mbeba;
	}
	
	@Column(name = "MMID", length = 20)
	public String getMmid() {
		return this.mmid;
	}
	
	@Column(name = "MOBILENO", nullable = false, length = 12)
	public String getMobileno() {
		return this.mobileno;
	}
	
	@Column(name = "NAME", length = 50)
	public String getName() {
		return this.name;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PSPBLOCKDATE", length = 19)
	public Date getPspblockdate() {
		return this.pspblockdate;
	}
	
	@Column(name = "REGID")
	public Long getRegid() {
		return this.regid;
	}
	
	@Column(name = "STATUS")
	public Integer getStatus() {
		return this.status;
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
	
	public void setCaid(Long caid) {
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
	
	public void setRegid(Long regid) {
		this.regid = regid;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public void setMigrated(String migrated) {
		this.migrated = migrated;
	}

	public void setActype(String actype) {
		this.actype = actype;
	}

	public void setAccPvdFormat(String accPvdFormat) {
		this.accPvdFormat = accPvdFormat;
	}

	public void setAccPvdIfsc(String accPvdIfsc) {
		this.accPvdIfsc = accPvdIfsc;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CustomerAccount [caid=");
		builder.append(caid);
		builder.append(", accaddeddate=");
		builder.append(accaddeddate);
		builder.append(", accrefnumber=");
		builder.append(accrefnumber);
		builder.append(", acctype=");
		builder.append(acctype);
		builder.append(", accvirtualid=");
		builder.append(accvirtualid);
		builder.append(", aeba=");
		builder.append(aeba);
		builder.append(", bankname=");
		builder.append(bankname);
		builder.append(", credjson=");
		builder.append(credjson);
		builder.append(", custcode=");
		builder.append(custcode);
		builder.append(", custtype=");
		builder.append(custtype);
		builder.append(", defaccount=");
		builder.append(defaccount);
		builder.append(", ifsc=");
		builder.append(ifsc);
		builder.append(", limitamount=");
		builder.append(limitamount);
		builder.append(", maskedaccnumber=");
		builder.append(maskedaccnumber);
		builder.append(", mbeba=");
		builder.append(mbeba);
		builder.append(", mmid=");
		builder.append(mmid);
		builder.append(", mobileno=");
		builder.append(mobileno);
		builder.append(", name=");
		builder.append(name);
		builder.append(", pspblockdate=");
		builder.append(pspblockdate);
		builder.append(", regid=");
		builder.append(regid);
		builder.append(", status=");
		builder.append(status);
		builder.append(", updatedDate=");
		builder.append(updatedDate);
		builder.append(", migrated=");
		builder.append(migrated);
		builder.append(", actype=");
		builder.append(actype);
		builder.append(", accPvdFormat=");
		builder.append(accPvdFormat);
		builder.append(", accPvdIfsc=");
		builder.append(accPvdIfsc);
		builder.append(", merchantType=");
		builder.append(merchantType);
		builder.append("]");
		return builder.toString();
	}

	
	
}
