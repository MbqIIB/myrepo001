package com.npst.upiserver.dto;

public class RegDto {
	private Long regId;
	private String defvpa;
	private Integer status;
	private String mobno;
	private String appos;
	private String gcmtoken;
	
	public RegDto() {
	}

	public RegDto(Long regId,String defvpa,Integer status,String mobno,String appos,String gcmtoken) {
		this.regId=regId;
		this.defvpa=defvpa;
		this.status=status;
		this.mobno=mobno;
		this.appos=appos;
		this.gcmtoken=gcmtoken;
	}

	public Long getRegId() {
		return regId;
	}

	public void setRegId(Long regId) {
		this.regId = regId;
	}

	public String getDefvpa() {
		return defvpa;
	}

	public void setDefvpa(String defvpa) {
		this.defvpa = defvpa;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMobno() {
		return mobno;
	}

	public void setMobno(String mobno) {
		this.mobno = mobno;
	}

	public String getAppos() {
		return appos;
	}

	public void setAppos(String appos) {
		this.appos = appos;
	}

	public String getGcmtoken() {
		return gcmtoken;
	}

	public void setGcmtoken(String gcmtoken) {
		this.gcmtoken = gcmtoken;
	}
}
