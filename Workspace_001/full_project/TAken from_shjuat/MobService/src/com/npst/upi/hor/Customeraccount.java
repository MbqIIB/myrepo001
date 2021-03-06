package com.npst.upi.hor;
// Generated 5 Nov, 2017 2:33:28 PM by Hibernate Tools 5.2.3.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Customeraccount generated by hbm2java
 */
@Entity
@Table(name = "customeraccount")
public class Customeraccount implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	private Long regid;
	private Integer status;
	private Date deleteddate;

	private String accPvdFormat;
	private String accPvdIfsc;
	
	public Customeraccount() {
	}

	public Customeraccount(Date accaddeddate, String accrefnumber, String acctype, String accvirtualid, String aeba,
			String bankname, String credjson, String custcode, String custtype, Integer defaccount, String ifsc,
			String limitamount, String maskedaccnumber, String mbeba, String mmid, String mobileno, String name,
			Date pspblockdate, Long regid, Integer status,String accPvdFormat,String accPvdIfsc) {
		this.accaddeddate = accaddeddate;
		this.accrefnumber = accrefnumber;
		this.acctype = acctype;
		this.accvirtualid = accvirtualid;
		this.aeba = aeba;
		this.bankname = bankname;
		this.credjson = credjson;
		this.custcode = custcode;
		this.custtype = custtype;
		this.defaccount = defaccount;
		this.ifsc = ifsc;
		this.limitamount = limitamount;
		this.maskedaccnumber = maskedaccnumber;
		this.mbeba = mbeba;
		this.mmid = mmid;
		this.mobileno = mobileno;
		this.name = name;
		this.pspblockdate = pspblockdate;
		this.regid = regid;
		this.status = status;
		this.accPvdFormat = accPvdFormat;
		this.accPvdIfsc = accPvdIfsc;
		
	}

	public Customeraccount(String accvirtualid, String mobileno) {
		this.accvirtualid = accvirtualid;
		this.mobileno = mobileno;
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
	public Integer getCaid() {
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

	
	@Column(name = "ACCPVDFORMAT")
	public String getAccPvdFormat() {
		return accPvdFormat;
	}

	public void setAccPvdFormat(String accPvdFormat) {
		this.accPvdFormat = accPvdFormat;
	}

	@Column(name = "ACCPVDIFSC")
	public String getAccPvdIfsc() {
		return accPvdIfsc;
	}

	public void setAccPvdIfsc(String accPvdIfsc) {
		this.accPvdIfsc = accPvdIfsc;
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

	public void setRegid(Long regid) {
		this.regid = regid;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Customeraccount [");
		if (caid != null) {
			builder.append("caid=");
			builder.append(caid);
			builder.append(", ");
		}
		if (accaddeddate != null) {
			builder.append("accaddeddate=");
			builder.append(accaddeddate);
			builder.append(", ");
		}
		if (accrefnumber != null) {
			builder.append("accrefnumber=");
			builder.append(accrefnumber);
			builder.append(", ");
		}
		if (acctype != null) {
			builder.append("acctype=");
			builder.append(acctype);
			builder.append(", ");
		}
		if (accvirtualid != null) {
			builder.append("accvirtualid=");
			builder.append(accvirtualid);
			builder.append(", ");
		}
		if (aeba != null) {
			builder.append("aeba=");
			builder.append(aeba);
			builder.append(", ");
		}
		if (bankname != null) {
			builder.append("bankname=");
			builder.append(bankname);
			builder.append(", ");
		}
		if (credjson != null) {
			builder.append("credjson=");
			builder.append(credjson);
			builder.append(", ");
		}
		if (custcode != null) {
			builder.append("custcode=");
			builder.append(custcode);
			builder.append(", ");
		}
		if (custtype != null) {
			builder.append("custtype=");
			builder.append(custtype);
			builder.append(", ");
		}
		if (defaccount != null) {
			builder.append("defaccount=");
			builder.append(defaccount);
			builder.append(", ");
		}
		if (ifsc != null) {
			builder.append("ifsc=");
			builder.append(ifsc);
			builder.append(", ");
		}
		if (limitamount != null) {
			builder.append("limitamount=");
			builder.append(limitamount);
			builder.append(", ");
		}
		if (maskedaccnumber != null) {
			builder.append("maskedaccnumber=");
			builder.append(maskedaccnumber);
			builder.append(", ");
		}
		if (mbeba != null) {
			builder.append("mbeba=");
			builder.append(mbeba);
			builder.append(", ");
		}
		if (mmid != null) {
			builder.append("mmid=");
			builder.append(mmid);
			builder.append(", ");
		}
		if (mobileno != null) {
			builder.append("mobileno=");
			builder.append(mobileno);
			builder.append(", ");
		}
		if (name != null) {
			builder.append("name=");
			builder.append(name);
			builder.append(", ");
		}
		if (pspblockdate != null) {
			builder.append("pspblockdate=");
			builder.append(pspblockdate);
			builder.append(", ");
		}
		if (regid != null) {
			builder.append("regid=");
			builder.append(regid);
			builder.append(", ");
		}
		if (status != null) {
			builder.append("status=");
			builder.append(status);
		}
		
		if (accPvdFormat != null) {
			builder.append("accPvdFormat=");
			builder.append(accPvdFormat);
		}
		if (accPvdIfsc != null) {
			builder.append("accPvdIfsc=");
			builder.append(accPvdIfsc);
		}
		
		builder.append("]");
		return builder.toString();
	}

	@Column(name = "DELETEDDATE")
	public Date getDeleteddate() {
		return deleteddate;
	}

	public void setDeleteddate(Date deleteddate) {
		this.deleteddate = deleteddate;
	}

	/*
	 * public Boolean isDeleted() { return this.status == ConstantI.DELETE_ACCOUNT;
	 * }
	 */

}
