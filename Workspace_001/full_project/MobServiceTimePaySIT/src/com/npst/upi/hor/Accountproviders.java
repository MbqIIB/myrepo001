package com.npst.upi.hor;

// Generated 19 Aug, 2017 5:33:34 PM by Hibernate Tools 5.2.3.Final

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

/**
 * Accountproviders generated by hbm2java
 */
@Entity
@Table(name = "accountproviders")
public class Accountproviders implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -4380431166130527794L;
	private long idaccountproviders;
	private Reqresplistaccpvd reqresplistaccpvd;
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
	private Byte rank;

	public Accountproviders() {
	}

	public Accountproviders(long idaccountproviders, Reqresplistaccpvd reqresplistaccpvd, String name, String ifsc,
			String iin, String isactive, String lastmodifiedts, String prods, Date recorddate, String spocphone,
			String spocemail, String spocname, String url) {
		this.idaccountproviders = idaccountproviders;
		this.reqresplistaccpvd = reqresplistaccpvd;
		this.name = name;
		this.ifsc = ifsc;
		this.iin = iin;
		this.isactive = isactive;
		this.lastmodifiedts = lastmodifiedts;
		this.prods = prods;
		this.recorddate = recorddate;
		this.spocphone = spocphone;
		this.spocemail = spocemail;
		this.spocname = spocname;
		this.url = url;
	}

	@Column(name = "RANK")
	@ColumnDefault("'0'")
	public Byte getRank() {
		return rank;
	}

	public void setRank(Byte rank) {
		this.rank = rank;
	}

	public Accountproviders(long idaccountproviders, String name) {
		this.idaccountproviders = idaccountproviders;
		this.name = name;
	}

	// @Id
	//
	// @Column(name = "IDACCOUNTPROVIDERS", unique = true, nullable = false,
	// precision = 22, scale = 0)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDACCOUNTPROVIDERS", unique = true, nullable = false)
	public long getIdaccountproviders() {
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

	/**
	 * @return the mobRegFormat
	 */

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

	@Column(name = "RECORDDATE")
	public Date getRecorddate() {
		return this.recorddate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDREQRESPLISTACCPVD")
	public Reqresplistaccpvd getReqresplistaccpvd() {
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

	public void setIdaccountproviders(long idaccountproviders) {
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

	/**
	 * @param mobRegFormat
	 *            the mobRegFormat to set
	 */
	public void setMobRegFormat(String mobRegFormat) {
		this.mobRegFormat = mobRegFormat;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProds(String prods) {
		this.prods = prods;
	}

	public void setRecorddate(Date recorddate) {
		this.recorddate = recorddate;
	}

	public void setReqresplistaccpvd(Reqresplistaccpvd reqresplistaccpvd) {
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
