package com.npst.upiserver.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "transno", uniqueConstraints = @UniqueConstraint(columnNames = "OrgId"))
public class Transno implements java.io.Serializable {
	
	/**
	 *
	 */
	private static final long	serialVersionUID	= 1L;
	private int					transNo;
	private Integer				orgId;
	private String				handle;
	private Integer				nbin;
	private String				ifsc;
	private Integer				seqNo;
	
	public Transno() {
	}
	
	public Transno(int transNo) {
		this.transNo = transNo;
	}
	
	public Transno(int transNo, Integer orgId, String handle, Integer nbin, String ifsc, Integer seqNo) {
		this.transNo = transNo;
		this.orgId = orgId;
		this.handle = handle;
		this.nbin = nbin;
		this.ifsc = ifsc;
		this.seqNo = seqNo;
	}
	
	@Column(name = "Handle", length = 10)
	public String getHandle() {
		return this.handle;
	}
	
	@Column(name = "IFSC", length = 12)
	public String getIfsc() {
		return this.ifsc;
	}
	
	@Column(name = "NBIN")
	public Integer getNbin() {
		return this.nbin;
	}
	
	@Column(name = "OrgId")
	public Integer getOrgId() {
		return this.orgId;
	}
	
	@Column(name = "seqNo")
	public Integer getSeqNo() {
		return this.seqNo;
	}
	
	@Id
	@Column(name = "TransNo", unique = true, nullable = false)
	public int getTransNo() {
		return this.transNo;
	}
	
	public void setHandle(String handle) {
		this.handle = handle;
	}
	
	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}
	
	public void setNbin(Integer nbin) {
		this.nbin = nbin;
	}
	
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	
	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}
	
	public void setTransNo(int transNo) {
		this.transNo = transNo;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Transno [transNo=");
		builder.append(transNo);
		builder.append(", ");
		if (orgId != null) {
			builder.append("orgId=");
			builder.append(orgId);
			builder.append(", ");
		}
		if (handle != null) {
			builder.append("handle=");
			builder.append(handle);
			builder.append(", ");
		}
		if (nbin != null) {
			builder.append("nbin=");
			builder.append(nbin);
			builder.append(", ");
		}
		if (ifsc != null) {
			builder.append("ifsc=");
			builder.append(ifsc);
			builder.append(", ");
		}
		if (seqNo != null) {
			builder.append("seqNo=");
			builder.append(seqNo);
		}
		builder.append("]");
		return builder.toString();
	}
	
}
