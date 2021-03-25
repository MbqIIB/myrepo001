package com.npst.upiserver.dto;

public class AccountProviderListResp {
	private String	name;
	private String	iin;
	private String	ifsc;
	private String	active;
	private String	url;
	private String	spocName;
	private String	spocEmail;
	private String	spocPhone;
	private String	prods;
	private String	mobRegFormat;
	private String	rank;
	
	public AccountProviderListResp() {
		
	}
	
	public AccountProviderListResp(String name, String iin, String ifsc, String active, String url, String spocName,
			String spocEmail, String spocPhone, String prods, String rank) {
		super();
		this.name = name;
		this.iin = iin;
		this.ifsc = ifsc;
		this.active = active;
		this.url = url;
		this.spocName = spocName;
		this.spocEmail = spocEmail;
		this.spocPhone = spocPhone;
		this.prods = prods;
		this.rank = rank;
	}
	
	public String getActive() {
		return active;
	}
	
	public String getIfsc() {
		return ifsc;
	}
	
	public String getIin() {
		return iin;
	}
	
	public String getMobRegFormat() {
		return mobRegFormat;
	}
	
	public String getName() {
		return name;
	}
	
	public String getProds() {
		return prods;
	}
	
	public String getRank() {
		return rank;
	}
	
	public String getSpocEmail() {
		return spocEmail;
	}
	
	public String getSpocName() {
		return spocName;
	}
	
	public String getSpocPhone() {
		return spocPhone;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setActive(String active) {
		this.active = active;
	}
	
	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}
	
	public void setIin(String iin) {
		this.iin = iin;
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
	
	public void setRank(String rank) {
		this.rank = rank;
	}
	
	public void setSpocEmail(String spocEmail) {
		this.spocEmail = spocEmail;
	}
	
	public void setSpocName(String spocName) {
		this.spocName = spocName;
	}
	
	public void setSpocPhone(String spocPhone) {
		this.spocPhone = spocPhone;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		return "AccountProviderListResp [name=" + name + ", iin=" + iin + ", ifsc=" + ifsc + ", active=" + active
				+ ", url=" + url + ", spocName=" + spocName + ", spocEmail=" + spocEmail + ", spocPhone=" + spocPhone
				+ ", prods=" + prods + "]";
	}
}
