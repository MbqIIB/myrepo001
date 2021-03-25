package com.npst.upiserver.dto;

public class CustomerAccountDto {
	private String accrefnumber;
	private String accvirtualid;
	private Long regid;
	private Integer status;
	private Integer defaccount;
	private String name;
	private String merchantType;

	public CustomerAccountDto(String accrefnumber, String accvirtualid, Long regid, String name) {
		this.accrefnumber = accrefnumber;
		this.accvirtualid = accvirtualid;
		this.regid = regid;
		this.name = name;
	}

	public CustomerAccountDto(String accrefnumber, String accvirtualid, Long regid, Integer status,
			Integer defaccount) {
		this.accrefnumber = accrefnumber;
		this.accvirtualid = accvirtualid;
		this.regid = regid;
		this.status = status;
		this.defaccount = defaccount;
	}

	public CustomerAccountDto(String accrefnumber, String accvirtualid, Long regid, Integer status, Integer defaccount,
			String merchantType) {
		this.accrefnumber = accrefnumber;
		this.accvirtualid = accvirtualid;
		this.regid = regid;
		this.status = status;
		this.defaccount = defaccount;
		this.merchantType = merchantType;
	}

	public CustomerAccountDto() {

	}

	public String getAccrefnumber() {
		return accrefnumber;
	}

	public void setAccrefnumber(String accrefnumber) {
		this.accrefnumber = accrefnumber;
	}

	public String getAccvirtualid() {
		return accvirtualid;
	}

	public void setAccvirtualid(String accvirtualid) {
		this.accvirtualid = accvirtualid;
	}

	public Long getRegid() {
		return regid;
	}

	public void setRegid(Long regid) {
		this.regid = regid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getDefaccount() {
		return defaccount;
	}

	public void setDefaccount(Integer defaccount) {
		this.defaccount = defaccount;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
