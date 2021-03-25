package com.npst.upi.hor;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Custvpa implements Serializable {
	
	@Column(name = "regId")
	private int		regId;
	
	@Column(name = "regVPA", unique = true)
	private String	regVpa;
	
	public int getRegId() {
		return regId;
	}
	
	public void setRegId(int regId) {
		this.regId = regId;
	}
	
	public String getRegVpa() {
		return regVpa;
	}
	
	public void setRegVpa(String regVpa) {
		this.regVpa = regVpa;
	}
	
	
	
	public Custvpa() {
	}

	public Custvpa(int regId, String regVpa) {
		super();
		this.regId = regId;
		this.regVpa = regVpa;
	}

	@Override
	public boolean equals(Object obj) {
		boolean flag = false;
		Custvpa cVpa = (Custvpa) obj;
		if (cVpa.getRegId() == regId && cVpa.getRegVpa().equalsIgnoreCase(regVpa)) flag = true;
		
		return flag;
	}
	
	@Override
	public int hashCode() {
		int result = regVpa.hashCode();
		result = 31 * result + regId;
		return result;
	}
	
}