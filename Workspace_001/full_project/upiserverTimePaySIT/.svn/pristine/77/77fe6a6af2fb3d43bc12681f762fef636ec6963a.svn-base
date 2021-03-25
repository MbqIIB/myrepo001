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
@Table(name = "spamvpa")
public class SpamVpa implements java.io.Serializable {
	
	private Long	idspamvpa;
	private String	spamvirtualid;
	private String	txnid;
	private Date	addeddate;
	private Long		regid;
	
	public SpamVpa() {
	}
	
	public SpamVpa(String spamvirtualid, String txnid, Date addeddate, Long regid) {
		this.spamvirtualid = spamvirtualid;
		this.txnid = txnid;
		this.addeddate = addeddate;
		this.regid = regid;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "addeddate", nullable = false, length = 10)
	public Date getAddeddate() {
		return this.addeddate;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idspamvpa", unique = true, nullable = false)
	public Long getIdspamvpa() {
		return this.idspamvpa;
	}
	
	@Column(name = "regid", nullable = false)
	public Long getRegid() {
		return this.regid;
	}
	
	@Column(name = "spamvirtualid", nullable = false, length = 100)
	public String getSpamvirtualid() {
		return this.spamvirtualid;
	}
	
	@Column(name = "txnid", nullable = false, length = 36)
	public String getTxnid() {
		return this.txnid;
	}
	
	public void setAddeddate(Date addeddate) {
		this.addeddate = addeddate;
	}
	
	public void setIdspamvpa(Long idspamvpa) {
		this.idspamvpa = idspamvpa;
	}
	
	public void setRegid(Long regid) {
		this.regid = regid;
	}
	
	public void setSpamvirtualid(String spamvirtualid) {
		this.spamvirtualid = spamvirtualid;
	}
	
	public void setTxnid(String txnid) {
		this.txnid = txnid;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Spamvpa [");
		if (idspamvpa != null) {
			builder.append("idspamvpa=").append(idspamvpa).append(", ");
		}
		if (spamvirtualid != null) {
			builder.append("spamvirtualid=").append(spamvirtualid).append(", ");
		}
		if (txnid != null) {
			builder.append("txnid=").append(txnid).append(", ");
		}
		if (addeddate != null) {
			builder.append("addeddate=").append(addeddate).append(", ");
		}
		if (regid != null) {
			builder.append("regid=").append(regid);
		}
		builder.append("]");
		return builder.toString();
	}

}
