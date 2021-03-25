package com.npst.upi.hor;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "masterdetails")
public class MasterConfig implements Serializable {

	private static final long serialVersionUID = 7395142587901431893L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "code")
	private String code;

	@Column(name = "value")
	private String value;

	@Column(name = "code_type")
	private String codeType;

	@Column(name = "status")
	private Integer status;

	public MasterConfig() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "MasterEntity [id=" + id + ", code=" + code + ", value=" + value + ", codeType=" + codeType + ", status="
				+ status + "]";
	}

	public MasterConfig(String code, String value, String codeType, Integer status) {
		super();
		this.code = code;
		this.value = value;
		this.codeType = codeType;
		this.status = status;
	}

}