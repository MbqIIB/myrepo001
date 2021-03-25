package com.npst.upiserver.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "accountproviders")
public class AccountProvidersEntity implements java.io.Serializable {
	private static final long serialVersionUID = -4380431166130527794L;
	private Long idaccountproviders;
	private ReqRespListAccPvdEntity reqresplistaccpvd;
	private String name;
	private String ifsc;
	private String iin;
	private String isactive;
	private String lastmodifiedts;
	private String prods;
	private Date recorddate;
	private String spocphone;
	private String spocemail;
	private String spocname;
	private String url;
	private String mobRegFormat;
	private Byte rank=0;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDACCOUNTPROVIDERS", unique = true, nullable = false)
	public Long getIdaccountproviders() {
		return this.idaccountproviders;
	}

	@Column(name = "IFSC")
	public String getIfsc() {
		return this.ifsc;
	}

	@Column(name = "IIN")
	public String getIin() {
		return this.iin;
	}

	@Column(name = "ISACTIVE")
	public String getIsactive() {
		return this.isactive;
	}

	@Column(name = "LASTMODIFIEDTS")
	public String getLastmodifiedts() {
		return this.lastmodifiedts;
	}

	@Column(name = "MOBREGFORMAT")
	public String getMobRegFormat() {
		return mobRegFormat;
	}

	@Column(name = "NAME", nullable = false)
	public String getName() {
		return this.name;
	}

	@Column(name = "PRODS")
	public String getProds() {
		return this.prods;
	}

	@Column(name = "RANK")
	@ColumnDefault("'0'")
	public Byte getRank() {
		return rank;
	}

	@Column(name = "RECORDDATE")
	public Date getRecorddate() {
		return this.recorddate;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "IDREQRESPLISTACCPVD")
	public ReqRespListAccPvdEntity getReqresplistaccpvd() {
		return this.reqresplistaccpvd;
	}

	@Column(name = "SPOCEMAIL")
	public String getSpocemail() {
		return this.spocemail;
	}

	@Column(name = "SPOCNAME")
	public String getSpocname() {
		return this.spocname;
	}

	@Column(name = "SPOCPHONE")
	public String getSpocphone() {
		return this.spocphone;
	}

	@Column(name = "URL")
	public String getUrl() {
		return this.url;
	}

	public void setIdaccountproviders(Long idaccountproviders) {
		this.idaccountproviders = idaccountproviders;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

	public void setIin(String iin) {
		this.iin = iin;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	public void setLastmodifiedts(String lastmodifiedts) {
		this.lastmodifiedts = lastmodifiedts;
	}

	public void setMobRegFormat(String mobRegFormat) {
		this.mobRegFormat = mobRegFormat;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProds(String prods) {
		this.prods = prods;
	}

	public void setRank(Byte rank) {
		this.rank = rank;
	}

	public void setRecorddate(Date recorddate) {
		this.recorddate = recorddate;
	}

	public void setReqresplistaccpvd(ReqRespListAccPvdEntity reqresplistaccpvd) {
		this.reqresplistaccpvd = reqresplistaccpvd;
	}

	public void setSpocemail(String spocemail) {
		this.spocemail = spocemail;
	}

	public void setSpocname(String spocname) {
		this.spocname = spocname;
	}

	public void setSpocphone(String spocphone) {
		this.spocphone = spocphone;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
