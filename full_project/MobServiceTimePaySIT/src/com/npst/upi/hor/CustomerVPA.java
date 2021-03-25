package com.npst.upi.hor;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import sun.nio.cs.Surrogate.Generator;

@Entity
@Table(name = "customervpa")
public class CustomerVPA implements Serializable {
	
	private static final long	serialVersionUID	= 1L;
	
	@EmbeddedId
	private Custvpa				custVpa;
	
	@Column(name = "createDate", length = 19)
	private Date				createDate;
	
	@Column(name = "modifyDate", length = 19)
	private Date				modifyDate;
	
	private  transient boolean isDuplicateVPA;
	
	
	
	public CustomerVPA() {
	}

	public CustomerVPA(Custvpa custVpa,  Date modifyDate) {
		super();
		this.custVpa = custVpa;
		this.modifyDate = modifyDate;
	}

	public boolean isDuplicateVPA() {
		return isDuplicateVPA;
	}

	public void setDuplicateVPA(boolean isDuplicateVPA) {
		this.isDuplicateVPA = isDuplicateVPA;
	}

	public Custvpa getCustVpa() {
		return custVpa;
	}
	
	public void setCustVpa(Custvpa custVpa) {
		this.custVpa = custVpa;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Date getModifyDate() {
		return modifyDate;
	}
	
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CustomerVPA [");
		
		if (custVpa.getRegId() != 0) {
			builder.append("regId=");
			builder.append(custVpa.getRegId());
			builder.append(", ");
		}
		
		if (custVpa.getRegVpa() != null) {
			builder.append("regVpa=");
			builder.append(custVpa.getRegVpa());
			builder.append(", ");
		}
		
		if (modifyDate != null) {
			builder.append("modifyDate=");
			builder.append(modifyDate);
			builder.append(", ");
		}
		if (createDate != null) {
			builder.append("createDate=");
			builder.append(createDate);
			builder.append(", ");
		}
		return builder.toString();
	}
}

