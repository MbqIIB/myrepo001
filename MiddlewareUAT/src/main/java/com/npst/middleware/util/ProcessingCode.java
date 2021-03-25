package com.npst.middleware.util;

public enum ProcessingCode {
	GETACCOUNTLIST("950011"),
	CREDIT("950011"),
	DEBIT("950011");
	public static ProcessingCode fromValue(String v) {
		for (ProcessingCode c : ProcessingCode.values()) {
			if (c.value.equals(v)) { return c; }
		}
		throw new IllegalArgumentException(v);
	}
	
	private final String value;
	
	ProcessingCode(String v) {
		value = v;
	}
	
	public String value() {
		return value;
	}
}
