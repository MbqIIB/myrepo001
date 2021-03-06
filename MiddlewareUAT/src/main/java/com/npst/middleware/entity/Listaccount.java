package com.npst.middleware.entity;
// Generated 5 Mar, 2017 5:02:39 PM by Hibernate Tools 5.2.0.CR1

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Listaccount generated by hbm2java
 */
@Entity
@Table(name = "listaccount")
public class Listaccount implements java.io.Serializable {
	
	/**
	 *
	 */
	private static final long	serialVersionUID	= 1L;
//	private ListaccountId		id;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "listaccountid", unique = true, nullable = false)
	private Integer 			id;
	private String				vpa;
	private String				code;
	private String				name;
	private String				type;
	private String				accType;
	private String				mbeba;
	private String				accRefNumber;
	private String				maskedAccNumber;
	private String				ifsc;
	private String				mmid;
	private String				aeba;
	private String				credType;
	private String				credSubtype;
	private String				credDtype;
	private String				credDlength;
	private String				bankName;
	private Date				accAddedDate;
	private String				defVpa;
	private String				defAcc;
	private String				limtAmount;
	private String				status;
	
	public Listaccount() {
	}
	
//	public Listaccount(ListaccountId id) {
//		this.id = id;
//	}
	
	public Integer getId() {
		return id;
	}

	public Listaccount(Integer id, String vpa, String code, String name, String type, String accType,
			String mbeba, String accRefNumber, String maskedAccNumber, String ifsc, String mmid, String aeba,
			String credType, String credSubtype, String credDtype, String credDlength, String bankName,
			Date accAddedDate, String defVpa, String defAcc, String limtAmount, String status) {
		this.id = id;
		this.vpa = vpa;
		this.code = code;
		this.name = name;
		this.type = type;
		this.accType = accType;
		this.mbeba = mbeba;
		this.accRefNumber = accRefNumber;
		this.maskedAccNumber = maskedAccNumber;
		this.ifsc = ifsc;
		this.mmid = mmid;
		this.aeba = aeba;
		this.credType = credType;
		this.credSubtype = credSubtype;
		this.credDtype = credDtype;
		this.credDlength = credDlength;
		this.bankName = bankName;
		this.accAddedDate = accAddedDate;
		this.defVpa = defVpa;
		this.defAcc = defAcc;
		this.limtAmount = limtAmount;
		this.status = status;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "AccAddedDate", length = 19)
	public Date getAccAddedDate() {
		return this.accAddedDate;
	}
	
	@Column(name = "AccRefNumber", length = 35)
	public String getAccRefNumber() {
		return this.accRefNumber;
	}
	
	@Column(name = "AccType", length = 45)
	public String getAccType() {
		return this.accType;
	}
	
	@Column(name = "Aeba", length = 10)
	public String getAeba() {
		return this.aeba;
	}
	
	@Column(name = "BankName", length = 50)
	public String getBankName() {
		return this.bankName;
	}
	
	@Column(name = "Code", length = 20)
	public String getCode() {
		return this.code;
	}
	
	@Column(name = "CredDLength", length = 10)
	public String getCredDlength() {
		return this.credDlength;
	}
	
	@Column(name = "CredDType", length = 20)
	public String getCredDtype() {
		return this.credDtype;
	}
	
	@Column(name = "CredSubtype", length = 10)
	public String getCredSubtype() {
		return this.credSubtype;
	}
	
	@Column(name = "CredType", length = 10)
	public String getCredType() {
		return this.credType;
	}
	
	@Column(name = "DefAcc", length = 1)
	public String getDefAcc() {
		return this.defAcc;
	}
	
	@Column(name = "DefVpa", length = 1)
	public String getDefVpa() {
		return this.defVpa;
	}
	
//	@EmbeddedId
//	
//	@AttributeOverrides({
//			@AttributeOverride(name = "mobNo", column = @Column(name = "MobNo", nullable = false, length = 12)),
//			@AttributeOverride(name = "sno", column = @Column(name = "Sno", nullable = false))
//	})
//	public ListaccountId getId() {
//		return this.id;
//	}
	
	@Column(name = "Ifsc", length = 15)
	public String getIfsc() {
		return this.ifsc;
	}
	
	@Column(name = "LimtAmount", length = 20)
	public String getLimtAmount() {
		return this.limtAmount;
	}
	
	@Column(name = "MaskedAccNumber", length = 35)
	public String getMaskedAccNumber() {
		return this.maskedAccNumber;
	}
	
	@Column(name = "Mbeba", length = 10)
	public String getMbeba() {
		return this.mbeba;
	}
	
	@Column(name = "Mmid", length = 15)
	public String getMmid() {
		return this.mmid;
	}
	
	@Column(name = "Name", length = 50)
	public String getName() {
		return this.name;
	}
	
	@Column(name = "Status", length = 45)
	public String getStatus() {
		return this.status;
	}
	
	@Column(name = "Type", length = 20)
	public String getType() {
		return this.type;
	}
	
	@Column(name = "Vpa")
	public String getVpa() {
		return this.vpa;
	}
	
	public void setAccAddedDate(Date accAddedDate) {
		this.accAddedDate = accAddedDate;
	}
	
	public void setAccRefNumber(String accRefNumber) {
		this.accRefNumber = accRefNumber;
	}
	
	public void setAccType(String accType) {
		this.accType = accType;
	}
	
	public void setAeba(String aeba) {
		this.aeba = aeba;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public void setCredDlength(String credDlength) {
		this.credDlength = credDlength;
	}
	
	public void setCredDtype(String credDtype) {
		this.credDtype = credDtype;
	}
	
	public void setCredSubtype(String credSubtype) {
		this.credSubtype = credSubtype;
	}
	
	public void setCredType(String credType) {
		this.credType = credType;
	}
	
	public void setDefAcc(String defAcc) {
		this.defAcc = defAcc;
	}
	
	public void setDefVpa(String defVpa) {
		this.defVpa = defVpa;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}
	
	public void setLimtAmount(String limtAmount) {
		this.limtAmount = limtAmount;
	}
	
	public void setMaskedAccNumber(String maskedAccNumber) {
		this.maskedAccNumber = maskedAccNumber;
	}
	
	public void setMbeba(String mbeba) {
		this.mbeba = mbeba;
	}
	
	public void setMmid(String mmid) {
		this.mmid = mmid;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setVpa(String vpa) {
		this.vpa = vpa;
	}
	
}
