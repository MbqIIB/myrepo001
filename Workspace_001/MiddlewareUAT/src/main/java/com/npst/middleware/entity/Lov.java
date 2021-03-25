package com.npst.middleware.entity;
// Generated 5 Mar, 2017 5:02:39 PM by Hibernate Tools 5.2.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Lov generated by hbm2java
 */
@Entity
@Table(name = "lov")
public class Lov implements java.io.Serializable {
	
	/**
	 *
	 */
	private static final long	serialVersionUID	= 1L;
	private Integer				idLov;
	private String				lovType;
	private String				lovValues;
	
	public Lov() {
	}
	
	public Lov(String lovType, String lovValues) {
		this.lovType = lovType;
		this.lovValues = lovValues;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	
	@Column(name = "idLov", unique = true, nullable = false)
	public Integer getIdLov() {
		return this.idLov;
	}
	
	@Column(name = "LovType", length = 500)
	public String getLovType() {
		return this.lovType;
	}
	
	@Column(name = "LovValues", length = 500)
	public String getLovValues() {
		return this.lovValues;
	}
	
	public void setIdLov(Integer idLov) {
		this.idLov = idLov;
	}
	
	public void setLovType(String lovType) {
		this.lovType = lovType;
	}
	
	public void setLovValues(String lovValues) {
		this.lovValues = lovValues;
	}
	
}
